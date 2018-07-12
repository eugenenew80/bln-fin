package bln.fin.soap.bp;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BusinessPartner", namespace = "http://bis.kegoc.kz/soap")
public class BusinessPartnerDto {
    @XmlElement(required = true)
    private String num;

    @XmlElement(required = true)
    private String group;

    @XmlElement(required = true)
    private String name;

    @XmlElement(required = true)
    private String fullName;

    @XmlElement(required = true)
    private String searchCriteria;

    @XmlElement(required = true)
    private Date foundDate;

    @XmlElement
    private Date liqDate;

    @XmlElement
    private String industry;

    @XmlElement(required = true)
    private String taxNumberType;

    @XmlElement(required = true)
    private String taxNumber;

    @XmlElement(required = true)
    private AddressDto legalAddress;

    @XmlElement(required = true)
    private AddressDto actualAddress;

    @XmlElement(name = "bankAccount")
    private List<BankAccountDto> bankAccounts;

    @XmlElement(name = "relation")
    private List<RelationDto> relations;
}
