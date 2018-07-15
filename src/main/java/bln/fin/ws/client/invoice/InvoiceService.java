package bln.fin.ws.client.invoice;

import org.springframework.stereotype.Service;

@Service
public interface InvoiceService {
    void sendByCustomer(Long vendorId, Long customerId);
    void sendByContract(Long contractId);
    void sendOne(Long invoiceId);
    void sendCancelledInvoices();
}
