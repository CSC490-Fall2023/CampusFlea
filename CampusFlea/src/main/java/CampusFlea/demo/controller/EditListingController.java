package CampusFlea.demo.controller;

import CampusFlea.demo.model.Listing;
import CampusFlea.demo.services.DatabaseService;
import CampusFlea.demo.services.ListingService;
import org.h2.engine.Database;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Connection;

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

    @PostMapping("/editlisting")
    public String processEditListing(@RequestParam int id, @RequestParam String title, @RequestParam int category, @RequestParam int price, @RequestParam String description) {
        // Create a new instance of database
        DatabaseService dbSrv = new DatabaseService();
        Connection conn = dbSrv.getConnection();

        // Update the listing
        ListingService.updateListing(conn, id, title, description, price, category);

        // Redirect back to profile
        return "redirect:/profile";
    }
}
