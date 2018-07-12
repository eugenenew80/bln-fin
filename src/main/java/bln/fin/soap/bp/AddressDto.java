package bln.fin.soap.bp;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Address", namespace = "http://bis.kegoc.kz/soap")
public class AddressDto {

    @XmlElement(required = true)
    private String country;

    @XmlElement(required = true)
    private String region;

    @XmlElement(required = true)
    private String city;

    @XmlElement(required = true)
    private String street;

    @XmlElement
    private String houseNum;

    @XmlElement
    private String buildNum;

    @XmlElement
    private String postCode;

    @XmlElement
    private String room;

    @XmlElement
    private String area;

    @XmlElement
    private String phoneNum;

    @XmlElement
    private String intPhoneNum;

    @XmlElement
    private String cellPhoneNum;

    @XmlElement
    private String fax;

    @XmlElement
    private String email;
}
