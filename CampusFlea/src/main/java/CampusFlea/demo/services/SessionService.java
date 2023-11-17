package CampusFlea.demo.services;

import jakarta.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionService {
    public static int getUserIdFromSession(HttpSession session) {
        // Get the user's session key
        String sessionKey = (String) session.getAttribute("session_key");

        // Check if session key is set
        if (sessionKey == null) {
            System.out.println("Did not find session key");
            return -1;
        }

        // Get the user id based on the session key
        int userId = getUserIdFromSessionKey(sessionKey);

        // Return the userId
        return userId;
    }

    private static int getUserIdFromSessionKey(String sessionKey) {
        // Establish database connection
        DatabaseService dbSrv = new DatabaseService();
        Connection conn = dbSrv.getConnection();

        // Create the query
        String sql = "SELECT uid FROM login_sessions WHERE key = ?;";

        // Prepare the query
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, sessionKey);

            // Execute the query
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                int uid = rs.getInt("uid");
                return uid;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }
}
