package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.Document;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author SDavidLedesma
 */
public class Chat {

    private String chatId;
    private String chatName;
    private List<User> participants;
    private List<Message> messages;
    private String thumbnailImageUrl;

    public Chat(String chatId, String chatName, List<User> participants, String thumbnailImageUrl) {
        this.chatId = chatId;
        this.chatName = chatName;
        this.participants = participants;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.messages = new ArrayList<>();
    }

    public Document toDocument() {
        List<Document> participantsDocs = participants.stream().map(User::toDocument).collect(Collectors.toList());
        List<Document> messagesDocs = messages.stream().map(Message::toDocument).collect(Collectors.toList());

        return new Document("chatId", chatId)
                .append("chatName", chatName)
                .append("participants", participantsDocs)
                .append("messages", messagesDocs)
                .append("thumbnailImageUrl", thumbnailImageUrl);
    }

    public static Chat fromDocument(Document doc) {
        List<User> participants = ((List<Document>) doc.get("participants")).stream().map(User::fromDocument).collect(Collectors.toList());
        List<Message> messages = ((List<Document>) doc.get("messages")).stream().map(Message::fromDocument).collect(Collectors.toList());

        Chat chat = new Chat(
                doc.getString("chatId"),
                doc.getString("chatName"),
                participants,
                doc.getString("thumbnailImageUrl")
        );
        chat.messages = messages;
        return chat;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getThumbnailImageUrl() {
        return thumbnailImageUrl;
    }

    public void setThumbnailImageUrl(String thumbnailImageUrl) {
        this.thumbnailImageUrl = thumbnailImageUrl;
    }

}
