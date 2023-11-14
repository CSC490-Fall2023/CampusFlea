package CampusFlea.demo.services;

import CampusFlea.demo.model.UserMessage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChatService {
    public static UserMessage[] getAllMessages(int userId) {
        // Create new list of messages
        List<UserMessage> userMessages = new ArrayList<>();

        // Establish database connection
        DatabaseService dbSrv = new DatabaseService();
        Connection conn = dbSrv.getConnection();

        // Create the query
        String query = "SELECT * FROM messages WHERE toId = ? OR fromId = ?;";

        try {
            // Prepare the query
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, userId);

            // Execute the query
            ResultSet rs = preparedStatement.executeQuery();

            int id = rs.getInt("id");
            int listingId = rs.getInt("listingId");
            int timeSent = rs.getInt("timeSent");
            int toId = rs.getInt("toId");
            int fromId = rs.getInt("fromId");
            String message = rs.getString("message");

            // Create the new message object
            UserMessage thisMessage = new UserMessage(id, listingId, timeSent, toId, fromId, message);
            userMessages.add(thisMessage);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

        // Return the user messages as an array
        return userMessages.toArray(new UserMessage[0]);
    }

    public static void saveChatMessage(int toId, int fromId, int listingId, String message) {
        // TODO: Implement this
    }
}
