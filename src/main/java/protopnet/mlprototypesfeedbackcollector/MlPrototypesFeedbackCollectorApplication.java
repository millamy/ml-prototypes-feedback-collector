package protopnet.mlprototypesfeedbackcollector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Main application class for the ML Prototypes Feedback Collector application.
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableJpaRepositories("protopnet.mlprototypesfeedbackcollector")
@ComponentScan(basePackages = {"protopnet.mlprototypesfeedbackcollector.model"})
@EntityScan("protopnet.mlprototypesfeedbackcollector.model")
public class MlPrototypesFeedbackCollectorApplication {

    /**
     * Main method to run the Spring Boot application.
     *
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(MlPrototypesFeedbackCollectorApplication.class, args);
    }
}
