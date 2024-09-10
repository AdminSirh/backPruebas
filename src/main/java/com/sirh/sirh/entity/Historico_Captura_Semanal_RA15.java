/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author rroscero23
 */
@Entity
@Table(name = "historico_captura_semanal_ra15")
public class Historico_Captura_Semanal_RA15 implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_ra_historico;
    private Integer trabajador_id;
    private Integer tipo_id;
    private Double horas_planta;
    private String ruta;
    private Integer estatus;
    private String estado;
    private String a1;
    private String a2;
    private String a3;
    private String a4;
    private String a5;
    private String a6;
    private String a7;
    private String a8;
    private String a9;
    private String a10;
    private String a11;
    private String a12;
    private String a13;
    private String a14;
    private String a15;
    private String a16;
    private String a17;
    private String a18;
    private String a19;
    private String a20;
    private String a21;
    private String a22;
    private String a23;
    private String a24;
    private String a25;
    private String a26;
    private String a27;
    private String a28;
    private String captura;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_inicial_captura;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_final_captura;
    private Integer area;
    private Integer activo_ra15;
    private Double prop_desc_1;
    private Double prop_desc_2;
    private Double total_hrs_descanso;
    private Integer cod_puesto_suplencia;
    private Integer anio_planta;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_vigencia_planta;
    private Integer ruta_id;
    private Integer turno_id;
    private Integer abono_descuento_id;
    private Integer jornada_normal_id;
    private Integer tiempo_extra_id;
    private Integer paseos_laborados_id;
    private Integer excedente_jornada_id;
    private Integer suplencia_id;
    private Integer periodo_aplicacion_id;
    private Integer inasistencia_id;
    private Integer resumen_id;
    private Double abono_jornada_historico;
    private Double descuento_jornada_historico;
    private Double abono_doble_historico;
    private Double descuento_doble_historico;
    private Double abono_triple_historico;
    private Double descuento_triple_historico;
    private Double abono_descanso_historico;
    private Double descuento_descanso_historico;
    private Double abono_prima_historico;
    private Double descuento_prima_historico;
    private Double abono_festivo_historico;
    private Double descuento_festivo_historico;
    private Double abono_dias_lab_historico;
    private Double descuento_dias_lab_historico;
    private Double abono_dif_suplencia_historico;
    private Double descuento_dif_suplencia_historico;
    private String observaciones_historico;
    private Double descuento_omisiones_historico;
    private String observaciones_omisiones_historico;
    private Double lunes_jn;
    private Double martes_jn;
    private Double miercoles_jn;
    private Double jueves_jn;
    private Double viernes_jn;
    private Double sabado_jn;
    private Double domingo_jn;
    private Double lunes_te;
    private Double martes_te;
    private Double miercoles_te;
    private Double jueves_te;
    private Double viernes_te;
    private Double sabado_te;
    private Double domingo_te;
    private Double lunes_pl;
    private Double martes_pl;
    private Double miercoles_pl;
    private Double jueves_pl;
    private Double viernes_pl;
    private Double sabado_pl;
    private Double domingo_pl;
    private Double lunes_ej;
    private Double martes_ej;
    private Double miercoles_ej;
    private Double jueves_ej;
    private Double viernes_ej;
    private Double sabado_ej;
    private Double domingo_ej;
    private Double lunes_sup;
    private Double martes_sup;
    private Double miercoles_sup;
    private Double jueves_sup;
    private Double viernes_sup;
    private Double sabado_sup;
    private Double domingo_sup;
    private String lunes_inc;
    private String martes_inc;
    private String miercoles_inc;
    private String jueves_inc;
    private String viernes_inc;
    private String sabado_inc;
    private String domingo_inc;
    private Double horas_turno_r;
    private Double horas_doble_r;
    private Double horas_triple_r;
    private Double dias_laborados_r;
    private Integer total_faltas_r;
    private Double total_paseos_r;
    private Integer prima_dominical_r;
    private Double festivo_r;
    private Double omisiones_r;
    private Double dif_paseos_r;
    private Double dif_prima_r;
    private Double dif_tiempo_extra_r;
    private Double dif_sueldo_r;
    private Integer dias_pago_r;
    private Integer dias_cve_27_r;

    public Historico_Captura_Semanal_RA15() {
        super();
    }

    public Historico_Captura_Semanal_RA15(Integer id_ra_historico, Integer trabajador_id, Integer tipo_id, Double horas_planta, String ruta, Integer estatus, String estado, String a1, String a2, String a3, String a4, String a5, String a6, String a7, String a8, String a9, String a10, String a11, String a12, String a13, String a14, String a15, String a16, String a17, String a18, String a19, String a20, String a21, String a22, String a23, String a24, String a25, String a26, String a27, String a28, String captura, LocalDate fecha_inicial_captura, LocalDate fecha_final_captura, Integer area, Integer activo_ra15, Double prop_desc_1, Double prop_desc_2, Double total_hrs_descanso, Integer cod_puesto_suplencia, Integer anio_planta, LocalDate fecha_vigencia_planta, Integer ruta_id, Integer turno_id, Integer abono_descuento_id, Integer jornada_normal_id, Integer tiempo_extra_id, Integer paseos_laborados_id, Integer excedente_jornada_id, Integer suplencia_id, Integer periodo_aplicacion_id, Integer inasistencia_id, Integer resumen_id, Double abono_jornada_historico, Double descuento_jornada_historico, Double abono_doble_historico, Double descuento_doble_historico, Double abono_triple_historico, Double descuento_triple_historico, Double abono_descanso_historico, Double descuento_descanso_historico, Double abono_prima_historico, Double descuento_prima_historico, Double abono_festivo_historico, Double descuento_festivo_historico, Double abono_dias_lab_historico, Double descuento_dias_lab_historico, Double abono_dif_suplencia_historico, Double descuento_dif_suplencia_historico, String observaciones_historico, Double descuento_omisiones_historico, String observaciones_omisiones_historico, Double lunes_jn, Double martes_jn, Double miercoles_jn, Double jueves_jn, Double viernes_jn, Double sabado_jn, Double domingo_jn, Double lunes_te, Double martes_te, Double miercoles_te, Double jueves_te, Double viernes_te, Double sabado_te, Double domingo_te, Double lunes_pl, Double martes_pl, Double miercoles_pl, Double jueves_pl, Double viernes_pl, Double sabado_pl, Double domingo_pl, Double lunes_ej, Double martes_ej, Double miercoles_ej, Double jueves_ej, Double viernes_ej, Double sabado_ej, Double domingo_ej, Double lunes_sup, Double martes_sup, Double miercoles_sup, Double jueves_sup, Double viernes_sup, Double sabado_sup, Double domingo_sup, String lunes_inc, String martes_inc, String miercoles_inc, String jueves_inc, String viernes_inc, String sabado_inc, String domingo_inc, Double horas_turno_r, Double horas_doble_r, Double horas_triple_r, Double dias_laborados_r, Integer total_faltas_r, Double total_paseos_r, Integer prima_dominical_r, Double festivo_r, Double omisiones_r, Double dif_paseos_r, Double dif_prima_r, Double dif_tiempo_extra_r, Double dif_sueldo_r, Integer dias_pago_r, Integer dias_cve_27_r) {
        this.id_ra_historico = id_ra_historico;
        this.trabajador_id = trabajador_id;
        this.tipo_id = tipo_id;
        this.horas_planta = horas_planta;
        this.ruta = ruta;
        this.estatus = estatus;
        this.estado = estado;
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
        this.a4 = a4;
        this.a5 = a5;
        this.a6 = a6;
        this.a7 = a7;
        this.a8 = a8;
        this.a9 = a9;
        this.a10 = a10;
        this.a11 = a11;
        this.a12 = a12;
        this.a13 = a13;
        this.a14 = a14;
        this.a15 = a15;
        this.a16 = a16;
        this.a17 = a17;
        this.a18 = a18;
        this.a19 = a19;
        this.a20 = a20;
        this.a21 = a21;
        this.a22 = a22;
        this.a23 = a23;
        this.a24 = a24;
        this.a25 = a25;
        this.a26 = a26;
        this.a27 = a27;
        this.a28 = a28;
        this.captura = captura;
        this.fecha_inicial_captura = fecha_inicial_captura;
        this.fecha_final_captura = fecha_final_captura;
        this.area = area;
        this.activo_ra15 = activo_ra15;
        this.prop_desc_1 = prop_desc_1;
        this.prop_desc_2 = prop_desc_2;
        this.total_hrs_descanso = total_hrs_descanso;
        this.cod_puesto_suplencia = cod_puesto_suplencia;
        this.anio_planta = anio_planta;
        this.fecha_vigencia_planta = fecha_vigencia_planta;
        this.ruta_id = ruta_id;
        this.turno_id = turno_id;
        this.abono_descuento_id = abono_descuento_id;
        this.jornada_normal_id = jornada_normal_id;
        this.tiempo_extra_id = tiempo_extra_id;
        this.paseos_laborados_id = paseos_laborados_id;
        this.excedente_jornada_id = excedente_jornada_id;
        this.suplencia_id = suplencia_id;
        this.periodo_aplicacion_id = periodo_aplicacion_id;
        this.inasistencia_id = inasistencia_id;
        this.resumen_id = resumen_id;
        this.abono_jornada_historico = abono_jornada_historico;
        this.descuento_jornada_historico = descuento_jornada_historico;
        this.abono_doble_historico = abono_doble_historico;
        this.descuento_doble_historico = descuento_doble_historico;
        this.abono_triple_historico = abono_triple_historico;
        this.descuento_triple_historico = descuento_triple_historico;
        this.abono_descanso_historico = abono_descanso_historico;
        this.descuento_descanso_historico = descuento_descanso_historico;
        this.abono_prima_historico = abono_prima_historico;
        this.descuento_prima_historico = descuento_prima_historico;
        this.abono_festivo_historico = abono_festivo_historico;
        this.descuento_festivo_historico = descuento_festivo_historico;
        this.abono_dias_lab_historico = abono_dias_lab_historico;
        this.descuento_dias_lab_historico = descuento_dias_lab_historico;
        this.abono_dif_suplencia_historico = abono_dif_suplencia_historico;
        this.descuento_dif_suplencia_historico = descuento_dif_suplencia_historico;
        this.observaciones_historico = observaciones_historico;
        this.descuento_omisiones_historico = descuento_omisiones_historico;
        this.observaciones_omisiones_historico = observaciones_omisiones_historico;
        this.lunes_jn = lunes_jn;
        this.martes_jn = martes_jn;
        this.miercoles_jn = miercoles_jn;
        this.jueves_jn = jueves_jn;
        this.viernes_jn = viernes_jn;
        this.sabado_jn = sabado_jn;
        this.domingo_jn = domingo_jn;
        this.lunes_te = lunes_te;
        this.martes_te = martes_te;
        this.miercoles_te = miercoles_te;
        this.jueves_te = jueves_te;
        this.viernes_te = viernes_te;
        this.sabado_te = sabado_te;
        this.domingo_te = domingo_te;
        this.lunes_pl = lunes_pl;
        this.martes_pl = martes_pl;
        this.miercoles_pl = miercoles_pl;
        this.jueves_pl = jueves_pl;
        this.viernes_pl = viernes_pl;
        this.sabado_pl = sabado_pl;
        this.domingo_pl = domingo_pl;
        this.lunes_ej = lunes_ej;
        this.martes_ej = martes_ej;
        this.miercoles_ej = miercoles_ej;
        this.jueves_ej = jueves_ej;
        this.viernes_ej = viernes_ej;
        this.sabado_ej = sabado_ej;
        this.domingo_ej = domingo_ej;
        this.lunes_sup = lunes_sup;
        this.martes_sup = martes_sup;
        this.miercoles_sup = miercoles_sup;
        this.jueves_sup = jueves_sup;
        this.viernes_sup = viernes_sup;
        this.sabado_sup = sabado_sup;
        this.domingo_sup = domingo_sup;
        this.lunes_inc = lunes_inc;
        this.martes_inc = martes_inc;
        this.miercoles_inc = miercoles_inc;
        this.jueves_inc = jueves_inc;
        this.viernes_inc = viernes_inc;
        this.sabado_inc = sabado_inc;
        this.domingo_inc = domingo_inc;
        this.horas_turno_r = horas_turno_r;
        this.horas_doble_r = horas_doble_r;
        this.horas_triple_r = horas_triple_r;
        this.dias_laborados_r = dias_laborados_r;
        this.total_faltas_r = total_faltas_r;
        this.total_paseos_r = total_paseos_r;
        this.prima_dominical_r = prima_dominical_r;
        this.festivo_r = festivo_r;
        this.omisiones_r = omisiones_r;
        this.dif_paseos_r = dif_paseos_r;
        this.dif_prima_r = dif_prima_r;
        this.dif_tiempo_extra_r = dif_tiempo_extra_r;
        this.dif_sueldo_r = dif_sueldo_r;
        this.dias_pago_r = dias_pago_r;
        this.dias_cve_27_r = dias_cve_27_r;
    }

    public Integer getId_ra_historico() {
        return id_ra_historico;
    }

    public void setId_ra_historico(Integer id_ra_historico) {
        this.id_ra_historico = id_ra_historico;
    }

    public Integer getTrabajador_id() {
        return trabajador_id;
    }

    public void setTrabajador_id(Integer trabajador_id) {
        this.trabajador_id = trabajador_id;
    }

    public Integer getTipo_id() {
        return tipo_id;
    }

    public void setTipo_id(Integer tipo_id) {
        this.tipo_id = tipo_id;
    }

    public Double getHoras_planta() {
        return horas_planta;
    }

    public void setHoras_planta(Double horas_planta) {
        this.horas_planta = horas_planta;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getA1() {
        return a1;
    }

    public void setA1(String a1) {
        this.a1 = a1;
    }

    public String getA2() {
        return a2;
    }

    public void setA2(String a2) {
        this.a2 = a2;
    }

    public String getA3() {
        return a3;
    }

    public void setA3(String a3) {
        this.a3 = a3;
    }

    public String getA4() {
        return a4;
    }

    public void setA4(String a4) {
        this.a4 = a4;
    }

    public String getA5() {
        return a5;
    }

    public void setA5(String a5) {
        this.a5 = a5;
    }

    public String getA6() {
        return a6;
    }

    public void setA6(String a6) {
        this.a6 = a6;
    }

    public String getA7() {
        return a7;
    }

    public void setA7(String a7) {
        this.a7 = a7;
    }

    public String getA8() {
        return a8;
    }

    public void setA8(String a8) {
        this.a8 = a8;
    }

    public String getA9() {
        return a9;
    }

    public void setA9(String a9) {
        this.a9 = a9;
    }

    public String getA10() {
        return a10;
    }

    public void setA10(String a10) {
        this.a10 = a10;
    }

    public String getA11() {
        return a11;
    }

    public void setA11(String a11) {
        this.a11 = a11;
    }

    public String getA12() {
        return a12;
    }

    public void setA12(String a12) {
        this.a12 = a12;
    }

    public String getA13() {
        return a13;
    }

    public void setA13(String a13) {
        this.a13 = a13;
    }

    public String getA14() {
        return a14;
    }

    public void setA14(String a14) {
        this.a14 = a14;
    }

    public String getA15() {
        return a15;
    }

    public void setA15(String a15) {
        this.a15 = a15;
    }

    public String getA16() {
        return a16;
    }

    public void setA16(String a16) {
        this.a16 = a16;
    }

    public String getA17() {
        return a17;
    }

    public void setA17(String a17) {
        this.a17 = a17;
    }

    public String getA18() {
        return a18;
    }

    public void setA18(String a18) {
        this.a18 = a18;
    }

    public String getA19() {
        return a19;
    }

    public void setA19(String a19) {
        this.a19 = a19;
    }

    public String getA20() {
        return a20;
    }

    public void setA20(String a20) {
        this.a20 = a20;
    }

    public String getA21() {
        return a21;
    }

    public void setA21(String a21) {
        this.a21 = a21;
    }

    public String getA22() {
        return a22;
    }

    public void setA22(String a22) {
        this.a22 = a22;
    }

    public String getA23() {
        return a23;
    }

    public void setA23(String a23) {
        this.a23 = a23;
    }

    public String getA24() {
        return a24;
    }

    public void setA24(String a24) {
        this.a24 = a24;
    }

    public String getA25() {
        return a25;
    }

    public void setA25(String a25) {
        this.a25 = a25;
    }

    public String getA26() {
        return a26;
    }

    public void setA26(String a26) {
        this.a26 = a26;
    }

    public String getA27() {
        return a27;
    }

    public void setA27(String a27) {
        this.a27 = a27;
    }

    public String getA28() {
        return a28;
    }

    public void setA28(String a28) {
        this.a28 = a28;
    }

    public String getCaptura() {
        return captura;
    }

    public void setCaptura(String captura) {
        this.captura = captura;
    }

    public LocalDate getFecha_inicial_captura() {
        return fecha_inicial_captura;
    }

    public void setFecha_inicial_captura(LocalDate fecha_inicial_captura) {
        this.fecha_inicial_captura = fecha_inicial_captura;
    }

    public LocalDate getFecha_final_captura() {
        return fecha_final_captura;
    }

    public void setFecha_final_captura(LocalDate fecha_final_captura) {
        this.fecha_final_captura = fecha_final_captura;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Integer getActivo_ra15() {
        return activo_ra15;
    }

    public void setActivo_ra15(Integer activo_ra15) {
        this.activo_ra15 = activo_ra15;
    }

    public Double getProp_desc_1() {
        return prop_desc_1;
    }

    public void setProp_desc_1(Double prop_desc_1) {
        this.prop_desc_1 = prop_desc_1;
    }

    public Double getProp_desc_2() {
        return prop_desc_2;
    }

    public void setProp_desc_2(Double prop_desc_2) {
        this.prop_desc_2 = prop_desc_2;
    }

    public Double getTotal_hrs_descanso() {
        return total_hrs_descanso;
    }

    public void setTotal_hrs_descanso(Double total_hrs_descanso) {
        this.total_hrs_descanso = total_hrs_descanso;
    }

    public Integer getCod_puesto_suplencia() {
        return cod_puesto_suplencia;
    }

    public void setCod_puesto_suplencia(Integer cod_puesto_suplencia) {
        this.cod_puesto_suplencia = cod_puesto_suplencia;
    }

    public Integer getAnio_planta() {
        return anio_planta;
    }

    public void setAnio_planta(Integer anio_planta) {
        this.anio_planta = anio_planta;
    }

    public LocalDate getFecha_vigencia_planta() {
        return fecha_vigencia_planta;
    }

    public void setFecha_vigencia_planta(LocalDate fecha_vigencia_planta) {
        this.fecha_vigencia_planta = fecha_vigencia_planta;
    }

    public Integer getRuta_id() {
        return ruta_id;
    }

    public void setRuta_id(Integer ruta_id) {
        this.ruta_id = ruta_id;
    }

    public Integer getTurno_id() {
        return turno_id;
    }

    public void setTurno_id(Integer turno_id) {
        this.turno_id = turno_id;
    }

    public Integer getAbono_descuento_id() {
        return abono_descuento_id;
    }

    public void setAbono_descuento_id(Integer abono_descuento_id) {
        this.abono_descuento_id = abono_descuento_id;
    }

    public Integer getJornada_normal_id() {
        return jornada_normal_id;
    }

    public void setJornada_normal_id(Integer jornada_normal_id) {
        this.jornada_normal_id = jornada_normal_id;
    }

    public Integer getTiempo_extra_id() {
        return tiempo_extra_id;
    }

    public void setTiempo_extra_id(Integer tiempo_extra_id) {
        this.tiempo_extra_id = tiempo_extra_id;
    }

    public Integer getPaseos_laborados_id() {
        return paseos_laborados_id;
    }

    public void setPaseos_laborados_id(Integer paseos_laborados_id) {
        this.paseos_laborados_id = paseos_laborados_id;
    }

    public Integer getExcedente_jornada_id() {
        return excedente_jornada_id;
    }

    public void setExcedente_jornada_id(Integer excedente_jornada_id) {
        this.excedente_jornada_id = excedente_jornada_id;
    }

    public Integer getSuplencia_id() {
        return suplencia_id;
    }

    public void setSuplencia_id(Integer suplencia_id) {
        this.suplencia_id = suplencia_id;
    }

    public Integer getPeriodo_aplicacion_id() {
        return periodo_aplicacion_id;
    }

    public void setPeriodo_aplicacion_id(Integer periodo_aplicacion_id) {
        this.periodo_aplicacion_id = periodo_aplicacion_id;
    }

    public Integer getInasistencia_id() {
        return inasistencia_id;
    }

    public void setInasistencia_id(Integer inasistencia_id) {
        this.inasistencia_id = inasistencia_id;
    }

    public Integer getResumen_id() {
        return resumen_id;
    }

    public void setResumen_id(Integer resumen_id) {
        this.resumen_id = resumen_id;
    }

    public Double getAbono_jornada_historico() {
        return abono_jornada_historico;
    }

    public void setAbono_jornada_historico(Double abono_jornada_historico) {
        this.abono_jornada_historico = abono_jornada_historico;
    }

    public Double getDescuento_jornada_historico() {
        return descuento_jornada_historico;
    }

    public void setDescuento_jornada_historico(Double descuento_jornada_historico) {
        this.descuento_jornada_historico = descuento_jornada_historico;
    }

    public Double getAbono_doble_historico() {
        return abono_doble_historico;
    }

    public void setAbono_doble_historico(Double abono_doble_historico) {
        this.abono_doble_historico = abono_doble_historico;
    }

    public Double getDescuento_doble_historico() {
        return descuento_doble_historico;
    }

    public void setDescuento_doble_historico(Double descuento_doble_historico) {
        this.descuento_doble_historico = descuento_doble_historico;
    }

    public Double getAbono_triple_historico() {
        return abono_triple_historico;
    }

    public void setAbono_triple_historico(Double abono_triple_historico) {
        this.abono_triple_historico = abono_triple_historico;
    }

    public Double getDescuento_triple_historico() {
        return descuento_triple_historico;
    }

    public void setDescuento_triple_historico(Double descuento_triple_historico) {
        this.descuento_triple_historico = descuento_triple_historico;
    }

    public Double getAbono_descanso_historico() {
        return abono_descanso_historico;
    }

    public void setAbono_descanso_historico(Double abono_descanso_historico) {
        this.abono_descanso_historico = abono_descanso_historico;
    }

    public Double getDescuento_descanso_historico() {
        return descuento_descanso_historico;
    }

    public void setDescuento_descanso_historico(Double descuento_descanso_historico) {
        this.descuento_descanso_historico = descuento_descanso_historico;
    }

    public Double getAbono_prima_historico() {
        return abono_prima_historico;
    }

    public void setAbono_prima_historico(Double abono_prima_historico) {
        this.abono_prima_historico = abono_prima_historico;
    }

    public Double getDescuento_prima_historico() {
        return descuento_prima_historico;
    }

    public void setDescuento_prima_historico(Double descuento_prima_historico) {
        this.descuento_prima_historico = descuento_prima_historico;
    }

    public Double getAbono_festivo_historico() {
        return abono_festivo_historico;
    }

    public void setAbono_festivo_historico(Double abono_festivo_historico) {
        this.abono_festivo_historico = abono_festivo_historico;
    }

    public Double getDescuento_festivo_historico() {
        return descuento_festivo_historico;
    }

    public void setDescuento_festivo_historico(Double descuento_festivo_historico) {
        this.descuento_festivo_historico = descuento_festivo_historico;
    }

    public Double getAbono_dias_lab_historico() {
        return abono_dias_lab_historico;
    }

    public void setAbono_dias_lab_historico(Double abono_dias_lab_historico) {
        this.abono_dias_lab_historico = abono_dias_lab_historico;
    }

    public Double getDescuento_dias_lab_historico() {
        return descuento_dias_lab_historico;
    }

    public void setDescuento_dias_lab_historico(Double descuento_dias_lab_historico) {
        this.descuento_dias_lab_historico = descuento_dias_lab_historico;
    }

    public Double getAbono_dif_suplencia_historico() {
        return abono_dif_suplencia_historico;
    }

    public void setAbono_dif_suplencia_historico(Double abono_dif_suplencia_historico) {
        this.abono_dif_suplencia_historico = abono_dif_suplencia_historico;
    }

    public Double getDescuento_dif_suplencia_historico() {
        return descuento_dif_suplencia_historico;
    }

    public void setDescuento_dif_suplencia_historico(Double descuento_dif_suplencia_historico) {
        this.descuento_dif_suplencia_historico = descuento_dif_suplencia_historico;
    }

    public String getObservaciones_historico() {
        return observaciones_historico;
    }

    public void setObservaciones_historico(String observaciones_historico) {
        this.observaciones_historico = observaciones_historico;
    }

    public Double getDescuento_omisiones_historico() {
        return descuento_omisiones_historico;
    }

    public void setDescuento_omisiones_historico(Double descuento_omisiones_historico) {
        this.descuento_omisiones_historico = descuento_omisiones_historico;
    }

    public String getObservaciones_omisiones_historico() {
        return observaciones_omisiones_historico;
    }

    public void setObservaciones_omisiones_historico(String observaciones_omisiones_historico) {
        this.observaciones_omisiones_historico = observaciones_omisiones_historico;
    }

    public Double getLunes_jn() {
        return lunes_jn;
    }

    public void setLunes_jn(Double lunes_jn) {
        this.lunes_jn = lunes_jn;
    }

    public Double getMartes_jn() {
        return martes_jn;
    }

    public void setMartes_jn(Double martes_jn) {
        this.martes_jn = martes_jn;
    }

    public Double getMiercoles_jn() {
        return miercoles_jn;
    }

    public void setMiercoles_jn(Double miercoles_jn) {
        this.miercoles_jn = miercoles_jn;
    }

    public Double getJueves_jn() {
        return jueves_jn;
    }

    public void setJueves_jn(Double jueves_jn) {
        this.jueves_jn = jueves_jn;
    }

    public Double getViernes_jn() {
        return viernes_jn;
    }

    public void setViernes_jn(Double viernes_jn) {
        this.viernes_jn = viernes_jn;
    }

    public Double getSabado_jn() {
        return sabado_jn;
    }

    public void setSabado_jn(Double sabado_jn) {
        this.sabado_jn = sabado_jn;
    }

    public Double getDomingo_jn() {
        return domingo_jn;
    }

    public void setDomingo_jn(Double domingo_jn) {
        this.domingo_jn = domingo_jn;
    }

    public Double getLunes_te() {
        return lunes_te;
    }

    public void setLunes_te(Double lunes_te) {
        this.lunes_te = lunes_te;
    }

    public Double getMartes_te() {
        return martes_te;
    }

    public void setMartes_te(Double martes_te) {
        this.martes_te = martes_te;
    }

    public Double getMiercoles_te() {
        return miercoles_te;
    }

    public void setMiercoles_te(Double miercoles_te) {
        this.miercoles_te = miercoles_te;
    }

    public Double getJueves_te() {
        return jueves_te;
    }

    public void setJueves_te(Double jueves_te) {
        this.jueves_te = jueves_te;
    }

    public Double getViernes_te() {
        return viernes_te;
    }

    public void setViernes_te(Double viernes_te) {
        this.viernes_te = viernes_te;
    }

    public Double getSabado_te() {
        return sabado_te;
    }

    public void setSabado_te(Double sabado_te) {
        this.sabado_te = sabado_te;
    }

    public Double getDomingo_te() {
        return domingo_te;
    }

    public void setDomingo_te(Double domingo_te) {
        this.domingo_te = domingo_te;
    }

    public Double getLunes_pl() {
        return lunes_pl;
    }

    public void setLunes_pl(Double lunes_pl) {
        this.lunes_pl = lunes_pl;
    }

    public Double getMartes_pl() {
        return martes_pl;
    }

    public void setMartes_pl(Double martes_pl) {
        this.martes_pl = martes_pl;
    }

    public Double getMiercoles_pl() {
        return miercoles_pl;
    }

    public void setMiercoles_pl(Double miercoles_pl) {
        this.miercoles_pl = miercoles_pl;
    }

    public Double getJueves_pl() {
        return jueves_pl;
    }

    public void setJueves_pl(Double jueves_pl) {
        this.jueves_pl = jueves_pl;
    }

    public Double getViernes_pl() {
        return viernes_pl;
    }

    public void setViernes_pl(Double viernes_pl) {
        this.viernes_pl = viernes_pl;
    }

    public Double getSabado_pl() {
        return sabado_pl;
    }

    public void setSabado_pl(Double sabado_pl) {
        this.sabado_pl = sabado_pl;
    }

    public Double getDomingo_pl() {
        return domingo_pl;
    }

    public void setDomingo_pl(Double domingo_pl) {
        this.domingo_pl = domingo_pl;
    }

    public Double getLunes_ej() {
        return lunes_ej;
    }

    public void setLunes_ej(Double lunes_ej) {
        this.lunes_ej = lunes_ej;
    }

    public Double getMartes_ej() {
        return martes_ej;
    }

    public void setMartes_ej(Double martes_ej) {
        this.martes_ej = martes_ej;
    }

    public Double getMiercoles_ej() {
        return miercoles_ej;
    }

    public void setMiercoles_ej(Double miercoles_ej) {
        this.miercoles_ej = miercoles_ej;
    }

    public Double getJueves_ej() {
        return jueves_ej;
    }

    public void setJueves_ej(Double jueves_ej) {
        this.jueves_ej = jueves_ej;
    }

    public Double getViernes_ej() {
        return viernes_ej;
    }

    public void setViernes_ej(Double viernes_ej) {
        this.viernes_ej = viernes_ej;
    }

    public Double getSabado_ej() {
        return sabado_ej;
    }

    public void setSabado_ej(Double sabado_ej) {
        this.sabado_ej = sabado_ej;
    }

    public Double getDomingo_ej() {
        return domingo_ej;
    }

    public void setDomingo_ej(Double domingo_ej) {
        this.domingo_ej = domingo_ej;
    }

    public Double getLunes_sup() {
        return lunes_sup;
    }

    public void setLunes_sup(Double lunes_sup) {
        this.lunes_sup = lunes_sup;
    }

    public Double getMartes_sup() {
        return martes_sup;
    }

    public void setMartes_sup(Double martes_sup) {
        this.martes_sup = martes_sup;
    }

    public Double getMiercoles_sup() {
        return miercoles_sup;
    }

    public void setMiercoles_sup(Double miercoles_sup) {
        this.miercoles_sup = miercoles_sup;
    }

    public Double getJueves_sup() {
        return jueves_sup;
    }

    public void setJueves_sup(Double jueves_sup) {
        this.jueves_sup = jueves_sup;
    }

    public Double getViernes_sup() {
        return viernes_sup;
    }

    public void setViernes_sup(Double viernes_sup) {
        this.viernes_sup = viernes_sup;
    }

    public Double getSabado_sup() {
        return sabado_sup;
    }

    public void setSabado_sup(Double sabado_sup) {
        this.sabado_sup = sabado_sup;
    }

    public Double getDomingo_sup() {
        return domingo_sup;
    }

    public void setDomingo_sup(Double domingo_sup) {
        this.domingo_sup = domingo_sup;
    }

    public String getLunes_inc() {
        return lunes_inc;
    }

    public void setLunes_inc(String lunes_inc) {
        this.lunes_inc = lunes_inc;
    }

    public String getMartes_inc() {
        return martes_inc;
    }

    public void setMartes_inc(String martes_inc) {
        this.martes_inc = martes_inc;
    }

    public String getMiercoles_inc() {
        return miercoles_inc;
    }

    public void setMiercoles_inc(String miercoles_inc) {
        this.miercoles_inc = miercoles_inc;
    }

    public String getJueves_inc() {
        return jueves_inc;
    }

    public void setJueves_inc(String jueves_inc) {
        this.jueves_inc = jueves_inc;
    }

    public String getViernes_inc() {
        return viernes_inc;
    }

    public void setViernes_inc(String viernes_inc) {
        this.viernes_inc = viernes_inc;
    }

    public String getSabado_inc() {
        return sabado_inc;
    }

    public void setSabado_inc(String sabado_inc) {
        this.sabado_inc = sabado_inc;
    }

    public String getDomingo_inc() {
        return domingo_inc;
    }

    public void setDomingo_inc(String domingo_inc) {
        this.domingo_inc = domingo_inc;
    }

    public Double getHoras_turno_r() {
        return horas_turno_r;
    }

    public void setHoras_turno_r(Double horas_turno_r) {
        this.horas_turno_r = horas_turno_r;
    }

    public Double getHoras_doble_r() {
        return horas_doble_r;
    }

    public void setHoras_doble_r(Double horas_doble_r) {
        this.horas_doble_r = horas_doble_r;
    }

    public Double getHoras_triple_r() {
        return horas_triple_r;
    }

    public void setHoras_triple_r(Double horas_triple_r) {
        this.horas_triple_r = horas_triple_r;
    }

    public Double getDias_laborados_r() {
        return dias_laborados_r;
    }

    public void setDias_laborados_r(Double dias_laborados_r) {
        this.dias_laborados_r = dias_laborados_r;
    }

    public Integer getTotal_faltas_r() {
        return total_faltas_r;
    }

    public void setTotal_faltas_r(Integer total_faltas_r) {
        this.total_faltas_r = total_faltas_r;
    }

    public Double getTotal_paseos_r() {
        return total_paseos_r;
    }

    public void setTotal_paseos_r(Double total_paseos_r) {
        this.total_paseos_r = total_paseos_r;
    }

    public Integer getPrima_dominical_r() {
        return prima_dominical_r;
    }

    public void setPrima_dominical_r(Integer prima_dominical_r) {
        this.prima_dominical_r = prima_dominical_r;
    }

    public Double getFestivo_r() {
        return festivo_r;
    }

    public void setFestivo_r(Double festivo_r) {
        this.festivo_r = festivo_r;
    }

    public Double getOmisiones_r() {
        return omisiones_r;
    }

    public void setOmisiones_r(Double omisiones_r) {
        this.omisiones_r = omisiones_r;
    }

    public Double getDif_paseos_r() {
        return dif_paseos_r;
    }

    public void setDif_paseos_r(Double dif_paseos_r) {
        this.dif_paseos_r = dif_paseos_r;
    }

    public Double getDif_prima_r() {
        return dif_prima_r;
    }

    public void setDif_prima_r(Double dif_prima_r) {
        this.dif_prima_r = dif_prima_r;
    }

    public Double getDif_tiempo_extra_r() {
        return dif_tiempo_extra_r;
    }

    public void setDif_tiempo_extra_r(Double dif_tiempo_extra_r) {
        this.dif_tiempo_extra_r = dif_tiempo_extra_r;
    }

    public Double getDif_sueldo_r() {
        return dif_sueldo_r;
    }

    public void setDif_sueldo_r(Double dif_sueldo_r) {
        this.dif_sueldo_r = dif_sueldo_r;
    }

    public Integer getDias_pago_r() {
        return dias_pago_r;
    }

    public void setDias_pago_r(Integer dias_pago_r) {
        this.dias_pago_r = dias_pago_r;
    }

    public Integer getDias_cve_27_r() {
        return dias_cve_27_r;
    }

    public void setDias_cve_27_r(Integer dias_cve_27_r) {
        this.dias_cve_27_r = dias_cve_27_r;
    }

    
}
