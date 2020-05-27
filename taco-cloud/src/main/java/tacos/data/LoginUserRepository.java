package tacos.data;


import org.springframework.data.repository.CrudRepository;

import tacos.LoginUser;

public interface LoginUserRepository extends CrudRepository<LoginUser, Long> {
	LoginUser findByUsername(String username);
}
