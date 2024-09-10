document.addEventListener('DOMContentLoaded', () => {
    areas();
});

let tabla; //Variable para inicializar tabla


//FUNCIÓN QUE COMPLE CON LAS CONDICIONES DE BUSQUEDA
function filtro() {
    const areasSelect = document.getElementById('areas');
    const estatusCheckBox = document.querySelectorAll('input[type="checkbox"][name="check"]:checked');

    if (areasSelect.value !== '' && estatusCheckBox.length > 0) {
        AreayEstatus();
    } else if (areasSelect.value !== '' && estatusCheckBox.length === 0) {
        buscarTrabajadorByArea();
    } else if (areasSelect.value === '' && estatusCheckBox.length > 0) {
        estatusPlaza();
    } else if (areasSelect.value === '' && estatusCheckBox.length === 0) {
        buscarPuesto();
    } 

}

function generarReporte(){
    const areasSelect = document.getElementById('areas');
    const valorSeleccionado = areasSelect.value;
    let url;
    //Verifica si se ha seleccionado un valor
    if(valorSeleccionado !== ''){
         url = 'report/monitorPlazasAutorizadasAreas/descargaPlazaPorAreas?areas_id=' + valorSeleccionado.toString() + "&tipo=pdf";
    }
    
    $.ajax({
        type: 'GET',
        contentType: 'application/pdf',
        url: url,
        xhrFields: {
            responseType: 'blob'
        },
        success: function (blob) {
            if($.isEmptyObject(blob)){
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar:true, closeButton: true});
            }
            toastr["success"]("Reporte Generado con éxito", "Aviso", {progressBar: true, closeButton: true});
            const link = document.createElement('a');
            link.href = window.URL.createObjectURL(blob);
            //Nombre de la descarga
            link.download = "ReporteMonitorPlazasAutorizadasPorAreasId_" + valorSeleccionado.toString() + ".pdf";
            link.click();
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar datos : " + e, "Error", {progressBar: true, closeButton: true});
        }
    });
}

//*********************************************************************************************************
//FUNCIÓN QUE BUSCA POR EL ID_AREA Y EL ESTATUS
//*********************************************************************************************************
function AreayEstatus() {

    const estatus_plaza_id = [];

    if ($('#checkAsignado').is(":checked")) {
        estatus_plaza_id.push($('#checkAsignado').val());
        $('#numero_plaza').val('');
        $('#puesto').val('');
    }
    if ($('#checkCancelado').is(":checked")) {
        $('#numero_plaza').val('');
        $('#puesto').val('');
        estatus_plaza_id.push($('#checkCancelado').val());
    }
    if ($('#checkDisponible').is(":checked")) {
        $('#numero_plaza').val('');
        $('#puesto').val('');
        estatus_plaza_id.push($('#checkDisponible').val());
    }

    if (estatus_plaza_id.length >= 2) {
        $('#numero_plaza').val('');
        $('#puesto').val('');
    }

    const area_id = $('#areas').val();

    const queryString = $.param({
        area_id: area_id,
        estatus_plaza_id: estatus_plaza_id.join(',')
    });

    $.ajax({
        type: 'GET',
        url: 'plaza/areaAndEstatusPlaza?' + queryString,
        dataType: 'json',
        success: function (data) {
            if (tabla) {
                tabla.destroy();
            }
            if ($.isEmptyObject(data)) {
                toastr.warning('No se encontraron datos para el número de plaza y el área seleccionada.', 'Aviso', {progressBar: true, closeButton: true});
                tabla.clear().draw();
            }
            tabla = $('#myTable').DataTable({
                data: data,
                columns: [
                    {data: 'numero_expediente'},
                    {data: "", sTitle: "Nombre del Trabajador", width: 200, visible: true, render: function (d, t, r) {
                            var he;
                            he = r.nombre + ' ' + r.apellido_paterno + ' ' + r.apellido_materno;
                            return he;
                        }
                    },
                    {data: 'puesto'}
                ]
            });
        },
        error: function () {
            toastr.warning('No se encontraron datos para el número de plaza y el área seleccionada.', 'Aviso', {progressBar: true, closeButton: true});
        }
    });
}

