package bln.fin.soap.invoice;

import bln.fin.entity.*;
import bln.fin.entity.enums.InvoiceLineTypeEnum;
import bln.fin.repo.*;
import bln.fin.soap.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.jws.WebService;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@WebService(endpointInterface = "bln.fin.soap.invoice.InvoiceService", portName = "InvoiceServicePort", serviceName = "InvoiceService", targetNamespace = "http://bis.kegoc.kz/soap")
public class InvoiceServiceImpl implements InvoiceService {
    private final PurchaseInvoiceRepo purchaseInvoiceRepo;
    private final SaleInvoiceRepo saleInvoiceRepo;
    private final BusinessPartnerRepo businessPartnerRepo;
    private final ContractRepo contractRepo;
    private final UnitRepo unitRepo;
    private final ItemRepo itemRepo;

    @Override
    public Message updateStatuses(List<InvoiceStatusDto> list) {
        Message msg = new Message();
        msg.setStatus("success");
        msg.setDetails(list.size() + " records updated");
        return msg;
    }

    @Override
    public Message createInvoices(List<InvoiceDto> list) {
        List<PurchaseInvoice> invoices = new ArrayList<>();
        for (InvoiceDto invoiceDto : list) {
            LocalDate erpDate = invoiceDto.getDocDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate accountingDate = invoiceDto.getAccountingDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            BusinessPartner vendor = businessPartnerRepo.findByErpBpNum(invoiceDto.getBpNum());
            BusinessPartner customer = businessPartnerRepo.findByErpCompanyCode(invoiceDto.getCompanyCode());
            Contract contract = contractRepo.findByContractNum(invoiceDto.getExtContractNum());

            PurchaseInvoice invoice = purchaseInvoiceRepo.findByErpNumAndErpDate(invoiceDto.getDocNum(), erpDate);
            if (invoice == null)
                invoice = new PurchaseInvoice();

            invoice.setAccountingDate(accountingDate);
            invoice.setInvoiceDate(erpDate);
            invoice.setTurnoverDate(erpDate);
            invoice.setErpDate(erpDate);
            invoice.setNum(invoiceDto.getExtDocNum());
            invoice.setErpNum(invoiceDto.getDocNum());
            invoice.setAmount(invoiceDto.getAmount());
            invoice.setTax(invoiceDto.getTax());
            invoice.setCurrencyCode(invoiceDto.getCurrencyCode());
            invoice.setCustomer(customer);
            invoice.setConsignee(customer);
            invoice.setVendor(vendor);
            invoice.setConsignor(vendor);
            invoice.setContract(contract);

            List<PurchaseInvoiceLine> lines = invoice.getLines();
            if (lines == null) {
                lines = new ArrayList<>();
                invoice.setLines(lines);
            }

            for (InvoiceLineDto lineDto : invoiceDto.getLines()) {
                Unit unit = unitRepo.findByCode(lineDto.getUnit());
                Item item = itemRepo.findByCode(lineDto.getItemNum());

                PurchaseInvoiceLine invoiceLine = lines.stream()
                    .filter(t -> t.getPosNum().equals(lineDto.getPosNum()))
                    .findFirst()
                    .orElse(new PurchaseInvoiceLine());

                if (invoiceLine.getId() == null)
                    lines.add(invoiceLine);

                invoiceLine.setInvoice(invoice);
                invoiceLine.setPosNum(lineDto.getPosNum());
                invoiceLine.setAmount(lineDto.getAmount());
                invoiceLine.setQuantity(lineDto.getQuantity());
                invoiceLine.setUnitPrice(lineDto.getPrice());
                invoiceLine.setLineTypeCode(InvoiceLineTypeEnum.LINE);
                invoiceLine.setUnit(unit);
                invoiceLine.setUnitName(lineDto.getUnit());
                invoiceLine.setItem(item);
                invoiceLine.setDescription(lineDto.getPosName());
            }

            invoices.add(invoice);
        }
        purchaseInvoiceRepo.save(invoices);

        Message msg = new Message();
        msg.setStatus("success");
        msg.setDetails(invoices.size() + " records created");
        return msg;
    }
}
