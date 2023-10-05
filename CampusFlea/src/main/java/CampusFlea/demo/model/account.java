package CampusFlea.demo.model;


import java.io.Serializable;
import java.time.Instant;

//@Table(name = "account")
public class account implements Serializable {

    private Long id;

    private String username;

    private String password; //<- security issue

    private String email;

    private Instant createdOn;

    private Instant lastLogin;

    private String bookmarks; //<- don't know about this datatype

    private boolean isAdmin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Instant getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Instant lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(String bookmarks) {
        this.bookmarks = bookmarks;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
