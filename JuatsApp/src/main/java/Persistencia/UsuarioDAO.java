/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import Model.Usuario;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.Arrays;
import org.bson.Document;

/**
 *
 * @author SDavidLedesma
 */
public class UsuarioDAO {

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> userCollection;

    public UsuarioDAO() {
        // Configuración de la conexión a MongoDB
        MongoClientURI url = new MongoClientURI("mongodb://localhost:27017");
        mongoClient = new MongoClient(url);
        database = mongoClient.getDatabase("JuatsApp");
        userCollection = database.getCollection("Usuarios");
    }

    public void RegistrarUsuario(String numero, String password) {
        // Validar los datos del usuario (longitud, formato, etc.)
        if (numero == null || numero.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("El número de teléfono y la contraseña son obligatorios.");
        }

        // Verificar si el número de teléfono ya está en uso
        Document existingUser = userCollection.find(new Document("numero", numero)).first();
        if (existingUser != null) {
            throw new RuntimeException("El número de teléfono ya está registrado.");
        }

        // Crear el documento del nuevo usuario
        Document newUser = new Document()
                .append("numero", numero)
                .append("contrasenia", password);

        // Insertar el nuevo usuario en la colección de usuarios
        userCollection.insertOne(newUser);
    }

    public Usuario login(String phoneNumberOrUsername, String password) {
        Document userDocument = userCollection.find(
                new Document("$or", Arrays.asList(
                        new Document("numero", phoneNumberOrUsername),
                        new Document("contrasenia", phoneNumberOrUsername)
                ))
        ).first();

        if (userDocument != null) {
            String storedPassword = userDocument.getString("contrasenia");
            if (storedPassword.equals(password)) {
                // Las credenciales son correctas, devolver el usuario
                // Aquí podrías devolver un objeto User con los datos del usuario
                return new Usuario(userDocument.getString("numero"), userDocument.getString("usuario"));
            }
        }

        // Las credenciales son incorrectas, lanzar excepción o devolver null
        throw new RuntimeException("Nombre de usuario o contraseña incorrectos.");
    }
}
