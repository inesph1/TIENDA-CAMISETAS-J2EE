<%-- 
    Document   : envios
    Created on : 2 jun 2024, 13:03:42
    Author     : ines
--%>

<%@page import="Modelo.Usuario"%>
<%@page import="Modelo.Envio"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Historial de envíos</title>
        <link rel="icon" href="./Vista/Recursos/imagenes/tabNavegador.png" type="image/png">
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <link href="./Vista/Recursos/Estilos/estilosComunesAdministrador.css" rel="stylesheet">
        <style>
            .table-custom th, .table-custom td {
                text-align: center;
                vertical-align: middle;
            }
          
            .container-custom {
                margin-top: 20px;
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
    <body>
        <%
             String boton = request.getParameter("boton");
            System.out.println(boton);
            if (boton == null) {
                boton = "No autorizado";
            }
            HttpSession sessionUser = request.getSession(false);
         if (sessionUser != null) {
                Usuario user = (Usuario) sessionUser.getAttribute("objetoUsuario");
                if (user != null && user.getRol() == 1 && (boton.equals("Envios"))) {%>
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
                </form>
             </div>
            <%
                // Obtener la lista de envíos desde la sesión
                ArrayList<Envio> envios = (ArrayList<Envio>) session.getAttribute("envios");
                if (envios != null && !envios.isEmpty()) {
            %>
            <table class="table table-bordered table-custom mt-3">
                <thead class="thead-dark">
                    <tr>
                        <th>Fecha Envio</th>
                        <th>Fecha Llegada</th>
                        <th>Dirección</th>
                        <th>Usuario</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        // Recorrer y mostrar cada envío
                        for (Envio envio : envios) {
                    %>
                    <tr>
                        <td><%=(envio.getFecha_envio().toString()).substring(0, 10) %></td>
                        <td><%=(envio.getFecha_llegada().toString()).substring(0, 10) %></td>
                        <td><%= envio.getDireccion() %></td>
                        <td><%= envio.getUsuario() %></td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
            <%
                } else {
            %>
            <div class="alert alert-info" role="alert">
                No hay envíos disponibles.
            </div>
            <%
                }
            %>
        </div>
    
        <!-- Bootstrap JS, Popper.js, y jQuery -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <%}else{%>
 <div class="no-autorizado">
            <h5 class="text-center">¡VAYA! PARECE QUE NO HEMOS PODIDO RESOLVER TU PETICIÓN.</h5> 
            <h6> Para una mayor seguridad para todos, comprobamos que los usuarios estén registrados o que accedan a los enlaces mediante la navegación web. Si quieres acceder a esta página, por favor registrate o accede mediante los elementos habilitados.</h6>
            <h6>Estamos trabajando para hacer de esta una web mejor. Muchas gracias por tu comprensión.</h6>
            <h6 class="text-right">EL EQUIPO DE ÁCIDO</h6> 
        </div>
<%}}%>
    </body>
</html>
