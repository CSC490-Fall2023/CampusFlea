package CampusFlea.demo.model;

public class Listing {
    private final int id;
    private final String title;
    private final String description;
    private final int type;
    private final int status;
    private final int price;
    private final int want;
    private final int have;
    private final int category;

    public Listing(int id, String title, String description, int type, int status, int price, int want, int have, int category) {
        this.id = id;
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

    public int getCategory() {
        return category;
    }
}
