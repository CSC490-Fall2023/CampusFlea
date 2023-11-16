package CampusFlea.demo.controller;

import CampusFlea.demo.model.Listing;
import CampusFlea.demo.services.AccountService;
import CampusFlea.demo.services.DatabaseService;
import CampusFlea.demo.services.ListingService;
import CampusFlea.demo.services.SessionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Connection;

@Controller
public class ListingController {

    @GetMapping("/listing")
    public String list(Model model, HttpSession session, @RequestParam String id) {
        load(model, session, id);
        return "listview";
    }

    @RequestMapping(value = "/listing", params = "save")
    public String saveListing(Model model, HttpSession session, @RequestParam String id) {
        // Get current user id
        int userId = SessionService.getUserIdFromSession(session);

        // Toggle listing save
        ListingService.toggleSave(userId, id);

        // Load the page
        load(model, session, id);
        return "listview";
    }

    @RequestMapping(value = "/listing", params = "flag")
    public String flagListing(Model model, HttpSession session, @RequestParam String id) {
        load(model, session, id);
        return "listview";
    }

    private void load(Model model, HttpSession session, String listingId) {
        // Get the listing information
        int listingIdInt = Integer.parseInt(listingId);
        Listing listing = ListingService.getListing(listingIdInt);

        // Add image to listing
        String[] images = ListingService.getListingImages(listingIdInt);
        listing.setImages(images);

        // Create database connection
        DatabaseService dbSrv = new DatabaseService();
        Connection conn = dbSrv.getConnection();

        // Get the poster's username and profile picture
        int posterId = listing.getUid();
        String username = AccountService.getUsername(conn, posterId);
        String avatar = AccountService.getProfilePicture(posterId);

        // Attach the listing object
        model.addAttribute("listing", listing);

        // Attach additional used information
        model.addAttribute("username", username);
        model.addAttribute("avatar", avatar);

        // Get current user id
        int userId = SessionService.getUserIdFromSession(session);
        model.addAttribute("userId", userId);
    }
}
