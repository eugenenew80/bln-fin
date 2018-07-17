//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.07.17 at 10:22:48 PM ALMT 
//


package sap.contract.sd;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Contract complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Contract"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="item" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="ContractID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="SAPContractID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="ContractDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="ActivityKind" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="CounterpartyName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="ConsigneeName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="ProductName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="Price" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="Amount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="RateName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="Currency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="PaymentTerms" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="NDS_percent" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="NDS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="PeriodBeginDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="PeriodEndDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="EstimatedChargesFlg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="KEGOCBranchName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="KEGOCBranchLocationArea" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="UserName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="SupperiorBISContrac" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="Discount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="DiscountStartDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="DiscountEndDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="FactEndDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Contract", namespace = "urn:kegoc.kz:BIS:LO_0001_Contract", propOrder = {
    "item"
})
public class Contract {

    protected List<Contract.Item> item;

    /**
     * Gets the value of the item property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the item property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Contract.Item }
     * 
     * 
     */
    public List<Contract.Item> getItem() {
        if (item == null) {
            item = new ArrayList<Contract.Item>();
        }
        return this.item;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="ContractID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="SAPContractID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="ContractDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="ActivityKind" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="CounterpartyName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="ConsigneeName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="ProductName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="Price" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="Amount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="RateName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="Currency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="PaymentTerms" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="NDS_percent" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="NDS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="PeriodBeginDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="PeriodEndDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="EstimatedChargesFlg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="KEGOCBranchName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="KEGOCBranchLocationArea" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="UserName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="SupperiorBISContrac" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="Discount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="DiscountStartDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="DiscountEndDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="FactEndDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "contractID",
        "sapContractID",
        "contractDate",
        "activityKind",
        "counterpartyName",
        "consigneeName",
        "productName",
        "price",
        "amount",
        "rateName",
        "currency",
        "paymentTerms",
        "ndsPercent",
        "nds",
        "periodBeginDate",
        "periodEndDate",
        "estimatedChargesFlg",
        "kegocBranchName",
        "kegocBranchLocationArea",
        "userName",
        "supperiorBISContrac",
        "discount",
        "discountStartDate",
        "discountEndDate",
        "factEndDate"
    })
    public static class Item {

        @XmlElement(name = "ContractID")
        protected String contractID;
        @XmlElement(name = "SAPContractID")
        protected String sapContractID;
        @XmlElement(name = "ContractDate")
        protected String contractDate;
        @XmlElement(name = "ActivityKind")
        protected String activityKind;
        @XmlElement(name = "CounterpartyName")
        protected String counterpartyName;
        @XmlElement(name = "ConsigneeName")
        protected String consigneeName;
        @XmlElement(name = "ProductName")
        protected String productName;
        @XmlElement(name = "Price")
        protected String price;
        @XmlElement(name = "Amount")
        protected String amount;
        @XmlElement(name = "RateName")
        protected String rateName;
        @XmlElement(name = "Currency")
        protected String currency;
        @XmlElement(name = "PaymentTerms")
        protected String paymentTerms;
        @XmlElement(name = "NDS_percent")
        protected String ndsPercent;
        @XmlElement(name = "NDS")
        protected String nds;
        @XmlElement(name = "PeriodBeginDate")
        protected String periodBeginDate;
        @XmlElement(name = "PeriodEndDate")
        protected String periodEndDate;
        @XmlElement(name = "EstimatedChargesFlg")
        protected String estimatedChargesFlg;
        @XmlElement(name = "KEGOCBranchName")
        protected String kegocBranchName;
        @XmlElement(name = "KEGOCBranchLocationArea")
        protected String kegocBranchLocationArea;
        @XmlElement(name = "UserName")
        protected String userName;
        @XmlElement(name = "SupperiorBISContrac")
        protected String supperiorBISContrac;
        @XmlElement(name = "Discount")
        protected String discount;
        @XmlElement(name = "DiscountStartDate")
        protected String discountStartDate;
        @XmlElement(name = "DiscountEndDate")
        protected String discountEndDate;
        @XmlElement(name = "FactEndDate")
        protected String factEndDate;

        /**
         * Gets the value of the contractID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getContractID() {
            return contractID;
        }

        /**
         * Sets the value of the contractID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setContractID(String value) {
            this.contractID = value;
        }

        /**
         * Gets the value of the sapContractID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSAPContractID() {
            return sapContractID;
        }

        /**
         * Sets the value of the sapContractID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSAPContractID(String value) {
            this.sapContractID = value;
        }

        /**
         * Gets the value of the contractDate property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getContractDate() {
            return contractDate;
        }

        /**
         * Sets the value of the contractDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setContractDate(String value) {
            this.contractDate = value;
        }

        /**
         * Gets the value of the activityKind property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getActivityKind() {
            return activityKind;
        }

        /**
         * Sets the value of the activityKind property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setActivityKind(String value) {
            this.activityKind = value;
        }

        /**
         * Gets the value of the counterpartyName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCounterpartyName() {
            return counterpartyName;
        }

        /**
         * Sets the value of the counterpartyName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCounterpartyName(String value) {
            this.counterpartyName = value;
        }

        /**
         * Gets the value of the consigneeName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getConsigneeName() {
            return consigneeName;
        }

        /**
         * Sets the value of the consigneeName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setConsigneeName(String value) {
            this.consigneeName = value;
        }

        /**
         * Gets the value of the productName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getProductName() {
            return productName;
        }

        /**
         * Sets the value of the productName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setProductName(String value) {
            this.productName = value;
        }

        /**
         * Gets the value of the price property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPrice() {
            return price;
        }

        /**
         * Sets the value of the price property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPrice(String value) {
            this.price = value;
        }

        /**
         * Gets the value of the amount property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAmount() {
            return amount;
        }

        /**
         * Sets the value of the amount property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAmount(String value) {
            this.amount = value;
        }

        /**
         * Gets the value of the rateName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRateName() {
            return rateName;
        }

        /**
         * Sets the value of the rateName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRateName(String value) {
            this.rateName = value;
        }

        /**
         * Gets the value of the currency property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCurrency() {
            return currency;
        }

        /**
         * Sets the value of the currency property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCurrency(String value) {
            this.currency = value;
        }

        /**
         * Gets the value of the paymentTerms property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPaymentTerms() {
            return paymentTerms;
        }

        /**
         * Sets the value of the paymentTerms property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPaymentTerms(String value) {
            this.paymentTerms = value;
        }

        /**
         * Gets the value of the ndsPercent property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNDSPercent() {
            return ndsPercent;
        }

        /**
         * Sets the value of the ndsPercent property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNDSPercent(String value) {
            this.ndsPercent = value;
        }

        /**
         * Gets the value of the nds property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNDS() {
            return nds;
        }

        /**
         * Sets the value of the nds property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNDS(String value) {
            this.nds = value;
        }

        /**
         * Gets the value of the periodBeginDate property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPeriodBeginDate() {
            return periodBeginDate;
        }

        /**
         * Sets the value of the periodBeginDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPeriodBeginDate(String value) {
            this.periodBeginDate = value;
        }

        /**
         * Gets the value of the periodEndDate property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPeriodEndDate() {
            return periodEndDate;
        }

        /**
         * Sets the value of the periodEndDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPeriodEndDate(String value) {
            this.periodEndDate = value;
        }

        /**
         * Gets the value of the estimatedChargesFlg property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEstimatedChargesFlg() {
            return estimatedChargesFlg;
        }

        /**
         * Sets the value of the estimatedChargesFlg property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEstimatedChargesFlg(String value) {
            this.estimatedChargesFlg = value;
        }

        /**
         * Gets the value of the kegocBranchName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getKEGOCBranchName() {
            return kegocBranchName;
        }

        /**
         * Sets the value of the kegocBranchName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setKEGOCBranchName(String value) {
            this.kegocBranchName = value;
        }

        /**
         * Gets the value of the kegocBranchLocationArea property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getKEGOCBranchLocationArea() {
            return kegocBranchLocationArea;
        }

        /**
         * Sets the value of the kegocBranchLocationArea property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setKEGOCBranchLocationArea(String value) {
            this.kegocBranchLocationArea = value;
        }

        /**
         * Gets the value of the userName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getUserName() {
            return userName;
        }

        /**
         * Sets the value of the userName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUserName(String value) {
            this.userName = value;
        }

        /**
         * Gets the value of the supperiorBISContrac property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSupperiorBISContrac() {
            return supperiorBISContrac;
        }

        /**
         * Sets the value of the supperiorBISContrac property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSupperiorBISContrac(String value) {
            this.supperiorBISContrac = value;
        }

        /**
         * Gets the value of the discount property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDiscount() {
            return discount;
        }

        /**
         * Sets the value of the discount property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDiscount(String value) {
            this.discount = value;
        }

        /**
         * Gets the value of the discountStartDate property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDiscountStartDate() {
            return discountStartDate;
        }

        /**
         * Sets the value of the discountStartDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDiscountStartDate(String value) {
            this.discountStartDate = value;
        }

        /**
         * Gets the value of the discountEndDate property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDiscountEndDate() {
            return discountEndDate;
        }

        /**
         * Sets the value of the discountEndDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDiscountEndDate(String value) {
            this.discountEndDate = value;
        }

        /**
         * Gets the value of the factEndDate property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFactEndDate() {
            return factEndDate;
        }

        /**
         * Sets the value of the factEndDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFactEndDate(String value) {
            this.factEndDate = value;
        }

    }

}
