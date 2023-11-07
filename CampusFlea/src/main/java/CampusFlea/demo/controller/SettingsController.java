package CampusFlea.demo.controller;

import CampusFlea.demo.model.Account;
import CampusFlea.demo.services.AccountService;
import CampusFlea.demo.services.SessionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class SettingsController {
    @GetMapping("/settings")
    public String userSetting(Model model, HttpSession session) {
        // Check that the session key is valid (redirect them to login otherwise)
        int userId = SessionService.getUserIdFromSession(session);
        if (userId == -1) {
            return "redirect:/signin";
        }

        // Create the account object from the found userId
        Account user = AccountService.getAccount(userId);
        String avatar = AccountService.getProfilePicture(userId);

        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("avatar", avatar);
        return "usersettings";
    }

    @PostMapping("/settings")
    public String updateSettings(@RequestParam String username, @RequestParam String email, @RequestParam String password, @RequestParam MultipartFile avatar, HttpSession session) throws IOException {
        // Check that the session key is valid (redirect them to login otherwise)
        int userId = SessionService.getUserIdFromSession(session);
        if (userId == -1) {
            return "redirect:/signin";
        }

        // Change the username if needed
        if (!username.isEmpty()) {
            AccountService.updateUsername(userId, username);
        }

        // Change the email if needed
        if (!email.isEmpty()) {
            AccountService.updateEmail(userId, email);
        }

        // Change the password if needed
        if (!password.isEmpty()) {
            AccountService.updatePassword(userId, password);
        }

        // Update the avatar if needed
        if (!avatar.isEmpty()) {
            AccountService.updateAvatar(userId, avatar);
        }
        return "redirect:/settings";
    }
}
