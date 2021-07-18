package com.servicios.reformas.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.servicios.reformas.entity.PreReserva;
import com.servicios.reformas.entity.Reserva;
import com.servicios.reformas.entity.Usuario;

public interface ReservaService {
	
	
  Page<Reserva> encuentraTodos(Pageable page);
  
  Reserva guardar(Reserva reservaValida, Usuario usuario, PreReserva preReserva);
  
  List<Reserva> encuentraPorUsuario(Usuario usuario);
  
  void actualizaReserva(Map<String, String> form, Reserva reserva);
  
  void eliminarReserva(Long id);
}
