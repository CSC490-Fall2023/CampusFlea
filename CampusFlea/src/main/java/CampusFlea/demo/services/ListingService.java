package CampusFlea.demo.services;

import CampusFlea.demo.model.Listing;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListingService {
    public static final String[] CATEGORIES = {
            "Clothing",
            "Electronics",
            "Furniture",
            "Jewelry",
            "Vehicles"
    };

    public static final String[] STATUS = {
            "ACTIVE",
            "PENDING",
            "SOLD"
    };

    public static String DEFAULT_IMAGE = "images/List Items/item.jpg";

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

            int uid = rs.getInt("uid");
            String title = rs.getString("title");
            String description = rs.getString("description");
            int type = rs.getInt("type");
            int status = rs.getInt("status");
            int price = rs.getInt("price");
            int want = rs.getInt("want");
            int have = rs.getInt("have");
            String category = CATEGORIES[rs.getInt("category")];

            // Return the listing
            return new Listing(listingId, uid, title, description, type, status, price, want, have, category);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Listing[] getAllUserListings(int userId) {
        // Establish database connection
        DatabaseService dbsrv = new DatabaseService();
        Connection conn = dbsrv.getConnection();

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
                int uid = rs.getInt("uid");
                String title = rs.getString("title");
                String description = rs.getString("description");
                int type = rs.getInt("type");
                int status = rs.getInt("status");
                int price = rs.getInt("price");
                int want = rs.getInt("want");
                int have = rs.getInt("have");
                String category = CATEGORIES[rs.getInt("category")];

                Listing listing = new Listing(id, uid, title, description, type, status, price, want, have, category);
                listings.add(listing);
            }

            // Return the listings as an array
            return listings.toArray(new Listing[0]);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Listing[] getAllListings() {
        // Establish database connection
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
                int uid = rs.getInt("uid");
                String title = rs.getString("title");
                String description = rs.getString("description");
                int type = rs.getInt("type");
                int status = rs.getInt("status");
                int price = rs.getInt("price");
                int want = rs.getInt("want");
                int have = rs.getInt("have");
                String category = CATEGORIES[rs.getInt("category")];

                Listing listing = new Listing(id, uid, title, description, type, status, price, want, have, category);
                listings.add(listing);
            }

            // Return the listings as an array
            return listings.toArray(new Listing[0]);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static int createListing(String title, String description, int type, int price, int category, int uid) {
        // Establish database connection
        DatabaseService dbSrv = new DatabaseService();
        Connection conn = dbSrv.getConnection();

        // Create the insertion query
        String query = "INSERT INTO listings(uid, title, description, type, price, category) VALUES(?, ?, ?, ?, ?, ?);";

        // Prepare the query
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, uid);
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, description);
            preparedStatement.setInt(4, type);
            preparedStatement.setInt(5, price);
            preparedStatement.setInt(6, category);

            // Execute the query
            preparedStatement.executeUpdate();

            // Get the new listing ID
            query = "SELECT id FROM listings WHERE uid = ? AND title = ? AND description = ? AND TYPE = ? AND price = ? AND category = ?;";

            // Prepare the statement
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, uid);
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, description);
            preparedStatement.setInt(4, type);
            preparedStatement.setInt(5, price);
            preparedStatement.setInt(6, category);

            // Execute the query
            ResultSet rs = preparedStatement.executeQuery();
            int id = rs.getInt("id");
            return id;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public static boolean listingIsSaved(int userId, int listingId) {
        String[] listingIds = getSavedListingIds(userId);
        for (String thisListingId : listingIds) {
            // Ignore empty ids
            if (thisListingId.isEmpty()) {
                continue;
            }

            int value = Integer.parseInt(thisListingId);
            if (listingId == value) {
                return true;
            }
        }
        return false;
    }

    private static String[] getSavedListingIds(int userId) {
        // Establish database connection
        DatabaseService dbSrv = new DatabaseService();
        Connection conn = dbSrv.getConnection();

        try {
            // Create the query
            String query = "SELECT saved FROM accounts WHERE id = ?;";

            // Prepare the query
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, userId);

            // Execute the query
            ResultSet rs = preparedStatement.executeQuery();

            // Get the saved string
            String savedIdsString = rs.getString(1);

            // Convert the string to an array
            String[] savedIds = savedIdsString != null ? savedIdsString.split(",") : new String[0];

            // Return the saved ids
            return savedIds;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new String[0];
        }
    }

    public static Listing[] getSavedListings(int userId) {
        // Establish database connection
        DatabaseService dbSrv = new DatabaseService();
        Connection conn = dbSrv.getConnection();

        // Get the current saved listing ids
        String[] savedListingIds = getSavedListingIds(userId);

        // Create a list to hold all listings found
        List<Listing> listings = new ArrayList<>();

        // Loop through each id and get listing information
        for (String listingId : savedListingIds) {
            // Skip non-existent listing ids
            if (listingId.isEmpty()) {
                continue;
            }

            String query = "SELECT * FROM listings WHERE id = ?;";

            try {
                // Prepare the query
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setInt(1, Integer.parseInt(listingId));

                // Execute the query
                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("id");
                    int uid = rs.getInt("uid");
                    String title = rs.getString("title");
                    String description = rs.getString("description");
                    int type = rs.getInt("type");
                    int status = rs.getInt("status");
                    int price = rs.getInt("price");
                    int want = rs.getInt("want");
                    int have = rs.getInt("have");
                    String category = CATEGORIES[rs.getInt("category")];

                    // Compose the listing and add it to the listings list
                    Listing listing = new Listing(id, uid, title, description, type, status, price, want, have, category);
                    listings.add(listing);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }
        // Return the listings as an array
        return listings.toArray(new Listing[0]);
    }

    public static String[] getListingImages(int listingId) {
        String imageDir = "CampusFlea/target/classes/static/uploads/listings/" + listingId;
        File directory = new File(imageDir);
        File[] images = directory.listFiles();

        if (images == null) {
            return new String[]{DEFAULT_IMAGE};
        }

        String[] imageNames = new String[images.length];

        for (int i = 0; i < imageNames.length; i++) {
            imageNames[i] = "uploads/listings/" + listingId + "/" + images[i].getName();
        }
        return imageNames;
    }

    public static void toggleSave(int userId, String listingId) {
        // Check if item is saved
        boolean isSaved = listingIsSaved(userId, listingId);

        if (isSaved) {
            // Remove from save
            unsaveListing(userId, listingId);
        } else {
            // Add to save
            saveListing(userId, listingId);
        }
    }

    private static boolean listingIsSaved(int userId, String listingId) {
        // Establish database connection
        DatabaseService dbSrv = new DatabaseService();
        Connection conn = dbSrv.getConnection();

        String[] savedListings = getSavedListingIds(userId);

        for (String listing : savedListings) {
            if (listing.equals(listingId)) {
                return true;
            }
        }
        return false;
    }

    private static void unsaveListing(int userId, String listingId) {
        // Establish database connection
        DatabaseService dbSrv = new DatabaseService();
        Connection conn = dbSrv.getConnection();

        // Get the user's existing saved listings
        String[] savedListings = getSavedListingIds(userId);

        // Convert to a list in order to add new saved listing id
        List<String> savedListingsList = new ArrayList<>(Arrays.asList(savedListings));

        // Add listing id to existing list
        savedListingsList.remove(listingId);

        // Convert the list to csv
        String savedListingsCSV = String.join(",", savedListingsList);

        // Save new string to the database
        // Create the update query
        String query = "UPDATE accounts SET saved = ? WHERE id = ?;";

        // Prepare the query
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, savedListingsCSV);
            preparedStatement.setInt(2, userId);

            // Execute the query
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void saveListing(int userId, String listingId) {
        // Establish database connection
        DatabaseService dbSrv = new DatabaseService();
        Connection conn = dbSrv.getConnection();

        // Get the user's existing saved listings
        String[] savedListings = getSavedListingIds(userId);

        // Convert to a list in order to add new saved listing id
        List<String> savedListingsList = new ArrayList<>(Arrays.asList(savedListings));

        // Add listing id to existing list
        savedListingsList.add(listingId);

        // Convert the list to csv
        String savedListingsCSV = String.join(",", savedListingsList);

        // Save new string to the database
        // Create the update query
        String query = "UPDATE accounts SET saved = ? WHERE id = ?;";

        // Prepare the query
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, savedListingsCSV);
            preparedStatement.setInt(2, userId);

            // Execute the query
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateListing(int id, String title, String description, int type, int price, int category) {
        // Establish database connection
        DatabaseService dbSrv = new DatabaseService();
        Connection conn = dbSrv.getConnection();

        // Create the update query
        String query = "UPDATE listings SET title = ?, description = ?, type = ?, price = ?, category = ? WHERE id = ?;";

        // Prepare the query
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setInt(3, type);
            preparedStatement.setInt(4, price);
            preparedStatement.setInt(5, category);
            preparedStatement.setInt(6, id);

            // Execute the query
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteListing(int listingId) {
        // Establish database connection
        DatabaseService dbSrv = new DatabaseService();
        Connection conn = dbSrv.getConnection();

        // Create the delete query
        String query = "DELETE FROM listings WHERE id = ?;";

        // Prepare the query
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, listingId);

            // Execute the query
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int getSellerId(int listingId) {
        // Establish database connection
        DatabaseService dbSrv = new DatabaseService();
        Connection conn = dbSrv.getConnection();

        String query = "SELECT uid FROM listings WHERE id = ?;";

        try {
            // Prepare the query
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, listingId);

            // Execute the query
            ResultSet rs = preparedStatement.executeQuery();

            // return if exists
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
