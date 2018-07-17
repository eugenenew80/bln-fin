package bln.fin.ws.server.saleInvoice;

import bln.fin.ws.server.MessageDto;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.List;

@WebService(name = "SaleInvoiceService", targetNamespace = "http://bis.kegoc.kz/soap")
public interface SaleInvoiceService {
    @WebMethod(action = "createInvoices")
    @WebResult(name = "msg")
    List<MessageDto> createInvoices(@WebParam(name = "invoice") List<SaleInvoiceDto> list);
}
