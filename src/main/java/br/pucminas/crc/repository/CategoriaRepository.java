package br.pucminas.crc.repository;

import br.pucminas.crc.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by luiz on 04/10/17.
 */
public interface CategoriaRepository extends JpaRepository<Categoria, Long>
{
}// end interface CategoriaRepository
