package CampusFlea.demo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import CampusFlea.demo.websocket.pojo.ResultMessage;


public class MessageUtils {
    public static String getMessage(boolean isSystemMessage, String fromName, Object message) {
        ResultMessage result = new ResultMessage();
        result.setSystem(isSystemMessage);
        result.setMessage(message);
        if (fromName != null) {
            result.setFromName(fromName);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
           
            e.printStackTrace();
            return ""; 
        }
    }
}
