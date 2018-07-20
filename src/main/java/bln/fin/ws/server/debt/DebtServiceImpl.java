package bln.fin.ws.server.debt;

import bln.fin.entity.*;
import bln.fin.entity.enums.DirectionEnum;
import bln.fin.entity.enums.SessionStatusEnum;
import bln.fin.repo.*;
import bln.fin.ws.SessionService;
import bln.fin.ws.server.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
@WebService(endpointInterface = "bln.fin.ws.server.debt.DebtService", portName = "DebtServicePort", serviceName = "DebtService", targetNamespace = "http://bis.kegoc.kz/soap")
public class DebtServiceImpl implements DebtService {
    private final DebtBusinessService debtBusinessService;
    private final CheckApplicationRepo checkApplicationRepo;
    private final ReceiptApplicationRepo receiptApplicationRepo;
    private final SessionService sessionService;

    @Override
    public List<MessageDto> createDebts(List<DebtDto> list) {
        SoapSession session = sessionService.createSession("DEBT", DirectionEnum.IMPORT);

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

        try {
            checkApplicationRepo.save(checkList);
            receiptApplicationRepo.save(receiptList);
            sessionService.successSession(session, (long) checkList.size() + (long) receiptList.size());
        }
        catch (Exception e) {
            sessionService.errorSession(session, e);
        }

        return createResponse(session);
    }

    private List<MessageDto> createResponse(SoapSession session) {
        List<MessageDto> messages = new ArrayList<>();
        MessageDto msg = new MessageDto();
        msg.setSystem("BIS");

        if (session.getStatus()==SessionStatusEnum.C) {
            msg.setMsgType("S");
            msg.setMsgNum("0");
            messages.add(msg);
        }

        if (session.getStatus()==SessionStatusEnum.E) {
            msg.setMsgType("E");
            msg.setMsgNum("1");
            msg.setMsg(session.getErrMsg());
            messages.add(msg);
        }

        return messages;
    }
}
