package ch.teachu.teachuapi;

import ch.teachu.teachuapi.configs.GradeProperties;
import ch.teachu.teachuapi.configs.SecurityProperties;
import ch.teachu.teachuapi.properties.DatabaseProperties;
import ch.teachu.teachuapi.properties.FileProperties;
import ch.teachu.teachuapi.util.IStorageAccessService;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({DatabaseProperties.class, SecurityProperties.class, GradeProperties.class, FileProperties.class})
@AllArgsConstructor
public class TeachuApiApplication {

	private final IStorageAccessService storageAccessService;

	public static void main(String[] args) {
		SpringApplication.run(TeachuApiApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void start() {
		storageAccessService.init();
	}
}
