package com.geoparking.profileservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProfileServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProfileServiceApplication.class, args);
	}

	// @Bean
	// public WebMvcConfigurer corsConfigurer() {
	// return new WebMvcConfigurer() {

	// @Override
	// public void addCorsMappings(CorsRegistry registry) {
	// registry.addMapping("/**").allowedOrigins("http://localhost:3000");
	// }
	// };
	// }

}
