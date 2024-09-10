document.addEventListener('DOMContentLoaded', () => {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParametros = new URLSearchParams(d);
    var id_trabajador = urlParametros.get('id_trabajador');
    tabs(id_trabajador);
    
});

const tabs = (id_trabajador) => {
    $('#myTab').html("<ul class='nav nav-tabs' role='tablist'>" +
            "<li class='btn btn-primary' onclick=descuentoAuxilio('" + id_trabajador + "')>1.- Descuentos </li>&nbsp;" +
            "<li class='btn btn-primary' onclick=cuentaActiva('" + id_trabajador + "')>2.- Cuenta Activa</li>&nbsp;" +
            "<li class='btn btn-primary' onclick=pagos('" + id_trabajador + "')>3.- Movimiento de Pagos</li>&nbsp;");
};

const descuentoAuxilio = (id_trabajador) => {
    window.location.href = 'descuentoFondoAuxilio?id_trabajador=' + id_trabajador;
};

const cuentaActiva = (id_trabajador) => {
    window.location.href = 'fondoAuxilioCuentaActiva?id_trabajador=' + id_trabajador;
};

const pagos = (id_trabajador) => {
    window.location.href = 'fondoAuxilioPagos?id_trabajador=' + id_trabajador;
};
