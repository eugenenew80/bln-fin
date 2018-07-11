package bln.fin.soap.invoice;

import bln.fin.entity.*;
import bln.fin.repo.*;
import bln.fin.soap.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.jws.WebService;
import java.util.List;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
@WebService(endpointInterface = "bln.fin.soap.invoice.InvoiceService", portName = "InvoiceServicePort", serviceName = "InvoiceService", targetNamespace = "http://bis.kegoc.kz/soap")
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceBusinessService invoiceBusinessService;
    private final PurchaseInvoiceRepo purchaseInvoiceRepo;
    private final SaleInvoiceRepo saleInvoiceRepo;

    @Override
    public Message updateStatuses(List<InvoiceStatusDto> list) {
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

        Message msg = new Message();
        msg.setStatus("success");
        msg.setDetails(purchaseInvoices.size() + saleInvoices.size() + " records updated");
        return msg;
    }

    @Override
    public Message createInvoices(List<InvoiceDto> list) {
        List<PurchaseInvoice> invoices = list.stream()
            .filter(t -> t.getBpType().equals("K"))
            .map(invoiceBusinessService::createPurchaseInvoice)
            .filter(t -> t!=null)
            .collect(toList());

        purchaseInvoiceRepo.save(invoices);

        Message msg = new Message();
        msg.setStatus("success");
        msg.setDetails(invoices.size() + " records created");
        return msg;
    }
}
