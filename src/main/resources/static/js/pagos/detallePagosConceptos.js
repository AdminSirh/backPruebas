document.addEventListener('DOMContentLoaded', () => {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var id_pago = urlParams.get('id_pago');
    var id_trabajador = urlParams.get('id_trabajador');
    obtenNomina(id_trabajador,id_pago);
});
// TABLA DE CONCEPTOS DESGLOSADOS DEPENDIENDO LA NOMINA DEL TRABAJADOR
function tablaConceptos(id_pago){
    var nomina = $("#nomina").val();
    
    var url = 
        nomina === "1" ? "pagos/listarCalculosPercepcionDeduccion1/" + id_pago :
        nomina === "2" ? "pagos/listarCalculosPercepcionDeduccion2/" + id_pago :
        nomina === "3" ? "pagos/listarCalculosPercepcionDeduccion3/" + id_pago :
        nomina === "4" ? "pagos/listarCalculosPercepcionDeduccion4/" + id_pago :
        nomina === "5" ? "pagos/listarCalculosPercepcionDeduccion5/" + id_pago :
        "";

    tabla_Conceptos = $('#tabla_Conceptos').dataTable({
        "ajax": {
            url: url,
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
            {data: "cat_Incidencias.cve_nomina"},
            {data: "cat_Incidencias.descripcion"},
            {data: "monto"},
            {data: "periodo_generacion"},
            {data: "periodo_generacion"}
        ]
    });
}

// OBTIENE LA NOMIMA DEL TRABAJADOR POR SU ID
function obtenNomina(id_trabajador,id_pago){
    $.ajax({
        type: "GET",
        url: "trabajador/buscarPlazaTrabajador/" + id_trabajador,
        dataType: 'json',
        success: function (data) {
            $("#nomina").val(data.data.cat_Tipo_Nomina.id_tipo_nomina);
            tablaConceptos(id_pago);
            obtenNeto(id_pago);
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Tipo Nómina : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

// REALIZA LAS SUMAS DE LAS PERCEPCIONES DEL TRABAJADOR Y LAS DEDUCCIONES PARA AL FINAL RESTAR Y OBTENER EL NETO
function obtenNeto(id_pago){
    var nomina = $("#nomina").val();
    var percepciones = [];
    var deducciones = [];
    console.log(id_pago);
    var url = 
        nomina === "1" ? "pagos/listarCalculosPercepcionDeduccion1/" + id_pago :
        nomina === "2" ? "pagos/listarCalculosPercepcionDeduccion2/" + id_pago :
        nomina === "3" ? "pagos/listarCalculosPercepcionDeduccion3/" + id_pago :
        nomina === "4" ? "pagos/listarCalculosPercepcionDeduccion4/" + id_pago :
        nomina === "5" ? "pagos/listarCalculosPercepcionDeduccion5/" + id_pago :
        "";
    
    $.ajax({
        type: "GET",
        url: url,
        dataType: 'json',
        success: function (data) {
            if (data !== undefined) {
                for (var i = 0; i < data.length; i++) {
                    if (data[i].cat_Incidencias.cat_tipo_incidencia.descripcion === "Calculo Percepción") {
                        percepciones.push(data[i].monto);

                    }else {
                        deducciones.push(data[i].monto);
                    }
                }
                var total_percepciones = percepciones.reduce((total, current) => total + current, 0);
                var total_deducciones = deducciones.reduce((total, current) => total + current, 0);
                var sueldo_neto = total_percepciones - total_deducciones;
                $("#percepciones").val(total_percepciones);
                $("#deducciones").val(total_deducciones);
                $("#sueldo_neto").val(sueldo_neto);
            }
                
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Tipo Nómina : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

function verPagos(){
    window.location.href = 'consultaPagos';
}