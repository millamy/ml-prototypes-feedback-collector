package protopnet.mlprototypesfeedbackcollector.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import protopnet.mlprototypesfeedbackcollector.model.User;
import protopnet.mlprototypesfeedbackcollector.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom implementation of UserDetailsService to load user-specific data.
 */
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor for CustomUserDetailsService.
     *
     * @param userRepository the user repository to be used by the service.
     * @param passwordEncoder the password encoder to be used by the service.
     */
    public CustomUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Loads the user by username.
     *
     * @param username the username of the user to load.
     * @return the UserDetails object containing user data.
     * @throws UsernameNotFoundException if the user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        System.out.println("CustomUserDetailsService.loadUserByUsername() called");

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        System.out.println("User found: " + user.getUsername());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

}
