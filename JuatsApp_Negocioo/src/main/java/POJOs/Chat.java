/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJOs;

import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;


/**
 *
 * @author SDavidLedesma
 */
//Clase POJO
public class Chat {

    //Atributos
    private String chatId;
    private String chatName;
    private List<User> participants;
    private List<Message> messages;
    private String thumbnailImageUrl;

    /**
     * Constructor por omision
     */
    public Chat() {
    }

    /**
     * Constructor con los atributos creados
     *
     * @param chatId
     * @param chatName
     * @param participants
     * @param thumbnailImageUrl
     */
    public Chat(String chatId, String chatName, List<User> participants, String thumbnailImageUrl) {
        this.chatId = chatId;
        this.chatName = chatName;
        this.participants = participants;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.messages = new ArrayList<>();
    }

    public Chat(String chatName, List<User> participants, String thumbnailImageUrl) {
        this.chatName = chatName;
        this.participants = participants;
        this.thumbnailImageUrl = thumbnailImageUrl;
    }

    public Chat(String chatId, List<User> participants) {
        this.chatId = chatId;
        this.participants = participants;
    }

    //Getters y setters
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

    // Método para agregar participantes
    public void addParticipant(User participant) {
        participants.add(participant);
    }

    // Método para agregar un mensaje al chat
    public void addMessage(Message message) {
        messages.add(message);
    }

    // Método para generar un ID de chat
    public static String generateChatId() {
        return new ObjectId().toString();
    }

}
