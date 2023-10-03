package CampusFlea.demo.config;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;

public class GetHttpSessionConfig extends ServerEndpointConfig.Configurator{
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        //super.modifyHandshake(sec, request, response);
        //get HttpSession
        HttpSession httpSession=(HttpSession) request.getHttpSession();
        //HttpSession save to ServerEndpointConfig
        sec.getUserProperties().put(HttpSession.class.getName(),httpSession);
    }
}
