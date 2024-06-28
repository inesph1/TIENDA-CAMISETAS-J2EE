/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */


    function toggleProductos(id) {
        let productosDiv =document.getElementById("productos_" + id);
        if (productosDiv.style.display === "none") {
            productosDiv.style.display = "block";
             productosDiv.classList.add("show");
        } else {
            productosDiv.style.display = "none";
            productosDiv.classList.remove("show");
        }
    }

