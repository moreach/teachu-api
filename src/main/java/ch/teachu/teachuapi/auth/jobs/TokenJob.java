package ch.teachu.teachuapi.auth.jobs;

import ch.teachu.teachuapi.auth.AuthRepo;
import ch.teachu.teachuapi.enums.LogLevel;
import ch.teachu.teachuapi.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TokenJob {

    private final AuthRepo authRepo;

    @Autowired
    public TokenJob(AuthRepo authRepo) {
        this.authRepo = authRepo;
    }

    @Scheduled(cron = "0 0/10 * * * ?")
    public void deleteOldTokens() {
        int count = authRepo.deleteExpiredTokens();
        LogUtil.log("Deleted " + count + " tokens that expired", getClass(), LogLevel.INFO);
    }
}
