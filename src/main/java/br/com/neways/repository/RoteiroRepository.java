package br.com.neways.repository;

import br.com.neways.model.Roteiro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoteiroRepository extends JpaRepository<Roteiro, Long> {
}
