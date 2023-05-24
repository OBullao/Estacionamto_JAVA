package br.com.uniamerica.estacionamento.service;
import br.com.uniamerica.estacionamento.Entity.Configuracao;
import br.com.uniamerica.estacionamento.Entity.Modelo;
import br.com.uniamerica.estacionamento.repository.ConfiguracaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import java.util.List;
@Service
public class ConfiguracaoService {
    @Autowired
    private ConfiguracaoRepository configuracaoRepository;
    public List<Configuracao> listartudo(){
        return configuracaoRepository.findAll();
    }

    // ...

    private void validarCampos(Configuracao configuracao) {
        Assert.notNull(configuracao.getValorHora(), "Error, campo ValorHora vazio");
        Assert.notNull(configuracao.getValorHoraMulta(), "Error, campo ValorHoraMulta vazio");
        Assert.notNull(configuracao.getInicioExpediente(), "Error, campo InicioExpediente vazio");
        Assert.notNull(configuracao.getFimExpediente(), "Error, campo FimExpediente vazio");
        Assert.notNull(configuracao.getTempoParaDesconto(), "Error, campo TempoParaDesconto vazio");
        Assert.notNull(configuracao.getTempoDeDesconto(), "Error, campo TempoDeDesconto vazio");
        Assert.notNull(configuracao.getVagasVam(), "Error, campo VagasVam vazio");
        Assert.notNull(configuracao.getVagasCarro(), "Error, campo VagasCarro vazio");
        Assert.notNull(configuracao.getVagasMoto(), "Error, campo VagasMoto vazio");
    }

    @Transactional(rollbackFor = Exception.class)
    public Configuracao cadastrar(Configuracao configuracao) {
        validarCampos(configuracao);
        return this.configuracaoRepository.save(configuracao);
    }

    @Transactional(rollbackFor = Exception.class)
    public void atualizar(Long id, Configuracao configuracao) {
        final Configuracao marcaBanco = this.configuracaoRepository.findById(configuracao.getId()).orElse(null);
        Assert.isTrue(marcaBanco.getId().equals(id), "Error, id da URL diferente do body");
        validarCampos(configuracao);
        Assert.isTrue(marcaBanco == null || marcaBanco.getId().equals(configuracao.getId()), "Não foi possível identificar o registro");
        this.configuracaoRepository.save(configuracao);
    }


}
