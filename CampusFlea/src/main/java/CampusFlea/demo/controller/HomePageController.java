package CampusFlea.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {

    private String test;

    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("test", test);
        return "home";
    }
}
