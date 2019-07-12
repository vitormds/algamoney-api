package com.example.algamoneyapi.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoneyapi.event.RecursoCriadoEvent;
import com.example.algamoneyapi.model.Lancamento;
import com.example.algamoneyapi.repository.LancamentoRepository;
import com.example.algamoneyapi.repository.filter.LancamentoFilter;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {
	@Autowired private LancamentoRepository  lancamentoRepository;
	@Autowired private ApplicationEventPublisher publisher;

	@GetMapping
	public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable){
		return lancamentoRepository.filtrar(lancamentoFilter,pageable);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Lancamento> buscapelocodigo(@PathVariable Long codigo) {
		Lancamento l = lancamentoRepository.findOne(codigo);
		return l != null ? ResponseEntity.ok(l) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Lancamento> Criar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response ) {
		Lancamento l = lancamentoRepository.save(lancamento);	
		publisher.publishEvent(new RecursoCriadoEvent(this, response, l.getCodigo())); 
		return ResponseEntity.status(HttpStatus.CREATED).body(l);
	}
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long codigo) {
		lancamentoRepository.delete(codigo);
	}

	

}
