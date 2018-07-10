package bln.fin.soap.invoice;

import bln.fin.soap.Message;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.List;

@WebService(name = "InvoiceService", targetNamespace = "http://bis.kegoc.kz/soap")
public interface InvoiceService {
    @WebMethod(action = "updateStatuses")
    @WebResult(name = "result")
    Message updateStatuses(@WebParam(name = "status") List<InvoiceStatusDto> list);

    @WebMethod(action = "createInvoices")
    @WebResult(name = "result")
    Message createInvoices(@WebParam(name = "invoice") List<InvoiceDto> list);
}
