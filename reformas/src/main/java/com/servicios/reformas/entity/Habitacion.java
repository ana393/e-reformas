package com.servicios.reformas.entity;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;


/*
 * Clase que describe la entidad "Habitacion"
 * La anotacion @Entity indica que el objeto de esta clase estara procesado por hibernate.
 * La anotacion @Table indica el nombre de la tabla en la base de datos MySQL.
 */
@Entity
@Table(name = "habitacion")
public class Habitacion {



	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
     private long id;
	
	 @NotBlank(message="Complete el campo, por favor!")
     private String nombre;
	 
	 @Length(max = 1024, message = "Mensaje demasiado largo (mas de 1kb)!")
	 @NotBlank(message="Complete el campo, por favor!")
     private String descripcion;
	 
     private String img_url;
	 
     private Double precio;
     /*
      * Mas productos  perteneceran a una categoria
      *@ManyToOne relacion 
      */
     @ManyToOne
     @JoinColumn(name="category_id")
     private Categoria categoria;
    
    public Habitacion() {}
	

	public Habitacion(String nombre, String descripcion, String img_url, Double precio, Categoria categoria) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.img_url = img_url;
		this.precio = precio;
		this.categoria = categoria;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public Categoria getCategoria() {
		return categoria;
	}


	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}



	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getImg_url() {
		return img_url;
	}


	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public Double getPrecio() {
		return precio;
	}


	public void setPrecio(Double precio) {
		this.precio = precio;
	}
   /*
    * @{code equal}
    */
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		Habitacion habitacion = (Habitacion) o;
		return Objects.equals(id, habitacion.getId())&&
			   Objects.equals(nombre, habitacion.getNombre()) &&
			   Objects.equals(precio, habitacion.getPrecio());
	}
	
    /*
	 * @{code hashCode}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id, nombre, precio);
	}
	
	@Override
	public String toString() {
		return "Habitacion [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", img_url=" + img_url
				+ ", precio=" + precio + ", categoria=" + categoria + "]";
	}


}
