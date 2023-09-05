package GLINS_BE.GLINS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class GlinsApplication {
	public static void main(String[] args) {
		SpringApplication.run(GlinsApplication.class, args);
	}
}