package com.example.algamoneyapi.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.algamoneyapi.model.Usuario;
import com.example.algamoneyapi.repository.UsuarioRepository;
import com.example.algamoneyapi.util.UsuarioSistema;

@Service
public class AppUserDetailsService implements UserDetailsService{
	@Autowired private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);
		Usuario u = usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Usuário ou senha incorretos"));
		return new UsuarioSistema(u, getPermissoes(u));
	}
	//Lista de permissões do usuário
	private Collection<? extends GrantedAuthority> getPermissoes(Usuario u) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		u.getPermissoes().forEach(p -> authorities.add(new SimpleGrantedAuthority
				(p.getDescricao().toUpperCase())));
		return authorities;
	}
	
}
