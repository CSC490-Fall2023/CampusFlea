package CampusFlea.demo.controller;

import CampusFlea.demo.model.Counter;
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

@Controller
public class CreateListingController {
    @GetMapping("/createlisting")
    public String createListing(Model model, HttpSession session) {
        // Check that the session key is valid (redirect them to login otherwise)
        int userId = SessionService.getUserIdFromSession(session);
        if (userId == -1) {
            return "redirect:/signin";
        }

        // Attach counter
        model.addAttribute("counter", new Counter());

        // Attach categories
        model.addAttribute("categories", ListingService.CATEGORIES);

        return "createlisting";
    }

    @PostMapping("/createlisting")
    public String processCreateListing(@RequestParam String title, @RequestParam int category, @RequestParam int price, @RequestParam String description, @RequestParam MultipartFile[] images, HttpSession session) throws IOException {
        // Check that the session key is valid (redirect them to login otherwise)
        int userId = SessionService.getUserIdFromSession(session);
        if (userId == -1) {
            return "redirect:/signin";
        }

        // Create the new listing
        int listingId = ListingService.createListing(title, description, price, category, userId);

        if (listingId == -1) {
            System.out.println("Invalid listingId.");
            return "redirect:/";
        }

        for (MultipartFile image : images) {
            // Download the file
            String fileName = image.getOriginalFilename();
            byte[] bytes = image.getBytes();

            // Make sure the directory exists
            String imageDir = "CampusFlea/target/classes/static/uploads/listings/" + listingId;
            File directory = new File(imageDir);
            if (!directory.exists()) {
                directory.mkdir();
            }
            // save the image to disk
            Path path = Paths.get(imageDir, fileName);
            Files.write(path, bytes);
        }

        // TODO: Add visual confirmation

        // Redirect back to home
        return "redirect:/";
    }
}
