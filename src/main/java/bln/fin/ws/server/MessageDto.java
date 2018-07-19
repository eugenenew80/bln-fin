package bln.fin.ws.server;

import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Msg", namespace = "http://bis.kegoc.kz/server")
public class MessageDto {

    @XmlElement
    @Documentation("Идентификатор объекта в БИС")
    private String id;

    @XmlElement
    @Documentation("Идентификатор объекта в SAP")
    private String sapId;

    @XmlElement(required = true)
    @Documentation("Код системы: BIS")
    private String system;

    @XmlElement(required = true)
    @Documentation("№ сообщения")
    private String msgNum;

    @XmlElement(required = true)
    @Documentation("Тип сообщения: S, W, E")
    private String msgType;

    @XmlElement
    @Documentation("Текст сообщения")
    private String msg;
}
