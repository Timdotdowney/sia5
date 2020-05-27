package tacos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@NoArgsConstructor(force=true)
@RequiredArgsConstructor
public class LoginUser {
	
	
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private final String username;
	private final String password;
	private final String fullname;
	private final String street;
	private final String city;
	private final String state;
	private final String zip;
	private final String phoneNumber;

	public LoginUser(LoginUser user) {
		if (user != null) {
			this.id = user.id;
			this.username = user.username;
			this.password = user.password;
			this.fullname = user.fullname;
			this.street = user.street;
			this.city = user.city;
			this.state = user.state;
			this.zip = user.zip;
			this.phoneNumber = user.phoneNumber;	
		} else {
			this.id = null;
			this.username = null;
			this.password = null;
			this.fullname = null;
			this.street = null;
			this.city = null;
			this.state = null;
			this.zip = null;
			this.phoneNumber = null;
		}
	}
}
