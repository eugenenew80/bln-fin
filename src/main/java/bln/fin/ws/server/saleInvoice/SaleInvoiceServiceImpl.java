package bln.fin.ws.server.saleInvoice;

import bln.fin.ws.server.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.jws.WebService;
import java.util.List;

@RequiredArgsConstructor
@Service
@WebService(endpointInterface = "bln.fin.ws.server.saleInvoice.SaleInvoiceService", portName = "SaleInvoiceServicePort", serviceName = "SaleInvoiceService", targetNamespace = "http://bis.kegoc.kz/soap")
public class SaleInvoiceServiceImpl implements SaleInvoiceService {

    @Override
    public List<MessageDto> createInvoices(List<SaleInvoiceDto> list) {
        return null;
    }
}
