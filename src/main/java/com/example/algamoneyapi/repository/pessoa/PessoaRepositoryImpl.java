package com.example.algamoneyapi.repository.pessoa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.example.algamoneyapi.model.Pessoa;
import com.example.algamoneyapi.repository.filter.PessoaFilter;

import aj.org.objectweb.asm.Type;

public class PessoaRepositoryImpl implements PessoaRepositoryQuery {

	@Autowired
	private EntityManager entity;
	
	@Override
	public Page<Pessoa> pesquisar(PessoaFilter filter, Pageable page) {
		CriteriaBuilder builder = entity.getCriteriaBuilder();
		CriteriaQuery<Pessoa> query = builder.createQuery(Pessoa.class);
		Root<Pessoa> root = query.from(Pessoa.class);
		Predicate[] predicates = CriarRestricoes(filter, builder, root);
		query.where(predicates);
		TypedQuery<Pessoa> typedQuery = entity.createQuery(query);
		adicionarRestricoesDePaginacao(typedQuery, page);
		
		return new PageImpl<>(typedQuery.getResultList(), page,total(filter));
	}

	/**
	 * Calcula a quantidade de elementos
	 * totais retornado pela consulta
	 * @param pessoaFilter
	 * @return
	 */

	private Long total(PessoaFilter pessoaFilter) {

 		CriteriaBuilder builder = entity.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Pessoa> root = criteria.from(Pessoa.class);

 		Predicate[] predicates = CriarRestricoes(pessoaFilter, builder, root);
		criteria.where(predicates);

 		criteria.select(builder.count(root));

 		return entity.createQuery(criteria).getSingleResult();
	}


	private void adicionarRestricoesDePaginacao(TypedQuery<Pessoa> typedQuery, Pageable page) {
		int paginaAtual = page.getPageNumber();
		int totalRegistroPorPagina = page.getPageSize();		
		int primeiroRegistroDaPagina =  paginaAtual * totalRegistroPorPagina;
		typedQuery.setFirstResult(primeiroRegistroDaPagina);
		typedQuery.setMaxResults(totalRegistroPorPagina);
		
		
		
	}



	private Predicate[] CriarRestricoes(PessoaFilter filter, CriteriaBuilder builder, Root<Pessoa> root) {
		
		
		/*Sem metamodel
		  predicates.add(builder.like(builder.lower(root.get("descricao")),"%" + lancamentoFilter.getDescricao().toLowerCase() + "%"));
		*/
		List<Predicate> predicates = new ArrayList<>();
		if(!StringUtils.isEmpty(filter.getNome())) {
			predicates.add(builder.like(builder.lower(root.get("nome")), "%" +
		filter.getNome().toLowerCase() + "%" ));
		}
		/* 
		 * Como o array e variavel cria-se
		 * uma lista de array
		 */
		return predicates.toArray(new Predicate[predicates.size()]);
	}




}
