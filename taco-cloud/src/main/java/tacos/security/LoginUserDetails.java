package tacos.security;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import tacos.LoginUser;

public class LoginUserDetails extends LoginUser implements UserDetails {
	
	private LoginUser loginUser;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LoginUserDetails(LoginUser loginUser) {
		super(loginUser);
		this.loginUser = loginUser;
	}
	
	public LoginUser getLoginUser() {
		return this.loginUser;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	

}
