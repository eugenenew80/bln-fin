package bln.fin.ws.server.debt;

import bln.fin.entity.*;
import bln.fin.repo.*;
import bln.fin.ws.server.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
@WebService(endpointInterface = "bln.fin.ws.server.debt.DebtService", portName = "DebtServicePort", serviceName = "DebtService", targetNamespace = "http://bis.kegoc.kz/server")
public class DebtServiceImpl implements DebtService {
    private final DebtBusinessService debtBusinessService;
    private final CheckApplicationRepo checkApplicationRepo;
    private final ReceiptApplicationRepo receiptApplicationRepo;

    @Override
    public List<MessageDto> createDebts(List<DebtDto> list) {
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

        List<MessageDto> messages = new ArrayList<>();
        MessageDto msg = new MessageDto();
        msg.setSysCode("BIS");
        msg.setMsgType("S");
        msg.setMsgNum("0");
        messages.add(msg);

        return messages;
    }
}
