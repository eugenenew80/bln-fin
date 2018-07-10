package bln.fin.soap.debt;

import bln.fin.entity.*;
import bln.fin.entity.enums.DebtTypeEnum;
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
@WebService(endpointInterface = "bln.fin.soap.debt.DebtService", portName = "DebtServicePort", serviceName = "DebtService", targetNamespace = "http://bis.kegoc.kz/soap")
public class DebtServiceImpl implements DebtService {
    private final CheckApplicationRepo checkApplicationRepo;
    private final ReceiptApplicationRepo receiptApplicationRepo;
    private final BusinessPartnerRepo businessPartnerRepo;
    private final ContractRepo contractRepo;
    private final PurchaseInvoiceRepo purchaseInvoiceRepo;

    @Override
    public Message createDebts(List<DebtDto> list) {

        List<CheckApplication> checkList = new ArrayList<>();
        List<ReceiptApplication> receiptList = new ArrayList<>();
        for (DebtDto debtDto : list) {
            LocalDate accountingDate = debtDto.getAccountingDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate erpDocDate = debtDto.getDocDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (debtDto.getBpType().equals("D")) {
                ReceiptApplication receipt = receiptApplicationRepo.findByErpDocNumAndErpDocDateAndCurrentRecordIsTrue(debtDto.getDocNum(), erpDocDate);
                if (receipt!=null) {
                    if (!accountingDate.equals(receipt.getAccountingDate())) {
                        if (!receipt.getAmount().equals(debtDto.getAmount())) {
                            receipt.setCurrentRecord(false);
                            receiptList.add(receipt);
                            receipt = new ReceiptApplication();
                        }
                        else
                            receipt = null;
                    }
                }
                else
                    receipt = new ReceiptApplication();

                if (receipt!=null) {
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
                    receiptList.add(receipt);
                }
            }

            if (debtDto.getBpType().equals("K")) {
                BusinessPartner vendor = businessPartnerRepo.findByErpBpNum(debtDto.getBpNum());
                BusinessPartner customer = businessPartnerRepo.findByErpCompanyCode(debtDto.getCompanyCode());
                Contract contract = contractRepo.findByContractNum(debtDto.getExtContractNum());
                PurchaseInvoice invoice = purchaseInvoiceRepo.findByErpDocNumAndErpDocDate(debtDto.getDocNum(), erpDocDate);

                CheckApplication check = checkApplicationRepo.findByErpDocNumAndErpDocDateAndCurrentRecordIsTrue(debtDto.getDocNum(), erpDocDate);
                if (check!=null) {
                    if (!accountingDate.equals(check.getAccountingDate())) {
                        if (!check.getAmount().equals(debtDto.getAmount())) {
                            check.setCurrentRecord(false);
                            checkList.add(check);
                            check = new CheckApplication();
                        }
                        else
                            check = null;
                    }
                }
                else
                    check = new CheckApplication();


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
                checkList.add(check);
            }
        }

        if (!checkList.isEmpty())
            checkApplicationRepo.save(checkList);

        if (!receiptList.isEmpty())
            receiptApplicationRepo.save(receiptList);

        Message msg = new Message();
        msg.setStatus("success");
        msg.setDetails(list.size() + " records created");
        return msg;
    }
}
