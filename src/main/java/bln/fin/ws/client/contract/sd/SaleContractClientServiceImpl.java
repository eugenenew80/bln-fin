package bln.fin.ws.client.contract.sd;

import bln.fin.entity.enums.BatchStatusEnum;
import bln.fin.entity.enums.DirectionEnum;
import bln.fin.entity.pi.*;
import bln.fin.repo.ContractInterfaceRepo;
import bln.fin.repo.SessionMessageRepo;
import bln.fin.ws.SessionService;
import bln.fin.ws.client.ClientHelper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;
import sap.contract.sd.Contract;
import sap.contract.sd.ObjectFactory;
import sap.contract.sd.Response;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.toList;

@SuppressWarnings("Duplicates")
@Service
@RequiredArgsConstructor
public class SaleContractClientServiceImpl implements SaleContractClientService {
    private static final Logger logger = LoggerFactory.getLogger(SaleContractClientService.class);
    private static final String objectCode = "SALE_CONTRACT";
    private final ContractInterfaceRepo contractInterfaceRepo;
    private final WebServiceTemplate saleContractServiceTemplate;
    private final SessionService sessionService;
    private final SessionMessageRepo sessionMessageRepo;

    @Override
    public void send() {
        List<ContractInterface> list = contractInterfaceRepo.findAllByStatus(BatchStatusEnum.W)
            .stream()
            .filter(t -> t.getBpType().equals("D"))
            .collect(toList());

        send(list);
    }

    private void send(List<ContractInterface> list) {
        if (list.isEmpty()) {
            logger.warn("List is empty");
            return;
        }

        logger.info("started");
        Session session = sessionService.createSession(objectCode, DirectionEnum.EXPORT);
        list = updateStatuses(list, session);

        List<Contract.Item> items = list.stream()
            .map(ClientHelper::createContractItem)
            .filter(t -> t != null)
            .collect(toList());

        Contract contractReq = new ObjectFactory().createContract();
        contractReq.getItem().addAll(items);
        debugRequest(items);

        try {
            QName qName = new QName("urn:kegoc.kz:BIS:LO_0001_Contract", "Contract");
            JAXBElement<Contract> request = new JAXBElement<>(qName, Contract.class, contractReq);
            JAXBElement<Response> response = (JAXBElement<Response>) saleContractServiceTemplate.marshalSendAndReceive(request);
            List<SessionMessage> messages = saveMessages(response.getValue(), session);
            sessionService.successSession(session, (long) items.size());
            updateStatuses(list, messages);
        }

        catch (SoapFaultClientException e) {
            logger.error("Fault Code: " + e.getFaultCode());
            logger.error("Fault Reason: " + e.getFaultStringOrReason());
            sessionService.errorSession(session, e);
            updateStatuses(list, session);
        }

        catch (Exception e) {
            logger.debug("Error during request: " + e.getMessage());
            sessionService.errorSession(session, e);
            updateStatuses(list, session);
        }
        logger.info("completed");
    }

    private void debugRequest(List<Contract.Item> list) {
        logger.debug("---------------------------------");
        for (Contract.Item line: list) logger.debug(line.toString());
        logger.debug("---------------------------------");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    List<SessionMessage> saveMessages(Response response, Session session) {
        List<SessionMessage> list = new ArrayList<>();
        for (Response.Item item : response.getItem()) {
            if (item.getMsgType() == null && item.getMsgNum() == null && item.getMsg() == null)
                continue;

            SessionMessage sessionMessage = new SessionMessage();
            sessionMessage.setSession(session);
            sessionMessage.setObjectCode(objectCode);
            sessionMessage.setObjectId(item.getID());
            sessionMessage.setSapId(item.getIDSAP());
            sessionMessage.setMsgNum(item.getMsgNum());
            sessionMessage.setMsgType(item.getMsgType());
            sessionMessage.setMsg(item.getMsg());
            list.add(sessionMessage);
        }
        return sessionMessageRepo.save(list);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    List<ContractInterface> updateStatuses(List<ContractInterface> list, List<SessionMessage> messages) {
        for (ContractInterface line: list) {
            BatchStatusEnum status = messages.stream()
                .filter(t -> t.getObjectCode().equals(objectCode))
                .filter(t -> t.getObjectId() != null && t.getObjectId().trim().equals(line.getId().toString()))
                .filter(t -> t.getMsgType().equals("S"))
                .map(t -> BatchStatusEnum.C)
                .findFirst()
                .orElse(BatchStatusEnum.E);

            line.setStatus(status);
            line.setLastUpdateDate(LocalDateTime.now());
        }
        return contractInterfaceRepo.save(list);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    List<ContractInterface> updateStatuses(List<ContractInterface> list, Session session) {
        for (ContractInterface line: list) {
            line.setStatus(session.getStatus());
            line.setSession(session);
            line.setLastUpdateDate(LocalDateTime.now());
        }
        return contractInterfaceRepo.save(list);
    }
}
