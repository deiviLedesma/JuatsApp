/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import Model.Chat;
import Model.Mensaje;
import Model.Usuario;
import Seguridad.EncryptionUtils;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoClients;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatDAO {

    private MongoDatabase database;
    private MongoCollection<Document> chatCollection;
    private MongoCollection<Document> messageCollection;

    public ChatDAO() {
        // Inicializar la conexión con MongoDB
        var mongoClient = MongoClients.create("mongodb://localhost:27017");
        this.database = mongoClient.getDatabase("juatsapp");
        this.chatCollection = database.getCollection("chats");
        this.messageCollection = database.getCollection("messages");
    }

    // Crear un nuevo chat
    public String createChat(String chatName, List<Usuario> participants, String thumbnailImageUrl) {
        List<Document> participantDocs = new ArrayList<>();
        for (Usuario user : participants) {
            participantDocs.add(new Document("phoneNumber", user.getNumero())
                    .append("username", user.getUsuario()));
        }

        Document chatDoc = new Document("chatName", chatName)
                .append("participants", participantDocs)
                .append("thumbnailImageUrl", thumbnailImageUrl);

        chatCollection.insertOne(chatDoc);
        return chatDoc.getObjectId("_id").toString();
    }

    // Agregar un mensaje a un chat
    public void addMessage(String chatId, String text, String imageUrl, Usuario sender) throws Exception {
        String encryptedText = EncryptionUtils.encrypt(text);
        String encryptedImageUrl = imageUrl != null ? EncryptionUtils.encrypt(imageUrl) : null;

        Document messageDoc = new Document("chatId", new ObjectId(chatId))
                .append("text", encryptedText)
                .append("imageUrl", encryptedImageUrl)
                .append("sender", new Document("phoneNumber", sender.getNumero())
                        .append("username", sender.getUsuario()))
                .append("timestamp", new Date());

        messageCollection.insertOne(messageDoc);
    }

    // Obtener todos los mensajes de un chat
    public List<Mensaje> getMessages(String chatId) throws Exception {
        List<Mensaje> messages = new ArrayList<>();
        var cursor = messageCollection.find(new Document("chatId", new ObjectId(chatId))).iterator();

        while (cursor.hasNext()) {
            Document doc = cursor.next();
            String decryptedText = EncryptionUtils.decrypt(doc.getString("text"));
            String decryptedImageUrl = doc.getString("imageUrl") != null ? EncryptionUtils.decrypt(doc.getString("imageUrl")) : null;

            Document senderDoc = (Document) doc.get("sender");
            Usuario sender = new Usuario(senderDoc.getString("phoneNumber"), senderDoc.getString("username"));

//            Mensaje message = new Mensaje(doc.getObjectId("_id").toString(), decryptedText, decryptedImageUrl, sender, doc.getDate("timestamp"));
  //          messages.add(message);
        }

        return messages;
    }

    // Editar un mensaje
    public void editMessage(String messageId, String newText, String newImageUrl) throws Exception {
        String encryptedText = EncryptionUtils.encrypt(newText);
        String encryptedImageUrl = newImageUrl != null ? EncryptionUtils.encrypt(newImageUrl) : null;

        Document updateDoc = new Document("$set", new Document("text", encryptedText)
                .append("imageUrl", encryptedImageUrl));

        messageCollection.updateOne(new Document("_id", new ObjectId(messageId)), updateDoc);
    }

    // Eliminar un mensaje
    public void deleteMessage(String messageId) {
        messageCollection.deleteOne(new Document("_id", new ObjectId(messageId)));
    }

    // Obtener todos los chats en los que un usuario participa
    public List<Chat> getUserChats(Usuario user) {
        List<Chat> chats = new ArrayList<>();
        var cursor = chatCollection.find(new Document("participants.phoneNumber", user.getNumero())).iterator();

        while (cursor.hasNext()) {
            Document doc = cursor.next();
            String chatId = doc.getObjectId("_id").toString();
            String chatName = doc.getString("chatName");
            String thumbnailImageUrl = doc.getString("thumbnailImageUrl");

            List<Usuario> participants = new ArrayList<>();
            var participantsDocs = doc.getList("participants", Document.class);
            for (Document participantDoc : participantsDocs) {
                participants.add(new Usuario(participantDoc.getString("phoneNumber"), participantDoc.getString("username")));
            }

         //   Chat chat = new Chat(chatId, chatName, participants, thumbnailImageUrl);
           // chats.add(chat);
        }

        return chats;
    }

    // Editar un chat
    public void editChat(String chatId, String newChatName, String newThumbnailImageUrl) {
        Document updateDoc = new Document("$set", new Document("chatName", newChatName)
                .append("thumbnailImageUrl", newThumbnailImageUrl));

        chatCollection.updateOne(new Document("_id", new ObjectId(chatId)), updateDoc);
    }

    // Eliminar un chat
    public void deleteChat(String chatId) {
        chatCollection.deleteOne(new Document("_id", new ObjectId(chatId)));
        messageCollection.deleteMany(new Document("chatId", new ObjectId(chatId)));
    }
}
