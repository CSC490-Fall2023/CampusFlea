package CampusFlea.demo.services;

import CampusFlea.demo.model.Chat;
import CampusFlea.demo.model.ChatMessage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChatService {
    public static Chat[] getAllAffiliatedChats(int userId) {
        // Create new list of messages
        List<Chat> chats = new ArrayList<>();

        // Establish database connection
        DatabaseService dbSrv = new DatabaseService();
        Connection conn = dbSrv.getConnection();

        // Create the query
        String query = "SELECT DISTINCT chatId FROM messages WHERE senderId = ?;";

        // TODO: Also get received messages

        try {
            // Prepare the query
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, userId);

            // Execute the query
            ResultSet rs = preparedStatement.executeQuery();

            int chatId = rs.getInt("chatId");
            int listingId = rs.getInt("listingId");
            int buyerId = rs.getInt("buyerId");

            // Get all chat messages for the associated chatId
            ChatMessage[] chatMessages = getChatMessages(chatId);

            // Create a new Chat
            Chat chat = new Chat(chatId, listingId, buyerId, chatMessages);
            chats.add(chat);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

        // Return the user messages as an array
        return chats.toArray(new Chat[0]);
    }

    public static ChatMessage[] getChatMessages(int chatId) {
        // Create new list of messages
        List<ChatMessage> userMessages = new ArrayList<>();

        // Establish database connection
        DatabaseService dbSrv = new DatabaseService();
        Connection conn = dbSrv.getConnection();

        // Create the query
        String query = "SELECT id, senderId, timestamp, message FROM messages WHERE chatId = ?;";

        try {
            // Prepare the query
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, chatId);

            // Execute the query
            ResultSet rs = preparedStatement.executeQuery();

            int id = rs.getInt("id");
            int senderId = rs.getInt("senderId");
            int timestamp = rs.getInt("timestamp");
            String message = rs.getString("message");

            // Create the new message object
            ChatMessage thisMessage = new ChatMessage(id, chatId, senderId, timestamp, message);
            userMessages.add(thisMessage);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

        // Return the user messages as an array
        return userMessages.toArray(new ChatMessage[0]);
    }

    public static void saveChatMessage(int toId, int fromId, int listingId, String message) {
        // TODO: Implement this
    }
}
