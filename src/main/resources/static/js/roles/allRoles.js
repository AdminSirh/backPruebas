document.addEventListener('DOMContentLoaded', () => {
    asiganrElementos();
});

function asiganrElementos() {
    $.ajax({
        type: "GET",
        url: "sessionRoles",
        dataType: 'json',
        
        success: function (data) {
            if (Array.isArray(data)) {
                data.forEach(function(subArray) {
                    subArray.forEach(function(obj) {
                        //avaluosSistema/  AGREGAR RUTA PARA SERVIDOR
                        
                        if (obj.rolNombre=== 'ROLE_ADMIN' ) {
                            $('#logUsuarios').html('<label class="form-label" for="addon-wrapping-left">Log de Usuarios</label>    <br>'+
                                                   ' <a href="/movimientosUsuario"><img src="img/tiempo.png" width="100" height="100"></a>');
                            
                            $('#usuariosAdmin').html('<label class="form-label" for="addon-wrapping-left">Usuarios</label> <br>  ' +
                                    '  <a href="/usuariosAdmin"><img src="img/empleados.png" width="100" height="100"></a>');
                                           
                            $('#expedientesAdmin').html('<label class="form-label" for="addon-wrapping-left">Expedientes</label> <br>' +
                                    '  <a href="/expedientesAdmin"><img src="img/expediente.png" width="100" height="100"></a>');
                            
                            $('#catalogosAdmin').html('<label class="form-label" for="addon-wrapping-left">Catalogos</label>  <br> ' +
                                    '   <a href="/catalogosAdmin"><img src="img/revista.png" width="100" height="100"></a>');
                            
                            $('#trabajadoresAdmin').html('<label class="form-label" for="addon-wrapping-left">Trabajadores</label>  <br> ' +
                                    '   <a href="/trabajadoresAdmin"><img src="img/trabajo-en-equipo.png" width="100" height="100"></a>');
                            
                            $('#reportesGenerales').html('<label class="form-label" for="addon-wrapping-left">Reportes</label>  <br> ' +
                                    '   <a href="/reportesGenerales"><img src="img/reporte.png" width="100" height="100"></a>');
                        
                        }else if (obj.rolNombre=== 'ROLE_USER') {
                            $('#logUsuarios').html('<label class="form-label" for="addon-wrapping-left">Log de Usuarios</label>    <br>'+
                                                   ' <a href="/movimientosUsuario"><img src="img/tiempo.png" width="100" height="100"></a>');
                            
                            $('#usuariosAdmin').html('<label class="form-label" for="addon-wrapping-left">Usuarios</label> <br>  ' +
                                    '  <a href="/usuariosAdmin"><img src="img/empleados.png" width="100" height="100"></a>');
                                           
                            $('#expedientesAdmin').html('<label class="form-label" for="addon-wrapping-left">Expedientes</label> <br>' +
                                    '  <a href="/expedientesAdmin"><img src="img/expediente.png" width="100" height="100"></a>');
                            
                            $('#catalogosAdmin').html('<label class="form-label" for="addon-wrapping-left">Catalogos</label>  <br> ' +
                                    '   <a href="/catalogosAdmin"><img src="img/revista.png" width="100" height="100"></a>');
                            
                            $('#trabajadoresAdmin').html('<label class="form-label" for="addon-wrapping-left">Trabajadores</label>  <br> ' +
                                    '   <a href="/trabajadoresAdmin"><img src="img/trabajo-en-equipo.png" width="100" height="100"></a>');
                            
                            $('#reportesGenerales').html('<label class="form-label" for="addon-wrapping-left">Reportes</label>  <br> ' +
                                    '   <a href="/reportesGenerales"><img src="img/reporte.png" width="100" height="100"></a>');
                        
                        }else if (obj.rolNombre=== 'ROLE_PERSONAL') {
                            
                            $('#trabajadoresAdmin').html('<label class="form-label" for="addon-wrapping-left">Trabajadores</label>  <br> ' +
                                    '   <a href="/trabajadoresAdmin"><img src="img/trabajo-en-equipo.png" width="100" height="100"></a>');
                        
                        }else if (obj.rolNombre=== 'ROLE_REPORTES') {
                            
                            $('#reportesGenerales').html('<label class="form-label" for="addon-wrapping-left">Reportes</label>  <br> ' +
                                    '   <a href="/reportesGenerales"><img src="img/reporte.png" width="100" height="100"></a>');
                       
                        
                        }else if (obj.rolNombre=== 'ROLE_NOMINA') {
                            $('#expedientesAdmin').html('<label class="form-label" for="addon-wrapping-left">Expedientes</label> <br>' +
                                    '  <a href="/expedientesAdmin"><img src="img/expediente.png" width="100" height="100"></a>');
                            
                            $('#catalogosAdmin').html('<label class="form-label" for="addon-wrapping-left">Catalogos</label>  <br> ' +
                                    '   <a href="/catalogosAdmin"><img src="img/revista.png" width="100" height="100"></a>');
                            
                            $('#trabajadoresAdmin').html('<label class="form-label" for="addon-wrapping-left">Trabajadores</label>  <br> ' +
                                    '   <a href="/trabajadoresAdmin"><img src="img/trabajo-en-equipo.png" width="100" height="100"></a>');
                            
                            $('#reportesGenerales').html('<label class="form-label" for="addon-wrapping-left">Reportes</label>  <br> ' +
                                    '   <a href="/reportesGenerales"><img src="img/reporte.png" width="100" height="100"></a>');
                        }
                    });
                });
            } else {
               // console.log("La estructura de datos no es como se esperaba.");
            }
        },
        error: function (e) {
            toastr['error'](e.responseJSON.messages, "Aviso");
        }
    });
}
