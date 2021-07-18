package com.servicios.reformas.service.Impl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.servicios.reformas.entity.ItemReserva;
import com.servicios.reformas.entity.PreReserva;
import com.servicios.reformas.entity.Reserva;
import com.servicios.reformas.entity.StatusReserva;
import com.servicios.reformas.entity.Usuario;
import com.servicios.reformas.repository.ItemReservaRepository;
import com.servicios.reformas.repository.ReservaRepository;
import com.servicios.reformas.service.ReservaService;


@Service
public class ReservaServiceImpl implements ReservaService {
	
	private final ReservaRepository reservaRepository;
	private final ItemReservaRepository itemRepository;
	private static final Logger log = LoggerFactory.getLogger(ReservaServiceImpl.class);
	
	@Autowired
	public ReservaServiceImpl(ReservaRepository reservaRepository, ItemReservaRepository itemRepository) {
		this.reservaRepository = reservaRepository;
		this.itemRepository = itemRepository;
	}

	
	@Override
	public Page<Reserva> encuentraTodos(Pageable page) {
		return reservaRepository.findAll(page);
	}
	
  /*
   * @CacheEvict(value = "nr_itemos", allEntries = true), 
   * 			anotacion que permite eliminar uno o mas valores de la cache,
   * 		    teniendo posibilidad de cargar datos nuevos despues de la execusion de este metodo
   */
	@Override
	@Transactional
	@CacheEvict(value = "nr_itemos", allEntries = true)
	public Reserva guardar(Reserva reservaValida, Usuario usuario, PreReserva preReserva) {
		
        Reserva reserva = new Reserva();
		reserva.setNombre(reservaValida.getNombre());
		reserva.setTelefono(reservaValida.getTelefono());
		reserva.setEmail(reservaValida.getEmail());
		reserva.setFechaReserva(LocalDate.now());
		reserva.setUsuario(usuario);
		reserva.setPrecioTotal(preReserva.obtenerTotal());
		reserva.setEstado(StatusReserva.PENDIENTE);
		reservaRepository.save(reserva);
		
		List<ItemReserva> lista = preReserva.getCartItems();
		for (ItemReserva item : lista) {
			item.setReserva(reserva);
			itemRepository.save(item);
			
		}
		
		return reserva;
	}

	@Override
	public List<Reserva> encuentraPorUsuario(Usuario usuario) {
		return reservaRepository.findByUsuario(usuario);
	}
	
	/*
	 * Guarda reserva con distinto estado
	 * 
	 * @param
	 */
	@Override
	public void actualizaReserva( Map<String, String> form, Reserva reserva) {
		Set <String> estados = Arrays.stream(StatusReserva.values())
				.map(StatusReserva::name)
				.collect(Collectors.toSet());
		
		for (String key : form.keySet()) {
			if (estados.contains(key)) {
				reserva.setEstado(StatusReserva.valueOf(key));
				log.info("IN actualizarReserva(): {}", reserva.getEstado());
				reservaRepository.save(reserva);
			}
		}	
	}


	@Override
	public void eliminarReserva(Long id) {
		reservaRepository.deleteById(id);
	}
	

}
