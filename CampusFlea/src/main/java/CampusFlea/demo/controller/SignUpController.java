package CampusFlea.demo.controller;

import CampusFlea.demo.services.AccountService;
import CampusFlea.demo.services.DatabaseService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Connection;

@Controller
public class SignUpController {
    @GetMapping("/signup")
    public String signup(Model model) {
        return "signup";
    }

    @PostMapping("/signup")
    public String processSignUp(@RequestParam String username, @RequestParam String password, @RequestParam String email, HttpSession session) {
        // Establish a database connection
        DatabaseService dbSrv = new DatabaseService();
        Connection conn = dbSrv.getConnection();

        // Return back to signup if email does not end in '.edu'
        if (!email.endsWith("@uncg.edu")) {
            return "redirect:/signup";
        }

        // Create the account
        boolean created = AccountService.createAccount(conn, username, password, email);

        // Redirect the new user to the home page
        if (created) {
            // Create a new session key
            int userId = AccountService.getId(conn, username);
            String sessionKey = AccountService.createLoginSession(conn, userId);

            // Save the session key
            session.setAttribute("session_key", sessionKey);

            // Create a new verification request
            AccountService.createNewVerification(userId);

            // Redirect to verification page
            return "redirect:/verify";
        }

        // If created unsuccessfully, redirect the user back to the signup
        return "redirect:/signup";
    }
}
