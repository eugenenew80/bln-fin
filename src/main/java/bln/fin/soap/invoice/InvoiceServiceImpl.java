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
import java.util.Optional;

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

        List<PurchaseInvoice> purchInvoices = new ArrayList<>();
        List<SaleInvoice> saleInvoices = new ArrayList<>();
        for (InvoiceStatusDto statusDto : list) {
            LocalDate erpDocDate = statusDto.getDocDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate esfDocDate = statusDto.getEsfDocDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (statusDto.getBpType().equals("K")) {
                PurchaseInvoice invoice = purchaseInvoiceRepo.findByErpDocNumAndErpDocDate(statusDto.getDocNum(), erpDocDate);
                if (invoice!=null) {
                    invoice.setEsfDocNum(statusDto.getEsfDocNum());
                    invoice.setEsfDocDate(esfDocDate);
                    invoice.setEsfStatus(statusDto.getEsfStatus());
                    purchInvoices.add(invoice);
                }
            }

            if (statusDto.getBpType().equals("D")) {
                SaleInvoice invoice = saleInvoiceRepo.findByErpDocNumAndErpDocDate(statusDto.getDocNum(), erpDocDate);
                if (invoice!=null) {
                    invoice.setEsfDocNum(statusDto.getEsfDocNum());
                    invoice.setEsfDocDate(esfDocDate);
                    invoice.setEsfStatus(statusDto.getEsfStatus());
                    saleInvoices.add(invoice);
                }
            }
        }

        if (!purchInvoices.isEmpty())
            purchaseInvoiceRepo.save(purchInvoices);

        if (!saleInvoices.isEmpty())
            saleInvoiceRepo.save(saleInvoices);

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

            PurchaseInvoice invoice = purchaseInvoiceRepo.findByErpDocNumAndErpDocDate(invoiceDto.getDocNum(), erpDate);
            if (invoice == null)
                invoice = new PurchaseInvoice();

            invoice.setAccountingDate(accountingDate);
            invoice.setInvoiceDate(erpDate);
            invoice.setTurnoverDate(erpDate);
            invoice.setErpDocDate(erpDate);
            invoice.setDocNum(invoiceDto.getExtDocNum());
            invoice.setErpDocNum(invoiceDto.getDocNum());
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

            for (int i = 0; i < lines.size(); i++) {
                PurchaseInvoiceLine line = lines.get(i);
                Optional<InvoiceLineDto> lineDto = invoiceDto.getLines().stream()
                    .filter(t -> t.getPosNum().equals(line.getPosNum()))
                    .findFirst();

                if (!lineDto.isPresent())
                    lines.remove(line);
            }

            for (InvoiceLineDto lineDto : invoiceDto.getLines()) {
                Unit unit = unitRepo.findByCode(lineDto.getUnit());
                Item item = itemRepo.findByErpCode(lineDto.getItemNum());

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
