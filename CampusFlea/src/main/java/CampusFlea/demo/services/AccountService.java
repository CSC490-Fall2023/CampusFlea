package CampusFlea.demo.services;

import CampusFlea.demo.model.Account;

import java.nio.charset.Charset;
import java.sql.*;
import java.time.Instant;
import java.util.Random;

public class AccountService {
    public static Account getAccount(int userId) {
        // Create a new SQLite connection using the database service
        DatabaseService dbSrv = new DatabaseService();
        Connection conn = dbSrv.getConnection();

        try {
            // Create the query
            String sql = "SELECT * FROM accounts WHERE id = ?;";

            // Prepare the query
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, userId);

            // Execute the query
            ResultSet rs = preparedStatement.executeQuery();

            String username = rs.getString("username");
            String password = rs.getString("password");
            String salt = rs.getString("salt");
            String email = rs.getString("email");
            int createdOn = rs.getInt("created_time");
            int lastLogin = rs.getInt("last_login");
            String bookmarks = rs.getString("bookmarks");
            boolean isAdmin = rs.getBoolean("admin");

            // Return the account
            return new Account(userId, username, password, salt, email, createdOn, lastLogin, bookmarks, isAdmin);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static boolean createAccount(String username, String password, String email) {
        // Create a new SQLite connection using the database service
        DatabaseService dbSrv = new DatabaseService();
        Connection conn = dbSrv.getConnection();

        // TODO - Gene: We must determine if the credentials are valid before creating the account

        try {
            // Create the secret salt
            String salt = generateSalt();

            // Get the created time
            int timeNow = (int)(Instant.now().getEpochSecond() / 1000);

            String sql = "INSERT INTO accounts(username, password, salt, email, created_time, last_login) VALUES(?, ?, ?, ?, ?, ?)";

            // Prepare the query
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, salt);
            preparedStatement.setInt(5, timeNow);
            preparedStatement.setInt(6, timeNow);

            // Execute the query
            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    private static String generateSalt() {
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        return generatedString;
    }
}
