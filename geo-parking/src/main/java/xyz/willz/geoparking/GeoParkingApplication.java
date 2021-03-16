package xyz.willz.geoparking;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import xyz.willz.geoparking.service.storage.ImageStorageProperties;
import xyz.willz.geoparking.service.storage.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(ImageStorageProperties.class)
public class GeoParkingApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeoParkingApplication.class, args);

		System.out.println("Application Started...✔✔✔🙌💯✔");
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.init();
		};
	}
}
