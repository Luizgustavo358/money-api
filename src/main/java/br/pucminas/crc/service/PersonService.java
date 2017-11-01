package br.pucminas.crc.service;

import br.pucminas.crc.model.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by luiz on 18/10/17.
 */
public interface PersonService
{
    Page<Pessoa> listAllByPage(Pageable pageable);
}// end interface PersonService
