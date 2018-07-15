package bln.fin.ws.server.req;

import bln.fin.entity.ReqLine;
import bln.fin.entity.SoapSession;
import bln.fin.entity.enums.DirectionEnum;
import bln.fin.entity.enums.SessionStatusEnum;
import bln.fin.repo.ReqLineRepo;
import bln.fin.ws.SessionService;
import bln.fin.ws.server.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.toList;

@Service
@WebService(endpointInterface = "bln.fin.ws.server.req.ReqService", portName = "ReqServicePort", serviceName = "ReqService", targetNamespace = "http://bis.kegoc.kz/server")
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

        try {
            reqLineRepo.save(reqLines);
            sessionService.successSession(session, (long) reqLines.size());
        }
        catch (Exception e) {
            sessionService.errorSession(session, e);
        }

        return createResponse(session);
    }

    private List<MessageDto> createResponse(SoapSession session) {
        List<MessageDto> messages = new ArrayList<>();
        MessageDto msg = new MessageDto();
        msg.setSysCode("BIS");

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
