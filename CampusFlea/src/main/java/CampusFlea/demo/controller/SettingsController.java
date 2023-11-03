package CampusFlea.demo.controller;

import CampusFlea.demo.model.Account;
import CampusFlea.demo.services.AccountService;
import CampusFlea.demo.services.DatabaseService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Controller
public class SettingsController {
    @GetMapping("/settings")
    public String userSetting(Model model, HttpSession session) {
        // Get the user's session key
        String sessionKey = (String) session.getAttribute("session_key");

        // Check if session key is set
        if (sessionKey == null) {
            System.out.println("Did not find session key");
            return "redirect:/signin";
        }

        // Establish database connection
        DatabaseService dbSrv = new DatabaseService();
        Connection conn = dbSrv.getConnection();

        // Get the user id based on the session key
        int userId = AccountService.getUserIdFromSessionKey(conn, sessionKey);

        // Check that the session key is valid (redirect them to login otherwise)
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
        // Get the user's session key
        String sessionKey = (String) session.getAttribute("session_key");

        // Check if session key is set
        if (sessionKey == null) {
            System.out.println("Did not find session key");
            return "redirect:/signin";
        }

        // Establish database connection
        DatabaseService dbSrv = new DatabaseService();
        Connection conn = dbSrv.getConnection();

        // Get the user id based on the session key
        int userId = AccountService.getUserIdFromSessionKey(conn, sessionKey);

        // Check that the session key is valid (redirect them to login otherwise)
        if (userId == -1) {
            return "redirect:/signin";
        }

        System.out.printf("Username: %s, email: %s, password: %s", username, email, password);

        // Change the username if needed
        if (!username.isEmpty()) {
            String query = "UPDATE accounts SET username = ? WHERE id = ?;";

            // Prepare the query
            try {
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, username);
                preparedStatement.setInt(2, userId);

                // Execute the query
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        // Change the email if needed
        if (!email.isEmpty()) {
            String query = "UPDATE accounts SET email = ? WHERE id = ?;";

            // Prepare the query
            try {
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, email);
                preparedStatement.setInt(2, userId);

                // Execute the query
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        // Change the password if needed
        if (!password.isEmpty()) {
            // TODO: password
        }

        // Update the avatar if needed
        if (!avatar.isEmpty()) {
            // Download the files
            String fileName = avatar.getOriginalFilename();
            byte[] bytes = avatar.getBytes();

            // Make sure the directory exists
            String imageDir = "CampusFlea/target/classes/static/uploads/avatars/" + userId;
            File directory = new File(imageDir);
            if (!directory.exists()) {
                directory.mkdir();
            } else {
                for (File file : directory.listFiles()) {
                    if (file.isFile()) {
                        file.delete();
                    }
                }
            }

            // save the image to disk
            Path path = Paths.get(imageDir, fileName);
            Files.write(path, bytes);
        }
        return "redirect:/settings";
    }
}
