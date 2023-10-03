package CampusFlea.demo.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseService {
    private static final String sqliteFile = "jdbc:sqlite:sql/campusflea.db";
    private Connection conn;

    public DatabaseService() {
        try {
            conn = DriverManager.getConnection(sqliteFile);
            System.out.println("SQLite connection has been established");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Connection getConnection() {
        return conn;
    }
}
