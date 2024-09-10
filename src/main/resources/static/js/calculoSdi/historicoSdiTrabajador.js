document.addEventListener('DOMContentLoaded', () => {
    const currentUrl = window.location.href;
    const urlParams = new URLSearchParams(new URL(currentUrl).search);
    const idTrabajador = urlParams.get('id_trabajador');
    const idBimestre = urlParams.get('id_bimestre');
    obtenerInformacionTrabajador(idTrabajador);
    obtenerInformacionPlazaTrabajador(idTrabajador);
    generarTablasCalculados(idTrabajador, idBimestre);
});
//Recupera información general del trabajador
function obtenerInformacionTrabajador(idTrabajador) {
    $.ajax({
        type: "GET",
        url: "trabajador/buscarTrabajador/" + idTrabajador,
        dataType: 'json',
        success: function (data) {
            $('#campo_expediente').val(data.data.cat_expediente.numero_expediente);
            $('#campo_nombre').val(data.data.persona.nombre + " " + data.data.persona.apellido_paterno + " " + data.data.persona.apellido_materno);
            $('#campo_prestaciones_si_no').val(data.data.prestaciones_si_no === 2 ? 'No' : 'Si');
        },
        error: function (e) {
            toastr["error"]("Ocurrió un error al obtener información del trabajador" + e, "Error", {progressBar: true, closeButton: true});
        }
    });
}
//Recupera información relevante de la plaza del trabajador
function obtenerInformacionPlazaTrabajador(idTrabajador) {
    $.ajax({
        type: "GET",
        url: "trabajador/buscarPlazaTrabajador/" + idTrabajador,
        dataType: 'json',
        success: function (data) {
            $('#campo_nomina').val(data.data.cat_Tipo_Nomina.desc_nomina);
            $('#id_nomina').val(data.data.cat_Tipo_Nomina.id_tipo_nomina);
            $('#campo_tipo_contrato').val(data.data.cat_tipo_contrato.descripcion);
            obtenerSalarioMinDiario();
        },
        error: function (e) {
            toastr["error"]("Ocurrió un error al obtener información del trabajador" + e, "Error", {progressBar: true, closeButton: true});
        }
    });
}

//Se busca del catalogo_incidencias el concepto con id 186 que corresponde al concepto 'Salario Mínimo'
function obtenerSalarioMinDiario() {
    let idNomina = $('#id_nomina').val();
    $.ajax({
        type: "GET",
        url: "propiedadesNomina/findSalMinPropiedad/" + idNomina,
        dataType: 'json',
        success: function (data) {
            $('#salario_minimo_diario').val('$' + data);
        },
        error: function (e) {
            toastr["error"]("Ocurrió un error al recuperar el salario mínimo para finiquito", "Error", {progressBar: true, closeButton: true});
        }
    });
}

