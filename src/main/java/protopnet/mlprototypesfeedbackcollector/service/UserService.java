package protopnet.mlprototypesfeedbackcollector.service;

import protopnet.mlprototypesfeedbackcollector.model.User;
import protopnet.mlprototypesfeedbackcollector.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Service class for handling user-related operations.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor for UserService.
     *
     * @param userRepository the user repository to be used by the service.
     * @param passwordEncoder the password encoder to be used by the service.
     */
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new user.
     *
     * @param user the user to register.
     * @return the registered user.
     * @throws RuntimeException if the username is already taken.
     */
    public User registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("Username already taken");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Finds a user by their username.
     *
     * @param username the username of the user to find.
     * @return the user with the specified username, or null if no user is found.
     */
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}