package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.Entity.Marca;
import br.com.uniamerica.estacionamento.Entity.Modelo;
import br.com.uniamerica.estacionamento.Entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {
    List<Marca> findByAtivo(boolean ativo);
    @Query("from Modelo where marca = :marca")
    public List<Modelo> findModelo (@Param("marca") final Marca marca);
    @Query("SELECT COUNT(m) FROM Marca m WHERE m.nome = :nome")
    public int countByNome(@Param("nome") String nome);


}
