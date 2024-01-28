package mg.company.varotravam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CORSConfig {
    @Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/**")
					.allowedOrigins("https://varotravam-admin.netlify.app/**")
					.allowedMethods("GET", "POST", "PUT", "DELETE")
					.allowedHeaders("Authorization", "Content-Type")
					.allowCredentials(true)
					.maxAge(3600);
				registry.addMapping("/authentification/**")
					.allowedOrigins("https://varotravam-admin.netlify.app/**")
					.allowedMethods("GET", "POST", "PUT", "DELETE")
					.allowedHeaders("Authorization", "Content-Type")
					.allowCredentials(true)
					.maxAge(3600);
			}
		};
	}
}
