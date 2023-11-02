package CampusFlea.demo.controller;

import CampusFlea.demo.model.Account;
import CampusFlea.demo.services.AccountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class ChatController {
    @GetMapping("/getUsername")
    public String Chat(Model model, HttpSession session) {
        // get username
        String sessionKey = (String) session.getAttribute("session_key");
        // Check if session key is set
        if (sessionKey == null) {
            System.out.println("Did not find session key");
            return "redirect:/signin";
        }
        System.out.printf("Found session key: %s\n", sessionKey);
        int userId = AccountService.getUserIdFromSessionKey(sessionKey);
        Account user = AccountService.getAccount(userId);
        String username = user.getUsername();
        return username;
    }
}
