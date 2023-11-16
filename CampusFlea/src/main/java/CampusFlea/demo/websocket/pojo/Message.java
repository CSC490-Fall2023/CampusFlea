package CampusFlea.demo.websocket.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//User send message
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String toName;
    private String Receiver;
    private String message;
}
