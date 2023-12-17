package protopnet.mlprototypesfeedbackcollector.repository;

import  protopnet.mlprototypesfeedbackcollector.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
