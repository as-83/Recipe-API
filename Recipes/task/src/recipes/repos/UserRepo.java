package recipes.repos;

import org.springframework.data.repository.CrudRepository;
import recipes.model.User;

import java.util.Optional;

public interface UserRepo extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
