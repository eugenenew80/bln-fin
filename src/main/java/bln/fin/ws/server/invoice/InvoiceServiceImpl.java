package bln.fin.ws.server.invoice;

import bln.fin.entity.enums.BatchStatusEnum;
import bln.fin.entity.enums.DirectionEnum;
import bln.fin.entity.pi.InvoiceInterface;
import bln.fin.entity.pi.InvoiceLineInterface;
import bln.fin.entity.pi.InvoiceStatusInterface;
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
import java.util.*;

import static bln.fin.common.Util.*;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
@WebService(endpointInterface = "bln.fin.ws.server.invoice.InvoiceService", portName = "InvoiceServicePort", serviceName = "InvoiceService", targetNamespace = "http://bis.kegoc.kz/soap")
public class InvoiceServiceImpl implements InvoiceService {
    private static final Logger logger = LoggerFactory.getLogger(InvoiceService.class);
    private final InvoiceInterfaceRepo invoiceInterfaceRepo;
    private final InvoiceStatusInterfaceRepo invoiceStatusInterfaceRepo;
    private final SessionService sessionService;
    private final DozerBeanMapper mapper;

    @Override
    public List<MessageDto> updateStatuses(List<InvoiceStatusDto> list) {
        if (list == null) {
            logger.warn("Input data list is empty");
            return Arrays.asList(createErrorEmptyMessage());
        }

        logger.info("started");
        debugInvoiceStatusRequest(list);
        Session session = sessionService.createSession("INVOICE_STATUS", DirectionEnum.IMPORT);

        List<MessageDto> messages = list.stream()
            .map(t -> createInvoiceStatus(t, session))
            .collect(toList());

        sessionService.successSession(session, (long) list.size());
        logger.info("completed");
        return messages;
    }

    @Override
    public List<MessageDto> createInvoices(List<InvoiceDto> list) {
        if (list == null) {
            logger.warn("Input data list is empty");
            return Arrays.asList(createErrorEmptyMessage());
        }

        logger.info("started");
        debugInvoiceRequest(list);
        Session session = sessionService.createSession("INVOICE", DirectionEnum.IMPORT);

        List<MessageDto> messages = list.stream()
            .map(t -> createInvoice(t, session))
            .collect(toList());

        sessionService.successSession(session, (long) list.size());
        logger.info("completed");
        return messages;
    }

    private MessageDto createInvoiceStatus(InvoiceStatusDto invoiceStatusDto, Session session) {
        logger.debug("Creating line:: docNum = " + invoiceStatusDto.getDocNum() + ", docDate = " + invoiceStatusDto.getDocDate());

        String sapId = invoiceStatusDto.getDocNum()!=null ? invoiceStatusDto.getDocNum() : "";
        MessageDto msg;
        try {
            InvoiceStatusInterface invoiceStatusInterface = invoiceStatusInterfaceRepo.findByDocNumAndDocDate(invoiceStatusDto.getDocNum(), toLocalDate(invoiceStatusDto.getDocDate()))
                .stream()
                .filter(t -> t.getStatus() == BatchStatusEnum.W)
                .findFirst()
                .orElse(new InvoiceStatusInterface());

            mapper.map(invoiceStatusDto, invoiceStatusInterface);
            invoiceStatusInterface.setStatus(BatchStatusEnum.W);
            invoiceStatusInterface.setSession(session);
            addMonitoring(invoiceStatusInterface);

            invoiceStatusInterface = invoiceStatusInterfaceRepo.save(invoiceStatusInterface);
            msg = createSuccessLineMessage(sapId, invoiceStatusInterface.getId().toString());
            logger.debug("Creating line successfully completed");
        }
        catch (Exception e) {
            logger.debug("Error during creating line: " + e.getMessage());
            msg = createErrorLineMessage(sapId, e);
        }
        return msg;
    }

    private MessageDto createInvoice(InvoiceDto invoiceDto, Session session) {
        logger.debug("Creating line:: docNum = " + invoiceDto.getDocNum() + ", docDate = " + invoiceDto.getDocDate());

        String sapId = invoiceDto.getDocNum()!=null ? invoiceDto.getDocNum().toString() : "";
        MessageDto msg;
        try {
            InvoiceInterface invoiceInterface = invoiceInterfaceRepo.findByDocNumAndDocDate(invoiceDto.getDocNum(), toLocalDate(invoiceDto.getDocDate()))
                .stream()
                .filter(t -> t.getStatus() == BatchStatusEnum.W)
                .findFirst()
                .orElse(new InvoiceInterface());

            mapper.map(invoiceDto, invoiceInterface);
            invoiceInterface.setStatus(BatchStatusEnum.W);
            invoiceInterface.setSession(session);
            addMonitoring(invoiceInterface);

            invoiceInterface.setLines(Optional.ofNullable(invoiceInterface.getLines()).orElse(new HashSet<>()));
            for (InvoiceLineDto invoiceLineDto : invoiceDto.getLines()) {
                InvoiceLineInterface invoiceLineInterface = createInvoiceLine(invoiceInterface, invoiceLineDto);
                invoiceInterface.getLines().add(invoiceLineInterface);
            }

            invoiceInterface = invoiceInterfaceRepo.save(invoiceInterface);
            msg = createSuccessLineMessage(sapId, invoiceInterface.getId().toString());
            logger.debug("Creating line successfully completed");
        }
        catch (Exception e) {
            logger.debug("Error during creating line: " + e.getMessage());
            msg = createErrorLineMessage(sapId, e);
        }
        return msg;
    }

    private InvoiceLineInterface createInvoiceLine(InvoiceInterface invoiceInterface, InvoiceLineDto invoiceLineDto) {
        InvoiceLineInterface invoiceLineInterface = invoiceInterface.getLines()
            .stream()
            .filter(t -> t.getPosNum().equals(invoiceLineDto.getPosNum()))
            .findFirst()
            .orElse(new InvoiceLineInterface());

        mapper.map(invoiceLineDto, invoiceLineInterface);
        invoiceLineInterface.setInvoice(invoiceInterface);
        addMonitoring(invoiceLineInterface);
        return invoiceLineInterface;
    }

    private void debugInvoiceStatusRequest(List<InvoiceStatusDto> list) {
        logger.debug("-----------------------");
        for (InvoiceStatusDto line: list) logger.debug(line.toString());
        logger.debug("-----------------------");
    }

    private void debugInvoiceRequest(List<InvoiceDto> list) {
        logger.debug("-----------------------");
        for (InvoiceDto line: list) logger.debug(line.toString());
        logger.debug("-----------------------");
    }
}
