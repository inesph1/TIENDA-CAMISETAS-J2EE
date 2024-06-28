<%-- 
    Document   : edicionProductos
    Created on : 16 may 2024, 9:23:55
    Author     : ines
--%>

<%@page import="Modelo.Usuario"%>
<%@page import="Configuracion.ProductoDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Modelo.Producto"%>
<%@ page import="com.google.gson.Gson" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Editar productos</title>
        <link rel="icon" href="./Vista/Recursos/imagenes/tabNavegador.png" type="image/png">
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <link href="./Vista/Recursos/Estilos/estilosComunesAdministrador.css" rel="stylesheet">
        <script src="./Vista/Scripts/ScriptFormularios.js"></script>
  <style>

        .btn-volver-form {
            margin-left: auto; /* Alinear a la derecha */
            margin-right: 20px; /* Ajustar margen derecho */
        }

        .contenido {
            margin-top: 20px; /* Ajusta este valor según la altura de tu barra de navegación */
        }

        .form-group {
            margin-left: 20px; /* Añadir margen izquierdo al formulario */
        }

        .custom-select {
            width: 200px; /* Ajusta el ancho según sea necesario */
        }

        .form-check-input {
            width: 20px; /* Ancho del checkbox */
            height: 20px; /* Altura del checkbox */
           /* margin-right: 10px; /* Margen derecho para separar del texto */
            background-color: #fff; /* Color de fondo */
            border: 1px solid #ced4da; /* Borde */
            border-radius: 3px; /* Bordes redondeados */
            cursor: pointer; /* Cambia el cursor al pasar por encima */
        }

        /* Estilo para cuando el checkbox está marcado */
        .form-check-input:checked {
            background-color: #007bff; /* Color de fondo cuando está marcado */
            border-color: #007bff; /* Color del borde cuando está marcado */
        }

        /* Estilo para el texto al lado del checkbox */
        .form-check-label {
            color: #555; /* Color del texto */
        }

        /* Estilo para cuando se pasa el ratón sobre el checkbox */
        .form-check-input:hover {
            background-color: #f0f0f0; /* Color de fondo al pasar el ratón */
        }
        
         .form-control.custom-select:focus {
                border-color: #FFD700; /* Amarillo pastel */
                box-shadow: 0 0 0 0.2rem rgba(255, 215, 0, 0.25); 
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
        <%HttpSession sessionUser = request.getSession(false);
            ProductoDAO pD = new ProductoDAO();
            ArrayList<Producto> productosVenta = new ArrayList<Producto>();
            productosVenta = pD.obtenerTodosLosProductos();
            String nombre = "";
            String productosJson = "";

            String error = (String) request.getAttribute("error");
            if (error == null) {
                error = "";
            }

             String boton = request.getParameter("boton");
            System.out.println(boton);
            if (boton == null) {
                boton = "No autorizado";
            }
            
            if (sessionUser != null) {
                Usuario user = (Usuario) sessionUser.getAttribute("objetoUsuario");
                if (user != null && user.getRol() == 1 && (boton.equals("Guardar cambios")|| boton.equals("Modificar Productos"))) {
                    nombre = (String) user.getNombre();
                
                Gson gson = new Gson();
                productosJson = gson.toJson(productosVenta);

            %>

        <script>
            var productos = <%= productosJson%>;
        </script>

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

       <div class="container mt-3">
    <div class="container-align-center">
        <h1>Seleccione el producto</h1>
        <form action="Controlador" method="POST" class="btn-volver-form">
            <input type="submit" name="boton" value="Volver" class="btn btn-volver">
            <input type="hidden" name="volverVistaAdmin" value="volverVistaAdmin">
        </form>
    </div>

    <div class="form-group">
        <% if (!error.isEmpty()) { %>
        <p class="error-message error-background"><%= error %></p>
        <% } else { %>
        <p class="error-message"><%= error %></p>
        <% } %>
        <select id="productoSelect" name="producto" onchange="cargarFormulario(this.value)" class="form-control custom-select">
            <option value="" disabled selected>Seleccione producto</option>
            <% for (Producto producto : productosVenta) { %>
            <option value="<%= producto.getId() %>"><%= producto.getNombre() %></option>
            <% } %>
        </select>
    </div>
</div>

<div class="container-center">
    <div class="form-container mt-5" >
        <div id="productoForm" style="display: none;">
            <form action="Controlador" method="post" class="form-group">
                <div class="form-row align-items-center justify-content-center">
                    <!-- Imagen -->
                    <div class="col-auto form-image" style="margin-right: 50px;">
                        <img id="pImagen" src="" alt="" style="width: 300px; height: auto;">
                    </div>
                    <!-- Inputs -->
                    <div class="col-md-9" style="max-width: 500px;">
                        <!-- Cambiado a col-md-9 -->
                        <input type="hidden" id="pId" name="pId" class="form-control mb-2">
                        <input type="text" id="pNombre" name="pNombre" class="form-control mb-2" style="width: 100%;">
                        <br>
                        <input type="select" id="pTalla" name="pTalla" disabled="true" class="form-control mb-2" style="width: 100%;">
                        <br>
                        <input type="number" step="0.01" id="pPrecio" name="pPrecio" class="form-control mb-2" style="width: 100%;">
                        <br>
                        <input type="number" step="1" id="pCantidad" name="pCantidad" class="form-control mb-2" style="width: 100%;">
                        <br>
                        <div class="form-check ">
                            <input class="form-check-input" type="checkbox" id="pAlta" name="pAlta">
                            <label class="form-check-label" for="pAlta">Dar de alta</label>
                        </div>
                        <div class="text-right">
                            <!-- Contenedor para el botón -->
                            <input type="submit" name="boton" value="Guardar cambios" class="btn btn-custom">
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
        <!-- Bootstrap JS -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <%}else{%> <div class="no-autorizado">
            <h5 class="text-center">¡VAYA! PARECE QUE NO HEMOS PODIDO RESOLVER TU PETICIÓN.</h5> 
            <h6> Para una mayor seguridad para todos, comprobamos que los usuarios estén registrados o que accedan a los enlaces mediante la navegación web. Si quieres acceder a esta página, por favor registrate o accede mediante los elementos habilitados.</h6>
            <h6>Estamos trabajando para hacer de esta una web mejor. Muchas gracias por tu comprensión.</h6>
            <h6 class="text-right">EL EQUIPO DE ÁCIDO</h6> 
        </div><%}}%>
    </body>
</html>