

function validarContrasena() {
            var contrasena = document.getElementById("contrasena").value;

            // Comprobar si la contraseña cumple con los requisitos
            var tieneMinusculas = /[a-z]/.test(contrasena);
            var tieneMayusculas = /[A-Z]/.test(contrasena);
            var tieneAlMenos8Caracteres = contrasena.length >= 6;

            if (!tieneMinusculas || !tieneMayusculas || !tieneAlMenos8Caracteres) {
                var mensajeError = document.getElementById("error");
                mensajeError.textContent = "La contraseña debe contener al menos una letra minúscula, una letra mayúscula y mínimo 6 caracteres.";
                return false;
            }

            return true; // La contraseña cumple con los requisitos
        }