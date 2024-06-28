/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Configuracion;

import Modelo.Producto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ines
 */
public class ProductoDAO {

    private ConexionDAO connDAO;
    private Connection conexion;

    public ProductoDAO() {
        this.connDAO = new ConexionDAO();
        this.conexion = connDAO.conectarBD();
    }

    public ArrayList<Producto> obtenerTodosLosProductos() {
        ArrayList<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos";

        try (Connection conn = this.conexion; PreparedStatement stmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Producto producto = new Producto();
                    producto.setId(rs.getInt("id_producto"));
                    producto.setNombre(rs.getString("nombre"));
                    producto.setTalla(rs.getString("talla"));
                    //aqui en el caso de talla se podría buscar por el código de producto al que pertenece y crear un elemento talla y guardarlo en el arraylist
                    producto.setPrecio(rs.getFloat("precio"));
                    producto.setCantidad(rs.getInt("cantidad"));
                    producto.setImagen(rs.getString("imagen"));
                    producto.setAlta(rs.getBoolean("alta"));
                    productos.add(producto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }

    public Producto buscarProducto(ArrayList<Producto> listadoProductos, int id) {
        for (Producto producto : listadoProductos) {
            if (producto.getId() == id) {
                System.out.println(producto.getId() + "?=" + id);
                return producto;
            }
        }
        return null;
    }

    public int obtenerCantidadMaxima(int id) {

        int cantidad = 0;
        String sql = "SELECT cantidad FROM productos WHERE id_producto = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = this.conexion;
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                cantidad = rs.getInt("cantidad");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // manejar excepciones
        } finally {
            // mantener conexion abierta por si se trabaja mas adelante con la instancia de la conexion
        }

        return cantidad;
    }

    //NO CIERRA LA CONEXION PORQUE SERÁ CERRADA CON EL METODO OBTENER TODOS LOS PRODUCTOS PARA ACTUALIZAR LOS CAMBIOS
    public void nuevoProducto(Producto prod) {
        System.out.println("Producto a guardar " + prod);
        String sql = "INSERT INTO productos (nombre, talla, precio, cantidad, imagen, alta) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement pstmt = this.conexion.prepareStatement(sql);

            pstmt.setString(1, prod.getNombre());
            pstmt.setString(2, prod.getTalla());
            pstmt.setDouble(3, prod.getPrecio());
            pstmt.setInt(4, prod.getCantidad());
            pstmt.setString(5, prod.getImagen());
            pstmt.setBoolean(6, prod.isAlta());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Producto insertado exitosamente.");
            } else {
                System.out.println("No se pudo insertar el producto.");
            }
            // cerrar el PreparedStatement
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al insertar el producto: " + e.getMessage());
        }
    }

    //NO CIERRA LA CONEXION PORQUE SERÁ CERRADA CON EL METODO OBTENER TODOS LOS PRODUCTOS PARA ACTUALIZAR LOS CAMBIOS
    public void modificarProducto(Producto prod) {
        //buscar id coincidente y modificar los datos
        System.out.println(prod);
        String sql = "UPDATE productos SET nombre = ?, talla=?, precio = ?, cantidad = ?, alta=? WHERE id_producto = ?";

        try {
            PreparedStatement pstmt = this.conexion.prepareStatement(sql);
            pstmt.setString(1, prod.getNombre());
            pstmt.setString(2, prod.getTalla());
            pstmt.setDouble(3, prod.getPrecio());
            pstmt.setInt(4, prod.getCantidad());
            pstmt.setBoolean(5, prod.isAlta());
            pstmt.setInt(6, prod.getId());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Producto actualizado exitosamente.");
            } else {
                System.out.println("No se encontró el producto con el ID especificado.");
            }
            // cierre PreparedStatement 
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al actualizar el producto: " + e.getMessage());
        }
    }

    /*public void actualizarCantidadBD(int p, String t, int cantidad) {
        System.out.println("ACTUALIZAR PRODUCTO CON ID " + p);
        System.out.println("EL USUARIO HA " + t + " PRODUCTOS EN SU CESTA");

        // define las consultas SQL para sumar y restar
        String sqlSumar = "UPDATE productos SET cantidad = cantidad + ? WHERE id_producto = ?";
        String sqlRestar = "UPDATE productos SET cantidad = cantidad - ? WHERE id_producto = ?";

        // define que consulta se realiza según el tipo que se pasa por parametro
        String sql = null;
        if ("sumar".equals(t)) {
            sql = sqlRestar;
        } else if ("restar".equals(t)) {
            sql = sqlSumar;
        } else {
            System.out.println("Tipo de operación no válido");
            return;
        }

        try (Connection conn = this.conexion; PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, cantidad);
            pstmt.setInt(2, p);

            // ejecutar la actualización
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("La cantidad del producto se ha actualizado correctamente.");
            } else {
                System.out.println("No se encontró el producto con el ID especificado.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al actualizar la cantidad del producto.");
        }
    }*/
    
    //NO CIERRA LA CONEXION PARA SEGUIR USANDO LA INSTANCIA PDAO producto
    public void actualizarCantidadBD(int p, String t, int cantidad) {
    System.out.println("ACTUALIZAR PRODUCTO CON ID " + p);
    System.out.println("EL USUARIO HA " + t + " PRODUCTOS EN SU CESTA");

    String sqlSumar = "UPDATE productos SET cantidad = cantidad + ? WHERE id_producto = ?";
    String sqlRestar = "UPDATE productos SET cantidad = cantidad - ? WHERE id_producto = ?";

    String sql = "sumar".equals(t) ? sqlRestar : ("restar".equals(t) ? sqlSumar : null);
    if (sql == null) {
        System.out.println("Tipo de operación no válido");
        return;
    }

    try (PreparedStatement pstmt = this.conexion.prepareStatement(sql)) {
        pstmt.setInt(1, cantidad);
        pstmt.setInt(2, p);

        int rowsAffected = pstmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("La cantidad del producto se ha actualizado correctamente.");
        } else {
            System.out.println("No se encontró el producto con el ID especificado.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Error al actualizar la cantidad del producto.");
    }
}


    public void actualizarCantidadProducto(int p, int cantidad) {
        System.out.println("ACTUALIZAR PRODUCTO CON ID " + p);
        System.out.println("EN EN LA BAD HAY QUE  " + cantidad);
        PreparedStatement pstmt = null;

        try {
            String sql = "UPDATE productos SET cantidad = cantidad + ? WHERE id_producto = ?";
            pstmt = this.conexion.prepareStatement(sql);
            pstmt = conexion.prepareStatement(sql);

            pstmt.setInt(1, cantidad);
            pstmt.setInt(2, p);

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Cantidad del producto actualizada exitosamente.");
            } else {
                System.out.println("No se encontró el producto con el ID especificado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // cerrar recursos
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void close() {
        // cerrar la conexión cuando se destruya la instancia de ProductoDAO
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    /* public boolean comprobarStockBD(Producto p) {
        boolean stockSuficiente = false;
        String sql = "SELECT cantidad FROM productos WHERE id_producto = ?";

        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, p.getId());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int cantidadDisponible = resultSet.getInt("cantidad");
                    if (p.getCantidad() <= cantidadDisponible) {
                        stockSuficiente = true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                throw e;  
            } catch (SQLException ex) {
                Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return stockSuficiente;
    }*/

 /*public int obtenerCantidadBD(Producto p) {
        String sql = "SELECT cantidad FROM productos WHERE id_producto = ?";
         int cantidadDisponible =0;
        
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, p.getId());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    cantidadDisponible = resultSet.getInt("cantidad");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                throw e;  
            } catch (SQLException ex) {
                Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return cantidadDisponible;
    }*/
 /* public void eliminarProducto(int id) {
    }*/

 /*  public int obtenerCantidadTalla(String pTalla){
        return 0;
    }*/
}
