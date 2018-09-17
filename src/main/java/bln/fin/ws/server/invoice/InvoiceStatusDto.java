package bln.fin.ws.server.invoice;

import lombok.Data;

import javax.persistence.Column;
import javax.xml.bind.annotation.*;
import java.util.Date;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvoiceStatus", namespace = "http://bis.kegoc.kz/soap")
@Documentation("Информация о документе с портала ЭСФ")
public class InvoiceStatusDto {

    @XmlElement(required = true)
    @Documentation("Тип делового партнера: K - поставщик, D - заказчик")
    private String bpType;

    @XmlElement(required = true)
    @Documentation("№ документа в SAP")
    private String docNum;

    @XmlElement(required = true)
    @Documentation("Дата документа в SAP")
    private Date docDate;

    @XmlElement(required = true)
    @Documentation("№ документа на портале ЭСФ")
    private String esfDocNum;

    @XmlElement(required = true)
    @Documentation("Дата регистрации на портале ЭСФ")
    private Date esfDocDate;

    @XmlElement(required = true)
    @Documentation("Статус на портале ЭСФ")
    private String esfStatus;
}
