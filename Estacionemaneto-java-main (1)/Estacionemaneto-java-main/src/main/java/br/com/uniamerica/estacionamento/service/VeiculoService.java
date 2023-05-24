package br.com.uniamerica.estacionamento.service;
import br.com.uniamerica.estacionamento.Entity.Modelo;
import br.com.uniamerica.estacionamento.Entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
@Service
public class VeiculoService {
    @Autowired
    private VeiculoRepository Repository;
    public List<Veiculo> listartudo(){
        return this.Repository.findAll();
    }
    private final String regexPlaca = "^[a-zA-Z]{4}-\\d{4}$";
    @Transactional(rollbackFor = Exception.class)
    public Veiculo cadastrar(Veiculo cadastrar) {

        int count = this.Repository.countByplaca(cadastrar.getPlaca());
        Assert.isTrue(count == 0, "<Erro> A placa já existe");
        Assert.isTrue(cadastrar.getPlaca().matches(regexPlaca), "Error: A placa está errada");
        Assert.isTrue(cadastrar.getPlaca().length() < 10, "Error: Placa ultrapassou o limite máximo de caracteres (10)");
        Assert.isTrue(cadastrar.getAno() > 1990 && cadastrar.getAno() <= 2023, "Error: Ano errado");
        Assert.notNull(cadastrar.getAno(), "Error, campo ano vazio");
        Assert.notNull(cadastrar.getPlaca(), "Error, campo placa vazio");
        Assert.notNull(cadastrar.getCor(), "Error, campo cor vazio");
        Assert.notNull(cadastrar.getTipo(), "Error, campo tipo vazio");


        return this.Repository.save(cadastrar);
    }

    @Transactional(rollbackFor = Exception.class)
    public void atualizar(Long id, Veiculo atualizar) {
        final Veiculo marcaBanco = this.Repository.findById(atualizar.getId()).orElse(null);
        Assert.isTrue(marcaBanco.getId().equals(id) ,"Error id da URL diferente do body");


        // pq isso nao da ceto
        Assert.isTrue(marcaBanco == null || marcaBanco.getId().equals(atualizar.getId()),"nao foi possivel identificar o registro");
        this.Repository.save(atualizar);
    }




}
