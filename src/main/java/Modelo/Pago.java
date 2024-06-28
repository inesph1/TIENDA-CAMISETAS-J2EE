/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author ines
 */
public class Pago extends Cesta {

    private Timestamp fecha_pago;
    private String cliente;

    public Pago() {
        super();
        this.fecha_pago = new Timestamp(System.currentTimeMillis());
        this.cliente = "";
    }

    public Pago(Timestamp fecha_pago, String cliente, int id, String estado, ArrayList<Producto> productos) {
        super(id, estado, productos);
        this.fecha_pago = fecha_pago;
        this.cliente = cliente;
    }

    public Timestamp getFecha_pago() {
        return fecha_pago;
    }

    public String getCliente() {
        return cliente;
    }

    public void setFecha_pago(Timestamp fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
    
    public void tramitarPedido(){
    //hacer referencia a pagosDAO pasando la información para que la inserte
    }

}
