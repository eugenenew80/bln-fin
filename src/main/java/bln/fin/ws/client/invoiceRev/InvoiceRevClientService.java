package bln.fin.ws.client.invoiceRev;

import org.springframework.stereotype.Service;

@Service
public interface InvoiceRevClientService {
    void sendAll();
    void sendOne(Long invoiceId);
}
