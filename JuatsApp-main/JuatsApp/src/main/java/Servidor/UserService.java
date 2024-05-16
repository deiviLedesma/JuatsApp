/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servidor;

import Model.User;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author SDavidLedesma
 */
public class UserService {

    private static User currentUser;
    private MongoDatabase database;

    public UserService(MongoDatabase database) {
        this.database = database;
    }

    public List<String> getUsersFromDatabase() {
        List<String> userList = new ArrayList<>();
        MongoCollection<Document> usersCollection = database.getCollection("users");
        for (Document doc : usersCollection.find()) {
            userList.add(doc.getString("username"));
        }
        return userList;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }
}
