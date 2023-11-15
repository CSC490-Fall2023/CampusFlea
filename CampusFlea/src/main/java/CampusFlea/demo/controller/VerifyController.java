package CampusFlea.demo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VerifyController {
    @GetMapping("/verify")
    public String verify(Model model, HttpSession session) {
        return "verify";
    }
}
