package CampusFlea.demo.controller;

import CampusFlea.demo.model.Listing;
import CampusFlea.demo.services.ListingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ListingController {

    @GetMapping("/listing")
    public String list(Model model) {
        // Get the listing id
        int listingId = 1;

        // Get the listing information
        Listing listing = ListingService.getListing(listingId);

        // Attach the listing object
        model.addAttribute("listing", listing);
        return "ListView";
    }
}
