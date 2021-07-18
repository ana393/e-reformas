package com.servicios.reformas.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.servicios.reformas.entity.Habitacion;
 /*
  * Capa interfaz del Servicio para la entidad {@link Habitacion} 
  * 
  * @author Ana Tcaci
  */

/**
 * @author atcac
 *
 */
public interface HabitacionService {
  
	/**
	 * @param pagina
	 * @return
	 */
	Page<Habitacion> encuentraTodo(Pageable pagina);
	
	Page<Habitacion> encuentraPorNombre(String nombre, Pageable pagina);
	
	Page<Habitacion> encuentraPorCategoria(List<Long> id, Pageable pagina);
	
	Page<Habitacion> encuentraPorPrecio(BigDecimal precioInicial, BigDecimal precioFinal, Pageable pagina);
	
	BigDecimal maxHabiatcionPrecio();
	   
	BigDecimal minHabitacionPrecio();
	
	Habitacion encuentraPorId(long id);
	
	Habitacion guardaHabitacion(Habitacion habitacion, MultipartFile Fichero);
	
	void eliminarHabitacionPorId(Long id);

}
