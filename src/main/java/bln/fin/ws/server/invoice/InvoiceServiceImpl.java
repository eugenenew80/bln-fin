package bln.fin.ws.server.invoice;

import bln.fin.entity.*;
import bln.fin.repo.*;
import bln.fin.ws.server.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
@WebService(endpointInterface = "bln.fin.ws.server.invoice.InvoiceService", portName = "InvoiceServicePort", serviceName = "InvoiceService", targetNamespace = "http://bis.kegoc.kz/server")
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceBusinessService invoiceBusinessService;
    private final PurchaseInvoiceRepo purchaseInvoiceRepo;
    private final SaleInvoiceRepo saleInvoiceRepo;

    @Override
    public List<MessageDto> updateStatuses(List<InvoiceStatusDto> list) {
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

        purchaseInvoiceRepo.save(purchaseInvoices);
        saleInvoiceRepo.save(saleInvoices);

        List<MessageDto> messages = new ArrayList<>();
        MessageDto msg = new MessageDto();
        msg.setSysCode("BIS");
        msg.setMsgType("S");
        msg.setMsgNum("0");
        messages.add(msg);

        return messages;
    }

    @Override
    public List<MessageDto> createInvoices(List<InvoiceDto> list) {
        List<PurchaseInvoice> invoices = list.stream()
            .filter(t -> t.getBpType().equals("K"))
            .map(invoiceBusinessService::createPurchaseInvoice)
            .filter(t -> t!=null)
            .collect(toList());

        purchaseInvoiceRepo.save(invoices);

        List<MessageDto> messages = new ArrayList<>();
        MessageDto msg = new MessageDto();
        msg.setSysCode("BIS");
        msg.setMsgType("S");
        msg.setMsgNum("0");
        messages.add(msg);

        return messages;
    }
}
