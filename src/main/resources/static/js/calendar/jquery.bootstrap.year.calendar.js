let colorMapping = {};

document.addEventListener('DOMContentLoaded', () => {
    cmb_incidencias();
    loadCalendar();
    loadIncidenceDescription();
    incidenceColor();
});

$('#cmb_incidencias').on('change', function () {
    const selectedValue = $(this).val();
    loadCalendar(selectedValue);
});

function incidenceColor() {
    //Llamada para recuperar colores de la base de datos
    $.ajax({
        type: "GET",
        url: "catalogos/listarDatosIncidencias",
        dataType: 'json',
        async: false,
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontró información de para los colores de las incidencias...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                data.forEach(function (data) {
                    let id_incidencia = data.id_incidencia;
                    let color_hex = data.color_kardex || "#808080"; // Si no hay color, se coloca en gris
                    colorMapping[id_incidencia] = color_hex;
                });
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Orden : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

function loadIncidenceDescription() {
    // Se listan las incidencias que estan relacioinadas con el kardex
    tablaIncidenciasKardex = $('#tablaIncidenciasKardex').dataTable({
        "ajax": {
            url: "catalogos/listarIncidenciasKardex",
            method: 'GET',
            dataSrc: ''
        },
        responsive: true,
        bProcessing: true,
        bPaginate: false,
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
            {data: "descripcion"},
            {data: "inicial_descripcion"},
            {
                data: "id_incidencia",
                sTitle: "Color",
                width: 100,
                visible: true,
                render: function (id_incidencia, type, row) {
                    // Obtén el color del mapeo
                    var color = colorMapping[id_incidencia];
                    // Crea un div con el color de fondo
                    var colorDiv = '<div style="width: 30px; height: 30px; background-color: ' + color + '"></div>';

                    return colorDiv;
                }
            }
        ]
    });
}
//Se añade la funcion obtener iniciales incidencias al cambiar de año
$(document).on("click", function (event) {
    if (event.target.classList.contains('year-neighbor')) {
        obtenerInicialesIncidencias();
    }
    if (event.target.classList.contains('year-neighbor2')) {
        obtenerInicialesIncidencias();
    }
});
$(document).on("click", ".next", function () {
    setTimeout(function () {
        obtenerInicialesIncidencias();
    }, 200);
});
$(document).on("click", ".prev", function () {
    setTimeout(function () {
        obtenerInicialesIncidencias();
    }, 200);
});

let calendar = null;

//Appending the list of incidences inside the combo-box
function cmb_incidencias(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarIncidenciasKardex",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontró información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#cmb_incidencias').empty().append("<option value=''>* Todas las incidencias</option>");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id_incidencia === id) ? "selected" : "";
                        var option = $('<option value="' + data[x].id_incidencia + '"' + vselected + '>' + data[x].descripcion + '</option>');
                        // Si se desea aplicar colores a las opciones de las incidencias
//                        var color = colorMapping[data[x].id_incidencia];
//                        if (color) {
//                            option.css('color', color);
//                        }
                        $('#cmb_incidencias').append(option);
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Orden : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

function editEvent(event) {
    $('#event-modal input[name="event-index"]').val(event ? event.id : '');
    $('#event-modal input[name="event-name"]').val(event ? event.name : '');
    $('#event-modal input[name="event-location"]').val(event ? event.location : '');
    $('#event-modal input[name="folio"]').val(event ? event.folio : '');
    $('#event-modal input[name="bis"]').val(event ? event.bis : '');
    $('#event-modal input[name="event-start-date"]').datepicker('update', event ? event.startDate : '');
    $('#event-modal input[name="event-end-date"]').datepicker('update', event ? event.endDate : '');
    $('#event-modal').modal();
}

function deleteEvent(event) {
    var dataSource = calendar.getDataSource();

    calendar.setDataSource(dataSource.filter(item => item.id !== event.id));
}

function saveEvent() {
    var event = {
        id: $('#event-modal input[name="event-index"]').val(),
        name: $('#event-modal input[name="event-name"]').val(),
        location: $('#event-modal input[name="event-location"]').val(),
        folio: $('#event-modal input[name="folio"]').val(),
        bis: $('#event-modal input[name="bis"]').val(),
        startDate: $('#event-modal input[name="event-start-date"]').datepicker('getDate'),
        endDate: $('#event-modal input[name="event-end-date"]').datepicker('getDate')
    };

    var dataSource = calendar.getDataSource();

    if (event.id) {
        for (var i in dataSource) {
            if (dataSource[i].id == event.id) {
                dataSource[i].name = event.name;
                dataSource[i].location = event.location;
                dataSource[i].location = event.folio;
                dataSource[i].location = event.bis;
                dataSource[i].startDate = event.startDate;
                dataSource[i].endDate = event.endDate;
            }
        }
    } else
    {
        var newId = 0;
        for (var i in dataSource) {
            if (dataSource[i].id > newId) {
                newId = dataSource[i].id;
            }
        }

        newId++;
        event.id = newId;

        dataSource.push(event);
    }

    calendar.setDataSource(dataSource);
    $('#event-modal').modal('hide');
}

function loadCalendar(selectedValue) {
    let url;
    //var currentYear = new Date().getFullYear();
    let dataSource;
    let id_worker = new URLSearchParams(decodeURIComponent(window.location.search)).get('id_trabajador');
    if (selectedValue === undefined || selectedValue === '') {
        url = "incidencias/buscarIncidenciasAutorizadas/" + id_worker;
    } else {
        url = "incidencias/buscarIncidenciaEspecificaTrabajador/" + id_worker + "/" + selectedValue;
    }

    $.ajax({
        type: "GET",
        url: url,
        dataType: "json",
        success: (data) => {
            const timeZoneOffsetMs = new Date().getTimezoneOffset() * 60 * 1000;
            dataSource = data.map(function (item) {
                //Selecting the color for each incidence
                const color = colorMapping[item.cat_incidencias.id_incidencia] || "purple"; // Default color if the incidence is not present
                //Settiing the date to be the correct one acording to the registers comming from the database
                const startDate = new Date(item.fecha_inicio);
                startDate.setDate(startDate.getDate() + 1);
                const endDate = new Date(item.fecha_fin);
                endDate.setDate(endDate.getDate() + 1);
                //Creating the object with all the incidences to sent them to the incidences table
                return {
                    id: item.id_incidencia,
                    name: item.cat_incidencias.descripcion,
                    location: item.observaciones,
                    folio: item.folio,
                    bis: item.bis,
                    startDate: startDate,
                    endDate: endDate,
                    color: color,
                    inicialDescripcion: item.inicial_descripcion
                };
            });
            //The datasource is sent to this function to show al the incidences with the corresponding color scheme
            initializeCalendar(dataSource);
            obtenerInicialesIncidencias();
        },
        error: (e) => {
            alert(e);
        }
    });

    function initializeCalendar(dataSource) {
        calendar = new Calendar('#calendar', {
            enableContextMenu: true,
            enableRangeSelection: true,
            language: 'es',
            contextMenuItems: [
                {
                    text: 'Ver Detalle',
                    click: editEvent
                },
//            {
//                text: 'Delete',
//                click: deleteEvent
//            }
            ],
            selectRange: function (e) {
                editEvent({startDate: e.startDate, endDate: e.endDate});
            },
            mouseOnDay: function (e) {
                if (e.events.length > 0) {
                    var content = '';

                    for (var i in e.events) {
                        //Se añaden las figuras en el hover del mouse para los días con varias incidencias o para los días con una sola incidencia también
                        let figure = adminLogDetectRole(e.events[i]);
                        //Si la figura es idefinida es porque el creador lo hizo con otro rol o no se encontró el id de la incidencia
                        if (figure === undefined) {
                            figure = '';
                        }
                        content += '<div class="event-tooltip-content">'
                                + '<div class="event-name" style="color:' + e.events[i].color + '">' + e.events[i].name + '</div>'
                                + '<div class="event-location">' + (e.events[i].location ?? '') + '</div>'
                                + figure
                                + '</div>';
                    }

                    $(e.element).popover({
                        trigger: 'manual',
                        container: 'body',
                        html: true,
                        content: content
                    });

                    $(e.element).popover('show');
                }
            },
            mouseOutDay: function (e) {
                if (e.events.length > 0) {
                    $(e.element).popover('hide');
                }
            },
            dayContextMenu: function (e) {
                $(e.element).popover('hide');
            },
            dataSource: dataSource
        });
        //This was added so the user is advised to rick click in  incidence to show more details
        $(".day").on("click", function () {
            toastr.options = {
                "closeButton": false,
                "debug": false,
                "newestOnTop": false,
                "progressBar": false,
                "positionClass": "toast-top-right",
                "preventDuplicates": true,
                "onclick": null,
                "showDuration": "300",
                "hideDuration": "1000",
                "timeOut": "5000",
                "extendedTimeOut": "1000",
                "showEasing": "swing",
                "hideEasing": "linear",
                "showMethod": "fadeIn",
                "hideMethod": "fadeOut"
            };
            toastr["warning"]("Para ver detalles de incidencias selecciónalas con el clic derecho", "Aviso");
        });

        $('#save-event').click(function () {
            saveEvent();
        });
    }
}

function adminLogDetectRole(evento) {
    let figuraAColocar;
    // Listado de operaciones a buscar
    const operacionesABuscar = ['guardarIncidenciaTrabajador', 'guardarIncidenciaTrabajadorVacaciones'];
    // Obtener la fecha de inicio del año actual
    const startDate = new Date(new Date().getFullYear(), 0, 1).toISOString().split('T')[0];
    // Obtener la fecha de fin del año actual
    const endDate = new Date(new Date().getFullYear(), 11, 31).toISOString().split('T')[0];
    operacionesABuscar.forEach(operacionABuscar => {
        $.ajax({
            type: "GET",
            url: "adminLog/buscarOperacionPorFecha/" + operacionABuscar + "/" + startDate + "/" + endDate,
            dataType: 'json',
            async: false,
            success: function (dataAdminLog) {
                //Recorre cada elemento del log con la operacion guardar la incidencia desde el inicio hasta el fin de año
                dataAdminLog.forEach(function (elemento) {
                    var idIncidenciaGenerada = JSON.parse(elemento.id_incidencia_generada.split('data=')[1].split(')')[0]);
                    if (evento.id === idIncidenciaGenerada) {
                        if (elemento.creators_role === "ROLE_PERSONAL") {
                            figuraAColocar = '<i class="fa fa-circle" aria-hidden="true"></i> Personal';
                        } else if (elemento.creators_role === "ROLE_NOMINA") {
                            figuraAColocar = '<i class="fa fa-square" aria-hidden="true"></i> Nómina';
                        }
                    }
                });
            },
            error: function (e) {
                toastr["warning"]("Error al recuperar roles involucrados : " + e, " Error", {progressBar: true, closeButton: true});
            }
        });
        // Si figuraAColocar se establece, salimos del bucle forEach
        if (figuraAColocar) {
            return false;
        }
    });
    return figuraAColocar;
}

let initialIncidences = {};
function obtenerInicialesIncidencias() {
    //Llamada para recuperar las iniciales de las incidencias de la base de datos
    $.ajax({
        type: "GET",
        url: "catalogos/listarDatosIncidencias",
        dataType: 'json',
        async: false,
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontró información de para los colores de las incidencias...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                data.forEach(function (data) {
                    let id_incidencia = data.id_incidencia;
                    let inicial_descripcion = data.inicial_descripcion || "#808080";
                    initialIncidences[id_incidencia] = inicial_descripcion;
                });
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Iniciales de las incidencias : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
    const rgbColorMapping = {};
    for (const key in colorMapping) {
        if (colorMapping.hasOwnProperty(key)) {
            const hexColor = colorMapping[key];
            const rgbColor = hexToRgb(hexColor);
            rgbColorMapping[key] = rgbColor;
        }
    }
    // Crear un objeto para rastrear los colores ya procesados
    const processedColors = {};
    // Iterar sobre los colores y agregar subíndice solo una vez por color
    Object.values(rgbColorMapping).forEach(value => {
        const colorKey = Object.keys(rgbColorMapping).find(key => rgbColorMapping[key] === value);
        if (!processedColors[colorKey]) {
            let targetTd = $('td.day[style*="box-shadow: ' + value + ' 0px -4px 0px 0px inset;"]');

            const subIndexValue = initialIncidences[colorKey];
            const subIndex = $('<sub>').text(subIndexValue);
            subIndex.css({
                'color': 'black',
                'font-weight': 'bold',
                'position': 'relative',
                'top': '10px',
                'font-size': '9.7px'
            });
            targetTd.prepend(subIndex);

            // Marcar el color como procesado
            processedColors[colorKey] = true;
        }
    });

// Selecciona todos los elementos <td> con la clase "day"
    const targetTds = $('td.day');

// Itera a través de los elementos <td>, caso en el que dos incidencias o más coincidan el mismo día
    targetTds.each(function () {
        const td = $(this);
        const boxShadowStyle = td.css('box-shadow');

        // Verifica si el estilo de sombra de caja contiene al menos dos asignaciones rgb
        const rgbMatches = boxShadowStyle.match(/rgb\(\d+,\s*\d+,\s*\d+\)/g);

        if (rgbMatches && rgbMatches.length >= 2) {
            // Crea un elemento <i> con las clases de Font Awesome
            const icon = $('<i>').addClass('fa fa-plus-circle').attr('aria-hidden', 'true');

            // Aplica estilos si es necesario
            icon.css({
                'color': 'black',
                'font-weight': 'bold'
            });

            // Agrega el icono al elemento <td>
            td.prepend(icon);
        }
    });
}
//Convierte de hexadecimal a rgb
function hexToRgb(hex) {
    const bigint = parseInt(hex.slice(1), 16);
    const r = (bigint >> 16) & 255;
    const g = (bigint >> 8) & 255;
    const b = bigint & 255;
    return `rgb(${r}, ${g}, ${b})`;
}
