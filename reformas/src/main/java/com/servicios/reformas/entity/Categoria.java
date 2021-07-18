package com.servicios.reformas.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "categoria")
public class Categoria {
	
	   @Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
	   private Long id;
	   private String nombre;
	   /*
	    * Una categoria contiene mas habitaciones
	    * @OneToMany relacion
	    */
	   @JsonIgnore
	   @OneToMany(mappedBy ="categoria", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	   private Set<Habitacion> habitaciones;
	   
	   
   public Categoria() {}	   
	
  
   public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	   public Set<Habitacion> getHabitaciones() {
			return habitaciones;
		}


		public void setHabitaciones(Set<Habitacion> habitaciones) {
			this.habitaciones = habitaciones;
		}
		
	/*
	 * Metodo para salvar la asosiacion	
	 */
	 public void anadeHabitacion(Habitacion habitacion) {
		 habitacion.setCategoria(this);
		 habitaciones.add(habitacion);
	 }
}
