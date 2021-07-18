package com.servicios.reformas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.servicios.reformas.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	/*
	 * Retorna el usuario de la base de datos con el mismo email que el valor del parametro
	 * 
	 * @param email  del usuario a devolver
	 */
   Optional<Usuario> findByEmail(String email);
 
}
