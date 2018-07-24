package bln.fin.ws.client.invoice;

import bln.fin.entity.SaleInvoice;
import bln.fin.entity.SaleInvoiceLine;
import bln.fin.entity.enums.DocTypeEnum;
import bln.fin.entity.enums.InvoiceTypeEnum;
import bln.fin.repo.SaleInvoiceRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import static bln.fin.common.Util.toXMLGregorianCalendar;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class InvoiceClientServiceImpl implements InvoiceClientService {
    private final SaleInvoiceRepo saleInvoiceRepo;
    private final WebServiceTemplate saleInvoiceServiceTemplate;

    @Override
    public void sendByCustomer(Long vendorId, Long customerId) {
        List<SaleInvoice> invoices = saleInvoiceRepo.findAllByVendorIdAndCustomerId(vendorId, customerId);
        request(invoices);
    }

    @Override
    public void sendByContract(Long contractId) {
        List<SaleInvoice> invoices = saleInvoiceRepo.findAllByContractId(contractId);
        request(invoices);
    }

    @Override
    public void sendOne(Long invoiceId) {
        List<SaleInvoice> invoices = Arrays.asList(saleInvoiceRepo.findOne(invoiceId));
        request(invoices);
    }

    private void request(List<SaleInvoice> invoices) {
        List<sap.saleInvoice.SaleInvoice> items = createItems(invoices);
        if (items.isEmpty())
            return;

        sap.saleInvoice.CreateInvoices saleInvoiceReq = new sap.saleInvoice.ObjectFactory().createCreateInvoices();
        saleInvoiceReq.getInvoice().addAll(items);

        try {
            Object response = saleInvoiceServiceTemplate.marshalSendAndReceive(saleInvoiceReq);
            updateHeaders(invoices);
        }
        catch (SoapFaultClientException e) {
            System.out.println("Fault Code: " + e.getFaultCode());
            System.out.println("Fault Reason: " + e.getFaultStringOrReason());
            System.out.println("Message: " + e.getLocalizedMessage());
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<sap.saleInvoice.SaleInvoice> createItems(List<SaleInvoice> invoices) {
        return invoices
            .stream()
            .filter(t -> t.getTransferredToErpDate()==null)
            .map(t -> createItem(t))
            .filter(t -> t != null)
            .collect(toList());
    }

    private sap.saleInvoice.SaleInvoice createItem(SaleInvoice saleInvoice) {
        sap.saleInvoice.SaleInvoice saleInvoiceDto = new sap.saleInvoice.SaleInvoice();

        if (saleInvoice.getDocTypeCode() == DocTypeEnum.ESTIMATED)
            saleInvoiceDto.setDocType("ZFO");

        if (saleInvoice.getDocTypeCode() == DocTypeEnum.FACT) {
            if (saleInvoice.getInvoiceTypeCode() == InvoiceTypeEnum.ORDINARY)
                saleInvoiceDto.setDocType("ZF2");

            if (saleInvoice.getInvoiceTypeCode() == InvoiceTypeEnum.CREDIT)
                saleInvoiceDto.setDocType("ZG2");

            if (saleInvoice.getInvoiceTypeCode() == InvoiceTypeEnum.DEBIT)
                saleInvoiceDto.setDocType("ZL2");

            if (saleInvoice.getInvoiceTypeCode() == InvoiceTypeEnum.CORRECTED)
                saleInvoiceDto.setDocType("ZL2");
        }

        if (saleInvoice.getSrcSaleInvoice()!=null)
            saleInvoiceDto.setSapDocNum(saleInvoice.getSrcSaleInvoice().getErpDocNum());

        saleInvoiceDto.setId(saleInvoice.getId());
        saleInvoiceDto.setTurnoverDate(toXMLGregorianCalendar(saleInvoice.getTurnoverDate()));
        saleInvoiceDto.setAccountingDate(toXMLGregorianCalendar(saleInvoice.getAccountingDate()));

        if (saleInvoice.getCustomer()!=null)
            saleInvoiceDto.setCustomerNum(saleInvoice.getCustomer().getErpBpNum());

        if (saleInvoice.getConsignor()!=null)
            saleInvoiceDto.setConsigneeNum(saleInvoice.getConsignor().getErpBpNum());

        if (saleInvoice.getContract()!=null)
            saleInvoiceDto.setContractNum(saleInvoice.getContract().getContractNum());

        if (saleInvoice.getContract()!=null)
            saleInvoiceDto.setExtContractNum(saleInvoice.getContract().getContractNum());

        saleInvoiceDto.setAmount(saleInvoice.getAmount());
        saleInvoiceDto.setTax(saleInvoice.getTax());
        saleInvoiceDto.setCurrencyCode(saleInvoice.getCurrencyCode());

        for (SaleInvoiceLine line : saleInvoice.getLines())
            saleInvoiceDto.getLine().add(createLine(line));

        return saleInvoiceDto;
    }

    private sap.saleInvoice.Line createLine(SaleInvoiceLine line) {
        sap.saleInvoice.Line lineDto = new sap.saleInvoice.Line();

        if (line.getItem()!=null)
            lineDto.setItemNum(line.getItem().getErpCode());

        if (line.getUnit()!=null)
            lineDto.setUnit(line.getUnit().getCode());

        lineDto.setItemName(line.getDescription());
        lineDto.setQuantity(line.getQuantity());
        lineDto.setPrice(lineDto.getPrice());
        lineDto.setAmount(lineDto.getAmount());
        return lineDto;
    }

    private void updateHeaders(List<SaleInvoice> invoices) {
        for (SaleInvoice invoice: invoices)
            invoice.setTransferredToErpDate(LocalDateTime.now());
        saleInvoiceRepo.save(invoices);
    }
}
