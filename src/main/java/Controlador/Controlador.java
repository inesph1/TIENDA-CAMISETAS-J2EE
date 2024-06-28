/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controlador;

import Configuracion.CarritoDAO;
//import Configuracion.ConexionDAO;
import Configuracion.ProductoDAO;
import Modelo.Carrito;
import Modelo.Cesta;
import Modelo.Envio;
import Modelo.Producto;
import Modelo.Usuario;
//import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
//import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
//import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author ines
 */
@MultipartConfig //IMPORTANTE???
public class Controlador extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        System.out.println("Lo primero se ejecuta el controlador");
        RequestDispatcher dispatcher = null;

        HttpSession session = request.getSession(false); //obtiene la sesión si existe sin inicializarla
        ServletContext context = getServletContext();
        context.setAttribute("controlador", Boolean.TRUE);
//al pasar al controlador inicializa de nuevo el booleano que indica que se va a acceder a la siguiente pagina habiendo pasado por aqui
//se usa en el caso del index

        Usuario user;
        Carrito cesta = new Carrito();

        ProductoDAO pDAO = new ProductoDAO();
        CarritoDAO cDAO = new CarritoDAO();

        ArrayList<Producto> productosVenta = (ArrayList<Producto>) context.getAttribute("productosVenta");
        if (productosVenta == null) {
            productosVenta = new ArrayList<Producto>();
        }
        /*productosVenta = pDAO.obtenerTodosLosProductos(); //CARGAR LOS PRODUCTOS
        context.setAttribute("productosVenta", productosVenta); //guarda los productos en el usuario de contexto*/

        ArrayList<Cesta> listadoPedidos = null; //obtener todas las cestas cuyo estado sea pedido

        String url = "";
        String valor = request.getParameter("boton");
        String urlAnterior = "";

        System.out.println("VALOR BOTON 2" + valor);
        if (session != null) {
            user = (Usuario) session.getAttribute("objetoUsuario");
            System.out.println("USUARIO LOGGEADO");
        } else {
            user = null;
            System.out.println("USUARIO nulo");
        }

        //En la primera ejecucción carga el index si boton es nulo obtiene los productos t los carga en un atributo de contexto
        if (valor == null) {
            productosVenta = pDAO.obtenerTodosLosProductos(); //CARGAR LOS PRODUCTOS
            context.setAttribute("productosVenta", productosVenta); //guarda los productos en el usuario de contexto
            url = "Vista/Paginas/index.jsp";
        } else if (valor.equals("ACIDO")) {
             productosVenta = pDAO.obtenerTodosLosProductos(); //CARGAR LOS PRODUCTOS
            context.setAttribute("productosVenta", productosVenta); //guarda los productos en el usuario de contexto
            session.setAttribute("urlAnterior", "Vista/Paginas/index.jsp");
            url = "Vista/Paginas/index.jsp";
        } else if (valor.equals("ACIDO-PANEL")) {
              productosVenta = pDAO.obtenerTodosLosProductos(); //CARGAR LOS PRODUCTOS
            context.setAttribute("productosVenta", productosVenta); //guarda los productos en el usuario de contexto
            session.setAttribute("urlAnterior", "Vista/Paginas/perfilAdministrador.jsp");
            url = "Vista/Paginas/perfilAdministrador.jsp";
        } else if (user == null) {
            url = "Vista/Paginas/login.jsp";
        } else if (valor.equals("Guardar en la cesta")) {
            String string_producto_id = request.getParameter("producto_id");
            int producto_id = Integer.parseInt(string_producto_id);
            Producto auxiliar = pDAO.buscarProducto(productosVenta, producto_id);
            session.setAttribute("objetoProducto", auxiliar);
            session.setAttribute("urlAnterior", "Vista/Paginas/detalle_producto.jsp");
            url = "Vista/Paginas/detalle_producto.jsp";
        } else {
            switch (valor) {
                case "Guardar":
                    String valorFinalStr = request.getParameter("cantidadHidden");//cantidad actual del producto
                    String valorAccion = request.getParameter("accion"); //suma o resta?
                    String valorBaseStr = request.getParameter("baseHidden"); //valor base del carrito
                    //Parseos y calculo de diferencia 
                    int valorBase = parseInt(valorBaseStr);
                    int valorFinal = parseInt(valorFinalStr); //valor final del producto de la cesta parseado

                    int cantidadBD = valorBase - valorFinal;
                    if (cantidadBD < 0) {
                        cantidadBD = cantidadBD * (-1);
                    }

                    String talla = request.getParameter("hiddenTalla");
                    Producto producto = (Producto) session.getAttribute("nuevoProducto");
                    if (valorFinal >= 0 && !(talla.equals("")) && talla != null) {
                        //System.out.println("ENTRA EN EL IF PARA GUARDARLO");
                        producto.setCantidad(valorFinal);
                        //anadir producto al carro
                        user = (Usuario) session.getAttribute("objetoUsuario");
                        if (user != null) {
                            Carrito cestaUs = user.getCarrito();
                            cestaUs.sumarProductosCarro(producto);
                            //user.setCarrito(cestaUs); CREO NO ES NECESARIO
                            System.out.println("-----------SIZE CESTA--------------" + cestaUs.getProductos().size());
                            session.setAttribute("objetoUsuario", user);
                            pDAO.actualizarCantidadBD(producto.getId(), valorAccion, cantidadBD);
                            //ANADIDO RECIENTEMENTE
                            productosVenta = pDAO.obtenerTodosLosProductos(); //CARGAR LOS PRODUCTOS
                            context.setAttribute("productosVenta", productosVenta);

                            try {
                                cDAO.actualizarCarritoBD(user.getIdCarrito(), producto);
                                user.setCarrito(cDAO.obtenerCarroBD(user.getIdCarrito()));
                            } catch (SQLException ex) {
                                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }

                    url = "Vista/Paginas/detalle_producto.jsp";
                    break;
                case "Mi Perfil":
                    //redirige al formulario para loggear
                    break;
                case "Modificar Productos":
                    //redirige al formulario para loggear
                    url = "Vista/Paginas/edicionProductos.jsp";
                    break;
                case "Guardar cambios":
                    System.out.println("GUARDAR CAMBIOS");
                    String pIdstr = request.getParameter("pId");
                    String pNombre = request.getParameter("pNombre");
                    String pTalla = request.getParameter("pTalla");
                    if (pTalla == null) {
                        pTalla = "Unisex";
                    }
                    String pPreciostr = request.getParameter("pPrecio");
                    String pCantidadstr = request.getParameter("pCantidad");
                    boolean pAlta = request.getParameter("pAlta") != null;
                    if (pIdstr == null || pIdstr.trim().isEmpty() || pNombre == null || pNombre.trim().isEmpty() || pTalla == null || pTalla.trim().isEmpty()
                            || pPreciostr == null || pPreciostr.trim().isEmpty() || pCantidadstr == null || pCantidadstr.trim().isEmpty()) {
                        request.setAttribute("error", "No es posible actualizar a campos vacios");
                    } else {
                        // System.out.println("ALTA" + pAlta);
                        int pId = Integer.parseInt(pIdstr);
                        float pPrecio = Float.parseFloat(pPreciostr);
                        int pCantidad = Integer.parseInt(pCantidadstr);

                        Producto pAux = new Producto(pId, pNombre, pTalla, pPrecio, pCantidad, pAlta);
                        //SE CAMBIA EL METODO modificar PRODUCTO PARA QUE SOLO CIERRE EL PREPARED STATEMENT
                        pDAO.modificarProducto(pAux);
                        //ACTUALIZAR ATRIBUTO DE CONTEXTO CON LOS CAMBIOS
                        productosVenta = pDAO.obtenerTodosLosProductos(); //CARGAR LOS PRODUCTOS Y CIERRA LA CONEXION
                        context.setAttribute("productosVenta", productosVenta); //guarda los productos en el usuario de contexto*/
                    }
                    request.setAttribute("error", "Producto modificado con éxito");
                    url = "Vista/Paginas/edicionProductos.jsp";
                    break;
                case "Nuevo Producto":
                    url = "Vista/Paginas/nuevo_producto.jsp";
                    break;
                case "Guardar producto":
                    // Lógica de subida de archivos
                    System.out.println("--------------------GUARDAR PRODUCTO-------------------- ");
                    String nombreProducto = request.getParameter("pNombre");
                    pPreciostr = request.getParameter("pPrecio");
                    pCantidadstr = request.getParameter("pCantidad");
                    talla = request.getParameter("pTalla");
                    if (talla == null) {
                        talla = "Unisex";
                    }// aunque está deshabilitado, se envía el valor
                    boolean alta = request.getParameter("pAlta") != null ? true : false; //si es null guarda un false
                    url = "";
                    if (nombreProducto == null || nombreProducto.trim().isEmpty() || talla == null || talla.trim().isEmpty() || pPreciostr == null || pPreciostr.trim().isEmpty()
                            || pCantidadstr == null || pCantidadstr.trim().isEmpty()) {
                        request.setAttribute("error", "Por favor, rellene todos los campos");
                    } else {
                        float precio = Float.parseFloat(pPreciostr);
                        int cantidad = Integer.parseInt(pCantidadstr);
                        System.out.println(nombreProducto + "" + talla + "" + precio + "" + cantidad + "" + alta);

                        if (request.getContentType() != null && request.getContentType().startsWith("multipart/form-data")) {
                            String uploadDir = getServletContext().getRealPath("/") + "../../src/main/webapp/Vista/Recursos/imagenes/";
                            // System.out.println("Directorio de subida: " + uploadDir);  
                            File uploadDirFile = new File(uploadDir);
                            if (!uploadDirFile.exists()) {
                                System.out.println("DIRECTORIO NO EXISTE LO CREA");
                                uploadDirFile.mkdirs();
                            }

                            Part filePart = request.getPart("file");
                            if (filePart != null) {
                                String contentDisposition = filePart.getHeader("content-disposition");
                                String fileName = "";
                                if (contentDisposition != null) {
                                    String[] parts = contentDisposition.split(";");
                                    for (String part : parts) {
                                        if (part.trim().startsWith("filename")) {
                                            fileName = part.substring(part.indexOf('=') + 1).trim().replace("\"", "");
                                            fileName = Paths.get(fileName).getFileName().toString();
                                            url = "./Vista/Recursos/imagenes/" + fileName;
                                        }
                                    }
                                }
                                if (!fileName.isEmpty()) {
                                    File file = new File(uploadDirFile, fileName);
                                    try (InputStream fileContent = filePart.getInputStream()) {
                                        Files.copy(fileContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                                        request.setAttribute("error", "Archivo subido exitosamente: " + fileName);
                                    } catch (IOException e) {
                                        request.setAttribute("error", "Error al subir el archivo: " + e.getMessage());
                                    }
                                } else {
                                    request.setAttribute("error", "No se ha podido determinar el nombre del archivo.");
                                }
                            } else {
                                request.setAttribute("error", "No se ha seleccionado ningún archivo.");
                            }
                        }
                        Producto nuevo = new Producto(nombreProducto, talla, precio, cantidad, url, alta);
                        //SE CAMBIA EL METODO NUEVO PRODUCTO PARA QUE SOLO CIERRE EL PREPARED STATEMENT
                        //ACTUALIZAR ATRIBUTO DE CONTEXTO CON LOS CAMBIOS EL METODO OBTENER TODOS LOS PRODUCTOS CIERRA LA CONEXIÓN DE PDAO
                        pDAO.nuevoProducto(nuevo);
                        productosVenta = pDAO.obtenerTodosLosProductos(); //CARGAR LOS PRODUCTOS
                        context.setAttribute("productosVenta", productosVenta); //guarda los productos en el usuario de contexto*/
                    }
                    url = "Vista/Paginas/nuevo_producto.jsp";
                    break;
                case "Ver Vista Usuario":
                    url = "Vista/Paginas/index.jsp";
                    break;
                case "Administrar":
                    System.out.println("Entra en el case");
                    url = "Vista/Paginas/perfilAdministrador.jsp";
                    break;
                case "Pagar":
                    url = "Vista/Paginas/pasarelaPago.jsp";
                    break;

                case "ConfirmarPago":
                    System.out.println("CONFIRMAR PAGO");
                    user = (Usuario) session.getAttribute("objetoUsuario");

                    //OBTENER LA DIRECCION DEL FORMULARIO de Paypal
                    String pDireccion = request.getParameter("direccion");
                    String feedbackEncoded = request.getParameter("feedback");
                    System.out.println(feedbackEncoded); //los espacios vienen codificados con porcentajes
                    String feedback = java.net.URLDecoder.decode(feedbackEncoded, "UTF-8"); // decodifica el valor de feedback
                    request.setAttribute("feedback", feedback);
                    //System.out.println("DIRECCION " + pDireccion);
                    if (user != null) {
                        Carrito cestaUs = user.getCarrito();
                        cestaUs.setEstado("Pagado");

                        try {
                            cDAO.actualizarESTADOCarritoBD(user.getEmail(), user.getIdCarrito(), cestaUs.getEstado(), pDireccion);
                        } catch (SQLException ex) {
                            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        cestaUs = new Carrito("carrito");
                        cestaUs.setId(cDAO.nuevaCesta(cestaUs)); //nueva cesta retorna el id de la cesta de la base de datos
                        cDAO.actualizarIDCestaUsuario(user.getEmail(), cestaUs.getId());
                        user.setCarrito(cestaUs);
                        session.setAttribute("objetoUsuario", user);
                    }
                    url = "Vista/Paginas/index.jsp";
                    break;
                case "Pedidos Pendientes":
                    ArrayList<Envio> pedidosPendientes = new ArrayList();
                    pedidosPendientes = cDAO.obtenerPedidos();
                    for (Envio envio : pedidosPendientes) {
                        Carrito carro = new Carrito();
                        try {
                            carro = cDAO.obtenerCarroBD(envio.getId());
                        } catch (SQLException ex) {
                            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        envio.setProductos(carro.getProductos());
                    }
                    session.setAttribute("pedidos", pedidosPendientes);
                    url = "Vista/Paginas/pedidos.jsp";
                    break;

                case "Envios":
                    ArrayList<Envio> envios = new ArrayList();
                    envios = cDAO.obtenerEnvios();
                    session.setAttribute("envios", envios);
                    url = "Vista/Paginas/envios.jsp";
                    break;
                case "Marcar como Enviado":
                    String cestaId = request.getParameter("cesta_id");
                    int idCesta = Integer.parseInt(cestaId);

                     {
                        try {
                            cDAO.actualizarESTADOCarritoBD("", idCesta, "Enviado", "");
                            pedidosPendientes = cDAO.obtenerPedidos();
                            for (Envio envio : pedidosPendientes) {
                                Carrito carro = new Carrito();
                                try {
                                    carro = cDAO.obtenerCarroBD(envio.getId());
                                } catch (SQLException ex) {
                                    Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                envio.setProductos(carro.getProductos());
                            }
                            session.setAttribute("pedidos", pedidosPendientes);
                        } catch (SQLException ex) {
                            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    url = "Vista/Paginas/pedidos.jsp";
                    break;
                case "Inicio":
                    url = "Vista/Paginas/index.jsp";
                    break;
                case "Volver":
                    System.out.println("VOLVER");
                    String valorOculto = request.getParameter("volverVistaAdmin");
                    if (user.getRol() == 1 && valorOculto != null) {
                        System.out.println("ENTRA AQUI 2");
                        url = "Vista/Paginas/perfilAdministrador.jsp";
                    } else {
                        System.out.println("ENTRA AQUI");
                        url = "Vista/Paginas/index.jsp";
                    }
                    break;
                case "Cancelar":
                    //VOLVER AL PERFIL O A DETALLE PRO
                    break;
                case "Carrito":
                    Map<Integer, Integer> maximos = new HashMap<>();
                    ArrayList<Producto> carrito = user.getCarrito().getProductos();
                    for (Producto p : carrito) {
                        int maximoProducto = pDAO.obtenerCantidadMaxima(p.getId()); // Implementa la lógica para obtener el máximo de la base de datos
                        maximos.put(p.getId(), maximoProducto);
                    }
                    pDAO.close();
                    request.setAttribute("maximos", maximos);
                    url = "Vista/Paginas/carrito.jsp";
                    break;
                case "Actualizar":
                case "Actualizar cambios y pagar":
                    System.out.println("MOSTRAR LO QUE RECIBE");
                    Map<Integer, Integer> productosMapa = new HashMap<>();
                    String[] idProductos = request.getParameterValues("id_producto_hidden");
                    String[] cantidades = request.getParameterValues("cantidad_hidden");

                    user = (Usuario) session.getAttribute("objetoUsuario");
                    if (user != null) {
                        cesta = user.getCarrito();

                        if (idProductos != null && cantidades != null && idProductos.length == cantidades.length) {
                            for (int i = 0; i < idProductos.length; i++) {
                                try {
                                    int id = Integer.parseInt(idProductos[i]);
                                    int cantidad = Integer.parseInt(cantidades[i]);
                                    productosMapa.put(id, cantidad);
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            System.out.println("Error: Los arrays de IDs y cantidades no coinciden en longitud.");
                        }

                        for (Map.Entry<Integer, Integer> entry : productosMapa.entrySet()) {
                            int id = entry.getKey();
                            int nuevaCantidad = entry.getValue();
                            int cantidadAnterior = 0;

                            producto = new Producto();
                            producto.setId(id);

                            if (cesta.buscarProducto(producto)) { //encuentra el producto en la cesta lo obtiene
                                producto = cesta.obtenerProducto(producto); //sobreescribe el producto por el de la cesta
                                cantidadAnterior = producto.getCantidad();
                                producto.setCantidad(nuevaCantidad);

                                int datoActualizado = cantidadAnterior - nuevaCantidad;
                                //ACTUALIZA EL CARRO EN LA BD
                                pDAO.actualizarCantidadProducto(id, datoActualizado);
                                try {
                                    cDAO.actualizarCarritoBD(user.getIdCarrito(), producto);
                                } catch (SQLException ex) {
                                    Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }
                     {
                        //OBTIENE EL CARRO DE LA BD ACTUALIZADA ANTES DE VOLVER AL JSP 
                        try {
                            System.out.println("ID CARRO SEGUN USUARIO" + user.getIdCarrito());
                            user.setCarrito(cDAO.obtenerCarroBD(user.getIdCarrito()));
                        } catch (SQLException ex) {
                            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    //ACTUALIZA LOS MAXIMOS
                    maximos = new HashMap<>();
                    carrito = user.getCarrito().getProductos();
                    for (Producto p : carrito) {
                        int maximoProducto = pDAO.obtenerCantidadMaxima(p.getId()); // Implementa la lógica para obtener el máximo de la base de datos
                        maximos.put(p.getId(), maximoProducto);
                    }
                    pDAO.close();
                    request.setAttribute("maximos", maximos);
                    //ACTUALIZA LA SESION DEL USUARIO CON LOS NUEVOS CAMBIOS EN EL CARRITO
                    session.setAttribute("objetoUsuario", user);

                    if (valor.equals("Actualizar")) {
                        url = "Vista/Paginas/carrito.jsp";
                    } else {
                        {
                            try {
                                if (cDAO.obtenerCarroBD(user.getIdCarrito()).getProductos().size() <= 0) {
                                    System.out.println("No hay productos");
                                    request.setAttribute("error", " Debes seleccionar al menos un producto para realizar el pago ");
                                    url = "Vista/Paginas/carrito.jsp";
                                } else {
                                    url = "Vista/Paginas/pasarelaPago.jsp";
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }

                    break;

                default:
                    // out.println("<h1>NR</h1>");
                    break;
            }

        }
        dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
