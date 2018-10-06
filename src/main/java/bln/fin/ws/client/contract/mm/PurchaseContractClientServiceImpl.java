package bln.fin.ws.client.contract.mm;

import bln.fin.entity.enums.BatchStatusEnum;
import bln.fin.entity.enums.DirectionEnum;
import bln.fin.entity.pi.ContractInterface;
import bln.fin.entity.pi.ContractLineInterface;
import bln.fin.entity.pi.Session;
import bln.fin.entity.pi.SessionMessage;
import bln.fin.repo.ContractInterfaceRepo;
import bln.fin.repo.SessionMessageRepo;
import bln.fin.ws.SessionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;
import sap.contract.mm.Contract;
import sap.contract.mm.ObjectFactory;
import sap.contract.mm.Response;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.toList;

@SuppressWarnings("ALL")
@Service
@RequiredArgsConstructor
public class PurchaseContractClientServiceImpl implements PurchaseContractClientService {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseContractClientService.class);
    private static final String objectCode = "PURCHASE_CONTRACT";
    private final ContractInterfaceRepo contractInterfaceRepo;
    private final WebServiceTemplate purchaseContractServiceTemplate;
    private final SessionService sessionService;
    private final SessionMessageRepo sessionMessageRepo;

    @Override
    public void send() {
        List<ContractInterface> list = contractInterfaceRepo.findAllByStatusAndBpType(BatchStatusEnum.W, "K");
        if (list.isEmpty()) return;

        logger.info("started");

        Session session = sessionService.createSession(objectCode, DirectionEnum.EXPORT);
        List<Contract.Item> items = createContractItems(list);

        Contract contractReq = new ObjectFactory().createContract();
        contractReq.getItem().addAll(items);
        debugRequest(items);

        try {
            QName qName = new QName("urn:kegoc.kz:BIS:ZMM_0042_Contract", "Contract");
            JAXBElement<Contract> root = new JAXBElement<>(qName, Contract.class, contractReq);

            JAXBElement<Response> response = (JAXBElement<Response>) purchaseContractServiceTemplate.marshalSendAndReceive(root);
            List<SessionMessage> messages = saveMessages(response.getValue(), session);
            updateStatuses(list, messages, session);
            sessionService.successSession(session, (long) items.size());
        }

        catch (SoapFaultClientException e) {
            logger.error("Fault Code: " + e.getFaultCode());
            logger.error("Fault Reason: " + e.getFaultStringOrReason());
            sessionService.errorSession(session, e);
        }

        catch (Exception e) {
            logger.debug("Error during request: " + e.getMessage());
            sessionService.errorSession(session, e);
        }
        logger.info("completed");
    }


    private List<Contract.Item> createContractItems(List<ContractInterface> list) {
        return list
            .stream()
            .map(t -> createContractItem(t))
            .filter(t -> t != null)
            .collect(toList());
    }

    private Contract.Item createContractItem(ContractInterface line) {
        logger.debug("Creating item:: id = " + line.getId());
        Contract.Item item = new Contract.Item();
        item.setId(line.getId());
        item.setContractNum(line.getContractNum());
        item.setExtContractNum(line.getExtContractNum());
        item.setCompanyCode(line.getCompanyCode());
        item.setAmount(line.getAmount());
        item.setCurrencyCode(line.getCurrencyCode());
        for (ContractLineInterface contractLine : line.getLines()) {
            Contract.Item.Row row = new Contract.Item.Row();
            row.setUnit(contractLine.getUnit());
            row.setQuantity(contractLine.getQuantity());
            row.setPrice(contractLine.getPrice());
            item.getRow().add(row);
        }
        logger.debug("Creating item successfully completed");
        return item;
    }

    @SuppressWarnings("Duplicates")
    private List<SessionMessage> saveMessages(Response response, Session session) {
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

    private List<ContractInterface> updateStatuses(List<ContractInterface> list, List<SessionMessage> messages, Session session) {
        for (ContractInterface line: list) {
            SessionMessage message = messages.stream()
                .filter(t -> t.getObjectCode().equals(objectCode))
                .filter(t -> t.getObjectId()!=null && t.getObjectId().trim().equals(line.getId().toString()))
                .filter(t -> t.getMsgType().equals("S"))
                .findFirst()
                .orElse(null);

            if (message != null)
                line.setStatus(BatchStatusEnum.C);
            else
                line.setStatus(BatchStatusEnum.E);

            line.setLastUpdateDate(LocalDateTime.now());
            line.setSession(session);
        }
        return contractInterfaceRepo.save(list);
    }

    private void debugRequest(List<Contract.Item> list) {
        logger.debug("---------------------------------");
        for (Contract.Item line: list) logger.debug(line.toString());
        logger.debug("---------------------------------");
    }
}
