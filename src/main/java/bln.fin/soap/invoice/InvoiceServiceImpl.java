package bln.fin.soap.invoice;

import bln.fin.soap.Message;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.List;

@Service
@WebService(endpointInterface = "bln.fin.soap.invoice.InvoiceService", portName = "InvoiceServicePort", serviceName = "InvoiceService", targetNamespace = "http://bis.kegoc.kz/soap")
public class InvoiceServiceImpl implements InvoiceService {

    @Override
    public Message updateStatuses(List<InvoiceStatusDto> list) {
        Message msg = new Message();
        msg.setStatus("success");
        msg.setDetails(list.size() + " records updated");
        return msg;
    }
}
