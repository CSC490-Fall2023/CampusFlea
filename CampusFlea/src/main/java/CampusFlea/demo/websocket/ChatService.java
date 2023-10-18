package CampusFlea.demo.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class ChatService {
    @Autowired
    private ChatMessageRepository chatMessageRepository;
    public List<ChatMessage> getMessageBySender(String sender){
        return  chatMessageRepository.findBySender(sender);
    }
    public List<ChatMessage> getMessageByReceiver(String receiver){
        return  chatMessageRepository.findByReceiver(receiver);
    }
    public void saveChatMessage(String sender,String receiver,String message){
        ChatMessage chatMessage=new ChatMessage();
        chatMessage.setSender(sender);
        chatMessage.setReceiver(receiver);
        chatMessage.setTime(new Date(System.currentTimeMillis()));
        chatMessageRepository.save(chatMessage);
    }
}
