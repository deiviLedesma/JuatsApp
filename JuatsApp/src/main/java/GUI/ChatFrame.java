/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import Model.Message;
import Model.User;
import Servidor.ChatClient;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 *
 * @author SDavidLedesma
 */
public class ChatFrame extends JFrame {

    private ChatClient client;
    private JTextArea chatArea;
    private JTextField messageField;

    public ChatFrame() {
        setTitle("Chat");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        client = new ChatClient();

        JPanel panel = new JPanel(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        messageField = new JTextField();
        messageField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        panel.add(messageField, BorderLayout.SOUTH);

        add(panel);

        // Agrega un oyente de mensajes para actualizar la interfaz de usuario
        client.addMessageListener(new ChatClient.MessageListener() {
            public void onMessageReceived(Message message) {
                displayMessage(message);
            }

            public void onMessageReceived(Object message) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
    }

    private void sendMessage() {
        String messageText = messageField.getText().trim();
        if (!messageText.isEmpty()) {
            // Crea un nuevo mensaje con el texto ingresado y el usuario actual
            User currentUser = getCurrentUser(); // Debes implementar este método
            if (currentUser != null) {
                Message message = new Message("1", messageText, "", currentUser, new Date()); // ID del mensaje debe ser generado
                client.sendMessage(message);
                messageField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo obtener el usuario actual.");
            }
        }
    }

    // Método para mostrar un mensaje en la conversación
    private void displayMessage(Message message) {
        chatArea.append(message.getSender().getUsername() + ": " + message.getText() + "\n");
    }

    // Método para obtener el usuario actual (debes implementarlo según la lógica de tu aplicación)
    private User getCurrentUser() {
        // Implementa este método para devolver el usuario actual de tu aplicación
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ChatFrame().setVisible(true);
            }
        });
    }
}
