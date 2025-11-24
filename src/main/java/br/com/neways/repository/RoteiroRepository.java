package br.com.neways.repository;

import br.com.neways.model.Roteiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoteiroRepository extends JpaRepository<Roteiro, Long> {
    List<Roteiro> findByCriadorId(Long idCriador);

    @Query("SELECT r FROM Roteiro r WHERE " +
            "LOWER(r.nome) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(r.descricao) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(r.criador.nomeCompleto) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(r.criador.nomeDeUsuario) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Roteiro> consultaGeral(@Param("termo") String termo);
}
