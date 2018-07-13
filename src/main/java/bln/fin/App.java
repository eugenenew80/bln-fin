package bln.fin;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;
import sap.erp.plan.ObjectFactory;
import sap.erp.plan.SalesPlan;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.io.IOException;
import java.math.BigInteger;
import java.util.GregorianCalendar;

@EntityScan(
    basePackageClasses = { App.class, Jsr310JpaConverters.class }
)
@EnableScheduling
@SpringBootApplication
public class App  {

    public static void main(String[] args) throws DatatypeConfigurationException {
        String username = "PIAPPLBIS_D";
        String password = "Qwer!11111";

        HttpRequestInterceptor httpRequestInterceptor = new HttpRequestInterceptor() {
            @Override
            public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
                httpRequest.removeHeaders(HTTP.CONTENT_LEN);
            }
        };

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

        RequestConfig requestConfig = RequestConfig.custom()
            .setAuthenticationEnabled(true)
            .build();

        CloseableHttpClient httpClient = HttpClients.custom()
            .addInterceptorFirst(httpRequestInterceptor)
            .setDefaultRequestConfig(requestConfig)
            .setDefaultCredentialsProvider(credentialsProvider)
            .build();

        HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender(httpClient);

        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setContextPath("sap.erp.plan");

        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setMarshaller(jaxb2Marshaller);
        webServiceTemplate.setUnmarshaller(jaxb2Marshaller);
        webServiceTemplate.setMessageSender(messageSender);
        webServiceTemplate.setDefaultUri("http://kegoci10.corp.kegoc.kz:50000/XISOAPAdapter/MessageServlet?senderParty=&senderService=BIS_D&receiverParty=&receiverService=&interface=SalesPlan&interfaceNamespace=urn:kegoc.kz:BIS:LO_0002_3_SalesPlan");


        ObjectFactory factory = new ObjectFactory();

        SalesPlan.Item item = new SalesPlan.Item();
        item.setAmount(123d);
        item.setCurrency("KZT");
        item.setForecastType("O");
        item.setQuantity(2);
        item.setVersion(new BigInteger("1"));

        GregorianCalendar startDate = new GregorianCalendar();
        startDate.set(2018, 1, 1);
        item.setStartDate( DatatypeFactory.newInstance().newXMLGregorianCalendar(startDate));

        GregorianCalendar endDate = new GregorianCalendar();
        startDate.set(2018, 1, 31);
        item.setEndDate( DatatypeFactory.newInstance().newXMLGregorianCalendar(endDate));

        SalesPlan salesPlanReq = factory.createSalesPlan();
        salesPlanReq.getItem().add(item);

        webServiceTemplate.marshalSendAndReceive(salesPlanReq);

        SpringApplication.run(App.class, args);
    }
}
