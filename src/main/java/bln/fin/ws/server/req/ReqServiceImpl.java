package bln.fin.ws.server.req;

import bln.fin.entity.ReqLine;
import bln.fin.entity.SoapSession;
import bln.fin.entity.enums.DirectionEnum;
import bln.fin.repo.ReqLineRepo;
import bln.fin.ws.SessionService;
import bln.fin.ws.server.MessageDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static bln.fin.common.Util.getCause;
import static java.util.stream.Collectors.toList;

@Service
@WebService(endpointInterface = "bln.fin.ws.server.req.ReqService", portName = "ReqServicePort", serviceName = "ReqService", targetNamespace = "http://bis.kegoc.kz/soap")
@RequiredArgsConstructor
public class ReqServiceImpl implements ReqService {
    private static final Logger logger = LoggerFactory.getLogger(ReqService.class);
    private final ReqBusinessService reqBusinessService;
    private final ReqLineRepo reqLineRepo;
    private final SessionService sessionService;

    @Override
    public List<MessageDto> createReqLines(List<ReqLineDto> list) {
        logger.info("started");
        debugRequest(list);

        SoapSession session = sessionService.createSession("REQ", DirectionEnum.IMPORT);

        List<ReqLine> reqLines;
        try {
            logger.debug("Creating entities");
            reqLines = list.stream()
                .map(reqBusinessService::createReqLine)
                .filter(t -> t!=null)
                .collect(toList());
        }
        catch (Exception e) {
            logger.error("Error during creating creating entities");

            sessionService.errorSession(session, e);
            String err = e.getMessage() != null ? e.getMessage() : e.getClass().getCanonicalName();
            MessageDto msg = new MessageDto();
            msg.setSystem("BIS");
            msg.setMsgType("E");
            msg.setMsgNum("1");
            msg.setSapId(null);
            msg.setMsg(err);
            return Arrays.asList(msg);
        }

        List<MessageDto> messages = new ArrayList<>();
        try {
            logger.debug("Saving entities");
            for (ReqLine reqLine : reqLines) {
                MessageDto msg = save(reqLine);
                messages.add(msg);
            }
            sessionService.successSession(session, (long) reqLines.size());
        }
        catch (Exception e) {
            logger.debug("Error during saving entities");
            sessionService.errorSession(session, e);
        }

        logger.info("completed");
        return messages;
    }

    private void debugRequest(List<ReqLineDto> list) {
        logger.debug("List of input records");
        for (ReqLineDto line: list) {
            logger.debug("-----------------------");
            logger.debug("company code: " + line.getCompanyCode());
            logger.debug("reqNum: " + line.getReqNum());
            logger.debug("posNum: " + line.getPosNum());
            logger.debug("posName: " + line.getPosName());
            logger.debug("itemNum: " + line.getItemNum());
            logger.debug("currencyCode: " + line.getCurrencyCode());
            logger.debug("price: " + line.getPrice());
            logger.debug("quantity: " + line.getQuantity());
            logger.debug("unit: " + line.getUnit());
            logger.debug("deleted: " + line.getDeleted());
            logger.debug("unlocked: " + line.getUnlocked());
            logger.debug("-----------------------");
            logger.debug("");
        }
    }

    private MessageDto save(ReqLine reqLine) {
        MessageDto msg = new MessageDto();
        try {
            logger.debug("Saving entity: reqNum = " + reqLine.getReqNum() + ", posNum = " + reqLine.getPosNum());

            reqLineRepo.save(reqLine);
            msg.setSystem("BIS");
            msg.setMsgType("S");
            msg.setMsgNum("0");
            msg.setMsg("OK");
            msg.setId(reqLine.getId().toString());
            msg.setSapId(reqLine.getReqNum().toString());
        }
        catch (Exception e) {
            logger.debug("Error during saving entity: reqNum = " + reqLine.getReqNum() + ", posNum = " + reqLine.getPosNum());

            String err;
            Throwable cause = getCause(e);
            if (cause.getMessage()!=null)
                err = cause.getMessage();
            else
                err = cause.getClass().getCanonicalName();

            String sapId = reqLine.getReqNum()!=null ? reqLine.getReqNum().toString() : "";
            msg.setSystem("BIS");
            msg.setMsgType("E");
            msg.setMsgNum("1");
            msg.setSapId(sapId);
            msg.setMsg(err);
        }

        return msg;
    }
}
