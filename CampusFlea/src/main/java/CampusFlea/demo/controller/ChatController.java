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
import org.springframework.web.bind.annotation.ResponseBody;

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

        load(model, userId, null);
        return "chat";
    }

    @RequestMapping(value = "/chat", params = "id")
    public String comeFromListing(Model model, @RequestParam String id, HttpSession session) {
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

        // If chat is null, this is a new chat (we must make a new instance)
        if (chat == null) {
            chat = new Chat(-1, listingId, userId, null);
        }

        model.addAttribute("chat", chat);

        load(model, userId, chat);
        return "chat";
    }

    @RequestMapping(value = "/chat", params = {"id", "message"})
    public String sendMessage(Model model, @RequestParam String id, @RequestParam String message, HttpSession session) {
        // Check that the session key is valid (redirect them to login otherwise)
        int userId = SessionService.getUserIdFromSession(session);
        if (userId == -1) {
            return "redirect:/signin";
        }

        // Get the listing using the listingId
        int listingId = Integer.parseInt(id);
        Listing listing = ListingService.getListing(listingId);

        System.out.printf("id=%s\n", id);
        System.out.printf("message=%s\n", message);

        // TODO: Check for duplicate messages

        // Save the chat
        int chatId = ChatService.getChatId(listingId, userId);
        ChatService.saveChatMessage(chatId, listing.getUid(), listingId, message);

        // Add the listing to the model
        model.addAttribute("listing", listing);

        // Add the Chat to the model
        Chat chat = ChatService.getListingChat(listingId, userId);
        model.addAttribute("chat", chat);

        // Save chat in database
        load(model, userId, null);
        return "chat";
    }

    private void load(Model model, int userId, Chat chat) {
        // Add the userId to model
        model.addAttribute("userId", userId);

        // Create the account object from the found userId
        // load items from saved items in DB
        Account user = AccountService.getAccount(userId);
        model.addAttribute("username", user.getUsername());

        // Add the avatar link for loading
        String avatar = AccountService.getProfilePicture(userId);
        model.addAttribute("avatar", avatar);

        // Get all chats relating to user
        Chat[] chats = ChatService.getAllAffiliatedChats(userId);

        // Check if we are loading a chat
        if (chat != null) {
            boolean chatExists = ChatService.chatExists(chats, chat);

            // If doesn't exist, add it
            if (!chatExists) {
                // Create a new array any copy over
                Chat[] newChats = new Chat[chats.length + 1];
                for (int i = 0; i < chats.length; i++) {
                    newChats[i] = chats[i];
                }
                newChats[newChats.length - 1] = chat;
                chats = newChats;
            }
        }
        model.addAttribute("chats", chats);
    }
    @GetMapping("/getUsername")
    @ResponseBody
    public String Chat(Model model, HttpSession session) {
        // get username
        String sessionKey = (String) session.getAttribute("session_key");
        // Check if session key is set
        if (sessionKey == null) {
            System.out.println("Did not find session key");
            return "redirect:/signin";
        }
        System.out.printf("Found session key: %s\n", sessionKey);
        int userId = SessionService.getUserIdFromSession(session);
        Account user = AccountService.getAccount(userId);
        String username = user.getUsername();
        return username;
    }
}
