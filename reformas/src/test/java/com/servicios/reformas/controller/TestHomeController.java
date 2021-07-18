package com.servicios.reformas.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.servicios.reformas.controller.HomeController;

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
public class TestHomeController {
	
	@Autowired
	private MockMvc mockMvc;
	

	
	 /**
	  * @Test: marca todos los metodos de tipo test dentro de la clase {@link TestCase}
	  * @throws Exception
	  */
	@Test
	 public void  homePageTest() throws Exception{
		 this.mockMvc.perform(get("/"))
		 			.andExpect(status().isOk())
		 			.andExpect(content().string(containsString("Hello, Guest")));
	 }
}
