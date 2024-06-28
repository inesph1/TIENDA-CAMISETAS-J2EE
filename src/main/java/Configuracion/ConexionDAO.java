/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Configuracion;

import Modelo.Usuario;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author ines
 */
public class ConexionDAO {

    Connection conn;

    public Connection conectarBD() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

            String userName = "root";
            String password = "";
            String url = "jdbc:mysql://localhost/acido";
            conn = DriverManager.getConnection(url, userName, password);

        } catch (Exception e) {
            System.err.println("Error al conectar");
        }

        return conn; //se retorna
    }
    
    public void cerrarConexion(Connection connection) {
        if (connection != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
