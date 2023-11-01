package CampusFlea.demo.services;

import CampusFlea.demo.model.Listing;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ListingService {
    public static final String[] CATEGORIES = {
            "Clothing",
            "Electronics",
            "Furniture",
            "Vehicles"
    };

    public static final String[] STATUS = {
            "ACTIVE",
            "PENDING",
            "SOLD"
    };

    public static Listing getListing(int listingId) {
        // Create the DB connection
        DatabaseService dbSrv = new DatabaseService();
        Connection conn = dbSrv.getConnection();

        // Formulate and prepare the query
        String sql = "SELECT * FROM listings WHERE id = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, listingId);
            ResultSet rs = preparedStatement.executeQuery();

            String title = rs.getString("title");
            String description = rs.getString("description");
            int type = rs.getInt("type");
            int status = rs.getInt("status");
            int price = rs.getInt("price");
            int want = rs.getInt("want");
            int have = rs.getInt("have");
            int category = rs.getInt("category");

            // Return the listing
            return new Listing(listingId, title, description, type, status, price, want, have, category);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Listing[] getAllUserListings(Connection conn, int userId) {
        try {
            // Create the query
            String query = "SELECT * FROM listings WHERE uid = ?;";

            // Prepare the query
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, userId);

            // Execute the query
            ResultSet rs = preparedStatement.executeQuery();

            // Create a list to hold all listings found
            List<Listing> listings = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                int type = rs.getInt("type");
                int status = rs.getInt("status");
                int price = rs.getInt("price");
                int want = rs.getInt("want");
                int have = rs.getInt("have");
                int category = rs.getInt("category");

                Listing listing = new Listing(id, title, description, type, status, price, want, have, category);
                listings.add(listing);
            }

            // Return the listings as an array
            return listings.toArray(new Listing[0]);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Listing[] getAllListings(Connection conn) {
        try {
            // Create the query
            String sql = "SELECT * FROM listings;";

            // Execute the query
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // Create a list to hold all listings found
            List<Listing> listings = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                int type = rs.getInt("type");
                int status = rs.getInt("status");
                int price = rs.getInt("price");
                int want = rs.getInt("want");
                int have = rs.getInt("have");
                int category = rs.getInt("category");

                Listing listing = new Listing(id, title, description, type, status, price, want, have, category);
                listings.add(listing);
            }

            // Return the listings as an array
            return listings.toArray(new Listing[0]);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void createListing(Connection conn, String title, String description, int price, int category, int uid) {
        // Create the insertion query
        String query = "INSERT INTO listings(uid, title, description, price, category) VALUES(?, ?, ?, ?, ?);";

        // Prepare the query
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, uid);
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, description);
            preparedStatement.setInt(4, price);
            preparedStatement.setInt(5, category);

            // Execute the query
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateListing(Connection conn, int id, String title, String description, int price, int category) {
        // Create the update query
        String query = "UPDATE listings WHERE id = ? SET title = ?, description = ?, price = ?, category = ?";

        // Prepare the query
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, description);
            preparedStatement.setInt(4, price);
            preparedStatement.setInt(5, category);

            // Execute the query
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
