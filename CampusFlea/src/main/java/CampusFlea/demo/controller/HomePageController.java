package CampusFlea.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import CampusFlea.demo.model.account;

@Controller
public class HomePageController {

    private String username;
    account user = new account();

    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "home";
    }
}
