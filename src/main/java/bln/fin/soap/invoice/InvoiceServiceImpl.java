package bln.fin.soap.invoice;

import bln.fin.entity.*;
import bln.fin.entity.enums.InvoiceLineTypeEnum;
import bln.fin.repo.*;
import bln.fin.soap.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.jws.WebService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static bln.fin.common.Util.toLocalDate;

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

        List<PurchaseInvoice> purchaseInvoices = new ArrayList<>();
        List<SaleInvoice> saleInvoices = new ArrayList<>();
        for (InvoiceStatusDto statusDto : list) {
            if (statusDto.getBpType().equals("K")) {
                PurchaseInvoice invoice = toPurchaseInvoice(statusDto);
                if (invoice!=null)
                    purchaseInvoices.add(invoice);
            }

            if (statusDto.getBpType().equals("D")) {
                SaleInvoice invoice = toSaleInvoice(statusDto);
                if (invoice!=null)
                    saleInvoices.add(invoice);
            }
        }

        if (!purchaseInvoices.isEmpty())
            purchaseInvoiceRepo.save(purchaseInvoices);

        if (!saleInvoices.isEmpty())
            saleInvoiceRepo.save(saleInvoices);

        Message msg = new Message();
        msg.setStatus("success");
        msg.setDetails(purchaseInvoices.size() + saleInvoices.size() + " records updated");
        return msg;
    }

    private PurchaseInvoice toPurchaseInvoice(InvoiceStatusDto statusDto) {
        LocalDate erpDocDate = toLocalDate(statusDto.getDocDate());
        LocalDate esfDocDate = toLocalDate(statusDto.getEsfDocDate());

        PurchaseInvoice invoice = purchaseInvoiceRepo.findByErpDocNumAndErpDocDate(statusDto.getDocNum(), erpDocDate);
        if (invoice!=null) {
            invoice.setEsfDocNum(statusDto.getEsfDocNum());
            invoice.setEsfDocDate(esfDocDate);
            invoice.setEsfStatus(statusDto.getEsfStatus());
        }

        return invoice;
    }

    private SaleInvoice toSaleInvoice(InvoiceStatusDto statusDto) {
        LocalDate erpDocDate = toLocalDate(statusDto.getDocDate());
        LocalDate esfDocDate = toLocalDate(statusDto.getEsfDocDate());

        SaleInvoice invoice = saleInvoiceRepo.findByErpDocNumAndErpDocDate(statusDto.getDocNum(), erpDocDate);
        if (invoice!=null) {
            invoice.setEsfDocNum(statusDto.getEsfDocNum());
            invoice.setEsfDocDate(esfDocDate);
            invoice.setEsfStatus(statusDto.getEsfStatus());
        }

        return invoice;
    }


    @Override
    public Message createInvoices(List<InvoiceDto> list) {
        List<PurchaseInvoice> invoices = new ArrayList<>();
        for (InvoiceDto invoiceDto : list) {
            PurchaseInvoice invoice = createPurchaseInvoice(invoiceDto);
            if (invoice!=null)
                invoices.add(invoice);
        }
        purchaseInvoiceRepo.save(invoices);

        Message msg = new Message();
        msg.setStatus("success");
        msg.setDetails(invoices.size() + " records created");
        return msg;
    }


    private PurchaseInvoice createPurchaseInvoice(InvoiceDto invoiceDto) {
        LocalDate erpDocDate = toLocalDate(invoiceDto.getDocDate());
        LocalDate accountingDate = toLocalDate(invoiceDto.getAccountingDate());

        PurchaseInvoice invoice = purchaseInvoiceRepo.findByErpDocNumAndErpDocDate(invoiceDto.getDocNum(), erpDocDate);
        if (invoice == null)
            invoice = new PurchaseInvoice();

        BusinessPartner vendor = businessPartnerRepo.findByErpBpNum(invoiceDto.getBpNum());
        BusinessPartner customer = businessPartnerRepo.findByErpCompanyCode(invoiceDto.getCompanyCode());
        Contract contract = contractRepo.findByContractNum(invoiceDto.getExtContractNum());

        invoice.setAccountingDate(accountingDate);
        invoice.setInvoiceDate(erpDocDate);
        invoice.setTurnoverDate(erpDocDate);
        invoice.setErpDocDate(erpDocDate);
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
        if (invoice.getLines() == null) {
            lines = new ArrayList<>();
            invoice.setLines(lines);
        }

        for (int i = 0; i < invoice.getLines().size(); i++) {
            PurchaseInvoiceLine line = lines.get(i);
            Optional<InvoiceLineDto> lineDto = invoiceDto.getLines().stream()
                .filter(t -> t.getPosNum().equals(line.getPosNum()))
                .findFirst();

            if (!lineDto.isPresent())
                invoice.getLines().remove(line);
        }

        for (InvoiceLineDto lineDto : invoiceDto.getLines()) {
            PurchaseInvoiceLine invoiceLine = createPurchaseInvoiceLine(invoice, lineDto);
            if (invoiceLine.getId() == null)
                invoice.getLines().add(invoiceLine);
        }

        return invoice;
    }

    private PurchaseInvoiceLine createPurchaseInvoiceLine(PurchaseInvoice invoice, InvoiceLineDto lineDto) {
        PurchaseInvoiceLine invoiceLine = invoice.getLines().stream()
            .filter(t -> t.getPosNum().equals(lineDto.getPosNum()))
            .findFirst()
            .orElse(new PurchaseInvoiceLine());

        Unit unit = unitRepo.findByCode(lineDto.getUnit());
        Item item = itemRepo.findByErpCode(lineDto.getItemNum());

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
        invoiceLine.setItemNum(lineDto.getItemNum());

        return invoiceLine;
    }
}
