//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.12.20 at 02:23:00 PM ALMT 
//


package bems;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for SIDEXResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SIDEXResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TransmissionTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="TransmissionState"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;enumeration value="OK"/&gt;
 *               &lt;enumeration value="ERROR"/&gt;
 *               &lt;length value="1"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="TransmissionStateInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="responseFile" type="{http://www.smarttech.at/SIDEX-Service/}FileType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SIDEXResponseType", propOrder = {
    "transmissionTime",
    "transmissionState",
    "transmissionStateInfo",
    "responseFile"
})
public class SIDEXResponseType {

    @XmlElement(name = "TransmissionTime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar transmissionTime;
    @XmlElement(name = "TransmissionState", required = true)
    protected String transmissionState;
    @XmlElement(name = "TransmissionStateInfo")
    protected String transmissionStateInfo;
    protected FileType responseFile;

    /**
     * Gets the value of the transmissionTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTransmissionTime() {
        return transmissionTime;
    }

    /**
     * Sets the value of the transmissionTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTransmissionTime(XMLGregorianCalendar value) {
        this.transmissionTime = value;
    }

    /**
     * Gets the value of the transmissionState property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransmissionState() {
        return transmissionState;
    }

    /**
     * Sets the value of the transmissionState property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransmissionState(String value) {
        this.transmissionState = value;
    }

    /**
     * Gets the value of the transmissionStateInfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransmissionStateInfo() {
        return transmissionStateInfo;
    }

    /**
     * Sets the value of the transmissionStateInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransmissionStateInfo(String value) {
        this.transmissionStateInfo = value;
    }

    /**
     * Gets the value of the responseFile property.
     * 
     * @return
     *     possible object is
     *     {@link FileType }
     *     
     */
    public FileType getResponseFile() {
        return responseFile;
    }

    /**
     * Sets the value of the responseFile property.
     * 
     * @param value
     *     allowed object is
     *     {@link FileType }
     *     
     */
    public void setResponseFile(FileType value) {
        this.responseFile = value;
    }

}
