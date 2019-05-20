package com.example.algamoneyapi.resource;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.algamoneyapi.event.RecursoCriadoEvent;
import com.example.algamoneyapi.model.Pessoa;
import com.example.algamoneyapi.repository.PessoaRepository;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {
@Autowired PessoaRepository pessoaRepository;
@Autowired ApplicationEventPublisher publisher;

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
		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoa.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(p);
	}
}
