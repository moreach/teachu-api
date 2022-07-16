package ch.teachu.teachuapi.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TokenJob {

//    private static final Logger LOG = LoggerFactory.getLogger(TokenJob.class);
//    private final AuthRepo authRepo;
//
//    @Autowired
//    public TokenJob(AuthRepo authRepo) {
//        this.authRepo = authRepo;
//    }
//
//    @Scheduled(cron = "0 0/10 * * * ?")
//    public void deleteOldTokens() {
//        // todo replace with auth service
//        int count = authRepo.deleteExpiredTokens();
//        LOG.info("Deleted " + count + " tokens that expired");
//    }
}
