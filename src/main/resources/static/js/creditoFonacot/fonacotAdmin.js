document.addEventListener('DOMContentLoaded', () => {
    comparaId();
    comparaFechasCorte();
});

//Variable global para generar los datos del trabajador
let workersData = [];

function tabla() {
    var arreglo_trabajadores = [];
    $tablaTrabajadores = $('#tabla_Trabajadores').dataTable({

        "ajax": {
            url: "trabajador/listadoTrabajadoresPlaza",
            method: 'GET',
            dataSrc: ''
        },
        responsive: true,
        bProcessing: true,
        select: true,
        "language": {
            'processing': 'Procesando espera...',
            "sProcessing": "Procesando...",
            "sLengthMenu": "Mostrar _MENU_ registros",
            "sZeroRecords": "No se encontraron resultados",
            "sEmptyTable": " ",
            "sInfo": " _START_ al _END_ Total: _TOTAL_",
            "sInfoEmpty": " ",
            "sInfoFiltered": "(filtrado de un total de _MAX_ registros)",
            "sInfoPostFix": "",
            //"searchPlaceholder": "Buscar",
            "search": "Buscar",
            "paginate": {
                "previous": 'Anterior',
                "next": 'Siguiente'
            },
            "paging": false,
            "bPaging": false,
            "scrollY": "300px",
            "sUrl": "",
            "sInfoThousands": ",",
            "sLoadingRecords": "Cargando...",
            "oAria": {
                "sSortAscending": ": Activar para ordenar la columna de manera ascendente",
                "sSortDescending": ": Activar para ordenar la columna de manera descendente"
            }
        },
        columns: [

            {data: "cat_expediente.numero_expediente"},
            {data: "persona.nombre"},
            {data: "persona.apellido_paterno"},
            {data: "persona.apellido_materno"},
            {data: "persona.fecha_nacimiento"},
            {data: "persona.cat_genero.desc_genero"},
            {data: "persona.cat_estado_civil.desc_edo_civil"},
            {data: "persona.curp"},
            {data: "persona.fecha_captura", render: function (data, type, row) {
                    var fechaCaptura = new Date(data);
                    return fechaCaptura.toLocaleDateString();
                }},
            {data: "fecha_antiguedad", render: function (data, type, row) {
                    var fechaCaptura = new Date(data);
                    return fechaCaptura.toLocaleDateString();
                }},
            {data: "", sTitle: "FONACOT", width: 100, visible: true, render: function (d, t, r) {
                    var he;
                    he = '<button type="button" class="btn btn-outline-primary" onclick="capturaCredito(' + r.id_trabajador + ')"><span class="fa fa-edit"></span>Captura Crédito</button>';
                    return he;
                }
            },
            {data: "", sTitle: "Agregar Trabajador a TXT", width: 100, visible: true, className: "text-center", render: function (d, t, r) {
                    var activos = $('#activos').val();
                    var activosN = activos.split(",");
                    var he;
                    arreglo_trabajadores.push(r.id_trabajador);
                    for (var i = 0; i < activosN.length; i++) {
                        if (activosN[i] === r.id_trabajador.toString()) {
                            he = '<button type="button" disabled= "true" class="btn btn-success" onclick="guardaSolicitud(' + r.id_trabajador + ')"><span class="fa fa-plus me-2"></span> Agregar</button>';
                            return he;
                        } else {
                            he = '<button type="button" class="btn btn-success" onclick="guardaSolicitud(' + r.id_trabajador + ')"><span class="fa fa-plus me-2"></span> Agregar</button>';

                        }
                    }
                    return he;
                }
            }]
    });
}


function comparaId() {
    if ($.fn.DataTable.isDataTable('#tabla_Trabajadores')) {
        $('#tabla_Trabajadores').DataTable().destroy();
    }
    var trabajadores = [];
    $.ajax({
        type: "GET",
        url: "creditoFonacot/listarSolicitudesFonacot",
        dataType: 'json',
        success: function (data) {
            if (data !== undefined) {
                for (var i = 0; i < data.length; i++) {
                    trabajadores.push(data[i].trabajador.id_trabajador);
                }
                $('#activos').val(trabajadores);

                tabla();
            } else {

                tabla();
            }
        },
        error: function (e) {

            alert(e);
        }
    });


}


