package protopnet.mlprototypesfeedbackcollector.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller class for handling login-related requests.
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    /**
     * Endpoint for displaying the login form.
     *
     * @return the name of the Thymeleaf/JSP template for the login page.
     */
    @GetMapping
    public String loginForm() {
        return "Login";
    }
}
