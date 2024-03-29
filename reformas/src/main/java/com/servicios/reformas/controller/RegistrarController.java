package com.servicios.reformas.controller;

import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.servicios.reformas.entity.Usuario;
import com.servicios.reformas.service.UsuarioService;

/*
 * Clase Controller para el registro del usuario.
 * Este controlador permite acceso a las paginas relacionadas por todos los usuarios, independiente de su rol
 * 
 * La anotacion @Controller  sirve para informar a Spring que esta es una clase @Bean,
 * y que se debe cargar cuando se lanza la aplicacion.
 * 
 *@author Ana Tcaci
 *@version 1.0
 *@see Usuario
 *@see UsuarioService
 *  
 */

@Controller
public class RegistrarController{
	/*
	 * Objeto de tipo servicio que trabaja con usuarios.
	 */
	private final UsuarioService usuarioService;
	private static final Logger logger = LoggerFactory.getLogger(RegistrarController.class);
	
	/*
	 * Constructor para inicializar las variables del controllador de registro-
	 * La anotacion @Autowired permite  a Spring inicializar automaticamente el objeto.
	 * 
	 * @param userService. Objeto de tipo servicio que trabaja con usuarios.
	 */
	@Autowired
	public RegistrarController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	/*
	 * Retorna la pagina de registro
	 * URL request {"/registro"}, metodo GET.
	 * 
	 * @return pagina de registro
	 */
	@GetMapping("/login")
	public String logeo(Usuario usuario) {
		return "login";
	}
	
	/*
	 * Retorna la pagina de registro
	 * URL request {"/registro"}, metodo GET.
	 * 
	 * @return pagina de registro
	*/
	@GetMapping("/registro")
	public String registro() {
		logger.info("In registro(), el usuario autentificado es: {}");
		return "registro";
	} 
	
	@PostMapping("/registro")
	public String registro(
			@RequestParam("password2") String contrasenaConfirm,
			@Valid Usuario usuario,
			BindingResult bindingResult,
			Model model) {
		logger.info("IN registro(): {}", model.asMap());
		
		boolean esConfirmVacio = contrasenaConfirm.isBlank();
		boolean esContrasenaDiferente = usuario.getPassword() !=null && !usuario.getPassword().equals(contrasenaConfirm);
		
		if(esConfirmVacio) {
			model.addAttribute("password2Error", "Confirme su contrasena, por favor.");	
		}
		
		if(esContrasenaDiferente) {
			model.addAttribute("passwordError", "Las contrasenas no coinciden.");
		}
		
		
		if(esConfirmVacio || esContrasenaDiferente || bindingResult.hasErrors()) {
			Map<String, String> errors = ControllerUtill.obtenerErrores(bindingResult);
			logger.info("In registro(): errors - {}", errors.toString());
			model.mergeAttributes(errors);
			return "registro";
		} 

		if(usuarioService.usuarioExiste(usuario.getEmail())) {
			logger.info("IN registro(): usuarioExiste - {}", usuario.getEmail());
			model.addAttribute("emailError", "Este usuario existe!");
			return "registro";
		}
			usuarioService.guardar(usuario);
			model.addAttribute("messageType", "alert-danger");
			model.addAttribute("message", "Gracias por unirse a nuestro equipo");
			logger.info("Usuario {} registrado", usuario.toString());
		
		
		return "redirect:/login";
	}
	
}
