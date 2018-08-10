package bln.fin.ws.client.plan;

import bln.fin.entity.SalePlanHeader;
import bln.fin.entity.SalePlanLine;
import bln.fin.entity.SoapSession;
import bln.fin.entity.enums.DirectionEnum;
import bln.fin.repo.SalePlanHeaderRepo;
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
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import static bln.fin.common.Util.toXMLGregorianCalendar;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class SalePlanServiceImpl implements SalePlanService {
    private final SalePlanHeaderRepo salePlanHeaderRepo;
    private final WebServiceTemplate salePlanServiceTemplate;
    private final SessionService sessionService;
    private static final Logger logger = LoggerFactory.getLogger(SalePlanServiceImpl.class);

    @Override
    public void send(Long headerId) {
        List<SalePlanHeader> headers = Arrays.asList(salePlanHeaderRepo.findOne(headerId));
        request(headers);
    }

    @Override
    public void sendAll() {
        List<SalePlanHeader> headers = salePlanHeaderRepo.findAll();
        request(headers);
    }

    private void request(List<SalePlanHeader> headers) {
        List<SalesPlan.Item> items = mapToSalePlanItems(headers);
        if (items.isEmpty())
            return;

        SoapSession session = sessionService.createSession("SalePlan", DirectionEnum.EXPORT);
        logger.info("Request started");

        SalesPlan salesPlanReq = new ObjectFactory().createSalesPlan();
        salesPlanReq.getItem().addAll(items);

        try {
            JAXBElement<Response> response = (JAXBElement<Response>) salePlanServiceTemplate.marshalSendAndReceive(salesPlanReq);
            updateHeaders(headers, response.getValue());
            sessionService.successSession(session, (long) items.size());
        }

        catch (SoapFaultClientException e) {
            logger.error("Fault Code: " + e.getFaultCode());
            logger.error("Fault Reason: " + e.getFaultStringOrReason());
            sessionService.errorSession(session, e);
        }

        catch (Exception e) {
            e.printStackTrace();
            sessionService.errorSession(session, e);
        }
        logger.info("Request completed");
    }

    private List<SalesPlan.Item> mapToSalePlanItems(List<SalePlanHeader> headers) {
        return headers
            .stream()
            .filter(t -> t.getTransferredToErpDate()==null)
            .flatMap(t -> t.getLines().stream())
            .map(t -> mapToSalePlanItem(t))
            .filter(t -> t != null)
            .collect(toList());
    }

    private SalesPlan.Item mapToSalePlanItem(SalePlanLine line) {
        SalePlanHeader header = line.getHeader();
        SalesPlan.Item item = new SalesPlan.Item();

        item.setId(line.getId());
        item.setItemNum(line.getItem().getErpCode());
        item.setVersion(new BigInteger(header.getVersion().toString()));
        item.setForecastType(header.getForecastType().toShortString());
        item.setStartDate(toXMLGregorianCalendar(header.getStartDate()));
        item.setEndDate(toXMLGregorianCalendar(header.getEndDate()));
        item.setQuantity(line.getQuantity());
        item.setAmount(line.getAmount());
        item.setCurrency(header.getCurrencyCode());
        item.setCompanyCode("1010");
        item.setChannel("10");

        return item;
    }

    private void updateHeaders(List<SalePlanHeader> headers, Response response) {




        for (SalePlanHeader header: headers) {
            Response.Item status = getStatus(header, response);
            if (status.getMsgType().equals("S")) {
                header.setTransferredToErpDate(LocalDateTime.now());
                header.setErpId(status.getIDSAP().trim());
            }
            header.setTransferredStatus(status.getMsgType());
            header.setTransferredText(status.getMsg());
        }
        salePlanHeaderRepo.save(headers);
    }

    private Response.Item getStatus(SalePlanHeader header, Response response) {
        return response.getItem().get(0);
    }
}
