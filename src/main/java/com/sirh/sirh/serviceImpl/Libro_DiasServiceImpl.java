/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.serviceImpl;

import com.sirh.sirh.DTO.HorarioDTO;
import com.sirh.sirh.DTO.Libro_DiasDTO;
import com.sirh.sirh.DTO.Trabajador_DepositoDTO;
import com.sirh.sirh.entity.Horario;
import com.sirh.sirh.entity.Libro_Dias;
import com.sirh.sirh.entity.Libro_Dias_Comentario;
import com.sirh.sirh.repository.HorarioRepository;
import com.sirh.sirh.repository.Libro_DiasRepository;
import com.sirh.sirh.repository.Libro_Dias_ComentarioRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sirh.sirh.service.Libro_DiasService;
import java.time.LocalDateTime;

/**
 *
 * @author akalvarez19
 */
@Service
@Transactional
public class Libro_DiasServiceImpl implements Libro_DiasService {

    @Autowired
    private HorarioRepository horarioRepository;

    @Autowired
    private Libro_DiasRepository libro_diasRepository;

    @Autowired
    private Libro_Dias_ComentarioRepository libro_Dias_ComentarioRepository;

    @Override
    public Horario saveHorario(Horario horario) {
        return horarioRepository.save(horario);
    }

    @Override
    public List<Horario> findHorario(Integer trabajador_id) {
        return horarioRepository.findHorario(trabajador_id);
    }

    @Override
    public Horario editarHorario(HorarioDTO horario, Integer id_horario) {
        Horario d = this.horarioRepository.findById(id_horario).get();
        Horario datos = d;
        datos.setTipo_clave_id(horario.getTipo_clave_id());
        datos.setAnio(horario.getAnio());
        datos.setEstatus(horario.getEstatus());
        return horarioRepository.save(datos);

    }

    @Override
    public Horario findHorarioTrabajador(Integer trabajador_id) {
        return horarioRepository.findHorarioTrabajador(trabajador_id);
    }

    @Override
    public Horario findOneHorario(Integer id) {
        return horarioRepository.findById(id).get();
    }


    @Override
    public List<Horario> saveAll(List<Horario> productData) {
        return horarioRepository.saveAll(productData);
    }

    // ********************************************
    // ******** Servicios Para Guardar datos de libro_dias *******
    // ********************************************
    @Override
    public Libro_Dias saveLibro_Dias(Libro_DiasDTO libro_dias) {
        Libro_Dias libroD = new Libro_Dias();
        libroD.setCat_dias(libro_dias.getCat_dias());
        libroD.setHorario_entrada(libro_dias.getHorario_entrada());
        libroD.setHorario_salida(libro_dias.getHorario_salida());
        libroD.setHoras_trabajadas(libro_dias.getHoras_trabajadas());
        libroD.setHoras_pago(libro_dias.getHoras_pago());
        libroD.setTrabajador_id(libro_dias.getTrabajador_id());
        libroD.setEstatus(1);
        libroD.setDia_descanso(libro_dias.getDia_descanso());
        libroD.setCat_Depositos(libro_dias.getCat_Depositos());
        LocalDateTime datetime = LocalDateTime.now();
        libroD.setFecha_captura(datetime);
        libroD.setFecha_movimiento(datetime);
        return libro_diasRepository.save(libroD);
    }

    @Override
    public Libro_Dias_Comentario saveComentario(Libro_Dias_Comentario libro_Dias_Comentario) {
        return libro_Dias_ComentarioRepository.save(libro_Dias_Comentario);
    }
    @Override
    public List<Libro_Dias> findLibro_Dias(Integer trabajador_id) {
        return libro_diasRepository.findLibro_Dias(trabajador_id);
    }

    @Override
    public Libro_Dias_Comentario findComentarioGeneralTrabajador(Integer trabajador_id) {
        return libro_Dias_ComentarioRepository.findComentarioGeneralTrabajador(trabajador_id);
    }

    @Override
    public Libro_Dias editarLibro_Dias(Libro_DiasDTO libro_dias, Integer id_libro_dias) {
        Libro_Dias d = this.libro_diasRepository.findById(id_libro_dias).get();
        Libro_Dias datos = d;
        //datos.setComentario(libro_dias.getComentario());
        //datos.setCat_dias(libro_dias.getCat_dias());
        datos.setHorario_entrada(libro_dias.getHorario_entrada());
        datos.setHorario_salida(libro_dias.getHorario_salida());
        datos.setHoras_trabajadas(libro_dias.getHoras_trabajadas());
        datos.setHoras_pago(libro_dias.getHoras_pago());
        datos.setDia_descanso(libro_dias.getDia_descanso());
        datos.setCat_Depositos(libro_dias.getCat_Depositos());
        return libro_diasRepository.save(datos);
    }

    @Override
    public Libro_Dias_Comentario editarComentario(Libro_Dias_Comentario libro_dias_comentario, Integer id_comentario) {
        Libro_Dias_Comentario d = this.libro_Dias_ComentarioRepository.findById(id_comentario).get();
        Libro_Dias_Comentario datos = d;
        datos.setComentario(libro_dias_comentario.getComentario());
        return libro_Dias_ComentarioRepository.save(datos);
    }

    @Override
    public Libro_Dias findLibro_DiasTrabajador(Integer trabajador_id) {
        return libro_diasRepository.findLibro_DiasTrabajador(trabajador_id);
    }

    @Override
    public Libro_Dias findOneLibro_Dias(Integer id) {
        return libro_diasRepository.findById(id).get();
    }

    @Override
    public Libro_Dias findLibro_H(Integer id_libro_dias) {
        return libro_diasRepository.findById(id_libro_dias).get();
    }

    @Override
    public Libro_Dias estatus(Integer id, Integer estatus) {
        Libro_Dias estado = libro_diasRepository.findById(id).get();
        estado.setEstatus(estatus);

        return libro_diasRepository.save(estado);
    }

    @Override
    public List<Libro_Dias> findDiasTrabajador(Integer trabajador_id) {
        return libro_diasRepository.findDiasTrabajador(trabajador_id);
    }

    @Override
    public List<Libro_Dias> findDiasDescansosTrabajador(Integer trabajador_id) {
        return libro_diasRepository.findDiasDescansosTrabajador(trabajador_id);
    }

    @Override
    public List<Trabajador_DepositoDTO> findTrabajadoresBaseSinDepositoAsignado() {
        return libro_diasRepository.findTrabajadoresBaseSinDepositoAsignado();
    }

    @Override
    public List<Libro_Dias> findExpedienteLibroDias(Integer expediente) {
        return libro_diasRepository.findExpedienteLibroDias(expediente);
    }

}
