<%-- 
    Document   : perfiln
    Created on : 3 jun 2024, 15:32:53
    Author     : ines
--%>

<%@page import="Modelo.Producto"%>
<%@page import="Modelo.Usuario"%>
<%@page import="Modelo.Envio"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mi perfil</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <%
            ServletContext context = getServletContext();
            String accion = request.getParameter("boton");
            HttpSession sessionuser = request.getSession(false);
            if(sessionuser!=null){
            Usuario user = (Usuario) sessionuser.getAttribute("objetoUsuario");
            String nombre = "";
            int rol = 0;
            if (user != null) {
                nombre = user.getNombre();
                rol = user.getRol();
           
            String error = (String) request.getAttribute("error");
            if (error == null) {
                error = "";
            }
            String url = "";
            String cierre = "";
            if (accion.equals("Mi perfil") || accion.equals("Cambiar") || accion.equals("Actualizar")) {
                url = "./Vista/Paginas/";
            } else if (accion.equals("Mis Compras") || accion.equals("Datos Personales") || accion.equals("Cambiar contrasena") || accion.equals("Cambiar datos")) {
                cierre = "./../../";
            }

            System.out.println("El boton pulsado es " + accion);
            System.out.println("ruta " + url);

            boolean compras = accion.equals("Mi perfil") || accion.equals("Mis Compras");
            boolean datos = accion.equals("Datos Personales") || accion.equals("Actualizar") || accion.equals("Cambiar contrasena") || accion.equals("Cambiar datos");
            String activeC = "";
            String activeD = "";
            if (datos) {
                activeD = "active";
            } else {
                activeC = "active";
            }

        %>
        <script src="<%=cierre%>./Vista/Scripts/ScriptToggle.js"></script>
        <script src="<%=cierre%>./Vista/Scripts/validacionPassword.js"></script>
        <link rel="icon" href="<%=cierre%>./Vista/Recursos/imagenes/tabNavegadorBlanco.png" type="image/png">
        <link href="<%=cierre%>./Vista/Recursos/Estilos/estilosComunesUsuario.css" rel="stylesheet">
        <style>
            /* Estilos del Tab */
            .tab-nav-button {
                background: none;
                border: none;
                padding: .5rem 1rem;
                cursor: pointer;
                color: #007bff;
                font-size: 1rem;
                border: 1px solid transparent;
                border-top-left-radius: .25rem;
                border-top-right-radius: .25rem;
                transition: background-color 0.3s ease, color 0.3s ease;
            }

            .tab-nav-button:hover,
            .tab-nav-button:focus {
                color: #0056b3;
                text-decoration: none;
            }

            .tab-nav-button.active {
                /*color: #007bff;*/
                /*background-color: #e9ecef;*/
                color:#343a40;
                background-color: buttonface;
                border-color: #dee2e6 #dee2e6 #fff;
            }

            .tab-nav-button:focus {
                outline: none;
                box-shadow: none;
            }

            .tab-nav-item {
                margin-bottom: -1px;
            }

            .container {
                padding-top: 100px;
            }

            .productos {
                border: 1px solid #6C757D; /* Gris predeterminado */
            }
            .productos.show {
                border-color: #FFC107; /* Amarillo visible cuando está desplegado */
            }

            .form-container {
                max-width: 400px;
                margin: 0 auto;
            }
            .form-container input {
                height: 30px;
                /* font-size: 14px;*/
            }
        </style>
    </head>
    <body>

    <body>
        <nav class="navbar navbar-expand-lg navbar-custom">
            <form action="<%=cierre%>Controlador" method="POST" class="navbar-brand"> 
                <button type="submit" name="boton" value="ACIDO" class="navbar-brand">ÁCIDO</button>
            </form>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                    aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <% if (!nombre.isEmpty()) {%>
                    <li class="nav-item">
                        <form action="<%=cierre%>Controlador" method="POST" class="nav-link-form nav-link">
                            <input name="boton" value="Carrito" type="submit" class="btn btn-link nav-link">
                        </form>
                    </li>
                    <% }%>
                </ul>
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item">
                        <form action="<%=cierre%>ValidarUsuario" method="POST" class="nav-link-form nav-link">
                            <input name="boton" value="Mi perfil" type="submit" class="btn btn-link nav-link">
                        </form>
                    </li>
                    <li class="nav-item">
                        <form action="<%=cierre%>ValidarUsuario" method="POST" class="nav-link-form nav-link">
                            <input name="boton" value="Cerrar sesion" type="submit" class="btn btn-link nav-link">
                        </form>
                    </li>
                    <% if (rol == 1) {%>
                    <li class="nav-item">
                        <form action="<%=cierre%>Controlador" method="POST">
                            <input name="boton" value="Administrar" type="submit" class="btn btn-admin panel-admin-btn">
                        </form>
                    </li>
                    <% }%>
                </ul>
            </div>
        </nav>
        <div class="container">
            <form action="<%=url%>perfil.jsp" method="POST" class="nav-link-form nav-link">
                <ul class="nav nav-tabs" id="myTab" role="tablist">
                    <li class="nav-item" role="presentation">
                        <button name="boton" value="Mis Compras" type="submit" class="tab-nav-button <%=activeC%>" id="mis-compras-tab" data-toggle="tab" role="tab" aria-controls="mis-compras" aria-selected="<%=compras%>">Mis Compras</button>
                    </li>
                    <li class="nav-item" role="presentation">
                        <button name="boton" value="Datos Personales" type="submit" class="tab-nav-button <%=activeD%>"  id="datos-personales-tab" data-toggle="tab" role="tab" aria-controls="datos-personales" aria-selected="<%=datos%>">Datos Personales</button>
                    </li>
                </ul>
            </form>

            <div class="tab-content" id="myTabContent">
                <!-- Contenido de Mis Compras -->
                <div class="tabla-contenedor">


                    <% if (accion.equals("Mi perfil") || accion.equals("Mis Compras")) {%>

                    <% ArrayList<Envio> historial = (ArrayList<Envio>) context.getAttribute("historial");
                        if (historial != null && !historial.isEmpty()) { %>
                    <%
                        for (Envio envio : historial) {
                            float precioTotal = 0;
                    %>

                    <%  String fechaEnvio = (envio.getFecha_envio() != null) ? (envio.getFecha_envio().toString()).substring(0, 10) : "-";
                        String fechaLlegada = (envio.getFecha_llegada() != null) ? (envio.getFecha_llegada().toString()).substring(0, 10) : "-";
                        ArrayList<Producto> productos = envio.getProductos();
                        for (Producto prod : productos) {
                            precioTotal += prod.getPrecio() * prod.getCantidad();
                        }
                    %>
                    <div class="card mb-3 mt-3" onclick="toggleProductos('<%= envio.getId()%>')">
                        <div class="card-body">
                            <h5 class="card-title text-left"><%= envio.getEstado()%></h5>
                            <p class="card-title text-right">LOCALIZADOR: <%= envio.getId()%></p>
                            <p class="card-text">Envío: <%= fechaEnvio%></p>
                            <p class="card-text">Fecha llegada: <%= fechaLlegada%></p>
                        </div>
                    </div>
                    <div class="productos card mb-3" id="productos_<%= envio.getId()%>" style="display: none;">
                        <div class="card-body">
                            <h5 class="card-title">Productos</h5>
                            <% for (Producto prod : productos) {
                            %>
                            <p class="card-text"><%= prod.getCantidad()%> X <%= prod.getNombre()%> Talla: <%= prod.getTalla()%></p>
                            <% }%>
                            <p class="card-text text-right">Precio: <%= String.format("%.2f", precioTotal)%>€</strong></p>
                            <p class="card-text text-right">IVA: <%=  String.format("%.2f", precioTotal * 0.21)%>€</strong></p>
                            <p class="card-text text-right"><strong>PAGADO: <%= String.format("%.2f", precioTotal + (precioTotal * 0.21))%>€</strong></p>
                            <form action="Controlador" method="POST">
                                <input type="hidden" name="cesta_id" value="<%= envio.getId()%>">
                            </form>
                        </div>
                    </div>
                    <% } %>
                    <% } else { %>
                    <p>No hay historial de compras disponible.</p>
                    <% }%>
                    <% } %>

                </div>


                <div class="formulario-datos">
                    <% if (accion.equals("Datos Personales") || accion.equals("Actualizar")) {%>
                    <% if (!error.isEmpty()) {%>
                    <p class="error-message error-background"><%= error%></p>
                    <% } else {%>
                    <p class="error-message"><%= error%></p>
                    <% }%>

                    <form action="<%=url%>perfil.jsp" method="POST">
                        <input name="boton" value="Cambiar datos" type="submit" class="btn btn-custom">
                        <input name="boton" value="Cambiar contrasena" type="submit" class="btn btn-custom">
                        <div class="container">
                            <div class="form-container">
                                <% if (user != null) {%>
                                <h5 class="card-title">Información del Usuario</h5>
                                <div class="card">

                                    <div class="card-body">

                                        <p class="card-text"><strong>Nombre:</strong> <%= user.getNombre()%></p>
                                        <p class="card-text"><strong>Apellidos:</strong> <%= user.getApellidos()%></p>
                                        <p class="card-text"><strong>Email:</strong> <%= user.getEmail()%></p>
                                        <p class="card-text"><strong>Dirección:</strong> <%= (user.getDireccion() != null && !user.getDireccion().isEmpty()) ? user.getDireccion() : "No hay dirección disponible"%></p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <% } else { %>
                        <p>Ocurrió un error al cargar el usuario</p>
                        <% } %>
                    </form>
                    <% } else if (accion.equals("Cambiar contrasena") || accion.equals("Cambiar")) {%>
                    <% if (!error.isEmpty()) {%>
                    <p class="error-message error-background"><%= error%></p>
                    <% } else {%>
                    <p class="error-message"><%= error%></p>
                    <% }%>
                    <div class="form-container mt-5">
                        <form action="<%=url%>../../ValidarUsuario" method="POST"  onsubmit="return validarContrasena()">
                            <p id="error" class="error-message text-center"></p>
                            <h5>Modificación de contraseña</h5>
                            <div class="mb-3">
                                <label for="txtNombre" class="form-label">Introduzca su contraseña actual</label>
                                <input type="password" name="txtpassActual" placeholder="Contraseña actual" required class="form-control" id="txtpassActual">
                            </div>
                            <div class="mb-3">
                                <label for="txtpassNueva" class="form-label">Introduzca su nueva contraseña</label>
                                <input type="password"  id="contrasena" name="txtpassNueva" placeholder="Nueva Contraseña" required class="form-control" >
                            </div>
                            <div class="mb-3">
                                <label for="txtpassNuevaRP" class="form-label">Repita su nueva contraseña</label>
                                <input type="password"  name="txtpassNuevaRP" placeholder="Repita Contraseña" required class="form-control" id="txtpassNuevaRP">
                            </div>
                            <div class="text-right">
                                <button type="submit" class="btn btn-custom" name="boton" value="Cambiar"">Cambiar</button>
                            </div>
                        </form>
                        <% } else if (accion.equals("Cambiar datos")) {%>

                        <div class="form-container mt-5">
                            <form action="<%=url%>../../ValidarUsuario" method="POST" class="form-group">
                                <h5>Modificación de datos personales</h5>
                                <div class="mb-3">
                                    <label for="txtNombre" class="form-label">Nombre</label>
                                    <input type="text" class="form-control" id="txtNombre" name="txtNombre" placeholder="<%= user.getNombre()%>">
                                </div>
                                <div class="mb-3">
                                    <label for="txtApellidos" class="form-label">Apellidos</label>
                                    <input type="text" class="form-control" id="txtApellidos" name="txtApellidos" placeholder="<%= user.getApellidos()%>">
                                </div>
                                <div class="mb-4">
                                    <label for="txtDireccion" class="form-label">Dirección</label>
                                    <input type="text" class="form-control" id="txtDireccion" name="txtDireccion" placeholder="<%= (user.getDireccion() != null && !user.getDireccion().isEmpty()) ? user.getDireccion() : "Aún no has introducido ninguna dirección"%>">
                                </div>
                                <div class="text-right">
                                    <button type="submit" class="btn btn-custom" name="boton" value="Actualizar">Actualizar</button>
                                </div>
                            </form>
                        </div>
                        <% }%>
                    </div>
                </div>
            </div>
                    <% }else{%><p class="text-danger text-center">ACCESO NO AUTORIZADO</p>
<%}}%>
    </body>
</html>
