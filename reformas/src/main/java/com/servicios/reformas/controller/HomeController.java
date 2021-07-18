package com.servicios.reformas.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.servicios.reformas.entity.Habitacion;
import com.servicios.reformas.service.HabitacionService;

@Controller
public class HomeController {
	
	private final HabitacionService habitacionService;
	
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	public HomeController(HabitacionService habitacionService) {
		this.habitacionService = habitacionService;
	}
	 /*
	  * Retorna todos los productos a la pagina principal.
	  * URL request {"/"}, method GET
	  * 
	  * @return pagina principal con atributo model
	  */
	@GetMapping("/")
	public String home(Model model) {
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		 log.info("In home(), el usuario autentificado es: {}", authentication.getAuthorities());
		//List<Habitacion> habitaciones = habitacionService.encuentraTodos();
		// model.addAttribute("habitaciones", habitaciones);
		return "home";
	}
	
	
	@GetMapping("/buscar")
	public String buscar(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC, size = 5) Pageable pageRequest,
						 @RequestParam(name="buscar", required = false) String filter,
						 Model model) {
		Page<Habitacion> pagina = habitacionService.encuentraPorNombre(filter, pageRequest);
		 log.info("In home(), el usuario autentificado es: {}", pagina.getNumberOfElements());
		int[] paginacion = ControllerUtill.computePagination(pagina);
	
		model.addAttribute("pagina", paginacion);
		model.addAttribute("url", "/menu");
		model.addAttribute("page", pagina);
		
		return "recursos";
	}
}
