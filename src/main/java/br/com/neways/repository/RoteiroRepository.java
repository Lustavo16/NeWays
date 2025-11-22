package br.com.neways.repository;

import br.com.neways.model.Roteiro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoteiroRepository extends JpaRepository<Roteiro, Long> {
    List<Roteiro> findByCriadorId(Long idCriador);
}
