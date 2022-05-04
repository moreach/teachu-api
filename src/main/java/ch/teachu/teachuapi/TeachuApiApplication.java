package ch.teachu.teachuapi;

import ch.teachu.teachuapi.properties.DatabaseProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(DatabaseProperties.class)
public class TeachuApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeachuApiApplication.class, args);
	}

}
