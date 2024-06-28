/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author ines
 */
public class Cesta {

    private int id;
    private String estado;
    private ArrayList<Producto> productos;

    public Cesta() {
        this.id = 0;
        this.estado = "";
        this.productos = new ArrayList<>();
    }

    public Cesta(int id, String estado, ArrayList<Producto> productos) {
        this.id = id;
        this.estado = estado;
        this.productos = productos;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }

    public int getId() {
        return id;
    }

    public String getEstado() {
        return estado;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public float calcularTotalCesta() {
        float total = 0;
        for (Producto prod : this.productos) {
            total = total + (prod.getCantidad() * prod.getPrecio());
        }
        return total;
    }

    //REVISAR
    public void eliminarProductosCarro(Producto prod) {
        //se pasa por parametro un objeto producto con una cantidad y un id
        if (this.estado.equals("carrito")) {
            Iterator<Producto> iterador = this.productos.iterator();
            while (iterador.hasNext()) {
                Producto producto = iterador.next();
                if (producto.getId() == prod.getId()) {
                    if (prod.getCantidad() <= 0) {
                        iterador.remove();
                    } else {
                        producto.setCantidad(prod.getCantidad());
                    }

                }
            }
        }

    }

    //REVISAR
    public void sumarProductosCarro(Producto prod) {
        //se pasa por parametro un objeto producto con una cantidad y un id, talla etc
        if (this.estado.equals("carrito")) {
            Iterator<Producto> iterador = this.productos.iterator();
            boolean encontrado = false;

            while (iterador.hasNext()) {
                Producto producto = iterador.next();
                if (producto.getId() == prod.getId() && producto.getTalla() == prod.getTalla()) {
                    if (prod.getCantidad() >= 0 /*&& (prod.getCantidad()+producto.getCantidad()<= 25)*/) {
                        producto.setCantidad(prod.getCantidad()); //REVISAR CON LOS NUEVOS CAMBIOS SI SUMA O NO
                        System.out.println("PRODUCTO MODIFICADO");
                        encontrado = true;
                        break;
                    } else {
                        System.out.println("NEGATIVO NO VALIDO");
                    }
                }
            }
            if (!encontrado) {
                System.out.println("PRODUCTO AÑADIDO");
                this.productos.add(prod);
            }
        } else {
            System.out.println("El estado no es carrito");
        }

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < this.productos.size(); i++) {
            sb.append(this.productos.get(i).toString());
            if (i < this.productos.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
