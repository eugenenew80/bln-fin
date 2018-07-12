package bln.fin.soap.bp;

import bln.fin.soap.MessageDto;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.List;

@WebService(name = "BusinessPartnerService", targetNamespace = "http://bis.kegoc.kz/soap")
public interface BusinessPartnerService {
    @WebMethod(action = "createBusinessPartners")
    @WebResult(name = "result")
    List<MessageDto> createBusinessPartners(@WebParam(name = "bp") List<BusinessPartnerDto> list);
}
