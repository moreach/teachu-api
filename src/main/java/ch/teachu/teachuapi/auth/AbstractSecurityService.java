package ch.teachu.teachuapi.auth;


import ch.teachu.teachuapi.auth.daos.AuthUserDao;
import ch.teachu.teachuapi.errorhandling.InvalidException;
import ch.teachu.teachuapi.errorhandling.NotFoundException;
import ch.teachu.teachuapi.parent.AbstractService;
import org.springframework.security.crypto.password.PasswordEncoder;


public abstract class AbstractSecurityService extends AbstractService {

	protected final PasswordEncoder passwordEncoder;
	protected final AuthRepo authRepo;

	public AbstractSecurityService (PasswordEncoder passwordEncoder, AuthRepo authRepo) {
		this.passwordEncoder = passwordEncoder;
		this.authRepo = authRepo;
	}
	
	protected AuthUserDao login(String email, String password) {
		AuthUserDao user = authRepo.findAuthUserByEmail(email)
				.orElseThrow(() -> new NotFoundException("Email not found: " + email));
		ensurePasswordMatches(email, user, password);
		return user;
	}

	protected void ensurePasswordMatches(String email, AuthUserDao user, String password) {
		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new InvalidException("password for email" + email);
		}
	}
}
