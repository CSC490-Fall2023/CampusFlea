package CampusFlea.demo.model;

public class UserMessage {
    private final int id;
    private final int listingId;
    private final int timeSent;
    private final int toId;
    private final int fromId;
    private final String message;

    public UserMessage(int id, int listingId, int timeSent, int toId, int fromId, String message) {
        this.id = id;
        this.listingId = listingId;
        this.timeSent = timeSent;
        this.toId = toId;
        this.fromId = fromId;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public int getListingId() {
        return listingId;
    }

    public int getTimeSent() {
        return timeSent;
    }

    public int getToId() {
        return toId;
    }

    public int getFromId() {
        return fromId;
    }

    public String getMessage() {
        return message;
    }
}
