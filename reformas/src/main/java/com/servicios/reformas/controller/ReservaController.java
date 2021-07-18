package com.servicios.reformas.controller;

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
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.servicios.reformas.entity.PreReserva;
import com.servicios.reformas.entity.Reserva;
import com.servicios.reformas.entity.StatusReserva;
import com.servicios.reformas.entity.Usuario;
import com.servicios.reformas.service.PreReservaService;
import com.servicios.reformas.service.ReservaService;


@Controller
@RequestMapping("/reserva")
public class ReservaController {
	
	
	private final ReservaService reservaService;
	private final PreReservaService prService;
	private static final Logger log = LoggerFactory.getLogger(ReservaController.class);
	
	@Autowired
	public ReservaController(ReservaService reservaService, PreReservaService prService) {
		this.reservaService = reservaService;
		this.prService = prService;
	}
	
	@GetMapping
	public String obtenerReserva(Authentication usuarioSession, Model model){
		Usuario usuarioDB = (Usuario)usuarioSession.getPrincipal();
		PreReserva pr = prService.obtenPreReserva(usuarioDB);
		
		model.addAttribute("reservas", pr.getCartItems());
		model.addAttribute("preReserva", pr);
		log.info("IN obtenerReserva(), GET: {}");
		return "reserva";
	
	}
	
	@PostMapping
	public String postReserva(Authentication usuarioSession,
								@Valid Reserva reservaValida, BindingResult bindingResult,
								Model model) {
		
		log.info("IN registro(): {}", model.asMap());
		Usuario usuarioDB = (Usuario)usuarioSession.getPrincipal();
		PreReserva preReserva = prService.obtenPreReserva(usuarioDB);
		if(bindingResult.hasErrors()) {
			Map<String, String> mapErrores = ControllerUtill.obtenerErrores(bindingResult) ;
			model.mergeAttributes(mapErrores);
			model.addAttribute("reservas", preReserva.getCartItems());
			model.addAttribute("preReserva", preReserva);
			return "reserva";
		} else {
			  reservaService.guardar(reservaValida, usuarioDB, preReserva);	
			  
		}
		return "/finalizarReserva";
	}
	
	@GetMapping("/finalizarReserva")
	public String finalizaReserva(Model model) {
		Reserva reserva = (Reserva) model.asMap().get("reservaUsuario");
		if(reserva == null) {
			return "redirect:/";
		}
		model.addAttribute("reservaUsuario", reserva);
		log.info("IN finalizarReserva() {}", reserva.getId());
		return "finalizarReserva";
	}
	
	/**
     * Retorna todas las reservas del consumidor.
     * URL request {"/reservas"}, metodo GET.
     *
     * @param model objeto {@link Model}.
     * @return reservas pagina con atributos uso del objeto  {@link Model}.
     */
    @GetMapping("/reservas")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String obtenerListaReservas(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageRequest, Model model) {
		 Page<Reserva> page = reservaService.encuentraTodos(pageRequest);
			int[] pagination = ControllerUtill.computePagination(page);
			log.info("IN pagina_Recursos(): ", pagination.length);
			model.addAttribute("pagina", pagination);
			model.addAttribute("url","/reserva/reservas");
			model.addAttribute("page", page);
        log.info("IN ObtenerListaReserva() {}", page);
        return "admin/reservasList";
    }
    
    
    /**
     * metodo para obtener la pagina de editar el estado de la {@link Reserva}.
     * 
     * @param reserva
     * @param model
     * @return
     */
    @GetMapping("{reserva}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String obtenerEstado(@PathVariable Reserva reserva, Model model) {
  	  model.addAttribute("reserva", reserva);
  	  model.addAttribute("estados", StatusReserva.values()); 
  	  return "admin/estadoReserva";
    }
    
   
    @PostMapping("/reservas")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String editarEstado(@RequestParam Map<String, String> form,
  		  					   @RequestParam("reservaId") Reserva reserva) {
  	  reservaService.actualizaReserva(form, reserva);
  	  log.info("IN editarEstado() : {}", form);
      return "redirect:/reserva/reservas";
    	}
    
   
    @RequestMapping("/eliminar")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String eliminarReserva(@RequestParam("id") Long id) {
    	log.info("IN eliminarReserva() : {}", id);
    	reservaService.eliminarReserva(id);
    	return "redirect:/reserva/reservas";
    }
	
	/*@GetMapping("/reservaUsuario")
	public String obtenerReservasUsuario(Authentication usuarioSession, Model model) {
		Usuario usuarioDB = (Usuario)usuarioSession.getPrincipal();
		List<Reserva> reservas = reservaService.encuentraPorUsuario(usuarioDB);
		model.addAttribute("reservas", reservas);
		return "reservas";
	}
	
	@GetMapping("/reservas")
	public String obtenerListaReservas(Model model){
		List<Reserva> listaReserva = reservaService.encuentraTodos();
		model.addAttribute("reservas", listaReserva);
		return "reserva/resevas";
	}*/
}