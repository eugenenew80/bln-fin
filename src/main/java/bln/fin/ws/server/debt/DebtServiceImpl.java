package bln.fin.ws.server.debt;

import bln.fin.entity.*;
import bln.fin.entity.enums.DirectionEnum;
import bln.fin.repo.*;
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

@RequiredArgsConstructor
@Service
@WebService(endpointInterface = "bln.fin.ws.server.debt.DebtService", portName = "DebtServicePort", serviceName = "DebtService", targetNamespace = "http://bis.kegoc.kz/soap")
public class DebtServiceImpl implements DebtService {
    private static final Logger logger = LoggerFactory.getLogger(DebtService.class);
    private final DebtBusinessService debtBusinessService;
    private final CheckApplicationRepo checkApplicationRepo;
    private final ReceiptApplicationRepo receiptApplicationRepo;
    private final SessionService sessionService;

    @Override
    public List<MessageDto> createDebts(List<DebtDto> list) {
        logger.info("started");
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

        debugRequest(list);
        SoapSession session = sessionService.createSession("DEBT", DirectionEnum.IMPORT);

        List<ReceiptApplication> receiptList;
        try {
            receiptList = list.stream()
                .filter(t -> t.getBpType().equals("D"))
                .map(debtBusinessService::createReceiptApplication)
                .flatMap(t -> t.stream())
                .collect(toList());
        }
        catch (Exception e) {
            logger.error("Error during creating creating receipt application entities");
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

        List<CheckApplication> checkList;
        try {
            checkList = list.stream()
                .filter(t -> t.getBpType().equals("K"))
                .map(debtBusinessService::createCheckApplication)
                .flatMap(t -> t.stream())
                .collect(toList());
        }
        catch (Exception e) {
            logger.error("Error during creating creating check application entities");
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
            for (ReceiptApplication receipt : receiptList) {
                MessageDto msg = save(receipt);
                messages.add(msg);
            }

            for (CheckApplication check : checkList) {
                MessageDto msg = save(check);
                messages.add(msg);
            }

            sessionService.successSession(session, (long) (receiptList.size() + checkList.size()));
        }
        catch (Exception e) {
            logger.debug("Error during saving entities");
            sessionService.errorSession(session, e);
        }

        logger.info("completed");
        return messages;
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


    private MessageDto save(ReceiptApplication receipt) {
        MessageDto msg = new MessageDto();
        try {
            logger.debug("Saving receipt entity: erpDocNum = " + receipt.getErpDocNum() + ", erpDocDate = " + receipt.getErpDocDate());

            receiptApplicationRepo.save(receipt);
            msg.setSystem("BIS");
            msg.setMsgType("S");
            msg.setMsgNum("0");
            msg.setMsg("OK");
            msg.setId(receipt.getId().toString());
            msg.setSapId(receipt.getErpDocNum().toString());
        }
        catch (Exception e) {
            logger.debug("Error during saving receipt entity: erpDocNum = " + receipt.getErpDocNum() + ", erpDocDate = " + receipt.getErpDocDate());

            String err;
            Throwable cause = getCause(e);
            if (cause.getMessage()!=null)
                err = cause.getMessage();
            else
                err = cause.getClass().getCanonicalName();

            String sapId = receipt.getErpDocNum()!=null ? receipt.getErpDocNum().toString() : "";
            msg.setSystem("BIS");
            msg.setMsgType("E");
            msg.setMsgNum("1");
            msg.setSapId(sapId);
            msg.setMsg(err);
        }

        return msg;
    }

    private MessageDto save(CheckApplication check) {
        MessageDto msg = new MessageDto();
        try {
            logger.debug("Saving check entity: erpDocNum = " + check.getErpDocNum() + ", erpDocDate = " + check.getErpDocDate());

            checkApplicationRepo.save(check);
            msg.setSystem("BIS");
            msg.setMsgType("S");
            msg.setMsgNum("0");
            msg.setMsg("OK");
            msg.setId(check.getId().toString());
            msg.setSapId(check.getErpDocNum().toString());
        }
        catch (Exception e) {
            logger.debug("Error during saving check entity: erpDocNum = " + check.getErpDocNum() + ", erpDocDate = " + check.getErpDocDate());

            String err;
            Throwable cause = getCause(e);
            if (cause.getMessage()!=null)
                err = cause.getMessage();
            else
                err = cause.getClass().getCanonicalName();

            String sapId = check.getErpDocNum()!=null ? check.getErpDocNum().toString() : "";
            msg.setSystem("BIS");
            msg.setMsgType("E");
            msg.setMsgNum("1");
            msg.setSapId(sapId);
            msg.setMsg(err);
        }

        return msg;
    }
}