//******************************************************************************
//FUNCIÓN FILTRO POR ESTATUS -- MUESTRA DATOS
//******************************************************************************
function estatusPlaza() {

    estatus_plaza_id = [];

    if ($('#checkAsignado').is(":checked")) {
        estatus_plaza_id.push($('#checkAsignado').val());
        $('#numero_plaza').val('');
        $('#puesto').val('');
        //MostrarPlazaEstatus(estatus_plaza_id);
    }
    if ($('#checkCancelado').is(":checked")) {
        $('#numero_plaza').val('');
        $('#puesto').val('');
        estatus_plaza_id.push($('#checkCancelado').val());
        //MostrarPlazaEstatus(estatus_plaza_id);
    }
    if ($('#checkDisponible').is(":checked")) {
        $('#numero_plaza').val('');
        $('#puesto').val('');
        estatus_plaza_id.push($('#checkDisponible').val());
        // MostrarPlazaEstatus(estatus_plaza_id);
    }

    if (estatus_plaza_id.length >= 2) { // solo si hay dos o más checkboxes seleccionados
        $('#numero_plaza').val('');
        $('#puesto').val('');
        //MostrarPlazaEstatus(estatus_plaza_id);
    }

    MostrarPlazaEstatus(estatus_plaza_id);


    function MostrarPlazaEstatus(estatus_plaza_id) {
        if (estatus_plaza_id.length === 0) {
            return;
        }
        $.ajax({
            type: 'GET',
            url: "plaza/estatusPlazasDisp/?estatus_plaza_id=" + estatus_plaza_id,
            dataType: 'json',
            success: function (data) {

                if (tabla !== undefined) {
                    tabla.destroy(); //Limpiar la tabla si ya existe
                }
                if ($.isEmptyObject(data)) {
                    toastr.warning('No se encontraron datos para el estatus de la plaza.', 'Aviso', {progressBar: true, closeButton: true});
                    if(tabla){
                        tabla.clear().draw();
                    }
                }
                tabla = $('#myTable').DataTable({
                    data: data,
                    columns: [
                        {data: 'numero_expediente'},
                        {data: "", sTitle: "Nombre del Trabajador", width: 200, visible: true, render: function (d, t, r) {
                                var he;
                                he = r.nombre + ' ' + r.apellido_paterno + ' ' + r.apellido_materno;
                                return he;
                            }
                        },
                        {data: 'puesto'}
                    ]
                });
            },
            error: function (e) {
                toastr['warning']("El estatus no existe ");
            }
        });
    }
}
//
//***************************************************************************************
//FUNCIÓN QUE BUSCA UN TRABAJADOR POR EL NUMERO DE PLAZA
//***************************************************************************************
function buscarPuesto() {
    let numero_plaza = $('#numero_plaza').val();
    const numPlaza = document.getElementById('numero_plaza');
    numPlaza.addEventListener('change', function () {
        tabla = $('#myTable').DataTable();
        if (tabla !== undefined && tabla !== null) {
            $('#areas').val('');
            $('#checkAsignado').prop('checked', false);
            $('#checkCancelado').prop('checked', false);
            $('#checkDisponible').prop('checked', false);
        }
    });

    if (numero_plaza !== "") {
        $('#puesto').val();
        $.ajax({
            type: "GET",
            url: "plaza/buscarByNumeroPlaza/" + numero_plaza,
            dataType: 'json',
            success: function (data) {
//                event.preventDefault();
                if (tabla) {
                    tabla.destroy();
                }
                if ($.isEmptyObject(data)) {
                    toastr.warning('No se encontraron datos para el numero de la plaza.', 'Aviso', {progressBar: true, closeButton: true});
                    $('#numero_plaza').val('');
                    $('#puesto').val('');
                    if (tabla) {
                        tabla.clear().draw();
                    }
                }
                $('#numero_plaza').removeClass('is-invalid');
                tabla = $('#myTable').DataTable({
                    data: data,
                    columns: [
                        {data: 'numero_expediente'},
                        {data: "", sTitle: "Nombre del Trabajador", width: 200, visible: true, render: function (d, t, r) {
                                var he;
                                he = r.nombre + ' ' + r.apellido_paterno + ' ' + r.apellido_materno;
                                return he;
                            }
                        },
                        {data: 'puesto'}
                    ]
                });
                $('#puesto').val(data[0].puesto);
            },
            error: function (e) {
                $('#numero_plaza').val('');
                toastr["warning"]("La Plaza no existe, ingrese otra");
                $('#numero_plaza').addClass('is-invalid');
            }
        });
    }
}



///*=============================================
// COMBO AREAS
// =============================================*/
function areas(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarAreas",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#areas').empty().append("<option value=''>* Areas </option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].idArea === id) ? vselected = "selected" : vselected = " ";
                        $('#areas').append('<option value="' + data[x].idArea + '"' + vselected + '>' + data[x].desc_area + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar el área: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

//***********************************************************************************
//FUNCIÓN QUE BUSCA TRABJADORES POR EL ID AREEA
//***********************************************************************************
function buscarTrabajadorByArea() {
    let id_area = $('#areas').val();
    $.ajax({
        type: 'GET',
        url: "plaza/areaPlaza/" + id_area,
        dataType: 'json',
        success: function (data, textStatus, jqXHR) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                if (tabla) {
                    tabla.clear().draw();
                }
            } else {
                if (tabla) {
                    tabla.destroy();
                }
                tabla = $('#myTable').DataTable({
                    data: data,
                    columns: [
                        {data: 'numero_expediente'},
                        {data: "", sTitle: "Nombre del Trabajador", width: 200, visible: true, render: function (d, t, r) {
                                var he;
                                he = r.nombre + ' ' + r.apellido_paterno + ' ' + r.apellido_materno;
                                return he;
                            }
                        },
                        {data: 'puesto'}
                    ]
                });
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
        }
    });
}
//********************************************************************************
//Borrar tabla Y LIMPIAR CAMPOS al seleccionar otra ÁREA
//********************************************************************************
const comboIncidencias = document.getElementById('areas');
comboIncidencias.addEventListener('change', function () {
    $('#puesto').val('');
    $('#numero_plaza').val('');
    $('#checkAsignado').prop('checked', false);
    $('#checkCancelado').prop('checked', false);
    $('#checkDisponible').prop('checked', false);
    if (tabla === 0) {
        tabla.clear().draw();
    }
});

function limpiarCampos() {
    $('#areas').val('');
    $('#checkAsignado').prop('checked', false);
    $('#checkCancelado').prop('checked', false);
    $('#checkDisponible').prop('checked', false);
    if (tabla === 0) {
        tabla.clear().draw(); // Limpiar y redibujar la tabla
    }
}

const numPlaza = document.getElementById('numero_plaza');
numPlaza.addEventListener('change', function () {
    limpiarCampos();
});

//***********************************
//FUNCIÓN DEL BOTON LIMPIAR
//***********************************
function limpiarTodosLosCampos() {
    $('#numero_plaza').val('');
    $('#puesto').val('');
    $('#areas').val('');
    $('#checkAsignado').prop('checked', false);
    $('#checkCancelado').prop('checked', false);
    $('#checkDisponible').prop('checked', false);
    tabla.clear().draw(); // Limpiar y redibujar la tabla
}