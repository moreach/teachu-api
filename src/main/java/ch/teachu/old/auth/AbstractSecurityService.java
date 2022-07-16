//package ch.teachu.old.auth;
//
//
//import ch.teachu.old.auth.daos.AuthUserDAO;
//import ch.teachu.old.errorhandling.InvalidException;
//import ch.teachu.old.errorhandling.NotFoundException;
//import ch.teachu.old.parent.AbstractService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//
//public abstract class AbstractSecurityService extends AbstractService {
//
//	protected final PasswordEncoder passwordEncoder;
//	protected final AuthRepo authRepo;
//
//	public AbstractSecurityService(PasswordEncoder passwordEncoder, AuthRepo authRepo) {
//		this.passwordEncoder = passwordEncoder;
//		this.authRepo = authRepo;
//	}
//
//	protected AuthUserDAO login(String email, String password) {
//		AuthUserDAO user = authRepo.findActiveAuthUserByEmail(email)
//				.orElseThrow(() -> new NotFoundException("Email " + email));
//		ensurePasswordMatches(email, user, password);
//		return user;
//	}
//
//	private void ensurePasswordMatches(String email, AuthUserDAO user, String password) {
//		if (!passwordEncoder.matches(password, user.getPassword())) {
//			throw new InvalidException("password for email " + email);
//		}
//	}
//}
