package CampusFlea.demo.controller;

import CampusFlea.demo.model.Account;
import CampusFlea.demo.services.AccountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import CampusFlea.demo.websocket.pojo.*;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SignInController {
    @GetMapping("/signin")
    public String signin(Model model) {
        model.addAttribute("user", new Account());
        return "signin";
    }

    @PostMapping("/signin")
    public String processSignIn(Account account, HttpSession session){
        System.out.printf("Username=%s, password=%s\n", account.getUsername(), account.getPassword());

        // Check if credentials are valid
        boolean isValidCredentials = AccountService.isValidCredentials(account.getUsername(), account.getPassword());
        if (!isValidCredentials) {
            System.out.println("Username/password not valid!");
            return "redirect:/signin";
        }

        // Get the user id and use it to create a new session key
        int userId = AccountService.getId(account.getUsername());
        String sessionKey = AccountService.createLoginSession(userId);

        // Save the session key
        session.setAttribute("session_key", sessionKey);

        return "redirect:/home";
    }
}
