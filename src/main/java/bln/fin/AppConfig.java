package bln.fin;

import bln.fin.repo.*;
import bln.fin.ws.SessionService;
import bln.fin.ws.client.CustomEndpointInterceptor;
import bln.fin.ws.server.bp.BusinessPartnerServiceImpl;
import bln.fin.ws.server.debt.DebtBusinessService;
import bln.fin.ws.server.debt.DebtServiceImpl;
import bln.fin.ws.server.invoice.InvoiceBusinessService;
import bln.fin.ws.server.invoice.InvoiceServiceImpl;
import bln.fin.ws.server.req.ReqBusinessService;
import bln.fin.ws.server.req.ReqServiceImpl;
import bln.fin.ws.server.saleInvoice.SaleInvoiceServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.RequiredArgsConstructor;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import javax.xml.ws.Endpoint;

@Configuration
@RequiredArgsConstructor
public class AppConfig  {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new ParameterNamesModule());
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new Jdk8Module());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return mapper;
    }

    @Bean
    public WebServiceTemplate salePlanServiceTemplate() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setContextPath("sap.plan");

        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setMarshaller(jaxb2Marshaller);
        webServiceTemplate.setUnmarshaller(jaxb2Marshaller);
        webServiceTemplate.setDefaultUri("http://kegoci10.corp.kegoc.kz:50000/XISOAPAdapter/MessageServlet?senderParty=&senderService=BIS_D&receiverParty=&receiverService=&interface=BIS_SalesPlan&interfaceNamespace=urn:kegoc.kz:BIS:LO_0002_3_SalesPlan");
        webServiceTemplate.setInterceptors(new ClientInterceptor[] {new CustomEndpointInterceptor()});

        return webServiceTemplate;
    }

    @Bean
    public WebServiceTemplate saleInvoiceServiceTemplate() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setContextPath("sap.saleInvoice");

        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setMarshaller(jaxb2Marshaller);
        webServiceTemplate.setUnmarshaller(jaxb2Marshaller);
        webServiceTemplate.setDefaultUri("http://kegoci10.corp.kegoc.kz:50000/XISOAPAdapter/MessageServlet?senderParty=&senderService=BIS_D&receiverParty=&receiverService=&interface=BIS_EstimatedChargeInvoices&interfaceNamespace=urn:kegoc.kz:BIS:LO_0002_1_EstimatedChargeInvoice");

        return webServiceTemplate;
    }

    @Bean
    public WebServiceTemplate saleContractServiceTemplate() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setContextPath("sap.contract.sd");

        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setMarshaller(jaxb2Marshaller);
        webServiceTemplate.setUnmarshaller(jaxb2Marshaller);
        webServiceTemplate.setDefaultUri("http://kegoci10.corp.kegoc.kz:50000/XISOAPAdapter/MessageServlet?senderParty=&senderService=BIS_D&receiverParty=&receiverService=&interface=BIS_LO_Contract&interfaceNamespace=urn:kegoc.kz:BIS:LO_0001_Contract");

        return webServiceTemplate;
    }

    @Bean
    public WebServiceTemplate purchaseContractServiceTemplate() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setContextPath("sap.contract.mm");

        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setMarshaller(jaxb2Marshaller);
        webServiceTemplate.setUnmarshaller(jaxb2Marshaller);
        webServiceTemplate.setDefaultUri("http://kegoci10.corp.kegoc.kz:50000/XISOAPAdapter/MessageServlet?senderParty=&senderService=BIS_D&receiverParty=&receiverService=&interface=BIS_MM_Contract&interfaceNamespace=urn:kegoc.kz:BIS:ZMM_0042_Contract");

        return webServiceTemplate;
    }


    @Bean
    public ServletRegistrationBean dispatcherServlet() {
        return new ServletRegistrationBean(new CXFServlet(), "/services/*");
    }

    @Bean(name=Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        SpringBus springBus = new SpringBus();
        return springBus;
    }

    @Bean
    public Endpoint endpoint1() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), new DebtServiceImpl(debtBusinessService, checkApplicationRepo, receiptApplicationRepo, sessionService));
        endpoint.publish("/DebtService");
        return endpoint;
    }

    @Bean
    public Endpoint endpoint2() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), new InvoiceServiceImpl(invoiceBusinessService, purchaseInvoiceRepo, saleInvoiceRepo, sessionService));
        endpoint.publish("/InvoiceService");
        return endpoint;
    }

    @Bean
    public Endpoint endpoint3() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), new ReqServiceImpl(reqBusinessService, reqLineRepo, sessionService));
        endpoint.publish("/ReqService");
        return endpoint;
    }

    @Bean
    public Endpoint endpoint4() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), new BusinessPartnerServiceImpl(businessPartnerRepo, sessionService));
        endpoint.publish("/BusinessPartnerService");
        return endpoint;
    }

    @Bean
    public Endpoint endpoint5() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), new SaleInvoiceServiceImpl());
        endpoint.publish("/SaleInvoiceService");
        return endpoint;
    }


    @Autowired
    private final ReqLineRepo reqLineRepo;

    @Autowired
    private final CheckApplicationRepo checkApplicationRepo;

    @Autowired
    private final ReceiptApplicationRepo receiptApplicationRepo;

    @Autowired
    private final PurchaseInvoiceRepo purchaseInvoiceRepo;

    @Autowired
    private final SaleInvoiceRepo saleInvoiceRepo;

    @Autowired
    private final BusinessPartnerRepo businessPartnerRepo;

    @Autowired
    private final InvoiceBusinessService invoiceBusinessService;

    @Autowired
    private final ReqBusinessService reqBusinessService;

    @Autowired
    private final DebtBusinessService debtBusinessService;

    @Autowired
    private final SessionService sessionService;
}
