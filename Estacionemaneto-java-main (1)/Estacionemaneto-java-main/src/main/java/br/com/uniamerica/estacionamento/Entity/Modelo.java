package br.com.uniamerica.estacionamento.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;
@Entity
@Table(name = "modelos",schema = "public")
@Audited
@AuditTable(value = "ModeloAudit", schema = "audit")
public class Modelo extends AbstractEntity {
    @Getter
    @Setter
    @Column(name="nome",nullable=false,length=30)
    private String nome;
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name="Modelo_marca",nullable=false)
    private Marca marca;

}
