package CampusFlea.demo.controller;

import CampusFlea.demo.model.Account;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignInController {
//    @GetMapping("/signin")
//    public String signin(Model model) {
//        return "signin";
//    }
    @GetMapping("/signin")
    public String signin(Model model) {
        model.addAttribute("user", new Account());
        return "signin";
    }

    @PostMapping("/signin")
    public String processSignIn(Account account, BindingResult bindingResult){
        //TODO: Change to check DB (HARDCODED)
        String validUser = "user_test";
        String validPass = "pass";

        if(account.getUsername().equals(validUser) || account.getPassword().equals(validPass)){
            return "redirect:/home";
        }
        else
            return "#";

    }

}
