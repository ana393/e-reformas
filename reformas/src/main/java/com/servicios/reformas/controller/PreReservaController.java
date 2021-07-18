package com.servicios.reformas.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.servicios.reformas.entity.Habitacion;
import com.servicios.reformas.entity.ItemReserva;
import com.servicios.reformas.entity.PreReserva;
import com.servicios.reformas.entity.Usuario;
import com.servicios.reformas.service.HabitacionService;
import com.servicios.reformas.service.PreReservaService;

@Controller
@RequestMapping("/preReserva")
public class PreReservaController {
	
	private final HabitacionService habitacionService;
	private final PreReservaService preReservaService;
	private static final Logger logger = LoggerFactory.getLogger(PreReservaController.class);
	
	@Autowired
	public PreReservaController(HabitacionService habitacionService, PreReservaService preReservaService) {
		this.habitacionService = habitacionService;
		this.preReservaService = preReservaService;
		
	}
	
@GetMapping
public String obtenerPreReserva(Authentication usuarioSession, Model model) {
	Usuario usuarioDB = (Usuario)usuarioSession.getPrincipal();
	PreReserva preReserva = preReservaService.obtenPreReserva(usuarioDB);
	model.addAttribute("reservas", preReserva.getCartItems());
	model.addAttribute("preReserva", preReserva);
	
	logger.info("In obtenerPreReserva : {}");
	return "preReserva";
}


@PostMapping("/anade")
public String anadePreReserva(@RequestParam("anade") Habitacion habitacion,
							   RedirectAttributes attributes,
							  Model model, Authentication usuarioSession) {
	Usuario usuarioDB = (Usuario)usuarioSession.getPrincipal();
	int qty = 1;
	habitacion = habitacionService.encuentraPorId(habitacion.getId());
	
	preReservaService.anadeItemAPreReserva(habitacion, usuarioDB, qty);
	logger.info("In anadePreReserva : {}", usuarioDB.toString());
    return "redirect:/preReserva";
}



@GetMapping("/remove-item/{id}")
public String eliminaPreReserva(@PathVariable("id") long id) {
	ItemReserva item = preReservaService.encuentraItemPorId(id);
	preReservaService.eliminarItemreserva(item);
    return "redirect:/preReserva";
 }

/*
@RequestMapping("/update-item")
public String actualizarCantidad(@RequestParam("id") long id,
		@RequestParam("qty") Integer qty,  Model model) {
	ItemReserva item = preReservaService.encuentraItemPorId(id);
	if(item.actualizarCantidad(qty)) {
		preReservaService.actualizarItemReserva(item, qty);
	}
	preReservaService.eliminarItemreserva(item);
    return "redirect:/preReserva/cart";
 }*/
}