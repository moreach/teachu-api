package ch.teachu.teachuapi.scheduled;

import ch.teachu.teachuapi.authentication.AuthService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TokenJob {

    private final AuthService authService;

    public TokenJob(AuthService authService) {
        this.authService = authService;
    }

    @Scheduled(cron = "0 0/10 * * * ?")
    public void deleteOldTokens() {
        authService.deleteExpiredTokens();
    }
}
