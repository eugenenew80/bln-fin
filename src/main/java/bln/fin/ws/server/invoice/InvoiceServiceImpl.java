package bln.fin.ws.server.invoice;

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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static bln.fin.common.Util.getCause;
import static bln.fin.common.Util.toLocalDate;

@RequiredArgsConstructor
@Service
@WebService(endpointInterface = "bln.fin.ws.server.invoice.InvoiceService", portName = "InvoiceServicePort", serviceName = "InvoiceService", targetNamespace = "http://bis.kegoc.kz/soap")
public class InvoiceServiceImpl implements InvoiceService {
    private static final Logger logger = LoggerFactory.getLogger(InvoiceService.class);
    private final InvoiceInterfaceRepo invoiceInterfaceRepo;
    private final InvoiceStatusInterfaceRepo invoiceStatusInterfaceRepo;
    private final SessionService sessionService;

    @Override
    public List<MessageDto> updateStatuses(List<InvoiceStatusDto> list) {
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

        debugInvoiceStatusRequest(list);
        SoapSession session = sessionService.createSession("INVOICE_STATUS", DirectionEnum.IMPORT);
        List<MessageDto> messages = new ArrayList<>();

        try {
            for (InvoiceStatusDto invoiceStatusDto : list) {
                logger.debug("Creating line:: docNum = " + invoiceStatusDto.getDocNum() + ", docDate = " + invoiceStatusDto.getDocDate());

                MessageDto msg = new MessageDto();
                try {
                    InvoiceStatusInterface invoiceStatusInterface = invoiceStatusInterfaceRepo.findByDocNumAndDocDate(invoiceStatusDto.getDocNum(), toLocalDate(invoiceStatusDto.getDocDate()))
                        .stream()
                        .filter(t -> t.getStatus() == BatchStatusEnum.W)
                        .findFirst()
                        .orElse(new InvoiceStatusInterface());

                    if (invoiceStatusInterface.getId() == null)  {
                        invoiceStatusInterface.setCreateDate(LocalDateTime.now());
                        invoiceStatusInterface.setLastUpdateDate(null);
                    }

                    if (invoiceStatusInterface.getId() != null)
                        invoiceStatusInterface.setLastUpdateDate(LocalDateTime.now());

                    invoiceStatusInterface.setBpType(invoiceStatusDto.getBpType());
                    invoiceStatusInterface.setDocNum(invoiceStatusDto.getDocNum());
                    invoiceStatusInterface.setDocDate(toLocalDate(invoiceStatusDto.getDocDate()));
                    invoiceStatusInterface.setEsfDocNum(invoiceStatusDto.getEsfDocNum());
                    invoiceStatusInterface.setEsfDocDate(toLocalDate(invoiceStatusDto.getEsfDocDate()));
                    invoiceStatusInterface.setEsfStatus(invoiceStatusDto.getEsfStatus());
                    invoiceStatusInterface.setStatus(BatchStatusEnum.W);
                    invoiceStatusInterface.setSession(session);
                    invoiceStatusInterface = invoiceStatusInterfaceRepo.save(invoiceStatusInterface);

                    msg.setSystem("BIS");
                    msg.setMsgType("S");
                    msg.setMsgNum("0");
                    msg.setMsg("OK");
                    msg.setId(invoiceStatusInterface.getId().toString());
                    msg.setSapId(invoiceStatusDto.getDocNum().toString());
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

                    String sapId = invoiceStatusDto.getDocNum()!=null ? invoiceStatusDto.getDocNum().toString() : "";
                    msg.setSystem("BIS");
                    msg.setMsgType("E");
                    msg.setMsgNum("2");
                    msg.setSapId(sapId);
                    msg.setMsg(err);
                }
                messages.add(msg);
            }
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

    @Override
    public List<MessageDto> createInvoices(List<InvoiceDto> list) {
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

        debugInvoiceRequest(list);
        SoapSession session = sessionService.createSession("INVOICE", DirectionEnum.IMPORT);
        List<MessageDto> messages = new ArrayList<>();
        try {
            for (InvoiceDto invoiceDto : list) {
                logger.debug("Creating line:: docNum = " + invoiceDto.getDocNum() + ", docDate = " + invoiceDto.getDocDate());

                MessageDto msg = new MessageDto();
                try {
                    InvoiceInterface invoiceInterface = invoiceInterfaceRepo.findByDocNumAndDocDate(invoiceDto.getDocNum(), toLocalDate(invoiceDto.getDocDate()))
                        .stream()
                        .filter(t -> t.getStatus() == BatchStatusEnum.W)
                        .findFirst()
                        .orElse(new InvoiceInterface());

                    if (invoiceInterface.getId() == null)  {
                        invoiceInterface.setCreateDate(LocalDateTime.now());
                        invoiceInterface.setLastUpdateDate(null);
                    }

                    if (invoiceInterface.getId() != null)
                        invoiceInterface.setLastUpdateDate(LocalDateTime.now());

                    invoiceInterface.setBpType(invoiceDto.getBpType());
                    invoiceInterface.setBpNum(invoiceDto.getBpNum());
                    invoiceInterface.setDocNum(invoiceDto.getDocNum());
                    invoiceInterface.setExtDocNum(invoiceDto.getExtDocNum());
                    invoiceInterface.setDocDate(toLocalDate(invoiceDto.getDocDate()));
                    invoiceInterface.setContractNum(invoiceDto.getContractNum());
                    invoiceInterface.setExtContractNum(invoiceDto.getExtContractNum());
                    invoiceInterface.setCompanyCode(invoiceDto.getCompanyCode());
                    invoiceInterface.setAmount(invoiceDto.getAmount());
                    invoiceInterface.setTax(invoiceDto.getTax());
                    invoiceInterface.setCompanyCode(invoiceDto.getCurrencyCode());
                    invoiceInterface.setExchangeRate(invoiceDto.getExchangeRate());
                    invoiceInterface.setCurrencyCode(invoiceDto.getCurrencyCode());
                    invoiceInterface.setStatus(BatchStatusEnum.W);
                    invoiceInterface.setSession(session);

                    if (invoiceInterface.getLines()==null)
                        invoiceInterface.setLines(new ArrayList<>());

                    for (InvoiceLineDto invoiceLineDto : invoiceDto.getLines()) {
                        InvoiceLineInterface invoiceLineInterface = invoiceInterface.getLines()
                            .stream()
                            .filter(t -> t.getPosNum().equals(invoiceLineDto.getPosNum()))
                            .findFirst()
                            .orElse(new InvoiceLineInterface());

                        if (invoiceLineInterface.getId() == null)  {
                            invoiceLineInterface.setCreateDate(LocalDateTime.now());
                            invoiceLineInterface.setLastUpdateDate(null);
                        }

                        if (invoiceLineInterface.getId() != null)
                            invoiceLineInterface.setLastUpdateDate(LocalDateTime.now());

                        invoiceLineInterface.setInvoice(invoiceInterface);
                        invoiceLineInterface.setPosNum(invoiceLineDto.getPosNum());
                        invoiceLineInterface.setPosName(invoiceLineDto.getPosName());
                        invoiceLineInterface.setUnit(invoiceLineDto.getUnit());
                        invoiceLineInterface.setAmount(invoiceLineDto.getAmount());
                        invoiceLineInterface.setQuantity(invoiceLineDto.getQuantity());
                        invoiceInterface.getLines().add(invoiceLineInterface);
                    }
                    invoiceInterface = invoiceInterfaceRepo.save(invoiceInterface);

                    msg.setSystem("BIS");
                    msg.setMsgType("S");
                    msg.setMsgNum("0");
                    msg.setMsg("OK");
                    msg.setId(invoiceInterface.getId().toString());
                    msg.setSapId(invoiceDto.getDocNum().toString());
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

                    String sapId = invoiceDto.getDocNum()!=null ? invoiceDto.getDocNum().toString() : "";
                    msg.setSystem("BIS");
                    msg.setMsgType("E");
                    msg.setMsgNum("2");
                    msg.setSapId(sapId);
                    msg.setMsg(err);
                }
                messages.add(msg);
            }
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


    private void debugInvoiceStatusRequest(List<InvoiceStatusDto> list) {
        logger.debug("List of input records");
        for (InvoiceStatusDto line: list) {
            logger.debug("-----------------------");
            logger.debug("bpType: " + line.getBpType());
            logger.debug("docNum: " + line.getDocNum());
            logger.debug("docDate: " + line.getDocDate());
            logger.debug("esfDocNum: " + line.getEsfDocNum());
            logger.debug("esfDocDate: " + line.getEsfDocDate());
            logger.debug("esfStatus: " + line.getEsfStatus());
            logger.debug("-----------------------");
            logger.debug("");
        }
    }

    private void debugInvoiceRequest(List<InvoiceDto> list) {
        logger.debug("List of input records");
        for (InvoiceDto line: list) {
            logger.debug("-----------------------");
            logger.debug("bpType: " + line.getBpType());
            logger.debug("docNum: " + line.getDocNum());
            logger.debug("docDate: " + line.getDocDate());
            logger.debug("extDocNum: " + line.getExtDocNum());
            logger.debug("accountingDate: " + line.getAccountingDate());
            logger.debug("bpNum: " + line.getBpNum());
            logger.debug("contractNum: " + line.getContractNum());
            logger.debug("extContractNum: " + line.getExtContractNum());
            logger.debug("companyCode: " + line.getCompanyCode());
            logger.debug("amount: " + line.getAmount());
            logger.debug("tax: " + line.getTax());
            logger.debug("currencyCode: " + line.getCurrencyCode());
            logger.debug("exchangeRate: " + line.getExchangeRate());
            logger.debug("-----------------------");
            logger.debug("");
        }
    }
}
