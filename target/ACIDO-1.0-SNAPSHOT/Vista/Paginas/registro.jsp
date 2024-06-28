<%-- 
    Document   : registro2
    Created on : 30 may 2024, 21:12:23
    Author     : ines
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registro</title>
        <link rel="icon" href="./Vista/Recursos/imagenes/tabNavegadorBlanco.png" type="image/png">
         <link rel="icon" href="./../Recursos/imagenes/tabNavegadorBlanco.png" type="image/png">
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <script src="./../Scripts/validacionPassword.js"></script>
        <script src="./Vista/Scripts/validacionPassword.js"></script>
        <style>
            /* Customize additional styles here if needed */
            .container {
                margin-top: 50px;
            }
            .form-signin {
                max-width: 400px;
                padding: 15px;
                margin: 0 auto;
            }
            .form-signin .form-control {
                position: relative;
                box-sizing: border-box;
                height: auto;
                padding: 10px;
                font-size: 16px;
            }
            .form-signin .form-control:focus {
                z-index: 2;
            }
            .btn-custom {
                background-color: #f8f9fa;
                color: #343a40;
                border-color: #343a40;
            }
            .btn-custom:hover {
                background-color: #343a40;
                color: #ffffff;
            }
            
                .container {
            text-align: center; /* Para centrar el contenido del contenedor */
        }

        .container img {
            display: block; /* Convertir la imagen en un bloque */
            margin: 0 auto; /* Centrar horizontalmente */
            height: 200px;
            margin-bottom: 20px; /* Espacio adicional debajo de la imagen */
        }
        
         .error-message {
            color: #856404; /* Color del texto amarillo oscuro */
            padding: 10px; /* Espacio interno */
            border-radius: 5px; /* Bordes redondeados */
            margin-bottom: 20px; /* Margen inferior */
            font-weight: bold; /* Texto en negrita */
        }

        /* Estilo para el fondo del mensaje de error */
        .error-background {
            background-color: #fff3b0; /* Amarillo pastel */
        }
        </style>
    </head>
    <body>
        <% 
            String tokenRecibido = request.getParameter("token");
            ServletContext context = getServletContext();
            String tokenAlmacenado = (String) context.getAttribute("registroToken");
            String error = (String) request.getAttribute("error");
            String controlador="../../";
            String ruta = "./../Recursos/imagenes/tabNavegador.png";
            if (error == null) {
                error = "";    
            }else{
            controlador="";
            ruta = "./Vista/Recursos/imagenes/tabNavegador.png";}
            if (tokenAlmacenado != null) {
        %>
        <div class="container">
              <img src="<%=ruta%>" alt="Ácido Logo">
            <h2>Registro</h2>
             <% if (!error.isEmpty()) {%>
                <p class="error-message error-background form-signin"><%= error%></p>
                <% } else {%>
                <p class="error-message form-signin"><%= error%></p>
                <% } %>
             <p id="error" class="text-center"></p>
            <form class="form-signin" action="<%=controlador%>ValidarUsuario" method="post" onsubmit="return validarContrasena()">
                 
                <div class="form-group">
                    <label for="nombre">Nombre:</label>
                    <input type="text" id="nombre" name="txtnombre" placeholder="Nombre" class="form-control" required>
                </div>
                <div class="form-group">
                    <label for="apellidos">Apellidos:</label>
                    <input type="text" id="apellidos" name="txtapellidos" placeholder="Apellidos" class="form-control" required>
                </div>
                <div class="form-group">
                    <label for="email">Correo Electrónico:</label>
                    <input type="email" id="email" name="txtemail" placeholder="Correo electrónico" class="form-control" required>
                </div>
                <div class="form-group">
                    <label for="contrasena">Contraseña:</label>
                    <input type="password" id="contrasena" name="txtpass" placeholder="Mín. 6 caracteres, mayúscula y minúscula" class="form-control" required>
                </div>
                <button class="btn btn-lg btn-custom btn-block" type="submit" name="boton" value="Registrarse">Registrarse</button>
            </form>
        </div>
        <% } else { %>
        <p class="text-danger text-center">ACCESO NO AUTORIZADO</p>
        <% } %>

        <!-- Bootstrap JS and dependencies -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
