package CampusFlea.demo.controller;

import CampusFlea.demo.model.Listing;
import CampusFlea.demo.services.ListingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EditListingController {
    @GetMapping("/editlisting")
    public String editListing(Model model) {
        // Get the listing
        int listingId = 1;
        Listing listing = ListingService.getListing(listingId);

        // Add the listing to the model
        model.addAttribute("listing", listing);
        return "editlisting";
    }
}
