/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Configuracion;

import Modelo.Carrito;
import Modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ines
 */
public class UsuarioDAO {

    private ConexionDAO connDAO;
    private Connection conexion;

    public UsuarioDAO() {
        this.connDAO = new ConexionDAO();
        this.conexion = connDAO.conectarBD();
    }

    public boolean existe(String correo) {
        boolean encontrado = false;

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            // si algo coincide devuelve 1 si no 0
            String sql = "SELECT COUNT(*) FROM usuarios WHERE email = ?";

            // prepara la consulta
            stmt = conexion.prepareStatement(sql);
            stmt.setString(1, correo);

            // ejecuta
            rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1); //devuelve si hay registros
                encontrado = (count > 0); // si > 0, existen registros cn ese correo
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return encontrado;
    }

    public Usuario obtenerUsuario(String correo) {
        Usuario us = new Usuario();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            String sql = "SELECT * FROM usuarios WHERE email = ?";
            // prepara la consulta
            stmt = conexion.prepareStatement(sql);
            stmt.setString(1, correo);
            // ejecuta
            rs = stmt.executeQuery();

            if (rs.next()) {
                us.setEmail(correo);
                us.setPassword(rs.getString("contrasena"));
                us.setRol(rs.getInt("rol"));
                us.setNombre(rs.getString("nombre"));
                us.setApellidos(rs.getString("apellidos"));
                us.setDireccion(rs.getString("direccion"));
                us.setIdCarrito(rs.getInt("id_cesta"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return us;
    }

    public void registrarUsuario(Usuario user) {
        CarritoDAO cD = new CarritoDAO();
        Carrito cAux = new Carrito("carrito");
        cAux.setId(cD.nuevaCesta(cAux));
        user.setCarrito(cAux);

        String consulta = "INSERT INTO usuarios (email, contrasena, nombre,  apellidos, direccion, rol,id_cesta ) VALUES (?, ?, ?, ?,?, ?, ?)";

        try (
                // Preparar la consulta
                PreparedStatement pstmt = conexion.prepareStatement(consulta);) {
            // Establecer los valores de los parámetros en la consulta preparada
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getNombre());
            pstmt.setString(4, user.getApellidos());
            pstmt.setString(5, "");
            pstmt.setInt(6, user.getRol());
            pstmt.setInt(7, cAux.getId());

            // Ejecutar la consulta
            int filasInsertadas = pstmt.executeUpdate();

            if (filasInsertadas > 0) {
                System.out.println("Los datos han sido insertados correctamente.");
            } else {
                System.out.println("No se pudo insertar ningún dato.");
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar datos: " + e.getMessage());
        }
    }

    //hacer un insert en carritos ???
    //hacer un insert de usuarios con el id del carrito ???
    public void actualizarContrasena(Usuario user, String pass) {
        String sql = "UPDATE usuarios SET contrasena = ? WHERE email = ?";
        System.out.println(pass);
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, pass);
            pstmt.setString(2, user.getEmail());

            pstmt.executeUpdate(); //ejecutar consulta

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizarDatosUsuario(String nombre, String apellidos, String direccion, String email) {
        String sql = "UPDATE usuarios SET ";
        boolean first = true;
        System.out.println("ENTRA EN EL METODO ACTUALIZAR");
         System.out.println("Parámetros recibidos - Nombre: " + nombre + ", Apellidos: " + apellidos + ", Dirección: " + direccion + ", Email: " + email);

        if (nombre != null && !nombre.trim().isEmpty()) {
            sql += "nombre = ?";
            first = false;
        }
        if (apellidos != null && !apellidos.trim().isEmpty()) {
            if (!first) {
                sql += ", ";
            }
            sql += "apellidos = ?";
            first = false;
        }
        if (direccion != null && !direccion.trim().isEmpty()) {
            if (!first) {
                sql += ", ";
            }
            sql += "direccion = ?";
        }

        sql += " WHERE email = ?";

        System.out.println(sql);
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            int paramIndex = 1;

            if (nombre != null && !nombre.trim().isEmpty()) {
                pstmt.setString(paramIndex++, nombre);
            }
            if (apellidos != null && !apellidos.trim().isEmpty()) {
                pstmt.setString(paramIndex++, apellidos);
            }
            if (direccion != null && !direccion.trim().isEmpty()) {
                pstmt.setString(paramIndex++, direccion);
            }

            pstmt.setString(paramIndex, email);

            pstmt.executeUpdate(); // ejecutar consulta

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
