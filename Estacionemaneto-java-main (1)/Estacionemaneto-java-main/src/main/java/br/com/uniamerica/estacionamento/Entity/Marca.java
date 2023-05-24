package br.com.uniamerica.estacionamento.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;

@Entity
@Table(name = "marcas",schema = "public")
@Audited
@AuditTable(value = "marcasaudit", schema = "audit")
public class Marca extends AbstractEntity {
    @Getter
    @Setter
    @Column(name="marca",nullable=false,length=20)
    private String nome;

}
