package bln.fin.soap.bp;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Bank", namespace = "http://bis.kegoc.kz/soap")
public class BankAccountDto {
    @XmlElement(required = true)
    private String country;

    @XmlElement(required = true)
    private String bik;

    @XmlElement(required = true)
    private String account;

    @XmlElement(required = true)
    private String iban;
}
