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
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author SDavidLedesma
 */
public class ChatDAO implements IChatDAO {

    //SE CREA LA VARIABLE DONDE GUARDAREMOS LA COLECCION, UNA VEZ CREADA NO CAMBIA
    private final MongoCollection<Chat> coleccionChat;
    private final MongoCollection<Message> coleccionMessage;

    public ChatDAO(MongoCollection<Chat> coleccionChat) {
        this.coleccionChat = Conexion.Conexion.getDatabase().getCollection("chat", Chat.class);
        this.coleccionMessage = Conexion.Conexion.getDatabase().getCollection("messages", Message.class);

    }

    @Override
    public List<Chat> obtenerChatsDeUsuario(User user) {
        List<Chat> chats = new ArrayList<>();
        // Aquí debes escribir la lógica para obtener los chats del usuario desde tu base de datos
        // Por ejemplo:
        MongoCursor<Chat> cursor = coleccionChat.find(Filters.eq("usersanme", user)).iterator();
        while (cursor.hasNext()) {
            chats.add(cursor.next());
        }
        return chats;
    }

    @Override
    public List<Message> obteenerMensajesDeChat(Chat chat) {
        List<Message> mensajes = new ArrayList<>();
        try (MongoCursor<Message> cursor = coleccionMessage.find(eq("chatId", chat.getChatId())).iterator()) {
            while (cursor.hasNext()) {
                mensajes.add(cursor.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Manejar la excepción apropiadamente
        }
        return mensajes;
    }

    @Override
    public void addMessage(String chatId, String text, String imageURL, User sender) throws Exception {
        Message nuevoMensaje = new Message(new ObjectId(chatId).toString(), text, imageURL, sender, new Date());
        coleccionMessage.insertOne(nuevoMensaje);
    }

}
