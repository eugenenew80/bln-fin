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
        //request(invoices);
    }

    @Override
    public void sendByContract(Long contractId) {
        List<SaleInvoice> invoices = saleInvoiceRepo.findAllByContractId(contractId);
        //request(invoices);
    }

    @Override
    public void sendOne(Long invoiceId) {
        List<SaleInvoice> invoices = Arrays.asList(saleInvoiceRepo.findOne(invoiceId));
        //request(invoices);
    }

    /*
    private void request(List<SaleInvoice> invoices) {
        List<sap.saleInvoice.EstimatedChargeInvoices.Item> items = createItems(invoices);
        if (items.isEmpty())
            return;

        sap.saleInvoice.EstimatedChargeInvoices saleInvoiceReq = new sap.saleInvoice.ObjectFactory().createEstimatedChargeInvoices();
        saleInvoiceReq.getItem().addAll(items);

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

    private List<sap.saleInvoice.EstimatedChargeInvoices.Item> createItems(List<SaleInvoice> invoices) {
        return invoices
            .stream()
            .filter(t -> t.getTransferredToErpDate()==null)
            .map(t -> mapToInvoiceDto(t))
            .filter(t -> t != null)
            .collect(toList());
    }

    private sap.saleInvoice.EstimatedChargeInvoices.Item mapToInvoiceDto(SaleInvoice saleInvoice) {
        sap.saleInvoice.EstimatedChargeInvoices.Item saleInvoiceDto = new sap.saleInvoice.EstimatedChargeInvoices.Item();

        if (saleInvoice.getDocTypeCode() == DocTypeEnum.ESTIMATED)
            saleInvoiceDto.setDocType("ZFO");

        if (saleInvoice.getDocTypeCode() == DocTypeEnum.FACT) {
            if (saleInvoice.getInvoiceTypeCode() == InvoiceTypeEnum.ORDINARY)
                saleInvoiceDto.setDocType("ZF2");

            if (saleInvoice.getInvoiceTypeCode() == InvoiceTypeEnum.CORRECTED)
                saleInvoiceDto.setDocType("ZL2");
        }

        if (saleInvoice.getSrcSaleInvoice()!=null)
            saleInvoiceDto.setSrcDocNum(saleInvoice.getSrcSaleInvoice().getErpDocNum());

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
            saleInvoiceDto.getRow().add(mapToInvoiceLineDto(line));

        return saleInvoiceDto;
    }

    private sap.saleInvoice.EstimatedChargeInvoices.Item.Row mapToInvoiceLineDto(SaleInvoiceLine line) {
        sap.saleInvoice.EstimatedChargeInvoices.Item.Row lineDto = new sap.saleInvoice.EstimatedChargeInvoices.Item.Row();

        if (line.getItem()!=null)
            lineDto.setItemNum(line.getItem().getErpCode());

        if (line.getUnit()!=null)
            lineDto.setUnit(line.getUnit().getCode());

        lineDto.setPosName(line.getDescription());
        lineDto.setQuantity(line.getQuantity());
        lineDto.setPrice(lineDto.getPrice());
        lineDto.setAmount(lineDto.getAmount());
        return lineDto;
    }
    */

    private void updateHeaders(List<SaleInvoice> invoices) {
        for (SaleInvoice invoice: invoices)
            invoice.setTransferredToErpDate(LocalDateTime.now());
        saleInvoiceRepo.save(invoices);
    }
}
