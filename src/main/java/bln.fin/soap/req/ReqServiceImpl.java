package bln.fin.soap.req;

import bln.fin.soap.Message;
import org.springframework.stereotype.Service;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

@Service
@WebService(endpointInterface = "bln.fin.soap.req.ReqService", portName = "ReqServicePort", serviceName = "ReqService", targetNamespace = "http://bis.kegoc.kz/soap")
public class ReqServiceImpl implements ReqService {

    @Override
    public Message createReqs(@WebParam(name = "req") List<ReqLine> list) {
        Message msg = new Message();
        msg.setStatus("success");
        msg.setDetails(list.size() + " records created");
        return msg;
    }
}
