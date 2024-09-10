document.addEventListener('DOMContentLoaded', () => {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParametros = new URLSearchParams(d);
    var id_pago = urlParametros.get('id_pago');
    var id_trabajador = urlParametros.get('id_trabajador');
    tabs(id_pago, id_trabajador);
    
});

const tabs = (id_pago, id_trabajador) => {
    $('#myTab').html(`
        <ul class='nav nav-tabs' role='tablist'>
            <li class='btn btn-primary' onclick="pagosIncidencias('${id_pago}', '${id_trabajador}')">1.- Pagos Incidencias </li>&nbsp;
            <li class='btn btn-primary' onclick="pagosConceptos('${id_pago}', '${id_trabajador}')">2.- Pagos Conceptos </li>&nbsp;
        </ul>
    `);
};

const pagosIncidencias = (id_pago,id_trabajador) => {
    console.log("Hola1");
    window.location.href = 'detallePagosIncidencias?id_pago=' + id_pago + "&id_trabajador=" + id_trabajador;
};

const pagosConceptos = (id_pago,id_trabajador) => {
    console.log("Hola2");
    window.location.href = 'detallePagosConceptos?id_pago=' + id_pago + "&id_trabajador=" + id_trabajador;
};
