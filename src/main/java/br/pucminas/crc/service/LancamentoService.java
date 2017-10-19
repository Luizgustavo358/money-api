package br.pucminas.crc.service;

import br.pucminas.crc.model.Lancamento;
import br.pucminas.crc.model.Pessoa;
import br.pucminas.crc.repository.LancamentoRepository;
import br.pucminas.crc.repository.PessoaRepository;
import br.pucminas.crc.repository.filter.LancamentoFilter;
import br.pucminas.crc.service.exception.PessoaInexistenteOuInativaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by luiz on 16/10/17.
 */

@Service
public class LancamentoService implements LancamentosService
{
    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private LancamentoRepository lancamentoRepository;

    public Lancamento salvar(Lancamento lancamento)
    {
        Pessoa pessoa = pessoaRepository.findOne(lancamento.getPessoa().getCodigo());

        // verifica se existe a pessoa
        if(pessoa == null || pessoa.isInativo())
        {
            throw new PessoaInexistenteOuInativaException();
        }// end if

        return lancamentoRepository.save(lancamento);
    }// end salvar()

    @Override
    public Page<Lancamento> listAllByPage(LancamentoFilter lancamentoFilter, Pageable pageable)
    {
        return lancamentoRepository.findAll(pageable);
    }// end listAllbyPage()
}// end class LancamentoService
