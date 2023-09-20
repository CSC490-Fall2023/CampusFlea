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


}
