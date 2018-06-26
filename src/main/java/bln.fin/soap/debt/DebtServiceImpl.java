package bln.fin.soap.debt;

import bln.fin.soap.Message;
import org.springframework.stereotype.Service;
import javax.jws.WebService;
import java.util.List;

@Service
@WebService(endpointInterface = "bln.fin.soap.debt.DebtService", portName = "DebtServicePort", serviceName = "DebtService", targetNamespace = "http://bis.kegoc.kz/soap")
public class DebtServiceImpl implements DebtService {

    @Override
    public Message createDebts(List<Debt> list) {
        Message msg = new Message();
        msg.setStatus("success");
        return msg;
    }
}
