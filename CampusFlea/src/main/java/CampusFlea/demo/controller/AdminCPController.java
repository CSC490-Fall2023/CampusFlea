package CampusFlea.demo.controller;

import CampusFlea.demo.model.Account;
import CampusFlea.demo.model.Listing;
import CampusFlea.demo.services.AccountService;
import CampusFlea.demo.services.DatabaseService;
import CampusFlea.demo.services.ListingService;
import CampusFlea.demo.services.SessionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Connection;

@Controller
public class AdminCPController {
    @GetMapping("/admincp")
    public String adminCP(Model model, HttpSession session) {
        // Check that the session key is valid (redirect them to login otherwise)
        int userId = SessionService.getUserIdFromSession(session);
        if (userId == -1) {
            return "redirect:/signin";
        }

        // Make sure the user has admin privileges, otherwise redirect back to home
        Account adminAccount = AccountService.getAccount(userId);
        if (!adminAccount.getIsAdmin()) {
            return "redirect:/";
        }

        // Get all users
        Account[] accounts = AccountService.getAllAccounts();
        model.addAttribute("users", accounts);
        model.addAttribute("getEmail", accounts);
        model.addAttribute("isAdmin", accounts);
        //Get all items
        Listing[] listings = ListingService.getAllListings();
        model.addAttribute("id", listings);
        model.addAttribute("uid", listings);
        model.addAttribute("type", listings);
        model.addAttribute("status", listings);
        model.addAttribute("price", listings);
        return "admincp";


    }

    //Create user
   @GetMapping("/create-admin")
    public String createUser(Account account) {
        // Establish a database connection
        DatabaseService dbSrv = new DatabaseService();
        Connection conn = dbSrv.getConnection();

        boolean result = AccountService.createAccount(conn,account.getUsername(), account.getPassword(), account.getEmail());
        return "redirect:/admincp";
    }
    //edit account username
    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable("id") int userId, Model model) {
    try {
        Account user = AccountService.getAccount(userId);
        String avatar = AccountService.getProfilePicture(userId);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("avatar", avatar);

    } catch (Exception e) {
    }
    return "usersettings";
    }
    @PostMapping("/edit/{id}")
    public String updateSettings(@RequestParam String username, @RequestParam String email, @RequestParam String password, @RequestParam MultipartFile avatar, HttpSession session) throws IOException {
        // Check that the session key is valid (redirect them to login otherwise)
        int userId = SessionService.getUserIdFromSession(session);
        if (userId == -1) {
            return "redirect:/signin";
        }

        // Change the username if needed
        if (!username.isEmpty()) {
            AccountService.updateUsername(userId, username);
        }

        // Change the email if needed
        if (!email.isEmpty()) {
            AccountService.updateEmail(userId, email);
        }

        // Change the password if needed
        if (!password.isEmpty()) {
            AccountService.updatePassword(userId, password);
        }

        // Update the avatar if needed
        if (!avatar.isEmpty()) {
            AccountService.updateAvatar(userId, avatar);
        }
        return "redirect:/admincp";
    }
    //delete user
    @GetMapping("/delete/{id}")
    public  String deleteUser(@PathVariable("id") int userId){
        String query = "DELETE FROM accounts WHERE id = ?;";
        try { DatabaseService dbSrv = new DatabaseService();
            Connection conn = dbSrv.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, userId);

            // Execute the query
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/admincp";
    }
    //create listing
    @PostMapping("/listing")
    public String createList(Listing listing){
        DatabaseService dbSrv = new DatabaseService();
        Connection conn = dbSrv.getConnection();
        //category is int and string
        String category=listing.getCategory();
        try{
            int Intcategory =Integer.parseInt(category);
        int createListing=ListingService.createListing(listing.getTitle(),listing.getDescription(),listing.getPrice(),Intcategory,listing.getUid());

    }catch(NumberFormatException e){
        System.out.println(category+"is not a valid integer number");
    }
        return "redirect:/admincp";
    }
    //delete listing
    @GetMapping("/deletelisting/{id}")
    public String deletelisting(@PathVariable("id") int listId){
        ListingService.deleteListing(listId);
        return "redirect:/admincp";
    }
}