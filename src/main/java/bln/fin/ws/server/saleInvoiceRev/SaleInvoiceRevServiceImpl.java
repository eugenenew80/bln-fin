package bln.fin.ws.server.saleInvoiceRev;

import bln.fin.ws.server.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.List;

@RequiredArgsConstructor
@Service
@WebService(endpointInterface = "bln.fin.ws.server.saleInvoiceRev.SaleInvoiceRevService", portName = "SaleInvoiceRevServicePort", serviceName = "SaleInvoiceRevService", targetNamespace = "http://bis.kegoc.kz/soap")
public class SaleInvoiceRevServiceImpl implements SaleInvoiceRevService {

    @Override
    public List<MessageDto> revInvoices(List<SaleInvoiceRevDto> list) {
        return null;
    }
}
