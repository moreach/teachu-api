package ch.teachu.teachuapi;

import ch.teachu.teachuapi.shared.properties.DatabaseProperties;
import ch.teachu.teachuapi.shared.properties.SecurityProperties;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({DatabaseProperties.class, SecurityProperties.class})
@AllArgsConstructor
public class TeachuApiApplication {

//	private final IStorageAccessService storageAccessService;

    public static void main(String[] args) {
        SpringApplication.run(TeachuApiApplication.class, args);
    }
//
//	@EventListener(ApplicationReadyEvent.class)
//	public void start() {
//		storageAccessService.init();
//	}
}
