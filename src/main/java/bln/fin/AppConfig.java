package bln.fin;

import bln.fin.repo.*;
import bln.fin.ws.SessionService;
import bln.fin.ws.client.CustomClientInterceptor;
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
import org.apache.cxf.ext.logging.LoggingInInterceptor;
import org.apache.cxf.ext.logging.LoggingOutInterceptor;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.xml.ws.Endpoint;
import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class AppConfig  {
    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);
    private final SessionService sessionService;
    private final ReqLineInterfaceRepo reqLineInterfaceRepo;
    private final DebtInterfaceRepo debtInterfaceRepo;
    private final InvoiceInterfaceRepo invoiceInterfaceRepo;
    private final InvoiceStatusInterfaceRepo invoiceStatusInterfaceRepo;
    private final BpInterfaceRepo bpInterfaceRepo;
    private final BpRelationInterfaceRepo bpRelationInterfaceRepo;

    @Value("${pi.security.user.name}")
    private String userName;

    @Value("${pi.security.user.password}")
    private String password;

    @Value("${bems.security.user.name}")
    private String bemsUserName;

    @Value("${bems.security.user.password}")
    private String bemsPassword;

    @Value("${pi.sale_contract.url}")
    private String saleContractUrl;

    @Value("${pi.sale_invoice.url}")
    private String saleInvoiceUrl;

    @Value("${pi.sale_invoice_rev.url}")
    private String saleInvoiceRevUrl;

    @Value("${pi.purchase_contract.url}")
    private String purchaseContractUrl;

    @Value("${pi.sale_plan.url}")
    private String salePlanUrl;

    @Value("${bems.url}")
    private String bemsUrl;


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
    public Validator validator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator;
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
                "dozer/BusinessPartnerTranslateDto.xml",
                "dozer/SaleContractDto.xml",
                "dozer/SaleContractLineDto.xml",
                "dozer/SaleContractResponseDto.xml",
                "dozer/SaleInvoiceDto.xml",
                "dozer/SaleInvoiceLineDto.xml",
                "dozer/SaleInvoiceResponseDto.xml",
                "dozer/SalePlanDto.xml",
                "dozer/SalePlanResponseDto.xml",
                "dozer/SaleInvoiceRevDto.xml",
                "dozer/SaleInvoiceRevResponseDto.xml",
                "dozer/PurchaseContractDto.xml",
                "dozer/PurchaseContractLineDto.xml",
                "dozer/PurchaseContractResponseDto.xml"
        ));
        return mapper;
    }

    @Bean
    public WebServiceTemplate bemsServiceTemplate() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setContextPath("bems");

        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setMarshaller(jaxb2Marshaller);
        webServiceTemplate.setUnmarshaller(jaxb2Marshaller);
        webServiceTemplate.setDefaultUri(bemsUrl);
        webServiceTemplate.setInterceptors(new ClientInterceptor[] {new CustomClientInterceptor(bemsUserName, bemsPassword)});
        return webServiceTemplate;
    }

    @Bean
    public WebServiceTemplate salePlanServiceTemplate() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setContextPath("sap.plan");

        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setMarshaller(jaxb2Marshaller);
        webServiceTemplate.setUnmarshaller(jaxb2Marshaller);
        webServiceTemplate.setDefaultUri(salePlanUrl);
        webServiceTemplate.setInterceptors(new ClientInterceptor[] {new CustomClientInterceptor(userName, password)});
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
        webServiceTemplate.setInterceptors(new ClientInterceptor[] {new CustomClientInterceptor(userName, password)});
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
        webServiceTemplate.setInterceptors(new ClientInterceptor[] {new CustomClientInterceptor(userName, password)});
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
        webServiceTemplate.setInterceptors(new ClientInterceptor[] {new CustomClientInterceptor(userName, password)});
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
        webServiceTemplate.setInterceptors(new ClientInterceptor[] {new CustomClientInterceptor(userName, password)});
        return webServiceTemplate;
    }

    @Bean
    public ServletRegistrationBean dispatcherServlet() {
        return new ServletRegistrationBean(new CXFServlet(), "/services/*");
    }

    @Bean(name=Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        SpringBus springBus = new SpringBus();
        springBus.getInInterceptors().add(new LoggingInInterceptor());
        springBus.getOutInterceptors().add(new LoggingOutInterceptor());
        return springBus;
    }

    @Bean
    public Endpoint endpoint1(DozerBeanMapper dozerBeanMapper) {
        EndpointImpl endpoint = new EndpointImpl(springBus(), new DebtServiceImpl(debtInterfaceRepo, sessionService, dozerBeanMapper));
        endpoint.publish("/DebtService");
        return endpoint;
    }

    @Bean
    public Endpoint endpoint2(DozerBeanMapper dozerBeanMapper) {
        EndpointImpl endpoint = new EndpointImpl(springBus(), new InvoiceServiceImpl(invoiceInterfaceRepo, invoiceStatusInterfaceRepo, sessionService, dozerBeanMapper));
        endpoint.publish("/InvoiceService");
        return endpoint;
    }

    @Bean
    public Endpoint endpoint3(DozerBeanMapper dozerBeanMapper) {
        EndpointImpl endpoint = new EndpointImpl(springBus(), new ReqServiceImpl(reqLineInterfaceRepo, sessionService, dozerBeanMapper));
        endpoint.publish("/ReqService");
        return endpoint;
    }

    @Bean
    public Endpoint endpoint4(DozerBeanMapper dozerBeanMapper, Validator validator) {
        EndpointImpl endpoint = new EndpointImpl(springBus(), new BusinessPartnerServiceImpl(bpInterfaceRepo, bpRelationInterfaceRepo, sessionService, dozerBeanMapper, validator));
        endpoint.publish("/BusinessPartnerService");
        return endpoint;
    }
}