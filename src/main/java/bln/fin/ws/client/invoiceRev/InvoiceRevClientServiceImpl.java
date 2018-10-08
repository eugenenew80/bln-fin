package bln.fin.ws.client.invoiceRev;

import bln.fin.entity.enums.*;
import bln.fin.entity.pi.*;
import bln.fin.repo.*;
import sap.invoiceRev.*;
import bln.fin.ws.SessionService;
import lombok.RequiredArgsConstructor;
import org.dozer.DozerBeanMapper;
import org.slf4j.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;
import javax.xml.bind.JAXBElement;
import java.time.LocalDateTime;
import java.util.List;
import static bln.fin.common.Util.getSuccessMessage;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class InvoiceRevClientServiceImpl implements InvoiceRevClientService {
    private static final Logger logger = LoggerFactory.getLogger(InvoiceRevClientService.class);
    private static final String objectCode = "SALE_INVOICE_REV";
    private final InvoiceRevInterfaceRepo invoiceRevInterfaceRepo;
    private final WebServiceTemplate saleInvoiceRevServiceTemplate;
    private final SessionService sessionService;
    private final SessionMessageRepo sessionMessageRepo;
    private final DozerBeanMapper mapper;

    @Override
    public void send() {
        List<InvoiceRevInterface> list = invoiceRevInterfaceRepo.findAllByStatus(BatchStatusEnum.W);
        if (list.isEmpty()) return;
        send(list);
    }

    public void send(List<InvoiceRevInterface> list) {
        if (list.isEmpty()) return;
        logger.info("started");

        Session session = sessionService.createSession(objectCode, DirectionEnum.EXPORT);
        try {
            list = updateStatuses(list, session);
            JAXBElement<ReversedInvoice> request = createRequest(list);
            JAXBElement<Response> response = (JAXBElement<Response>) saleInvoiceRevServiceTemplate.marshalSendAndReceive(request);

            List<SessionMessage> messages = saveResponse(response, session);
            sessionService.successSession(session, (long) list.size());
            updateStatuses(list, messages, session);
        }
        catch (SoapFaultClientException e) {
            logger.error("Error during request, Fault Code: " + e.getFaultCode() + ", " + "Fault Reason: " + e.getFaultStringOrReason());
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

    private JAXBElement<ReversedInvoice> createRequest(List<InvoiceRevInterface> list) {
        List<ReversedInvoice.Item> items = list.stream()
            .map(rev -> mapper.map(rev, ReversedInvoice.Item.class))
            .filter(t -> t != null)
            .collect(toList());
        debugRequest(items);

        ReversedInvoice invoiceRevDto = new ReversedInvoice();
        invoiceRevDto.getItem().addAll(items);
        return new ObjectFactory().createReversedInvoice(invoiceRevDto);
    }

    private void debugRequest(List<ReversedInvoice.Item> list) {
        logger.debug("---------------------------------");
        for (ReversedInvoice.Item line: list) logger.debug(line.toString());
        logger.debug("---------------------------------");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected List<SessionMessage> saveResponse(JAXBElement<Response> response, Session session) {
        List<SessionMessage> list = response.getValue()
            .getItem()
            .stream()
            .filter(t -> t.getMsgType() != null || t.getMsgNum() != null || t.getMsg() != null)
            .map(t -> {
                SessionMessage msg = new SessionMessage(objectCode, session);
                mapper.map(t, msg);
                return msg;
            })
            .collect(toList());

        return sessionMessageRepo.save(list);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected List<InvoiceRevInterface> updateStatuses(List<InvoiceRevInterface> list, List<SessionMessage> messages, Session session) {
        for (InvoiceRevInterface line: list) {
            SessionMessage msg = getSuccessMessage(messages, line.getId().toString());
            line.setStatus(msg != null ? BatchStatusEnum.C : BatchStatusEnum.E);
            line.setSession(session);
            line.setLastUpdateDate(LocalDateTime.now());
        }
        return invoiceRevInterfaceRepo.save(list);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected List<InvoiceRevInterface> updateStatuses(List<InvoiceRevInterface> list, Session session) {
        for (InvoiceRevInterface line: list) {
            line.setStatus(session.getStatus());
            line.setSession(session);
            line.setLastUpdateDate(LocalDateTime.now());
        }
        return invoiceRevInterfaceRepo.save(list);
    }
}