package bln.fin.soap.debt;

import bln.fin.soap.Message;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.List;

@WebService(name = "DebtService", targetNamespace = "http://bis.kegoc.kz/soap")
public interface DebtService {
    @WebMethod
    @WebResult(name = "result")
    Message createDebts(@WebParam(name = "debt") List<DebtDto> list);
}
