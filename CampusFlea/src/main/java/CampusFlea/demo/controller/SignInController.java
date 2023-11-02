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
public class SignInController {
    @GetMapping("/signin")
    public String signin(Model model) {
        return "signin";
    }

    @PostMapping("/signin")
    public String processSignIn(@RequestParam String username, @RequestParam String password, HttpSession session) {
        System.out.printf("Username=%s, password=%s\n", username, password);

        // Establish a database connection
        DatabaseService dbSrv = new DatabaseService();
        Connection conn = dbSrv.getConnection();

        // Check if credentials are valid
        boolean isValidCredentials = AccountService.isValidCredentials(conn, username, password);
        if (!isValidCredentials) {
            System.out.println("Username/password not valid!");
            return "redirect:/signin";
        }

        // Get the user id and use it to create a new session key
        int userId = AccountService.getId(conn, username);

        System.out.printf("Found userId: %d\n", userId);

        String sessionKey = AccountService.createLoginSession(conn, userId);

        if (sessionKey == null) {
            System.out.println("sessionKey is null");
            return "redirect:/signin";
        }

        System.out.printf("Found sessionKey: %s\n", sessionKey);

        // Save the session key
        session.setAttribute("session_key", sessionKey);

        return "redirect:/";
    }
}
