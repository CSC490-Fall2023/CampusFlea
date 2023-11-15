package CampusFlea.demo.model;

public class ChatMessage {
    private final int id;
    private final int chatId;
    private final int senderId;
    private final int timestamp;
    private final String message;

    public ChatMessage(int id, int chatId, int senderId, int timestamp, String message) {
        this.id = id;
        this.chatId = chatId;
        this.senderId = senderId;
        this.timestamp = timestamp;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public int getChatId() {
        return chatId;
    }

    public int getSenderId() {
        return senderId;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }
}
