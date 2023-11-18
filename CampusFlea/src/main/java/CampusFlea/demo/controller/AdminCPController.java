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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
        model.addAttribute("isAdmin", user.getIsAdmin());
        model.addAttribute("targetId", userId);

    } catch (Exception e) {
    }
    return "usersettingsAdmin";
    }
    @PostMapping("/edit/{id}")
    public String updateSettings(@RequestParam int targetId, @RequestParam String username, @RequestParam String email, @RequestParam String password,
                                 @RequestParam MultipartFile avatar, @RequestParam(name = "isAdmin", required = false) boolean isAdmin,HttpSession session) throws IOException {
        // Check that the session key is valid (redirect them to login otherwise)
        int userId = SessionService.getUserIdFromSession(session);
        if (userId == -1) {
            return "redirect:/signin";
        }

        // Change the username if needed
        if (!username.isEmpty()) {
            AccountService.updateUsername(targetId, username);
        }

        // Change the email if needed
        if (!email.isEmpty()) {
            AccountService.updateEmail(targetId, email);
        }

        // Change the password if needed
        if (!password.isEmpty()) {
            AccountService.updatePassword(targetId, password);
        }

        // Update the avatar if needed
        if (!avatar.isEmpty()) {
            AccountService.updateAvatar(targetId, avatar);
        }

        // Update admin status
        //AccountService.updateAdminStatus(targetId, isAdmin);
        System.out.println(isAdmin);
        if(isAdmin==true){
            Account user=AccountService.getAccount(userId);
            String query = "UPDATE accounts SET admin = 1 WHERE id = ?;";
            try {

                DatabaseService dbSrv = new DatabaseService();
                Connection conn = dbSrv.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setInt(1, targetId);
                preparedStatement.executeUpdate();

            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
        else{
            String query2 = "UPDATE accounts SET admin = 0 WHERE id = ?;";
            try {

                DatabaseService dbSrv = new DatabaseService();
                Connection conn = dbSrv.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(query2);
                preparedStatement.setInt(1, targetId);
                preparedStatement.executeUpdate();

            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
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
        int createListing=ListingService.createListing(listing.getTitle(),listing.getDescription(),listing.getType(),listing.getPrice(),
                Intcategory,listing.getUid());

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