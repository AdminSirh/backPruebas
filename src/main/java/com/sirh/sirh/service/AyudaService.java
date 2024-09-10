/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.service;

import com.sirh.sirh.DTO.AyudaDTO;
import com.sirh.sirh.DTO.AyudaDatosDTO;
import com.sirh.sirh.entity.Ayuda;
import com.sirh.sirh.entity.Ayuda_Dias_Permiso;
import java.util.Date;
import java.util.List;

/**
 *
 * @author akalvarez19
 */
public interface AyudaService {

    public List<Ayuda> findAyuda(Integer id_ayuda);

    public Ayuda findAyudaID(Integer id_ayuda);

    public Ayuda saveAyuda(Ayuda ayuda);

    public Ayuda editarAyuda(AyudaDTO Ayuda, Integer id_ayuda);

    //public Ayuda findIncidenciasID(Integer id_ayuda);
    public List<Ayuda> findAyudaIDT(Integer trabajador_id);

    public List<AyudaDTO> findAyudaIfExists(Integer id_incidencia);

    public Ayuda estatus(Integer id, Integer estatus);

    public List<Ayuda_Dias_Permiso> findAllDiasPermiso();

    public Ayuda_Dias_Permiso findAyudaDiasPermiso(Integer id);

    public Ayuda_Dias_Permiso editarAyudaDiasPermiso(Ayuda_Dias_Permiso adp, Integer id_ayuda_dias_permiso);

    public Ayuda_Dias_Permiso saveAyudaDiasPermiso(Ayuda_Dias_Permiso ayuda_dias_permiso);

    public List<AyudaDatosDTO> findAllAyudas(Integer estadoIncidenciaParametro, Date fechaInicioParametro, Date fechaFinParametro);

}
