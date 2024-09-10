//Document ready en arrow
$(() => {
    /******************************************************
     *                    COMBO MESES
     ******************************************************/
    let meses = [
        "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
        "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    ];
    let selectMeses = $('#cmbMeses');

    for (let i = 0; i < meses.length; i++) {
        let option = $('<option>');
        option.text(meses[i]);
        option.val(i + 1);
        selectMeses.append(option);
    }

    /******************************************************
     *                    COMBO AÑOS
     ******************************************************/
    let fechaActual = new Date();
    let anioActual = fechaActual.getFullYear();
    let selectAnio = $('#anio_captura');
    let selectCmbAnio = $('#cmbAnio');

    for (let i = anioActual; i >= anioActual - 100; i--) {
        let option = $('<option>');
        option.text(i);
        option.val(i);
        selectAnio.append(option);
        //selectCmbAnio.append(option);
    }

    for (let i = anioActual; i >= anioActual - 100; i--) {
        let option = $('<option>');
        option.text(i);
        option.val(i);
        selectCmbAnio.append(option);
    }
    /******************************************************
     *            DETECCION DE SELECCION DE REPORTE 
     ******************************************************/
    //Labels de los combos 
    // Etiquetas de los combos
    const labelFechaCaptura = $('#labelFechaCaptura');
    const fechaCaptura = $('#fecha_captura');
    const labelMes = $('#labelmes');
    const cmbMeses = $('#cmbMeses');
    const cmbAnio = $('#cmbAnio');
    const lblAnioCaptura = $('#lbl_anio_captura');
    const anioCaptura = $('#anio_captura');

// Función de cambio del elemento cmbTipoReporte
    $('#cmbTipoReporte').change(function () {
        const selectedOption = $(this).val();
        // Ocultar todos los elementos
        labelFechaCaptura.hide();
        fechaCaptura.hide();
        labelMes.hide();
        cmbMeses.hide();
        cmbAnio.hide();
        lblAnioCaptura.hide();
        anioCaptura.hide();

        // Mostrar elementos según la opción seleccionada
        if (selectedOption === 'opcion1') {
            labelFechaCaptura.show();
            fechaCaptura.show();
        } else if (selectedOption === 'opcion2') {
            labelMes.show();
            cmbMeses.show();
            cmbAnio.show();
        } else if (selectedOption === 'opcion3') {
            lblAnioCaptura.show();
            anioCaptura.show();
        }
    });

    // Ocultar los campos al cargar la página
    labelFechaCaptura.hide();
    fechaCaptura.hide();
    labelMes.hide();
    cmbMeses.hide();
    lblAnioCaptura.hide();
    anioCaptura.hide();
    cmbAnio.hide();

    /******************************************************
     *                   BOTON DESCARGA 
     ******************************************************/
    $('#btnDescarga').click(function () {
        const selectedOption = $('#cmbTipoReporte').val();
        let tipoReporte = $('#cmbTipoReporte').val();
        //Comprobación de selección de reporte
        if (tipoReporte === 'default') {
            toastr["warning"]("Selecciona un tipo de reporte a generar", {progressBar: true, closeButton: true});
        }
        // Obtención de valores de inputs visibles
        if (selectedOption === 'opcion1') {
            const fechaCaptura = $('#fecha_captura').val();
            generarDescarga(fechaCaptura, null, null);
        } else if (selectedOption === 'opcion2') {
            const cmbMeses = $('#cmbMeses').val();
            const cmbAnio = $('#cmbAnio').val();
            generarDescarga(null, cmbMeses, cmbAnio);
        } else if (selectedOption === 'opcion3') {
            const anioCaptura = $('#anio_captura').val();
            generarDescarga(null, null, anioCaptura);
        } else if (selectedOption === 'opcion4') {
            generarDescarga(null, null, null);
        }
    });
});

const generarDescarga = (fecha, mes, anio) => {
    console.log(fecha, mes, anio);
    /*Deteccion de inputs recibidos para generar la desacrga con los parámetros correspondientes
     Reporte día en especifico */
    if (mes === null & anio === null & fecha !== null) {
        console.log(fecha);
        let url;
        let nombreDescarga;
        llamadaAjax(url, nombreDescarga);
        //Reporte mensual de un año en específico
    } else if (fecha === null & mes !== null & anio !== null) {
        console.log(mes, anio);
        let url;
        let nombreDescarga;
        llamadaAjax(url, nombreDescarga);
        //Reporte anual
    } else if (fecha === null & mes === null & anio !== null) {
        console.log(anio);
        let url;
        let nombreDescarga;
        llamadaAjax(url, nombreDescarga);
        //Histórico
    } else if (fecha === null & mes === null & anio === null) {
        let url = 'report/movimientos/movimientoPersonalHistorico?tipo=PDF';
        let nombreDescarga = "HistoricoMovimientosPlazas.pdf";
        llamadaAjax(url, nombreDescarga);
    }
};

const llamadaAjax = (url, nombreDescarga) => {
    $.ajax({
        type: "GET",
        contentType: 'application/pdf',
        url: url,
        xhrFields: {
            responseType: 'blob'
        },
        success: (blob) => {
            if ($.isEmptyObject(blob)) {
                toastr["warning"]("No se encontró información...", "Aviso", {progressBar: true, closeButton: true});
            }
            toastr["success"]("Reporte generado con éxito", "Aviso", {progressBar: true, closeButton: true});
            const link = document.createElement('a');
            link.href = window.URL.createObjectURL(blob);
            link.download = nombreDescarga;
            link.click();
        },
        error: (e) => {
            toastr["warning"]("Error al recuperar datos: " + e, "Error", {progressBar: true, closeButton: true});
        }

    });
};
