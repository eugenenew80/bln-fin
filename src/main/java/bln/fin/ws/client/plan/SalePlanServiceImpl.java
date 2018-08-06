package bln.fin.ws.client.plan;

import bln.fin.entity.SalePlanHeader;
import bln.fin.entity.SalePlanLine;
import bln.fin.repo.SalePlanHeaderRepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.springframework.ws.client.core.WebServiceTemplate;

import sap.plan.ObjectFactory;
import sap.plan.SalesPlan;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import static bln.fin.common.Util.toXMLGregorianCalendar;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

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

        logger.info("Batch started" );

        SalesPlan salesPlanReq = new ObjectFactory().createSalesPlan();
        salesPlanReq.getItem().addAll(items);

        StringBuffer response = new StringBuffer();
        HttpURLConnection con = null;
        try {
            con = getConnection(new URL("http://kegoci10.corp.kegoc.kz:50000/XISOAPAdapter/MessageServlet?senderParty=&senderService=BIS_D&receiverParty=&receiverService=&interface=BIS_SalesPlan&interfaceNamespace=urn:kegoc.kz:BIS:LO_0002_3_SalesPlan"));
            String body = getBody(salesPlanReq);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(body.getBytes());
                wr.flush();
            }

            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String output;
                while ((output = in.readLine()) != null) {
                    response.append(output);
                }
            }
            System.out.println(response.toString());

            logger.info("doRequest successfully completed");
        }

        catch (IOException e) {
            logger.error("doRequest failed: " + e);
        }

        finally {
            if (con!=null) con.disconnect();
        }


        /*
        try {
            Object response = salePlanServiceTemplate.marshalSendAndReceive(salesPlanReq, new WebServiceMessageCallback() {

                @Override
                public void doWithMessage(WebServiceMessage webServiceMessage) throws IOException, TransformerException {
                    TransportContext context = TransportContextHolder.getTransportContext();
                    HttpComponentsConnection connection = (HttpComponentsConnection) context.getConnection();


                    Header[] allHeaders = connection.getHttpPost().getAllHeaders();
                    for (int i = 0; i<allHeaders.length; i++) {
                        System.out.println(allHeaders[i].getElements()[0].getName());
                        System.out.println(allHeaders[i].getElements()[0].getValue());
                    }
                }
            } );
            //updateHeaders(headers);
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
        */
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


    private void updateHeaders(List<SalePlanHeader> headers) {
        for (SalePlanHeader header: headers)
            header.setTransferredToErpDate(LocalDateTime.now());
        salePlanHeaderRepo.save(headers);
    }



    private HttpURLConnection getConnection(URL url) {
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) url.openConnection();
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
            con.setRequestProperty("Authorization", "Basic UElBUFBMQklTX0Q6UXdlciExMTExMQ==");
            return con;
        }

        catch (IOException e) {
            logger.error("getConnection failed: " + e);
        }

        return null;
    }

    private String getBody(SalesPlan salesPlan) {
        String bodyTemplate = new String(envelopeTemplate);
        for (SalesPlan.Item item : salesPlan.getItem()) {
            String lineTemplate = new String(this.itemTemplate);
            itemTemplate = itemTemplate.replace("#id#", item.getId() + "");
            itemTemplate = itemTemplate.replace("#itemNum#", item.getItemNum());
            itemTemplate = itemTemplate.replace("#version#", item.getVersion() + "");
            itemTemplate = itemTemplate.replace("#forecastType#", item.getForecastType());
            itemTemplate = itemTemplate.replace("#startDate#", item.getStartDate().toXMLFormat());
            itemTemplate = itemTemplate.replace("#endDate#", item.getEndDate().toXMLFormat());
            itemTemplate = itemTemplate.replace("#amount#", item.getAmount() + "");
            itemTemplate = itemTemplate.replace("#quantity#", item.getQuantity() + "");
            itemTemplate = itemTemplate.replace("#currencyCode#", item.getCurrency());
            itemTemplate = itemTemplate.replace("#companyCode#", item.getCompanyCode());
            itemTemplate = itemTemplate.replace("#channel#", item.getChannel());
            bodyTemplate = bodyTemplate.replace("#items#", lineTemplate);
        }

        return bodyTemplate;
    }


    private String envelopeTemplate = "" +
        "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelopeTemplate/\" xmlns:urn=\"urn:kegoc.kz:BIS:LO_0002_3_SalesPlan\">\n" +
        "   <soapenv:Header/>\n" +
        "   <soapenv:Body>\n" +
        "      <urn:SalesPlan>\n" +
        "           #items#" +
        "     </urn:SalesPlan>\n" +
        "   </soapenv:Body>\n" +
        "</soapenv:Envelope>";

    private String itemTemplate = "" +
        "          <item>\n" +
        "            <id>#id#</id>\n" +
        "            <itemNum>#itemNum#</itemNum>\n" +
        "            <version>#version#</version>\n" +
        "            <forecastType>#forecastType#</forecastType>\n" +
        "            <startDate>#startDate#</startDate>\n" +
        "            <endDate>#endDate#</endDate>\n" +
        "            <quantity>#quantity#</quantity>\n" +
        "            <amount>#amount#</amount>\n" +
        "            <currency>#currencyCode#</currency>\n" +
        "            <companyCode>#companyCode#</companyCode>\n" +
        "            <channel>#channel#</channel>\n" +
        "         </item>\n";
}
