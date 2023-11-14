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
        String query = "SELECT * FROM chats WHERE buyerId = ?;";
        // TODO: Also get received messages

        try {
            // Prepare the query
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, userId);

            // Execute the query
            ResultSet rs = preparedStatement.executeQuery();

            // Get row data
            int id = rs.getInt("id");
            int listingId = rs.getInt("listingId");
            int buyerId = rs.getInt("buyerId");

            // Get all chat messages for the associated chatId
            ChatMessage[] chatMessages = getChatMessages(id);

            // Create a new Chat
            Chat chat = new Chat(id, listingId, buyerId, chatMessages);
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

    public static Chat getListingChat(int listingId, int userId) {
        // Establish database connection
        DatabaseService dbSrv = new DatabaseService();
        Connection conn = dbSrv.getConnection();

        // Create the query
        String query = "SELECT id, buyerId FROM chats WHERE listingId = ? AND buyerId = ?;";

        try {
            // Prepare the query
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, listingId);

            // Execute the query
            ResultSet rs = preparedStatement.executeQuery();

            int id = rs.getInt("id");
            int buyerId = rs.getInt("buyerId");

            ChatMessage[] chatMessages = getChatMessages(id);

            // Create Chat object
            Chat chat = new Chat(id, listingId, buyerId, chatMessages);
            return chat;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void saveChatMessage(int toId, int fromId, int listingId, String message) {
        // TODO: Implement this
    }
}
