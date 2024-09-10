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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author rroscero23
 */
@Entity
@Table(name = "captura_semanal_ra_15")
public class Captura_Semanal_RA15 implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_ra;
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
    //private Integer total_faltas;
    private Integer cod_puesto_suplencia;
    private Integer anio_planta;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_vigencia_planta;
    private Integer ruta_id;
    private Integer turno_id;
    //Foráneas a otros campos
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "abono_descuento_id")
    private Captura_Semanal_Abonos_Descuentos_RA15 captura_Semanal_Abonos_Descuentos_RA15;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "jornada_normal_id")
    private Captura_Semanal_Jornada_Normal_RA15 captura_Semanal_Jornada_Normal_RA15;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tiempo_extra_id")
    private Captura_Semanal_Tiempo_Extra_RA15 captura_Semanal_Tiempo_Extra_RA15;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "paseos_laborados_id")
    private Captura_Semanal_Paseos_Laborados_RA15 captura_Semanal_Paseos_Laborados_RA15;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "excedente_jornada_id")
    private Captura_Semanal_Excedente_Jornada_RA15 captura_Semanal_Excedente_Jornada_RA15;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "suplencia_id")
    private Captura_Semanal_Suplencias_RA15 captura_Semanal_Suplencias_RA15;
    private Integer periodo_aplicacion_id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "inasistencia_id")
    private Captura_Semanal_Inasistencias_RA15 captura_Semanal_Inasistencias_RA15;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resumen_id")
    private Captura_Semanal_Resumen_IncidenciasRA15 captura_Semanal_Resumen_IncidenciasRA15;

    public Captura_Semanal_RA15() {
        super();
    }

    public Captura_Semanal_RA15(Integer id_ra, Integer trabajador_id, Integer tipo_id, Double horas_planta, String ruta, Integer estatus, String estado, String a1, String a2, String a3, String a4, String a5, String a6, String a7, String a8, String a9, String a10, String a11, String a12, String a13, String a14, String a15, String a16, String a17, String a18, String a19, String a20, String a21, String a22, String a23, String a24, String a25, String a26, String a27, String a28, String captura, LocalDate fecha_inicial_captura, LocalDate fecha_final_captura, Integer area, Integer activo_ra15, Double prop_desc_1, Double prop_desc_2, Double total_hrs_descanso, Integer cod_puesto_suplencia, Integer anio_planta, LocalDate fecha_vigencia_planta, Integer ruta_id, Integer turno_id, Captura_Semanal_Abonos_Descuentos_RA15 captura_Semanal_Abonos_Descuentos_RA15, Captura_Semanal_Jornada_Normal_RA15 captura_Semanal_Jornada_Normal_RA15, Captura_Semanal_Tiempo_Extra_RA15 captura_Semanal_Tiempo_Extra_RA15, Captura_Semanal_Paseos_Laborados_RA15 captura_Semanal_Paseos_Laborados_RA15, Captura_Semanal_Excedente_Jornada_RA15 captura_Semanal_Excedente_Jornada_RA15, Captura_Semanal_Suplencias_RA15 captura_Semanal_Suplencias_RA15, Integer periodo_aplicacion_id, Captura_Semanal_Inasistencias_RA15 captura_Semanal_Inasistencias_RA15, Captura_Semanal_Resumen_IncidenciasRA15 captura_Semanal_Resumen_IncidenciasRA15) {
        this.id_ra = id_ra;
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
        this.captura_Semanal_Abonos_Descuentos_RA15 = captura_Semanal_Abonos_Descuentos_RA15;
        this.captura_Semanal_Jornada_Normal_RA15 = captura_Semanal_Jornada_Normal_RA15;
        this.captura_Semanal_Tiempo_Extra_RA15 = captura_Semanal_Tiempo_Extra_RA15;
        this.captura_Semanal_Paseos_Laborados_RA15 = captura_Semanal_Paseos_Laborados_RA15;
        this.captura_Semanal_Excedente_Jornada_RA15 = captura_Semanal_Excedente_Jornada_RA15;
        this.captura_Semanal_Suplencias_RA15 = captura_Semanal_Suplencias_RA15;
        this.periodo_aplicacion_id = periodo_aplicacion_id;
        this.captura_Semanal_Inasistencias_RA15 = captura_Semanal_Inasistencias_RA15;
        this.captura_Semanal_Resumen_IncidenciasRA15 = captura_Semanal_Resumen_IncidenciasRA15;
    }

    public Integer getId_ra() {
        return id_ra;
    }

    public void setId_ra(Integer id_ra) {
        this.id_ra = id_ra;
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

    public Captura_Semanal_Abonos_Descuentos_RA15 getCaptura_Semanal_Abonos_Descuentos_RA15() {
        return captura_Semanal_Abonos_Descuentos_RA15;
    }

    public void setCaptura_Semanal_Abonos_Descuentos_RA15(Captura_Semanal_Abonos_Descuentos_RA15 captura_Semanal_Abonos_Descuentos_RA15) {
        this.captura_Semanal_Abonos_Descuentos_RA15 = captura_Semanal_Abonos_Descuentos_RA15;
    }

    public Captura_Semanal_Jornada_Normal_RA15 getCaptura_Semanal_Jornada_Normal_RA15() {
        return captura_Semanal_Jornada_Normal_RA15;
    }

    public void setCaptura_Semanal_Jornada_Normal_RA15(Captura_Semanal_Jornada_Normal_RA15 captura_Semanal_Jornada_Normal_RA15) {
        this.captura_Semanal_Jornada_Normal_RA15 = captura_Semanal_Jornada_Normal_RA15;
    }

    public Captura_Semanal_Tiempo_Extra_RA15 getCaptura_Semanal_Tiempo_Extra_RA15() {
        return captura_Semanal_Tiempo_Extra_RA15;
    }

    public void setCaptura_Semanal_Tiempo_Extra_RA15(Captura_Semanal_Tiempo_Extra_RA15 captura_Semanal_Tiempo_Extra_RA15) {
        this.captura_Semanal_Tiempo_Extra_RA15 = captura_Semanal_Tiempo_Extra_RA15;
    }

    public Captura_Semanal_Paseos_Laborados_RA15 getCaptura_Semanal_Paseos_Laborados_RA15() {
        return captura_Semanal_Paseos_Laborados_RA15;
    }

    public void setCaptura_Semanal_Paseos_Laborados_RA15(Captura_Semanal_Paseos_Laborados_RA15 captura_Semanal_Paseos_Laborados_RA15) {
        this.captura_Semanal_Paseos_Laborados_RA15 = captura_Semanal_Paseos_Laborados_RA15;
    }

    public Captura_Semanal_Excedente_Jornada_RA15 getCaptura_Semanal_Excedente_Jornada_RA15() {
        return captura_Semanal_Excedente_Jornada_RA15;
    }

    public void setCaptura_Semanal_Excedente_Jornada_RA15(Captura_Semanal_Excedente_Jornada_RA15 captura_Semanal_Excedente_Jornada_RA15) {
        this.captura_Semanal_Excedente_Jornada_RA15 = captura_Semanal_Excedente_Jornada_RA15;
    }

    public Captura_Semanal_Suplencias_RA15 getCaptura_Semanal_Suplencias_RA15() {
        return captura_Semanal_Suplencias_RA15;
    }

    public void setCaptura_Semanal_Suplencias_RA15(Captura_Semanal_Suplencias_RA15 captura_Semanal_Suplencias_RA15) {
        this.captura_Semanal_Suplencias_RA15 = captura_Semanal_Suplencias_RA15;
    }

    public Integer getPeriodo_aplicacion_id() {
        return periodo_aplicacion_id;
    }

    public void setPeriodo_aplicacion_id(Integer periodo_aplicacion_id) {
        this.periodo_aplicacion_id = periodo_aplicacion_id;
    }

    public Captura_Semanal_Inasistencias_RA15 getCaptura_Semanal_Inasistencias_RA15() {
        return captura_Semanal_Inasistencias_RA15;
    }

    public void setCaptura_Semanal_Inasistencias_RA15(Captura_Semanal_Inasistencias_RA15 captura_Semanal_Inasistencias_RA15) {
        this.captura_Semanal_Inasistencias_RA15 = captura_Semanal_Inasistencias_RA15;
    }

    public Captura_Semanal_Resumen_IncidenciasRA15 getCaptura_Semanal_Resumen_IncidenciasRA15() {
        return captura_Semanal_Resumen_IncidenciasRA15;
    }

    public void setCaptura_Semanal_Resumen_IncidenciasRA15(Captura_Semanal_Resumen_IncidenciasRA15 captura_Semanal_Resumen_IncidenciasRA15) {
        this.captura_Semanal_Resumen_IncidenciasRA15 = captura_Semanal_Resumen_IncidenciasRA15;
    }

    
}
