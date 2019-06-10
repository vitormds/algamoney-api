package com.example.algamoneyapi.resource;


import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.example.algamoneyapi.event.RecursoCriadoEvent;
import com.example.algamoneyapi.model.Pessoa;
import com.example.algamoneyapi.repository.PessoaRepository;
import com.example.algamoneyapi.service.PessoaService;



@RestController
@RequestMapping("/pessoas")
public class PessoaResource {
@Autowired private PessoaRepository pessoaRepository;
@Autowired private PessoaService pessoaService;
@Autowired private ApplicationEventPublisher publisher;

	@GetMapping
	public List<Pessoa> list(){
		return pessoaRepository.findAll();
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Pessoa >buscarpelocodigo(@PathVariable Long codigo){
		
		Pessoa p = pessoaRepository.findOne(codigo);
		return p != null ? ResponseEntity.ok(p) : ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<Pessoa> Criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response){
		Pessoa p = pessoaRepository.save(pessoa);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, p.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(p);
	}
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long codigo) {
		pessoaRepository.delete(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Pessoa> atualizar(@PathVariable Long codigo, @Valid @RequestBody Pessoa pessoa){
		Pessoa pessoaSalva = pessoaService.atualizar(codigo, pessoa);
		return ResponseEntity.ok(pessoaSalva);
	}
	@PutMapping("/{codigo}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarPropriedadeAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo) {
		pessoaService.atualizarPropriedadeAtivo(codigo, ativo);
	}
}
