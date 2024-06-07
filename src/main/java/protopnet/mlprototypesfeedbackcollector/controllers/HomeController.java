package protopnet.mlprototypesfeedbackcollector.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller class for handling home and static page-related requests.
 */
@Controller
public class HomeController {

    /**
     * Endpoint for displaying the home page for non-registered users.
     *
     * @param model the model to be used for adding attributes.
     * @return the name of the Thymeleaf/JSP template for the home page.
     */
    @GetMapping("/")
    public String showHomePage(Model model) {
        return "HomePage";
    }

    /**
     * Endpoint for displaying the home page for registered users.
     *
     * @param model the model to be used for adding attributes.
     * @return the name of the Thymeleaf/JSP template for the registered users' home page.
     */
    @GetMapping("/home")
    public String showHomePageForRegisteredUser(Model model) {
        return "HomePageForRegisteredUser";
    }

    /**
     * Endpoint for displaying the About Us page.
     *
     * @param model the model to be used for adding attributes.
     * @return the name of the Thymeleaf/JSP template for the About Us page.
     */

    @GetMapping("/about-us")
    public String showAboutUs(Model model) {
        return "AboutUs";
    }
}
