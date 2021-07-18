package com.servicios.reformas.service.Impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.servicios.reformas.entity.Usuario;
import com.servicios.reformas.repository.UsuarioRepository;

@Transactional
@Service("UsuarioSecurityService")
public class UsuarioSecurityService  implements UserDetailsService{
	
	private final UsuarioRepository usuarioRepository;
	private static final Logger logger = LoggerFactory.getLogger(UsuarioSecurityService.class);
	
	@Autowired
	public UsuarioSecurityService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	
	/*
	 * Retorna el usuario de la base de datos con el mismo email que el valor del parametro
	 * 
	 * @param email email del usuario a devolver
	 * @return {@link Usuario} Objeto Clase  => transmitido luego la configuracion
	 */
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User doesn´t exists"));
		logger.info("IN loadUserByUserName(): - {}", usuario.toString());
		return usuario;
	}
	
	/*
	 * Metodo para autentificar al usuario según la interfaz userDetails;
	 * @param String email. 
	*/
	public void authenticationUser(String email) {
		UserDetails userDetails = loadUserByUsername(email);
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
					userDetails.getAuthorities());
		logger.info("IN authenticationUser(): auth: {}", authentication.toString());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	} 
		
}
