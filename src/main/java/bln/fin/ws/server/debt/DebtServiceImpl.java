package bln.fin.ws.server.debt;

import bln.fin.entity.enums.BatchStatusEnum;
import bln.fin.entity.enums.DirectionEnum;
import bln.fin.entity.pi.DebtInterface;
import bln.fin.entity.pi.Session;
import bln.fin.repo.*;
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
import java.util.stream.Collectors;
import static bln.fin.common.Util.*;

@RequiredArgsConstructor
@Service
@WebService(endpointInterface = "bln.fin.ws.server.debt.DebtService", portName = "DebtServicePort", serviceName = "DebtService", targetNamespace = "http://bis.kegoc.kz/soap")
public class DebtServiceImpl implements DebtService {
    private static final Logger logger = LoggerFactory.getLogger(DebtService.class);
    private final DebtInterfaceRepo debtInterfaceRepo;
    private final SessionService sessionService;
    private final DozerBeanMapper mapper;

    @Override
    public List<MessageDto> createDebts(List<DebtDto> list) {
        if (list == null) {
            logger.warn("Input data list is empty");
            return Arrays.asList(createErrorEmptyMessage());
        }

        logger.info("started");
        debugRequest(list);
        Session session = sessionService.createSession("DEBT", DirectionEnum.IMPORT);

        List<MessageDto> messages = list.stream()
            .map(t -> createDebt(t, session))
            .collect(Collectors.toList());

        sessionService.successSession(session, (long) list.size());
        debtInterfaceRepo.updateStatuses();

        logger.info("completed");
        return messages;
    }

    private MessageDto createDebt(DebtDto debtDto, Session session) {
        logger.debug("Creating line:: docNum = " + debtDto.getDocNum() + ", docDate = " + debtDto.getDocDate());

        String sapId = debtDto.getDocNum()!=null ? debtDto.getDocNum() : "";
        MessageDto msg;
        try {
            DebtInterface debtInterface = debtInterfaceRepo.findByDocNumAndDocDate(debtDto.getDocNum(), toLocalDate(debtDto.getDocDate()))
                .stream()
                .filter(t -> t.getStatus() == BatchStatusEnum.W)
                .filter(t -> t.getAccountingDate().isEqual(toLocalDate(debtDto.getAccountingDate())))
                .findFirst()
                .orElse(new DebtInterface());

            mapper.map(debtDto, debtInterface);
            debtInterface.setStatus(BatchStatusEnum.W);
            debtInterface.setSession(session);
            addMonitoring(debtInterface);

            debtInterface = debtInterfaceRepo.save(debtInterface);
            msg = createSuccessLineMessage(sapId, debtInterface.getId().toString());
            logger.debug("Creating line successfully completed");
        }
        catch (Exception e) {
            logger.debug("Error during creating line: " + e.getMessage());
            msg = createErrorLineMessage(sapId, e);
        }

        return msg;
    }

    private void debugRequest(List<DebtDto> list) {
        logger.debug("---------------------------------");
        for (DebtDto line: list) logger.debug(line.toString());
        logger.debug("---------------------------------");
    }
}
