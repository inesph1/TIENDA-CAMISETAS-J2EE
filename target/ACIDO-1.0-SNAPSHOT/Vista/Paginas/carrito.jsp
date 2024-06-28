<%-- 
    Document   : carrito
    Created on : 3 jun 2024, 18:47:00
    Author     : ines
--%>

<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Modelo.Producto"%>
<%@page import="Modelo.Carrito"%>
<%@page import="Modelo.Carrito"%>
<%@page import="Modelo.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mis productos</title>
        <link rel="icon" href="./Vista/Recursos/imagenes/tabNavegadorBlanco.png" type="image/png">
        <script src="./Vista/Scripts/sumaRestaParametros.js"></script>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <link href="./Vista/Recursos/Estilos/estilosComunesUsuario.css" rel="stylesheet">
    </head>

    <style>

        .container{
            padding:100px;
        }

        .producto img {
            width: 200px; /* Ancho deseado */
            height: auto; /* Altura automática para mantener la proporción */
            display: block; /* Asegura que la imagen se muestre como un bloque */
            margin-bottom: 10px; /* Espacio inferior para separar la imagen del resto del contenido */
        }

        .nombre{
            font-family: 'Sofia', sans-serif;
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

    <body>
        <%
            String boton = request.getParameter("boton");
            System.out.println(boton);
            if (boton == null) {
                boton = "No autorizado";
            }
            HttpSession sessionUser = request.getSession();
            ServletContext context = getServletContext();

            String error = (String) request.getAttribute("error");
            if (error == null) {
                error = "";
            }

            Carrito cesta = new Carrito();
            Producto producto = new Producto();
            int rol = 0;
            String nombre = "";
            //COMPROBAR QUE SI LA SESION ES NULA NO ACCEDA AL NULL DEL ATRIBUTO CESTA
            if (sessionUser != null) {
                Usuario user = (Usuario) sessionUser.getAttribute("objetoUsuario");
                System.out.println("USUARIO" + user);
                if (user != null && (boton.equals("Carrito") || boton.equals("Actualizar") || boton.equals("Actualizar cambios y pagar"))) {
                    cesta = user.getCarrito();
                    rol = user.getRol();
                    nombre = user.getNombre();
                    System.out.println("HASTA AQUI TODO BIEN, AHORA OBTENGO LOS PRODUCTSO");
                    // System.out.println(cesta.getProductos());

                    int maximo = 0;//obtener el maximo de producto que hay en la bd
        %>

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
                    <% if (!nombre.isEmpty()) {%>
                    <li class="nav-item">
                        <form action="Controlador" method="POST" class="nav-link-form nav-link">
                            <input name="boton" value="Carrito" type="submit" class="btn btn-link nav-link">
                        </form>
                    </li>
                    <% }%>
                </ul>
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item">
                        <form action="ValidarUsuario" method="POST" class="nav-link-form nav-link">
                            <input name="boton" value="Mi perfil" type="submit" class="btn btn-link nav-link">
                        </form>
                    </li>

                    <li class="nav-item">
                        <form action="ValidarUsuario" method="POST" class="nav-link-form nav-link">
                            <input name="boton" value="Cerrar sesion" type="submit" class="btn btn-link nav-link">
                        </form>
                    </li>
                    <% if (rol == 1) {%>
                    <li class="nav-item">
                        <form action="Controlador" method="POST">
                            <input name="boton" value="Administrar" type="submit" class="btn btn-admin panel-admin-btn">
                        </form>
                    </li>
                    <% }%>
                </ul>
            </div>
        </nav>
        <div class="container">
            <% if (!error.isEmpty()) {%>
            <p class="error-message error-background"><%= error%></p>
            <% } else {%>
            <p class="error-message"><%= error%></p>
            <% } %>
            <form action="carrito.jsp" method="POST">
                <div class="container">
                    <% ArrayList<Producto> carrito = cesta.getProductos();
                        if (carrito.size() != 0) {
                            Map<Integer, Integer> maximos = (Map<Integer, Integer>) request.getAttribute("maximos");
                            for (Producto p : carrito) {
                                maximo = maximos.get(p.getId());
                                maximo = maximo + p.getCantidad();%>                            

                    <div class="card mb-3">
                        <div class="row g-0">
                            <div class="col-md-4">
                                <img src="<%= p.getImagen()%>" alt="<%= p.getNombre()%>" class="img-fluid">
                            </div>
                            <div class="col-md-8">
                                <div class="card-body">
                                    <div class="producto">
                                        <h5 class="card-title nombre"><%= p.getNombre()%></h5>
                                        <p class="card-text">Precio: <%= p.getPrecio()%>€</p>
                                        <p class="card-text">Cantidad:
                                            <button type="button" name="btnresta" class="btn btn-outline-secondary rounded-0" id="restarbtn_<%=p.getId()%>">-</button>
                                            <input type="number" name="cantidad" class="form-control d-inline-block w-25 text-center"  id="cantidad_<%= p.getId()%>" value="<%=p.getCantidad()%>" min="0" max="<%=maximo%>" readonly>
                                            <button type="button" name="btnsuma" class="btn btn-outline-secondary rounded-0" id="sumarbtn_<%=p.getId()%>" >+</button>
                                        </p>
                                        <input type="hidden" id="id_producto" value="<%= p.getId()%>">
                                        <input type="hidden" id="cantidad_hidden_<%= p.getId()%>" value="<%= p.getCantidad()%>">
                                        <input type="hidden" id="precio_hidden_<%= p.getId()%>" value="<%= p.getPrecio()%>">
                                        <p class= "card-text" id="total_texto_<%= p.getId()%>"> <%= p.getPrecio()%>€ X <%= p.getCantidad()%>unidades = <%= p.getPrecio() * p.getCantidad()%>€</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <%  }%>
                </div>
            </form>
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Total del Carrito</h5>
                    <ul id="carrito"></ul>
                    <h5 id="total" class="card-title text-right"> <%= cesta.calcularTotalCesta()%> €</h5>
                </div>
            </div>
            <form action="Controlador" method="POST">
                <%
                    for (Producto p : carrito) {
                        maximo = maximos.get(p.getId());
                        maximo = maximo + p.getCantidad();%>

                <div class="productoHidden">
                    <input type="hidden" name="id_producto_hidden" value="<%= p.getId()%>">
                    <input  type="hidden" id="cantidad_hidden_controlador<%=p.getId()%>" name="cantidad_hidden" value="<%= p.getCantidad()%>">
                </div>
                <%}%>

                <div class="mt-5 text-right">
                    <input  class="btn btn-custom" name="boton" value="Actualizar" type="submit" >
                    <input class="btn btn-action" name="boton" value="Actualizar cambios y pagar" type="submit" >
                </div>
            </form>
            <%} else {
                System.out.println("ESTA VACIO");%>
            <p class="text-center">Aún no tienes productos en tu cesta</p>
            <%}%>

        </div>
        <%        } else {%>
        <div class="no-autorizado">
            <h5 class="text-center">¡VAYA! PARECE QUE NO HEMOS PODIDO RESOLVER TU PETICIÓN.</h5> 
            <h6> Para una mayor seguridad para todos, comprobamos que los usuarios estén registrados o que accedan a los enlaces mediante la navegación web. Si quieres acceder a esta página, por favor registrate o accede mediante los elementos habilitados.</h6>
            <h6>Estamos trabajando para hacer de esta una web mejor. Muchas gracias por tu comprensión.</h6>
            <h6 class="text-right">EL EQUIPO DE ÁCIDO</h6> 
        </div>
        <% }
            }%>
    </body>
</html>
