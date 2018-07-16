package bln.fin.ws.server.bp;

import bln.fin.ws.server.MessageDto;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.List;

@WebService(name = "BusinessPartnerService", targetNamespace = "http://bis.kegoc.kz/soap")
public interface BusinessPartnerService {
    @WebMethod(action = "createBusinessPartners")
    @WebResult(name = "msg")
    List<MessageDto> createBusinessPartners(@WebParam(name = "bp") List<BusinessPartnerDto> list);
}
