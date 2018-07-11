package bln.fin.soap.debt;

import bln.fin.entity.*;
import bln.fin.repo.*;
import bln.fin.soap.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.jws.WebService;
import java.util.List;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
@WebService(endpointInterface = "bln.fin.soap.debt.DebtService", portName = "DebtServicePort", serviceName = "DebtService", targetNamespace = "http://bis.kegoc.kz/soap")
public class DebtServiceImpl implements DebtService {
    private final DebtBusinessService debtBusinessService;
    private final CheckApplicationRepo checkApplicationRepo;
    private final ReceiptApplicationRepo receiptApplicationRepo;

    @Override
    public Message createDebts(List<DebtDto> list) {
        List<ReceiptApplication> receiptList = list.stream()
            .filter(t -> t.getBpType().equals("D"))
            .map(debtBusinessService::createReceiptApplication)
            .flatMap(t -> t.stream())
            .collect(toList());

        List<CheckApplication> checkList = list.stream()
            .filter(t -> t.getBpType().equals("K"))
            .map(debtBusinessService::createCheckApplication)
            .flatMap(t -> t.stream())
            .collect(toList());

        checkApplicationRepo.save(checkList);
        receiptApplicationRepo.save(receiptList);

        Message msg = new Message();
        msg.setStatus("success");
        msg.setDetails(list.size() + " records created");
        return msg;
    }
}
