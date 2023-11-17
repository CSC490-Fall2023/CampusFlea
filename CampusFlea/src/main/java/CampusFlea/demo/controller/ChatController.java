package CampusFlea.demo.controller;

import CampusFlea.demo.model.Account;
import CampusFlea.demo.model.Chat;
import CampusFlea.demo.model.Listing;
import CampusFlea.demo.services.*;
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

        load(model, userId, null);
        return "chat";
    }

    @RequestMapping(value = "/chat", params = "lid")
    public String comeFromListing(Model model, @RequestParam String lid, HttpSession session) {
        // Check that the session key is valid (redirect them to login otherwise)
        int userId = SessionService.getUserIdFromSession(session);
        if (userId == -1) {
            return "redirect:/signin";
        }

        // Get the listing using the listingId
        int listingId = Integer.parseInt(lid);
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

    @RequestMapping(value = "/chat", params = "id")
    public String chatClicked(Model model, @RequestParam String id, HttpSession session) {
        // Check that the session key is valid (redirect them to login otherwise)
        int userId = SessionService.getUserIdFromSession(session);
        if (userId == -1) {
            return "redirect:/signin";
        }

        // Attach the listing model using the chat id
        int chatId = Integer.parseInt(id);
        Chat chat = ChatService.getChat(chatId);

        // Add the listing to the model
        Listing listing = ListingService.getListing(chat.getListingId());
        model.addAttribute("listing", listing);

        model.addAttribute("chat", chat);

        load(model, userId, chat);
        return "chat";
    }

    @RequestMapping(value = "/chat", params = {"lid", "message"})
    public String sendMessage(Model model, @RequestParam String lid, @RequestParam String message, HttpSession session) {
        // Check that the session key is valid (redirect them to login otherwise)
        int userId = SessionService.getUserIdFromSession(session);
        if (userId == -1) {
            return "redirect:/signin";
        }

        // Get the listing using the listingId
        int listingId = Integer.parseInt(lid);
        Listing listing = ListingService.getListing(listingId);

        // TODO: Check for duplicate messages

        // Save the chat
        int chatId = ChatService.getChatId(listingId, userId);
        ChatService.saveChatMessage(chatId, userId, message);

        // Add the listing to the model
        model.addAttribute("listing", listing);

        // Add the Chat to the model
        Chat chat = ChatService.getListingChat(listingId, userId);
        model.addAttribute("chat", chat);

        // Send notification to receiver
        int toId = ChatService.getOtherUserIdInChat(listing.getId(), chat.getBuyerId(), userId);
        NotificationService.sendChatNotification(userId, toId, message);

        // Save chat in database
        load(model, userId, null);
        return "chat";
    }

    @RequestMapping(value = "/chat", params = {"id", "message"})
    public String sendReply(Model model, @RequestParam String id, @RequestParam String message, HttpSession session) {
        // Check that the session key is valid (redirect them to login otherwise)
        int userId = SessionService.getUserIdFromSession(session);
        if (userId == -1) {
            return "redirect:/signin";
        }

        // Get the chatId
        int chatId = Integer.parseInt(id);

        // Save the chat message
        ChatService.saveChatMessage(chatId, userId, message);

        // Get the latest chat info and add it to the model
        Chat chat = ChatService.getChat(chatId);
        model.addAttribute("chat", chat);

        // Add the listing to the model
        Listing listing = ListingService.getListing(chat.getListingId());
        model.addAttribute("listing", listing);

        // Get the other user's id.
        int toId = ChatService.getOtherUserIdInChat(listing.getId(), chat.getBuyerId(), userId);

        // Send notification to receiver
        NotificationService.sendChatNotification(userId, toId, message);

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

        // Add profile pictures to all chats
        for (Chat thisChat : chats) {
            // Get the user's profile picture
            int otherUserId = ChatService.getOtherUserIdInChat(thisChat.getListingId(), thisChat.getBuyerId(), userId);
            String profilePicture = AccountService.getProfilePicture(otherUserId);

            // Set it
            thisChat.setOtherProfilePicture(profilePicture);
        }

        model.addAttribute("chats", chats);

        // Set the header information if we have an active chat
        if (chat != null) {
            Listing listing = ListingService.getListing(chat.getListingId());
            int otherId = ChatService.getOtherUserIdInChat(chat.getListingId(), chat.getBuyerId(), userId);
            String otherUsername = AccountService.getUsername(otherId);
            model.addAttribute("chatUsername", otherUsername);
            model.addAttribute("chatItemName", listing.getTitle());
            model.addAttribute("chatPrice", listing.getPrice());
        }
    }
}
