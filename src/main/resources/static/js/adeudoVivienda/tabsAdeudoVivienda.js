document.addEventListener('DOMContentLoaded', () => {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParametros = new URLSearchParams(d);
    var id_trabajador = urlParametros.get('id_trabajador');
    tabs(id_trabajador);
    
});

const tabs = (id_trabajador) => {
    $('#myTab').html("<ul class='nav nav-tabs' role='tablist'>" +
            "<li class='btn btn-primary' onclick=descuentoVivienda('" + id_trabajador + "')>1.- Descuentos </li>&nbsp;" +
            "<li class='btn btn-primary' onclick=cuentaActiva('" + id_trabajador + "')>2.- Cuenta Activa</li>&nbsp;" +
            "<li class='btn btn-primary' onclick=pagos('" + id_trabajador + "')>3.- Movimiento de Pagos</li>&nbsp;");
};

const descuentoVivienda = (id_trabajador) => {
    window.location.href = 'descuentoAdeudoVivienda?id_trabajador=' + id_trabajador;
};

const cuentaActiva = (id_trabajador) => {
    window.location.href = 'adeudoViviendaCuentaActiva?id_trabajador=' + id_trabajador;
};

const pagos = (id_trabajador) => {
    window.location.href = 'adeudoViviendaPagos?id_trabajador=' + id_trabajador;
};
