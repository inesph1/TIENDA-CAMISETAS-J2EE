/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */
window.onload = inicio;

function inicio() {
    console.log("La página ha cargado completamente.");
    let btnsuma = document.getElementById("sumarbtn");
    let btnresta = document.getElementById("restarbtn");
    
    //btnsuma.removeEventListener('click', sumar);
    //btnresta.removeEventListener("click", restar);
    btnsuma.addEventListener("click", sumar);
    btnresta.addEventListener("click", restar);


    function sumar() {
        console.log("SUMAR");
        let input = document.getElementById("input_cantidad");
        let valor = parseInt(input.value);
        console.log(input.value);
        let maximo = parseInt(input.getAttribute("max"));

        if (input.value < maximo) {
            input.value = ++valor; //importante poner ++valor y no valor++
        }
        console.log("TOTAL DESPUES"+maximo);
         actualizarValorOculto("sumar");
        
    }

    function restar() {
    console.log("RESTAR");
    let input = document.getElementById("input_cantidad");
    console.log(" INPUT "+input.value);
    let valor = parseInt(input.value);
    valor = valor - 1;
    if (valor >= 0) {
            input.value = valor; //importante poner ++valor y no valor++
        }else{
            input.value = 0;
        }

    actualizarValorOculto("restar");
}

    function actualizarValorOculto(tipo) {
        let inputCantidad = document.getElementById("input_cantidad");
        let cantidadHidden = document.getElementById("cantidadHidden");
        let accion = document.getElementById("accion");
        let inputTalla = document.getElementById("hiddenTalla");
      
        cantidadHidden.value = inputCantidad.value;
        accion.value=tipo;
        inputTalla.value="Unisex";
    }


}