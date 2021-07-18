package com.servicios.reformas.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.servicios.reformas.controller.RegistrarController;

/**
 * @SpringBootTest combo anotacion que indica el lansamiento de la applicacion en entorno SpringBoot
 *                 el sistema identifica automaticamenete el paquete de la aplicacion y encontrara 
 *                 todas las configuraciones necesarias del contexto de la aplicacion.
 *                 
 *@AutoConfigureMockMvc crea los objetos de la aplicacion en un entorno falso.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestRegistrarController {
    
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private RegistrarController controller;
	
	
	
	/**
	 * @throws Exception
	 * metodo para comprobar la correcta autorizacion
	 *  uso de FormLoginRequestBuilder para la dereccion al formulario login predefinido en Spring Security
	 * 
	 * @return redirectedUrl("http://localhost/preReserva")
	 */
	@Test
	public void loginCorrectoTest() throws Exception {
		this.mockMvc.perform(post("/login").param("email", "admin@mail.com"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("http://localhost/preReserva"));
	}
	
	/**
	 * metodo test para comprobar la autentificaccion en caso de datos incorrectos
	 * llamada post 
	 * @throws Exception
	 * 
	 * @return isForbidden()
	 */
	@Test 
	public void credencialesErroneo() throws Exception {
		this.mockMvc.perform(post("/login").param("email", "v@mail.com"))
			.andDo(print())
			.andExpect(status().isForbidden());
	}
}
