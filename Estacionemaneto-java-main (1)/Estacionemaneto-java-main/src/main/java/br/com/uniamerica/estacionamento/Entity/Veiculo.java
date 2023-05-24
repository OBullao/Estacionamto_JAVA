package br.com.uniamerica.estacionamento.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;
@Entity

@Table(name = "veiculos",schema = "public")
@Audited
@AuditTable(value = "VeiculoAudit", schema = "audit")
public class Veiculo extends AbstractEntity {
    @Getter
    @Setter
    @Column(name="placa",nullable=false,unique = true,length=10)
    private String placa;
    @Getter
    @Setter
    @Column(name="ano",nullable=false)
    private int ano;
    @Enumerated(EnumType.STRING)
    @Getter @Setter
    @Column(name = "tipo", length = 10, nullable = false)
    private Tipo tipo;
    @Enumerated(EnumType.STRING)
    @Getter @Setter
    @Column(name = "cor", length = 20, nullable = false)
    private Cor cor;
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name="Veiculo_modelo",nullable=false)
    private Modelo modelo;

}
