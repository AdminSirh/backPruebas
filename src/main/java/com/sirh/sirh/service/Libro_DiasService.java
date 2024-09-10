/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.service;

import com.sirh.sirh.DTO.HorarioDTO;
import com.sirh.sirh.DTO.Libro_DiasDTO;
import com.sirh.sirh.DTO.Trabajador_DepositoDTO;
import com.sirh.sirh.entity.Horario;
import com.sirh.sirh.entity.Libro_Dias;
import com.sirh.sirh.entity.Libro_Dias_Comentario;
import java.util.List;

/**
 *
 * @author akalvarez19
 */
public interface Libro_DiasService {

    // ******** Servicios Para Libro *******
    public Horario saveHorario(Horario horario);

    public List<Horario> findHorario(Integer trabajador_id);

    public Horario findHorarioTrabajador(Integer trabajador_id);

    public Horario editarHorario(HorarioDTO horario, Integer id_horario);

    public Horario findOneHorario(Integer id);

    public List<Horario> saveAll(List<Horario> productData);

// ******** Servicios Para Libro *******  
    public Libro_Dias saveLibro_Dias(Libro_DiasDTO libro_dias);

    public List<Libro_Dias> findLibro_Dias(Integer trabajador_id);

    public Libro_Dias findLibro_DiasTrabajador(Integer trabajador_id);

    public Libro_Dias editarLibro_Dias(Libro_DiasDTO libro_dias, Integer id_libro_dias);

    public Libro_Dias findOneLibro_Dias(Integer id);
    
    public Libro_Dias_Comentario findComentarioGeneralTrabajador(Integer trabajador_id); 

//    public Libro_Dias_Comentario findComentario(Integer id);

    public Libro_Dias_Comentario editarComentario(Libro_Dias_Comentario libro_dias_comentario, Integer id_comentario);

    public Libro_Dias findLibro_H(Integer id_libro_dias);

    public Libro_Dias estatus(Integer id, Integer estatus);
    //public Libro_Dias saveLibro_Dias(Libro_DiasDTO libro_dias, Integer id);

    //public List<Libro_Dias> saveAllDias(List<Libro_Dias> productData);
    //public List<Horario> editarHorario(HorarioDTO horario, Integer id_horario);
    public List<Libro_Dias> findDiasTrabajador(Integer trabajador_id);

    public List<Libro_Dias> findDiasDescansosTrabajador(Integer trabajador_id);

    public List<Trabajador_DepositoDTO> findTrabajadoresBaseSinDepositoAsignado();

    public Libro_Dias_Comentario saveComentario(Libro_Dias_Comentario libro_Dias_Comentario);

//    public void actualizarComentario(Integer trabajadorId, Integer comentario);

    public List<Libro_Dias> findExpedienteLibroDias(Integer expediente);
}
