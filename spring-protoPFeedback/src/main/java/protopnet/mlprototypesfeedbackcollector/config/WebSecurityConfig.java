package protopnet.mlprototypesfeedbackcollector.config;

import lombok.RequiredArgsConstructor;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import protopnet.mlprototypesfeedbackcollector.service.CustomUserDetailsService;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig {



        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable())
                                .headers(headers -> headers.frameOptions(Customizer.withDefaults()))
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/css/**", "/js/**", "/images/**", "/fonts/**",
                                                                "/webjars/**")
                                                .permitAll()
                                                .requestMatchers("/").permitAll()
                                                .requestMatchers("/register/**").permitAll()
                                                .requestMatchers("/").permitAll()
                                                .requestMatchers("/about-us").permitAll()
                                                .requestMatchers(PathRequest.toH2Console()).permitAll()
                                                .anyRequest().authenticated())
                                .formLogin(
                                                form -> form
                                                                .loginPage("/login")
                                                                .loginProcessingUrl("/login")
                                                                .defaultSuccessUrl("/home", true)
                                                                .failureUrl("/login?error")
                                                                .permitAll())
                                .logout(logout -> logout
                                                .logoutUrl("/logout")
                                                .logoutSuccessUrl("/")
                                                .invalidateHttpSession(true)
                                                .deleteCookies("JSESSIONID"));
                ;

                return http.build();
        }

        @Bean
        public WebMvcConfigurer corsConfigurer() {
                return new WebMvcConfigurer() {
                        @Override
                        public void addCorsMappings(CorsRegistry registry) {
                                registry.addMapping("/**")
                                        .allowedOrigins("http://localhost:3000/")
                                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                                        .allowedHeaders("*")
                                        .allowCredentials(true);
                        }
                };
        }


//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedOrigins("http://localhost:3000")
//                        .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
//                        .allowedHeaders("*")
//                        .allowCredentials(true);
//            }
//        };
//    }
}