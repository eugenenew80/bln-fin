package bln.fin.soap.debt;

import bln.fin.entity.CheckApplication;
import bln.fin.entity.ReceiptApplication;
import bln.fin.entity.enums.DebtTypeEnum;
import bln.fin.repo.CheckApplicationRepo;
import bln.fin.repo.ReceiptApplicationRepo;
import bln.fin.soap.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.jws.WebService;
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
        List<CheckApplication> checkList = new ArrayList<>();
        List<ReceiptApplication> receiptList = new ArrayList<>();
        for (DebtDto debtDto : list) {
            if (debtDto.getBpType().equals("D")) {
                ReceiptApplication receipt = new ReceiptApplication();
                receipt.setDocNum(debtDto.getDocNum());
                receipt.setDocDate(debtDto.getDocDate());
                receipt.setAccountingDate(debtDto.getAccountingDate());
                receipt.setAmount(debtDto.getAmount());
                receipt.setCurrencyCode(debtDto.getCurrencyCode());
                receipt.setDebtTypeCode(DebtTypeEnum.valueOf(debtDto.getDebtType()));
                receipt.setCurrentRecord(true);
                receipt.setExchangeRate(debtDto.getExchangeRate());
                receipt.setBpNum(debtDto.getBpNum());
                receipt.setContractNum(debtDto.getContractNum());
                receipt.setExternalContractNum(debtDto.getExtContractNum());
                receiptList.add(receipt);
            }

            if (debtDto.getBpType().equals("K")) {
                CheckApplication check = new CheckApplication();
                check.setDocNum(debtDto.getDocNum());
                check.setDocDate(debtDto.getDocDate());
                check.setAccountingDate(debtDto.getAccountingDate());
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
