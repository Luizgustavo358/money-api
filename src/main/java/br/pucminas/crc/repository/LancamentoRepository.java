package br.pucminas.crc.repository;

import br.pucminas.crc.model.Lancamento;
import br.pucminas.crc.repository.lancamento.LancamentoRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by luiz on 16/10/17.
 */
public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery
{
}// end interface LancamentoRepository
