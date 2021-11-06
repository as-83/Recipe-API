package recipes.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import recipes.model.User;

import javax.persistence.Column;
import java.util.Optional;

@Component
public interface UserRepo extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
