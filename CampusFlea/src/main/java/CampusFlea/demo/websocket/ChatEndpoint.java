package CampusFlea.demo.websocket;
import com.fasterxml.jackson.databind.ObjectMapper;
import CampusFlea.demo.config.GetHttpSessionConfig;
import CampusFlea.demo.utils.MessageUtils;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.*;
import java.util.Map;
import jakarta.websocket.server.ServerEndpoint;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.lang.Throwable;
import CampusFlea.demo.websocket.pojo.Message;
import com.alibaba.fastjson.JSON;
import CampusFlea.demo.utils.MessageUtils;

@ServerEndpoint(value="/chat",configurator= GetHttpSessionConfig.class)
@Component
public class ChatEndpoint {
    private HttpSession httpSession;
    private static final Map<String,Session> onlineUsers= new ConcurrentHashMap<>();
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        //storage the session
        this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        String user = (String) this.httpSession.getAttribute("username");
        onlineUsers.put(user,session);
        //broadcast the message like someone online or offline
        String message = MessageUtils.getMessage(true,null,getFriends());
        broadcastAllUsers(message);
    }

    public Set getFriends() {
        Set<String> set = onlineUsers.keySet();
        return set;
    }

    private void broadcastAllUsers(String message) {
        try {
            //travel map set
            Set<Map.Entry<String, Session>> entries = onlineUsers.entrySet();
            for (Map.Entry<String, Session> entry : entries) {
                Session session = entry.getValue();
                //sender message
                session.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
        }
    }


    @OnMessage
    public void onMessage(String message) {
        try {
            //send message to users
            Message msg = JSON.parseObject(message, Message.class);
            //get receiver name
            String toName = msg.getToName();
            String mess = msg.getMessage();
            Session session = onlineUsers.get(toName);
            String user = (String) this.httpSession.getAttribute("user");
            //send message

            String msg1 = MessageUtils.getMessage(false, user, mess);
            session.getBasicRemote().sendText(msg1);
            //save message to database

        } catch (Exception e) {

        }
    }


    @OnClose
    public void onClose(Session session) {

        String user = (String) this.httpSession.getAttribute("user");
        onlineUsers.remove(user);
        //broadcast that someone offline
        String message = MessageUtils.getMessage(true,null,getFriends());
        // broadcastAllUsers(message);
    }
    }
