package bln.fin.soap.purchase;

import bln.fin.soap.Message;
import org.springframework.stereotype.Service;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

@Service
@WebService(endpointInterface = "bln.fin.soap.purchase.RequisitionService", portName = "RequisitionServicePort", serviceName = "RequisitionService", targetNamespace = "http://bis.kegoc.kz/soap")
public class RequisitionServiceImpl implements RequisitionService {

    @Override
    public Message createRequisitions(@WebParam(name = "requisition") List<RequisitionLine> list) {
        Message msg = new Message();
        msg.setStatus("success");
        msg.setDetails(list.size() + " records created");
        return msg;
    }
}
