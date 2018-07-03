package bln.fin;

import bln.fin.repo.*;
import bln.fin.soap.debt.DebtServiceImpl;
import bln.fin.soap.invoice.InvoiceServiceImpl;
import bln.fin.soap.req.ReqServiceImpl;
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
        EndpointImpl endpoint = new EndpointImpl(springBus(), new DebtServiceImpl(checkApplicationRepo, receiptApplicationRepo));
        endpoint.publish("/DebtService");
        return endpoint;
    }

    @Bean
    public Endpoint endpoint2() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), new InvoiceServiceImpl(purchaseInvoiceRepo, saleInvoiceRepo));
        endpoint.publish("/InvoiceService");
        return endpoint;
    }

    @Bean
    public Endpoint endpoint3() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), new ReqServiceImpl(reqLineRepo));
        endpoint.publish("/ReqService");
        return endpoint;
    }

    @Autowired
    private final ReqLineRepo reqLineRepo;

    @Autowired
    private final CheckApplicationRepo checkApplicationRepo;

    @Autowired
    private final ReceiptApplicationRepo receiptApplicationRepo;

    @Autowired
    private PurchaseInvoiceRepo purchaseInvoiceRepo;

    @Autowired
    private SaleInvoiceRepo saleInvoiceRepo;
}
