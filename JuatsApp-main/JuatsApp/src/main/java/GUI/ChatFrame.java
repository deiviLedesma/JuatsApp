/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import Model.Message;
import Model.User;
import Servidor.ChatClient;
import Servidor.UserService;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author SDavidLedesma
 */
public class ChatFrame extends JFrame {

     private UserService userService;
    private ChatClient client;
    private JTextArea chatArea;
    private JTextField messageField;
    private JComboBox<String> userComboBox;
    private MongoDatabase database;
    private MongoCollection<Document> messagesCollection;

    public ChatFrame() {
        setTitle("Chat");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("JuatApp");
        messagesCollection = database.getCollection("messages");

        userService = new UserService(database);
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

        List<String> userList = getUsersFromDatabase();
        userComboBox = new JComboBox<>(userList.toArray(new String[0]));
        panel.add(userComboBox, BorderLayout.NORTH);

        add(panel);
    }

    private void sendMessage() {
        String messageText = messageField.getText().trim();
        if (!messageText.isEmpty()) {
            String selectedUser = (String) userComboBox.getSelectedItem();
            User currentUser = UserService.getCurrentUser();
            if (currentUser != null) {
                Message message = new Message(generateMessageId(), messageText, "", currentUser, new Date());
                client.sendMessage(message);
                storeMessageInDatabase(message, selectedUser);
                messageField.setText("");
                loadMessagesFromDatabase(currentUser.getUsername(), selectedUser);
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo obtener el usuario actual.");
            }
        }
    }

    private List<String> getUsersFromDatabase() {
        return userService.getUsersFromDatabase();
    }

    private String generateMessageId() {
        return String.valueOf(System.currentTimeMillis());
    }

    private void storeMessageInDatabase(Message message, String recipient) {
        Document doc = new Document("messageId", message.getMessageId())
                .append("text", message.getText())
                .append("timestamp", message.getTimestamp())
                .append("sender", message.getSender().getUsername())
                .append("recipient", recipient);
        messagesCollection.insertOne(doc);
    }

    private void loadMessagesFromDatabase(String currentUser, String selectedUser) {
        chatArea.setText("");
        List<Document> messages = messagesCollection.find(Filters.or(
                Filters.and(Filters.eq("sender", currentUser), Filters.eq("recipient", selectedUser)),
                Filters.and(Filters.eq("sender", selectedUser), Filters.eq("recipient", currentUser))
        )).into(new ArrayList<>());

        for (Document messageDoc : messages) {
            String sender = messageDoc.getString("sender");
            String text = messageDoc.getString("text");
            Date timestamp = messageDoc.getDate("timestamp");
            chatArea.append(sender + " (" + timestamp + "): " + text + "\n");
        }
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