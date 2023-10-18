package CampusFlea.demo.services;

import CampusFlea.demo.model.Listing;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ListingService {
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
            int want = rs.getInt("want");
            int have = rs.getInt("have");
            int category = rs.getInt("category");

            // Return the listing
            return new Listing(listingId, title, description, type, status, want, have, category);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Listing[] getAllListings() {
        // Create a new SQLite connection using the database service
        DatabaseService dbSrv = new DatabaseService();
        Connection conn = dbSrv.getConnection();

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
                int want = rs.getInt("want");
                int have = rs.getInt("have");
                int category = rs.getInt("category");

                Listing listing = new Listing(id, title, description, type, status, want, have, category);
                listings.add(listing);
            }

            // Return the listings as an array
            return listings.toArray(new Listing[0]);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
