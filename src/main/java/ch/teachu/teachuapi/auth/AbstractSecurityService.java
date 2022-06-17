package ch.teachu.teachuapi.auth;


import ch.teachu.teachuapi.auth.daos.AuthUserDAO;
import ch.teachu.teachuapi.errorhandling.InvalidException;
import ch.teachu.teachuapi.errorhandling.NotFoundException;
import ch.teachu.teachuapi.parent.AbstractService;
import org.springframework.security.crypto.password.PasswordEncoder;


public abstract class AbstractSecurityService extends AbstractService {

	protected final PasswordEncoder passwordEncoder;
	protected final AuthRepo authRepo;

	public AbstractSecurityService(PasswordEncoder passwordEncoder, AuthRepo authRepo) {
		this.passwordEncoder = passwordEncoder;
		this.authRepo = authRepo;
	}
	
	protected AuthUserDAO login(String email, String password) {
		AuthUserDAO user = authRepo.findAuthUserByEmail(email)
				.orElseThrow(() -> new NotFoundException("Email " + email));
		ensurePasswordMatches(email, user, password);
		return user;
	}

	private void ensurePasswordMatches(String email, AuthUserDAO user, String password) {
		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new InvalidException("password for email " + email);
		}
	}
}
