package bln.fin.ws.client.plan;

import bln.fin.entity.pi.Session;
import bln.fin.entity.enums.BatchStatusEnum;
import bln.fin.entity.enums.DirectionEnum;
import bln.fin.entity.pi.SalePlanInterface;
import bln.fin.entity.pi.SessionMessage;
import bln.fin.repo.SalePlanInterfaceRepo;
import bln.fin.repo.SessionMessageRepo;
import bln.fin.ws.SessionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;
import sap.plan.ObjectFactory;
import sap.plan.Response;
import sap.plan.SalesPlan;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static bln.fin.common.Util.toXMLGregorianCalendar;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class SalePlanServiceImpl implements SalePlanService {
    private static final Logger logger = LoggerFactory.getLogger(SalePlanService.class);
    private static final String objectCode = "SalePlan";
    private final WebServiceTemplate salePlanServiceTemplate;
    private final SalePlanInterfaceRepo salePlanInterfaceRepo;
    private final SessionService sessionService;
    private final SessionMessageRepo sessionMessageRepo;

    @Override
    public void send() {
        List<SalePlanInterface> list = salePlanInterfaceRepo.findAllByStatus(BatchStatusEnum.W);
        if (list.isEmpty()) return;

        logger.info("started");
        Session session = sessionService.createSession(objectCode, DirectionEnum.EXPORT);
        List<SalesPlan.Item> items = createSalePlanItems(list);

        SalesPlan salesPlanReq = new ObjectFactory().createSalesPlan();
        salesPlanReq.getItem().addAll(items);
        debugRequest(items);

        try {
            QName qName = new QName("urn:kegoc.kz:BIS:LO_0002_3_SalesPlan", "SalesPlan");
            JAXBElement<SalesPlan> root = new JAXBElement<>(qName, SalesPlan.class, salesPlanReq);

            JAXBElement<Response> response = (JAXBElement<Response>) salePlanServiceTemplate.marshalSendAndReceive(root);
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


    private List<SalesPlan.Item> createSalePlanItems(List<SalePlanInterface> list) {
        return list
            .stream()
            .map(t -> createSalePlanItem(t))
            .filter(t -> t != null)
            .collect(toList());
    }

    private SalesPlan.Item createSalePlanItem(SalePlanInterface line) {
        logger.debug("Creating item:: id = " + line.getId());
        SalesPlan.Item item = new SalesPlan.Item();
        item.setId(line.getId());
        item.setItemNum(line.getItemNum());
        item.setVersion(new BigInteger(line.getVersion().toString()));
        item.setForecastType(line.getForecastType());
        item.setStartDate(toXMLGregorianCalendar(line.getStartDate()));
        item.setEndDate(toXMLGregorianCalendar(line.getEndDate()));
        item.setQuantity(line.getQuantity());
        item.setAmount(line.getAmount());
        item.setCurrency(line.getCurrency());
        item.setCompanyCode(line.getCompanyCode());
        item.setChannel(line.getChannel());
        logger.debug("Creating item successfully completed");
        return item;
    }

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

    private List<SalePlanInterface> updateStatuses(List<SalePlanInterface> list, List<SessionMessage> messages, Session session) {
        for (SalePlanInterface line: list) {
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
        return salePlanInterfaceRepo.save(list);
    }

    private void debugRequest(List<SalesPlan.Item> list) {
        logger.debug("---------------------------------");
        for (SalesPlan.Item line: list) logger.debug(line.toString());
        logger.debug("---------------------------------");
    }
}
