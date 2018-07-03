package bln.fin.soap.debt;

import bln.fin.entity.CheckApplication;
import bln.fin.entity.ReceiptApplication;
import bln.fin.entity.ReqItem;
import bln.fin.entity.enums.DebtTypeEnum;
import bln.fin.repo.CheckApplicationRepo;
import bln.fin.repo.ReceiptApplicationRepo;
import bln.fin.soap.Message;
import bln.fin.soap.req.ReqItemDto;
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

    @Override
    public Message createDebts(List<DebtDto> list) {

        for (int i = 0; i < list.size(); i++) {
            DebtDto debtDto = list.get(i);
        }

        List<CheckApplication> checkList = new ArrayList<>();
        List<ReceiptApplication> receiptList = new ArrayList<>();
        for (DebtDto debtDto : list) {
            LocalDate debtAccountingDate = debtDto.getAccountingDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate debtDocDate = debtDto.getDocDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (debtDto.getBpType().equals("D")) {
                ReceiptApplication receipt = receiptApplicationRepo.findByDocNumAndDocDateAndCurrentRecordIsTrue(debtDto.getDocNum(), debtDocDate);
                if (receipt!=null) {
                    if (!debtAccountingDate.equals(receipt.getAccountingDate())) {
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
                    receipt.setDocNum(debtDto.getDocNum());
                    receipt.setDocDate(debtDocDate);
                    receipt.setAccountingDate(debtAccountingDate);
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
                CheckApplication check = checkApplicationRepo.findByDocNumAndDocDateAndCurrentRecordIsTrue(debtDto.getDocNum(), debtDocDate);
                if (check!=null) {
                    if (!debtAccountingDate.equals(check.getAccountingDate())) {
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

                check.setDocNum(debtDto.getDocNum());
                check.setDocDate(debtDocDate);
                check.setAccountingDate(debtAccountingDate);
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
