package bln.fin.ws.client.invoiceRev;

import bln.fin.entity.enums.BatchStatusEnum;
import bln.fin.entity.enums.DirectionEnum;
import bln.fin.entity.pi.InvoiceRevInterface;
import bln.fin.entity.pi.Session;
import bln.fin.entity.pi.SessionMessage;
import bln.fin.repo.InvoiceRevInterfaceRepo;
import bln.fin.repo.SessionMessageRepo;
import bln.fin.ws.SessionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;
import sap.invoiceRev.ObjectFactory;
import sap.invoiceRev.Response;
import sap.invoiceRev.ReversedInvoice;
import javax.xml.bind.JAXBElement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static bln.fin.common.Util.toXMLGregorianCalendar;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class InvoiceRevClientServiceImpl implements InvoiceRevClientService {
    private static final Logger logger = LoggerFactory.getLogger(InvoiceRevClientService.class);
    private static final String objectCode = "InvoiceRev";
    private final InvoiceRevInterfaceRepo invoiceRevInterfaceRepo;
    private final WebServiceTemplate saleInvoiceRevServiceTemplate;
    private final SessionService sessionService;
    private final SessionMessageRepo sessionMessageRepo;

    @Override
    public void send() {
        List<InvoiceRevInterface> list = invoiceRevInterfaceRepo.findAllByStatus(BatchStatusEnum.W);
        if (list.isEmpty()) return;

        logger.info("started");

        Session session = sessionService.createSession(objectCode, DirectionEnum.EXPORT);
        List<ReversedInvoice.Item> items = createInvoiceRevItems(list);

        ReversedInvoice invoiceRevReq = new ObjectFactory().createReversedInvoice();
        invoiceRevReq.getItem().addAll(items);
        debugRequest(items);

        try {
            JAXBElement<Response> response = (JAXBElement<Response>) saleInvoiceRevServiceTemplate.marshalSendAndReceive(invoiceRevReq);
            List<SessionMessage> messages = saveMessages(response.getValue(), session);
            updateStatuses(list, messages, session);
            sessionService.successSession(session, (long) items.size());
        }

        catch (SoapFaultClientException e) {
            logger.error("Fault Code: " + e.getFaultCode());
            logger.error("Fault Reason: " + e.getFaultStringOrReason());
            sessionService.errorSession(session, e);
        }

        catch (Exception e) {
            logger.debug("Error during request: " + e.getMessage());
            sessionService.errorSession(session, e);
        }
        logger.info("completed");
    }


    private List<ReversedInvoice.Item> createInvoiceRevItems(List<InvoiceRevInterface> list) {
        return list
            .stream()
            .map(t -> createInvoiceRevItem(t))
            .filter(t -> t != null)
            .collect(toList());
    }

    private ReversedInvoice.Item createInvoiceRevItem(InvoiceRevInterface line) {
        logger.debug("Creating item:: id = " + line.getId());
        ReversedInvoice.Item item = new ReversedInvoice.Item();
        item.setId(line.getId());
        item.setDocDate(toXMLGregorianCalendar(line.getDocDate()));
        item.setDocNum(line.getDocNum());
        item.setRevDate(toXMLGregorianCalendar(line.getRevDate()));
        logger.debug("Creating item successfully completed");
        return item;
    }

    private List<SessionMessage> saveMessages(Response response, Session session) {
        List<SessionMessage> list = new ArrayList<>();
        for (Response.Item item : response.getItem()) {
            if (item.getMsgType() == null && item.getMsgNum() == null && item.getMsg() == null)
                continue;

            SessionMessage sessionMessage = new SessionMessage();
            sessionMessage.setSession(session);
            sessionMessage.setObjectCode(objectCode);
            sessionMessage.setObjectId(item.getID());
            sessionMessage.setSapId(item.getIDSAP());
            sessionMessage.setMsgNum(item.getMsgNum());
            sessionMessage.setMsgType(item.getMsgType());
            sessionMessage.setMsg(item.getMsg());
            list.add(sessionMessage);
        }
        return sessionMessageRepo.save(list);
    }

    private List<InvoiceRevInterface> updateStatuses(List<InvoiceRevInterface> list, List<SessionMessage> messages, Session session) {
        for (InvoiceRevInterface line: list) {
            SessionMessage message = messages.stream()
                .filter(t -> t.getObjectCode().equals(objectCode))
                .filter(t -> t.getObjectId()!=null && t.getObjectId().trim().equals(line.getId().toString()))
                .filter(t -> t.getMsgType().equals("S"))
                .findFirst()
                .orElse(null);

            if (message != null)
                line.setStatus(BatchStatusEnum.C);
            else
                line.setStatus(BatchStatusEnum.E);

            line.setLastUpdateDate(LocalDateTime.now());
            line.setSession(session);
        }
        return invoiceRevInterfaceRepo.save(list);
    }

    private void debugRequest(List<ReversedInvoice.Item> list) {
        logger.debug("---------------------------------");
        for (ReversedInvoice.Item line: list) logger.debug(line.toString());
        logger.debug("---------------------------------");
    }
}