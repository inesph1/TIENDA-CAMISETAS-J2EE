<%-- 
    Document   : detalle_producto
    Created on : 2 jun 2024, 16:30:24
    Author     : ines
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="Modelo.Usuario"%>
<%@page import="Modelo.Carrito"%>
<%@page import="Modelo.Producto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Producto</title>
        <link rel="icon" href="./Vista/Recursos/imagenes/tabNavegadorBlanco.png" type="image/png">
        <script src="./Vista/Scripts/ScriptBotonesCantidades.js"></script>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <link href="./Vista/Recursos/Estilos/estilosComunesUsuario.css" rel="stylesheet">

        <style>
            .sidebar {
                position: fixed;
                top: 0;
                right: 0;
                width: 300px;
                height: 100%;
                background-color: #f4f4f4;
                padding: 20px;
                margin-top:5rem;
                /*  overflow-y: auto;*/
            }

            .margen{
                margin-top:5rem;
            }

            .nombre{
                font-size: 40px;
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
    </head>
    <body>
        <%
             String boton = request.getParameter("boton");
            System.out.println(boton);
            if (boton == null) {
                boton = "No autorizado";
            }
            
            HttpSession sessionUser = request.getSession(false);
            //IF BUSCAR PRODUCTO EN LA CESTA, SI EXISTE CARGA ESE PRODUCTO CON LA CANTIDAD EXISTENTE COMO VALOR POR DEFECTO
            //SI NO LO ENCUENTRA OBTIENE EL VALOR DE LA SESION POR DEFECTO

            //COMPROBAR QUE SI LA SESION ES NULA NO ACCEDA AL NULL DEL ATRIBUTO CESTA
            if (sessionUser != null) {
                Producto producto = (Producto) session.getAttribute("objetoProducto");
                int maximo = 0;
                if (producto != null ) {
                    maximo = producto.getCantidad();//guarda el maximo del producto original sacado de la bd
                    System.out.println("HAY " + maximo + "productos");
                }

                int base = 0;
                Carrito cesta = new Carrito();
                String nombre = "";
                int rol = 0;

                Usuario user = (Usuario) sessionUser.getAttribute("objetoUsuario");
                System.out.println("USUARIO" + user);
                if (user != null && (boton.equals("Guardar") || boton.equals("Guardar en la cesta"))) {
                    System.out.println("USER ES DISTINTO DE NULL");
                    cesta = user.getCarrito();
                    nombre = user.getNombre();
                    rol = user.getRol();
                    System.out.println("CESTA" + cesta);
                    if (cesta.buscarProducto(producto)) { //encuentra el producto en la cesta lo obtiene
                        producto = cesta.obtenerProducto(producto); //sobreescribe el producto por el de la cesta
                        base = producto.getCantidad();
                    }
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
                            <input name="boton" value="Mi perfil" type="submit">
                        </form>
                    </li>
                    <li class="nav-item">
                        <form action="ValidarUsuario" method="POST" class="nav-link-form nav-link">
                            <input name="boton" value="Cerrar sesion" type="submit">
                        </form>
                    </li>
                    <% if (rol == 1) { %>
                    <li class="nav-item">
                        <form action="Controlador" method="POST">
                            <input name="boton" value="Administrar" type="submit"  class="btn btn-admin panel-admin-btn">
                        </form>
                    </li>
                    <% }%>
                </ul>
            </div>
        </nav>

        <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    <div class="margen"> 

                        <div class="row ">
                            <div class="col-lg-8 mt-5">
                                <div class="contenedor-producto">
                                    <div class="text-right">
                                        <form action="Controlador" method="POST" class="float-right"> 
                                            <input type="submit" name="boton" value="Volver" class="btn btn-volver">
                                        </form>
                                    </div>
                                    <form action="detalle_producto.jsp" method="POST">
                                        <p class="nombre"> <%= producto.getNombre()%> </p> 
                                        <img src="<%= producto.getImagen()%>" alt="<%= producto.getNombre()%>" class="img-fluid">
                                        <input type="hidden" name="producto_id" value="<%=producto.getId()%>">
                                        <br>
                                        <select name="talla" id="talla" class="form-control w-25 mt-2">
                                            <option value="unisex">Unisex</option>
                                        </select>

                                        <% if (maximo > 0 || cesta.buscarProducto(producto)) {%>
                                        <div class="form-group mt-2">
                                            <div class="form-group mt-2">
                                                <label for="input_cantidad" class="mb-1">Cantidad:</label>
                                                <div class="input-group" style="width: 8rem;">
                                                    <div class="input-group-prepend">
                                                        <button type="button" class="btn btn-outline-secondary rounded-0" id="restarbtn" style="width: 2rem; height: 2rem; display: flex; justify-content: center; align-items: center;">-</button>
                                                    </div>
                                                    <input type="number" name="cantidad" id="input_cantidad" class="form-control text-center rounded-0" value="<%=base%>" min="0" max="<%=maximo%>" readonly style="width: 4rem; height: 2rem;">
                                                    <!-- Establece un ancho fijo para el input -->
                                                    <div class="input-group-append">
                                                        <button type="button" class="btn btn-outline-secondary rounded-0" id="sumarbtn" style="width: 2rem; height: 2rem; display: flex; justify-content: center; align-items: center;">+</button>
                                                    </div>
                                                </div>
                                            </div>

                                            <% } else { %>
                                            <p class="text-danger">El producto no se encuentra disponible por el momento</p>
                                            <% } %>
                                    </form>
                                    <!-- Botón de añadir al carrito -->
                                    <form action="Controlador" method="POST">
                                        <%Producto nuevoProducto = (Producto) producto.clone();
                                            session.setAttribute("nuevoProducto", nuevoProducto);%>
                                        <% if (maximo > 0 || cesta.buscarProducto(producto)) {%>
                                        <input type="hidden" name="producto_id" value="<%=producto.getId()%>">
                                        <input type="hidden" name="hiddenTalla" id="hiddenTalla" value="<%=producto.getId()%>">
                                        <input type="hidden" id="cantidadHidden" name="cantidadHidden" value="0">
                                        <input type="hidden" name="baseHidden" id="baseHidden" value="<%=base%>">
                                        <input type="hidden" id="accion" name="accion" value="">
                                        <input type="submit" name="boton" value="Guardar" class="btn btn-custom mt-2">
                                        <% } %>
                                    </form>
                                </div>
                            </div>



                            <div class="col-lg-4">
                                <div class="sidebar">
                                    <h2 class="nombre">Mi cesta</h2>
                                    <% ArrayList<Producto> carrito = cesta.getProductos();
                                        if (carrito.size() != 0) {
                                            System.out.println("NO ESTA VACIO");
                                            for (Producto p : carrito) {%>
                                    <div class="producto mb-2">
                                        <h6><%= p.getNombre()%></h6>
                                        <p>Precio: <%= p.getPrecio()%> X <%= p.getCantidad()%></p>
                                    </div>
                                    <% }%>
                                    <ul id="carrito"></ul>
                                    <p id="total"> TOTAL: <%= cesta.calcularTotalCesta()%></p>
                                    <p id="total"> Si quieres modificar algún producto antes de realizar el pago puedes hacerlo desde la sección Mi carrito </p>
                                    <form action="Controlador" method="POST">
                                        <input name="boton" value="Pagar" type="submit" class="btn btn-action">
                                    </form>
                                    <% } else {
                                        System.out.println("ESTA VACIO"); %>
                                    <p>Elige un producto para comenzar</p>
                                    <% }%>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%     } else {%><div class="no-autorizado">
            <h5 class="text-center">¡VAYA! PARECE QUE NO HEMOS PODIDO RESOLVER TU PETICIÓN.</h5> 
            <h6> Para una mayor seguridad para todos, comprobamos que los usuarios estén registrados o que accedan a los enlaces mediante la navegación web. Si quieres acceder a esta página, por favor registrate o accede mediante los elementos habilitados.</h6>
            <h6>Estamos trabajando para hacer de esta una web mejor. Muchas gracias por tu comprensión.</h6>
            <h6 class="text-right">EL EQUIPO DE ÁCIDO</h6> 
        </div>
        <%
                }
            }
        %>
    </body>
</html>
