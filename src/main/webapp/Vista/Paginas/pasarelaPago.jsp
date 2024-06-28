<%-- 
    Document   : pasarelaPago
    Created on : 24 may 2024, 11:53:36
    Author     : ines
--%>

<%@page import="Modelo.Usuario"%>
<%@page import="Modelo.Carrito"%>
<%@page import="Configuracion.ProductoDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Modelo.Producto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Pasarela Pago</title>
     <link rel="icon" href="./Vista/Recursos/imagenes/tabNavegadorBlanco.png" type="image/png">
    <script src="https://www.paypal.com/sdk/js?client-id=ASj51WhnZR5wE6Ld757I6W2tKMeghQgWw-iaOa1lNsNHzIRI0xbsNXgK9ChzbG78WXFGRpxkQsRRcSlA&currency=EUR"></script>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script src="./Vista/Scripts/ScriptPayPal.js"></script>
    <style>
        .card-custom {
            border: 1px solid #343a40;
            border-radius: 10px;
            width:600px;
            max-width: 600px; /* Increase the maximum width */
            margin: 0 auto; /* Center the card */
        }
        .card-title-custom {
            color: #343a40;
            font-weight: bold;
        }
        .list-group-item-custom {
            background-color: #f8f9fa;
        }
        .list-group-item-custom:nth-child(odd) {
            background-color: #e9ecef;
        }
        .highlight {
            font-weight: bold;
            color: #dc3545;
        }
        .error-message {
            color: #856404; /* Color del texto amarillo oscuro */
            padding: 10px; /* Espacio interno */
            border-radius: 5px; /* Bordes redondeados */
            margin-bottom: 20px; /* Margen inferior */
            font-weight: bold; /* Texto en negrita */
        }
        .error-background {
            background-color: #fff3b0; /* Amarillo pastel */
        }
        .paypal-container {
            display: flex;
            justify-content: center;
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
        HttpSession sessionUser = request.getSession(false);
        float precioTotal = 0;
        Carrito cesta = new Carrito();
        String boton = request.getParameter("boton");
        System.out.println(boton);
        if (sessionUser != null) {
            Usuario user = (Usuario) sessionUser.getAttribute("objetoUsuario");
            if(user != null){
            cesta = user.getCarrito();
            precioTotal = cesta.calcularTotalCesta();
            if(precioTotal <= 0 || boton == null){%>
            <div class="no-autorizado">
            <h5 class="text-center">¡VAYA! PARECE QUE NO HEMOS PODIDO RESOLVER TU PETICIÓN.</h5> 
            <h6> Para una mayor seguridad para todos, comprobamos que los usuarios estén registrados o que accedan a los enlaces mediante la navegación web. Si quieres acceder a esta página, por favor registrate o accede mediante los elementos habilitados.</h6>
            <h6>Estamos trabajando para hacer de esta una web mejor. Muchas gracias por tu comprensión.</h6>
            <h6 class="text-right">EL EQUIPO DE ÁCIDO</h6> 
        </div>
       <% }
        else{
  
    %>
    <div class="container mt-5">
        <div class="d-flex flex-column align-items-center">
              <img src="./Vista/Recursos/imagenes/logoBlackAcido.png" alt="Logo" style="height: 150px; margin-right: 10px;"> 
            <div class="card card-custom">
                <div class="card-body text-center">
                    <% double iva = precioTotal * 0.21;
    double totalConIva = precioTotal + iva;
    String precioTotalFormatted = String.format("%.2f", precioTotal);
    String ivaFormatted = String.format("%.2f", iva);
    String totalConIvaFormatted = String.format("%.2f", totalConIva); %>
                    <h5 class="card-text">Total a pagar (con IVA):</h5>
                    <h4 class="card-title card-title-custom"><%= totalConIvaFormatted %> €</h4>
                    <h5 class="card-text">Resumen de la compra</h5>
                    <p class="list-group-item">Importe total: <%=precioTotalFormatted %> €</p>
                    <p class="card-text">IVA (21%): <%= ivaFormatted %> €</p>
                    <input type="hidden" id="valorCesta" name="valorCesta" value="<%=totalConIvaFormatted%>">
                </div>
            </div>
            <div id="paypal-button-container" class="mt-3" style=" width:600px;"></div>
        </div>
    </div>
                <%} }else{%>
    <p class="text-danger text-center">ACCESO NO AUTORIZADO</p><%}}%>
</body>
</html>
