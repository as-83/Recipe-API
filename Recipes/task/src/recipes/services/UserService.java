package recipes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import recipes.model.User;
import recipes.repos.UserRepo;

@Service
public class UserService {
    final UserRepo userRepo;
    final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }


    public boolean tryToAddUser(User user) {
        if (! userRepo.findByEmail(user.getEmail()).isPresent()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.save(user);
            return true;
        }
        return false;
    }
}
