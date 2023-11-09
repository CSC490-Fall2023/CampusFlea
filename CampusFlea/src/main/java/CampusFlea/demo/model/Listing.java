package CampusFlea.demo.model;

public class Listing {
    private final int id;
    private final int uid;
    private final String title;
    private final String description;
    private final int type;
    private final int status;
    private final int price;
    private final int want;
    private final int have;
    private final String category;

    private String image;
    private boolean saved;

    public Listing(int id, int uid, String title, String description, int type, int status, int price, int want, int have, String category) {
        this.id = id;
        this.uid = uid;
        this.title = title;
        this.description = description;
        this.type = type;
        this.status = status;
        this.price = price;
        this.want = want;
        this.have = have;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public int getUid() {
        return uid;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getType() {
        return type;
    }

    public int getStatus() {
        return status;
    }

    public int getPrice() {
        return price;
    }

    public int getWant() {
        return want;
    }

    public int getHave() {
        return have;
    }

    public String getCategory() {
        return category;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setSaved(boolean saved) { this.saved = saved; }

    public boolean getSaved() { return saved; };

    public String toString() {
        return id + ", " + title + ", " + description;
    }
}
