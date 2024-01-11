package mg.company.varotravam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class VarotravamApplication {

	public static void main(String[] args) {
		SpringApplication.run(VarotravamApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/authentification/utilisateur").allowedOrigins("*");
				registry.addMapping("/authentification/administrateur").allowedOrigins("*");
				registry.addMapping("/api/v1/categories").allowedOrigins("*");
				registry.addMapping("/api/v1/categories/ajouter").allowedOrigins("*");
			}
		};
	}

}
