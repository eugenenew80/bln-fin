package bln.fin.soap.invoice;

import bln.fin.entity.*;
import bln.fin.repo.*;
import bln.fin.soap.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
@WebService(endpointInterface = "bln.fin.soap.invoice.InvoiceService", portName = "InvoiceServicePort", serviceName = "InvoiceService", targetNamespace = "http://bis.kegoc.kz/soap")
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceBusinessService invoiceBusinessService;
    private final PurchaseInvoiceRepo purchaseInvoiceRepo;
    private final SaleInvoiceRepo saleInvoiceRepo;

    @Override
    public List<MessageDto> updateStatuses(List<InvoiceStatusDto> list) {
        List<PurchaseInvoice> purchaseInvoices = list.stream()
            .filter(t -> t.getBpType().equals("K"))
            .map(invoiceBusinessService::toPurchaseInvoice)
            .filter(t -> t!=null)
            .collect(toList());

        List<SaleInvoice> saleInvoices = list.stream()
            .filter(t -> t.getBpType().equals("D"))
            .map(invoiceBusinessService::toSaleInvoice)
            .filter(t -> t!=null)
            .collect(toList());

        purchaseInvoiceRepo.save(purchaseInvoices);
        saleInvoiceRepo.save(saleInvoices);

        List<MessageDto> msgs = new ArrayList<>();
        for (PurchaseInvoice invoice : purchaseInvoices) {
            MessageDto msg = new MessageDto();
            msg.setStatus("S");
            msg.setId(invoice.getId());
            msg.setSapId(invoice.getErpDocNum());
            msg.setMsgNum("0");
            msg.setText("OK");
            msgs.add(msg);
        }

        return msgs;
    }

    @Override
    public List<MessageDto> createInvoices(List<InvoiceDto> list) {
        List<PurchaseInvoice> invoices = list.stream()
            .filter(t -> t.getBpType().equals("K"))
            .map(invoiceBusinessService::createPurchaseInvoice)
            .filter(t -> t!=null)
            .collect(toList());

        purchaseInvoiceRepo.save(invoices);

        List<MessageDto> msgs = new ArrayList<>();
        for (PurchaseInvoice invoice : invoices) {
            MessageDto msg = new MessageDto();
            msg.setStatus("S");
            msg.setId(invoice.getId());
            msg.setSapId(invoice.getErpDocNum());
            msg.setMsgNum("0");
            msg.setText("OK");
            msgs.add(msg);
        }

        return msgs;
    }
}