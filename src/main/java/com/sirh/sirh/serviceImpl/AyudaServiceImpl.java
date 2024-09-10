/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.serviceImpl;

import com.sirh.sirh.DTO.AyudaDTO;
import com.sirh.sirh.DTO.AyudaDatosDTO;
import com.sirh.sirh.entity.Ayuda;
import com.sirh.sirh.entity.Ayuda_Dias_Permiso;
import com.sirh.sirh.repository.AyudaRepository;
import com.sirh.sirh.repository.Ayuda_Dias_PermisoRepository;
import com.sirh.sirh.service.AyudaService;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author akalvarez19
 */
@Service
@Transactional
public class AyudaServiceImpl implements AyudaService {

    @Autowired
    private AyudaRepository ayudaRepository;

    @Autowired
    private Ayuda_Dias_PermisoRepository ayuda_dias_permisoRepository;

    @Autowired
    private ModelMapper modelMapper;

    // ******************* AYUDA ******************
    @Override
    public Ayuda saveAyuda(Ayuda ayuda) {
        return ayudaRepository.save(ayuda);
    }

    @Override
    public Ayuda findAyudaID(Integer id_ayuda) {
        return ayudaRepository.findById(id_ayuda).get();
    }

    @Override
    public List<Ayuda> findAyudaIDT(Integer trabajador_id) {
        return ayudaRepository.findAyudaIDT(trabajador_id);
    }

    @Override
    public List<AyudaDTO> findAyudaIfExists(Integer id_incidencia) {
        return ayudaRepository.findAyudaIfExists(id_incidencia);
    }

    @Override
    public Ayuda editarAyuda(AyudaDTO ayuda, Integer id_ayuda) {
        Ayuda d = this.ayudaRepository.findById(id_ayuda).get();
        Ayuda datos = d;

        datos.setFecha_recepcion(ayuda.getFecha_recepcion());
        datos.setFecha_emision(ayuda.getFecha_emision());
        datos.setNombre(ayuda.getNombre());
        datos.setApellido_paterno(ayuda.getApellido_paterno());
        datos.setApellido_materno(ayuda.getApellido_materno());
        datos.setOrigen(ayuda.getOrigen());
        return ayudaRepository.save(datos);

    }

    @Override
    public Ayuda estatus(Integer id, Integer estatus) {
        Ayuda estado = ayudaRepository.findById(id).get();
        estado.setEstatus(estatus);

        return ayudaRepository.save(estado);
    }

    @Override
    public List<Ayuda> findAyuda(Integer id_ayuda) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Ayuda_Dias_Permiso> findAllDiasPermiso() {
        return ayuda_dias_permisoRepository.findAll().stream().map(ayuda_dias_permiso -> modelMapper.map(ayuda_dias_permiso, Ayuda_Dias_Permiso.class)).collect(Collectors.toList());
    }

    @Override
    public Ayuda_Dias_Permiso findAyudaDiasPermiso(Integer id) {
        return ayuda_dias_permisoRepository.findById(id).get();
    }

    @Override
    public Ayuda_Dias_Permiso saveAyudaDiasPermiso(Ayuda_Dias_Permiso ayuda_dias_permiso) {
        return ayuda_dias_permisoRepository.save(ayuda_dias_permiso);
    }

    @Override
    public Ayuda_Dias_Permiso editarAyudaDiasPermiso(Ayuda_Dias_Permiso adp, Integer id_ayuda_dias_permiso) {
        Ayuda_Dias_Permiso ad = this.ayuda_dias_permisoRepository.findById(id_ayuda_dias_permiso).get();
        Ayuda_Dias_Permiso datos = ad;
        datos.setDia_1(adp.getDia_1());
        datos.setDia_2(adp.getDia_2());
        datos.setDia_3(adp.getDia_3());
        datos.setDia_4(adp.getDia_4());
        datos.setDia_5(adp.getDia_5());
        datos.setDia_6(adp.getDia_6());
        return ayuda_dias_permisoRepository.save(datos);
    }

    @Override
    public List<AyudaDatosDTO> findAllAyudas(Integer estadoIncidenciaParametro, Date fechaInicioParametro, Date fechaFinParametro) {
        return ayudaRepository.findAllAyudas(estadoIncidenciaParametro, fechaInicioParametro, fechaFinParametro);
    }

}
