
import DAOs.UserDAO;
import POJOs.User;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
/**
 *
 * @author SDavidLedesma
 */
public class Caliz {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        UserDAO userDAO = new UserDAO();

        // Datos de prueba
        String numero = "1234567890";
        String password = "password123";

        try {
            // Registrar un nuevo usuario
            System.out.println("Registrando usuario...");
            userDAO.RegistrarUsuario(numero, password);
            System.out.println("Usuario registrado exitosamente.");

            // Intentar iniciar sesión con el nuevo usuario
            System.out.println("Iniciando sesión...");
            User user = userDAO.login(numero, password);

            if (user != null) {
                System.out.println("Inicio de sesión exitoso.");
                System.out.println("Número: " + user.getPhoneNumber());
            } else {
                System.out.println("Inicio de sesión fallido.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
