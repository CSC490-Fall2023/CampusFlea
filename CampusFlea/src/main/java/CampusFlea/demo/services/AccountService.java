package CampusFlea.demo.services;

import CampusFlea.demo.model.Account;

import java.security.MessageDigest;
import java.sql.*;
import java.time.Instant;

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

            // Encrypt the password
            String encryptedPassword = encryptPassword(password, salt);

            // Get the created time
            int timeNow = (int)(Instant.now().getEpochSecond() / 1000);

            String sql = "INSERT INTO accounts(username, password, salt, email, created_time, last_login) VALUES(?, ?, ?, ?, ?, ?)";

            // Prepare the query
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, encryptedPassword);
            preparedStatement.setString(3, salt);
            preparedStatement.setString(4, email);
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
        String salt = "";

        // Generate a 7-length String
        for (int i = 0; i < 7; i++) {
            // Generate random ASCII 33 to 126
            int ascii = (int)(33 + (Math.random() * 93));
            System.out.printf("ascii=%d\n", ascii);
            salt += String.valueOf((char)ascii);
        }
        return salt;
    }

    private static String encryptPassword(String password, String salt) {
        // Hash the password
        String passwordMD5 = getMD5(password);

        // Hash the salt
        String saltMD5 = getMD5(salt);

        // Hash both combined for further security
        return getMD5(passwordMD5 + saltMD5);
    }

    private static String getMD5(String input) {
        try {
            // Create the hash
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(input.getBytes());

            // Convert byte array to String
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean isValidCredentials(String username, String password) {
        // Get the encrypted password and salt
        String sql = "SELECT password, salt FROM accounts WHERE username = ?;";

        // Create the connection
        DatabaseService dbSrv = new DatabaseService();
        Connection conn = dbSrv.getConnection();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);

            // Execute the query
            ResultSet rs = preparedStatement.executeQuery();

            // Get the password and salt from query results
            String encryptedPassword = rs.getString("password");
            String salt = rs.getString("salt");

            // Encrypt the user's entered password
            String userEncryptedPassword = encryptPassword(password, salt);
            return userEncryptedPassword.equals(encryptedPassword);
        } catch (SQLException e) {
            return false;
        }
    }

    public static int getId(String username) {
        // Create the query string
        String sql = "SELECT id FROM accounts WHERE username = ?;";

        // Create the connection
        DatabaseService dbSrv = new DatabaseService();
        Connection conn = dbSrv.getConnection();

        // Prepare the query
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);

            // Execute the query
            ResultSet rs = preparedStatement.executeQuery();
            return rs.getInt("id");
        } catch (SQLException e) {
            return -1;
        }
    }
}
