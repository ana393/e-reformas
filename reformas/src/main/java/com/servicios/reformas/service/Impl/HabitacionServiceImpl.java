package com.servicios.reformas.service.Impl;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.servicios.reformas.entity.Habitacion;
import com.servicios.reformas.repository.HabitacionRepository;
import  com.servicios.reformas.service.HabitacionService;

@Service
public class HabitacionServiceImpl  implements HabitacionService{
	
	/*
	 * Implimentacion de la interfaz {@link HabitacionRepository}
	 * para trabajar con el objeto Habitacion de la Base de datos
	 */
	private final HabitacionRepository habitacionRepository;
	private static final Logger log = LoggerFactory.getLogger(HabitacionServiceImpl.class);
	
	/**
	 *  @Value, anotacion que indica a spring la nececidad de recibir una variable
	 *  			que busca en el el contexto de spring la propriedad "upload.path"
	 */
	@Value("${upload.path}")
	private String uploadPath;
	
  /**
 * Constructor que inicializa las variables del servicio Habitacion
 * @Autowired, anotacion que permite a Spring inicializar los objetos automaticamente.
 * 
 * @param habitacionRepository implimentacion de la interfaz {@link HabitacionRepository}
 * 								que trabaja con el objeto Habitacion de la Base de datos.
 */
@Autowired
  public HabitacionServiceImpl(HabitacionRepository habitacionRepository) {
		this.habitacionRepository = habitacionRepository;
	}
  
  

	/**
	 *
	 */
	@Override
	public Page<Habitacion> encuentraTodo(Pageable pagina) {
		return habitacionRepository.findAll(pagina);
	}
	
 /*
  * metodo de busqueda por titulo. Resultado guardado en una Lista
  * @param String Buscar
  */
//	@Override
//	public List<Habitacion> encuentraPorNombre(String buscar) {
//		List<Habitacion> result =  new ArrayList<>();
//		 habitacionRepository.BuscarHabitacionPorNombre(buscar).forEach(result::add);
//		 log.info("IN encuentraPorNombre() las habitaciones: {}", result.size());
//		return result;
//	}
	

	@Override
	public Page<Habitacion> encuentraPorNombre(String nombre, Pageable pagina) {
		return habitacionRepository.findByNombre(nombre, pagina);
	}

	@Override
	public Page<Habitacion> encuentraPorPrecio(BigDecimal precioInicial, BigDecimal precioFinal, Pageable pagina) {
		return habitacionRepository.findByPrecio(precioInicial, precioFinal, pagina);
	}
	
 
	@Override
	public Habitacion encuentraPorId(long id) {
		Habitacion result = habitacionRepository.findById(id).orElse(null);
		if(result == null) {
			log.warn("IN encontrarPorId() - no hay libros con id: {}", result);
		}
		log.info("IN encontrarPorId() - habitacion: {} encontrada por id: {}", result);
		return result;
	}
	
	/**
	 * Metodo para guardar resursos con fichero adjunto lectura con InputStream()
	 * 
	 * @param   habitacion
	 * @param   fichero
	 * @return  la clase {@link habitacion} que persistira en la base de datos.
	 */
	
	@Override
	public Habitacion guardaHabitacion(Habitacion habitacion,  MultipartFile fichero){
		log.info("IN guardarFichero() - carpeta {}", uploadPath);
		 Habitacion guardado = new Habitacion();
		  guardado.setNombre(habitacion.getNombre());
		  guardado.setCategoria(habitacion.getCategoria());
		  guardado.setDescripcion(habitacion.getDescripcion());
		  guardado.setPrecio(habitacion.getPrecio());
		 if (fichero == null) {
			 habitacion.setImg_url("vacio.jpg");
		 } else {
			 File SystemDir = new File(uploadPath);
			 if (!SystemDir.exists()) {
				 SystemDir.mkdir();
			 }
		//String uuidFile = UUID.randomUUID().toString(); 
		String nombre_Fichero = StringUtils.cleanPath(fichero.getOriginalFilename());
		
		try{
			fichero.transferTo(new File(uploadPath + "/" + nombre_Fichero));
		
			}catch(IOException e) {
			   e.printStackTrace();
			}
		
		habitacion.setImg_url(nombre_Fichero);
		
		
		 } 
		
		Habitacion persistente = habitacionRepository.save(habitacion);
		log.info("IN guardaHabitacion() - habitacion: {}  se guardo", persistente);
		
		return persistente;
	}
	
	
	/**
	 *  Metodo para eliminar recurso
	 *  
	 *@parama Long id
	 */
	@Override
	@Transactional
	public void eliminarHabitacionPorId(Long id) {
		habitacionRepository.deleteById(id);
		log.info("IN eliminarHabitacion() - habitacion con id: {} se ha eliminado");	
	}

//
//	@Override
//	public List<Habitacion> obtenerPorCategoria(int id) {
//		return habitacionRepository.encuentraPorCategoria(id);
//	}


	@Override
	public BigDecimal maxHabiatcionPrecio() {
		return habitacionRepository.maxHabiatcionPrecio();
	}

	@Override
	public BigDecimal minHabitacionPrecio() {
		return habitacionRepository.minHabitacionPrecio();
	}

	@Override
	public Page<Habitacion> encuentraPorCategoria(List<Long> id, Pageable pagina) {
		return habitacionRepository.encuentraPorCategoria(id, pagina);
	}


}
