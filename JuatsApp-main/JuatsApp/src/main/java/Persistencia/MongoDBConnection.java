/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;

/**
 *
 * @author SDavidLedesma
 */
public class MongoDBConnection {

    private static MongoDatabase database;

    private static MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");

    public static MongoDatabase getDatabase() {
        return mongoClient.getDatabase("chat_db");
    }

    public Document getUserDocument(String usernameOrPhoneNumber) {
        MongoCollection<Document> usersCollection = database.getCollection("users");
        Document userDoc = usersCollection.find(eq("phoneNumber", usernameOrPhoneNumber)).first();

        if (userDoc == null) {
            userDoc = usersCollection.find(eq("username", usernameOrPhoneNumber)).first();
        }

        return userDoc;
    }
}