//Guarda las solicitudes de FONACOT
function guardaSolicitud(id_trabajador) {
    var datos = {"trabajador": {"id_trabajador": id_trabajador}};

    $.ajax({
        type: "POST",
        url: "creditoFonacot/guardarRegistroFonacot",
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            toastr['success'](data.data, "Datos Guardados Correctamente");
            comparaId();
            $tablaTrabajadores.ajax.reload(null, false);
        },
        error: function (e) {
            toastr["warning"]("Error al guardar Solicitud " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}


//FUNCIÓN PARA DESCARGAR ARCHIVO DE TEXTO
function descargaTexto() {
    $.ajax({
        type: "GET",
        url: "creditoFonacot/listarDocumentoTexto",
        dataType: 'json',
        success: function (data) {
            const numero_expediente_ceros = '0000000';
            const contenidoTexto = data.map(item => {
                const fecha = new Date(item.fecha_ingreso);
                const dia = fecha.getDate().toString().padStart(2, '0');
                const mes = (fecha.getMonth() + 1).toString().padStart(2, '0');
                const anio = fecha.getFullYear();
                const fechaFormateada = `${dia}/${mes}/${anio}`;
                const sueldoMensualNeto = item.sueldo_mensual_neto || "";
                const sueldoMensual = item.sueldo_mensual || "";
                const numImss = item.num_imss || "";
                const numeroExpediente = numero_expediente_ceros + item.numero_expediente || "";

                const apellidoPaterno = item.apellido_paterno.toUpperCase() || "";
                const apellidoMaterno = item.apellido_materno.toUpperCase() || "";
                const nombre = item.nombre.toUpperCase() || "";

                const apellidoPaternoAlineado = padEnd(apellidoPaterno, 40);
                const apellidoMaternoAlineado = padEnd(apellidoMaterno, 20);
                const nombreAlineado = padEnd(nombre, 30);

                const contenido = [
                    "1",
                    item.rfc || "",
                    "1",
                    item.num_imss || "",
                    item.curp || "",
                    item.id_genero || "",
                    apellidoPaternoAlineado,
                    " ".repeat(1),
                    apellidoMaternoAlineado,
                    " ".repeat(1),
                    nombreAlineado,
                    " ".repeat(Math.max(0, 15 - nombreAlineado.length)),
                    apellidoPaternoAlineado,
                    " ".repeat(Math.max(0, 15 - apellidoPaternoAlineado.length)),
                    apellidoMaternoAlineado,
                    " ".repeat(Math.max(0, 15 - apellidoMaternoAlineado.length)),
                    nombreAlineado,
                    " ".repeat(Math.max(0, 15 - nombreAlineado.length)),
                    fechaFormateada,
                    padEnd(sueldoMensualNeto, 9),
                    padEnd(sueldoMensual, 13),
                    numImss,
                    numeroExpediente
                ].join("");

                return contenido;
            }).join('\n');

            const nombreArchivo = "solicitudesFONACOT.txt";

            descargarTexto(contenidoTexto, nombreArchivo);
        },
        error: function (e) {
            alert("Error while fetching data: " + e);
        }
    });
}

function alignRight(texto, width) {
    if (texto === null) {
        texto = '';
    }
    if (texto.length > width) {
        return texto.slice(-width);
    }
    return padEnd(texto, width); // Cambiamos padStart por padEnd
}

function padEnd(texto, width, char = '') {
    if (texto === null) {
        texto = '';
    }
    if (texto.length >= width) {
        return texto;
    }
    const padding = char.repeat(width - texto.length);
    return texto + padding;
}

//Funcion que realiza la descarga del TXT
function descargarTexto(texto, nombreArchivo) {
    const blob = new Blob([texto], {type: 'text/plain'});

    // Create a temporary link element to trigger the download
    const link = document.createElement('a');
    link.href = URL.createObjectURL(blob);
    link.download = nombreArchivo;

    // Append the link to the DOM, trigger the download, and remove the link
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
}

function comparaFechasCorte() {
    var hoy = new Date();
    var hoy_formato = formatearFecha(hoy);
    var fechas_corte = [];
    $.ajax({
        type: "GET",
        url: "creditoFonacot/listarFechasCortePrestamos",
        dataType: 'json',
        success: function (data) {
            if (data !== undefined) {
                for (var i = 0; i < data.length; i++) {
                    var fechaCorteFormateada = formatearFecha(data[i].fecha_corte);
                    fechas_corte.push(fechaCorteFormateada);
                }
                if (fechas_corte.includes(hoy_formato)) {
                    const boton_descuetos = document.getElementById('boton_descuentos');
                    boton_descuetos.disabled = true;
                }
            } else {
                const boton_descuetos = document.getElementById('boton_descuentos');
                boton_descuetos.disabled = false;
            }


        },
        error: function (e) {
            alert(e);
        }
    });
}

function formatearFecha(fecha) {
    var date = new Date(fecha);

    var year = date.getFullYear();
    var month = (date.getMonth() + 1).toString().padStart(2, '0'); // Agrega ceros a la izquierda si es necesario
    var day = date.getDate().toString().padStart(2, '0'); // Agrega ceros a la izquierda si es necesario

    return year + '-' + month + '-' + day; // Formato: AAAA-MM-DD
}

//MENSAJE DE CONFIRMACION PARA GENERAR LOS DESCUENTOS DE FONDO DE AUXILIO
function confirmacion() {
    $("#confirmacionModal").modal();
}

function descuentaPrestamos() {
    $.ajax({
        type: "GET",
        url: "creditoFonacot/listarCuentasActivas",
        dataType: 'json',
        success: function (data) {
            comparaFechasCorte();
            toastr['success']("Descuento realizado");
            $("#confirmacionModal").modal("hide");
        },
        error: function (e) {
            alert(e);
        }
    });
}


function capturaCredito(id_trabajador) {
    window.location.href = 'descuentoFONACOT?id_trabajador=' + id_trabajador;
}

function importacionDescuentos() {
    window.location.href = 'importacionDescuentos';
}










