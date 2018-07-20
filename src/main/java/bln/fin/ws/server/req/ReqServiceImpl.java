package bln.fin.ws.server.req;

import bln.fin.entity.ReqLine;
import bln.fin.entity.SoapSession;
import bln.fin.entity.enums.DirectionEnum;
import bln.fin.repo.ReqLineRepo;
import bln.fin.ws.SessionService;
import bln.fin.ws.server.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;
import static bln.fin.common.Util.getCause;
import static java.util.stream.Collectors.toList;

@Service
@WebService(endpointInterface = "bln.fin.ws.server.req.ReqService", portName = "ReqServicePort", serviceName = "ReqService", targetNamespace = "http://bis.kegoc.kz/soap")
@RequiredArgsConstructor
public class ReqServiceImpl implements ReqService {
    private final ReqBusinessService reqBusinessService;
    private final ReqLineRepo reqLineRepo;
    private final SessionService sessionService;

    @Override
    public List<MessageDto> createReqLines(List<ReqLineDto> list) {
        SoapSession session = sessionService.createSession("REQ", DirectionEnum.IMPORT);

        List<ReqLine> reqLines = list.stream()
            .map(reqBusinessService::createReqLine)
            .filter(t -> t!=null)
            .collect(toList());

        List<MessageDto> messages = new ArrayList<>();
        try {
            for (ReqLine reqLine : reqLines) {
                MessageDto msg = save(reqLine);
                messages.add(msg);
            }
            sessionService.successSession(session, (long) reqLines.size());
        }
        catch (Exception e) {
            sessionService.errorSession(session, e);
        }

        return messages;
    }

    private MessageDto save(ReqLine reqLine) {
        MessageDto msg = new MessageDto();
        try {
            reqLineRepo.save(reqLine);
            msg.setSystem("BIS");
            msg.setMsgType("S");
            msg.setMsgNum("0");
            msg.setMsg("OK");
            msg.setId(reqLine.getId().toString());
            msg.setSapId(reqLine.getReqNum().toString());
        }
        catch (Exception e) {
            msg.setSystem("BIS");
            msg.setMsgType("E");
            msg.setMsgNum("1");
            msg.setSapId(reqLine.getReqNum().toString());
            Throwable cause = getCause(e);
            if (cause.getMessage()!=null)
                msg.setMsg(cause.getMessage());
            else
                msg.setMsg(cause.getClass().getCanonicalName());
        }

        return msg;
    }
}
