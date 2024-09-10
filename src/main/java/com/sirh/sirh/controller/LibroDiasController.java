/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.controller;

import com.sirh.sirh.DTO.HorarioDTO;
import com.sirh.sirh.DTO.Libro_DiasDTO;
import com.sirh.sirh.DTO.Trabajador_DepositoDTO;
import com.sirh.sirh.entity.Horario;
import com.sirh.sirh.entity.Libro_Dias;
import com.sirh.sirh.entity.Libro_Dias_Comentario;
import com.sirh.sirh.util.Response;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sirh.sirh.exception.OutputEntity;
import com.sirh.sirh.service.Libro_DiasService;

/**
 *
 * @author akalvarez19
 */
@RestController
@RequestMapping("horarios")
public class LibroDiasController {

    @Autowired
    Libro_DiasService horarioService;

//************************* CONTROLADORES DE HORARIO *****************************************************
    //******************** GUARDAR LIBRO************************
    @PostMapping(value = "/guardarLibroTrabajador")
    public ResponseEntity<OutputEntity<String>> guardarHorarioTrabajador(@RequestBody Horario horario) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            horarioService.saveHorario(horario);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos horarios guardados con éxito");
            //out.success(Response.CREATED.getCode(), Response.CREATED.getKey(), "");
            return new ResponseEntity<>(out, out.getCode());

        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @PostMapping("/guardarComentarioGeneral")
    public ResponseEntity<Libro_Dias_Comentario> guardarComentarioGeneralHorarios(@RequestBody Libro_Dias_Comentario libro_Dias_Comentario) {
        try {
            return new ResponseEntity<>(horarioService.saveComentario(libro_Dias_Comentario), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //******************** EDITAR DATOS HORARIO DEL TRABAJADOR ************************
    @PostMapping(value = "/editarLibroTrabajador/{id_libro_dias}")
    public ResponseEntity<OutputEntity<String>> editarHorarioPersona(@RequestBody HorarioDTO horario, @PathVariable Integer id_horario) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            horarioService.editarHorario(horario, id_horario);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos horarios modificados con éxito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            //System.out.println("e " + e);
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    //******************** BUSCAR HORARIO DEL TRABAJADOR ************************
    //Buscar contacto por Id_Persona 
    @GetMapping(value = "/buscarLibro/{trabajador_id}")
    public ResponseEntity<OutputEntity<List<Horario>>> buscarHorarioTrabajador(@PathVariable("trabajador_id") Integer trabajador_id) {
        OutputEntity<List<Horario>> out = new OutputEntity<>();
        try {
            List<Horario> result = horarioService.findHorario(trabajador_id);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            //System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //******************** GUARDAR LIBRO DIAS************************
    @PostMapping(value = "/guardarHorarioTrabajador")
    public ResponseEntity<OutputEntity<String>> guardarLibro_DiasTrabajador(@RequestBody Libro_DiasDTO libro_dias) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            horarioService.saveLibro_Dias(libro_dias);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos horarios guardados con éxito");
            //out.success(Response.CREATED.getCode(), Response.CREATED.getKey(), "");
            return new ResponseEntity<>(out, out.getCode());

        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    //******************** EDITAR DATOS HORARIO DEL TRABAJADOR ************************
    @PostMapping(value = "/editarHorarioTrabajador/{id_libro_dias}")
    public ResponseEntity<OutputEntity<String>> editarLibro_DiasTrabajador(@RequestBody Libro_DiasDTO libro_dias, @PathVariable Integer id_libro_dias) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            horarioService.editarLibro_Dias(libro_dias, id_libro_dias);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos horarios modificados con éxito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            //System.out.println("e " + e);
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @PostMapping(value = "/editarComentario/{id_comentario}")
    public ResponseEntity<OutputEntity<String>> editarComentario(@RequestBody Libro_Dias_Comentario libro_dias_comentario, @PathVariable Integer id_comentario) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            horarioService.editarComentario(libro_dias_comentario, id_comentario);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos horarios modificados con éxito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    //******************** BUSCAR HORARIO DEL TRABAJADOR ************************
    //Buscar contacto por Id_Persona 
    @GetMapping(value = "/buscarHorario/{trabajador_id}")
    public ResponseEntity<List<Libro_Dias>> buscarLibro_DiasTrabajador(@PathVariable("trabajador_id") Integer trabajador_id) {
        try {
            List<Libro_Dias> result = horarioService.findLibro_Dias(trabajador_id);
            return new ResponseEntity<>(result, HttpStatus.OK);
            //out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            //return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            //System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //******************** BUSCAR HORARIO DEL TRABAJADOR ************************
    //Buscar contacto por Id_Persona 
    @GetMapping(value = "/buscarHorarioId/{id_libro_dias}")
    public ResponseEntity<OutputEntity<Libro_Dias>> findLibro_H(@PathVariable("id_libro_dias") Integer id_libro_dias) {
        OutputEntity<Libro_Dias> out = new OutputEntity<>();
        try {
            Libro_Dias result = horarioService.findLibro_H(id_libro_dias);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            //System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping(value = "/buscarComentario/{id}")
//    public ResponseEntity<OutputEntity<Libro_Dias_Comentario>> findComentario(@PathVariable("id") Integer id) {
//        OutputEntity<Libro_Dias_Comentario> out = new OutputEntity<>();
//        try {
//            Libro_Dias_Comentario result = horarioService.findComentario(id);
//            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
//            return new ResponseEntity<>(out, out.getCode());
//        } catch (Exception e) {
//            //System.out.println("e " + e);
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
    //Desactivar Usuario o activar usuario 
    //0 Inactivo 1 Activo
    //@SystemControllerLog(operation = "eliminarUsuario", type = "desactivo al usuario")
    @PostMapping(value = "/estatusHorario/{id}/{estatus}")
    public ResponseEntity<Libro_Dias> eliminarHorario(@PathVariable("id") Integer id, @PathVariable("estatus") Integer estatus) {
        try {
            return new ResponseEntity<>(horarioService.estatus(id, estatus), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Buscar horario por trabajador
    @GetMapping(value = "/buscarDiasTrabajador/{trabajador_id}")
    public ResponseEntity<List<Libro_Dias>> buscarDiasTrabajador(@PathVariable("trabajador_id") Integer trabajador_id) {
        try {
            List<Libro_Dias> result = horarioService.findLibro_Dias(trabajador_id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Buscar dias de descanso por trabajador
    @GetMapping(value = "/buscarDiasDescansoTrabajador/{trabajador_id}")
    public ResponseEntity<List<Libro_Dias>> buscarDiasDescansoTrabajador(@PathVariable("trabajador_id") Integer trabajador_id) {
        try {
            List<Libro_Dias> result = horarioService.findDiasDescansosTrabajador(trabajador_id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarTrabajadoresBaseSinDepositoAsignado")
    public ResponseEntity<List<Trabajador_DepositoDTO>> buscarTrabajadoresBaseSinDeposito() {
        try {
            List<Trabajador_DepositoDTO> result = horarioService.findTrabajadoresBaseSinDepositoAsignado();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarComentarioTrabajador/{trabajador_id}")
    public ResponseEntity<Libro_Dias_Comentario> findComentarioGeneralTrabajador(@PathVariable("trabajador_id") Integer trabajador_id) {
        try {
            Libro_Dias_Comentario result = horarioService.findComentarioGeneralTrabajador(trabajador_id);
            if (result == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PostMapping(value = "/actualizarComentario/{trabajador_id}/{comentario}")
//    public ResponseEntity<OutputEntity<String>> actualizarComentario(
//            @PathVariable("trabajador_id") Integer trabajadorId,
//            @PathVariable("comentario") Integer comentario) {
//        OutputEntity<String> out = new OutputEntity<>();
//        try {findComentario
//            horarioService.actualizarComentario(trabajadorId, comentario);
//            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Comentario actualizado con éxito");
//            return new ResponseEntity<>(out, out.getCode());
//        } catch (Exception e) {
//            out.error();
//            return new ResponseEntity<>(out, out.getCode());
//        }
//    }
}
