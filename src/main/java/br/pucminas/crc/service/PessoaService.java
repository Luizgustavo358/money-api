package br.pucminas.crc.service;

import br.pucminas.crc.model.Pessoa;
import br.pucminas.crc.repository.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

/**
 * Created by luiz on 11/10/17.
 */

@Service
public class PessoaService
{
    @Autowired
    private PessoaRepository pessoaRepository;

    public Pessoa atualizar(Long codigo, Pessoa pessoa)
    {
        Pessoa pessoaSalva = buscarPessoaPeloCodigo(codigo);

        BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");

        return pessoaRepository.save(pessoaSalva);
    }// end atualizar()

    public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo)
    {
        Pessoa pessoaSalva = buscarPessoaPeloCodigo(codigo);

        pessoaSalva.setAtivo(ativo);

        pessoaRepository.save(pessoaSalva);
    }// end atualizarPropriedadeAtivo()

    private Pessoa buscarPessoaPeloCodigo(Long codigo)
    {
        Pessoa pessoaSalva = pessoaRepository.findOne(codigo);

        if(pessoaSalva == null)
        {
            throw new EmptyResultDataAccessException(1);
        }// end if

        return pessoaSalva;
    }// end buscarPessoaPeloCodigo()
}// end class PessoaService
