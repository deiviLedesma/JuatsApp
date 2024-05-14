/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author SDavidLedesma
 */
public class Usuario {

    private String numero;
    private String usuario;
    private String password;

    public Usuario() {
    }

    public Usuario(String numero, String usuario, String password) {
        this.numero = numero;
        this.usuario = usuario;
        this.password = password;
    }

    public Usuario(String numero, String password) {
        this.numero = numero;
        this.password = password;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Usuario{" + "numero=" + numero + ", usuario=" + usuario + ", password=" + password + '}';
    }

}
