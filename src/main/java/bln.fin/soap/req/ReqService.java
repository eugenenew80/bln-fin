package bln.fin.soap.req;

import bln.fin.soap.Message;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.List;

@WebService(name = "ReqService", targetNamespace = "http://bis.kegoc.kz/soap")
public interface ReqService {
    @WebMethod
    @WebResult(name = "result")
    Message createReqs(@WebParam(name = "reqLine") List<ReqLine> list);
}
