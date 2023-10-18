package CampusFlea.demo.websocket.pojo;
import lombok.Data;

@Data
public class ResultMessage {

    private boolean isSystem;
    private String fromName;
    private Object message;
}