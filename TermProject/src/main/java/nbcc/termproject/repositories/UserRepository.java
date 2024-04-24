package nbcc.termproject.repositories;

import nbcc.termproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

    User getUsersByUserNameAndPassword(String username, String password);



}
