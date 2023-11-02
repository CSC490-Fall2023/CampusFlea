package CampusFlea.demo.controller;

import CampusFlea.demo.model.Account;
import CampusFlea.demo.model.Listing;
import CampusFlea.demo.services.AccountService;
import CampusFlea.demo.services.DatabaseService;
import CampusFlea.demo.services.ListingService;
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

@Controller
public class CreateListingController {
    @GetMapping("/createlisting")
    public String createListing(Model model, HttpSession session) {
        // Get the user's session key
        String sessionKey = (String) session.getAttribute("session_key");

        // Check if session key is set
        if (sessionKey == null) {
            System.out.println("Did not find session key");
            return "redirect:/signin";
        }

        return "createlisting";
    }

    @PostMapping("/createlisting")
    public String processCreateListing(@RequestParam String title, @RequestParam int category, @RequestParam int price, @RequestParam String description, @RequestParam MultipartFile images, HttpSession session) throws IOException {
        System.out.println(title);

        // Get the user's session key
        String sessionKey = (String) session.getAttribute("session_key");

        // Check if session key is set
        if (sessionKey == null) {
            System.out.println("Did not find session key");
            return "redirect:/signin";
        }

        // Establish a database connection
        DatabaseService dbSrv = new DatabaseService();
        Connection conn = dbSrv.getConnection();

        System.out.printf("Found session key: %s\n", sessionKey);

        // Get the user id based on the session key
        int userId = AccountService.getUserIdFromSessionKey(conn, sessionKey);

        // Check that the session key is valid (redirect them to login otherwise)
        if (userId == -1) {
            return "redirect:/signin";
        }

        // Create the new listing
        int listingId = ListingService.createListing(conn, title, description, price, category, userId);

        if (listingId == -1) {
            System.out.println("Invalid listingId.");
            return "redirect:/";
        }

        // Download the files
        String fileName = images.getOriginalFilename();
        byte[] bytes = images.getBytes();

        // Make sure the directory exists
        String imageDir = "CampusFlea/target/classes/static/uploads/listings/" + listingId;
        File directory = new File(imageDir);
        if (!directory.exists()) {
            directory.mkdir();
        }
        // save the image to disk
        Path path = Paths.get(imageDir, fileName);
        Files.write(path, bytes);

        // TODO: Add visual confirmation

        // Redirect back to home
        return "redirect:/";
    }
}
