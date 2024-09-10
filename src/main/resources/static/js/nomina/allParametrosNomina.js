document.addEventListener('DOMContentLoaded', () => {
    listarNominas();
    validacion();
    nominasCargadas();
});
//BLOQUEA O DESBLOQUEA LOS BOTONES SI EL SELECT DE NOMINA ESTÁ SELECCIONADO
function validacion(){
    var id_nomina = $("#nomina").val();
    var fileInput = document.getElementById('fileInput');
    var guarda = document.getElementById('guarda');
    if (id_nomina === "" || id_nomina === null) {
        fileInput.disabled = true;
        guarda.disabled = true;
    }else{
        fileInput.disabled = false;
        guarda.disabled = false;
    }
}
//LISTA LOS TIPOS DE NOMINAS 
function listarNominas(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarTipoNominas",
        dataType: 'json',
    success: function (data) {
            if ($.isEmptyObject(data)) {
            toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $("#nomina").empty().append("<option value=''>* Nomina </option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id_tipo_nomina === id) ? vselected = "selected" : vselected = " ";
                        $("#nomina").append('<option value="' + data[x].id_tipo_nomina + '"' + vselected + '>' + data[x].desc_nomina + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Nómina: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}
//OBTIENE EL CSV SELECCIONADO Y LO ACOMODA EN FILAS, VALIDA QUE HAYA SIDO SELECCIONADO EL FILE E INVOCA "GUARDA PERIODOS"
function obtenFile() {
    var file = $('#fileInput').prop('files')[0];
    var reader = new FileReader();
    if (file !== undefined) {
        reader.onload = function(event) {
        var csv = event.target.result;
        var rows = csv.split('\n');
        var header = rows[0].split(',');
        var data = [];

        for (var i = 1; i < rows.length; i++) {
            var row = rows[i].split(',');
            var rowData = {};

            for (var j = 0; j < header.length; j++) {
                rowData[header[j]] = row[j];
            }

            data.push(rowData);
        }
        guardaPeriodos(rows);
        };

        reader.readAsText(file);
    }
    else {
        toastr["warning"]("Favor de cargar el documento");
        $("#nomina").val("");
        validacion();
        $("#confirmacionModal").modal('hide');
    }

}
//SEPARA EL ARREGLO POR COMAS Y DESGLOSA EN CAMPOS LA INFORMACIÓN, CREA LA LISTA DE JSONS Y GUARDA CON EL SAVEALL, VALIDA QUE LA NOMINA CORRESPONDA CON EL CSV
function guardaPeriodos(periodos){
    let jsonArr = [];   
    for (var i = 1; i < periodos.length -1; i++) {
        var valores = periodos[i].split(',');
        var mes = valores[0];
        var anio = valores[1];
        var periodo = valores[2];
        var fecha_inicial = valores[3];
        var fecha_final = valores[4];
        var fecha_inicial_sueldo = valores[5];
        var fecha_final_sueldo = valores[6];
        var dias_incluidos = valores[7];
        var fecha_limite = valores[8];
        var fecha_corte = valores[9];
        var fecha_entrega_caja = valores[10];
        var fecha_entrega_caja_anticipada = valores[11];
        var fecha_pago = valores[12];
        var pagado = valores[13];
        var id_nomina = valores[14];
        var estimulo_puntualidad = valores[15];
        var estimulo_apego_reglamento = valores[16];
        var ayuda_transporte = valores[17];
        var quinquenio_mensual = valores[18];
        var vales_despensa = valores[19];
        var aguinaldo = valores[20];
        var especial = valores[21];
        var fecha_movimiento = valores[22];
        var fecha_inicial_DAS = valores[23];
        var fecha_final_DAS = valores[24];
        var fecha_inicial_est_vac = valores[25];
        var fecha_final_est_vac = valores[26];
        var fecha_imss = valores[27];
        var pago_pens = valores[28];
         
        let jsonObj = {mes: mes, anio: anio, periodo: periodo, fecha_inicial: fecha_inicial, fecha_final: fecha_final, fecha_inicial_sueldo:fecha_inicial_sueldo,
            fecha_final_sueldo: fecha_final_sueldo, dias_incluidos: dias_incluidos, fecha_limite: fecha_limite !== "" ? fecha_limite : "01/01/2099", fecha_corte: fecha_corte, fecha_entrega_caja: fecha_entrega_caja !== "" ? fecha_entrega_caja : "01/01/2099", fecha_entrega_caja_anticipada: fecha_entrega_caja_anticipada !== "" ? fecha_entrega_caja_anticipada : "01/01/2099",
            fecha_pago: fecha_pago, pagado: pagado, id_nomina: id_nomina, estimulo_puntualidad: estimulo_puntualidad, estimulo_apego_reglamento: estimulo_apego_reglamento, ayuda_transporte: ayuda_transporte,
            quinquenio_mensual: quinquenio_mensual, vales_despensa: vales_despensa, aguinaldo: aguinaldo, especial: especial, fecha_movimiento: fecha_movimiento !== "" ? fecha_movimiento : "01/01/2099", fecha_inicial_das: fecha_inicial_DAS !== "" ? fecha_inicial_DAS : "01/01/2099",
            fecha_final_das: fecha_final_DAS !== "" ? fecha_final_DAS : "01/01/2099", fecha_inicial_est_vac: fecha_inicial_est_vac !== "" ? fecha_inicial_est_vac : "01/01/2099", fecha_final_est_vac: fecha_final_est_vac !== "" ? fecha_final_est_vac : "01/01/2099" ,fecha_imss: fecha_imss, pago_pens: pago_pens};
        
        jsonArr.push(jsonObj);
    };
    
    var nomina_csv = jsonArr[0].id_nomina;
    var id_nomina = $("#nomina").val();
    if (id_nomina === nomina_csv) {
        $.ajax({
            type: "POST",
            url: 'periodosPago/guardaPeriodos',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(jsonArr),
            success: function (data) {
                if ($.isEmptyObject(data)) {
                    toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true, timeOut: 5000});
                }else{
                    toastr['success'](data.data, "Se ha agregado correctamente el periodo");
                    $("#nomina").val("");
                    validacion();
                    nominasCargadas();
                    $("#confirmacionModal").modal('hide');
                }
            },
            error: function (e) {
                toastr["warning"]("Error al guardar  los periodos : " + e, " error", {progressBar: true, closeButton: true, timeOut: 5000});
            }
        });
    }else{
        toastr["warning"]("Selecciona la nómina correspondiente al CSV ");
        $("#nomina").val("");
        validacion();
        $("#confirmacionModal").modal('hide');
    }

}
//ACTIVA EL MODAL Y BOTÓN DE CONFIRMACIÓN
function confirmacion() {
    $("#confirmacionModal").modal();
}
//MUESTRA LOS PERIODOS DE LAS NOMINAS CARGADAS EN LA TABLA PARA NO REPETIRLAS
function nominasCargadas(){
    var label_nominas = document.getElementById('nominas_cargadas');
    $.ajax({
        type: "GET",
        url: "periodosPago/listarPeriodosCargados",
        dataType: 'json',
    success: function (data) {
            if (data !== "") {
                var resultado = data.map(function(elemento) {
                    return "" + elemento + " ";
                }).join(',       ');
                label_nominas.textContent = resultado;
            }else{
                label_nominas.textContent = "No hay periodos cargados";
            }
        },
        error: function (e) {
            toastr["warning"]("Error los Periodos de Pago", {progressBar: true, closeButton: true});
        }
    });
}