package CampusFlea.demo.controller;

import CampusFlea.demo.model.Listing;
import CampusFlea.demo.services.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import CampusFlea.demo.model.Account;

import static CampusFlea.demo.services.ListingService.getAllListings;

@Controller
public class HomePageController {
    @GetMapping("/home")
    public String home(Model model) {
        // TODO: Get userId from session cookies
        int userId = 1;
        Account user = AccountService.getAccount(userId);

        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());

        System.out.printf("Logged in (username=%s, email=%s)\n", user.getUsername(), user.getEmail());

        Listing[] listings = getAllListings();

        //print to console listings w/ id
        for (Listing listing : listings) {
            System.out.printf("Showing listing (id=%d, title=%s)\n", listing.getId(), listing.getTitle());
        }
        //add to model for ThymeLeaf to read
        model.addAttribute("listings", listings);

        return "home";
    }
}
