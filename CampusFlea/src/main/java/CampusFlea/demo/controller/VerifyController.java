package CampusFlea.demo.controller;

import CampusFlea.demo.services.AccountService;
import CampusFlea.demo.services.SessionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VerifyController {
    @GetMapping("/verify")
    public String verify(Model model, HttpSession session) {
        // Check that the session key is valid (redirect them to login otherwise)
        int userId = SessionService.getUserIdFromSession(session);
        if (userId == -1) {
            return "redirect:/signin";
        }

        // Check if the account is already verified
        boolean verified = AccountService.isVerified(userId);
        if (verified) {
            return "redirect:/";
        }

        return "verify";
    }

    @PostMapping("/verify")
    public String processVerification(Model model, HttpSession session, @RequestParam String verificationCode)  {
        // Check that the session key is valid (redirect them to login otherwise)
        int userId = SessionService.getUserIdFromSession(session);
        if (userId == -1) {
            return "redirect:/signin";
        }

        // Get the user's actual verification code
        String realCode = AccountService.getVerificationCode(userId);

        // Compare codes
        if (verificationCode.equals(realCode)) {
            // Set as verified and return
            AccountService.setVerified(userId);
            return "redirect:/";
        }

        // Verification code must have been incorrect
        model.addAttribute("invalid", true);

        return "verify";
    }
}
