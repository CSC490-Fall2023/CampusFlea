package CampusFlea.demo.services;

import CampusFlea.demo.model.Account;

import java.sql.*;

public class AccountService {
    public static Account getAccount(int userId) {
// Create a new SQLite connection using the database service
        DatabaseService dbSrv = new DatabaseService();
        Connection conn = dbSrv.getConnection();

        try {
            // Create the query
            String sql = "SELECT * FROM account WHERE id = ?;";

            // Execute the query
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            String username = rs.getString("username");
            String email = rs.getString("email");

            // Return the account
            return new Account(userId, username, email);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
