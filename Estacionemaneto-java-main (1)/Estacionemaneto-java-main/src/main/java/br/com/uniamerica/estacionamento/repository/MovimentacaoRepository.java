package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.Entity.Condutor;
import br.com.uniamerica.estacionamento.Entity.Marca;
import br.com.uniamerica.estacionamento.Entity.Movimentacao;
import br.com.uniamerica.estacionamento.Entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
    List<Movimentacao> findByAtivo(boolean ativo);


    @Query("from Movimentacao where saida = null")
    public List<Movimentacao> findSaidas();

    @Query("from Movimentacao where veiculo = :veiculo AND saida = null AND id != :id")
    public List<Movimentacao> findVeiculo(@Param("veiculo")final Veiculo veiculo, @Param("id")final Long id);

    @Query("from Condutor where id = :id")
    public List<Condutor> findCondutorMov(@Param("id")final Long id);

    @Query("from Veiculo where id = :id")
    public List<Veiculo> findVeiculoMov(@Param("id")final Long id);


    @Query("from Movimentacao where saida = null")
    public  List<Movimentacao> findAbertas();

    @Query("from Movimentacao where condutor = :condutor")
    public List<Movimentacao> findMovimentacoesByCondutor(@Param("condutor") final Condutor condutor);

    @Query("from Movimentacao where veiculo = :veiculo")
    public List<Movimentacao> findMovimentacoesByVeiculo(@Param("veiculo") final Veiculo veiculo);




}
