/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import Model.User;
import Persistencia.MongoDBConnection;
import Servidor.UserService;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import org.bson.Document;

/**
 *
 * @author SDavidLedesma
 */
public class LoginRegisterFrame extends JFrame {

    private JTextField phoneNumberField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField birthDateField;
    private JTextField profileImageField;
    private JTextField addressField;
    private JComboBox<String> genderComboBox;

    private MongoDatabase database;

    public LoginRegisterFrame() {
        setTitle("Juatsapp - Registro e Inicio de Sesión");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        database = MongoDBConnection.getDatabase();

        getContentPane().setBackground(new Color(20, 150, 90));

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel registerPanel = createRegisterPanel();
        JPanel loginPanel = createLoginPanel();

        tabbedPane.add("Registro", registerPanel);
        tabbedPane.add("Inicio de Sesión", loginPanel);

        add(tabbedPane);
    }

    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new GridLayout(8, 2));
        getContentPane().setBackground(new Color(20, 150, 90));
        Font font = new Font("Arial Black", Font.PLAIN, 14);

        phoneNumberField = new JTextField();
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        birthDateField = new JTextField();
        profileImageField = new JTextField();
        addressField = new JTextField();
        genderComboBox = new JComboBox<>(new String[]{"Masculino", "Femenino", "Robot", "Ninja", "Otro"});

        phoneNumberField.setFont(font);
        phoneNumberField.setFont(font);
        usernameField.setFont(font);
        passwordField.setFont(font);
        birthDateField.setFont(font);
        profileImageField.setFont(font);
        addressField.setFont(font);
        genderComboBox.setFont(font);

        panel.add(new JLabel("Número de Teléfono:"));
        panel.add(phoneNumberField);
        panel.add(new JLabel("Nombre de Usuario:"));
        panel.add(usernameField);
        panel.add(new JLabel("Contraseña:"));
        panel.add(passwordField);
        panel.add(new JLabel("Fecha de Nacimiento:"));
        panel.add(birthDateField);
        panel.add(new JLabel("Imagen de Perfil:"));
        panel.add(profileImageField);
        panel.add(new JLabel("Dirección:"));
        panel.add(addressField);
        panel.add(new JLabel("Género:"));
        panel.add(genderComboBox);

        JButton registerButton = new JButton("Registrar");
        registerButton.setFont(font);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });

        panel.add(new JLabel());
        panel.add(registerButton);

        return panel;
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        getContentPane().setBackground(new Color(20, 150, 90));
        Font font = new Font("Arial Black", Font.PLAIN, 14);

        JTextField loginPhoneNumberField = new JTextField();
        JPasswordField loginPasswordField = new JPasswordField();

        loginPhoneNumberField.setFont(font);
        loginPasswordField.setFont(font);

        panel.add(new JLabel("Número de Teléfono o Usuario:"));
        panel.add(loginPhoneNumberField);
        panel.add(new JLabel("Contraseña:"));
        panel.add(loginPasswordField);

        JButton loginButton = new JButton("Iniciar Sesión");
        loginButton.setFont(font);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String phoneNumberOrUsername = loginPhoneNumberField.getText();
                String password = new String(loginPasswordField.getPassword());
                loginUser(phoneNumberOrUsername, password);
            }
        });

        panel.add(new JLabel());
        panel.add(loginButton);

        return panel;
    }

    private void registerUser() {
        String phoneNumber = phoneNumberField.getText();
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String birthDate = birthDateField.getText();
        String profileImage = profileImageField.getText();
        String address = addressField.getText();
        String gender = (String) genderComboBox.getSelectedItem();

        User user = new User(phoneNumber, username, password, birthDate, profileImage, address, gender);

        MongoCollection<Document> usersCollection = database.getCollection("users");
        Document existingUser = usersCollection.find(eq("phoneNumber", phoneNumber)).first();

        if (existingUser == null) {
            usersCollection.insertOne(user.toDocument());
            JOptionPane.showMessageDialog(this, "Usuario registrado exitosamente.");
        } else {
            JOptionPane.showMessageDialog(this, "El número de teléfono ya está registrado.");
        }
    }

    private void loginUser(String phoneNumberOrUsername, String password) {
        MongoCollection<Document> usersCollection = database.getCollection("users");
        Document userDoc = usersCollection.find(eq("phoneNumber", phoneNumberOrUsername)).first();

        if (userDoc == null) {
            userDoc = usersCollection.find(eq("username", phoneNumberOrUsername)).first();
        }

        if (userDoc != null && userDoc.getString("password").equals(password)) {
            User user = User.fromDocument(userDoc);
            UserService.setCurrentUser(user); // Establecer el usuario actual en SessionManager
            new ChatFrame().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginRegisterFrame().setVisible(true);
            }
        });
    }
}
