/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

/**
 *
 * @author SDavidLedesma
 */
public class Conexion {
    
    private static MongoClient mongoClient = null;
    private static final String cadenaConexion = "mongodb://localhost/27017";
    private static final String database = "JuatsApp";
    
    public static MongoDatabase getDatabase() {
        if (mongoClient == null) {

            //configura el codec para manejar POJOs
            CodecRegistry PojoCodecRegistry = CodecRegistries.fromRegistries(
                    MongoClientSettings.getDefaultCodecRegistry(),
                    CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build())
            );
            //configura los ajustes del cliente MongoDB, incluyendo la cadena de conexion URL y el registro de codecs anterior
            MongoClientSettings clientSettings = MongoClientSettings.builder()
                    .applyConnectionString(new com.mongodb.ConnectionString(cadenaConexion))
                    .codecRegistry(PojoCodecRegistry)
                    .build();
            //se asigna los ajsutes al MongoCliente static de la clase
            mongoClient = MongoClients.create(clientSettings);
            //regresa la base de datos con la configuracion codecs
            return mongoClient.getDatabase(database).withCodecRegistry(PojoCodecRegistry);
            
        }
        //si no es null, la regresa nuevamente
        return mongoClient.getDatabase(database);
    }
}
