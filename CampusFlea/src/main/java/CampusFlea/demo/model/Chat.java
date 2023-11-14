package CampusFlea.demo.model;

public class Chat {
    private final int id;
    private final int listingId;
    private final int buyerId;
    private final ChatMessage[] messages;

    public Chat(int id, int listingId, int buyerId, ChatMessage[] messages) {
        this.id = id;
        this.listingId = listingId;
        this.buyerId = buyerId;
        this.messages = messages;
    }

    public int getId() {
        return id;
    }

    public int getListingId() {
        return listingId;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public ChatMessage[] getMessages() {
        return messages;
    }
}
