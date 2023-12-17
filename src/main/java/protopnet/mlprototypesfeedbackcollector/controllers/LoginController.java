package protopnet.mlprototypesfeedbackcollector.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import protopnet.mlprototypesfeedbackcollector.model.User;
import protopnet.mlprototypesfeedbackcollector.repository.UserRepository;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @PostMapping
    public String loginPOST(@RequestParam String username, @RequestParam String password, Model model) {
        User user = userRepository.findByUsername(username);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "Login";
        }
    }

    @GetMapping
    public String loginForm() {
        return "Login";
    }
}
