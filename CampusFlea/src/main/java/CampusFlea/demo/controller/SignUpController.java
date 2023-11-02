package CampusFlea.demo.controller;

import CampusFlea.demo.model.Account;
import CampusFlea.demo.services.AccountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignUpController {
    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new Account());
        return "signup";
    }

    @PostMapping("/signup")
    public String processSignUp(Account account, HttpSession session){
        System.out.printf("Username=%s, password=%s, Email=%s\n", account.getUsername(), account.getPassword(), account.getEmail());

        // Create the account
        boolean created = AccountService.createAccount(account.getUsername(), account.getPassword(), account.getEmail());

        // Redirect the new user to the home page
        if (created) {
            // Create a new session key
            int userId = AccountService.getId(account.getUsername());
            String sessionKey = AccountService.createLoginSession(userId);

            // Save the session key
            session.setAttribute("session_key", sessionKey);

            return "redirect:/home";
        }

        // If created unsuccessfully, redirect the user back to the signup
        return "redirect:/signup";
    }
}
