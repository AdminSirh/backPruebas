document.addEventListener('DOMContentLoaded', () => {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var id_pago = urlParams.get('id_pago');
    var id_trabajador = urlParams.get('id_trabajador');
    obtenNomina(id_trabajador,id_pago);
});

// TABLA DE INCIDENCIAS DESGLOSADOS DEPENDIENDO LA NOMINA DEL TRABAJADOR
function tablaIncidencias(id_pago){
    
    var nomina = $("#nomina").val();
    var url = 
        nomina === "1" ? "pagos/listarPercepcionDeduccion1/" + id_pago :
        nomina === "2" ? "pagos/listarPercepcionDeduccion2/" + id_pago :
        nomina === "3" ? "pagos/listarPercepcionDeduccion3/" + id_pago :
        nomina === "4" ? "pagos/listarPercepcionDeduccion4/" + id_pago :
        nomina === "5" ? "pagos/listarPercepcionDeduccion5/" + id_pago :
        "";
    
    tabla_Incidencias = $('#tabla_Incidencias').dataTable({
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
            {data: "valor"},
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
            tablaIncidencias(id_pago);
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Tipo Nómina : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}



function verPagos(){
    window.location.href = 'consultaPagos';
}