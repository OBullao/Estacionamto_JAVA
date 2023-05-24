package br.com.uniamerica.estacionamento.service;
import br.com.uniamerica.estacionamento.Entity.*;
import br.com.uniamerica.estacionamento.Relatorio;
import br.com.uniamerica.estacionamento.repository.CondutorRepository;
import br.com.uniamerica.estacionamento.repository.ConfiguracaoRepository;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class MovimentacaoService {
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;
    @Autowired
    private ConfiguracaoRepository configuracaoRepository;
    @Autowired
    private CondutorRepository condutorRepository;
    private VeiculoRepository veiculoRepository;
    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Movimentacao movimentacao){
        Assert.isTrue(movimentacaoRepository.findVeiculo(movimentacao.getVeiculo(), movimentacao.getId()).isEmpty(),"Esse veiculo ja esta estacionado");
        Assert.isTrue(!this.movimentacaoRepository.findCondutorMov(movimentacao.getCondutor().getId()).isEmpty(),"Condutor nao existe");
        Assert.isTrue(!this.movimentacaoRepository.findVeiculoMov(movimentacao.getVeiculo().getId()).isEmpty(),"Veiculo nao existe");
        this.movimentacaoRepository.save(movimentacao);
    }
    public List<Movimentacao> listaCompleta() {
        return this.movimentacaoRepository.findAll();
    }
    @Transactional(rollbackFor = Exception.class)
    public void atualizar(Long id, Movimentacao atualizar) {
        final Movimentacao marcaBanco = this.movimentacaoRepository.findById(atualizar.getId()).orElse(null);
        Assert.isTrue(marcaBanco.getId().equals(id) ,"Error id da URL diferente do body");
        // pq isso nao da ceto
        Assert.isTrue(marcaBanco == null || marcaBanco.getId().equals(atualizar.getId()),"nao foi possivel identificar o registro");
        this.movimentacaoRepository.save(atualizar);
    }
    @Transactional(rollbackFor = Exception.class)
    public void deletar (final Movimentacao movimentacao) {
        final Movimentacao movimentacaoBanco = this.movimentacaoRepository.findById(movimentacao.getId()).orElse(null);
        movimentacaoBanco.setAtivo(false);
        this.movimentacaoRepository.save(movimentacaoBanco);

    }
    @Transactional(rollbackFor = Exception.class)
    public Relatorio sair(final Long id){
        // Verifica se a movimentação existe
        final Movimentacao movBanco = this.movimentacaoRepository.findById(id).orElse(null);
        Assert.isTrue(movBanco != null, "Não foi possivel identificar o registro informado");
        // Identifica o horário da saida e calcula o tempo entre os dois horários
       final LocalDateTime saida =  LocalDateTime.now();
        Duration duracao = Duration.between(movBanco.getEntrada(), saida);
        // Pega os valores de configuração
        final Configuracao config = this.configuracaoRepository.findById(1L).orElse(null);
        Assert.isTrue(config != null, "Configuracoes nao cadastradas");
        // Pega o desconto do cliente
        final Condutor condutor = this.condutorRepository.findById(movBanco.getCondutor().getId()).orElse(null);
        Assert.isTrue(condutor != null, "Condutor nao encontrado");
        // Seta saida e tempo
        movBanco.setSaida(saida);
        movBanco.setHoras(duracao.toHoursPart());
        movBanco.setMinutos(duracao.toMinutesPart());
        // Calcula o preco de acordo com o tempo passado
        final BigDecimal horas = BigDecimal.valueOf(duracao.toHoursPart());
        final BigDecimal minutos = BigDecimal.valueOf(duracao.toMinutesPart()).divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_EVEN);
        BigDecimal preco = config.getValorHora().multiply(horas).add(config.getValorHora().multiply(minutos));
        final BigDecimal tempoPago = condutor.getTempoPago() != null ? condutor.getTempoPago() : new BigDecimal(0);
        BigDecimal valor_desc;
        if (tempoPago.compareTo(new BigDecimal(config.getTempoParaDesconto())) >= 0) {
            valor_desc = config.getTempoDeDesconto();

            movBanco.setValorDesconto(valor_desc);
            condutor.setTempoPago(BigDecimal.ZERO);
        }else {
            valor_desc = BigDecimal.ZERO;
        }
        BigDecimal valorTotal = preco.subtract(valor_desc);
        movBanco.setValorTotal(valorTotal);
        movBanco.setValorHora(config.getValorHora());
        movBanco.setValorHoraMulta(config.getValorHoraMulta());
        if (config.isGerarDesconto()) {
            condutor.setTempoPago(tempoPago.add(horas.add(minutos)));
        }
        this.condutorRepository.save(condutor);
        this.movimentacaoRepository.save(movBanco);

        return new Relatorio(movBanco.getEntrada(), movBanco.getSaida(), movBanco.getCondutor(), movBanco.getVeiculo(), horas.intValue(),
                tempoPago.setScale(0, RoundingMode.HALF_EVEN),
                preco.subtract(valor_desc).setScale(2, RoundingMode.HALF_EVEN),
                valor_desc.setScale(2, RoundingMode.HALF_EVEN));
    }
    @Transactional(rollbackFor = Exception.class)
    public void deletar(final Long id){

        final Movimentacao movBanco = this.movimentacaoRepository.findById(id).orElse(null);
        Assert.isTrue(movBanco != null, "Registro não encontrado");

        movBanco.setAtivo(false);
        this.movimentacaoRepository.save(movBanco);

    }
}
