package br.com.uniamerica.estacionamento.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
@Entity
@Audited
@AuditTable(value = "CondutoresAudit", schema = "audit")
@Table(name = "condutores",schema = "public")
public class Condutor extends AbstractEntity {

    @Getter @Setter
    @Column(name="nome",nullable=false,length=100)
    private String nome;
    @Getter @Setter
    @Column(name="cpf",nullable=false,unique=true,length=15)
    private  String cpf;
    @Getter @Setter
    @Column(name="telefone",nullable=false,length=21)
    private String telefone;
    @Getter @Setter
    @Column(name="tempo_pago")
    private BigDecimal tempoPago;
    @Getter @Setter
    @Column(name="tempo_gasto")
    private BigDecimal tempoDesconto;


}
