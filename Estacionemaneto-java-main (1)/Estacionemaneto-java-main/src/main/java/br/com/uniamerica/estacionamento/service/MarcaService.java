package br.com.uniamerica.estacionamento.service;
import br.com.uniamerica.estacionamento.Entity.Marca;
import br.com.uniamerica.estacionamento.Entity.Modelo;
import br.com.uniamerica.estacionamento.repository.MarcaRepository;
import br.com.uniamerica.estacionamento.repository.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import java.util.List;
@Service
public class MarcaService {
    @Autowired
    private MarcaRepository  marcaRepository;
    @Autowired
    private ModeloRepository modeloRepository;
    public List<Marca> listartudo(){
        return marcaRepository.findAll();
    }
    @Transactional(rollbackFor = RuntimeException.class)
    public Marca cadastrar(Marca marca) {
        Assert.notNull(marca.getNome(), "Error, campo nome vazio");
        Assert.isTrue(marca.getNome().length() < 20, "Error: limite máximo de caracteres (20)");

        int count = this.marcaRepository.countByNome(marca.getNome());
        Assert.isTrue(count == 0, "Erro: A marca já existe");
        return this.marcaRepository.save(marca);
    }
    @Transactional(rollbackFor = Exception.class)
    public void   atualizar(Long id, Marca marca) {
        final Marca marcaBanco = this.marcaRepository.findById(marca.getId()).orElse(null);
        Assert.isTrue(marcaBanco.getId().equals(id) ,"Error id da URL diferente do body");

        Assert.isTrue(marcaBanco == null || marcaBanco.getId().equals(marca.getId()),"nao foi possivel identificar o registro");
        this.marcaRepository.save(marca);
    }
    @Transactional(rollbackFor = Exception.class)
    public void deletar(final Marca marca) {
        final Marca marcaBanco = this.marcaRepository.findById(marca.getId()).orElse(null);
        List<Modelo> modeloLista = this.marcaRepository.findModelo(marcaBanco);
        if (modeloLista.isEmpty()) {
            this.marcaRepository.delete(marcaBanco);
        } else {
            for (Modelo modelo : modeloLista) {
                this.modeloRepository.delete(modelo);
                System.out.println("Modelo apagado: " + modelo.getId());
            }
            this.marcaRepository.delete(marcaBanco);
        }
    }
















}
