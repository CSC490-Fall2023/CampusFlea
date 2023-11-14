package CampusFlea.demo.controller;

import CampusFlea.demo.model.Account;
import CampusFlea.demo.model.Chat;
import CampusFlea.demo.model.Listing;
import CampusFlea.demo.services.AccountService;
import CampusFlea.demo.services.ChatService;
import CampusFlea.demo.services.ListingService;
import CampusFlea.demo.services.SessionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChatController {
    @GetMapping("/chat")
    public String chat(Model model, HttpSession session) {
        // Check that the session key is valid (redirect them to login otherwise)
        int userId = SessionService.getUserIdFromSession(session);
        if (userId == -1) {
            return "redirect:/signin";
        }

        // Load null information to model
        model.addAttribute("listing", null);
        model.addAttribute("chat", null);

        load(model, userId);
        return "chat";
    }

    @RequestMapping(value = "/chat", params = "id")
    public String messageListing(Model model, @RequestParam String id, HttpSession session) {
        // Check that the session key is valid (redirect them to login otherwise)
        int userId = SessionService.getUserIdFromSession(session);
        if (userId == -1) {
            return "redirect:/signin";
        }

        // Get the listing using the listingId
        int listingId = Integer.parseInt(id);
        Listing listing = ListingService.getListing(listingId);

        // Add the listing to the model
        model.addAttribute("listing", listing);

        // Add the Chat to the model
        Chat chat = ChatService.getListingChat(listingId, userId);
        model.addAttribute("chat", chat);

        load(model, userId);
        return "chat";
    }

    private void load(Model model, int userId) {
        // Create the account object from the found userId
        // load items from saved items in DB
        Account user = AccountService.getAccount(userId);
        model.addAttribute("username", user.getUsername());

        // Add the avatar link for loading
        String avatar = AccountService.getProfilePicture(userId);
        model.addAttribute("avatar", avatar);

        // Get all chats relating to user
        Chat[] chats = ChatService.getAllAffiliatedChats(userId);
        model.addAttribute("chats", chats);
    }
}
