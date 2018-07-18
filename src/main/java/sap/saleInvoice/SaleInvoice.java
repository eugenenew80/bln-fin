//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.07.17 at 10:22:50 PM ALMT 
//


package sap.saleInvoice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Счета-фактуры
 * 
 * <p>Java class for SaleInvoice complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SaleInvoice"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="sapDocNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="sapDocDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="docNum" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="accountingDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="bpNum" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="contractNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="extContractNum" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="companyCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="system" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="tax" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="currencyCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="exchangeRate" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="line" type="{http://bis.kegoc.kz/soap}Line" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SaleInvoice", propOrder = {
    "id",
    "sapDocNum",
    "sapDocDate",
    "docNum",
    "accountingDate",
    "bpNum",
    "contractNum",
    "extContractNum",
    "companyCode",
    "system",
    "amount",
    "tax",
    "currencyCode",
    "exchangeRate",
    "line"
})
public class SaleInvoice {

    protected long id;
    protected String sapDocNum;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar sapDocDate;
    @XmlElement(required = true)
    protected String docNum;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar accountingDate;
    @XmlElement(required = true)
    protected String bpNum;
    protected String contractNum;
    @XmlElement(required = true)
    protected String extContractNum;
    @XmlElement(required = true)
    protected String companyCode;
    @XmlElement(required = true)
    protected String system;
    protected double amount;
    protected double tax;
    @XmlElement(required = true)
    protected String currencyCode;
    protected Double exchangeRate;
    @XmlElement(required = true)
    protected List<Line> line;

    /**
     * Gets the value of the id property.
     * 
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(long value) {
        this.id = value;
    }

    /**
     * Gets the value of the sapDocNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSapDocNum() {
        return sapDocNum;
    }

    /**
     * Sets the value of the sapDocNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSapDocNum(String value) {
        this.sapDocNum = value;
    }

    /**
     * Gets the value of the sapDocDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSapDocDate() {
        return sapDocDate;
    }

    /**
     * Sets the value of the sapDocDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSapDocDate(XMLGregorianCalendar value) {
        this.sapDocDate = value;
    }

    /**
     * Gets the value of the docNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocNum() {
        return docNum;
    }

    /**
     * Sets the value of the docNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocNum(String value) {
        this.docNum = value;
    }

    /**
     * Gets the value of the accountingDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAccountingDate() {
        return accountingDate;
    }

    /**
     * Sets the value of the accountingDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAccountingDate(XMLGregorianCalendar value) {
        this.accountingDate = value;
    }

    /**
     * Gets the value of the bpNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBpNum() {
        return bpNum;
    }

    /**
     * Sets the value of the bpNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBpNum(String value) {
        this.bpNum = value;
    }

    /**
     * Gets the value of the contractNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractNum() {
        return contractNum;
    }

    /**
     * Sets the value of the contractNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractNum(String value) {
        this.contractNum = value;
    }

    /**
     * Gets the value of the extContractNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtContractNum() {
        return extContractNum;
    }

    /**
     * Sets the value of the extContractNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtContractNum(String value) {
        this.extContractNum = value;
    }

    /**
     * Gets the value of the companyCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyCode() {
        return companyCode;
    }

    /**
     * Sets the value of the companyCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyCode(String value) {
        this.companyCode = value;
    }

    /**
     * Gets the value of the system property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSystem() {
        return system;
    }

    /**
     * Sets the value of the system property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSystem(String value) {
        this.system = value;
    }

    /**
     * Gets the value of the amount property.
     * 
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     */
    public void setAmount(double value) {
        this.amount = value;
    }

    /**
     * Gets the value of the tax property.
     * 
     */
    public double getTax() {
        return tax;
    }

    /**
     * Sets the value of the tax property.
     * 
     */
    public void setTax(double value) {
        this.tax = value;
    }

    /**
     * Gets the value of the currencyCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * Sets the value of the currencyCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrencyCode(String value) {
        this.currencyCode = value;
    }

    /**
     * Gets the value of the exchangeRate property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getExchangeRate() {
        return exchangeRate;
    }

    /**
     * Sets the value of the exchangeRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setExchangeRate(Double value) {
        this.exchangeRate = value;
    }

    /**
     * Gets the value of the line property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the line property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLine().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Line }
     * 
     * 
     */
    public List<Line> getLine() {
        if (line == null) {
            line = new ArrayList<Line>();
        }
        return this.line;
    }

}