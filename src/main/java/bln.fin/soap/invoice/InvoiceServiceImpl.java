package bln.fin.soap.invoice;

import bln.fin.repo.PurchaseInvoiceRepo;
import bln.fin.repo.SaleInvoiceRepo;
import bln.fin.soap.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.List;

@RequiredArgsConstructor
@Service
@WebService(endpointInterface = "bln.fin.soap.invoice.InvoiceService", portName = "InvoiceServicePort", serviceName = "InvoiceService", targetNamespace = "http://bis.kegoc.kz/soap")
public class InvoiceServiceImpl implements InvoiceService {
    private final PurchaseInvoiceRepo purchaseInvoiceRepo;
    private final SaleInvoiceRepo saleInvoiceRepo;

    @Override
    public Message updateStatuses(List<InvoiceStatusDto> list) {
        Message msg = new Message();
        msg.setStatus("success");
        msg.setDetails(list.size() + " records updated");
        return msg;
    }

    @Override
    public Message createInvoices(List<InvoiceDto> list) {
        Message msg = new Message();
        msg.setStatus("success");
        msg.setDetails(list.size() + " records created");
        return msg;
    }
}
