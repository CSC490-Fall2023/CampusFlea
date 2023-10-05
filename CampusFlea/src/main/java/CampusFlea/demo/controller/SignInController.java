package CampusFlea.demo.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class SignInController {
    @GetMapping("/signin")
    public String home(Model model) {
        return "signin";
    }
}
