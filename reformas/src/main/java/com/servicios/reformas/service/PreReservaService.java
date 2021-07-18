package com.servicios.reformas.service;

import com.servicios.reformas.entity.Habitacion;
import com.servicios.reformas.entity.ItemReserva;
import com.servicios.reformas.entity.PreReserva;
import com.servicios.reformas.entity.Usuario;

public interface PreReservaService {

	PreReserva obtenPreReserva(Usuario usuario);
	
	int obtenerNrItemos(Usuario usuario);
	
	ItemReserva encuentraItemPorId(Long id);
	
	ItemReserva anadeItemAPreReserva(Habitacion habitacion,Usuario usuario, int qty);
		
	void eliminarItemreserva(ItemReserva item);
	
	void actualizarItemReserva(ItemReserva item, Integer qty);
	
	void vaciarPreReserva(Usuario usuario);
}
