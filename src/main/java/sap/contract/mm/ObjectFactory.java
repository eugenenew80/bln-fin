//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.09.17 at 08:49:43 PM ALMT 
//


package sap.contract.mm;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the sap.contract.mm package. 
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

    private final static QName _Contract_QNAME = new QName("urn:kegoc.kz:BIS:ZMM_0042_Contract", "Contract");
    private final static QName _ContractResp_QNAME = new QName("urn:kegoc.kz:BIS:ZMM_0042_Contract", "Contract_Resp");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: sap.contract.mm
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Contract }
     * 
     */
    public Contract createContract() {
        return new Contract();
    }

    /**
     * Create an instance of {@link Contract.Item }
     * 
     */
    public Contract.Item createContractItem() {
        return new Contract.Item();
    }

    /**
     * Create an instance of {@link Response }
     * 
     */
    public Response createResponse() {
        return new Response();
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
     * Create an instance of {@link Contract.Item.Row }
     * 
     */
    public Contract.Item.Row createContractItemRow() {
        return new Contract.Item.Row();
    }

    /**
     * Create an instance of {@link Response.Item }
     * 
     */
    public Response.Item createResponseItem() {
        return new Response.Item();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Contract }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:kegoc.kz:BIS:ZMM_0042_Contract", name = "Contract")
    public JAXBElement<Contract> createContract(Contract value) {
        return new JAXBElement<Contract>(_Contract_QNAME, Contract.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Response }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:kegoc.kz:BIS:ZMM_0042_Contract", name = "Contract_Resp")
    public JAXBElement<Response> createContractResp(Response value) {
        return new JAXBElement<Response>(_ContractResp_QNAME, Response.class, null, value);
    }

}
