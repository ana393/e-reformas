package com.servicios.reformas.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;



@Entity(name="reservas")
public class Reserva {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private BigDecimal precioTotal;
	private LocalDate fechaReserva;
	
	@NotBlank(message = "Complete este campo, porfavor.")
	private String nombre;
	
	@NotBlank(message = "Complete este campo, porfavor.")
	private String telefono;
	
	@Email(message="El email es incorrecto.")
	@NotBlank(message = "Complete este campo, porfavor.")
	private String email;
	
	@OneToMany(mappedBy="reserva", cascade=CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
	private List<ItemReserva>  itemsReserva = new ArrayList<>();
	
	@Column(name = "estado_reserva")
	@Enumerated(EnumType.STRING)
	private StatusReserva estado;
	
	@ManyToOne
	private Usuario usuario;

	
	public Reserva() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(BigDecimal precioTotal) {
		this.precioTotal = precioTotal;
	}

	public LocalDate getFechaReserva() {
		return fechaReserva;
	}

	public void setFechaReserva(LocalDate fechaReserva) {
		this.fechaReserva = fechaReserva;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	

	public StatusReserva getEstado() {
		return estado;
	}

	public void setEstado(StatusReserva estado) {
		this.estado = estado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public List<ItemReserva> getItemsReserva() {
		return itemsReserva;
	}

	public void setItemsReserva(List<ItemReserva> itemsReserva) {
		this.itemsReserva = itemsReserva;
	}


	/*
	 * @{code equal}
	 */
		@Override
		public boolean equals(Object o) {
			if(this == o) return true;
			if(o == null || getClass() != o.getClass()) return false;
			Reserva reserva = (Reserva) o;
			return Objects.equals(id, reserva.getId())&&
				  Objects.equals(usuario, reserva.getUsuario()) ;
		}
		
	  /*
	   * @{code hashCode}
	   */
		@Override
		public int hashCode() {
			return Objects.hash(id, usuario, itemsReserva);
		}
}
