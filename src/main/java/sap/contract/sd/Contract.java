//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.10.04 at 10:14:47 PM ALMT 
//


package sap.contract.sd;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *                   &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *                   &lt;element name="contractType" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="extContractNum" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="contractNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="contractDate" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *                   &lt;element name="channel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="salesDepartCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="companyCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="companyAccountNum" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="customerNum" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="consigneeNum" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="customerAccountNum" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="currencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *                   &lt;element name="paymentCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="startDate" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *                   &lt;element name="endDate" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *                   &lt;element name="row" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="itemNum" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="unit" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="quantity" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *                             &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *                             &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
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
     *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
     *         &lt;element name="contractType" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="extContractNum" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="contractNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="contractDate" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
     *         &lt;element name="channel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="salesDepartCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="companyCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="companyAccountNum" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="customerNum" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="consigneeNum" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="customerAccountNum" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="currencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
     *         &lt;element name="paymentCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="startDate" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
     *         &lt;element name="endDate" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
     *         &lt;element name="row" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="itemNum" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="unit" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="quantity" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
     *                   &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
     *                   &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
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
    @XmlType(name = "", propOrder = {
        "id",
        "contractType",
        "extContractNum",
        "contractNum",
        "contractDate",
        "channel",
        "salesDepartCode",
        "companyCode",
        "companyAccountNum",
        "customerNum",
        "consigneeNum",
        "customerAccountNum",
        "currencyCode",
        "amount",
        "paymentCode",
        "startDate",
        "endDate",
        "row"
    })
    public static class Item {

        protected long id;
        @XmlElement(required = true)
        protected String contractType;
        @XmlElement(required = true)
        protected String extContractNum;
        protected String contractNum;
        @XmlElement(required = true)
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar contractDate;
        @XmlElement(required = true)
        protected String channel;
        @XmlElement(required = true)
        protected String salesDepartCode;
        @XmlElement(required = true)
        protected String companyCode;
        @XmlElement(required = true)
        protected String companyAccountNum;
        @XmlElement(required = true)
        protected String customerNum;
        @XmlElement(required = true)
        protected String consigneeNum;
        @XmlElement(required = true)
        protected String customerAccountNum;
        protected String currencyCode;
        protected Double amount;
        @XmlElement(required = true)
        protected String paymentCode;
        @XmlElement(required = true)
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar startDate;
        @XmlElement(required = true)
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar endDate;
        protected List<Contract.Item.Row> row;

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
         * Gets the value of the contractType property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getContractType() {
            return contractType;
        }

        /**
         * Sets the value of the contractType property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setContractType(String value) {
            this.contractType = value;
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
         * Gets the value of the contractDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getContractDate() {
            return contractDate;
        }

        /**
         * Sets the value of the contractDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setContractDate(XMLGregorianCalendar value) {
            this.contractDate = value;
        }

        /**
         * Gets the value of the channel property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getChannel() {
            return channel;
        }

        /**
         * Sets the value of the channel property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setChannel(String value) {
            this.channel = value;
        }

        /**
         * Gets the value of the salesDepartCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSalesDepartCode() {
            return salesDepartCode;
        }

        /**
         * Sets the value of the salesDepartCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSalesDepartCode(String value) {
            this.salesDepartCode = value;
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
         * Gets the value of the companyAccountNum property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCompanyAccountNum() {
            return companyAccountNum;
        }

        /**
         * Sets the value of the companyAccountNum property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCompanyAccountNum(String value) {
            this.companyAccountNum = value;
        }

        /**
         * Gets the value of the customerNum property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCustomerNum() {
            return customerNum;
        }

        /**
         * Sets the value of the customerNum property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCustomerNum(String value) {
            this.customerNum = value;
        }

        /**
         * Gets the value of the consigneeNum property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getConsigneeNum() {
            return consigneeNum;
        }

        /**
         * Sets the value of the consigneeNum property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setConsigneeNum(String value) {
            this.consigneeNum = value;
        }

        /**
         * Gets the value of the customerAccountNum property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCustomerAccountNum() {
            return customerAccountNum;
        }

        /**
         * Sets the value of the customerAccountNum property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCustomerAccountNum(String value) {
            this.customerAccountNum = value;
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
         * Gets the value of the amount property.
         * 
         * @return
         *     possible object is
         *     {@link Double }
         *     
         */
        public Double getAmount() {
            return amount;
        }

        /**
         * Sets the value of the amount property.
         * 
         * @param value
         *     allowed object is
         *     {@link Double }
         *     
         */
        public void setAmount(Double value) {
            this.amount = value;
        }

        /**
         * Gets the value of the paymentCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPaymentCode() {
            return paymentCode;
        }

        /**
         * Sets the value of the paymentCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPaymentCode(String value) {
            this.paymentCode = value;
        }

        /**
         * Gets the value of the startDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getStartDate() {
            return startDate;
        }

        /**
         * Sets the value of the startDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setStartDate(XMLGregorianCalendar value) {
            this.startDate = value;
        }

        /**
         * Gets the value of the endDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getEndDate() {
            return endDate;
        }

        /**
         * Sets the value of the endDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setEndDate(XMLGregorianCalendar value) {
            this.endDate = value;
        }

        /**
         * Gets the value of the row property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the row property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRow().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Contract.Item.Row }
         * 
         * 
         */
        public List<Contract.Item.Row> getRow() {
            if (row == null) {
                row = new ArrayList<Contract.Item.Row>();
            }
            return this.row;
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
         *         &lt;element name="itemNum" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="unit" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="quantity" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
         *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
         *         &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
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
            "itemNum",
            "unit",
            "quantity",
            "price",
            "amount"
        })
        public static class Row {

            @XmlElement(required = true)
            protected String itemNum;
            @XmlElement(required = true)
            protected String unit;
            protected Double quantity;
            protected Double price;
            protected Double amount;

            /**
             * Gets the value of the itemNum property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getItemNum() {
                return itemNum;
            }

            /**
             * Sets the value of the itemNum property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setItemNum(String value) {
                this.itemNum = value;
            }

            /**
             * Gets the value of the unit property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getUnit() {
                return unit;
            }

            /**
             * Sets the value of the unit property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setUnit(String value) {
                this.unit = value;
            }

            /**
             * Gets the value of the quantity property.
             * 
             */
            public Double getQuantity() {
                return quantity;
            }

            /**
             * Sets the value of the quantity property.
             * 
             */
            public void setQuantity(Double value) {
                this.quantity = value;
            }

            /**
             * Gets the value of the price property.
             * 
             */
            public Double getPrice() {
                return price;
            }

            /**
             * Sets the value of the price property.
             * 
             */
            public void setPrice(Double value) {
                this.price = value;
            }

            /**
             * Gets the value of the amount property.
             * 
             */
            public Double getAmount() {
                return amount;
            }

            /**
             * Sets the value of the amount property.
             * 
             */
            public void setAmount(Double value) {
                this.amount = value;
            }

        }

    }

}
