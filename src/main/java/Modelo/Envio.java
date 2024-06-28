/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author ines
 */
public class Envio extends Cesta {

    private Timestamp fecha_envio;
    private Timestamp fecha_llegada;
    private String direccion;
    private String usuario;

      public Envio() {
        super();
        this.fecha_envio = new Timestamp(System.currentTimeMillis());
        this.fecha_llegada = new Timestamp(System.currentTimeMillis());
        this.direccion = "";
        this.usuario = "";
    }

    public Envio(Timestamp fecha_envio, Timestamp fecha_llegada, String direccion, int id, String estado, ArrayList<Producto> productos) {
        super(id, estado, productos);
        this.fecha_envio = fecha_envio;
        this.fecha_llegada = fecha_llegada;
        this.direccion = direccion;
        this.usuario = "";
    }

    public void setFecha_envio(Timestamp fecha_envio) {
        this.fecha_envio = fecha_envio;
    }

    public void setFecha_llegada(Timestamp fecha_llegada) {
        this.fecha_llegada = fecha_llegada;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Timestamp getFecha_envio() {
        return fecha_envio;
    }

    public Timestamp getFecha_llegada() {
        return fecha_llegada;
    }

    public String getDireccion() {
        return direccion;
    }

    public Timestamp setFechaRecogida(Timestamp fechaEnvio) {
        long millisInDay = 1000 * 60 * 60 * 24; // Millis en un día
        long millis15Days = millisInDay * 3; // Millis en 15 días

        long fechaEnvioMillis = fechaEnvio.getTime();
        long fechaRecogidaMillis = fechaEnvioMillis + millis15Days;

        this.fecha_llegada = new Timestamp(fechaRecogidaMillis);
        return this.fecha_llegada;
    }

    public void setEnviarPedido() {
        // Guardar en enviosDAO
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }
}
