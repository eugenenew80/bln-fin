package bln.fin.soap.bp;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Relation", namespace = "http://bis.kegoc.kz/soap")
public class RelationDto {
    @XmlElement(required = true)
    private String relationType;

    @XmlElement(required = true)
    private String bpNum;
}
