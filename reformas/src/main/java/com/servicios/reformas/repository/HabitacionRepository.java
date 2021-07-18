package com.servicios.reformas.repository;



import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.servicios.reformas.entity.Habitacion;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
	
	//SQL consultas nativas para  consegir productos según su categoria asociada
//String OBTENER_TODO_POR_CATEGORIA_SQL = "select h from habitacion h inner join categoria hc on h.category_id = hc.id where (h.category_id)= ?1";
String OBTENER_CATEGORIA_ID = "SELECT * FROM habitacion WHERE category_id LIKE %?1%";
String OBTENER_POR_NOMBRE = "SELECT * FROM habitacion WHERE nombre like %:nombre%";
/*
 * Encuentra las habitaciones según categoria.
 * @param categoria_id
 * @return lista<habitacion>
 */
//   @Query(value = OBTENER_CATEGORIA_ID, nativeQuery = true)
//   List<Habitacion> encuentraPorCategoria(long id);
//   
// 
//   @Query(value = OBTENER_POR_NOMBRE, nativeQuery = true)
//   List<Habitacion> BuscarHabitacionPorNombre(String nombre);
   
   Page<Habitacion> findAll(Pageable pageable);
   
   @Query(value = OBTENER_CATEGORIA_ID, nativeQuery = true)
   Page<Habitacion> encuentraPorCategoria(List<Long> id, Pageable page);
  
   Page<Habitacion> findByNombre(String nombre, Pageable pagina);  
   
   Page<Habitacion> findByPrecio(BigDecimal precioInicial, BigDecimal precioFinal, Pageable pagina);
  
   @Query(value = "SELECT min(precio) FROM Habitacion")
   BigDecimal maxHabiatcionPrecio();
   
   @Query(value = "SELECT max(precio) FROM Habitacion")
   BigDecimal minHabitacionPrecio();
}
