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

import java.sql.Connection;

@Controller
public class MessagingController {
    @GetMapping("/chat")
    public String buying(Model model, HttpSession session) {
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
        // load items from saved items in DB
        Account user = AccountService.getAccount(userId);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());

        // Add the avatar link for loading
        String avatar = AccountService.getProfilePicture(userId);
        model.addAttribute("avatar", avatar);

        Listing[] listings = ListingService.getAllListings(conn);

        //TODO: ONLY SHOW SELLING CATEGORY
        for (Listing listing : listings) {
            System.out.printf("Showing listing (id=%d, title=%s)\n", listing.getId(), listing.getTitle());
        }
        //add to model for ThymeLeaf to read
        model.addAttribute("listings", listings);

        return "chat";
    }
}
