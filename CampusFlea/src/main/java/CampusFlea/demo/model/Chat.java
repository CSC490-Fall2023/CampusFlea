package CampusFlea.demo.model;

public class Chat {
    private final int id;
    private final int listingId;
    private final ChatMessage[] messages;

    public Chat(int id, int listingId, ChatMessage[] messages) {
        this.id = id;
        this.listingId = listingId;
        this.messages = messages;
    }

    public int getId() {
        return id;
    }

    public int getListingId() {
        return listingId;
    }

    public ChatMessage[] getMessages() {
        return messages;
    }
}
