package bln.fin.ws.server.debt;

import bln.fin.entity.*;
import bln.fin.entity.enums.BatchStatusEnum;
import bln.fin.entity.enums.DirectionEnum;
import bln.fin.repo.*;
import bln.fin.ws.SessionService;
import bln.fin.ws.server.MessageDto;
import lombok.RequiredArgsConstructor;
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

    @Override
    public List<MessageDto> createDebts(List<DebtDto> list) {
        if (list == null) {
            logger.warn("Input data list is empty");
            return Arrays.asList(createErrorEmptyMessage());
        }

        logger.info("started");
        debugRequest(list);
        SoapSession session = sessionService.createSession("DEBT", DirectionEnum.IMPORT);

        List<MessageDto> messages = list.stream()
            .map(t -> createDebt(t, session))
            .collect(Collectors.toList());

        sessionService.successSession(session, (long) list.size());
        logger.info("completed");
        return messages;
    }

    private MessageDto createDebt(DebtDto debtDto, SoapSession session) {
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

            addMonitoring(debtInterface);
            debtInterface.setBpType(debtDto.getBpType());
            debtInterface.setBpNum(debtDto.getBpNum());
            debtInterface.setContractNum(debtDto.getContractNum());
            debtInterface.setExtContractNum(debtDto.getExtContractNum());
            debtInterface.setDebtType(debtDto.getDebtType());
            debtInterface.setAccountingDate(toLocalDate(debtDto.getAccountingDate()));
            debtInterface.setAmount(debtDto.getAmount());
            debtInterface.setBaseAmount(debtDto.getBaseAmount());
            debtInterface.setCurrencyCode(debtDto.getCurrencyCode());
            debtInterface.setExchangeRate(debtDto.getExchangeRate());
            debtInterface.setDocNum(debtDto.getDocNum());
            debtInterface.setDocDate(toLocalDate(debtDto.getDocDate()));
            debtInterface.setCompanyCode(debtDto.getCompanyCode());
            debtInterface.setStatus(BatchStatusEnum.W);
            debtInterface.setSession(session);

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
        logger.debug("List of input records");
        for (DebtDto line: list) {
            logger.debug("-----------------------");
            logger.debug("bpType: " + line.getBpType());
            logger.debug("debtType: " + line.getDebtType());
            logger.debug("accountingDate: " + line.getAccountingDate());
            logger.debug("amount: " + line.getAmount());
            logger.debug("baseAmount: " + line.getBaseAmount());
            logger.debug("exchangeRate: " + line.getExchangeRate());
            logger.debug("currencyCode: " + line.getCurrencyCode());
            logger.debug("docNum: " + line.getDocNum());
            logger.debug("docDate: " + line.getDocDate());
            logger.debug("bpNum: " + line.getBpNum());
            logger.debug("contractNum: " + line.getContractNum());
            logger.debug("extContractNum: " + line.getExtContractNum());
            logger.debug("companyCode: " + line.getCompanyCode());
            logger.debug("-----------------------");
            logger.debug("");
        }
    }
}
