/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SDavidLedesma
 */
public class Chat {

    private Long chatId;
    private String nombreChat;
    private List<Usuario> participants;
    private List<Mensaje> mensajes;
    private String thumbnailImageUrl; // URL de la imagen en miniatura del chat

    public Chat() {
    }

    public Chat(Long chatId, String nombreChat, List<Usuario> participants, List<Mensaje> mensajes, String thumbnailImageUrl) {
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

    public List<Usuario> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Usuario> participants) {
        this.participants = participants;
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }

    public String getThumbnailImageUrl() {
        return thumbnailImageUrl;
    }

    public void setThumbnailImageUrl(String thumbnailImageUrl) {
        this.thumbnailImageUrl = thumbnailImageUrl;
    }

}
