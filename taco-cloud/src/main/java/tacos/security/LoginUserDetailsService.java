package tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import tacos.LoginUser;
import tacos.data.LoginUserRepository;

@Service
public class LoginUserDetailsService implements UserDetailsService {
	
	public LoginUserDetailsService() {
		System.out.println("default LoginUserDetailsService");
	}
	
	private LoginUserRepository userRepo;

	@Autowired
	public LoginUserDetailsService(LoginUserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public LoginUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LoginUser loginUser = userRepo.findByUsername(username);
		if (loginUser != null) {
			return new LoginUserDetails(loginUser);
		}
		throw new UsernameNotFoundException("User '" + username + "' not found");
	}
}
