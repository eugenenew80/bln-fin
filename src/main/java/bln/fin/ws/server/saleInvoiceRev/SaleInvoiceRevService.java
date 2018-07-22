package bln.fin.ws.server.saleInvoiceRev;

import bln.fin.ws.server.MessageDto;
import bln.fin.ws.server.saleInvoice.SaleInvoiceDto;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.List;

@WebService(name = "SaleInvoiceRevService", targetNamespace = "http://bis.kegoc.kz/soap")
public interface SaleInvoiceRevService {
    @WebMethod(action = "revInvoices")
    @WebResult(name = "msg")
    List<MessageDto> revInvoices(@WebParam(name = "invoiceRev") List<SaleInvoiceRevDto> list);
}
