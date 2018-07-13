package bln.fin.soap.req;

import bln.fin.entity.ReqLine;
import bln.fin.repo.ReqLineRepo;
import bln.fin.soap.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.toList;

@Service
@WebService(endpointInterface = "bln.fin.soap.req.ReqService", portName = "ReqServicePort", serviceName = "ReqService", targetNamespace = "http://bis.kegoc.kz/soap")
@RequiredArgsConstructor
public class ReqServiceImpl implements ReqService {
    private final ReqBusinessService reqBusinessService;
    private final ReqLineRepo reqLineRepo;

    @Override
    public List<MessageDto> createReqLines(List<ReqLineDto> list) {
        List<ReqLine> reqLines = list.stream()
            .map(reqBusinessService::createReqLine)
            .filter(t -> t!=null)
            .collect(toList());

        reqLineRepo.save(reqLines);

        List<MessageDto> messages = new ArrayList<>();
        MessageDto msg = new MessageDto();
        msg.setSysCode("BIS");
        msg.setMsgType("S");
        msg.setMsgNum("0");
        messages.add(msg);

        return messages;
    }
}
