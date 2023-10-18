package CampusFlea.demo.controller;

import CampusFlea.demo.model.Listing;
import CampusFlea.demo.services.AccountService;
import CampusFlea.demo.services.ListingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import CampusFlea.demo.model.Account;

@Controller
public class HomePageController {
    @GetMapping("/home")
    public String home(Model model) {
        // Initialize user account
        int userId = 1;
        Account user = AccountService.getAccount(userId);

        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());

        System.out.printf("Logged in (username=%s, email=%s)\n", user.getUsername(), user.getEmail());

        Listing[] listings = ListingService.getAllListings();

        //print to console listings w/ id
        for (Listing listing : listings) {
            System.out.printf("Showing listing (id=%d, title=%s)\n", listing.getId(), listing.getTitle());
        }

        // Add to model for ThymeLeaf to read
        model.addAttribute("listings", listings);
        return "home";
    }

    @GetMapping("/settings")
    public String userSetting(Model model){
        Account user = AccountService.getAccount(1);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "userSetting";
    }
}
