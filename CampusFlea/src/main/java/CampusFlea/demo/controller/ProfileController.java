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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfileController {
    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {
        // Check that the session key is valid (redirect them to login otherwise)
        int userId = SessionService.getUserIdFromSession(session);
        if (userId == -1) {
            return "redirect:/signin";
        }

        // Create the account object from the found userId
        Account user = AccountService.getAccount(userId);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());

        Listing[] listings = ListingService.getAllUserListings(userId);
        String avatar = AccountService.getProfilePicture(userId);

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

    @RequestMapping(value = "/profile", params = "delete")
    public String deleteListing(@RequestParam String listingId, HttpSession session) {
        // Check that the session key is valid (redirect them to login otherwise)
        int userId = SessionService.getUserIdFromSession(session);
        if (userId == -1) {
            return "redirect:/signin";
        }

        // Save the listing
        ListingService.deleteListing(Integer.parseInt(listingId));

        // Reload
        return "redirect:/profile";
    }
}
