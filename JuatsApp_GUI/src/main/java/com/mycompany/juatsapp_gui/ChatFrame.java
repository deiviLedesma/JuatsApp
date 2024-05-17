/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.juatsapp_gui;

import Conexion.Conexion;
import DAOs.ChatDAO;
import DAOs.UserDAO;
import POJOs.Chat;
import POJOs.Message;
import POJOs.User;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

/**
 *
 * @author diana
 */
public class ChatFrame extends javax.swing.JFrame {

    private DefaultComboBoxModel<String> comboBoxModel;
    private ChatDAO chatDAO;
    private UserDAO userDAO;
    private MongoCollection<Chat> coleccionChats;
    private MongoCollection<User> coleccionUsuarios;
    private CodecRegistry pojoCodecRegistry;
    private User currentUser;
    private Chat chat;

    /**
     * Creates new form Chat
     */
    public ChatFrame() {

        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        coleccionChats = Conexion.getDatabase().getCollection("chat", Chat.class);
        coleccionUsuarios = Conexion.getDatabase().getCollection("users", User.class);

        chatDAO = new ChatDAO(coleccionChats);

        // Creamos el CodecRegistry para UserDAO
        pojoCodecRegistry = fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build())
        );

        // Pasamos la colección de usuarios y el CodecRegistry al constructor de UserDAO
        userDAO = new UserDAO(coleccionUsuarios, pojoCodecRegistry);

        comboBoxModel = new DefaultComboBoxModel<>();
        jComboBox1.setModel(comboBoxModel);

        cargarUsuariosEnComboBox();
        mostrarMensajesDeChat(chat);
    }

    private Chat obtenerOCrearChat(User currentUser, User selectedUser) {
        List<Chat> chats = chatDAO.obtenerChatsDeUsuario(currentUser);
        for (Chat chat : chats) {
            if (chat.getParticipants().contains(selectedUser)) {
                return chat;
            }
        }
        return null;
    }

    private void cargarUsuariosEnComboBox() {
        List<User> usuarios = userDAO.obtenerUusarios();
        for (User usuario : usuarios) {
            jComboBox1.addItem(usuario.getUsername());
        }
    }

    private void mostrarMensajesDeChat(Chat chat) {
        jTextArea1.setText("");
        List<Message> mensajes = chatDAO.obteenerMensajesDeChat(chat);
        for (Message mensaje : mensajes) {
            jTextArea1.append(mensaje.getSender().getUsername() + ": " + mensaje.getText() + "\n");
        }
    }

    private void enviarMensaje() {
        String messageText = txtEnvia.getText().trim();
        if (!messageText.isEmpty()) {
            User selectedUser = (User) jComboBox1.getSelectedItem();
            if (selectedUser != null) {
                try {
                    // Crear o encontrar un chat entre el usuario actual y el usuario seleccionado
                    Chat chat = obtenerOCrearChat(currentUser, selectedUser);

                    // Crear un nuevo mensaje
                    Message message = new Message(chat.getChatId(), messageText, "", currentUser, new Date());

                    // Agregar el mensaje al chat
                    chatDAO.addMessage(chat.getChatId(), messageText, "", currentUser);

                    // Actualizar el área de texto del chat
                    mostrarMensajesDeChat(chat);

                    // Limpiar el campo de texto del mensaje
                    txtEnvia.setText("");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error al enviar el mensaje: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor selecciona un usuario.");
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jComboBox1 = new javax.swing.JComboBox<>();
        txtEnvia = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        Regresar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(253, 248, 248));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 550, 360));

        jComboBox1.setForeground(new java.awt.Color(82, 167, 253));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chat 1", "Chat 2", " " }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 530, 40));
        jPanel1.add(txtEnvia, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, 440, 30));

        jButton1.setBackground(new java.awt.Color(82, 167, 253));
        jButton1.setFont(new java.awt.Font("Segoe UI Symbol", 0, 14)); // NOI18N
        jButton1.setText("Enviar");
        jButton1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 450, 70, 30));

        Regresar.setBackground(new java.awt.Color(82, 167, 253));
        Regresar.setFont(new java.awt.Font("Segoe UI Symbol", 0, 14)); // NOI18N
        Regresar.setText("Regresar");
        Regresar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Regresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegresarActionPerformed(evt);
            }
        });
        jPanel1.add(Regresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 490, 110, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 592, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void RegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegresarActionPerformed
        // TODO add your handling code here:
        Login login = new Login();
        login.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_RegresarActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Chat.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Chat.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Chat.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Chat.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChatFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Regresar;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField txtEnvia;
    // End of variables declaration//GEN-END:variables
}