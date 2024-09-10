/* Este JS es implementado para que cuando se realizcen solicitudes ajax y el usuario no se encuente loggeado lo redireccione a la página de loggin con el mensaje correspondiente
 * Igualmente redirecciona al usuario al  login cuando ocurre un timeout
 * Session listener es el encargado de destruir la sesión para que el usuario deba de reingresar forzosamente*/

$(document).ajaxError(function myErrorHandler(event, xhr, ajaxOptions, thrownError) {
    if (xhr.status === 403) {
        // Verificar si la URL comienza con la IP del servidor
        if (window.location.href.startsWith("http://10.19.1.242:8080")) {
            // Agregar el prefijo "/sirh" a la URL antes de redirigir
            window.location.href = window.location.origin + "/sirh/login?expired=true";
        } else {
            // Mantener la URL original si no comienza con la IP del servidor
            window.location.href = window.location.origin + "/login?expired=true";
        }
    }
});

$(document).ready(function () {
    var timeout = 10 * 60 * 1000; // 10 minutos
    var minute = 60 * 1000; // 1 minuto
    var toastrOptions = {
        timeOut: timeout - minute,
        progressBar: true,
        closeButton: false,
        tapToDismiss: false,
        preventDuplicates: true,
        extendedTimeOut: 0,
        onHidden: function () {
            localStorage.setItem('lastActivity', Date.now().toString());
            updateTimeoutWarning();
        }
    };

    var currentToastr;

    function showTimeoutWarning(timeRemaining) {
        if (!currentToastr) {
            currentToastr = toastr.warning(
                    'Su sesión se cerrará en <span id="timeRemaining">' +
                    Math.floor(timeRemaining / 1000) +
                    '</span> segundos por Inactividad.',
                    'Cierre de sesión',
                    toastrOptions
                    );
        } else {
            $("#timeRemaining").text(Math.floor(timeRemaining / 1000));
        }
    }

    // Agregar esta función para cerrar el Toastr al detectar una interacción
    function closeToastrOnInteraction() {
        if (currentToastr) {
            toastr.clear(currentToastr);
            currentToastr = null;
        }
    }

    function updateTimeoutWarning() {
        var lastActivity = parseInt(localStorage.getItem('lastActivity'));
        var currentTime = Date.now();
        var elapsed = currentTime - lastActivity;
        var timeRemaining = timeout - elapsed;

        if (timeRemaining <= 0) {
            if (currentToastr) {
                toastr.clear(currentToastr);
                currentToastr = null;
            }
            if (window.location.href.startsWith("http://10.19.1.242:8080")) {
                // Agregar el prefijo "/sirh" a la URL antes de redirigir
                window.location.href = window.location.origin + "/sirh/login?expired=true";
            } else {
                // Mantener la URL original si no comienza con la IP del servidor
                window.location.href = window.location.origin + "/login?expired=true";
            }

        } else if (timeRemaining <= minute) {
            showTimeoutWarning(timeRemaining);
        }
    }

    localStorage.setItem('lastActivity', Date.now().toString());

    setInterval(updateTimeoutWarning, 1000);

    $('body').on('click keypress', function () {
        localStorage.setItem('lastActivity', Date.now().toString());
        updateTimeoutWarning();
        closeToastrOnInteraction();
    });
});
