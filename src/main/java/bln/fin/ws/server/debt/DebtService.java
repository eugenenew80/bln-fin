package bln.fin.ws.server.debt;

import bln.fin.ws.server.MessageDto;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.List;

@WebService(name = "DebtService", targetNamespace = "http://bis.kegoc.kz/server")
public interface DebtService {
    @WebMethod(action = "createDebts")
    @WebResult(name = "msg")
    List<MessageDto> createDebts(@WebParam(name = "debt") List<DebtDto> list);
}
