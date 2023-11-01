package CampusFlea.demo.controller;

import CampusFlea.demo.model.Listing;
import CampusFlea.demo.services.ListingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EditListingController {
    @GetMapping("/editlisting")
    public String editListing(Model model, @RequestParam int id) {
        // Get the listing
        Listing listing = ListingService.getListing(id);

        // Add the listing to the model
        model.addAttribute("listing", listing);
        return "editlisting";
    }
}
