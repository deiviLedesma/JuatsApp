/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJOs;

import java.util.Date;

/**
 *
 * @author SDavidLedesma
 */
//Clase POJO
public class Message {

    //Atributos
    private String messageId;
    private String text;
    private String imageUrl;
    private User sender;
    private Date timestamp;

    /**
     * Constructor por omision
     */
    public Message() {
    }

    public Message(String text, User sender) {
        this.text = text;
        this.sender = sender;
    }

    /**
     * Constructor con los atributos creados
     *
     * @param messageId
     * @param text
     * @param imageUrl
     * @param sender
     * @param timestamp
     */
    public Message(String messageId, String text, String imageUrl, User sender, Date timestamp) {
        this.messageId = messageId;
        this.text = text;
        this.imageUrl = imageUrl;
        this.sender = sender;
        this.timestamp = timestamp;
    }

    //Getters y setters
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

}
