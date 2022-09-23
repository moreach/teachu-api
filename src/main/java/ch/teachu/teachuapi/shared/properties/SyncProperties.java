package ch.teachu.teachuapi.shared.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "sync")
public class SyncProperties {
  private String apiKey;
  private String learnzKey;
}
