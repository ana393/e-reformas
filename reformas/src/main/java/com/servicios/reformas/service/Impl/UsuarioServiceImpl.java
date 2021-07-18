package com.servicios.reformas.service.Impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.servicios.reformas.entity.Role;
import com.servicios.reformas.entity.Usuario;
import com.servicios.reformas.repository.UsuarioRepository;
import com.servicios.reformas.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements  UsuarioService {
	
	
	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;

	private static final Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);

	@Autowired
	public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	
	/*
	  * @param email. Metodo para encontrar  el usuario  a través de  Optional
	  */
	@Transactional
	public Optional<Usuario> findByEmail(String email){
		return usuarioRepository.findByEmail(email);
	}
	@Override
	/*
	 * @param String email. Metodo de comprobacion si el usuario existe en la base de datos.
	 */
	
	public boolean usuarioExiste(String email) {
		return findByEmail(email).isPresent();
		
	}
	
	

	@Override
	public Usuario guardar(Usuario usuario) {
		if (usuario == null || usuario.getEmail().isEmpty()) {
			throw new RuntimeException();
		}
		usuario.setRoles(Collections.singleton(Role.USER));
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		
		Usuario registered = usuarioRepository.save(usuario);
		logger.info("IN guardar() - user: {} guardado", registered);
		return registered;
	}

	
	@Override
	public Page<Usuario> encuentraTodos(Pageable page) {
		return usuarioRepository.findAll(page);
	}
	
	/*
	 * Guarda usuario con set de roles
	 * 
	 * @param
	 */
	@Override
	public void actualizaUsuario( Map<String, String> form, Usuario usuario) {
		Set <String> roles = Arrays.stream(Role.values())
				.map(Role::name)
				.collect(Collectors.toSet());
		usuario.getRoles().clear();
		
		for (String key : form.keySet()) {
			if (roles.contains(key)) {
				usuario.getRoles().add(Role.valueOf(key));
			}
		}
		
		usuarioRepository.save(usuario);
	}

	@Override
	public Usuario findById(Long id) {
		Usuario result = usuarioRepository.findById(id).orElse(null);
		return result;
	}

	@Override
	public void delete(Long id) {
		usuarioRepository.deleteById(id);
		logger.info("IN delete() - user with id: {} successfully deleted");
	}	
}
