package CampusFlea.demo.controller;

import CampusFlea.demo.model.Listing;
import CampusFlea.demo.services.AccountService;
import CampusFlea.demo.services.DatabaseService;
import CampusFlea.demo.services.ListingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Connection;

@Controller
public class ListingController {

    @GetMapping("/listing")
    public String list(Model model, @RequestParam String id) {
        // Get the listing information
        int listingId = Integer.parseInt(id);
        Listing listing = ListingService.getListing(listingId);

        // Add image to listing
        String image = ListingService.getListingImages(listingId)[0];
        listing.setImage(image);

        // Create database connection
        DatabaseService dbSrv = new DatabaseService();
        Connection conn = dbSrv.getConnection();

        // Get the poster's username and profile picture
        int posterId = listing.getUid();
        String username = AccountService.getUsername(conn, posterId);
        String avatar = AccountService.getProfilePicture(posterId);

        // Attach the listing object
        model.addAttribute("listing", listing);
        model.addAttribute("category", ListingService.CATEGORIES[listing.getCategory()]);
        model.addAttribute("username", username);
        model.addAttribute("avatar", avatar);
        return "listview";
    }
}
