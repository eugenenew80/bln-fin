package bln.fin.ws.server.req;

import bln.fin.entity.ReqLineInterface;
import bln.fin.entity.SoapSession;
import bln.fin.entity.enums.BatchStatusEnum;
import bln.fin.entity.enums.DirectionEnum;
import bln.fin.repo.ReqLineInterfaceRepo;
import bln.fin.ws.SessionService;
import bln.fin.ws.server.MessageDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.jws.WebService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static bln.fin.common.Util.getCause;
import static bln.fin.common.Util.toLocalDate;

@Service
@WebService(endpointInterface = "bln.fin.ws.server.req.ReqService", portName = "ReqServicePort", serviceName = "ReqService", targetNamespace = "http://bis.kegoc.kz/soap")
@RequiredArgsConstructor
public class ReqServiceImpl implements ReqService {
    private static final Logger logger = LoggerFactory.getLogger(ReqService.class);
    private final ReqLineInterfaceRepo reqLineInterfaceRepo;
    private final SessionService sessionService;

    @Override
    public List<MessageDto> createReqLines(List<ReqLineDto> list) {
        if (list == null) {
            logger.warn("Input data list is empty");
            MessageDto msg = new MessageDto();
            msg.setSystem("BIS");
            msg.setMsgType("W");
            msg.setMsgNum("1");
            msg.setSapId(null);
            msg.setMsg("Input data list is empty");
            return Arrays.asList(msg);
        }

        logger.info("started");

        debugRequest(list);
        SoapSession session = sessionService.createSession("REQ", DirectionEnum.IMPORT);
        List<MessageDto> messages = new ArrayList<>();
        try {
            for (ReqLineDto lineDto : list ) {
                logger.debug("Creating line: reqNum = " + lineDto.getReqNum() + ", posNum = " + lineDto.getPosNum());

                MessageDto msg = new MessageDto();
                try {
                    ReqLineInterface lineInterface = reqLineInterfaceRepo.findByReqNumAndPosNum(lineDto.getReqNum(), lineDto.getPosNum())
                        .stream()
                        .filter(t -> t.getStatus() == BatchStatusEnum.W)
                        .findFirst()
                        .orElse(new ReqLineInterface());

                    if (lineInterface.getId() == null)  {
                        lineInterface.setCreateDate(LocalDateTime.now());
                        lineInterface.setLastUpdateDate(null);
                    }

                    if (lineInterface.getId() != null)
                        lineInterface.setLastUpdateDate(LocalDateTime.now());

                    lineInterface.setReqNum(lineDto.getReqNum());
                    lineInterface.setPosNum(lineDto.getPosNum());
                    lineInterface.setPosName(lineDto.getPosName());
                    lineInterface.setItemNum(lineDto.getItemNum());
                    lineInterface.setQuantity(lineDto.getQuantity());
                    lineInterface.setPrice(lineDto.getPrice());
                    lineInterface.setUnit(lineDto.getUnit());
                    lineInterface.setCurrencyCode(lineDto.getCurrencyCode());
                    lineInterface.setCompanyCode(lineDto.getCompanyCode());
                    lineInterface.setExpectedDate(toLocalDate(lineDto.getExpectedDate()));
                    lineInterface.setDeleted(lineDto.getDeleted());
                    lineInterface.setUnlocked(lineDto.getUnlocked());
                    lineInterface.setStatus(BatchStatusEnum.W);
                    lineInterface.setSession(session);
                    lineInterface = reqLineInterfaceRepo.save(lineInterface);

                    msg.setSystem("BIS");
                    msg.setMsgType("S");
                    msg.setMsgNum("0");
                    msg.setMsg("OK");
                    msg.setId(lineInterface.getId().toString());
                    msg.setSapId(lineDto.getReqNum().toString());

                    logger.debug("Creating line successfully completed");
                }
                catch (Exception e) {
                    logger.debug("Error during creating line: " + e.getMessage());

                    String err;
                    Throwable cause = getCause(e);
                    if (cause.getMessage()!=null)
                        err = cause.getMessage();
                    else
                        err = cause.getClass().getCanonicalName();

                    String sapId = lineDto.getReqNum()!=null ? lineDto.getReqNum().toString() : "";
                    msg.setSystem("BIS");
                    msg.setMsgType("E");
                    msg.setMsgNum("2");
                    msg.setSapId(sapId);
                    msg.setMsg(err);
                }
                messages.add(msg);
            }
            sessionService.successSession(session, (long) list.size());
        }

        catch (Exception e) {
            logger.debug("Error during saving entities " + e.getMessage());
            String err = e.getMessage() != null ? e.getMessage() : e.getClass().getCanonicalName();
            MessageDto msg = new MessageDto();
            msg.setSystem("BIS");
            msg.setMsgType("E");
            msg.setMsgNum("1");
            msg.setSapId(null);
            msg.setMsg(err);
            messages.add(msg);
            sessionService.errorSession(session, e);
        }

        logger.info("completed");
        return messages;
    }


    private void debugRequest(List<ReqLineDto> list) {
        logger.debug("List of input records");
        for (ReqLineDto line: list) {
            logger.debug("-----------------------");
            logger.debug("companyCode: " + line.getCompanyCode());
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
}
