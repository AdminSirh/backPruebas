/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.util;

import lombok.Getter;

/**
 *
 * @author jarellano22
 */
@Getter
public enum Response {
    OK("Proceso exitoso.", 200),
    CREATED("Creacion exitosa.", 200),
    UPDATE("Actualizacion exitosa.", 200),
    DELETED("Eliminacion exitosa,", 200),
    BADREQUEST("Error en su petición.", 200),
    NOTFOUND("No existen registros.", 404),
    INTERNALERROR("Error.", 500),
    CONFLICT("Hubo un conflicto en su petición.", 409),
    CURPEXISTE("Este candidato ya esta registrado (CURP).", 10),
    EXTENCION("Extencion del archivo no permitida.", 11),
    //-------N 26 Sep 22-------//
    USERNAMEEXISTE("El nombre de usuario ingresado ya existe.", 1),
    PASSWORDUPDATE("La Contraseña fue actualizada con éxito.", 2),
    //-------D 03 Oct 22-------//
    MENUEXISTE("El nombre de menu ingresado ya existe", 3),
    //-------N 20 Oct 22-------//
    EXPEDIENTEEXISTE("El número de expediente ingresado ya existe.", 4),
    CODPUESTOEXISTE("El código del puesto ingresado ya existe.", 5),
    COMMAXIMA("Modifique la comisión ingresada", 10);

    private String key;
    private int code;

    private Response(String key, int code) {
        this.key = key;
        this.code = code;
    }
}
