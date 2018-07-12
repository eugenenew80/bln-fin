package bln.fin.soap;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Msg", namespace = "http://bis.kegoc.kz/soap")
public class MessageDto {
    @XmlElement(required = true)
    private String msgNum;

    @XmlElement(required = true)
    private String status;

    @XmlElement(required = true)
    private String text;

    @XmlElement(required = true)
    private Long id;

    @XmlElement(required = true)
    private String sapId;
}
