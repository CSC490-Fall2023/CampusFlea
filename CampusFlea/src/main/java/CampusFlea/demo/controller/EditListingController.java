package CampusFlea.demo.controller;

import CampusFlea.demo.model.Listing;
import CampusFlea.demo.services.DatabaseService;
import CampusFlea.demo.services.ListingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;

@Controller
public class EditListingController {
    @GetMapping("/editlisting")
    public String editListing(Model model, @RequestParam int id) {
        // Get the listing
        Listing listing = ListingService.getListing(id);

        // Add the listing to the model
        model.addAttribute("listing", listing);
        model.addAttribute("id", id);
        return "editlisting";
    }

    @PostMapping("/editlisting")
    public String processEditListing(@RequestParam int id, @RequestParam String title, @RequestParam int category, @RequestParam int price, @RequestParam String description, @RequestParam MultipartFile images) throws IOException {
        // Create a new instance of database
        DatabaseService dbSrv = new DatabaseService();
        Connection conn = dbSrv.getConnection();

        // Download the files
        String fileName = images.getOriginalFilename();
        byte[] bytes = images.getBytes();

        // Make sure the directory exists
        String imageDir = "CampusFlea/target/classes/static/uploads/listings/" + id;
        File directory = new File(imageDir);
        if (!directory.exists()) {
            directory.mkdir();
        }
        // save the image to disk
        Path path = Paths.get(imageDir, fileName);
        Files.write(path, bytes);

        // Update the listing
        ListingService.updateListing(conn, id, title, description, price, category);

        // Redirect back to profile
        return "redirect:/profile";
    }
}
