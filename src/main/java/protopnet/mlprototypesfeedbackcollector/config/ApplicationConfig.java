package protopnet.mlprototypesfeedbackcollector.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import protopnet.mlprototypesfeedbackcollector.repository.UserRepository;
import protopnet.mlprototypesfeedbackcollector.service.CustomUserDetailsService;

/**
 * Configuration class for application-wide beans.
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    /**
     * Bean definition for PasswordEncoder.
     *
     * @return a BCryptPasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean definition for CustomUserDetailsService.
     *
     * @param userRepository  the user repository to be used by the service.
     * @param passwordEncoder the password encoder to be used by the service.
     * @return a CustomUserDetailsService instance.
     */
    @Bean
    public CustomUserDetailsService customUserDetailsService(UserRepository userRepository,
                                                             PasswordEncoder passwordEncoder) {
        return new CustomUserDetailsService(userRepository, passwordEncoder);
    }
}
