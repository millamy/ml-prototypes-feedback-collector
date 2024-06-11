package protopnet.mlprototypesfeedbackcollector.repository;

import  protopnet.mlprototypesfeedbackcollector.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for User entities.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their username.
     *
     * @param username the username of the user to find.
     * @return the user with the specified username, or null if no user is found.
     */
    User findByUsername(String username);
}
