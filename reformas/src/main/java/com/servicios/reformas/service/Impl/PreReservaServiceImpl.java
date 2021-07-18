package com.servicios.reformas.service.Impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.servicios.reformas.entity.Habitacion;
import com.servicios.reformas.entity.ItemReserva;
import com.servicios.reformas.entity.PreReserva;
import com.servicios.reformas.entity.Usuario;
import com.servicios.reformas.repository.ItemReservaRepository;
import com.servicios.reformas.service.PreReservaService;

@Service
public class PreReservaServiceImpl implements PreReservaService {
	
	private final ItemReservaRepository itemReservaRepo; 
    
	@Autowired
	public PreReservaServiceImpl(ItemReservaRepository itemReservaRepo) {
		this.itemReservaRepo = itemReservaRepo;
	}

	@Override
	public PreReserva obtenPreReserva(Usuario usuario) {
		return new PreReserva(itemReservaRepo.findAllByUsuarioAndReservaIsNull(usuario));
	}

	@Override
	@Cacheable("nr_itemos")
	public int obtenerNrItemos(Usuario usuario) {
		return itemReservaRepo.countDistinctByUsuarioAndReservaIsNull(usuario);
	}

	@Override
	public ItemReserva encuentraItemPorId(Long id) {
		Optional<ItemReserva> opt = itemReservaRepo.findById(id) ;
		return opt.get();
	}

	@Override
	@CacheEvict(value = "nr_itemos", allEntries = true)
	public ItemReserva anadeItemAPreReserva(Habitacion habitacion, Usuario usuario, int qty) {
		PreReserva preReserva = this.obtenPreReserva(usuario);
		ItemReserva itemReserva = preReserva.encuentraItemPorHabitacion(habitacion.getId());
		if(itemReserva != null) {
			itemReserva.anadeCantidad(qty);
 
			itemReserva = itemReservaRepo.save(itemReserva);
		} else {
			itemReserva = new ItemReserva();
			itemReserva.setUsuario(usuario);
			itemReserva.setHabitacion(habitacion);
			itemReserva.setCantidad(qty);
			itemReserva = itemReservaRepo.save(itemReserva);
		}
		return itemReserva;
	}



	@Override
	@CacheEvict(value = "nr_itemos", allEntries = true)
	public void eliminarItemreserva(ItemReserva item) {
		boolean i = itemReservaRepo.existsById(item.getId());
		if (i) {
		itemReservaRepo.deleteById(item.getId());
		}
	}
	
	@Override
	@CacheEvict(value = "nr_itemos", allEntries = true)
	public void actualizarItemReserva(ItemReserva item, Integer qty) {
		if(qty == null || qty <= 0) {
			this.eliminarItemreserva(item);
		}
	}


	@Override
	@CacheEvict(value = "nr_itemos", allEntries = true)
	public void vaciarPreReserva(Usuario usuario) {
		itemReservaRepo.deleteAllByUsuarioAndReservaIsNull(usuario);
	}

}
