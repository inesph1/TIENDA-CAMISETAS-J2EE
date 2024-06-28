<%-- 
    Document   : login2
    Created on : 30 may 2024, 21:10:51
    Author     : ines
--%>

<%@page import="java.math.BigInteger"%>
<%@page import="java.security.SecureRandom"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
         <link rel="icon" href="./Vista/Recursos/imagenes/tabNavegadorBlanco.png" type="image/png">
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
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
            .navbar-custom {
                background-color: #343a40;
            }
            .navbar-custom .navbar-brand,
            .navbar-custom .nav-link {
                color: #ffffff;
            }
            .navbar-custom .nav-link-form {
                padding: 0;
                margin: 0;
                height: 100%; /* Ocupa toda la altura de la barra de navegación */
            }
            .navbar-custom .nav-link-form input[type="submit"] {
                background: none;
                border: none;
                color: #ffffff;
                padding: 0 15px;
                height: 100%; /* Ocupa toda la altura de la barra de navegación */
                cursor: pointer;
                text-decoration: none;
                line-height: 56px; /* Alinea verticalmente el texto */
                font-size: 18px; /* Tamaño de letra más grande */
                margin: 0; /* Elimina cualquier margen adicional */
            }
            .navbar-custom .nav-link-form input[type="submit"]:hover {
                color: #fff3b0; /* Amarillo pasteloso */
            }
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
            
             .form-control::placeholder {
            text-align: center;
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
    String accion = request.getParameter("boton");
    String error = (String) request.getAttribute("error");
    if (error == null) {
        error = "";
    }
    SecureRandom random = new SecureRandom();

    String token = new BigInteger(130, random).toString(32);
    ServletContext context = getServletContext();
    context.setAttribute("registroToken", token);

    if (accion != null) {
    %>
    <div class="container">
    <img src="./Vista/Recursos/imagenes/tabNavegador.png" alt="Ácido Logo">
        <h2 class="text-center">Iniciar Sesión</h2>

        <form class="form-signin" action="ValidarUsuario" method="POST">
             <% if (!error.isEmpty()) {%>
                <p class="error-message error-background"><%= error%></p>
                <% } else {%>
                <p class="error-message"><%= error%></p>
                <% } %>
            <div class="form-group">
                <label for="email">Correo Electrónico:</label>
                <input type="text" id="email" name="txtemail" class="form-control" placeholder="Correo Electrónico" required>
            </div>
            <div class="form-group">
                <label for="password">Contraseña:</label>
                <input type="password" id="password" name="txtpass" class="form-control" placeholder="Contraseña" required>
            </div>
            <button class="btn btn-lg btn-custom btn-block" type="submit" name="boton" value="Iniciar sesion">Iniciar sesión</button>
            <p class="text-center mt-3">¿No tienes una cuenta? <a href="Vista/Paginas/registro.jsp">Regístrate</a></p>
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
