package bln.fin.ws.client.invoice;

import bln.fin.entity.enums.BatchStatusEnum;
import bln.fin.entity.enums.DirectionEnum;
import bln.fin.entity.pi.InvoiceInterface;
import bln.fin.entity.pi.InvoiceLineInterface;
import bln.fin.entity.pi.Session;
import bln.fin.entity.pi.SessionMessage;
import bln.fin.repo.InvoiceInterfaceRepo;
import bln.fin.repo.SessionMessageRepo;
import bln.fin.ws.SessionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
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

import static bln.fin.common.Util.toXMLGregorianCalendar;
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

    @Override
    public void send() {
        List<InvoiceInterface> list = invoiceInterfaceRepo.findAllByStatus(BatchStatusEnum.W)
            .stream()
            .filter(t -> t.getBpType().equals("D"))
            .collect(toList());
        if (list.isEmpty()) return;

        logger.info("started");

        Session session = sessionService.createSession(objectCode, DirectionEnum.EXPORT);
        List<EstimatedChargeInvoices.Item> items = createInvoiceItems(list);

        EstimatedChargeInvoices invoiceReq = new ObjectFactory().createEstimatedChargeInvoices();
        invoiceReq.getItem().addAll(items);
        debugRequest(items);

        try {
            QName qName = new QName("urn:kegoc.kz:BIS:LO_0002_1_EstimatedChargeInvoice", "EstimatedChargeInvoices");
            JAXBElement<EstimatedChargeInvoices> root = new JAXBElement<>(qName, EstimatedChargeInvoices.class, invoiceReq);

            JAXBElement<Response> response = (JAXBElement<Response>) saleInvoiceServiceTemplate.marshalSendAndReceive(root);
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


    private List<EstimatedChargeInvoices.Item> createInvoiceItems(List<InvoiceInterface> list) {
        return list
            .stream()
            .map(t -> createInvoiceItem(t))
            .filter(t -> t != null)
            .collect(toList());
    }

    private EstimatedChargeInvoices.Item createInvoiceItem(InvoiceInterface line) {
        logger.debug("Creating item:: id = " + line.getId());
        EstimatedChargeInvoices.Item item = new EstimatedChargeInvoices.Item();
        item.setId(line.getId());
        item.setDocType(line.getDocType());
        item.setSrcDocNum(line.getSrcDocNum());
        item.setOrderNum(line.getOrderNum());
        item.setTurnoverDate(toXMLGregorianCalendar(line.getTurnoverDate()));
        item.setAccountingDate(toXMLGregorianCalendar(line.getAccountingDate()));
        item.setCustomerNum(line.getBpNum());
        item.setContractNum(line.getContractNum());
        item.setExtContractNum(line.getExtContractNum());
        item.setCompanyCode(line.getCompanyCode());
        item.setAmount(line.getAmount());
        item.setTax(line.getTax());
        item.setCurrencyCode(line.getCurrencyCode());
        for (InvoiceLineInterface invoiceLine : line.getLines()) {
            EstimatedChargeInvoices.Item.Row row = new EstimatedChargeInvoices.Item.Row();
            row.setPosNum(invoiceLine.getPosNum());
            row.setConsigneeNum(invoiceLine.getBpNum());
            row.setPosName(invoiceLine.getPosName());
            row.setItemNum(invoiceLine.getItemNum());
            row.setUnit(invoiceLine.getUnit());
            row.setQuantity(invoiceLine.getQuantity());
            row.setPrice(invoiceLine.getPrice());
            row.setAmount(invoiceLine.getAmount());
            row.setTaxRate(invoiceLine.getTaxRate());
            item.getRow().add(row);
        }
        logger.debug("Creating item successfully completed");
        return item;
    }

    @SuppressWarnings("Duplicates")
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

    private List<InvoiceInterface> updateStatuses(List<InvoiceInterface> list, List<SessionMessage> messages, Session session) {
        for (InvoiceInterface line: list) {
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
        return invoiceInterfaceRepo.save(list);
    }

    private void debugRequest(List<EstimatedChargeInvoices.Item> list) {
        logger.debug("---------------------------------");
        for (EstimatedChargeInvoices.Item line: list) logger.debug(line.toString());
        logger.debug("---------------------------------");
    }
}
