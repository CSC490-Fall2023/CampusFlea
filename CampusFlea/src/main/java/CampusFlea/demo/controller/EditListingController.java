package CampusFlea.demo.controller;

import CampusFlea.demo.model.Listing;
import CampusFlea.demo.model.Counter;
import CampusFlea.demo.services.DatabaseService;
import CampusFlea.demo.services.ListingService;
import CampusFlea.demo.services.SessionService;
import jakarta.servlet.http.HttpSession;
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
    public String editListing(Model model, @RequestParam int id, HttpSession session) {
        // Check that the session key is valid (redirect them to login otherwise)
        int userId = SessionService.getUserIdFromSession(session);
        if (userId == -1) {
            return "redirect:/signin";
        }

        // Attach counter
        model.addAttribute("counter", new Counter());

        // Attach categories
        model.addAttribute("categories", ListingService.CATEGORIES);

        // Get the listing
        Listing listing = ListingService.getListing(id);

        // Add the listing to the model
        model.addAttribute("listing", listing);
        model.addAttribute("id", id);
        return "editlisting";
    }

    @PostMapping("/editlisting")
    public String processEditListing(@RequestParam int id, @RequestParam String title, @RequestParam int type, @RequestParam int category, @RequestParam int price, @RequestParam String description, @RequestParam MultipartFile[] images) throws IOException {
        // Create a new instance of database
        DatabaseService dbSrv = new DatabaseService();
        Connection conn = dbSrv.getConnection();

        // Download the files if exists
        if (images != null) {
            for (MultipartFile image : images) {
                String fileName = image.getOriginalFilename();

                if (fileName == null || fileName.isEmpty()) {
                    continue;
                }

                byte[] bytes = image.getBytes();

                // Make sure the directory exists
                String imageDir = "CampusFlea/target/classes/static/uploads/listings/" + id;
                File directory = new File(imageDir);
                if (!directory.exists()) {
                    directory.mkdir();
                }
                // save the image to disk
                Path path = Paths.get(imageDir, fileName);
                Files.write(path, bytes);
            }
        }

        // Update the listing
        ListingService.updateListing(id, title, description, type, price, category);

        // Redirect back to profile
        return "redirect:/profile";
    }
}
