package bln.fin.soap.bp;

import bln.fin.soap.Message;
import bln.fin.soap.debt.DebtDto;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.List;

@WebService(name = "BusinessPartnerService", targetNamespace = "http://bis.kegoc.kz/soap")
public interface BusinessPartnerService {
    @WebMethod(action = "createBusinessPartners")
    @WebResult(name = "result")
    Message createBusinessPartners(@WebParam(name = "bp") List<BusinessPartnerDto> list);
}
