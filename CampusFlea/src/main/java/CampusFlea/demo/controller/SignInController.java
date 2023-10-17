package CampusFlea.demo.controller;

import CampusFlea.demo.model.Account;
import CampusFlea.demo.services.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignInController {
    @GetMapping("/signin")
    public String signin(Model model) {
        model.addAttribute("user", new Account());
        return "signin";
    }

    @PostMapping("/signin")
    public String processSignIn(Account account, BindingResult bindingResult){
        System.out.printf("Username=%s, password=%s\n", account.getUsername(), account.getPassword());

        // Check if credentials are valid
        boolean isValidCredentials = AccountService.isValidCredentials(account.getUsername(), account.getPassword());
        if (!isValidCredentials) {
            System.out.println("Username/password not valid!");
            return "redirect:/signin";
        }

        // Get the account object
        int userId = AccountService.getId(account.getUsername());
        Account userAccount = AccountService.getAccount(userId);

        // TODO -Gene: Create a session key for the user
        System.out.println("Logged in!");
        return "redirect:/home";
    }
}
