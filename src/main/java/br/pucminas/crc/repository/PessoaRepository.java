package br.pucminas.crc.repository;

import br.pucminas.crc.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by luiz on 09/10/17.
 */
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
