//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.07.13 at 04:06:00 PM ALMT 
//


package sap.erp.plan;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the sap.erp.plan package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SalesPlan_QNAME = new QName("urn:kegoc.kz:LO_0002_3_SalesPlan", "SalesPlan");
    private final static QName _SalesPlanResp_QNAME = new QName("urn:kegoc.kz:BIS:LO_0002_3_SalesPlan", "SalesPlan_Resp");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: sap.erp.plan
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Response }
     * 
     */
    public Response createResponse() {
        return new Response();
    }

    /**
     * Create an instance of {@link SalesPlan }
     * 
     */
    public SalesPlan createSalesPlan() {
        return new SalesPlan();
    }

    /**
     * Create an instance of {@link ExchangeLogData }
     * 
     */
    public ExchangeLogData createExchangeLogData() {
        return new ExchangeLogData();
    }

    /**
     * Create an instance of {@link ExchangeFaultData }
     * 
     */
    public ExchangeFaultData createExchangeFaultData() {
        return new ExchangeFaultData();
    }

    /**
     * Create an instance of {@link FaultMessage }
     * 
     */
    public FaultMessage createFaultMessage() {
        return new FaultMessage();
    }

    /**
     * Create an instance of {@link Response.Item }
     * 
     */
    public Response.Item createResponseItem() {
        return new Response.Item();
    }

    /**
     * Create an instance of {@link SalesPlan.Item }
     * 
     */
    public SalesPlan.Item createSalesPlanItem() {
        return new SalesPlan.Item();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SalesPlan }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:kegoc.kz:LO_0002_3_SalesPlan", name = "SalesPlan")
    public JAXBElement<SalesPlan> createSalesPlan(SalesPlan value) {
        return new JAXBElement<SalesPlan>(_SalesPlan_QNAME, SalesPlan.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Response }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:kegoc.kz:BIS:LO_0002_3_SalesPlan", name = "SalesPlan_Resp")
    public JAXBElement<Response> createSalesPlanResp(Response value) {
        return new JAXBElement<Response>(_SalesPlanResp_QNAME, Response.class, null, value);
    }

}
