package br.pucminas.crc.repository.lancamento;

import br.pucminas.crc.model.*;
import br.pucminas.crc.repository.filter.LancamentoFilter;
import br.pucminas.crc.repository.projection.ResumoLancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luiz on 17/10/17.
 */
public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery
{
    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable)
    {
        // definir dados
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
        Root<Lancamento> root = criteria.from(Lancamento.class);

        // criar as restricoes
        Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<Lancamento> query = manager.createQuery(criteria);

        adicionarRestricoesDePeginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
    }// end filtrar()

    @Override
    public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable)
    {
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<ResumoLancamento> criteria = builder.createQuery(ResumoLancamento.class);

        Root<Lancamento> root = criteria.from(Lancamento.class);

        criteria.select(builder.construct(ResumoLancamento.class,
                                          root.get(Lancamento_.codigo),
                                          root.get(Lancamento_.descricao),
                                          root.get(Lancamento_.dataVencimento),
                                          root.get(Lancamento_.dataPagamento),
                                          root.get(Lancamento_.valor),
                                          root.get(Lancamento_.tipo),
                                          root.get(Lancamento_.categoria).get(Categoria_.nome),
                                          root.get(Lancamento_.pessoa).get(Pessoa_.nome)));

        // criar as restricoes
        Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<ResumoLancamento> query = manager.createQuery(criteria);

        adicionarRestricoesDePeginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
    }// end resumir()

    private Long total(LancamentoFilter lancamentoFilter)
    {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Lancamento> root = criteria.from(Lancamento.class);

        Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);

        criteria.where(predicates);

        criteria.select(builder.count(root));

        return manager.createQuery(criteria).getSingleResult();
    }// end total()

    private void adicionarRestricoesDePeginacao(TypedQuery<?> query, Pageable pageable)
    {
        int paginaAtual = pageable.getPageNumber();
        int totalRegistrosPorPagina = pageable.getPageSize();
        int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalRegistrosPorPagina);
    }// end adicionarRestricoesDePeginacao()

    private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder builder, Root<Lancamento> root)
    {
        List<Predicate> predicates = new ArrayList<>();

        if(!StringUtils.isEmpty(lancamentoFilter.getDescricao()))
        {
            predicates.add(builder.like(
                    builder.lower(root.get("descricao")),
                    "%" + lancamentoFilter.getDescricao().toLowerCase() + "%"));
        }// end if

        if(lancamentoFilter.getDataVencimentoDe() != null)
        {
            predicates.add(builder.greaterThanOrEqualTo( root.get("data_vencimento"),
                    lancamentoFilter.getDataVencimentoDe()));
        }// end if

        if(lancamentoFilter.getDataVencimentoAte() != null)
        {
            predicates.add(
                    builder.lessThanOrEqualTo(root.get("data_pagamento"),
                            lancamentoFilter.getDataVencimentoAte()));
        }// end if

        return predicates.toArray(new Predicate[predicates.size()]);
    }// end criarRestricoes()
}// end class LancamentoRepositoryImpl
