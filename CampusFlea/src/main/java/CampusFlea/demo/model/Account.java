package CampusFlea.demo.model;

public class Account {

    private int id;

    private String username;

    private String password;

    private String salt;

    private String email;

    private int createdOn;

    private int lastLogin;

    private String bookmarks;

    private boolean isAdmin;

    public Account(int id, String username, String password, String salt, String email, int createdOn, int lastLogin, String bookmarks, boolean isAdmin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.email = email;
        this.createdOn = createdOn;
        this.lastLogin = lastLogin;
        this.bookmarks = bookmarks;
        this.isAdmin = isAdmin;
    }

    public Account() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(int createdOn) {
        this.createdOn = createdOn;
    }

    public int getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(int lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(String bookmarks) {
        this.bookmarks = bookmarks;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
