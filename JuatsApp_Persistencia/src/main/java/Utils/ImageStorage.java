/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import Conexion.Conexion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 *
 * @author SDavidLedesma
 */
public class ImageStorage {

    private final MongoCollection<Document> imageCollection;

    public ImageStorage() {
        MongoDatabase database = Conexion.getDatabase();
        this.imageCollection = database.getCollection("user_images");
    }

    // Método para guardar la imagen de perfil de un usuario en la base de datos
    public void saveUserProfileImage(String userId, byte[] imageBytes) {
        Document imageDocument = new Document("_id", userId)
                .append("imageData", imageBytes);

        imageCollection.insertOne(imageDocument);
    }

    // Método para recuperar la imagen de perfil de un usuario de la base de datos
    public byte[] getUserProfileImage(String userId) {
        Document imageDocument = imageCollection.find(new Document("_id", userId)).first();
        if (imageDocument != null) {
            return (byte[]) imageDocument.get("imageData");
        }
        return null;
    }
}
