package bln.fin.soap.invoice;

import bln.fin.entity.PurchaseInvoice;
import bln.fin.repo.PurchaseInvoiceRepo;
import bln.fin.repo.SaleInvoiceRepo;
import bln.fin.soap.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
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
        List<PurchaseInvoice> invoices = new ArrayList<>();
        for (InvoiceDto invoiceDto : list) {
            LocalDate erpDate = invoiceDto.getDocDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate accountingDate = invoiceDto.getAccountingDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            PurchaseInvoice invoice = purchaseInvoiceRepo.findByErpNumAndErpDate(invoiceDto.getDocNum(), erpDate);
            if (invoice == null)
                invoice = new PurchaseInvoice();

            invoice.setAccountingDate(accountingDate);
            invoice.setInvoiceDate(erpDate);
            invoice.setTurnoverDate(erpDate);
            invoice.setNum(invoiceDto.getExtDocNum());
            invoice.setErpNum(invoiceDto.getDocNum());
            invoice.setAmount(invoiceDto.getAmount());
            invoice.setTax(invoiceDto.getTax());
            invoice.setCurrencyCode(invoiceDto.getCurrencyCode());
        }

        Message msg = new Message();
        msg.setStatus("success");
        msg.setDetails(list.size() + " records created");
        return msg;
    }
}
