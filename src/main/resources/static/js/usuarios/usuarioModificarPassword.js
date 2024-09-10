
/* global log */


 function passwordValueLogin(password) {
        validaPasswordUser(password);
        
    }
    
    
function validaPasswordUser(password) {
    if ( password !== null && password !=='') {
        $.ajax({
            type: "GET",
            url: "usuarios/verificarPassword/" + password,
            success: function (data) {
                if ($.isEmptyObject(data)) {
                    toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                } else {
                    var resultado=data.data;
                    $("#pass_Actual").val(resultado);
                }
            }
        });
    }else{
        toastr['error']("Los campos no pueden estar vacíos. Ingresa tu contraseña actual", "Aviso", {progressBar: true, closeButton: true});
    }
};


$("#formModificarPasswordSession").submit(function (e) {
    $("#alerta_PasswordNuevoTexto").empty();
    $("#alerta_PasswordActualTexto").empty();
    $("#alerta_PasswordConfirmarTexto").empty();
    event.preventDefault();

    //var passwordActualInput = $("#pass_Actual").val(); 
    var password_Actual = $("#password_Actual").val(); //Password que ingresa el usuario en el formulario
    let inputValue = document.getElementById("pass_Actual").value; //true o false -- Resultado de comparar contraseñas
    

    var password_Nuevo = $("#password_Nuevo").val();
    var password_Confirmar = $("#password_Confirmar").val();

    const patron = /^(?=.*[A-Z])(?=.*[!@#$&%"'()*+-./_])(?=.*[0-9])(?=.*[a-z]).{8,}$/;

    if ($.trim(password_Actual) === '') {
        $("#alerta_PasswordActualTexto").append("<label class='text-danger'><small>El campo no puede estar vacío. Ingresa la contraseña correcta</small></label>");
        $("#alerta_PasswordActual").addClass("invalid-feedback");
        return false;
    }
    
    if ($.trim(inputValue)=== "false" || $.trim(inputValue)===''  || $.trim(inputValue)!== "true" ) {
        toastr['error']("La contraseña ingresada no corresponde a la contraseña actual del usuario", "Aviso", {progressBar: true, closeButton: true});
        $("#alerta_PasswordActual").addClass("invalid-feedback");
        $("#alerta_PasswordActualTexto").append("<label class='text-danger'><small>Ingresa la contraseña correcta</small></label>");
        return false;
    }

   if ($.trim(password_Nuevo) === "" || $("#password_Nuevo").val().length < 8 || (!password_Nuevo.match(patron))) {
        toastr['error']("La contraseña ingresada no es segura, coloca caracteres, números, letras mayúsculas y minúsculas ", "Aviso", {progressBar: true, closeButton: true});
        $("#alerta_PasswordNuevoTexto").append("<label class='text-danger'><small>Por favor ingresa otra contraseña más segura</small></label>");
        $("#alerta_PasswordNuevo").addClass("invalid-feedback");
        return false;
    }

    if ($.trim(password_Actual) === $.trim(password_Nuevo)) {
        $("#alerta_PasswordNuevoTexto").append("<label class='text-danger'><small>Por favor ingresa otra contraseña</small></label>");
        $("#alerta_PasswordNuevo").addClass("invalid-feedback");
        toastr['error']("La 'Nueva Contraseña' no puede ser igual que la actual", "Aviso", {progressBar: true, closeButton: true});
        return false;
    }

    if (password_Confirmar!==password_Nuevo || $.trim(password_Confirmar)==="" ) {
        $("#alerta_PasswordConfirmar").addClass("invalid-feedback");
        $("#alerta_PasswordConfirmarTexto").append("<label class='text-danger'><small>La contraseña debe coincidir con la ingresada en el campo anterior</small></label>");
        $("#alerta_PasswordNuevo").addClass("invalid-feedback");
        toastr['error']("La contraseñas ingresadas deben coincidir  ", "Aviso", {progressBar: true, closeButton: true});
        
        return false;
    }

    var datos={"password": password_Nuevo};
    
    
    $.ajax({
            type: "POST",
            url: "usuarios/cambiaContrasena/" + password_Nuevo,
            data: JSON.stringify(datos),
            contentType: "application/json",
            success: function (data) {
                $("#cambiarcontrasenaModal").modal('hide');
                toastr['success']("Contraseña Actualizada", "Aviso", {progressBar: true, closeButton: true});
                
                $("#formModificarPasswordSession")[0].reset();
            },
            error: function (e) {
                toastr['error']("No se actualizo la contraseña", "Aviso", {progressBar: true, closeButton: true});
            }
        });
});

/*=============================================
 LIMPIAR FORMULARIO
 =============================================*/
$("#cancelar_btn").on("click", function (event) {
    $("div#alerta_PasswordActual").removeClass("#invalid-feedback");
    $("div#alerta_PasswordNuevoTexto").html("");
    $("div#alerta_PasswordActualTexto").html("");
    $("div#alerta_PasswordConfirmarTexto").html("");
    
    $("div#alerta_PasswordNuevo").removeClass("#invalid-feedback");
    $("div#alerta_PasswordNuevo").html("");
    
    $("div#alerta_PasswordConfirmar").removeClass("#invalid-feedback");
    $("div#alerta_PasswordConfirmar").html("");
    
    $("#formModificarPasswordSession")[0].reset();
    $("#alerta_PasswordConfirmar").empty();
});
