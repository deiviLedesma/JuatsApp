/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author SDavidLedesma
 */
//Clase POJO
public class User {

    //Atributos
    private String phoneNumber;
    private String username;
    private String password;
    private String birthDate;
    private String profileImage;
    private String address;
    private String gender;

    /**
     * Constructor por omision
     */
    public User() {
    }

    /**
     * Constructor con los atributos creados
     * @param phoneNumber
     * @param username
     * @param password
     * @param birthDate
     * @param profileImage
     * @param address
     * @param gender 
     */
    public User(String phoneNumber, String username, String password, String birthDate, String profileImage, String address, String gender) {
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
        this.birthDate = birthDate;
        this.profileImage = profileImage;
        this.address = address;
        this.gender = gender;
    }
    
    //Getters y setters

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    
}
