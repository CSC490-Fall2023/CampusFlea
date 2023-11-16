package CampusFlea.demo.controller;

import CampusFlea.demo.model.Account;
import CampusFlea.demo.services.AccountService;
import CampusFlea.demo.services.SessionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminCPController {
    @GetMapping("/admincp")
    public String adminCP(Model model, HttpSession session) {
        // Check that the session key is valid (redirect them to login otherwise)
        int userId = SessionService.getUserIdFromSession(session);
        if (userId == -1) {
            return "redirect:/signin";
        }

        // Make sure the user has admin privileges, otherwise redirect back to home
        Account adminAccount = AccountService.getAccount(userId);
        if (!adminAccount.getIsAdmin()) {
            return "redirect:/";
        }

        // Get all users
        Account[] accounts = AccountService.getAllAccounts();
        model.addAttribute("users", accounts);
        return "admincp";
    }
}
