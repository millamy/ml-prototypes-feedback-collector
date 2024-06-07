package protopnet.mlprototypesfeedbackcollector.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import protopnet.mlprototypesfeedbackcollector.model.User;
import protopnet.mlprototypesfeedbackcollector.service.UserService;

/**
 * Controller class for handling user registration-related requests.
 */
@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;

    /**
     * Constructor for RegisterController.
     *
     * @param userService the user service to be used by the controller.
     */
    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint for displaying the registration form.
     *
     * @return the name of the Thymeleaf/JSP template for the registration page.
     */
    @GetMapping
    public String showRegistrationForm() {
        return "Register";
    }

    /**
     * Endpoint for handling user registration.
     *
     * @param user the user object containing registration details.
     * @return a redirect to the login page with a success message or back to the registration page if the username is taken.
     */
    @PostMapping
    public String registerUser(User user) {
        User existingUser = userService.findByUsername(user.getUsername());
        if (existingUser != null) {
            return "redirect:/register?usernameTaken";
        }

        userService.registerUser(user);
        return "redirect:/login?regisrationSuccess";
    }

}

