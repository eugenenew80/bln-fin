package bln.fin.ws.server.invoice;

import bln.fin.entity.*;
import bln.fin.entity.enums.DirectionEnum;
import bln.fin.repo.*;
import bln.fin.ws.SessionService;
import bln.fin.ws.server.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

import static bln.fin.common.Util.getCause;
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

        List<MessageDto> messages = new ArrayList<>();
        try {
            for (PurchaseInvoice invoice : purchaseInvoices) {
                MessageDto msg = save(invoice);
                messages.add(msg);
            }

            for (SaleInvoice invoice : saleInvoices) {
                MessageDto msg = save(invoice);
                messages.add(msg);
            }

            sessionService.successSession(session, (long) purchaseInvoices.size() + (long) saleInvoices.size());
        }
        catch (Exception e) {
            sessionService.errorSession(session, e);
        }

        return messages;
    }

    @Override
    public List<MessageDto> createInvoices(List<InvoiceDto> list) {
        SoapSession session = sessionService.createSession("INVOICE", DirectionEnum.IMPORT);

        List<PurchaseInvoice> invoices = list.stream()
            .filter(t -> t.getBpType().equals("K"))
            .map(invoiceBusinessService::createPurchaseInvoice)
            .filter(t -> t!=null)
            .collect(toList());

        List<MessageDto> messages = new ArrayList<>();
        try {
            for (PurchaseInvoice invoice : invoices) {
                MessageDto msg = save(invoice);
                messages.add(msg);
            }
            sessionService.successSession(session, (long) invoices.size());
        }
        catch (Exception e) {
            sessionService.errorSession(session, e);
        }

        return messages;
    }


    private MessageDto save(PurchaseInvoice invoice) {
        MessageDto msg = new MessageDto();
        try {
            purchaseInvoiceRepo.save(invoice);
            msg.setMsgType("S");
            msg.setMsgNum("0");
            msg.setMsg("OK");
            msg.setId(invoice.getId().toString());
            msg.setSapId(invoice.getErpDocNum());
        }
        catch (Exception e) {
            msg.setMsgType("E");
            msg.setMsgNum("1");
            msg.setSapId(invoice.getErpDocNum());
            Throwable cause = getCause(e);
            if (cause.getMessage()!=null)
                msg.setMsg(cause.getMessage());
            else
                msg.setMsg(cause.getClass().getCanonicalName());
        }

        return msg;
    }

    private MessageDto save(SaleInvoice invoice) {
        MessageDto msg = new MessageDto();
        try {
            saleInvoiceRepo.save(invoice);
            msg.setMsgType("S");
            msg.setMsgNum("0");
            msg.setMsg("OK");
            msg.setId(invoice.getId().toString());
            msg.setSapId(invoice.getErpDocNum());
        }
        catch (Exception e) {
            msg.setMsgType("E");
            msg.setMsgNum("1");
            msg.setSapId(invoice.getErpDocNum());
            Throwable cause = getCause(e);
            if (cause.getMessage()!=null)
                msg.setMsg(cause.getMessage());
            else
                msg.setMsg(cause.getClass().getCanonicalName());
        }

        return msg;
    }
}
