/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

window.onload= inicio;


function inicio() {
    paypal.Buttons({
        createOrder: function(data, actions) {
            let cantidadPagar = document.getElementById("valorCesta").value;
            console.log(cantidadPagar);
            cantidadPagar = cantidadPagar.replace(',', '.');
            let total = parseFloat(cantidadPagar);
             console.log(total);
            return actions.order.create({
                purchase_units: [{
                    amount: {
                        value: total, // Cantidad que se va a cargar IMPORTANTE EL VALUE
                        currency_code: 'EUR' // Moneda EUR
                    }
                }]
            });
        },
        onApprove: function(data, actions) {
            return actions.order.capture().then(function(details) {
                // Captura la dirección postal del pagador
                var address = details.purchase_units[0].shipping.address;
                var addressLine1 = address.address_line_1 || '';
                var addressLine2 = address.address_line_2 || '';
                var adminArea2 = address.admin_area_2 || ''; // City
                var adminArea1 = address.admin_area_1 || ''; // State
                var postalCode = address.postal_code || '';
                var countryCode = address.country_code || '';
                var orderID = data.orderID;

                // Concatenar todos los detalles de la dirección en una sola línea
                var direccionCompleta = `${addressLine1}, ${addressLine2}, ${adminArea2}, ${adminArea1}, ${postalCode}, ${countryCode}`;
                console.log(direccionCompleta);

                // Crear el formulario y enviar los datos al servidor
                var form = document.createElement('form');
                form.setAttribute('method', 'POST');
                form.setAttribute('action', 'Controlador');

                let direccion = document.createElement('input');
                direccion.setAttribute('type', 'hidden');
                direccion.setAttribute('name', 'direccion');
                direccion.setAttribute('value', direccionCompleta);
                
                let boton = document.createElement('input');
                boton.setAttribute('type', 'hidden');
                boton.setAttribute('name', 'boton');
                boton.setAttribute('value', 'ConfirmarPago');
                
                 let feedback = document.createElement('input');
                feedback.setAttribute('type', 'hidden');
                feedback.setAttribute('name', 'feedback');
                feedback.setAttribute('value',  encodeURIComponent('Pago completado con éxito'));
                
                form.appendChild(direccion);
                form.appendChild(feedback);
                form.appendChild(boton);

                document.body.appendChild(form);
                form.submit();

              //alert('Pago completado ' + details.payer.name.given_name);
            });
        },
        onCancel: function(data) {
            alert('Transacción cancelada. No se ha realizado ningún cobro.');
        },
        onError: function(err) {
            var form = document.createElement('form');
                form.setAttribute('method', 'POST');
                form.setAttribute('action', 'Controlador');
                
            let feedback = document.createElement('input');
                feedback.setAttribute('type', 'hidden');
                feedback.setAttribute('name', 'feedback');
                 feedback.setAttribute('value',  encodeURIComponent('Error occurred during the transaction', err));
                
                 form.appendChild(feedback);
                 document.body.appendChild(form);
                form.submit();
            console.error('Error occurred during the transaction', err);
        }
    }).render('#paypal-button-container');
}