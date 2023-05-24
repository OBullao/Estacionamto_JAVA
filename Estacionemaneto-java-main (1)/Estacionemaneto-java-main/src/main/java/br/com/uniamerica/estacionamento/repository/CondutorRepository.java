package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.Entity.Condutor;
import br.com.uniamerica.estacionamento.Entity.Marca;
import br.com.uniamerica.estacionamento.Entity.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CondutorRepository extends JpaRepository<Condutor, Long> {
    @Modifying
    @Query(value="UPDATE condutores SET ativo = false WHERE id = :id", nativeQuery = true)
    public void desativar(@Param("id") Long id);

    List<Condutor> findByAtivo(boolean ativo);
    @Query("from Condutor where cpf = :cpf AND id != :id")
    public List<Condutor> findCpf(@Param("cpf") final String cpf,@Param("id")final Long id);

    @Query("from Condutor where telefone = :telefone AND id != :id")
    public List<Condutor> findTelefonesEditar(@Param("telefone")final String telefone, @Param("id")final Long id);

    @Query("from Condutor where telefone = :telefone")
    public List<Condutor> findTelefonesCadastro(@Param("telefone")final String Telefone);

    @Query("from Condutor where cpf = :cpf")
    public List<Condutor> findCpfCadastro(@Param("cpf")final String cpf);

    @Query("from Movimentacao where condutor = :condutor")
    public List<Movimentacao> findCondutor(@Param("condutor") final Condutor condutor);

}
