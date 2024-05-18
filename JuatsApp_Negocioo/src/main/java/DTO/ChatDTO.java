/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.util.List;

/**
 *
 * @author diana
 */
public class ChatDTO {
    private Long chatId;
    private String nombreChat;
    private List<UserDTO> participants;
    private List<MessageDTO> mensajes;
    private String thumbnailImageUrl; 

    public ChatDTO(Long chatId, String nombreChat, List<UserDTO> participants, List<MessageDTO> mensajes, String thumbnailImageUrl) {
        this.chatId = chatId;
        this.nombreChat = nombreChat;
        this.participants = participants;
        this.mensajes = mensajes;
        this.thumbnailImageUrl = thumbnailImageUrl;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getNombreChat() {
        return nombreChat;
    }

    public void setNombreChat(String nombreChat) {
        this.nombreChat = nombreChat;
    }

    public List<UserDTO> getParticipants() {
        return participants;
    }

    public void setParticipants(List<UserDTO> participants) {
        this.participants = participants;
    }

    public List<MessageDTO> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<MessageDTO> mensajes) {
        this.mensajes = mensajes;
    }

    public String getThumbnailImageUrl() {
        return thumbnailImageUrl;
    }

    public void setThumbnailImageUrl(String thumbnailImageUrl) {
        this.thumbnailImageUrl = thumbnailImageUrl;
    }
    
    
}
