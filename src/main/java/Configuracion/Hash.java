/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Configuracion;

import Modelo.Usuario;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
/**
 *
 * @author ines
 */
public class Hash {

    private String rawPassword;
    private String encodedPassword;
    private BCryptPasswordEncoder encoder;

    public Hash() {
        this.rawPassword = "";
        this.encodedPassword = "";
        this.encoder = new BCryptPasswordEncoder();
    }

    public Hash(String rawPassword, String encodedPassword, BCryptPasswordEncoder encoder) {
        this.rawPassword = rawPassword;
        this.encodedPassword = encodedPassword;
        this.encoder = encoder;
    }

    public String getRawPassword() {
        return rawPassword;
    }

    public String getEncodedPassword() {
        return encodedPassword;
    }

    public BCryptPasswordEncoder getEncoder() {
        return encoder;
    }

    public void setRawPassword(String rawPassword) {
        this.rawPassword = rawPassword;
    }

    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }

    public void setEncoder(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    
    public String hashear(String raw) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = raw;
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println("SE ENCRIPTA"+encodedPassword);
        return encodedPassword;
        //System.out.println("Contraseña hasheada: " + encodedPassword);
    }
    
    public boolean comparar(String raw, Usuario user ) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("ENTRA EN COMPARAR");
        String encodedPassword = user.getPassword();
        //encoder.encode("pepe") esto codificaria el string en caso de no estarlo
        if (encoder.matches(raw, encodedPassword)) {
            System.out.println("Las contraseñas coinciden.");
            return true;
        } else {
            System.out.println("Las contraseñas no coinciden.");
            return false;
        }
    }

}
