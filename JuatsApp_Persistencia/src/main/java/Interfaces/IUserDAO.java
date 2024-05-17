/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import POJOs.User;

/**
 *
 * @author SDavidLedesma
 */
public interface IUserDAO {
    
    public void RegistrarUsuario(String numero, String password);
    public User login (String phoneNumberOrUsername, String password);
    
    
}
