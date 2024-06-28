/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

window.onload = inicio;

function inicio() {
    console.log("La página ha cargado completamente.");
    let btnsumas = document.querySelectorAll("button[name='btnsuma']");
    let btnrestas = document.querySelectorAll("button[name='btnresta']");

    btnsumas.forEach(btnsuma => {
        btnsuma.addEventListener("click", function () {
            let id = this.id.split("_")[1]; // Obtenemos el ID del botón
            console.log("Clic en el botón Sumar con ID: " + id);
            let inputCantidad = document.getElementById("cantidad_" + id);
            let valor = parseInt(inputCantidad.value);
            let maximo = parseInt(inputCantidad.getAttribute("max"));
            let cantidadHidden =  document.getElementById("cantidad_hidden_controlador"+id);
            if (valor < maximo) {
                inputCantidad.value = ++valor;
                cantidadHidden.value = inputCantidad.value;
            }

            console.log(inputCantidad.value);
            actualizarParrafos(id, inputCantidad.value);
        });

    });

    btnrestas.forEach(btnresta => {
        btnresta.addEventListener("click", function () {
            let id = this.id.split("_")[1]; // Obtenemos el ID del botón
            console.log("Clic en el botón Restar con ID: " + id);
            let inputCantidad = document.getElementById("cantidad_" + id);
            let valor = parseInt(inputCantidad.value);
            let cantidadHidden =  document.getElementById("cantidad_hidden_controlador"+id);

            // Reducimos el valor del input si es mayor que 0
            if (valor > 0) {
                inputCantidad.value = --valor;
                 cantidadHidden.value = inputCantidad.value;
            } else {
                inputCantidad.value = 0;
                 cantidadHidden.value = 0;
            }
            actualizarParrafos(id, inputCantidad.value);
        });
    });

    function actualizarParrafos(id, valor) {
        let parrafoCantidad = document.getElementById("cantidad_hidden_" + id);
        let parrafoPrecio = document.getElementById("precio_hidden_" + id);
        let parrafoTotal = document.getElementById("total_texto_" + id);

        // Actualizar el valor de los input hidden con los nuevos valores
        parrafoCantidad.value = valor;
        if (valor == 0) {
            parrafoTotal.innerText = "Elemento eliminado de la cesta";
        } else {
            parrafoTotal.innerText = `${parseFloat(parrafoPrecio.value)}€ X  ${valor} unidades =  ${parseFloat(parrafoPrecio.value) * valor}€`;
        }

        actualizarTotal();
    }

    function actualizarTotal() {

        let total = 0;
        let productos = document.querySelectorAll(".producto");

        productos.forEach(producto => {
            let id = producto.querySelector("#id_producto").value;
            let precio = parseFloat(document.getElementById(`precio_hidden_${id}`).value);
            let cantidad = parseFloat(document.getElementById(`cantidad_hidden_${id}`).value);
            total += precio * cantidad;
        });

        let totalElemento = document.getElementById("total");
        totalElemento.innerText = total.toFixed(2) + "€";
    }

 /*function updateForm() {
            var productos = document.querySelectorAll(".producto");
            productos.forEach(function(producto) {
                var id = producto.querySelector("input[name='id_producto']").value;
                var cantidad = producto.querySelector("input[name='cantidad']").value;
                var cantidadHidden = document.querySelector("input[name='cantidad_hidden_" + id + "']");
                //var cantidadHiddenControlador = document.querySelector("input[name='cantidad_hidden_" + id + "']");
                cantidadHidden.value = cantidad;
            });
        }*/
}