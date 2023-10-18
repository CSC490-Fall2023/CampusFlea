package CampusFlea.demo.websocket;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Date;

public interface ChatMessageRepository extends CrudRepository<ChatMessage,Long> {
    List<ChatMessage> findBySender(String sender);
    List<ChatMessage> findByReceiver(String receiver);
}
