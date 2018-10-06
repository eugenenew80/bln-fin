package bln.fin.ws.client.plan;

import bln.fin.entity.pi.*;
import bln.fin.entity.enums.BatchStatusEnum;
import bln.fin.entity.enums.DirectionEnum;
import bln.fin.repo.SalePlanInterfaceRepo;
import bln.fin.repo.SessionMessageRepo;
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
import sap.plan.ObjectFactory;
import sap.plan.Response;
import sap.plan.SalesPlan;
import javax.xml.bind.JAXBElement;
import java.time.LocalDateTime;
import java.util.List;
import static bln.fin.common.Util.getSuccessMessage;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class SalePlanClientServiceImpl implements SalePlanClientService {
    private static final Logger logger = LoggerFactory.getLogger(SalePlanClientService.class);
    private static final String objectCode = "SALE_PLAN";
    private final WebServiceTemplate salePlanServiceTemplate;
    private final SalePlanInterfaceRepo salePlanInterfaceRepo;
    private final SessionService sessionService;
    private final SessionMessageRepo sessionMessageRepo;
    private final DozerBeanMapper mapper;

    @Override
    public void send() {
        List<SalePlanInterface> list = salePlanInterfaceRepo.findAllByStatus(BatchStatusEnum.W);
        send(list);
    }

    private void send(List<SalePlanInterface> list) {
        if (list.isEmpty()) return;
        logger.info("started");

        Session session = sessionService.createSession(objectCode, DirectionEnum.EXPORT);
        try {
            list = updateStatuses(list, session);
            JAXBElement<SalesPlan> request = createRequest(list);
            JAXBElement<Response> response = (JAXBElement<Response>) salePlanServiceTemplate.marshalSendAndReceive(request);

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
            logger.debug("Error during request: " + e.getMessage());
            sessionService.errorSession(session, e);
            updateStatuses(list, session);
        }
        logger.info("completed");
    }

    private JAXBElement<SalesPlan> createRequest(List<SalePlanInterface> list) {
        List<SalesPlan.Item> items = list.stream()
            .map(this::mapItem)
            .filter(t -> t != null)
            .collect(toList());
        debugRequest(items);

        SalesPlan salesPlanDto = new SalesPlan();
        salesPlanDto.getItem().addAll(items);
        return new ObjectFactory().createSalesPlan(salesPlanDto);
    }

    private SalesPlan.Item mapItem(SalePlanInterface plan) {
        return mapper.map(plan, SalesPlan.Item.class);
    }

    private void debugRequest(List<SalesPlan.Item> list) {
        logger.debug("---------------------------------");
        for (SalesPlan.Item line: list) logger.debug(line.toString());
        logger.debug("---------------------------------");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected List<SessionMessage> saveResponse(JAXBElement<Response> response, Session session) {
        List<SessionMessage> list = response.getValue()
            .getItem()
            .stream()
            .filter(t -> t.getMsgType() == null && t.getMsgNum() == null && t.getMsg() == null)
            .map(t -> {
                SessionMessage msg = new SessionMessage(objectCode, session);
                mapper.map(t, msg);
                return msg;
            })
            .collect(toList());

        return sessionMessageRepo.save(list);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected List<SalePlanInterface> updateStatuses(List<SalePlanInterface> list, List<SessionMessage> messages, Session session) {
        for (SalePlanInterface line: list) {
            SessionMessage msg = getSuccessMessage(messages, line.getId().toString());
            line.setStatus(msg != null ? BatchStatusEnum.C : BatchStatusEnum.E);
            line.setSession(session);
            line.setLastUpdateDate(LocalDateTime.now());
        }
        return salePlanInterfaceRepo.save(list);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected List<SalePlanInterface> updateStatuses(List<SalePlanInterface> list, Session session) {
        for (SalePlanInterface line: list) {
            line.setStatus(session.getStatus());
            line.setSession(session);
            line.setLastUpdateDate(LocalDateTime.now());
        }
        return salePlanInterfaceRepo.save(list);
    }
}
