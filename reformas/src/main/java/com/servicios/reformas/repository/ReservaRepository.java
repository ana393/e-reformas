package com.servicios.reformas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.servicios.reformas.entity.Reserva;
import com.servicios.reformas.entity.Usuario;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
	
	List<Reserva> findByUsuario(Usuario ususario);
	
	
//	@Query(value ="SELECT r FROM Reserva r JOIN FETCH r.itemsReserva", nativeQuery = true)
//	Reserva encuentraPorId(Long id);
}
