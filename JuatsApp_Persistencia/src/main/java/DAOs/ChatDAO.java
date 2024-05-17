/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Interfaces.IChatDAO;
import POJOs.Chat;
import POJOs.Message;
import POJOs.User;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author SDavidLedesma
 */
public class ChatDAO implements IChatDAO {

    //SE CEA LA VARIABLE DONDE GUARDAREMOS LA COLECCION, UNA VEZ CREADA NO CAMBIA
    private final MongoCollection<Chat> coleccionChat;
    private final MongoCollection<Message> coleccionMessage;

    public ChatDAO(MongoCollection<Chat> coleccionChat) {
        this.coleccionChat = Conexion.Conexion.getDatabase().getCollection("chat", Chat.class);
        this.coleccionMessage = Conexion.Conexion.getDatabase().getCollection("messages", Message.class);

    }

    @Override
    public String createChat(String chatName, List<User> participants, String imageURL) {
        Chat nuevoChat = new Chat(chatName, participants, imageURL);
        coleccionChat.insertOne(nuevoChat);
        return nuevoChat.getChatId();
    }

    @Override
    public void addMessage(String chatId, String text, String imageURL, User sender) throws Exception {
        Message nuevoMensaje = new Message(new ObjectId(chatId).toString(), text, imageURL, sender, new Date());
        coleccionMessage.insertOne(nuevoMensaje);
    }

    @Override
    public List<Message> getMessages(String chatId) throws Exception {
        List<Message> mensajes = new ArrayList<>();
        ObjectId objectId = new ObjectId(chatId);
        MongoCursor<Message> cursor = coleccionMessage.find(new Document("chatId", objectId)).iterator();
        while (cursor.hasNext()) {
            mensajes.add(cursor.next());
        }
        return mensajes;
    }

    @Override
    public void editMessage(String messageId, String newText, String imageURL) throws Exception {
        ObjectId objectId = new ObjectId(messageId);
        coleccionMessage.updateOne(
                Filters.eq("_id", objectId),
                Updates.combine(
                        Updates.set("text", newText),
                        Updates.set("imageUrl", imageURL)
                )
        );
    }

    @Override
    public void deleteMessage(String messageId) {
        ObjectId objectId = new ObjectId(messageId);
        coleccionMessage.deleteOne(new Document("_id", objectId));
    }

    @Override
    public List<Chat> getUserChats(User user) {
        List<Chat> chats = new ArrayList<>();
        MongoCursor<Chat> cursor = coleccionChat.find(Filters.eq("participants.phoneNumber", user.getPhoneNumber())).iterator();

        try {
            while (cursor.hasNext()) {
                chats.add(cursor.next());
            }
        } finally {
            cursor.close();
        }

        return chats;
    }

    @Override
    public void editChat(String chatId, String newChatName, String newImageURL) {
        ObjectId objectId = new ObjectId(chatId);
        coleccionChat.updateOne(
                Filters.eq("_id", objectId),
                Updates.combine(
                        Updates.set("chatName", newChatName),
                        Updates.set("thumbnailImageUrl", newImageURL)
                )
        );
    }

    @Override
    public void deleteChat(String chatId) {
        ObjectId objectId = new ObjectId(chatId);

        // Eliminar el chat de la colección de chats
        coleccionChat.deleteOne(Filters.eq("_id", objectId));

        // Eliminar todos los mensajes asociados a este chat
        coleccionMessage.deleteMany(Filters.eq("chatId", objectId));
    }

}
