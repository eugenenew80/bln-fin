package bln.fin.ws.server.req;

import bln.fin.entity.ReqLineInterface;
import bln.fin.entity.SoapSession;
import bln.fin.entity.enums.BatchStatusEnum;
import bln.fin.entity.enums.DirectionEnum;
import bln.fin.repo.ReqLineInterfaceRepo;
import bln.fin.ws.SessionService;
import bln.fin.ws.server.MessageDto;
import lombok.RequiredArgsConstructor;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.jws.WebService;
import java.util.Arrays;
import java.util.List;
import static bln.fin.common.Util.*;
import static java.util.stream.Collectors.toList;

@Service
@WebService(endpointInterface = "bln.fin.ws.server.req.ReqService", portName = "ReqServicePort", serviceName = "ReqService", targetNamespace = "http://bis.kegoc.kz/soap")
@RequiredArgsConstructor
public class ReqServiceImpl implements ReqService {
    private static final Logger logger = LoggerFactory.getLogger(ReqService.class);
    private final ReqLineInterfaceRepo reqLineInterfaceRepo;
    private final SessionService sessionService;
    private final DozerBeanMapper mapper;

    @Override
    public List<MessageDto> createReqLines(List<ReqLineDto> list) {
        if (list == null) {
            logger.warn("Input data list is empty");
            return Arrays.asList(createErrorEmptyMessage());
        }

        logger.info("started");
        debugRequest(list);
        SoapSession session = sessionService.createSession("REQ", DirectionEnum.IMPORT);

        List<MessageDto> messages = list.stream()
            .map(t -> createReq(t, session))
            .collect(toList());

        sessionService.successSession(session, (long) list.size());
        logger.info("completed, received records: " + list.size());
        return messages;
    }

    private MessageDto createReq(ReqLineDto lineDto, SoapSession session) {
        MessageDto msg;
        String sapId = lineDto.getReqNum()!=null ? lineDto.getReqNum().toString() : "";
        try {
            logger.debug("Creating line: reqNum = " + lineDto.getReqNum() + ", posNum = " + lineDto.getPosNum());
            ReqLineInterface line = reqLineInterfaceRepo.findByReqNumAndPosNum(lineDto.getReqNum(), lineDto.getPosNum())
                .stream()
                .filter(t -> t.getStatus() == BatchStatusEnum.W)
                .findFirst()
                .orElse(new ReqLineInterface());

            mapper.map(lineDto, line);
            line.setStatus(BatchStatusEnum.W);
            line.setSession(session);
            addMonitoring(line);

            line = reqLineInterfaceRepo.save(line);
            msg = createSuccessLineMessage(sapId, line.getId().toString());
            logger.debug("Creating line successfully completed");
        }
        catch (Exception e) {
            logger.debug("Error during creating line: " + e.getMessage());
            msg = createErrorLineMessage(sapId, e);
        }
        return msg;
    }

    private void debugRequest(List<ReqLineDto> list) {
        logger.debug("---------------------------------");
        for (ReqLineDto line: list) logger.debug(line.toString());
        logger.debug("---------------------------------");
    }
}
