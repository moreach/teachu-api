package ch.teachu.teachuapi.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "grade")
public class GradeProperties {
    private double maxMark;
    private double minMark;
}
