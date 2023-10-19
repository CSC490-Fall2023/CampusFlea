package CampusFlea.demo.controller;

import CampusFlea.demo.model.Listing;
import CampusFlea.demo.services.ListingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ListingController {

    @GetMapping("/listing")
    public String list(Model model, @RequestParam String id) {
        // Get the listing information
        int listingId = Integer.parseInt(id);
        Listing listing = ListingService.getListing(listingId);
        //System.out.printf("listing title=%s\n", listing.getTitle());

        // Attach the listing object
        model.addAttribute("listing", listing);
        return "listview";
    }
}
