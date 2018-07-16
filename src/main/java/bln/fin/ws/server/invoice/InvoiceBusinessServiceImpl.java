package bln.fin.ws.server.invoice;

import bln.fin.entity.*;
import bln.fin.entity.enums.InvoiceLineTypeEnum;
import bln.fin.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static bln.fin.common.Util.toLocalDate;

@Service
@RequiredArgsConstructor
public class InvoiceBusinessServiceImpl implements InvoiceBusinessService{
    private final PurchaseInvoiceRepo purchaseInvoiceRepo;
    private final SaleInvoiceRepo saleInvoiceRepo;
    private final BusinessPartnerRepo businessPartnerRepo;
    private final ContractKegRepo contractKegRepo;
    private final UnitRepo unitRepo;
    private final ItemRepo itemRepo;

    @Override
    public PurchaseInvoice toPurchaseInvoice(InvoiceStatusDto statusDto) {
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

    @Override
    public SaleInvoice toSaleInvoice(InvoiceStatusDto statusDto) {
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
    public PurchaseInvoice createPurchaseInvoice(InvoiceDto invoiceDto) {
        LocalDate erpDocDate = toLocalDate(invoiceDto.getDocDate());
        LocalDate accountingDate = toLocalDate(invoiceDto.getAccountingDate());

        PurchaseInvoice inv = purchaseInvoiceRepo.findByErpDocNumAndErpDocDate(invoiceDto.getDocNum(), erpDocDate);
        if (inv == null)
            inv = new PurchaseInvoice();

        BusinessPartner vendor = businessPartnerRepo.findByErpBpNum(invoiceDto.getBpNum());
        BusinessPartner customer = businessPartnerRepo.findByErpCompanyCode(invoiceDto.getCompanyCode());
        ContractKeg contract = contractKegRepo.findByContractNum(invoiceDto.getExtContractNum());

        inv.setAccountingDate(accountingDate);
        inv.setInvoiceDate(erpDocDate);
        inv.setTurnoverDate(erpDocDate);
        inv.setErpDocDate(erpDocDate);
        inv.setDocNum(invoiceDto.getExtDocNum());
        inv.setErpDocNum(invoiceDto.getDocNum());
        inv.setAmount(invoiceDto.getAmount());
        inv.setTax(invoiceDto.getTax());
        inv.setCurrencyCode(invoiceDto.getCurrencyCode());
        inv.setExchangeRate(invoiceDto.getExchangeRate());
        inv.setCustomer(customer);
        inv.setConsignee(customer);
        inv.setVendor(vendor);
        inv.setConsignor(vendor);
        inv.setContract(contract);


        List<PurchaseInvoiceLine> lines = inv.getLines();
        if (inv.getLines() == null) {
            lines = new ArrayList<>();
            inv.setLines(lines);
        }

        for (int i = 0; i < inv.getLines().size(); i++) {
            PurchaseInvoiceLine line = lines.get(i);
            Optional<InvoiceLineDto> lineDto = invoiceDto.getLines().stream()
                .filter(t -> t.getPosNum().equals(line.getPosNum()))
                .findFirst();

            if (!lineDto.isPresent())
                inv.getLines().remove(line);
        }


        for (InvoiceLineDto lineDto : invoiceDto.getLines()) {
            PurchaseInvoiceLine invoiceLine = createPurchaseInvoiceLine(inv, lineDto);
            if (invoiceLine.getId() == null)
                inv.getLines().add(invoiceLine);
        }

        return inv;
    }

    private PurchaseInvoiceLine createPurchaseInvoiceLine(PurchaseInvoice invoice, InvoiceLineDto lineDto) {
        PurchaseInvoiceLine invoiceLine = invoice.getLines().stream()
            .filter(t -> t.getPosNum().equals(lineDto.getPosNum()))
            .findFirst()
            .orElse(new PurchaseInvoiceLine());

        Unit unit = unitRepo.findByCode(lineDto.getUnit())!=null ? unitRepo.findByCode(lineDto.getUnit()) : null;
        Item item = lineDto.getItemNum()!=null ? itemRepo.findByErpCode(lineDto.getItemNum()) : null;

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
        invoiceLine.setTaxRateValue(lineDto.getTaxRate());

        return invoiceLine;
    }

}
