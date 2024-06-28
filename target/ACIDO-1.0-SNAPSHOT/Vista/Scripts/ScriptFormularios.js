function cargarFormulario(productoId) {
   // console.log("PRODUCTOS" + productos);
    let producto = '';

    for (let i = 0; i < productos.length; i++) {
        if (productos[i].id == productoId) {
            producto = productos[i];
            console.log(productos[i]);
            break;
        } else {
            console.log("no es igual");
        }
    }

    if (producto !== '') {
        var rutaImagen = producto.imagen.replace(/[\r\n]/g, ''); //elimina retorno de carro y los remblaca por ''
        document.getElementById("pId").value = producto.id;
        document.getElementById("pImagen").src= rutaImagen;
        document.getElementById("pImagen").alt = producto.nombre;
        document.getElementById("pNombre").value = producto.nombre;
        console.log("PRIMERO "+producto.talla);
        if(producto.talla!=null){
            document.getElementById("pTalla").value = producto.talla;
        }else{
             document.getElementById("pTalla").value = "Unisex";
        }
        document.getElementById("pPrecio").value = producto.precio;
        document.getElementById("pCantidad").value = producto.cantidad;
        document.getElementById("pAlta").checked = producto.alta;

        document.getElementById("productoForm").style.display = "block";
    } else {
        console.error("No se encontró el producto con ID: " + productoId);
    }
}