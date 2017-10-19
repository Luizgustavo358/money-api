package br.pucminas.crc.service;

import br.pucminas.crc.model.Lancamento;
import br.pucminas.crc.repository.filter.LancamentoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by luiz on 19/10/17.
 */
public interface LancamentosService
{
    Page<Lancamento> listAllByPage(LancamentoFilter lancamentoFilter, Pageable pageable);
}// end interface LancamentosService
