package protopnet.mlprototypesfeedbackcollector.config;

import lombok.RequiredArgsConstructor;
import protopnet.mlprototypesfeedbackcollector.repository.UserRepository;
import protopnet.mlprototypesfeedbackcollector.service.CustomUserDetailsService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
        public CustomUserDetailsService customUserDetailsService(UserRepository userRepository,
                        PasswordEncoder passwordEncoder) {
                return new CustomUserDetailsService(userRepository, passwordEncoder);
        }
}
