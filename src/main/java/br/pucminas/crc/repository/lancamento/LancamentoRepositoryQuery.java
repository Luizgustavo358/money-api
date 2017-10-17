package br.pucminas.crc.repository.lancamento;

import br.pucminas.crc.model.Lancamento;
import br.pucminas.crc.repository.filter.LancamentoFilter;

import java.util.List;

/**
 * Created by luiz on 17/10/17.
 */
public interface LancamentoRepositoryQuery
{
    public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter);
}// end interface LancamentoRepositoryQuery
