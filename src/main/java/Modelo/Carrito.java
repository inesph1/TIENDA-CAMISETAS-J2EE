/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;

/**
 *
 * @author ines
 */
public class Carrito extends Cesta {

    public Carrito() {
        super();
    }

    public Carrito(String estado) {
        super(0, estado, new ArrayList<Producto>());
    }

    public Carrito(int id, String estado, ArrayList<Producto> productos) {
        super(id, estado, productos);
    }

    public Carrito(int id, String estado) {
        super(id, estado, new ArrayList<Producto>());
    }

    /* public ArrayList anadirProductosAlCarro(Producto prod) {
        ArrayList<Producto> cesta = getProductos();
        cesta.add(prod);
        setProductos(cesta);
        return cesta;
        //llamar a DAO no se a que
    }*/
    public boolean buscarProducto(Producto prod) {
        for (Producto p : getProductos()) {
            if (p.getId() == prod.getId()) {
                return true;
            }
        }
        return false;
    }
    
    public Producto obtenerProducto(Producto prod) {
        Producto auxiliar = new Producto();
        for (Producto p : getProductos()) {
            if (p.getId() == prod.getId()) {
                auxiliar = p;
                return auxiliar;
            }
        }
        return auxiliar;
    }

}
