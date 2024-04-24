package nbcc.termproject.repositories;

import nbcc.termproject.entities.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoginRepository extends JpaRepository<UserLogin, String> {

    UserLogin getUserLoginByToken(String token);

}
