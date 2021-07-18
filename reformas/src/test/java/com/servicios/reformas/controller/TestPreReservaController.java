package com.servicios.reformas.controller;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.servicios.reformas.controller.PreReservaController;

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
@WithUserDetails("admin@mail.com")
public class TestPreReservaController {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private PreReservaController controller;
	
	
	@Test
	public void preReservaPageTest() throws Exception {
		this.mockMvc.perform(get("/preReserva"))
				    .andDo(print())
				    .andExpect(authenticated())
				    .andExpect(xpath("//*[@id=\"navbarNav\"]/ul[1]/li[5/span").string("Admin"));
	}
	
//	@Test
//	public void preReservaPageTest() throws Exception {
//		this.mockMvc.perform(get("/preReserva"))
//				    .andDo(print())
//				    .andExpect(authenticated())
//				    .andExpect(xpath("//*[@id=\"navbarNav\"]/ul[1]/li[5/span").string("Admin"));
//	}
	
	/**
	 * @throws Exception
	 * metodo para comprobar la autorizacion
	 * indicando la direccion a la pagina que requiere autorizacion
	 */
//	@Test
//	public void AccesoProhibidoTest() throws Exception {
//		this.mockMvc.perform(get("/preReserva"))
//			.andExpect(status().is3xxRedirection())
//			.andExpect(redirectedUrl("http://localhost/login"));
//	}
	
}
