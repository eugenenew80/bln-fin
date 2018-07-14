package bln.fin.ws.client.invoice;

import bln.fin.repo.SaleInvoiceRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {
    private final SaleInvoiceRepo saleInvoiceRepo;

    @Override
    public void sendByCustomer(Long vendorId, Long customerId) {

    }

    @Override
    public void sendByContract(Long contractId) {

    }

    @Override
    public void sendOne(Long invoiceId) {
    }

    @Override
    public void sendAll() {
    }
}
