$(document).ready(function () {
    $.ajax({
        type: "GET",
        url: "session",
        dataType: 'json',
        success: function (data) {
            var html = '';
            html += data[1] + " " + data[2];
            $("#usuarioNombre").append(html);
            
        },
        error: function (e) {
            alert(e);
        }
    });
});