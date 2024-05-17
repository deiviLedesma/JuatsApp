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
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import java.util.List;
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

        CodecRegistry pojoCodecRegistry = fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString("mongodb://localhost:27017"))
                .codecRegistry(pojoCodecRegistry)
                .build();

        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("JuatsApp");
        this.coleccionUser = database.getCollection("users", User.class);
    }

    public UserDAO(MongoCollection<User> coleccionUser, CodecRegistry pojoCodecRegistry) {
        this.coleccionUser = coleccionUser;

    }

    public void RegistrarUsuario(String phoneNumber, String password, String username, String birthDate, String address, String gender) {
        // Validación de entrada
        if (phoneNumber == null || phoneNumber.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("El número de teléfono y la contraseña son obligatorios.");
        }

        if (!phoneNumber.matches("\\d{10}")) {
            throw new IllegalArgumentException("El número de teléfono debe tener exactamente 10 dígitos.");
        }

        // Verificar si el usuario ya está registrado
        User existingUser = coleccionUser.find(eq("phoneNumber", phoneNumber)).first();
        if (existingUser != null) {
            throw new RuntimeException("El número de teléfono ya está registrado.");
        }

        try {
            // Encriptar la contraseña antes de almacenarla en la base de datos
            EncryptionUtils eu = new EncryptionUtils();
            String encryptedPassword = eu.Encriptar(password);

            // Crear un nuevo objeto User
            User newUser = new User(phoneNumber, username, encryptedPassword, birthDate, address, gender);

            // Insertar el nuevo usuario en la base de datos
            coleccionUser.insertOne(newUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al encriptar la contraseña", e);
        }
    }

    @Override
    public User login(String phoneNumberOrUsername, String password) {
        User user = coleccionUser.find(Filters.or(
                Filters.eq("phoneNumber", phoneNumberOrUsername),
                Filters.eq("username", phoneNumberOrUsername)))
                .first();
        if (user == null) {
            throw new RuntimeException("Nombre de usuario o contraseña incorrectos.");
        }

        try {
            EncryptionUtils eu = new EncryptionUtils();
            // Desencriptar la contraseña almacenada en la base de datos
            String storedPassword = user.getPassword();
            String decryptedPassword = eu.desencriptar(storedPassword);

            // Verificar si la contraseña proporcionada coincide con la contraseña almacenada
            if (password.equals(decryptedPassword)) {
                return user;
            } else {
                throw new RuntimeException("Nombre de usuario o contraseña incorrectos.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al desencriptar la contraseña", e);
        }
    }

    @Override
    public List<User> obtenerUusarios() {
        List<User> usuarios = new ArrayList<>();
        try (MongoCursor<User> cursor = coleccionUser.find().iterator()) {
            while (cursor.hasNext()) {
                usuarios.add(cursor.next());
            }
        } catch (Exception e) {
            // Manejar la excepción apropiadamente
            e.printStackTrace();
        }
        return usuarios;
    }

}
