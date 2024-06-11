package protopnet.mlprototypesfeedbackcollector.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Model class representing a user.
 */
@Setter
@Getter
@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
}
