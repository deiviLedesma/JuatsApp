/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;

/**
 *
 * @author SDavidLedesma
 */
public class Mensaje {

    private Long messageId;
    private String texto;
    private String imageUrl; // URL de la imagen adjunta al mensaje
    private Usuario remitente;
    private Date timestamp;

    public Mensaje() {
    }

    public Mensaje(Long messageId, String texto, String imageUrl, Usuario remitente, Date timestamp) {
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

    public Usuario getRemitente() {
        return remitente;
    }

    public void setRemitente(Usuario remitente) {
        this.remitente = remitente;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    
    
}
