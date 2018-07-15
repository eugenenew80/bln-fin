package bln.fin.ws.client.invoice;

import bln.fin.entity.SaleInvoice;
import bln.fin.repo.SaleInvoiceRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {
    private final SaleInvoiceRepo saleInvoiceRepo;

    @Override
    public void sendByCustomer(Long vendorId, Long customerId) {
        List<SaleInvoice> invoices = saleInvoiceRepo.findAllByVendorIdAndCustomerId(vendorId, customerId);
    }

    @Override
    public void sendByContract(Long contractId) {
        List<SaleInvoice> invoices = saleInvoiceRepo.findAllByContractId(contractId);
    }

    @Override
    public void sendOne(Long invoiceId) {
        SaleInvoice invoice = saleInvoiceRepo.findOne(invoiceId);
    }

    @Override
    public void sendCancelledInvoices() {
    }
}
