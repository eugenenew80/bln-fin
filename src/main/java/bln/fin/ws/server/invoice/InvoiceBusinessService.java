package bln.fin.ws.server.invoice;

import bln.fin.entity.PurchaseInvoice;
import bln.fin.entity.SaleInvoice;
import org.springframework.stereotype.Service;

@Service
public interface InvoiceBusinessService {
    PurchaseInvoice toPurchaseInvoice(InvoiceStatusDto statusDto);

    SaleInvoice toSaleInvoice(InvoiceStatusDto statusDto);

    PurchaseInvoice createPurchaseInvoice(InvoiceDto invoiceDto);
}
