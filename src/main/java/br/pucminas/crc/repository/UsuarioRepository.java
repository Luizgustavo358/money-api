package br.pucminas.crc.repository;

import br.pucminas.crc.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by luiz on 26/10/17.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long>
{
    Optional<Usuario> findByEmail(String email);
}// end interface UsuarioRepository
