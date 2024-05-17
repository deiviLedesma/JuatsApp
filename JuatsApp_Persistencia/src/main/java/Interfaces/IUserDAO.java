/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import POJOs.User;
import java.util.List;

/**
 *
 * @author SDavidLedesma
 */
public interface IUserDAO {

    public void RegistrarUsuario(String phoneNumber, String password, String username, String birthDate, String address, String gender);

    public User login(String phoneNumberOrUsername, String password);

    public List<User> obtenerUusarios();

}
