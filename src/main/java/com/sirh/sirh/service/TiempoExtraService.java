/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.service;

import com.sirh.sirh.entity.Tiempo_Extra;

/**
 *
 * @author rroscero23
 */
public interface TiempoExtraService {

    public Tiempo_Extra findTiempoExtra(Integer trabajadorId, Integer periodoApl, Integer periodoGen, String semanaIncidencias);

    public Tiempo_Extra findTiempoExtraID(Integer id_tiempo_extra);

    public Tiempo_Extra saveTiempoExtra(Tiempo_Extra tiempo_Extra);

    public Tiempo_Extra estatusTiempoExtra(Integer id_tiempo_extra, Integer estatus);

    public Tiempo_Extra updateTiempoExtra(Tiempo_Extra tiempo_extra, Integer id_tiempo_extra);

    public Tiempo_Extra updateCve19(Double diferenciaTiempoExtra, Integer id_tiempo_extra);
}
