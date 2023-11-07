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
public class SavedController {
    @GetMapping("/saved")
    public String saved(Model model, HttpSession session) {
        // Check that the session key is valid (redirect them to login otherwise)
        int userId = SessionService.getUserIdFromSession(session);
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

        Listing[] listings = ListingService.getSavedListings(userId);

        for (int i = 0; i < listings.length; i++) {
            int listingId = listings[i].getId();
            String image = ListingService.getListingImages(listingId)[0];
            listings[i].setImage(image);
        }

        //add to model for ThymeLeaf to read
        model.addAttribute("listings", listings);

        return "saved";
    }

    @RequestMapping(value = "/saved", params = "save")
    public String saveListing(@RequestParam String listingId, HttpSession session) {
        // Check that the session key is valid (redirect them to login otherwise)
        int userId = SessionService.getUserIdFromSession(session);
        if (userId == -1) {
            return "redirect:/signin";
        }

        // Save the listing
        ListingService.toggleSave(userId, listingId);

        // Reload
        return "redirect:/saved";
    }
}
