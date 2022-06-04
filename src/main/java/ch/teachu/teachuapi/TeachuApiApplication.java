package ch.teachu.teachuapi;

import ch.teachu.teachuapi.configs.GradeProperties;
import ch.teachu.teachuapi.configs.SecurityProperties;
import ch.teachu.teachuapi.properties.DatabaseProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({DatabaseProperties.class, SecurityProperties.class, GradeProperties.class})
public class TeachuApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeachuApiApplication.class, args);
	}

}
