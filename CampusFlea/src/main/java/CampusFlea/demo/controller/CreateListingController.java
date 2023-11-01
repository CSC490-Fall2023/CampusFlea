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
    public String processCreateListing(@RequestParam String title, @RequestParam int category, @RequestParam int price, @RequestParam String description, HttpSession session) {
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
        ListingService.createListing(conn, title, description, price, category, userId);

        // TODO: Add visual confirmation

        // Redirect back to home
        return "redirect:/home";
    }
}
