package CampusFlea.demo.controller;

import CampusFlea.demo.model.Listing;
import CampusFlea.demo.services.DatabaseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import CampusFlea.demo.model.account;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomePageController {

    private String username;
    account user = new account();

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());

        Listing[] listings = getListings();

        for (Listing listing : listings) {
            System.out.printf("Showing listing (id=%d, title=%s)\n", listing.getId(), listing.getTitle());

            // TODO: Show each listing in the UI
        }

        return "home";
    }

    private Listing[] getListings() {
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
