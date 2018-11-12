package bln.fin.ws.client.contract.sd;

import bln.fin.entity.enums.*;
import bln.fin.entity.pi.*;
import bln.fin.repo.*;
import sap.contract.sd.*;
import bln.fin.ws.SessionService;
import lombok.RequiredArgsConstructor;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;
import javax.xml.bind.JAXBElement;
import java.time.LocalDateTime;
import java.util.List;
import static bln.fin.common.Util.getSuccessMessage;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class SaleContractClientServiceImpl implements SaleContractClientService {
    private static final Logger logger = LoggerFactory.getLogger(SaleContractClientService.class);
    private static final String objectCode = "SALE_CONTRACT";
    private final ContractInterfaceRepo contractInterfaceRepo;
    private final WebServiceTemplate saleContractServiceTemplate;
    private final SessionService sessionService;
    private final SessionMessageRepo sessionMessageRepo;
    private final DozerBeanMapper mapper;

    @Override
    public void send() {
        List<ContractInterface> list = contractInterfaceRepo.findAllByStatusAndBpType(BatchStatusEnum.W, "D");
        send(list);
    }

    private void send(List<ContractInterface> list) {
        if (list.isEmpty()) return;
        logger.info("started");

        Session session = sessionService.createSession(objectCode, DirectionEnum.EXPORT);
        try {
            list = updateStatuses(list, session);
            JAXBElement<Contract> request = createRequest(list);
            JAXBElement<Response> response = (JAXBElement<Response>) saleContractServiceTemplate.marshalSendAndReceive(request);

            List<SessionMessage> messages = saveResponse(response, session);
            sessionService.successSession(session, (long) list.size());
            updateStatuses(list, messages, session);
        }
        catch (SoapFaultClientException e) {
            logger.error("Error during request, Fault Code: " + e.getFaultCode() + ", " + "Fault Reason: " + e.getFaultStringOrReason());
            sessionService.errorSession(session, e);
            updateStatuses(list, session);
        }
        catch (Exception e) {
            logger.error("Error during request: " + e.getMessage());
            sessionService.errorSession(session, e);
            updateStatuses(list, session);
        }

        contractInterfaceRepo.update();
        logger.info("completed");
    }

    private JAXBElement<Contract> createRequest(List<ContractInterface> list) {
        List<Contract.Item> items = list.stream()
            .map(this::mapItem)
            .filter(t -> t != null)
            .collect(toList());
        debugRequest(items);

        Contract contractDto = new Contract();
        contractDto.getItem().addAll(items);
        return new ObjectFactory().createContract(contractDto);
    }

    private Contract.Item mapItem(ContractInterface contract) {
        Contract.Item item = mapper.map(contract, Contract.Item.class);
        if (contract.getIsHeaderOnly() || contract.getLines() == null)
            return item;

        for (ContractLineInterface line : contract.getLines()) {
            Contract.Item.Row row = mapper.map(line, Contract.Item.Row.class);
            item.getRow().add(row);
        }
        return item;
    }

    private void debugRequest(List<Contract.Item> list) {
        logger.debug("---------------------------------");
        for (Contract.Item line: list) logger.debug(line.toString());
        logger.debug("---------------------------------");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected List<SessionMessage> saveResponse(JAXBElement<Response> response, Session session) {
        List<SessionMessage> list = response.getValue()
            .getItem()
            .stream()
            .filter(t -> t.getMsgType() != null || t.getMsgNum() != null || t.getMsg() != null)
            .map(t -> {
                SessionMessage msg = new SessionMessage(objectCode, session);
                mapper.map(t, msg);
                return msg;
            })
            .collect(toList());

        return sessionMessageRepo.save(list);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected List<ContractInterface> updateStatuses(List<ContractInterface> list, List<SessionMessage> messages, Session session) {
        for (ContractInterface line: list) {
            SessionMessage msg = getSuccessMessage(messages, line.getId().toString());
            line.setStatus(msg != null ? BatchStatusEnum.C : BatchStatusEnum.E);
            line.setContractNum(msg != null ? msg.getSapId() : line.getContractNum());
            line.setSession(session);
            line.setLastUpdateDate(LocalDateTime.now());
        }
        return contractInterfaceRepo.save(list);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected List<ContractInterface> updateStatuses(List<ContractInterface> list, Session session) {
        for (ContractInterface line: list) {
            line.setStatus(session.getStatus());
            line.setSession(session);
            line.setLastUpdateDate(LocalDateTime.now());
        }
        return contractInterfaceRepo.save(list);
    }
}