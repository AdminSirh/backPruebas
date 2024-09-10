/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */
//Algoritmo que crea el menu dinamico 
//Autor J. E. Arellano.
$(document).ready(function () {
    $.ajax({
        type: "GET",
        url: "menu",
        dataType: 'json',
        success: function (data) {
            var html = '';
            var len = data.length;
            const arr = [];
            const arrsm = [];
            //Guarda los valores de data en un arreglo
            for (var i = 0; i < len; i++) {
                //guarda los valores de menu en el arreglo arr               
                arr.push({id: data[i].submenu.menu.id, nombre: data[i].submenu.menu.menuNombre, icono: data[i].submenu.menu.icono, orden: data[i].submenu.menu.orden});
                //guarda los valores de submenu en un arreglo arrsm
                arrsm.push({idsm: data[i].submenu.id, nombresm: data[i].submenu.submenuNombre, menu_idsm: data[i].submenu.menu.id, descripcionsm: data[i].submenu.descripcion, ordensm: data[i].submenu.orden});
            }
            //Eliminamos los id duplocados de el arreglo arr de menu
            var uniqueArray = removeDuplicates(arr, "id");
            //Ordenamos numericamente por los valores de el atributo orden el arreglo uniqueArray de donde viene los valores del menu 
            var uniqueArray2 = uniqueArray.sort((a, b) => a.orden < b.orden ? -1 : +(a.orden > b.orden));
            //obtenemos la longitud del arreglo uniqueArray2
            var len_m = uniqueArray2.map(a => a.id).length;
            for (var i = 0; i < len_m; i++) {
                html += "<li class='nav-item'>";
                html += "<a class='nav-link collapsed' href='#' data-toggle='collapse' data-target='#collapse" + uniqueArray2.map(a => a.nombre)[i].replace(/\s/g, '') + "'";
                html += "'aria-expanded='true' aria-controls='#collapse" + uniqueArray2.map(a => a.nombre)[i].replace(/\s/g, '') + "'>";
                //Iconos del menu 
                html += "<i class='" + uniqueArray2.map(a => a.icono)[i] + "'></i>";
                //Label del menu 
                html += "<span>" + uniqueArray2.map(a => a.nombre)[i] + "</span>";
                html += "</a>";
                html += "<div id='collapse" + uniqueArray2.map(a => a.nombre)[i].replace(/\s/g, '') + "' class='collapse' aria-labelledby='heading" + uniqueArray2.map(a => a.nombre)[i].replace(/\s/g, '') + "' data-parent='#accordionSidebar'>";
                html += "<div class='bg-white py-2 collapse-inner rounded'>";
                html += " <h6 class='collapse-header'>Sub-menu</h6>";
                for (var j = 0; j < len; j++) {
                    //ordenamos los submenus atributo orden smn
                    arrsm.sort((a, b) => a.ordensm < b.ordensm ? -1 : +(a.ordensm > b.ordensm));
                    //asigna por medio de if el menu a su submenu
                    if (uniqueArray2.map(menu => menu.id)[i] === arrsm[j].menu_idsm) {
                        //Label Submenu
                        html += '<a class="collapse-item" href="' + arrsm[j].descripcionsm + '">' + arrsm[j].nombresm + '</a>';
                    }
                }
                html += "</div></div></li>";
            }
            $("#menu").append(html);
        },
        error: function (e) {
            alert(e);
        }
    });
});

function removeDuplicates(originalArray, prop) {
    var newArray = [];
    var lookupObject = {};

    for (var i in originalArray) {
        lookupObject[originalArray[i][prop]] = originalArray[i];
    }

    for (i in lookupObject) {
        newArray.push(lookupObject[i]);
    }
    return newArray;
}