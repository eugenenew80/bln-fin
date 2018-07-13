package bln.fin.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(of= {"id"})
@Entity
@Table(name = "dict_gjs")
public class Item {
    @Id
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "erp_code")
    private String erpCode;
}