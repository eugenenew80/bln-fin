package bln.fin.soap.debt;

import bln.fin.entity.*;
import bln.fin.entity.enums.DebtTypeEnum;
import bln.fin.repo.*;
import lombok.RequiredArgsConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static bln.fin.common.Util.toLocalDate;

@RequiredArgsConstructor
public class DebtBusinessServiceImpl implements DebtBusinessService {
    private final BusinessPartnerRepo businessPartnerRepo;
    private final ContractRepo contractRepo;
    private final PurchaseInvoiceRepo purchaseInvoiceRepo;
    private final SaleInvoiceRepo saleInvoiceRepo;
    private final CheckApplicationRepo checkApplicationRepo;
    private final ReceiptApplicationRepo receiptApplicationRepo;

    @Override
    public List<CheckApplication> createCheckApplication(DebtDto debtDto) {
        ArrayList<CheckApplication> list = new ArrayList<>();
        LocalDate accountingDate = toLocalDate(debtDto.getAccountingDate());
        LocalDate erpDocDate =  toLocalDate(debtDto.getDocDate());

        CheckApplication check = checkApplicationRepo.findByErpDocNumAndErpDocDateAndCurrentRecordIsTrue(debtDto.getDocNum(), erpDocDate);
        if (check == null){
            check = new CheckApplication();
            list.add(check);
        }

        if (check != null && !accountingDate.equals(check.getAccountingDate()) && !check.getAmount().equals(debtDto.getAmount())) {
            check.setCurrentRecord(false);
            list.add(check);
            check = new CheckApplication();
        }

        BusinessPartner vendor = businessPartnerRepo.findByErpBpNum(debtDto.getBpNum());
        BusinessPartner customer = businessPartnerRepo.findByErpCompanyCode(debtDto.getCompanyCode());
        PurchaseInvoice invoice = purchaseInvoiceRepo.findByErpDocNumAndErpDocDate(debtDto.getDocNum(), erpDocDate);
        Contract contract = contractRepo.findByContractNum(debtDto.getExtContractNum());

        check.setInvoice(invoice);
        check.setPayer(customer);
        check.setPayee(vendor);
        check.setContract(contract);
        check.setErpDocNum(debtDto.getDocNum());
        check.setErpDocDate(erpDocDate);
        check.setAccountingDate(accountingDate);
        check.setAmount(debtDto.getAmount());
        check.setCurrencyCode(debtDto.getCurrencyCode());
        check.setDebtTypeCode(DebtTypeEnum.valueOf(debtDto.getDebtType()));
        check.setCurrentRecord(true);
        check.setExchangeRate(debtDto.getExchangeRate());
        check.setBpNum(debtDto.getBpNum());
        check.setContractNum(debtDto.getContractNum());
        check.setExternalContractNum(debtDto.getExtContractNum());

        return list;
    }

    @Override
    public List<ReceiptApplication> createReceiptApplication(DebtDto debtDto) {
        List<ReceiptApplication> list = new ArrayList<>();
        LocalDate accountingDate = toLocalDate(debtDto.getAccountingDate());
        LocalDate erpDocDate =  toLocalDate(debtDto.getDocDate());

        ReceiptApplication receipt = receiptApplicationRepo.findByErpDocNumAndErpDocDateAndCurrentRecordIsTrue(debtDto.getDocNum(), erpDocDate);
        if (receipt == null){
            receipt = new ReceiptApplication();
            list.add(receipt);
        }

        if (receipt != null && !accountingDate.equals(receipt.getAccountingDate()) && !receipt.getAmount().equals(debtDto.getAmount())) {
            receipt.setCurrentRecord(false);
            list.add(receipt);
            receipt = new ReceiptApplication();
        }

        BusinessPartner vendor = businessPartnerRepo.findByErpCompanyCode(debtDto.getCompanyCode());
        BusinessPartner customer = businessPartnerRepo.findByErpBpNum(debtDto.getBpNum());
        SaleInvoice invoice = saleInvoiceRepo.findByErpDocNumAndErpDocDate(debtDto.getDocNum(), erpDocDate);
        Contract contract = contractRepo.findByContractNum(debtDto.getExtContractNum());

        receipt.setInvoice(invoice);
        receipt.setPayer(customer);
        receipt.setPayee(vendor);
        receipt.setContract(contract);
        receipt.setErpDocNum(debtDto.getDocNum());
        receipt.setErpDocDate(erpDocDate);
        receipt.setAccountingDate(accountingDate);
        receipt.setAmount(debtDto.getAmount());
        receipt.setCurrencyCode(debtDto.getCurrencyCode());
        receipt.setDebtTypeCode(DebtTypeEnum.valueOf(debtDto.getDebtType()));
        receipt.setExchangeRate(debtDto.getExchangeRate());
        receipt.setBpNum(debtDto.getBpNum());
        receipt.setContractNum(debtDto.getContractNum());
        receipt.setExternalContractNum(debtDto.getExtContractNum());
        receipt.setCurrentRecord(true);

        return list;
    }
}
