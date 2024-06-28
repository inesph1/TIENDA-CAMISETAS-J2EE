<%-- 
    Document   : nuevo_producto
    Created on : 1 jun 2024, 16:22:09
    Author     : ines
--%>

<%@page import="Modelo.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Nuevo Producto</title>
        <link rel="icon" href="./Vista/Recursos/imagenes/tabNavegador.png" type="image/png">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link href="./Vista/Recursos/Estilos/estilosComunesAdministrador.css" rel="stylesheet">
        <style>

            .container-align-center h1 {
                margin-right: 600px;
            }

            /* Estilo para cuando el texto está al lado del checkbox */
            .form-check-inline .form-check-label {
                display: inline-block; /* Hace que el texto se muestre en línea con el checkbox */
                vertical-align: middle; /* Alinea verticalmente el texto con el checkbox */
            }

            /* Estilo para cuando se pasa el ratón sobre el checkbox */
            .form-check-input:hover {
                background-color: #f0f0f0; /* Color de fondo al pasar el ratón */
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

    <% String error = (String) request.getAttribute("error");
        if (error == null) {
            error = "";
         }%>
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
                if (user != null && user.getRol() == 1 && (boton.equals("Guardar producto")|| boton.equals("Nuevo Producto"))) {%>
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
            <div class="row">
                <div class="col-md-12">
                    <% if (!error.isEmpty()) {%>
                    <p class="error-message error-background"><%= error%></p>
                    <% } else {%>
                    <p class="error-message"><%= error%></p>
                    <% }%>
                    <div class="container-align-center">
                        <h1 class="mt-3">Nuevo Producto</h1>
                        <form action="Controlador" method="POST" >
                            <input type="submit" name="boton" value="Volver" class="btn btn-volver">
                            <input type="hidden" name="volverVistaAdmin" value="volverVistaAdmin">
                        </form>
                    </div> 
                    <form action="Controlador" method="POST" enctype="multipart/form-data">
                        <div class="form-group">
                            <label for="pNombre">Nombre Producto</label>
                            <input placeholder="Nombre Producto" type="text" id="pNombre" name="pNombre" required class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="pTalla">Talla</label>
                            <input type="text" id="pTalla" value="Unisex" name="pTalla" disabled="true" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="pPrecio">Precio</label>
                            <input type="number" step="0.01" id="pPrecio" name="pPrecio" required class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="pCantidad">Cantidad</label>
                            <input type="number" step="1" id="pCantidad" name="pCantidad" required class="form-control">
                        </div>
                        <div class="form-group form-check">
                            <input type="checkbox" id="pAlta" name="pAlta" class="form-check-input">
                            <label for="pAlta" class="form-check-label">Dar de alta</label>
                        </div>  
                        <div class="form-group">
                            <label for="file">Imagen del producto</label>
                            <input type="file" name="file" accept="image/*" required class="form-control-file">
                        </div>
                        <div class="text-right">
                            <input type="submit" name="boton" value="Guardar producto" class="btn btn-custom">
                        </div>
                    </form>
                </div>
            </div>
        </div>
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
