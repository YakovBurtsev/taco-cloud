package data;

import org.springframework.data.jpa.repository.JpaRepository;
import tacos.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