//Se obtiene la clave nómina de la incidencia con base en la relación de la tabla relacion_calculos_sdi_claves
function obtenerClaveIncidencia(valorCalculado) {
    var resultadoClave;
    $.ajax({
        url: 'calculoSdi/encontrarRelacionCalculo/' + valorCalculado,
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function (data) {
            resultadoClave = data.cat_Incidencias.cve_nomina;
        },
        error: function (e) {
            console.error("Error al recuperar cálculos" + e);
        }
    });
    return resultadoClave;
}
//Se obtiene la descripción de la incidencia con base en la relación de la tabla relacion_calculos_sdi_claves
function obtenerDescIncidencia(valorCalculado) {
    var resultadoDesc;
    $.ajax({
        url: 'calculoSdi/encontrarRelacionCalculo/' + valorCalculado,
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function (data) {
            resultadoDesc = data.cat_Incidencias.descripcion;
        },
        error: function (e) {
            console.error("Error al recuperar cálculos" + e);
        }
    });
    return resultadoDesc;
}
//Retorna al home del sdi
function listadoTrabajadoresSdi() {
    window.location.href = 'sdiTrabajdores';
}
//Se procede a lostar y generar las tablas con los conceptos desglosados. Fijos variables y calculados
function generarTablasCalculados(idTrabajador, idBimestre) {
    $.ajax({
        url: 'calculoSdi/encontrarCalculoBimTrab/' + idBimestre + '/' + idTrabajador,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            const idNomina = $('#id_nomina').val();
            const fijos = data[0].tmp_Sdi_Fijo_Calculos_Bim;
            const variables = data[0].tmp_Sdi_Variable_Calculos_Bim;
            let diasDevengados = variables.valor_01;
//            let totalSalarioVariable = variables.valor_30;
//            let sdiVariableDiario = (totalSalarioVariable / diasDevengados).toFixed(2);
            //Se coloca la información en el encabezado de la página
            $('#dias_pagados').val(diasDevengados);
            $('#dias_devengados_bimestre').val(diasDevengados);
            const valoresFijos = [
                {numero: obtenerClaveIncidencia('fijo_01'), descripcion: obtenerDescIncidencia('fijo_01'), valor: fijos.fijo_01},
                {numero: obtenerClaveIncidencia('fijo_02'), descripcion: obtenerDescIncidencia('fijo_02'), valor: fijos.fijo_02},
                {numero: obtenerClaveIncidencia('fijo_03'), descripcion: obtenerDescIncidencia('fijo_03'), valor: fijos.fijo_03},
                {numero: obtenerClaveIncidencia('fijo_04'), descripcion: obtenerDescIncidencia('fijo_04'), valor: fijos.fijo_04},
                {numero: obtenerClaveIncidencia('fijo_05'), descripcion: obtenerDescIncidencia('fijo_05'), valor: fijos.fijo_05},
                {numero: obtenerClaveIncidencia('fijo_06'), descripcion: obtenerDescIncidencia('fijo_06'), valor: fijos.fijo_06},
                {numero: obtenerClaveIncidencia('fijo_07'), descripcion: obtenerDescIncidencia('fijo_07'), valor: fijos.fijo_07},
                {numero: obtenerClaveIncidencia('fijo_08'), descripcion: obtenerDescIncidencia('fijo_08'), valor: fijos.fijo_08},
                {numero: obtenerClaveIncidencia('fijo_09'), descripcion: obtenerDescIncidencia('fijo_09'), valor: fijos.fijo_09}
            ];

            let valoresVariables = [
                {numero: obtenerClaveIncidencia('valor_12'), descripcion: obtenerDescIncidencia('valor_12'), valor: variables.valor_09},
                {numero: obtenerClaveIncidencia('valor_13'), descripcion: obtenerDescIncidencia('valor_13'), valor: variables.valor_13},
                {numero: obtenerClaveIncidencia('valor_14'), descripcion: obtenerDescIncidencia('valor_14'), valor: variables.valor_14},
                {numero: obtenerClaveIncidencia('valor_05'), descripcion: obtenerDescIncidencia('valor_05'), valor: variables.valor_05},
                {numero: obtenerClaveIncidencia('valor_27'), descripcion: obtenerDescIncidencia('valor_27'), valor: variables.valor_27},
                {numero: obtenerClaveIncidencia('fijo_04'), descripcion: obtenerDescIncidencia('fijo_04'), valor: fijos.fijo_04},
                {numero: obtenerClaveIncidencia('valor_04'), descripcion: obtenerDescIncidencia('valor_04'), valor: variables.valor_04}
            ];
            //Si la nómina es de cofianza entonces se agrega el valor_09 que corresponde a la clave 07, descansos lab cfza
            if (idNomina === '3') {
                valoresVariables = valoresVariables.filter(item => item.numero !== obtenerClaveIncidencia('valor_12'));
                valoresVariables.push(
                        {numero: obtenerClaveIncidencia('valor_09'), descripcion: obtenerDescIncidencia('valor_09'), valor: variables.valor_09}
                );
            }
            //Si la nómina es de transportacion entonces se agregan los conceptos considerados para la nómina
            if (idNomina === '2') {
                valoresVariables = valoresVariables.filter(item => item.numero !== obtenerClaveIncidencia('valor_12'));
                valoresVariables.push(
                        {numero: obtenerClaveIncidencia('valor_11'), descripcion: obtenerDescIncidencia('valor_11'), valor: variables.valor_11}
                );
                valoresVariables.push(
                        {numero: obtenerClaveIncidencia('valor_12'), descripcion: obtenerDescIncidencia('valor_12'), valor: variables.valor_12}
                );
                valoresVariables.push(
                        {numero: obtenerClaveIncidencia('valor_17'), descripcion: obtenerDescIncidencia('valor_17'), valor: variables.valor_17}
                );
                valoresVariables.push(
                        {numero: obtenerClaveIncidencia('valor_16'), descripcion: obtenerDescIncidencia('valor_16'), valor: variables.valor_16}
                );

            }
            const valoresCalculados = [
                {descripcion: 'Suma Variables (Diaria)', valor: variables.valor_30},
                {descripcion: 'Excedente Estim. x Asist. Vales', valor: variables.valor_28},
                {descripcion: 'Diferencia de sueldo', valor: variables.valor_34},
                {descripcion: 'Diferencia de Fondo de Ahorro', valor: variables.valor_25},
                {descripcion: 'Diferencia de Prima de Vacaciones', valor: variables.valor_26},
                {descripcion: 'Suma Variables Retroactivo', valor: 0.0}, //Revisar
                {descripcion: 'S.D.I. Base de Cotización', valor: variables.valor_29},
                {descripcion: '', valor: ''},
                {descripcion: 'S.D.I. (Fijo)', valor: fijos.fijo_10},
                {descripcion: 'S.D.I. (Variable Diario)', valor: variables.valor_30},
                {descripcion: 'Excedente Estímulo x Puntualidad', valor: variables.valor_27},
                {descripcion: 'S.D.I. Mixto', valor: variables.valor_32},
                {descripcion: '', valor: ''},
                {descripcion: 'Número de Quinquenios', valor: 0.0}, //Revisar
                {descripcion: 'Días totales de Incapacidad', valor: variables.valor_31},
                {descripcion: 'Antiguedad en Años', valor: 0.0}, //Revisar
                {descripcion: 'Días para Prima Vac. Por Antig.', valor: variables.valor_35}
            ];
            //Por cada elemmento del objeto valores fijos...
            valoresFijos.forEach(({ numero, descripcion, valor }) => {
                //Se agrega una fila a la tabla
                agregarFilaFijos(numero, descripcion, redondearADosDecimales(valor));
            });
            //Se remarca el total del sdi en negritas
            agregarFilaFijos('', 'Total S.D.I (FIJO)', redondearADosDecimales(fijos.fijo_10), true);
            //Por cada elemento del objeto valores variables...
            valoresVariables.forEach(({ numero, descripcion, valor }) => {
                //Se agrega una fila a la tabla
                agregarFilaVariables(numero, descripcion, redondearADosDecimales(valor));
            });
            //Por cada elemento calculado...
            valoresCalculados.forEach(({descripcion, valor }) => {
                //Se agrega una fila a la tabla
                agregarFilaCalculados(descripcion, redondearADosDecimales(valor));
            });
        },
        error: function (e) {
            toastr["error"]("Error al recuperar cálculos" + e, "Error", {progressBar: true, closeButton: true});
        }
    });
}
//Redondea el valor ingresado a dos decimales, si no se recibe un valor se retorna la cadena vacía
function redondearADosDecimales(valor) {
    if (valor === '' || valor === null || valor === 0.0) {
        return '';
    }
    return parseFloat(valor).toFixed(2);
}
//Agrega una fila a la tabla de html en relación a los conceptos fijos
function agregarFilaFijos(numero, descripcion, valor, esTotal = false) {
    const filaHTML = `<tr><td>${numero}</td><td${esTotal ? '><b>' : '>'}${descripcion}${esTotal ? '</b>' : ''}</td><td${esTotal ? '><b>' : '>'}${valor}${esTotal ? '</b>' : ''}</td></tr>`;
    $('#tabla_fijos tbody').append(filaHTML);
}
//Añade una fila a la tabla de html en relación a los conceptos variables
function agregarFilaVariables(numero, descripcion, valor, esTotal = false) {
    const filaHTML = `<tr><td>${numero}</td><td${esTotal ? '><b>' : '>'}${descripcion}${esTotal ? '</b>' : ''}</td><td${esTotal ? '><b>' : '>'}${valor}${esTotal ? '</b>' : ''}</td></tr>`;
    $('#tabla_variables tbody').append(filaHTML);
}
//Agrega una fila a la tabla de html en relación a los conceptos calculados 
function agregarFilaCalculados(descripcion, valor, esTotal = false) {
    const filaHTML = `<tr><td>${descripcion}</td><td${esTotal ? '><b>' : '>'}${valor}${esTotal ? '</b>' : ''}</td></tr>`;
    $('#tabla_calculados tbody').append(filaHTML);
}