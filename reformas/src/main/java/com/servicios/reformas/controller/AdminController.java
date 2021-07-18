package com.servicios.reformas.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.servicios.reformas.entity.Categoria;
import com.servicios.reformas.entity.Habitacion;
import com.servicios.reformas.entity.Role;
import com.servicios.reformas.entity.Usuario;
import com.servicios.reformas.repository.CategoriaRepository;
import com.servicios.reformas.service.HabitacionService;
import com.servicios.reformas.service.UsuarioService;

 @Controller
 @RequestMapping("/admin")
 public class AdminController {
	
 private final HabitacionService habitacionService;
 private final CategoriaRepository categoriaRepository ;
 private final UsuarioService usuarioService;
 private static final Logger log = LoggerFactory.getLogger(AdminController.class);
 

 @Autowired
 public AdminController(HabitacionService habitacionService, CategoriaRepository categoriaRepository, UsuarioService usuarioService) {
	this.habitacionService = habitacionService;
	this.categoriaRepository = categoriaRepository;
	this.usuarioService = usuarioService;

}


 @GetMapping
 public String verPaginaAdmin() {
 	   log.info("IN verPaginAdmin() - lista: {} ");
	return "admin";
}
 
 @GetMapping("articulosList")
 public String listaArticulos(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageRequest, Model model) {
	 Page<Habitacion> page = habitacionService.encuentraTodo(pageRequest);
		int[] pagination = ControllerUtill.computePagination(page);
		log.info("IN pagina_Recursos(): ", pagination.length);
		model.addAttribute("pagina", pagination);
		model.addAttribute("url","/admin/articulosList");
		model.addAttribute("page", page);
 	   log.info("IN listaArticulos() - lista: {} ");
	return "admin/articulosList";
}
 

 @RequestMapping("/v/borrar/{id}")
 @PreAuthorize("hasAuthority('ADMIN')")
 public String borrarRecurso(@PathVariable("id") Long id){
	  log.info("IN borrarRecurso()", id);
	  habitacionService.eliminarHabitacionPorId(id);
	  return "redirect:/admin/articulosList";
 }
 
 @GetMapping("/crea_recurso")
 @PreAuthorize("hasAuthority('ADMIN')")
 public String andeHabitacion( Model model) {
	
	Habitacion habitacion = new Habitacion();
	List<Categoria> categorias = categoriaRepository.findAll();
	model.addAttribute("habitacion", habitacion);
	model.addAttribute("categorias", categorias);
	
	log.info("IN anadeHabitacion() -  Categorias {} ", categorias.size());
	return "admin/crea_recurso";
}
 
 
  @PostMapping("/guardar_recurso")
  @PreAuthorize("hasAuthority('ADMIN')")
  public String guardarRecurso( @Valid Habitacion habitacion,
		  						BindingResult bindingResult,
		  						Model model,
		  						@RequestParam("fichero")MultipartFile fichero)  {
	  List<Categoria> categorias = categoriaRepository.findAll();
	  	if(bindingResult.hasErrors()) {
	  		Map<String, String> eroresMap = ControllerUtill.obtenerErrores(bindingResult);
	  		log.info("In registro(): errors - {}", eroresMap.toString());
	  		model.mergeAttributes(eroresMap);
	  		model.addAttribute("categorias", categorias);
	  		return "admin/crea_recurso";
	  	}
	  	
	   habitacionService.guardaHabitacion(habitacion, fichero); 
	 
	   log.info("IN guardarRecurso() @Post");
	return "redirect:/admin";
  }
 
  
  /*
   * Obtener lista de usuarios registrados para el cambio de permisos
   * @see editarPermisos()
   */

  @GetMapping("/usuarios") 
  @PreAuthorize("hasAuthority('ADMIN')")
  public String listaUsuarios(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageRequest, Model model) {
		 Page<Usuario> page = usuarioService.encuentraTodos(pageRequest);
			int[] pagination = ControllerUtill.computePagination(page);
			log.info("IN pagina_Recursos(): ", pagination.length);
			model.addAttribute("pagina", pagination);
			model.addAttribute("url","/admin/usuarios");
			model.addAttribute("page", page);
	 	   log.info("IN verPaginAdmin() - lista: {} ");
	  
	  return "admin/usuarios";
  }
  
 
  @GetMapping("{usuario}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public String obtenerPermisos(@PathVariable Usuario usuario, Model model) {
	  model.addAttribute("usuario", usuario);
	  model.addAttribute("roles", Role.values()); 
	  return "admin/permisos";
  }
  
  /*
   * Metodo para guardar los permisos del usuario
   */
  
  @PostMapping
  @PreAuthorize("hasAuthority('ADMIN')")
  public String editarPermisos(@RequestParam Map<String, String> form,
		  					   @RequestParam("userId") Usuario usuario) {
	  usuarioService.actualizaUsuario(form, usuario);
	  log.info("ADMIN guardo los permisos del usuario : {}",usuario.getRoles());
    return "redirect:/admin";
  	}
  
 }
