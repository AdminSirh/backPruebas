document.addEventListener('DOMContentLoaded', () => {
    tabla();
});

//LISTA A LOS TRABAJADORES CON NOMINA PARA PODER APLICAR DESCUENTOS
function tabla() {
    $tablaTrabajadores = $('#tabla_Trabajadores').dataTable({

        "ajax": {
            url: "trabajador/listarTrabajadoresSeparacion",
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

            {data: "numero_expedienteDTO"},
            {data: "nombreDTO"},
            {data: "apellido_paternoDTO"},
            {data: "apellido_maternoDTO"},
            {data: "rfcDTO"},
            {data: "desc_areaDTO"},
            {data: "puestoDTO"},
            {data: "nominaDTO"},
            {data: "imssDTO"},
            {data: "fecha_nacimientoDTO", render: function (data, type, row) {
                    var fechaCaptura = new Date(data);
                    return fechaCaptura.toLocaleDateString();
                }},
            {data: "fecha_ingresoDTO", render: function (data, type, row) {
                    var fechaCaptura = new Date(data);
                    return fechaCaptura.toLocaleDateString();
                }},
            {data: "", sTitle: "Cálculo Separación", width: 100, visible: true, render: function (d, t, r) {
                    var he;
                    he = '<button type="button" class="btn btn-outline-primary" onclick="calcular(' + r.id_trabajadorDTO + ')"><span class="fa fa-edit"></span>Calcular</button>';
                    return he;
                }
            }]
    });
}


function calcular(id_trabajador) {
    window.location.href = 'calculoSeparacion?id_trabajador=' + id_trabajador;
}


