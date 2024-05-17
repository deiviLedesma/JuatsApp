/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bson.internal.Base64;

/**
 *
 * @author SDavidLedesma
 */
public class EncryptionUtils {

    String LLAVE = "bdaEsLaOnda";

    public SecretKeySpec crearClave(String llave) {

        try {
            byte[] cadena = llave.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            cadena = md.digest(cadena);
            cadena = Arrays.copyOf(cadena, 16);
            SecretKeySpec spec = new SecretKeySpec(cadena, "AES");
            return spec;
        } catch (Exception e) {
            return null;
        }
    }

    public String Encriptar(String encriptar) {
        try {
            SecretKeySpec spec = crearClave(LLAVE);
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.ENCRYPT_MODE, spec);

            byte[] cadena = encriptar.getBytes("UTF-8");
            byte[] encriptada = c.doFinal(cadena);
            String cadena_encriptada = Base64.encode(encriptada);
            return cadena_encriptada;

        } catch (Exception e) {
            return "";

        }
    }

    public String desencriptar(String desencriptar) {
        try {
            SecretKeySpec spec = crearClave(LLAVE);
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.DECRYPT_MODE, spec);

            byte[] cadena = Base64.decode(desencriptar);
            byte[] desencriptacion = c.doFinal(cadena);
            String cadena_desencriptada = new String(desencriptacion);
            return cadena_desencriptada;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * private static final String AES_ALGORITHM = "AES"; private static final
     * String SECRET_KEY = "SecretKey12345678"; // 16 bytes
     *
     * public static String encrypt(String plainText) throws Exception { Key key
     * = new SecretKeySpec(SECRET_KEY.getBytes(), AES_ALGORITHM); Cipher cipher
     * = Cipher.getInstance(AES_ALGORITHM); cipher.init(Cipher.ENCRYPT_MODE,
     * key); byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
     * return Base64.getEncoder().encodeToString(encryptedBytes); }
     *
     * public static String decrypt(String encryptedText) throws Exception { Key
     * key = new SecretKeySpec(SECRET_KEY.getBytes(), AES_ALGORITHM); Cipher
     * cipher = Cipher.getInstance(AES_ALGORITHM);
     * cipher.init(Cipher.DECRYPT_MODE, key); byte[] decryptedBytes =
     * cipher.doFinal(Base64.getDecoder().decode(encryptedText)); return new
     * String(decryptedBytes); } *
     */
}
