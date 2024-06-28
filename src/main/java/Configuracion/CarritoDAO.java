/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Configuracion;

import Modelo.Carrito;
import Modelo.Cesta;
import Modelo.Envio;
import Modelo.Producto;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author ines
 */
public class CarritoDAO {

    private ConexionDAO connDAO;
    private Connection conexion;

    public CarritoDAO() {
        this.connDAO = new ConexionDAO();
        this.conexion = connDAO.conectarBD();
    }

    public int nuevaCesta(Cesta cAux) {
        String query = "INSERT INTO cestas (estado) VALUES (?)";

        try (PreparedStatement pstmt = conexion.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            // Asignar valor al parámetro
            pstmt.setString(1, cAux.getEstado());

            // Ejecutar la consulta
            pstmt.executeUpdate();

            // Obtener el ID generado
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int idCesta = generatedKeys.getInt(1);
                System.out.println("Cesta creada correctamente. ID de la cesta: " + idCesta);
                return idCesta;
            } else {
                throw new SQLException("No se pudo obtener el ID de la cesta creada.");
            }
        } catch (SQLException e) {
            System.out.println("Error al crear la cesta: " + e.getMessage());
            return -1; // Otra señal de error
        }
    }

    public void actualizarIDCestaUsuario(String email, int idCesta) {
        System.out.println("ID CESTA " + idCesta);
        String sqlUpdate = "UPDATE usuarios SET id_cesta = ? WHERE email = ?";

        try (Connection conn = connDAO.conectarBD(); PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {
            // Establecer los parámetros de la consulta preparada
            pstmt.setInt(1, idCesta);
            pstmt.setString(2, email);

            // Ejecutar la consulta
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Se actualizó correctamente el id_cesta del usuario con el correo electrónico: " + email);
            } else {
                System.out.println("No se encontró ningún usuario con el correo electrónico: " + email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al actualizar el id_cesta del usuario: " + e.getMessage());
        }

    }

    public void actualizarCarritoBD(int idCarrito, Producto prod) throws SQLException {

        if (prod.getCantidad() == 0) {
            // Si la cantidad es 0, eliminar el producto del carrito
            String sqlDelete = "DELETE FROM cestas_productos WHERE id_cesta = ? AND id_producto = ?";
            try (PreparedStatement statementDelete = conexion.prepareStatement(sqlDelete)) {
                statementDelete.setInt(1, idCarrito);
                statementDelete.setInt(2, prod.getId());
                statementDelete.executeUpdate();
                System.out.println("Producto eliminado del carrito porque la cantidad es 0.");
            }
        } else {
            String sqlSelect = "SELECT * FROM cestas_productos WHERE id_cesta = ? AND id_producto = ?";
            try (PreparedStatement statementSelect = conexion.prepareStatement(sqlSelect)) {
                statementSelect.setInt(1, idCarrito);
                statementSelect.setInt(2, prod.getId());

                try (ResultSet resultSet = statementSelect.executeQuery()) {
                    if (resultSet.next()) {
                        // EXISTE, ACTUALIZA
                        String sqlUpdate = "UPDATE cestas_productos SET talla = ?, cantidad = ? WHERE id_cesta = ? AND id_producto = ?";
                        try (PreparedStatement statementUpdate = conexion.prepareStatement(sqlUpdate)) {
                            statementUpdate.setString(1, prod.getTalla());
                            statementUpdate.setInt(2, prod.getCantidad());
                            statementUpdate.setInt(3, idCarrito);
                            statementUpdate.setInt(4, prod.getId());
                            statementUpdate.executeUpdate();
                        }
                    } else {
                        // NO EXISTE INSERTA
                        String sqlInsert = "INSERT INTO cestas_productos (id_cesta, id_producto, talla, cantidad) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement statementInsert = conexion.prepareStatement(sqlInsert)) {
                            statementInsert.setInt(1, idCarrito);
                            statementInsert.setInt(2, prod.getId());
                            statementInsert.setString(3, prod.getTalla());
                            statementInsert.setInt(4, prod.getCantidad());
                            statementInsert.executeUpdate();
                        }
                    }
                }
            }
        }
    }

    public void actualizarESTADOCarritoBD(String usuario, int idCarrito, String estado, String direccion) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ResultSet generatedKeys = null;//guarda las claves del id del pedido generado
        int idPedido = 0;//guarda el id del pedido

        String sqlUpdate = "UPDATE cestas SET estado = ? WHERE id_cesta = ?";
        PreparedStatement statementUpdate = null;

        try {
            statementUpdate = conexion.prepareStatement(sqlUpdate);
            statementUpdate.setString(1, estado);  // Primero el estado
            statementUpdate.setInt(2, idCarrito);  // Luego el ID de la cesta

            int rowsAffected = statementUpdate.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Cesta actualizada exitosamente.");
            } else {
                System.out.println("No se encontró la cesta con el ID especificado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar el PreparedStatement en el bloque finally
            if (statementUpdate != null) {
                try {
                    statementUpdate.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        if (estado.equals("Pagado")) {
            //insert into pedidos
            //HACE FALTA RECOGER DIRECCION
            String sqlInsertPedidos = "INSERT INTO pedidos (id_cesta, direccion, usuario) VALUES (?, ?, ?)";
            PreparedStatement pstmtPedidos = null;

            try {
                pstmtPedidos = conexion.prepareStatement(sqlInsertPedidos, Statement.RETURN_GENERATED_KEYS); //devuelve el id del pedido generado
                pstmtPedidos.setInt(1, idCarrito);
                pstmtPedidos.setString(2, direccion);
                pstmtPedidos.setString(3, usuario);

                int rowsAffected = pstmtPedidos.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Pedido insertado exitosamente.");
                    // si todo va bien se obtienen las claves
                    generatedKeys = pstmtPedidos.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        idPedido = generatedKeys.getInt(1);
                        System.out.println("ID del pedido generado: " + idPedido);
                        // Aquí puedes devolver el ID del pedido o hacer lo que necesites con él
                    } else {
                        System.out.println("No se pudo obtener el ID del pedido generado.");
                    }
                } else {
                    System.out.println("Error al insertar el pedido.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error al insertar el pedido: " + e.getMessage());
            } finally {
                // Cerrar el PreparedStatement en el bloque finally
                if (pstmtPedidos != null) {
                    try {
                        pstmtPedidos.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

            //insert into pagos
            String sqlInsertPagos = "INSERT INTO pagos (id_pedido) VALUES (?)";
            PreparedStatement pstmtPagos = null;

            try {
                pstmtPagos = conexion.prepareStatement(sqlInsertPagos);
                pstmtPagos.setInt(1, idPedido);

                int rowsAffected = pstmtPagos.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Pago insertado exitosamente.");
                } else {
                    System.out.println("Error al insertar el pago.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error al insertar el pago: " + e.getMessage());
            } finally {
                // Cerrar el PreparedStatement en el bloque finally
                if (pstmtPagos != null) {
                    try {
                        pstmtPagos.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (estado.equals("Enviado")) {
            //update pedidos añadir fecha de envio actual y calcular cuando se va a recibir
            Envio envio = new Envio();
            Timestamp fechaHoraEnvio = new Timestamp(System.currentTimeMillis());
            envio.setFecha_envio(fechaHoraEnvio);
            System.out.println("ENVIADO " + fechaHoraEnvio);

            envio.setFechaRecogida(fechaHoraEnvio);
            System.out.println("FECHA RECOGIDA " + envio.getFecha_llegada());

            String sqlUpdateEnvios = "UPDATE pedidos SET fecha_estimada = ?, fecha_envio = ? WHERE id_cesta = ?";
            //update pedidos 	fecha_estimada, fecha_envio	where id_cesta= idCarrito
            try {
                PreparedStatement pstmtEnvios = conexion.prepareStatement(sqlUpdateEnvios);
                // Convertir las fechas a Timestamp
                Timestamp timestampFechaRecogida = new Timestamp(envio.getFecha_llegada().getTime());
                Timestamp timestampFechaEnvio = new Timestamp(fechaHoraEnvio.getTime());

                System.out.println(" TIMESTAMP FECHA RECOGIDA " + timestampFechaRecogida);
                System.out.println("TIMESTAMP FECHA ENVIO " + timestampFechaEnvio);
                System.out.println(" ENVIO FECHA RECOGIDA " + envio.getFecha_llegada());
                System.out.println("ENVIO FECHA ENVIO " + envio.getFecha_envio());

                pstmtEnvios.setTimestamp(1, envio.getFecha_llegada());
                pstmtEnvios.setTimestamp(2, envio.getFecha_envio());
                pstmtEnvios.setInt(3, idCarrito);

                int filasActualizadas = pstmtEnvios.executeUpdate(); //ejecutar consultas
                if (filasActualizadas > 0) {
                    System.out.println("Se han actualizado " + filasActualizadas + " filas en la tabla pedidos.");
                } else {
                    System.out.println("No se ha actualizado ninguna fila en la tabla pedidos.");
                }
                pstmtEnvios.close(); //cerrar prepared statement
            } catch (SQLException ex) {
                // manejo de excepciones
                ex.printStackTrace();
            }

        }

    }

    public Carrito obtenerCarroBD(int idCarrito) throws SQLException {
        Carrito c = new Carrito(idCarrito, "carrito");
        ArrayList<Producto> productos = new ArrayList<>();
        String sql = "SELECT p.nombre AS nombre_producto, p.precio, p.imagen, cp.talla, cp.cantidad, cp.id_producto, cp.id_detalle "
                + "FROM cestas_productos cp "
                + "JOIN productos p ON cp.id_producto = p.id_producto "
                + "WHERE cp.id_cesta = ?";

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        System.out.println("ID CARRO " + idCarrito);
        try {
            statement = conexion.prepareStatement(sql);
            // Establecer el parámetro de ID del carrito
            statement.setInt(1, idCarrito);

            // Ejecutar la consulta
            resultSet = statement.executeQuery();

            // Procesar los resultados
            while (resultSet.next()) {
                // Obtener los datos del producto y del detalle del carrito
                String nombreProducto = resultSet.getString("nombre_producto");
                float precio = resultSet.getFloat("precio");
                String talla = resultSet.getString("talla");
                int cantidad = resultSet.getInt("cantidad");
                int idProducto = resultSet.getInt("id_producto");
                int idDetalle = resultSet.getInt("id_detalle");
                String imagen = resultSet.getString("imagen");

                // Construir un objeto Producto
                Producto producto = new Producto(idProducto, nombreProducto, talla, precio, cantidad, imagen);
                System.out.println(producto.toString());
                productos.add(producto);
            }

            c.setProductos(productos);
        } finally {
            // Cerrar el ResultSet y el PreparedStatement, pero no la conexión
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return c;
    }

    public ArrayList<Envio> obtenerPedidos() {
        ArrayList<Envio> pedidos = new ArrayList();
        String sql = "SELECT * FROM pedidos WHERE fecha_envio IS NULL;";

        try {
            Statement statement = conexion.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Envio envio = new Envio();
                envio.setId(resultSet.getInt("id_cesta")); // Suponiendo que id_cesta corresponde al id en tu clase Envio
                envio.setEstado("Pagado");
                envio.setUsuario(resultSet.getString("usuario"));
                envio.setDireccion(resultSet.getString("direccion"));

                // campos nulos
                envio.setFecha_envio(null);
                envio.setFecha_llegada(null);

                pedidos.add(envio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedidos;
    }

    public ArrayList<Envio> obtenerEnvios() {
        ArrayList<Envio> envios = new ArrayList();
        String sql = "SELECT * FROM pedidos WHERE fecha_envio IS NOT NULL;";

        try {
            Statement statement = conexion.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Envio envio = new Envio();
                envio.setId(resultSet.getInt("id_cesta"));
                envio.setEstado("Enviado");
                envio.setUsuario(resultSet.getString("usuario"));
                envio.setDireccion(resultSet.getString("direccion"));
                envio.setFecha_envio(resultSet.getTimestamp("fecha_envio"));
                envio.setFecha_llegada(resultSet.getTimestamp("fecha_estimada"));
                envios.add(envio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return envios;

    }

    public ArrayList<Envio> obtenerHistorialCompras(String email) {
        ArrayList<Envio> historial = new ArrayList<Envio>();
        //String sql = "SELECT * FROM pedidos WHERE usuario = ?";
        String sql = "SELECT pedidos.id_cesta, pedidos.fecha_estimada, pedidos.fecha_envio, productos.nombre AS nombre_producto, productos.precio, cestas_productos.id_producto, cestas_productos.talla, cestas_productos.cantidad " +
             "FROM pedidos " +
             "INNER JOIN cestas_productos ON pedidos.id_cesta = cestas_productos.id_cesta " +
             "INNER JOIN productos ON cestas_productos.id_producto = productos.id_producto " +
             "WHERE pedidos.usuario = ?;";
        
        try (
                PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            Timestamp fechaActual = new Timestamp(System.currentTimeMillis());

            Envio envio = null;
            int cestaAnterior = -1;

            while (resultSet.next()) {
                int idCesta = resultSet.getInt("id_cesta");

                // SI CAMBIA EL ID DE LA CESTA SE GENERA OTRO ENVIO CADA ENVIO ASOCIADO A UN ID CESTA
                if (idCesta != cestaAnterior) {
                    //si envio no es nulo guarda el envio anterior
                   if (envio != null) {
                        historial.add(envio);
                    }
                    envio = new Envio();
                    envio.setId(idCesta);
                    // envio.setUsuario(resultSet.getString("usuario")); // Assuming you have the user info
                    if (resultSet.getTimestamp("fecha_estimada") != null && resultSet.getTimestamp("fecha_envio") != null) {
                        envio.setFecha_envio(resultSet.getTimestamp("fecha_envio"));
                        envio.setFecha_llegada(resultSet.getTimestamp("fecha_estimada"));
                        if (resultSet.getTimestamp("fecha_estimada").before(fechaActual)) {
                            envio.setEstado("Entregado");
                        } else {
                            envio.setEstado("Enviado");
                        }
                    } else {
                        envio.setFecha_envio(null);
                        envio.setFecha_llegada(null);
                        envio.setEstado("En preparación");
                    }
                    cestaAnterior = idCesta;
                }

                // Crear el producto y añadirlo al envío
                Producto producto = new Producto();
                producto.setId(resultSet.getInt("id_producto"));
                producto.setNombre(resultSet.getString("nombre_producto"));
                producto.setPrecio(resultSet.getFloat("precio"));
                producto.setTalla(resultSet.getString("talla"));
                producto.setCantidad(resultSet.getInt("cantidad"));
                envio.getProductos().add(producto);
            }

            // añade el último envio a la lista
            if (envio != null) {
                historial.add(envio);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // manejo excepciones
        }
        return historial;
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
}
