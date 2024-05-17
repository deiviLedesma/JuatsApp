/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Interfaces.IUserDAO;
import POJOs.User;
import Utils.EncryptionUtils;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

/**
 *
 * @author SDavidLedesma
 */
public class UserDAO implements IUserDAO {

    private final MongoCollection<User> coleccionUser;

    public UserDAO() {
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString("mongodb://localhost:27017"))
                .codecRegistry(pojoCodecRegistry)
                .build();

        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("JuatsApp");
        this.coleccionUser = database.getCollection("users", User.class);
    }

    @Override
    public void RegistrarUsuario(String numero, String password) {
        if (numero == null || numero.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("El número de teléfono y la contraseña son obligatorios.");
        }

        if (!numero.matches("\\d{10}")) {
            throw new IllegalArgumentException("El número de teléfono debe tener exactamente 10 dígitos.");
        }

        User existingUser = coleccionUser.find(Filters.eq("phoneNumber", numero)).first();
        if (existingUser != null) {
            throw new RuntimeException("El número de teléfono ya está registrado.");
        }

        try {
            String encryptedPassword = EncryptionUtils.encrypt(password);
            User newUser = new User(numero, encryptedPassword);
            coleccionUser.insertOne(newUser);
        } catch (Exception e) {
            throw new RuntimeException("Error al encriptar la contraseña", e);
        }
    }

    @Override
    public User login(String phoneNumberOrUsername, String password) {
        User user = coleccionUser.find(Filters.or(
                Filters.eq("phoneNumber", phoneNumberOrUsername),
                Filters.eq("username", phoneNumberOrUsername)))
                .first();

        if (user != null) {
            try {
                String decryptedPassword = EncryptionUtils.decrypt(user.getPassword());
                if (decryptedPassword.equals(password)) {
                    return user;
                }
            } catch (Exception e) {
                throw new RuntimeException("Error al desencriptar la contraseña", e);
            }
        }

        throw new RuntimeException("Nombre de usuario o contraseña incorrectos.");
    }
}
