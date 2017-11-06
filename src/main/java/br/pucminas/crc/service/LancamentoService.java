package br.pucminas.crc.service;

import br.pucminas.crc.model.Lancamento;
import br.pucminas.crc.model.Pessoa;
import br.pucminas.crc.repository.LancamentoRepository;
import br.pucminas.crc.repository.PessoaRepository;
import br.pucminas.crc.repository.filter.LancamentoFilter;
import br.pucminas.crc.repository.projection.ResumoLancamento;
import br.pucminas.crc.service.exception.PessoaInexistenteOuInativaException;
import org.springframework.beans.BeanUtils;
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
        validarPessoa(lancamento);

        return lancamentoRepository.save(lancamento);
    }// end salvar()

    private void validarPessoa(Lancamento lancamento) {
        Pessoa pessoa = null;

        if (lancamento.getPessoa().getCodigo() != null) {
            pessoa = pessoaRepository.findOne(lancamento.getPessoa().getCodigo());
        }// end if

        // verifica se existe a pessoa
        if(pessoa == null || pessoa.isInativo()) {
            throw new PessoaInexistenteOuInativaException();
        }// end if
    }// end validarPessoa()

    public Lancamento atualizar(Long codigo, Lancamento lancamento) {
        Lancamento lancamentoSalvo = buscarLancamentoExistente(codigo);

        if (!lancamento.getPessoa().equals(lancamentoSalvo.getPessoa())) {
            validarPessoa(lancamento);
        }// end if

        BeanUtils.copyProperties(lancamento, lancamentoSalvo, "codigo");

        return lancamentoRepository.save(lancamentoSalvo);
    }// end atualizar()

    private Lancamento buscarLancamentoExistente(Long codigo) {
        Lancamento lancamentoSalvo = lancamentoRepository.findOne(codigo);

        if (lancamentoSalvo == null) {
            throw new IllegalArgumentException();
        }// end if

        return lancamentoSalvo;
    }// end buscarLancamentoExistente()

    @Override
    public Page<Lancamento> listAllByPage(LancamentoFilter lancamentoFilter, Pageable pageable)
    {
        return lancamentoRepository.findAll(pageable);
    }// end listAllbyPage()

    @Override
    public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable)
    {
        return lancamentoRepository.resumir(lancamentoFilter, pageable);
    }// end listAllbyPage()
}// end class LancamentoService
