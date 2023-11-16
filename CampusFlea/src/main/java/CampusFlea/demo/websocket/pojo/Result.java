package CampusFlea.demo.websocket.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//login and return information to web
public class Result {
    private boolean flag;//login success or not
    private String message;//login result
}
