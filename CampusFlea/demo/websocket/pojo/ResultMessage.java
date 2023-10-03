package CampusFlea.demo.websocket.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//User send message
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultMessage {
    private boolean isSystem;//judge is system message or not, if it is return true
    private String fromName;//If system message is null, or send users' name to user
    private Object message;//if get system message return array
}
