package CampusFlea.demo.controller;

import CampusFlea.demo.model.Account;
import CampusFlea.demo.model.Listing;
import CampusFlea.demo.services.AccountService;
import CampusFlea.demo.services.ListingService;
import CampusFlea.demo.services.SessionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SellingController {
    @GetMapping("/selling")
    public String selling(Model model, HttpSession session) {
        // Check that the session key is valid (redirect them to login otherwise)
        int userId = SessionService.getUserIdFromSession(session);
        if (userId == -1) {
            return "redirect:/signin";
        }

        // Load username as attribute
        Account user = AccountService.getAccount(userId);
        model.addAttribute("username", user.getUsername());

        // Add the avatar link for loading
        String avatar = AccountService.getProfilePicture(userId);
        model.addAttribute("avatar", avatar);

        Listing[] listings = ListingService.getAllUserListings(userId);

        // Go through each listing
        for (int i = 0; i < listings.length; i++) {
            int listingId = listings[i].getId();

            // Add image to listings
            String[] images = ListingService.getListingImages(listingId);
            listings[i].setImage(images[0]);
        }

        //add to model for ThymeLeaf to read
        model.addAttribute("listings", listings);

        return "selling";
    }
}
