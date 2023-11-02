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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;

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
        String avatar = AccountService.getProfilePicture(conn, userId);

        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("avatar", avatar);
        return "usersettings";
    }

    @PostMapping("/settings")
    public static void uploadAvatar(@RequestParam MultipartFile file) throws IOException {
        System.out.println(file);

        //MultipartFile file = request.getFile("image");
        String fileName = file.getOriginalFilename();
        byte[] bytes = file.getBytes();

        // save the image to disk
        Path path = Paths.get("uploads", fileName);
        Files.write(path, bytes);
    }
}
