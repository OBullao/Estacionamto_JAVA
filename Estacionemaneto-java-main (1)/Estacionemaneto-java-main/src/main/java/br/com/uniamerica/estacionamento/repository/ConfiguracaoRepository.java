package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.Entity.Configuracao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ConfiguracaoRepository extends JpaRepository<Configuracao, Long> {
}
