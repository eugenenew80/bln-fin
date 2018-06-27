package bln.fin.soap.purchase;

import bln.fin.soap.Message;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.List;

@WebService(name = "RequisitionService", targetNamespace = "http://bis.kegoc.kz/soap")
public interface RequisitionService {
    @WebMethod
    @WebResult(name = "result")
    Message createRequisitions(@WebParam(name = "requisition") List<RequisitionLine> list);
}
