package bln.fin.ws.server.bp;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.Date;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BusinessPartner", namespace = "http://bis.kegoc.kz/server")
@Documentation("Деловой партнер")
public class BusinessPartnerDto {

    @XmlAttribute(required = true)
    @Documentation("Код языка")
    private String lang;

    @XmlElement(required = true)
    @Documentation("№ делового партнера")
    private String bpNum;

    @XmlElement(required = true)
    @Documentation("Группа")
    private String group;

    @XmlElement(required = true)
    @Documentation("Наименование делового партнера")
    private String name;

    @XmlElement(required = true)
    @Documentation("Полное наименование делового партнера")
    private String fullName;

    @XmlElement(required = true)
    @Documentation("Критерий поиска")
    private String searchCriteria;

    @XmlElement(required = true)
    @Documentation("Дата основания")
    private Date foundDate;

    @XmlElement
    @Documentation("Дата ликвидации")
    private Date liqDate;

    @XmlElement
    @Documentation("Отрасль")
    private String industry;

    @XmlElement(required = true)
    @Documentation("Типа налогового номера")
    private String taxNumberType;

    @XmlElement(required = true)
    @Documentation("Налоговый номер")
    private String taxNumber;

    @XmlElement(required = true)
    @Documentation("Юридический адрес")
    private AddressDto legalAddress;

    @XmlElement
    @Documentation("Фактический адрес")
    private AddressDto actualAddress;

    @XmlElement(name = "bankAccount")
    @Documentation("Список банковских счетов")
    private List<BankAccountDto> bankAccounts;

    @XmlElement(name = "relation")
    @Documentation("Отношения")
    private List<RelationDto> relations;
}
