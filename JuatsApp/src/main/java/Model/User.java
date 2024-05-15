/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.io.Serializable;
import org.bson.Document;

/**
 *
 * @author SDavidLedesma
 */
public class User implements Serializable {

    private String phoneNumber;
    private String username;
    private String password;
    private String birthDate;
    private String profileImage;
    private String address;
    private String gender;

    public User() {
    }

    public User(String phoneNumber, String username, String password, String birthDate, String profileImage, String address, String gender) {
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
        this.birthDate = birthDate;
        this.profileImage = profileImage;
        this.address = address;
        this.gender = gender;
    }

    public Document toDocument() {
        return new Document("phoneNumber", phoneNumber)
                .append("username", username)
                .append("password", password)
                .append("birthDate", birthDate)
                .append("profileImage", profileImage)
                .append("address", address)
                .append("gender", gender);
    }

    public static User fromDocument(Document doc) {
        return new User(
                doc.getString("phoneNumber"),
                doc.getString("username"),
                doc.getString("password"),
                doc.getString("birthDate"),
                doc.getString("profileImage"),
                doc.getString("address"),
                doc.getString("gender")
        );
    }

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
