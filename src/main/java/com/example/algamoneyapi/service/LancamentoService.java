package com.example.algamoneyapi.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.algamoneyapi.exceptionhandler.PessoaInexistenteOuInativaException;
import com.example.algamoneyapi.model.Lancamento;
import com.example.algamoneyapi.model.Pessoa;
import com.example.algamoneyapi.repository.LancamentoRepository;
import com.example.algamoneyapi.repository.PessoaRepository;
@Service
public class LancamentoService {
	@Autowired LancamentoRepository lancamentoRepository;
	@Autowired PessoaRepository pessoaRepository;

	public Lancamento atualizar(Long codigo, Lancamento l) throws IllegalAccessException {
		Lancamento lancamento = existeLancamento(codigo);
		if(!lancamento.getPessoa().equals(l.getPessoa())) {
			validarPessoa(l);
		}
				//Faz a copia dos dados alterados exceto codigo
				BeanUtils.copyProperties(l, lancamento,"codigo");

		 		return lancamentoRepository.save(lancamento);
	}

	private void validarPessoa(Lancamento l) {
		
		Pessoa pessoa = null;
		if(l.getPessoa().getCodigo() != null) {
			
		pessoa = pessoaRepository.findOne(l.getPessoa().getCodigo());
		}
		
		if(pessoa == null || pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
	}

	private Lancamento existeLancamento(Long codigo) throws IllegalAccessException {
		Lancamento lancamento = lancamentoRepository.findOne(codigo);
		if(lancamento == null) {
			throw new IllegalAccessException();
		}
		return lancamento;
	}
	
}
