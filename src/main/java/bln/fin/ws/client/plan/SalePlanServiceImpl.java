package bln.fin.ws.client.plan;

import bln.fin.entity.SalePlanHeader;
import bln.fin.entity.SalePlanLine;
import bln.fin.repo.SalePlanHeaderRepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;
import sap.plan.ObjectFactory;
import sap.plan.SalesPlan;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import static bln.fin.common.Util.toXMLGregorianCalendar;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

@Service
@RequiredArgsConstructor
public class SalePlanServiceImpl implements SalePlanService {
    private final SalePlanHeaderRepo salePlanHeaderRepo;
    private final WebServiceTemplate salePlanServiceTemplate;
    private static final int groupCount = 100;
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
        List<SalesPlan.Item> items = createItems(headers);
        if (items.isEmpty())
            return;

        int i = 0;
        for (List<SalesPlan.Item> groupItems : splitItems(items)) {
            i++;
            logger.info("Batch started: " + i);

            SalesPlan salesPlanReq = new ObjectFactory().createSalesPlan();
            salesPlanReq.getItem().addAll(groupItems);

            try {
                Object response = salePlanServiceTemplate.marshalSendAndReceive(salesPlanReq);
                updateHeaders(headers);
            }
            catch (SoapFaultClientException e) {
                System.out.println("Fault Code: " + e.getFaultCode());
                System.out.println("Fault Reason: " + e.getFaultStringOrReason());
                System.out.println("Message: " + e.getLocalizedMessage());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                logger.info("Batch completed: " + i);
            }
        }
    }

    private List<SalesPlan.Item> createItems(List<SalePlanHeader> headers) {
        return headers
            .stream()
            .filter(t -> t.getTransferredToErpDate()==null)
            .flatMap(t -> t.getLines().stream())
            .map(t -> createItem(t))
            .filter(t -> t != null)
            .collect(toList());
    }

    private SalesPlan.Item createItem(SalePlanLine line) {
        SalePlanHeader header = line.getHeader();
        SalesPlan.Item item = new SalesPlan.Item();
        item.setCurrency(header.getCurrencyCode());
        item.setVersion(new BigInteger(header.getVersion().toString()));
        item.setForecastType(header.getForecastType().toShortString());
        item.setQuantity(line.getQuantity());
        item.setAmount(line.getAmount());
        item.setStartDate(toXMLGregorianCalendar(header.getStartDate()));
        item.setEndDate(toXMLGregorianCalendar(header.getEndDate()));
        return item;
    }

    private List<List<SalesPlan.Item>> splitItems(List<SalesPlan.Item> list) {
        return range(0, list.size())
            .boxed()
            .collect(groupingBy(index -> index / groupCount))
            .values()
            .stream()
            .map(indices -> indices
                .stream()
                .map(list::get)
                .collect(toList()))
            .collect(toList());
    }

    private void updateHeaders(List<SalePlanHeader> headers) {
        for (SalePlanHeader header: headers)
            header.setTransferredToErpDate(LocalDateTime.now());
        salePlanHeaderRepo.save(headers);
    }
}
