<%-- 
    Document   : pedidos
    Created on : 2 jun 2024, 12:12:46
    Author     : ines
--%>

<%@page import="Modelo.Usuario"%>
<%@page import="Configuracion.UsuarioDAO"%>
<%@page import="Modelo.Envio"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Modelo.Producto"%>
<%@page import="Modelo.Producto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pedidos pendientes de envío</title>
        <link rel="icon" href="./Vista/Recursos/imagenes/tabNavegador.png" type="image/png">
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <link href="./Vista/Recursos/Estilos/estilosComunesAdministrador.css" rel="stylesheet">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script src="./Vista/Scripts/ScriptToggle.js"></script>
        <style>
            .productos {
                border: 1px solid #6C757D; /* Gris predeterminado */
            }
            .productos.show {
                border-color: #FFC107; /* Amarillo visible cuando está desplegado */
            }
                                .no-autorizado {
            background-color: #343a40;
            color: white;
             font-family: 'Sofia', sans-serif;
            border-radius: 10px;
            padding: 20px;
            text-align: center;
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            max-width: 80%;
        }
        </style>
    </head>

    <%
         String boton = request.getParameter("boton");
            System.out.println(boton);
            if (boton == null) {
                boton = "No autorizado";
            }
        HttpSession sessionUser = request.getSession(false);
        ArrayList<Envio> pedidos = null;
        UsuarioDAO u = new UsuarioDAO();
        if (sessionUser != null) {
            Usuario user = (Usuario) sessionUser.getAttribute("objetoUsuario");
            System.out.println("USUARIO" + user);
            if (user != null && user.getRol() == 1 && (boton.equals("Pedidos Pendientes")|| boton.equals("Marcar como Enviado"))) {
                pedidos = (ArrayList<Envio>) sessionUser.getAttribute("pedidos");
                System.out.println("PEDIDOS SIZE" + pedidos.size());
          
    %>
    <body >
        <nav class="navbar navbar-expand-lg navbar-custom">
            <form action="Controlador" method="POST" class="navbar-brand"> 
                <button type="submit" name="boton" value="ACIDO-PANEL" class="navbar-brand">ÁCIDO PANEL DE ADMINISTRACIÓN</button>
            </form>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                    aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item">
                        <form action="ValidarUsuario" method="POST" class="nav-link-form nav-link">
                            <input name="boton" value="Cerrar sesion" type="submit" class="btn btn-custom">
                        </form>
                    </li>
                </ul>
            </div>
        </nav>
        <div class="container mt-5">
            <div class="text-right">
                <form action="Controlador" method="POST" >
                    <input type="submit" name="boton" value="Volver" class="btn btn-volver">
                    <input type="hidden" name="volverVistaAdmin" value="volverVistaAdmin">
                </form></div>

            <% int contador = 0;
                if (pedidos != null && !pedidos.isEmpty()) {
                    u = new UsuarioDAO();
                    for (Envio envio : pedidos) {
                        contador++;
            %>
            <div class="card mb-3 mt-3" onclick="toggleProductos('<%=contador%>')">
                <div class="card-body">
                    <h5 class="card-title"><%= u.obtenerUsuario(envio.getUsuario()).getNombre()%> <%= u.obtenerUsuario(envio.getUsuario()).getApellidos()%></h5>
                    <p class="card-text">Dirección: <%= envio.getDireccion()%></p>
                </div>
            </div>
            <div class="productos card mb-3" id="productos_<%= contador%>" style="display: none;">
                <div class="card-body">
                    <% ArrayList<Producto> productos = envio.getProductos(); %>
                    <% for (Producto prod : productos) {%>
                    <p class="card-text"><%= prod.getCantidad()%> X <%= prod.getNombre()%> Talla: <%= prod.getTalla()%></p>
                    <% }%>
                    <form action="Controlador" method="POST">
                        <input type="hidden" name="cesta_id" value="<%= envio.getId()%>">
                        <div class="text-right">
                            <button name="boton" value="Marcar como Enviado" type="submit" class="btn btn-custom">Marcar como Enviado</button>
                        </div>
                    </form>
                </div>
            </div>
            <% } %>
            <% } else { %>
            <p class="alert alert-info">No hay pedidos pendientes.</p>
            <% }%>
        </div>
        <%  }else{%><div class="no-autorizado">
            <h5 class="text-center">¡VAYA! PARECE QUE NO HEMOS PODIDO RESOLVER TU PETICIÓN.</h5> 
            <h6> Para una mayor seguridad para todos, comprobamos que los usuarios estén registrados o que accedan a los enlaces mediante la navegación web. Si quieres acceder a esta página, por favor registrate o accede mediante los elementos habilitados.</h6>
            <h6>Estamos trabajando para hacer de esta una web mejor. Muchas gracias por tu comprensión.</h6>
            <h6 class="text-right">EL EQUIPO DE ÁCIDO</h6> 
        </div>
<%}
        }%>
    </body>
</html>
