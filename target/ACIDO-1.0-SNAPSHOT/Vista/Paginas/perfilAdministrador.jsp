<%-- 
    Document   : perfilAdminitsrador
    Created on : 1 jun 2024, 10:03:29
    Author     : ines
--%>

<%@page import="Modelo.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <!-- Bootstrap CSS -->
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
         <link rel="icon" href="./Vista/Recursos/imagenes/tabNavegador.png" type="image/png">
     <style>
            .btn-custom {
                background-color: #f8f9fa;
                color: #343a40;
                border-color: #343a40;
            }
            .btn-custom:hover {
                background-color: #343a40;
                color: #ffffff;
            }
            .header, .content {
                padding-top: 20px;
                padding-bottom: 20px;
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
       if (user != null && user.getRol() == 1 && (boton.equals("Administrar") || boton.equals("ACIDO-PANEL") || boton.equals("Iniciar sesion")|| boton.equals("Volver"))) {%>
        <div class="container mt-5">
            <div class="row header align-items-center">
                <div class="col text-left">
                    <form action="Controlador" method="POST">
                       <!--LOGOTIPO ACIDO-->
                        <img src="./Vista/Recursos/imagenes/tabNavegador.png" alt="Logo" style="height: 150px; margin-right: 10px;">
                        <button type="submit" name="boton" value="Ver Vista Usuario" class="btn btn-custom">Ver Vista Usuario</button>
                    </form>
                </div>
                <div class="col text-right">
                    <form action="ValidarUsuario" method="POST">
                        <button type="submit" name="boton" value="Cerrar sesion" class="btn btn-custom">Cerrar sesión</button>
                    </form>
                </div>
            </div>
            <div class="row content">
                <div class="col">
                     
                    <h1 class="text-center">Perfil de Administrador</h1>
                    <form action="Controlador" method="POST" class="text-center mt-4">
                        <button type="submit" name="boton" value="Modificar Productos" class="btn btn-custom mb-2">Modificar Productos</button>
                        <button type="submit" name="boton" value="Nuevo Producto" class="btn btn-custom mb-2">Nuevo Producto</button>
                        <button type="submit" name="boton" value="Pedidos Pendientes" class="btn btn-custom mb-2">Pedidos Pendientes</button>
                        <button type="submit" name="boton" value="Envios" class="btn btn-custom mb-2">Envíos</button>
                    </form>
                </div>
            </div>
        </div>

        <!-- Bootstrap JS and dependencies -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
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
