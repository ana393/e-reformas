package com.servicios.reformas.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.servicios.reformas.entity.Usuario;

/*
 * Service para la entidad {@link Usuario} 
 * 
 * @author Ana Tcaci
 */
public interface UsuarioService {
	
	/*
	 * Guarda informacion del usuario
	 * 
	 * @param Usuario usuario
	 * @return el {@link Usuario} objeto que se guarda en la base de datos.
	 */
	Usuario guardar(Usuario usuario);
	
	Page<Usuario> encuentraTodos(Pageable page);
	
	/*
	 * Guarda usuario con set de roles
	 */
	void actualizaUsuario( Map<String, String> form, Usuario usuario);
	
    Usuario findById(Long id);
	
	void delete(Long id);
    
    
    /*
     * Retorna true si el usuario  existe
     * 
     * @param String email
     * @return true si el usuario  existe
     */
	
    boolean usuarioExiste(String email);
	
	

	
}
