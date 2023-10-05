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
    public void onOpen(Session session,EndpointConfig config){
        this.httpSession=(HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        String user=(String) this.httpSession.getAttribute("user");
        //save username
        onlineUsers.put(user,session);
        //send system message
       String message= MessageUtils.getMessage(true,null,getFriends());
       Broadcast(message);
    }
    //return online friends
    private Set<String> getFriends(){

        return onlineUsers.keySet();
    }

    private void Broadcast(String message) {
        try {
            //Traversal map
            Set<Map.Entry<String,Session>> entries= onlineUsers.entrySet();

            for (Map.Entry<String,Session>entry:entries) {
                Session session=entry.getValue();
                session.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Browser send message to service
     * @param message
     */

    @OnMessage
    public void onMessage(String message){
    //send message to target users
        //JSON transfer java object(message)
        try {
            Message msg = JSON.parseObject(message, Message.class);
            //get receiver name
            String toName = msg.getToName();
            String mes = msg.getMessage();
            Session session = onlineUsers.get(toName);
            String user = (String) this.httpSession.getAttribute("user");
            String sendmessage = MessageUtils.getMessage(false, user, mes);
            session.getBasicRemote().sendText(sendmessage);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * close websocket
     * @param session
     */
    @OnClose
    public void onClose(Session session) {
        // remove users' session from onlineUsers
        String user = (String) this.httpSession.getAttribute("user");
        onlineUsers.remove(user);
    }
    }
