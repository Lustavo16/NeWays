package br.com.neways.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.neways.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
