package com.servicios.reformas.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.servicios.reformas.entity.Habitacion;
import com.servicios.reformas.service.HabitacionService;

@Controller
@RequestMapping("/recursos")
public class CatalogoController {

	
	 private final HabitacionService habitacionService;
	 
	 private static final Logger log = LoggerFactory.getLogger(CatalogoController.class);
	 public CatalogoController(HabitacionService habitacionService) {
		this.habitacionService = habitacionService;
	}
	 
	 
		@GetMapping
		public String pagina_Recursos(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageRequest, Model model) {
			Page<Habitacion> page = habitacionService.encuentraTodo(pageRequest);
			int[] pagination = ControllerUtill.computePagination(page);
			log.info("IN pagina_Recursos(): ", pagination.length);
			model.addAttribute("pagina", pagination);
			model.addAttribute("url","/recursos");
			model.addAttribute("page", page);
			log.info("IN pagina_Recursos()");
			return "recursos";
		}
		
		/*
		 * Retorna lista de habitaciones en la pagina del catalogo con paginacion, 
		 * segun el parametro seleccionado.
		 */
//	@GetMapping("buscar")
//	public String buscarPorParametro(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC, size = 5) Pageable pageRequest,
//									 
//									 Model model) {
//		log.info("IN buscarPorParametro()");
//		
//			Page<Habitacion> rangoTitulo = habitacionService.encuentraPorNombre(null, pageRequest);
//			int[] pagination = ControllerUtill.computePagination(rangoTitulo);
//			model.addAttribute("pagination", pagination);
//			//model.addAttribute("url", "/menu/search?precioInicial=" + precioInicial + "&precioFinal=" + precioFinal);
//			model.addAttribute("page", rangoTitulo);
//			
//			return "recursos";
//		
//	}
	  
}
