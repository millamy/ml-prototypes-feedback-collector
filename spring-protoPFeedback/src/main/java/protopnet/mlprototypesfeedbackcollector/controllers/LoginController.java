package protopnet.mlprototypesfeedbackcollector.controllers;


import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public String loginForm() {
        return "Login";
    }
}
