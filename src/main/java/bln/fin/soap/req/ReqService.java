package bln.fin.soap.req;

import bln.fin.soap.MessageDto;
import javax.jws.*;
import java.util.List;

@WebService(name = "ReqService", targetNamespace = "http://bis.kegoc.kz/soap")
public interface ReqService {
    @WebMethod(action = "createReqLines")
    @WebResult(name = "item")
    List<MessageDto> createReqLines(@WebParam(name = "reqLine") List<ReqLineDto> list);
}
