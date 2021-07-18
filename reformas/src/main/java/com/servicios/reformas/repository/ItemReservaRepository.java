package com.servicios.reformas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.servicios.reformas.entity.ItemReserva;
import com.servicios.reformas.entity.Usuario;

public interface ItemReservaRepository extends JpaRepository<ItemReserva, Long> {
	
	@EntityGraph(attributePaths = { "habitacion"})
	List<ItemReserva> findAllByUsuarioAndReservaIsNull(Usuario usario);
	
	void deleteAllByUsuarioAndReservaIsNull(Usuario usuario);
	
	int countDistinctByUsuarioAndReservaIsNull(Usuario usuario);
	

}
