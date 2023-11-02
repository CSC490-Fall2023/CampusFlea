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
public class ProfileController {
    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {
        // Get the user's session key
        String sessionKey = (String) session.getAttribute("session_key");

        // Check if session key is set
        if (sessionKey == null) {
            System.out.println("Did not find session key");
            return "redirect:/signin";
        }

        System.out.printf("Found session key: %s\n", sessionKey);

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
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());

        Listing[] listings = ListingService.getAllUserListings(conn, userId);
        String avatar = AccountService.getProfilePicture(conn, userId);

        // Set the appearance image of each listing
        for (int i = 0; i < listings.length; i++) {
            int listingId = listings[i].getId();
            String[] images = ListingService.getListingImages(listingId);
            listings[i].setImage(images[0]);
        }

        //add to model for ThymeLeaf to read
        model.addAttribute("listings", listings);
        model.addAttribute("avatar", avatar);

        return "profile";
    }
}
