/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.util.Date;

/**
 *
 * @author diana
 */
public class MessageDTO {
    private Long messageId;
    private String texto;
    private String imageUrl; // URL de la imagen adjunta al mensaje
    private UserDTO remitente;
    private Date timestamp;

    //Constructor
    public MessageDTO(Long messageId, String texto, String imageUrl, UserDTO remitente, Date timestamp) {
        this.messageId = messageId;
        this.texto = texto;
        this.imageUrl = imageUrl;
        this.remitente = remitente;
        this.timestamp = timestamp;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public UserDTO getRemitente() {
        return remitente;
    }

    public void setRemitente(UserDTO remitente) {
        this.remitente = remitente;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    
    
}
