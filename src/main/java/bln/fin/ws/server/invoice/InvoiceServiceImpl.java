package bln.fin.ws.server.invoice;

import bln.fin.entity.*;
import bln.fin.entity.enums.DirectionEnum;
import bln.fin.entity.enums.SessionStatusEnum;
import bln.fin.repo.*;
import bln.fin.ws.SessionService;
import bln.fin.ws.server.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
@WebService(endpointInterface = "bln.fin.ws.server.invoice.InvoiceService", portName = "InvoiceServicePort", serviceName = "InvoiceService", targetNamespace = "http://bis.kegoc.kz/soap")
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceBusinessService invoiceBusinessService;
    private final PurchaseInvoiceRepo purchaseInvoiceRepo;
    private final SaleInvoiceRepo saleInvoiceRepo;
    private final SessionService sessionService;

    @Override
    public List<MessageDto> updateStatuses(List<InvoiceStatusDto> list) {
        SoapSession session = sessionService.createSession("INVOICE_STATUS", DirectionEnum.IMPORT);

        List<PurchaseInvoice> purchaseInvoices = list.stream()
            .filter(t -> t.getBpType().equals("K"))
            .map(invoiceBusinessService::toPurchaseInvoice)
            .filter(t -> t!=null)
            .collect(toList());

        List<SaleInvoice> saleInvoices = list.stream()
            .filter(t -> t.getBpType().equals("D"))
            .map(invoiceBusinessService::toSaleInvoice)
            .filter(t -> t!=null)
            .collect(toList());

        try {
            purchaseInvoiceRepo.save(purchaseInvoices);
            saleInvoiceRepo.save(saleInvoices);
            sessionService.successSession(session, (long) purchaseInvoices.size() + (long) saleInvoices.size());
        }
        catch (Exception e) {
            sessionService.errorSession(session, e);
        }

        return createResponse(session);
    }

    @Override
    public List<MessageDto> createInvoices(List<InvoiceDto> list) {
        SoapSession session = sessionService.createSession("INVOICE", DirectionEnum.IMPORT);

        List<PurchaseInvoice> invoices = list.stream()
            .filter(t -> t.getBpType().equals("K"))
            .map(invoiceBusinessService::createPurchaseInvoice)
            .filter(t -> t!=null)
            .collect(toList());

        try {
            purchaseInvoiceRepo.save(invoices);
            sessionService.successSession(session, (long) invoices.size());
        }
        catch (Exception e) {
            sessionService.errorSession(session, e);
        }

        return createResponse(session);
    }


    private List<MessageDto> createResponse(SoapSession session) {
        List<MessageDto> messages = new ArrayList<>();
        MessageDto msg = new MessageDto();
        msg.setSysCode("BIS");

        if (session.getStatus()==SessionStatusEnum.C) {
            msg.setMsgType("S");
            msg.setMsgNum("0");
            messages.add(msg);
        }

        if (session.getStatus()==SessionStatusEnum.E) {
            msg.setMsgType("E");
            msg.setMsgNum("1");
            msg.setMsg(session.getErrMsg());
            messages.add(msg);
        }

        return messages;
    }
}
