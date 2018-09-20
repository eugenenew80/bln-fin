package bln.fin.ws.server.bp;

import lombok.Data;
import javax.xml.bind.annotation.*;
import java.util.Date;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BusinessPartner", namespace = "http://bis.kegoc.kz/soap")
@Documentation("Деловой партнер")
public class BusinessPartnerDto {

    @XmlElement(required = true)
    @Documentation("№ делового партнера")
    private String bpNum;

    @XmlElement(required = true)
    @Documentation("Группа")
    private String group;

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

    @XmlElement(required = true, name = "translate")
    @Documentation("Перевод")
    private List<BusinessPartnerTranslateDto> translates;

    @XmlElement(required = true, name = "address")
    @Documentation("Адреса")
    private List<AddressDto> addresses;

    @XmlElement(name = "bankAccount")
    @Documentation("Список банковских счетов")
    private List<BankAccountDto> bankAccounts;
}
