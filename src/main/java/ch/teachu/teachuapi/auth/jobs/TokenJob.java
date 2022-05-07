package ch.teachu.teachuapi.auth.jobs;

import ch.teachu.teachuapi.auth.AuthRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TokenJob {

    public static final Logger logger = LoggerFactory.getLogger(TokenJob.class);

    private final AuthRepo authRepo;

    @Autowired
    public TokenJob(AuthRepo authRepo) {
        this.authRepo = authRepo;
    }

    @Scheduled(cron = "0 0/10 * * * ?")
    public void deleteOldTokens() {
        int changed = authRepo.deleteExpiredTokens();
        logger.info("Deleted " + changed + " tokens that expired.");
    }
}
