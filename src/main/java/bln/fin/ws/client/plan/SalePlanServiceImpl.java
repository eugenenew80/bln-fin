package bln.fin.ws.client.plan;

import bln.fin.entity.SalePlanHeader;
import bln.fin.entity.SalePlanLine;
import bln.fin.repo.SalePlanHeaderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import sap.erp.plan.ObjectFactory;
import sap.erp.plan.SalesPlan;
import java.math.BigInteger;
import java.util.List;

import static bln.fin.common.Util.toXMLGregorianCalendar;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class SalePlanServiceImpl implements SalePlanService {
    private final SalePlanHeaderRepo salePlanHeaderRepo;
    private final WebServiceTemplate salePlanServiceTemplate;

    @Override
    public void send(Long headerId) {
        SalePlanHeader header = salePlanHeaderRepo.findOne(headerId);
        if (header.getTransferredToErpDate()!=null)
            return;

        List<SalesPlan.Item> items = header.getLines().stream()
            .map(t -> createItem(header, t))
            .filter(t -> t != null)
            .collect(toList());

        SalesPlan salesPlanReq = new ObjectFactory().createSalesPlan();
        salesPlanReq.getItem().addAll(items);
        salePlanServiceTemplate.marshalSendAndReceive(salesPlanReq);
    }

    private SalesPlan.Item createItem(SalePlanHeader header, SalePlanLine line) {
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
}
