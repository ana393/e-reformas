 package com.servicios.reformas.entity;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "items_reserva")
public class ItemReserva {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int cantidad;
	/*
	 * @ManyToOne, Relacion unidireccional
	 */
	@OneToOne
	@JoinColumn(name="habitacion_id")
	private Habitacion habitacion;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="usuario_id")
	private Usuario usuario;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade= CascadeType.ALL)
	@JoinColumn(name="reserva_id")
	private Reserva reserva;
	
	
	public boolean actualizarCantidad(Integer cantidad) {
		return cantidad == null || cantidad <= 0;
	}
	
	public BigDecimal Total() {
		return new BigDecimal(habitacion.getPrecio()).multiply(new BigDecimal(cantidad));
	}
	
	public void anadeCantidad(int cantidad) {
		if(cantidad > 0) {
			this.cantidad = this.cantidad + cantidad;
		}
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public Habitacion getHabitacion() {
		return habitacion;
	}

	public void setHabitacion(Habitacion habitacion) {
		this.habitacion = habitacion;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		ItemReserva item = (ItemReserva) o;
		return Objects.equals(id, item.getId());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
