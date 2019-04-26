package bln.fin.ws.client.invoice;

import bln.fin.entity.enums.*;
import bln.fin.entity.pi.*;
import bln.fin.repo.*;
import sap.invoice.*;
import bln.fin.ws.SessionService;
import lombok.RequiredArgsConstructor;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;
import javax.xml.bind.JAXBElement;
import java.time.LocalDateTime;
import java.util.List;
import static bln.fin.common.Util.getSuccessMessage;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class InvoiceClientServiceImpl implements InvoiceClientService {
    private static final Logger logger = LoggerFactory.getLogger(InvoiceClientService.class);
    private static final String objectCode = "SALE_INVOICE";
    private final InvoiceInterfaceRepo invoiceInterfaceRepo;
    private final WebServiceTemplate saleInvoiceServiceTemplate;
    private final SessionService sessionService;
    private final SessionMessageRepo sessionMessageRepo;
    private final DozerBeanMapper mapper;

    @Override
    public void send() {
        List<InvoiceInterface> list = invoiceInterfaceRepo.findAllByStatusAndBpType(BatchStatusEnum.W, "D");
        if (list.isEmpty()) return;

        for (InvoiceInterface i : list)
            send(asList(i));
    }

    private void send(List<InvoiceInterface> list) {
        if (list.isEmpty()) return;
        logger.info("started");

        Session session = sessionService.createSession(objectCode, DirectionEnum.EXPORT);
        try {
            list = updateStatuses(list, session);
            JAXBElement<EstimatedChargeInvoices> request = createRequest(list);
            JAXBElement<Response> response = (JAXBElement<Response>) saleInvoiceServiceTemplate.marshalSendAndReceive(request);

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

        invoiceInterfaceRepo.updateStatuses();
        logger.info("completed");
    }

    private JAXBElement<EstimatedChargeInvoices> createRequest(List<InvoiceInterface> list) {
        List<EstimatedChargeInvoices.Item> items = list.stream()
            .map(this::mapItem)
            .filter(t -> t != null)
            .collect(toList());
        debugRequest(items);

        EstimatedChargeInvoices invoiceDto = new EstimatedChargeInvoices();
        invoiceDto.getItem().addAll(items);
        return new ObjectFactory().createEstimatedChargeInvoices(invoiceDto);
    }

    private EstimatedChargeInvoices.Item mapItem(InvoiceInterface invoice) {
        EstimatedChargeInvoices.Item item = mapper.map(invoice, EstimatedChargeInvoices.Item.class);
        if (invoice.getLines() == null)
            return item;

        for (InvoiceLineInterface line : invoice.getLines()) {
            EstimatedChargeInvoices.Item.Row row = mapper.map(line, EstimatedChargeInvoices.Item.Row.class);
            //row.setConsigneeNum(invoice.getBpNum());
            item.getRow().add(row);
        }
        return item;
    }

    private void debugRequest(List<EstimatedChargeInvoices.Item> list) {
        logger.debug("---------------------------------");
        for (EstimatedChargeInvoices.Item line: list) logger.debug(line.toString());
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
    protected List<InvoiceInterface> updateStatuses(List<InvoiceInterface> list, List<SessionMessage> messages, Session session) {
        for (InvoiceInterface line: list) {
            SessionMessage msg = getSuccessMessage(messages, line.getId().toString());
            line.setStatus(msg != null ? BatchStatusEnum.C : BatchStatusEnum.E);

            if (msg != null && msg.getSapId() != null) {
                int index = msg.getSapId().indexOf("#");
                if (index >= 0) {
                    String orderNum = msg.getSapId().substring(0, index);
                    String docNum = msg.getSapId().substring(index + 1);
                    line.setDocNum(docNum);
                    line.setOrderNum(orderNum);
                }
                else line.setDocNum(msg.getSapId());
            }

            line.setSession(session);
            line.setLastUpdateDate(LocalDateTime.now());
        }
        return invoiceInterfaceRepo.save(list);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected List<InvoiceInterface> updateStatuses(List<InvoiceInterface> list, Session session) {
        for (InvoiceInterface line: list) {
            line.setStatus(session.getStatus());
            line.setSession(session);
            line.setLastUpdateDate(LocalDateTime.now());
        }
        return invoiceInterfaceRepo.save(list);
    }
}