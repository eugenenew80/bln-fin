package bln.fin.ws.client.invoice;

import bln.fin.entity.SaleInvoice;
import bln.fin.repo.SaleInvoiceRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceClientServiceImpl implements InvoiceClientService {
    private final SaleInvoiceRepo saleInvoiceRepo;
    private final WebServiceTemplate saleInvoiceServiceTemplate;

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
