/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controlador;

import Configuracion.CarritoDAO;
import Configuracion.ConexionDAO;
import Configuracion.Hash;
//import Configuracion.ProductoDAO;
import Configuracion.UsuarioDAO;
import Modelo.Carrito;
import Modelo.Envio;
import Modelo.Usuario;
import java.io.IOException;
//import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ines
 */
public class ValidarUsuario extends HttpServlet {

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

        RequestDispatcher dispatcher = null;
        //HttpSession session;
        HttpSession session = request.getSession(false);//CAMBIADO IGUAL DA ERROR
        ConexionDAO conexion = new ConexionDAO();
        Connection conn = conexion.conectarBD();
        UsuarioDAO userDAO = new UsuarioDAO();
        Usuario auxiliar = new Usuario();
        //CarritoDAO carritoDAO = new CarritoDAO(conexion, conn);

        ServletContext context = getServletContext();
        context.setAttribute("controlador", Boolean.TRUE);

        Hash encriptar = new Hash();
        String valor = request.getParameter("boton");
        String url = "";
        String email = "";
        String pass = "";
        String nombre = "";
        String passE = "";
        System.out.println(" --------------------VALIDAR USUARIO------------------ ");
        System.out.println(" BOTON " + valor);

        switch (valor) {
            case "Registrarse":
                System.out.println("REGISTRARSE ");
                nombre = request.getParameter("txtnombre");
                String apellidos = request.getParameter("txtapellidos");
                email = request.getParameter("txtemail");
                pass = request.getParameter("txtpass");

                if (nombre == null || nombre.trim().isEmpty() || apellidos == null || apellidos.trim().isEmpty() || email == null || email.trim().isEmpty()
                        || pass == null || pass.trim().isEmpty()) {
                    request.setAttribute("error", "Todos los campos son obligatorios.");
                    url = "Vista/Paginas/registro.jsp";
                } else {
                    //BUSCAR SI ENCUENTRA EL USUARIO SI LO ENCUENTRA NO REGISTRA SI NO, LO REGISTRA
                    passE = encriptar.hashear(pass);
                    if (!(userDAO.existe(email))) {
                        Carrito c = new Carrito();
                        Usuario user = new Usuario(2, nombre, apellidos, email, passE, c);
                        System.out.println(user);
                        userDAO.registrarUsuario(user);
                        request.setAttribute("error", "USUARIO REGISTRADO CORRECTAMENTE");
                        System.out.println("El USUARIO NO EXISTE ");
                        url = "Vista/Paginas/login.jsp";
                    } else {
                        request.setAttribute("error", "El USUARIO YA EXISTE ");
                        System.out.println("El USUARIO EXISTE ");
                        url = "Vista/Paginas/registro.jsp";
                    }
                }
                break;
            case "Iniciar sesion":
                //Comprueba credenciales
                email = request.getParameter("txtemail");
                pass = request.getParameter("txtpass");
                System.out.println("ENTRA INICIAR SESION");
                if (email == null || email.trim().isEmpty() || pass == null || pass.trim().isEmpty()) {
                    request.setAttribute("error", "Todos los campos son obligatorios.");
                    url = "Vista/Paginas/login.jsp";
                } else {
                    if (userDAO.existe(email)) {
                        auxiliar = userDAO.obtenerUsuario(email);
                        System.out.println("EXISTE USUARIO");
                        if (encriptar.comparar(pass, auxiliar)) {
                            System.out.println("ENTRA HASEAR Y COMPARAR");
                            try {
                                session = request.getSession(true);
                                //obtener la cesta y guardarla
                                CarritoDAO cDAO = new CarritoDAO();
                                Carrito c = new Carrito();
                                c = cDAO.obtenerCarroBD(auxiliar.getIdCarrito());
                                cDAO.close();
                                System.out.println("HA OBTENIDO EL CARRITO");
                                System.out.println(c);
                                System.out.println(c.getProductos().size());
                                auxiliar.setCarrito(c);
                                System.out.println("HA GUARDADO EN EL CARRITO EL CARRITO");
                                session.setAttribute("objetoUsuario", auxiliar);

                                if (auxiliar.getRol() == 1) {
                                    url = "Vista/Paginas/perfilAdministrador.jsp";
                                } else {
                                    url = "Vista/Paginas/index.jsp";
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(ValidarUsuario.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            request.setAttribute("error", " USUARIO/CONTRASENA INCORRECTAS ");
                            System.out.println(" USUARIO/CONTRASENA INCORRECTAS ");
                            url = "Vista/Paginas/login.jsp";
                        }

                    } else {
                        request.setAttribute("error", " USUARIO/CONTRASENA INCORRECTAS ");
                        url = "Vista/Paginas/login.jsp";
                    }
                }
                //1. existe
                //2.obtener
                //3.comparar contraseñas
                break;
            case "Log in":
                //redirige al formulario para loggear
                url = "Vista/Paginas/login.jsp";
                break;

            case "Cerrar sesion":
                session = request.getSession(false); // obtener la sesión actual, sin crear una nueva
                if (session != null) {
                    session.invalidate(); // invalidar la sesión
                    System.out.println("Cerrar sesion");
                }
                url = "Vista/Paginas/index.jsp";
                break;

            case "Mi perfil":
                session = request.getSession(false);
                auxiliar = (Usuario) session.getAttribute("objetoUsuario");
                if (auxiliar != null) {
                    CarritoDAO cDAO = new CarritoDAO();
                    ArrayList<Envio> historial = cDAO.obtenerHistorialCompras(auxiliar.getEmail());
                    context.setAttribute("historial", historial);
                     url = "Vista/Paginas/perfil.jsp";
                    //url = "Vista/Paginas/perfilBKP.jsp";
                } else {
                    url = "Vista/Paginas/index.jsp";
                }
                break;

            case "Cambiar":
                System.out.println("Llega al controlador");
                session = request.getSession(false);
                auxiliar = (Usuario) session.getAttribute("objetoUsuario");
                String txtpassActual = request.getParameter("txtpassActual");
                String txtpassNueva = request.getParameter("txtpassNueva");
                String txtpassNuevaRP = request.getParameter("txtpassNuevaRP");

                if (txtpassActual == null || txtpassActual.trim().isEmpty() || txtpassNueva == null || txtpassNueva.trim().isEmpty() || txtpassNuevaRP == null || txtpassNuevaRP.trim().isEmpty()) {
                    request.setAttribute("error", "Por favor, debe rellenar los campos con caracteres válidos");
                } else {
                    if (encriptar.comparar(txtpassActual, auxiliar)) {
                        if (txtpassNueva.equals(txtpassNuevaRP)) {
                            passE = encriptar.hashear(txtpassNueva);
                            userDAO.actualizarContrasena(auxiliar, passE);
                            request.setAttribute("error", "Contraseña cambiada");
                            System.out.println("Contraseña cambiada");
                        } else {
                            request.setAttribute("error", "Las nuevas contraseñas no coinciden");
                            System.out.println("Las nuevas contraseñas no coinciden");
                        }
                    } else {
                        request.setAttribute("error", "Contraseña incorrecta");
                        System.out.println("Contraseña incorrecta");
                    }
                }
                //url = "Vista/Paginas/perfilBKP.jsp";
                url = "Vista/Paginas/perfil.jsp";
                break;
            case "Actualizar":
                System.out.println("Llega al controlador");
                nombre = request.getParameter("txtNombre");
                apellidos = request.getParameter("txtApellidos");
                String direccion = request.getParameter("txtDireccion");
                auxiliar = (Usuario) session.getAttribute("objetoUsuario");
                //System.out.println(auxiliar.getEmail());
                userDAO.actualizarDatosUsuario(nombre, apellidos, direccion, auxiliar.getEmail());
                auxiliar = userDAO.obtenerUsuario(auxiliar.getEmail()); //actualizar usuario
                session.setAttribute("objetoUsuario", auxiliar); //guardar el usuario en la sesion
                request.setAttribute("error", "DATOS MODIFICADOS CORRECTAMENTE");
               // url = "Vista/Paginas/perfilBKP.jsp";
                 url = "Vista/Paginas/perfil.jsp";
                break;
            default:
                System.out.println(" DEFAULT ");
                url = "Vista/Paginas/registro.jsp";
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
