package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.Entity.*;
import br.com.uniamerica.estacionamento.Entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    @Query("SELECT COUNT(m) FROM Veiculo m WHERE m.placa = :nome")
    public int countByplaca(@Param("nome") String nome);
    @Query("from Movimentacao where veiculo = :veiculo_id")
    public List<Movimentacao> findByVeiculo (@Param("veiculo_id")final Veiculo veiculo_id);

    List<Veiculo> findByAtivo(boolean ativo);


    @Query("from Veiculo where ativo = true")
    public List<Veiculo> findByAtivos();

    @Query("from Veiculo where modelo = :modelo")
    public List<Veiculo> findVeiculosByModelo(@Param("modelo") final Modelo modelo);

    @Query("from Veiculo where placa = :placa")
    public List<Veiculo> findVeiculosByPlaca(@Param("placa") final String placa);

}
