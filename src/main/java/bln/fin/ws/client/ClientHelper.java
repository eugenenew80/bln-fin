package bln.fin.ws.client;

import bln.fin.entity.pi.ContractInterface;
import bln.fin.entity.pi.ContractLineInterface;
import bln.fin.entity.pi.InvoiceInterface;
import bln.fin.entity.pi.InvoiceLineInterface;
import sap.contract.sd.Contract;
import sap.invoice.EstimatedChargeInvoices;
import java.math.BigDecimal;
import static bln.fin.common.Util.toXMLGregorianCalendar;

public class ClientHelper {
    public static Contract.Item createContractItem(ContractInterface line) {
        Contract.Item item = new Contract.Item();

        item.setId(line.getId());
        item.setContractType(line.getContractType());
        item.setContractNum(line.getContractNum());
        item.setExtContractNum(line.getExtContractNum());
        item.setContractDate( toXMLGregorianCalendar(line.getContractDate()));
        item.setChannel(line.getChannel());
        item.setSalesDepartCode(line.getSaleDepartCode());
        item.setCompanyCode(line.getCompanyCode());
        item.setCompanyAccountNum(line.getCompanyAccountNum());
        item.setCustomerAccountNum(line.getBpAccountNum());
        item.setCustomerNum(line.getBpNum());
        item.setConsigneeNum(line.getConsBpNum());
        item.setCurrencyCode(line.getCurrencyCode());
        item.setPaymentCode(line.getPaymentCode());
        item.setStartDate(toXMLGregorianCalendar(line.getStartDate()));
        item.setEndDate(toXMLGregorianCalendar(line.getEndDate()));
        item.setAmount(line.getAmount());
        item.setAmount(line.getAmount());
        item.setCurrencyCode(line.getCurrencyCode());

        for (ContractLineInterface contractLine : line.getLines()) {
            Contract.Item.Row row = createContractLine(contractLine);
            item.getRow().add(row);
        }
        return item;
    }

    private static Contract.Item.Row createContractLine(ContractLineInterface contractLine) {
        Contract.Item.Row row = new Contract.Item.Row();
        row.setItemNum(contractLine.getItemNum());
        row.setUnit(contractLine.getUnit());
        row.setQuantity(BigDecimal.valueOf(contractLine.getQuantity()));
        row.setPrice(contractLine.getPrice());
        row.setAmount(BigDecimal.valueOf(contractLine.getAmount()));
        return row;
    }


    public static EstimatedChargeInvoices.Item createInvoiceItem(InvoiceInterface line) {
        EstimatedChargeInvoices.Item item = new EstimatedChargeInvoices.Item();
        item.setId(line.getId());
        item.setDocType(line.getDocType());
        item.setSrcDocNum(line.getSrcDocNum());
        item.setOrderNum(line.getOrderNum());
        item.setTurnoverDate(toXMLGregorianCalendar(line.getTurnoverDate()));
        item.setAccountingDate(toXMLGregorianCalendar(line.getAccountingDate()));
        item.setCustomerNum(line.getBpNum());
        item.setContractNum(line.getContractNum());
        item.setExtContractNum(line.getExtContractNum());
        item.setCompanyCode(line.getCompanyCode());
        item.setAmount(line.getAmount());
        item.setTax(line.getTax());
        item.setCurrencyCode(line.getCurrencyCode());
        for (InvoiceLineInterface invoiceLine : line.getLines()) {
            EstimatedChargeInvoices.Item.Row row = createInvoiceRow(invoiceLine);
            item.getRow().add(row);
        }
        return item;
    }

    private static EstimatedChargeInvoices.Item.Row createInvoiceRow(InvoiceLineInterface invoiceLine) {
        EstimatedChargeInvoices.Item.Row row = new EstimatedChargeInvoices.Item.Row();
        row.setPosNum(invoiceLine.getPosNum());
        row.setConsigneeNum(invoiceLine.getBpNum());
        row.setPosName(invoiceLine.getPosName());
        row.setItemNum(invoiceLine.getItemNum());
        row.setUnit(invoiceLine.getUnit());
        row.setQuantity(invoiceLine.getQuantity());
        row.setPrice(invoiceLine.getPrice());
        row.setAmount(invoiceLine.getAmount());
        row.setTaxRate(invoiceLine.getTaxRate());
        return row;
    }
}
