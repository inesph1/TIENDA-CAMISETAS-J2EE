/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author ines
 */
public class Producto implements Cloneable {

    private int id;
    private String nombre;
    //private ArrayList<Talla> talla;
    private String talla;
    private float precio;
    private int cantidad;
    private String imagen;
    private boolean alta;

    public Producto() {
        this.id = 0;
        this.nombre = "";
        this.talla = "";
        this.precio = 0;
        this.cantidad = 0;
        this.imagen = "";
        this.alta = false;
    }

    //producto para eliminar de la lista
    public Producto(int id, int cantidad) {
        this.id = id;
        this.nombre = "";
        this.talla = "";
        this.precio = 0;
        this.cantidad = cantidad;
    }

    public Producto(int id, String nombre, String talla, float precio, int cantidad, String url) {
        this.id = id;
        this.nombre = nombre;
        this.talla = talla;
        this.precio = precio;
        this.cantidad = cantidad;
        this.imagen = url;

    }

    public Producto(int id, String nombre, String talla, float precio, int cantidad, String url, Boolean alta) {
        this.id = id;
        this.nombre = nombre;
        this.talla = talla;
        this.precio = precio;
        this.cantidad = cantidad;
        this.imagen = url;
        this.alta = alta;
    }
    
        public Producto( String nombre, String talla, float precio, int cantidad, String url, Boolean alta) {
        this.id = 0;
        this.nombre = nombre;
        this.talla = talla;
        this.precio = precio;
        this.cantidad = cantidad;
        this.imagen = url;
        this.alta = alta;
    }
    
        public Producto(int id, String nombre, String talla, float precio, int cantidad, Boolean alta) {
        this.id = id;
        this.nombre = nombre;
        this.talla = talla;
        this.precio = precio;
        this.cantidad = cantidad;
        this.imagen = "";
        this.alta = alta;
    }

    public void setAlta(boolean alta) {
        this.alta = alta;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTalla() {
        return talla;
    }

    public float getPrecio() {
        return precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public boolean isAlta() {
        return alta;
    }

    public String getImagen() {
        return imagen;
    }

    public void sumarCantidad(int suma) {
        this.cantidad = this.cantidad + suma;
    }

    public void restarCantidad(int resta) {
        if (this.cantidad > 0) {
            this.cantidad = this.cantidad - resta;
        } else {
            System.out.println("No se puede restar a 0 ");
        }

    }

    public float calcularTotalProducto(int cantidad, float precio) {
        return cantidad * precio;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
