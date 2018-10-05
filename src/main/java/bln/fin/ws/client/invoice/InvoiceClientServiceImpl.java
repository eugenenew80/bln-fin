package bln.fin.ws.client.invoice;

import bln.fin.entity.enums.BatchStatusEnum;
import bln.fin.entity.enums.DirectionEnum;
import bln.fin.entity.pi.InvoiceInterface;
import bln.fin.entity.pi.Session;
import bln.fin.entity.pi.SessionMessage;
import bln.fin.repo.InvoiceInterfaceRepo;
import bln.fin.repo.SessionMessageRepo;
import bln.fin.ws.SessionService;
import bln.fin.ws.client.ClientHelper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;
import sap.invoice.EstimatedChargeInvoices;
import sap.invoice.ObjectFactory;
import sap.invoice.Response;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.toList;

@SuppressWarnings("Duplicates")
@Service
@RequiredArgsConstructor
public class InvoiceClientServiceImpl implements InvoiceClientService {
    private static final Logger logger = LoggerFactory.getLogger(InvoiceClientService.class);
    private static final String objectCode = "SALE_INVOICE";
    private final InvoiceInterfaceRepo invoiceInterfaceRepo;
    private final WebServiceTemplate saleInvoiceServiceTemplate;
    private final SessionService sessionService;
    private final SessionMessageRepo sessionMessageRepo;

    @Override
    public void send() {
        List<InvoiceInterface> list = invoiceInterfaceRepo.findAllByStatus(BatchStatusEnum.W)
            .stream()
            .filter(t -> t.getBpType().equals("D"))
            .collect(toList());

        send(list);
    }

    private void send(List<InvoiceInterface> list) {
        if (list.isEmpty()) return;

        logger.info("started");
        Session session = sessionService.createSession(objectCode, DirectionEnum.EXPORT);
        list = updateStatuses(list, session);

        List<EstimatedChargeInvoices.Item> items = list.stream()
            .map(ClientHelper::createInvoiceItem)
            .filter(t -> t != null)
            .collect(toList());

        EstimatedChargeInvoices invoiceReq = new ObjectFactory().createEstimatedChargeInvoices();
        invoiceReq.getItem().addAll(items);
        debugRequest(items);

        try {
            QName qName = new QName("urn:kegoc.kz:BIS:LO_0002_1_EstimatedChargeInvoice", "EstimatedChargeInvoices");
            JAXBElement<EstimatedChargeInvoices> request = new JAXBElement<>(qName, EstimatedChargeInvoices.class, invoiceReq);
            JAXBElement<Response> response = (JAXBElement<Response>) saleInvoiceServiceTemplate.marshalSendAndReceive(request);
            List<SessionMessage> messages = saveMessages(response.getValue(), session);
            sessionService.successSession(session, (long) items.size());
            updateStatuses(list, messages);
        }

        catch (SoapFaultClientException e) {
            logger.error("Fault Code: " + e.getFaultCode());
            logger.error("Fault Reason: " + e.getFaultStringOrReason());
            sessionService.errorSession(session, e);
            updateStatuses(list, session);
        }

        catch (Exception e) {
            logger.debug("Error during request: " + e.getMessage());
            sessionService.errorSession(session, e);
            updateStatuses(list, session);
        }
        logger.info("completed");
    }

    private void debugRequest(List<EstimatedChargeInvoices.Item> list) {
        logger.debug("---------------------------------");
        for (EstimatedChargeInvoices.Item line: list) logger.debug(line.toString());
        logger.debug("---------------------------------");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    List<SessionMessage> saveMessages(Response response, Session session) {
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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    List<InvoiceInterface> updateStatuses(List<InvoiceInterface> list, List<SessionMessage> messages) {
        for (InvoiceInterface line: list) {
            BatchStatusEnum status = messages.stream()
                .filter(t -> t.getObjectCode().equals(objectCode))
                .filter(t -> t.getObjectId() != null && t.getObjectId().trim().equals(line.getId().toString()))
                .filter(t -> t.getMsgType().equals("S"))
                .map(t -> BatchStatusEnum.C)
                .findFirst()
                .orElse(BatchStatusEnum.E);

            line.setStatus(status);
            line.setLastUpdateDate(LocalDateTime.now());
        }
        return invoiceInterfaceRepo.save(list);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    List<InvoiceInterface> updateStatuses(List<InvoiceInterface> list, Session session) {
        for (InvoiceInterface line: list) {
            line.setStatus(session.getStatus());
            line.setSession(session);
            line.setLastUpdateDate(LocalDateTime.now());
        }
        return invoiceInterfaceRepo.save(list);
    }
}
