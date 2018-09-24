package bln.fin;

import bln.fin.repo.*;
import bln.fin.ws.SessionService;
import bln.fin.ws.client.CustomEndpointInterceptor;
import bln.fin.ws.server.bp.BusinessPartnerServiceImpl;
import bln.fin.ws.server.debt.DebtServiceImpl;
import bln.fin.ws.server.invoice.InvoiceServiceImpl;
import bln.fin.ws.server.req.ReqServiceImpl;
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
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import javax.xml.ws.Endpoint;
import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class AppConfig  {
    private final String purchaseContractUrl = "http://kegoci10.corp.kegoc.kz:50000/XISOAPAdapter/MessageServlet?senderParty=&senderService=BIS_D&receiverParty=&receiverService=&interface=BIS_MM_Contract&interfaceNamespace=urn:kegoc.kz:BIS:ZMM_0042_Contract";
    private final String saleContractUrl     = "http://kegoci10.corp.kegoc.kz:50000/XISOAPAdapter/MessageServlet?senderParty=&senderService=BIS_D&receiverParty=&receiverService=&interface=BIS_LO_Contract&interfaceNamespace=urn:kegoc.kz:BIS:LO_0001_Contract";
    private final String saleInvoiceRevUrl   = "http://kegoci10.corp.kegoc.kz:50000/XISOAPAdapter/MessageServlet?senderParty=&senderService=BIS_D&receiverParty=&receiverService=&interface=BIS_ReversedInvoice&interfaceNamespace=urn:kegoc.kz:BIS:LO_0002_4_ReversedInvoice";
    private final String saleInvoiceUrl      = "http://kegoci10.corp.kegoc.kz:50000/XISOAPAdapter/MessageServlet?senderParty=&senderService=BIS_D&receiverParty=&receiverService=&interface=BIS_EstimatedChargeInvoices&interfaceNamespace=urn:kegoc.kz:BIS:LO_0002_1_EstimatedChargeInvoice";
    private final String salePlanUrl         = "http://kegoci10.corp.kegoc.kz:50000/XISOAPAdapter/MessageServlet?senderParty=&senderService=BIS_D&receiverParty=&receiverService=&interface=BIS_SalesPlan&interfaceNamespace=urn:kegoc.kz:BIS:LO_0002_3_SalesPlan";

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
    public DozerBeanMapper dozerBeanMapper() {
        DozerBeanMapper mapper = new DozerBeanMapper();
        mapper.setMappingFiles(Arrays.asList(
            "dozer/MappingConfig.xml",
            "dozer/ReqLineDto.xml",
            "dozer/DebtDto.xml",
            "dozer/InvoiceStatusDto.xml",
            "dozer/InvoiceDto.xml",
            "dozer/InvoiceLineDto.xml",
            "dozer/AddressDto.xml",
            "dozer/AddressTranslateDto.xml",
            "dozer/BankAccountDto.xml",
            "dozer/RelationDto.xml",
            "dozer/BusinessPartnerDto.xml",
            "dozer/BusinessPartnerTranslateDto.xml"
        ));
        return mapper;
    }

    @Bean
    public WebServiceTemplate salePlanServiceTemplate() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setContextPath("sap.plan");

        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setMarshaller(jaxb2Marshaller);
        webServiceTemplate.setUnmarshaller(jaxb2Marshaller);
        webServiceTemplate.setDefaultUri(salePlanUrl);
        webServiceTemplate.setInterceptors(new ClientInterceptor[] {new CustomEndpointInterceptor()});

        return webServiceTemplate;
    }

    @Bean
    public WebServiceTemplate saleInvoiceServiceTemplate() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setContextPath("sap.invoice");

        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setMarshaller(jaxb2Marshaller);
        webServiceTemplate.setUnmarshaller(jaxb2Marshaller);
        webServiceTemplate.setDefaultUri(saleInvoiceUrl);
        webServiceTemplate.setInterceptors(new ClientInterceptor[] {new CustomEndpointInterceptor()});

        return webServiceTemplate;
    }

    @Bean
    public WebServiceTemplate saleInvoiceRevServiceTemplate() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setContextPath("sap.invoiceRev");

        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setMarshaller(jaxb2Marshaller);
        webServiceTemplate.setUnmarshaller(jaxb2Marshaller);
        webServiceTemplate.setDefaultUri(saleInvoiceRevUrl);
        webServiceTemplate.setInterceptors(new ClientInterceptor[] {new CustomEndpointInterceptor()});

        return webServiceTemplate;
    }

    @Bean
    public WebServiceTemplate saleContractServiceTemplate() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setContextPath("sap.contract.sd");

        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setMarshaller(jaxb2Marshaller);
        webServiceTemplate.setUnmarshaller(jaxb2Marshaller);
        webServiceTemplate.setDefaultUri(saleContractUrl);
        webServiceTemplate.setInterceptors(new ClientInterceptor[] {new CustomEndpointInterceptor()});

        return webServiceTemplate;
    }

    @Bean
    public WebServiceTemplate purchaseContractServiceTemplate() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setContextPath("sap.contract.mm");

        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setMarshaller(jaxb2Marshaller);
        webServiceTemplate.setUnmarshaller(jaxb2Marshaller);
        webServiceTemplate.setDefaultUri(purchaseContractUrl);
        webServiceTemplate.setInterceptors(new ClientInterceptor[] {new CustomEndpointInterceptor()});

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
        EndpointImpl endpoint = new EndpointImpl(springBus(), new DebtServiceImpl(debtInterfaceRepo, sessionService, dozerBeanMapper()));
        endpoint.publish("/DebtService");
        return endpoint;
    }

    @Bean
    public Endpoint endpoint2() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), new InvoiceServiceImpl(invoiceInterfaceRepo, invoiceStatusInterfaceRepo, sessionService, dozerBeanMapper()));
        endpoint.publish("/InvoiceService");
        return endpoint;
    }

    @Bean
    public Endpoint endpoint3() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), new ReqServiceImpl(reqLineInterfaceRepo, sessionService, dozerBeanMapper()));
        endpoint.publish("/ReqService");
        return endpoint;
    }

    @Bean
    public Endpoint endpoint4() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), new BusinessPartnerServiceImpl(bpInterfaceRepo, bpRelationInterfaceRepo, sessionService, dozerBeanMapper()));
        endpoint.publish("/BusinessPartnerService");
        return endpoint;
    }


    @Autowired
    private final SessionService sessionService;

    @Autowired
    private final ReqLineInterfaceRepo reqLineInterfaceRepo;

    @Autowired
    private final DebtInterfaceRepo debtInterfaceRepo;

    @Autowired
    private final InvoiceInterfaceRepo invoiceInterfaceRepo;

    @Autowired
    private final InvoiceStatusInterfaceRepo invoiceStatusInterfaceRepo;

    @Autowired
    private final BpInterfaceRepo bpInterfaceRepo;

    @Autowired
    private final BpRelationInterfaceRepo bpRelationInterfaceRepo;
}
