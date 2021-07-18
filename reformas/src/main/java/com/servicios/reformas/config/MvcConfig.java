package com.servicios.reformas.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
	
 @Value("${upload.path}")
 private String uploadPath;
 
 /**
  * Returns RestTemplate which offers templates for common scenarios by HTTP method.
  *
  * @return RestTemplate.
  */
 @Bean
 public RestTemplate getRestTemplate() {
     return new RestTemplate();
 }
 
 public void addResourceHandlerRepository(ResourceHandlerRegistry registry) {
	 registry.addResourceHandler("/img/**")
	 		 .addResourceLocations("file://" + uploadPath + "/");
 }
 
}
