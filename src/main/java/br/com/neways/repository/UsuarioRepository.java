package br.com.neways.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.neways.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNomeDeUsuario(String nomeDeUsuario);
}
