package br.pucminas.crc.repository.lancamento;

import br.pucminas.crc.model.Lancamento;
import br.pucminas.crc.repository.filter.LancamentoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by luiz on 17/10/17.
 */
public interface LancamentoRepositoryQuery
{
    Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
}// end interface LancamentoRepositoryQuery
