/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.io.Serializable;
import java.util.Date;
import org.bson.Document;

/**
 *
 * @author SDavidLedesma
 */
public class Message implements Serializable  {

    private String messageId;
    private String text;
    private String imageUrl;
    private User sender;
    private Date timestamp;

    public Message(String messageId, String text, String imageUrl, User sender, Date timestamp) {
        this.messageId = messageId;
        this.text = text;
        this.imageUrl = imageUrl;
        this.sender = sender;
        this.timestamp = timestamp;
    }

    public Document toDocument() {
        return new Document("messageId", messageId)
                .append("text", text)
                .append("imageUrl", imageUrl)
                .append("sender", sender.toDocument())
                .append("timestamp", timestamp);
    }

    public static Message fromDocument(Document doc) {
        return new Message(
                doc.getString("messageId"),
                doc.getString("text"),
                doc.getString("imageUrl"),
                User.fromDocument(doc.get("sender", Document.class)),
                doc.getDate("timestamp")
        );
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    private static class User {

        public User() {
        }
    }

}
