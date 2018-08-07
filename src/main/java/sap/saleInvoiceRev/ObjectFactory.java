//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.08.06 at 12:33:27 PM ALMT 
//


package sap.saleInvoiceRev;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the sap.saleInvoiceRev package. 
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

    private final static QName _RevInvoices_QNAME = new QName("http://bis.kegoc.kz/soap", "revInvoices");
    private final static QName _RevInvoicesResponse_QNAME = new QName("http://bis.kegoc.kz/soap", "revInvoicesResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: sap.saleInvoiceRev
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RevInvoices }
     * 
     */
    public RevInvoices createRevInvoices() {
        return new RevInvoices();
    }

    /**
     * Create an instance of {@link RevInvoicesResponse }
     * 
     */
    public RevInvoicesResponse createRevInvoicesResponse() {
        return new RevInvoicesResponse();
    }

    /**
     * Create an instance of {@link SaleInvoiceRev }
     * 
     */
    public SaleInvoiceRev createSaleInvoiceRev() {
        return new SaleInvoiceRev();
    }

    /**
     * Create an instance of {@link Msg }
     * 
     */
    public Msg createMsg() {
        return new Msg();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RevInvoices }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bis.kegoc.kz/soap", name = "revInvoices")
    public JAXBElement<RevInvoices> createRevInvoices(RevInvoices value) {
        return new JAXBElement<RevInvoices>(_RevInvoices_QNAME, RevInvoices.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RevInvoicesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bis.kegoc.kz/soap", name = "revInvoicesResponse")
    public JAXBElement<RevInvoicesResponse> createRevInvoicesResponse(RevInvoicesResponse value) {
        return new JAXBElement<RevInvoicesResponse>(_RevInvoicesResponse_QNAME, RevInvoicesResponse.class, null, value);
    }

}
