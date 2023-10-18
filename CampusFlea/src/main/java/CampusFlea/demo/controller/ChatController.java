package CampusFlea.demo.controller;

import CampusFlea.demo.websocket.ChatMessage;
import CampusFlea.demo.websocket.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.userdetails.UserDetails;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private ChatMessageRepository chatMessageRepository;
    @GetMapping("/{sender}/history")
    public List<ChatMessage> getChatHistory(@PathVariable String sender, @AuthenticationPrincipal UserDetails currentUser) throws AccessDeniedException {
       //check username and sender is same or not ,to search chat history
         if (sender.equals(currentUser.getUsername())) {
            return chatMessageRepository.findBySender(sender);
    } else {
           throw new AccessDeniedException("Access denied");
        }
    }
    }


