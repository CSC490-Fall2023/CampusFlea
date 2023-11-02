package CampusFlea.demo.controller;
import CampusFlea.demo.model.Listing;
import CampusFlea.demo.services.ListingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import CampusFlea.demo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.*;
import CampusFlea.demo.services.AccountService;
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ItemListRepository itemListRepository;

    //Get User
    @GetMapping("/account")

    public String listAccounts(Model model) {
        model.addAttribute("accounts", accountRepository.findAll());
        return "accountList";
    }

    //create user
    @PostMapping("/account")
    public String createUser(Account account, RedirectAttributes redirectAttributes) {
        boolean result = AccountService.createAccount(account.getUsername(), account.getPassword(), account.getEmail());

        return "redirect:/admin/account";
    }

    @GetMapping("/account/{userId}")
    public String getUserById(@PathVariable int userId, Model model) {
        Account account = AccountService.getAccount(userId);
        if (account != null) {
            model.addAttribute("account", account);
            return "accountdetail";//
        } else {
            return "error";
        }
    }

    @PutMapping("/account/{userId}")
    public Account updateaccount(@PathVariable int userId, @RequestBody Account account) {
        if (accountRepository.existsById(userId)) {
            account.setId(userId);
            return accountRepository.save(account);
        }
        return null;
    }

    @DeleteMapping("/account/{userId}")
    public String deleteUser(@PathVariable int userId) {
        if (accountRepository.existsById(userId)) {
            accountRepository.deleteById(userId);

            return "redirect:/admin/account";
        } else {
            return "error";
        }
    }

    //get all items
    public String listItems(Model model) {
        model.addAttribute("listing", itemListRepository.findAll());
        return "itemList";
    }

    //Get item by ID
    @GetMapping("/listing/{listingId}")
    public String getItemById(@PathVariable int Id, Model model) {
        Listing listing = ListingService.getListing(Id);
        if (listing != null) {
            model.addAttribute("listing", listing);
            return "listingdetail";//
        } else {
            return "error";
        }
    }

    //Create item
    @PostMapping("/list")
    public String createItem(Listing listing) {
        itemListRepository.save(listing);
        return "redirect:/admin/listing";
    }

    //Update item
    @PutMapping("/listing/{listingId}")
    public Listing updateItem(@PathVariable int listingId, @RequestBody Listing listing) {
        //if (itemListRepository.existsById(listingId)) {
        //  listing.getId();
        //return itemListRepository.save(listingId);
        //}
        return null;
    }

    @DeleteMapping("/listing/{listingId}")
    public String deleteItem(@PathVariable int listingId) {
        if (itemListRepository.existsById(listingId)) {
            itemListRepository.deleteById(listingId);
            return "redirect:/admin/listing";
        } else {
            return "error";
        }
    }
}
