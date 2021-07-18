package com.servicios.reformas.entity;

import java.math.BigDecimal;
import java.util.List;

public class PreReserva {
    
	List<ItemReserva> lista;

	public PreReserva(List<ItemReserva> lista) {
		this.lista = lista;
	}
	
	public BigDecimal obtenerTotal() {
		BigDecimal total = new BigDecimal(0);
		for (ItemReserva item : this.lista) {
			total = total.add(item.Total());
		}
		return total;	
	}
	
	 public boolean esVacio() {
		 return lista.isEmpty();
	 }
	
	 public void eliminarItemReserva(ItemReserva itemReserva) {
		 lista.removeIf(item -> item.getId() == itemReserva.getId());
	 }
	 
	 public ItemReserva encuentraItemPorHabitacion(long id) {
			for (ItemReserva item : this.lista) {
				if (item.getHabitacion().getId() == id ) {
					return item;
				}
			}
			return null;
		}
		
	public int obtenNrItemos() {
			return this.lista.size();
		}	
		
	public List<ItemReserva> getCartItems() {
			return lista;
		}
		
	public void setCartItems(List<ItemReserva> lista) {
			this.lista = lista;
		}
}
