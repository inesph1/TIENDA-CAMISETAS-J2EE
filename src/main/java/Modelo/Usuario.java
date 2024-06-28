/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author ines
 */
public class Usuario {

    private int rol;
    private String nombre;
    private String apellidos;
    private String email;
    private String password;
    private Carrito carrito;
    private String direccion;
    //private ArrayList<Pedido> historial;

    public Usuario() {
        this.rol = 0;
        this.nombre = "";
        this.apellidos = "";
        this.email = "";
        this.password = "";
        this.carrito = new Carrito();
        this.direccion = "";
    }

    public Usuario(int rol, String nombre, String apellidos, String email, String password) {
        this.rol = rol;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.password = password;
        System.out.println("USUARIO CREADO.");
    }
    
        public Usuario(int rol, String nombre, String apellidos, String email, String password, Carrito c) {
        this.rol = rol;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.password = password;
        this.carrito = c;
        System.out.println("USUARIO CREADO.");
    }

    public Usuario(int rol, String nombre, String apellidos, String email, String password, String direccion) {
        this.rol = rol;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.password = password;
        this.direccion = direccion;
        System.out.println("USUARIO CREADO.");
    }
    
    public void setRol(int rol) {
        this.rol = rol;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCarrito(Carrito pCarrito) {
        this.carrito = pCarrito;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Carrito getCarrito() {
        return this.carrito;
    }

    public int getRol() {
        return this.rol;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getApellidos() {
        return this.apellidos;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void cambiarDatos() {
    }

    public void setIdCarrito(int id) {
        this.carrito.setId(id);
    }

    public int getIdCarrito() {
        return this.carrito.getId();
    }


    /*public Pedido verHistorial(){
    }*/
 /* public int obtenerIdCesta(){    
        return this.cesta.getId();
    }*/
    /*public String toString() {
        return this.rol + " " + this.nombre + " " + this.apellidos + "" + this.email + " " + this.password + "";
    }*/
}
