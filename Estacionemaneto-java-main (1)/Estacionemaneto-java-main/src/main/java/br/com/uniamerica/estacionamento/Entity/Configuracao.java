package br.com.uniamerica.estacionamento.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;
import java.time.LocalTime;
@Entity
@Table(name = "Configuracaos",schema = "public")
@Audited
@AuditTable(value = "ConfiguracaoAudit", schema = "audit")
public class Configuracao extends AbstractEntity {
    @Getter @Setter
    @Column(name="inicio_expediente",nullable=false)
    private LocalTime inicioExpediente;
    @Getter @Setter
    @Column(name="fim_expediente",nullable=false)
    private LocalTime fimExpediente;
    @Getter
    @Setter
    @Column(name="valor_hora",nullable=false)
    private BigDecimal valorHora;
    @Getter @Setter
    @Column(name="valor_hora_multa",nullable=false)
    private BigDecimal valorHoraMulta;
    @Getter @Setter
    @Column(name="tempo_para_desconto",nullable=false)
    private  Integer tempoParaDesconto;

    @Getter @Setter
    @Column(name="tempo_de_desconto",nullable=false)
    private  BigDecimal tempoDeDesconto;



    @Getter @Setter
    @Column(name="gerar_desconto",nullable=false)
    private  boolean gerarDesconto;
    @Getter @Setter
    @Column(name="vagas_moto",nullable=false)
    private Integer vagasMoto;
    @Getter @Setter
    @Column(name="vagas_carro",nullable=false)
    private Integer vagasCarro;
    @Getter @Setter
    @Column(name="Vagas_vam",nullable=false)
    private Integer VagasVam;


}
