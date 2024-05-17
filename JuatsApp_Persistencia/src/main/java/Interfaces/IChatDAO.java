/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import POJOs.Chat;
import POJOs.Message;
import POJOs.User;
import java.util.List;

/**
 *
 * @author SDavidLedesma
 */
public interface IChatDAO {

    public List<Chat> obtenerChatsDeUsuario(User user);
    
    public List<Message> obteenerMensajesDeChat(Chat chat);
    
    public void addMessage(String chatId, String next, String imgaeURL, User sender) throws Exception;
    /**
     * public String createChat(String chatName, List<User> participants, String
     * imageURL);
     *
     * public List<String> obtenerNombresUsuariosEnChat(String chatId);
     *
     * public void addMessage(String chatId, String text, String imageURL, User
     * sender) throws Exception;
     *
     * public List<Message> getMessages(String chatId) throws Exception;
     *
     * public void editMessage(String messageId, String newText, String
     * imageURL) throws Exception;
     *
     * public void deleteMessage(String messageId);
     *
     * public List<Chat> getUserChats(User user);
     *
     * public void editChat(String chatId, String newChatName, String
     * newImageURL);
     *
     * public void deleteChat (String chatId);
    * *
     */
}
