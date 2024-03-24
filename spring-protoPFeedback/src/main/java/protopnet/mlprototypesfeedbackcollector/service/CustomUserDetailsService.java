package protopnet.mlprototypesfeedbackcollector.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import protopnet.mlprototypesfeedbackcollector.repository.UserRepository;
import protopnet.mlprototypesfeedbackcollector.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;




import java.util.ArrayList;
import java.util.List;


public class CustomUserDetailsService implements UserDetailsService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        //debug log to console
        System.out.println("CustomUserDetailsService.loadUserByUsername() called");

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        //Debbuing log to console
        System.out.println("User found: " + user.getUsername());


        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

}
