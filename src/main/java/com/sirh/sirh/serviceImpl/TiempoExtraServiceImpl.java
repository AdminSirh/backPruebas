/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.serviceImpl;

import com.sirh.sirh.entity.Tiempo_Extra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sirh.sirh.repository.Tiempo_ExtraRepository;
import com.sirh.sirh.service.TiempoExtraService;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author rroscero23
 */
@Service
@Transactional
public class TiempoExtraServiceImpl implements TiempoExtraService {

    @Autowired
    private Tiempo_ExtraRepository tiempo_ExtraRepository;

    @Override
    public Tiempo_Extra findTiempoExtra(Integer trabajadorId, Integer periodoApl, Integer periodoGen, String semanaIncidencias) {
        return tiempo_ExtraRepository.findTiempoExtra(trabajadorId, periodoApl, periodoGen, semanaIncidencias);
    }

    @Override
    public Tiempo_Extra findTiempoExtraID(Integer id_tiempo_extra) {
        return tiempo_ExtraRepository.findById(id_tiempo_extra).get();
    }

    @Override
    public Tiempo_Extra saveTiempoExtra(Tiempo_Extra tiempo_Extra) {
        LocalDate date = LocalDate.now();
        Tiempo_Extra te = new Tiempo_Extra();
        te.setTrabajador_id(tiempo_Extra.getTrabajador_id());
        te.setPeriodo_aplicacion_id(tiempo_Extra.getPeriodo_aplicacion_id());
        te.setPeriodo_generacion_id(tiempo_Extra.getPeriodo_generacion_id());
        te.setSemana_incidencias(tiempo_Extra.getSemana_incidencias());
        te.setNomina_id(tiempo_Extra.getNomina_id());
        te.setFecha_inicio(date);

        te.setTiempo_extra_1(tiempo_Extra.getTiempo_extra_1());
        te.setTiempo_extra_2(tiempo_Extra.getTiempo_extra_2());
        te.setTiempo_extra_3(tiempo_Extra.getTiempo_extra_3());
        te.setTiempo_extra_4(tiempo_Extra.getTiempo_extra_4());
        te.setTiempo_extra_5(tiempo_Extra.getTiempo_extra_5());
        te.setTiempo_extra_6(tiempo_Extra.getTiempo_extra_6());
        te.setTiempo_extra_7(tiempo_Extra.getTiempo_extra_7());

        te.setPaseos_lab_1(tiempo_Extra.getPaseos_lab_1());
        te.setPaseos_lab_2(tiempo_Extra.getPaseos_lab_2());
        te.setPaseos_lab_3(tiempo_Extra.getPaseos_lab_3());
        te.setPaseos_lab_4(tiempo_Extra.getPaseos_lab_4());
        te.setPaseos_lab_5(tiempo_Extra.getPaseos_lab_5());
        te.setPaseos_lab_6(tiempo_Extra.getPaseos_lab_6());
        te.setPaseos_lab_7(tiempo_Extra.getPaseos_lab_7());

        te.setTe_doble_1(tiempo_Extra.getTe_doble_1());
        te.setTe_doble_2(tiempo_Extra.getTe_doble_2());
        te.setTe_doble_3(tiempo_Extra.getTe_doble_3());
        te.setTe_doble_4(tiempo_Extra.getTe_doble_4());
        te.setTe_doble_5(tiempo_Extra.getTe_doble_5());
        te.setTe_doble_6(tiempo_Extra.getTe_doble_6());
        te.setTe_doble_7(tiempo_Extra.getTe_doble_7());

        te.setTe_triple_1(tiempo_Extra.getTe_triple_1());
        te.setTe_triple_2(tiempo_Extra.getTe_triple_2());
        te.setTe_triple_3(tiempo_Extra.getTe_triple_3());
        te.setTe_triple_4(tiempo_Extra.getTe_triple_4());
        te.setTe_triple_5(tiempo_Extra.getTe_triple_5());
        te.setTe_triple_6(tiempo_Extra.getTe_triple_6());
        te.setTe_triple_7(tiempo_Extra.getTe_triple_7());

        te.setTotal_te_doble(tiempo_Extra.getTotal_te_doble());
        te.setTotal_te_triple(tiempo_Extra.getTotal_te_triple());
        te.setTotal_te_paseos_doble(tiempo_Extra.getTotal_te_paseos_doble());
        te.setTotal_te_festivo_doble(tiempo_Extra.getTotal_te_festivo_doble());
        te.setEstatus(1);
        return tiempo_ExtraRepository.save(te);
    }

