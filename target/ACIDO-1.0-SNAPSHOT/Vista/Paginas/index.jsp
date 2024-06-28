<%-- 
    Document   : index
    Created on : 1 jun 2024, 10:19:40
    Author     : ines
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="Modelo.Usuario"%>
<%@page import="Modelo.Producto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ácido</title>
        <link rel="icon" href="./Vista/Recursos/imagenes/tabNavegadorBlanco.png" type="image/png">
        <!-- Bootstrap CSS -->
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <link href="./Vista/Recursos/Estilos/estilosComunesUsuario.css" rel="stylesheet">
        <style>
            .container-fluid {
                padding: 0; /* Elimina el relleno del contenedor y quita todo el padding por defecto que quedaba*/
            }

            /* Ajusta el margen superior del contenedor de productos */
            #productos-container {
                margin-top: 60px; /* Ajusta el valor según el tamaño de la barra de navegación */
            }

            .precio {
                font-family: 'Sofia-bold', sans-serif;
            }

            h1{
                font-family: 'Sofia', sans-serif;
                text-align: center; /* Centra el texto horizontalmente */
                margin-top: 50px; /* Añade separación superior */
                margin-bottom: 50px; /* Añade separación inferior */
            }
            h3 {
                font-family: 'Sofia', sans-serif;
                font-size: 40px;
            }

            .img-fluid {
                transition: transform 0.3s ease, filter 0.3s ease; /* Transición suave */
            }

            /* Estilo para el efecto al pasar el ratón */
            .img-fluid:hover {
                transform: scale(1.1); /* Agrandar la imagen al 110% */
            }
        </style>
    </head>
    <body>
        <%
            session = request.getSession(); //obtiene sesion si existe

            ServletContext context = getServletContext();
            ArrayList<Producto> productosVenta = (ArrayList<Producto>) context.getAttribute("productosVenta");
            Boolean controlador = (Boolean) context.getAttribute("controlador");
             
           

            if (controlador ) {

                String botonLogin = "Log in";
                String nombre = "";
                int rol = 2;
                if (session != null) {
                    Usuario user = (Usuario) session.getAttribute("objetoUsuario");
                    if (user != null) {
                        nombre = (String) user.getNombre();
                        rol = (int) user.getRol();
                        botonLogin = "Mi perfil";


        %>

        <%       }
            }%>

        <nav class="navbar navbar-expand-lg navbar-custom">
            <form action="Controlador" method="POST" class="navbar-brand"> 
                <button type="submit" name="boton" value="ACIDO" class="navbar-brand">ÁCIDO</button>
            </form>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                    aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <% if (!nombre.isEmpty()) { %>
                    <li class="nav-item">
                        <form action="Controlador" method="POST" class="nav-link-form nav-link">
                            <input name="boton" value="Carrito" type="submit">
                        </form>
                    </li>
                    <% }%>
                </ul>
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item">
                        <form action="ValidarUsuario" method="POST" class="nav-link-form nav-link">
                            <input name="boton" value="<%= botonLogin%>" type="submit">
                        </form>
                    </li>
                    <li class="nav-item">
                        <form action="ValidarUsuario" method="POST" class="nav-link-form nav-link">
                            <input name="boton" value="Cerrar sesion" type="submit">
                        </form>
                    </li>
                    <% if (rol == 1) { %>
                    <li class="nav-item">
                        <form action="Controlador" method="POST" >
                            <input name="boton" value="Administrar" type="submit"  class="btn btn-admin panel-admin-btn">
                        </form>
                    </li>
                    <% }%>
                </ul>
            </div>
        </nav>

        <div id="carousel-container">
            <div class="container-fluid">
                <!-- Utilizamos container-fluid para que el slider ocupe todo el ancho de la pantalla -->
                <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel" data-interval="3000"
                     style="height: 400px; overflow: hidden;">
                    <!-- Aplicamos un estilo inline para fijar la altura y ocultar las imágenes que sobresalen -->
                    <ol class="carousel-indicators">
                        <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
                        <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
                        <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
                    </ol>
                    <div class="carousel-inner">
                        <div class="carousel-item active">
                            <img class="d-block w-100" src="./Vista/Recursos/imagenes/imagen2.JPG" alt="Second slide">
                        </div>
                        <div class="carousel-item">
                            <img class="d-block w-100" src="./Vista/Recursos/imagenes/imagen4.JPG" alt="Third slide">
                        </div>
                        <div class="carousel-item">
                            <img class="d-block w-100" src="./Vista/Recursos/imagenes/camiseta6.JPG" alt="First slide">

                        </div>
                    </div>
                    <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div>
            </div>
        </div>
        <% String error = (String) request.getAttribute("feedback");
            if (error == null) {
                error = "";
                     }
                     if (!error.isEmpty()) {%>
        <h5 class="error-message error-background text-center"><%= error%></h5>
        <% } else {%>
        <p class="error-message"><%= error%></p>
        <% } %>
        <h1>
            <% if (!nombre.isEmpty()) {%>
            ¡Hola, <%= nombre%>! Explora la nueva colección Mayo/Septiembre 2024
            <% } else { %>
            Explora la nueva colección Mayo/Septiembre 2024
            <% } %>
        </h1>
        <div id="productos-container" class="container">
            <div class="row">
                <%
                    int contador = 0;
                    if (productosVenta != null && !productosVenta.isEmpty()) {
                        for (Producto producto : productosVenta) {
                            if (producto.isAlta()) {
                                if (contador % 3 == 0) { %> 
                <div class="col-md-4"> <!-- Abre la columna -->
                    <% }%> <!-- Cierra la etiqueta de apertura -->
                    <div class="producto">
                        <h3><%= producto.getNombre()%></h3>
                        <img src="<%= producto.getImagen()%>" alt="<%= producto.getNombre()%>" class="img-fluid">
                        <p class="precio">Precio: <%= producto.getPrecio()%> €</p>
                        <form  action="Controlador" method="POST">
                            <input type="hidden" name="producto_id" value="<%= producto.getId()%>">
                            <input type="submit" name="boton" value="Guardar en la cesta" class="btn btn-custom">
                        </form>
                        <form id="form-guardar" >

                        </form>
                    </div>
                    <%
                            contador++;
                            if (contador % 3 == 0 || contador == productosVenta.size()) { %> 
                </div> <!-- Cierra la columna -->
                <% } else { %> <!-- Si no es múltiplo de 3 o el último elemento, cierra el div de producto y abre otro -->
            </div> <!-- Cierra la columna -->
            <div class="col-md-4"> <!-- Abre una nueva columna -->
                <% } %> <!-- Cierra el if -->
                <% }
                        }
                    } else { %> 
                <p>Ocurrió un error al cargar los productos</p>
                <% } %>
            </div> <!-- Cierra el container -->
            <%  } else {%>
             <p class="text-danger text-center">ACCESO NO AUTORIZADO</p>
            <% } %>
            <% context.setAttribute("controlador", Boolean.FALSE);%>
        </div>

        <!-- Bootstrap JS and dependencies -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    </body>
</html>