    @Override
    public Tiempo_Extra estatusTiempoExtra(Integer id_tiempo_extra, Integer estatus) {
        Tiempo_Extra te = tiempo_ExtraRepository.findById(id_tiempo_extra).get();
        te.setEstatus(estatus);
        return tiempo_ExtraRepository.save(te);
    }

    @Override
    public Tiempo_Extra updateTiempoExtra(Tiempo_Extra tiempo_Extra, Integer id_tiempo_extra) {
        Tiempo_Extra te = tiempo_ExtraRepository.findById(id_tiempo_extra).get();
        LocalDate date = LocalDate.now();
        te.setTrabajador_id(tiempo_Extra.getTrabajador_id());
        te.setPeriodo_aplicacion_id(tiempo_Extra.getPeriodo_aplicacion_id());
        te.setPeriodo_generacion_id(tiempo_Extra.getPeriodo_generacion_id());
        te.setSemana_incidencias(tiempo_Extra.getSemana_incidencias());
        te.setNomina_id(tiempo_Extra.getNomina_id());
        te.setFecha_inicio(date);

        te.setTiempo_extra_1(tiempo_Extra.getTiempo_extra_1());
        te.setTiempo_extra_2(tiempo_Extra.getTiempo_extra_2());
        te.setTiempo_extra_3(tiempo_Extra.getTiempo_extra_3());
        te.setTiempo_extra_4(tiempo_Extra.getTiempo_extra_4());
        te.setTiempo_extra_5(tiempo_Extra.getTiempo_extra_5());
        te.setTiempo_extra_6(tiempo_Extra.getTiempo_extra_6());
        te.setTiempo_extra_7(tiempo_Extra.getTiempo_extra_7());

        te.setPaseos_lab_1(tiempo_Extra.getPaseos_lab_1());
        te.setPaseos_lab_2(tiempo_Extra.getPaseos_lab_2());
        te.setPaseos_lab_3(tiempo_Extra.getPaseos_lab_3());
        te.setPaseos_lab_4(tiempo_Extra.getPaseos_lab_4());
        te.setPaseos_lab_5(tiempo_Extra.getPaseos_lab_5());
        te.setPaseos_lab_6(tiempo_Extra.getPaseos_lab_6());
        te.setPaseos_lab_7(tiempo_Extra.getPaseos_lab_7());

        te.setTe_doble_1(tiempo_Extra.getTe_doble_1());
        te.setTe_doble_2(tiempo_Extra.getTe_doble_2());
        te.setTe_doble_3(tiempo_Extra.getTe_doble_3());
        te.setTe_doble_4(tiempo_Extra.getTe_doble_4());
        te.setTe_doble_5(tiempo_Extra.getTe_doble_5());
        te.setTe_doble_6(tiempo_Extra.getTe_doble_6());
        te.setTe_doble_7(tiempo_Extra.getTe_doble_7());

        te.setTe_triple_1(tiempo_Extra.getTe_triple_1());
        te.setTe_triple_2(tiempo_Extra.getTe_triple_2());
        te.setTe_triple_3(tiempo_Extra.getTe_triple_3());
        te.setTe_triple_4(tiempo_Extra.getTe_triple_4());
        te.setTe_triple_5(tiempo_Extra.getTe_triple_5());
        te.setTe_triple_6(tiempo_Extra.getTe_triple_6());
        te.setTe_triple_7(tiempo_Extra.getTe_triple_7());

        te.setTotal_te_doble(tiempo_Extra.getTotal_te_doble());
        te.setTotal_te_triple(tiempo_Extra.getTotal_te_triple());
        te.setTotal_te_paseos_doble(tiempo_Extra.getTotal_te_paseos_doble());
        te.setTotal_te_festivo_doble(tiempo_Extra.getTotal_te_festivo_doble());

        te.setEstatus(1);
        return tiempo_ExtraRepository.save(te);
    }

    @Override
    public Tiempo_Extra updateCve19(Double diferenciaTiempoExtra, Integer id_tiempo_extra) {
        Tiempo_Extra te = tiempo_ExtraRepository.findById(id_tiempo_extra).get();
        te.setDif_cve_19(diferenciaTiempoExtra);
        return tiempo_ExtraRepository.save(te);
    }

}
