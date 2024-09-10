/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.entity;

import java.io.Serializable;

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
@Table(name = "relacion_incapacidades_nomina_dias")
public class Relacion_Incapacidades_Nomina_Dias implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_relacion_incapacidad;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nomina_id")
    private Cat_Tipo_Nomina cat_Tipo_Nomina;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "prestaciones_si_no")
    private Cat_Si_No prestaciones_si_no;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "motivo_incapacidad_id")
    private Cat_Motivo_Incapacidad cat_Motivo_Incapacidad;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_incapacidad_id")
    private Cat_Tipo_Incapacidad cat_Tipo_Incapacidad;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "incidencia_id_primer_grupo")
    private Cat_Incidencias cat_IncidenciasPrimerGrupo;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "incidencia_id_segundo_grupo")
    private Cat_Incidencias cat_IncidenciasSegundoGrupo;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "incidencia_id_tercer_grupo")
    private Cat_Incidencias cat_IncidenciasTercerGrupo;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "incidencia_id_cuarto_grupo")
    private Cat_Incidencias cat_IncidenciasCuartoGrupo;
    private Integer dias_primer_grupo;
    private Integer dias_segundo_grupo;
    private Integer dias_tercer_grupo;
    private Integer dias_cuarto_grupo;
    private Integer extemporanea_si_no;

    public Relacion_Incapacidades_Nomina_Dias() {
    }

    public Relacion_Incapacidades_Nomina_Dias(Integer id_relacion_incapacidad, Cat_Tipo_Nomina cat_Tipo_Nomina, Cat_Si_No prestaciones_si_no, Cat_Motivo_Incapacidad cat_Motivo_Incapacidad, Cat_Tipo_Incapacidad cat_Tipo_Incapacidad, Cat_Incidencias cat_IncidenciasPrimerGrupo, Cat_Incidencias cat_IncidenciasSegundoGrupo, Cat_Incidencias cat_IncidenciasTercerGrupo, Cat_Incidencias cat_IncidenciasCuartoGrupo, Integer dias_primer_grupo, Integer dias_segundo_grupo, Integer dias_tercer_grupo, Integer dias_cuarto_grupo, Integer extemporanea_si_no) {
        this.id_relacion_incapacidad = id_relacion_incapacidad;
        this.cat_Tipo_Nomina = cat_Tipo_Nomina;
        this.prestaciones_si_no = prestaciones_si_no;
        this.cat_Motivo_Incapacidad = cat_Motivo_Incapacidad;
        this.cat_Tipo_Incapacidad = cat_Tipo_Incapacidad;
        this.cat_IncidenciasPrimerGrupo = cat_IncidenciasPrimerGrupo;
        this.cat_IncidenciasSegundoGrupo = cat_IncidenciasSegundoGrupo;
        this.cat_IncidenciasTercerGrupo = cat_IncidenciasTercerGrupo;
        this.cat_IncidenciasCuartoGrupo = cat_IncidenciasCuartoGrupo;
        this.dias_primer_grupo = dias_primer_grupo;
        this.dias_segundo_grupo = dias_segundo_grupo;
        this.dias_tercer_grupo = dias_tercer_grupo;
        this.dias_cuarto_grupo = dias_cuarto_grupo;
        this.extemporanea_si_no = extemporanea_si_no;
    }

    public Integer getId_relacion_incapacidad() {
        return id_relacion_incapacidad;
    }

    public void setId_relacion_incapacidad(Integer id_relacion_incapacidad) {
        this.id_relacion_incapacidad = id_relacion_incapacidad;
    }

    public Cat_Tipo_Nomina getCat_Tipo_Nomina() {
        return cat_Tipo_Nomina;
    }

    public void setCat_Tipo_Nomina(Cat_Tipo_Nomina cat_Tipo_Nomina) {
        this.cat_Tipo_Nomina = cat_Tipo_Nomina;
    }

    public Cat_Si_No getPrestaciones_si_no() {
        return prestaciones_si_no;
    }

    public void setPrestaciones_si_no(Cat_Si_No prestaciones_si_no) {
        this.prestaciones_si_no = prestaciones_si_no;
    }

    public Cat_Motivo_Incapacidad getCat_Motivo_Incapacidad() {
        return cat_Motivo_Incapacidad;
    }

    public void setCat_Motivo_Incapacidad(Cat_Motivo_Incapacidad cat_Motivo_Incapacidad) {
        this.cat_Motivo_Incapacidad = cat_Motivo_Incapacidad;
    }

    public Cat_Tipo_Incapacidad getCat_Tipo_Incapacidad() {
        return cat_Tipo_Incapacidad;
    }

    public void setCat_Tipo_Incapacidad(Cat_Tipo_Incapacidad cat_Tipo_Incapacidad) {
        this.cat_Tipo_Incapacidad = cat_Tipo_Incapacidad;
    }

    public Cat_Incidencias getCat_IncidenciasPrimerGrupo() {
        return cat_IncidenciasPrimerGrupo;
    }

    public void setCat_IncidenciasPrimerGrupo(Cat_Incidencias cat_IncidenciasPrimerGrupo) {
        this.cat_IncidenciasPrimerGrupo = cat_IncidenciasPrimerGrupo;
    }

    public Cat_Incidencias getCat_IncidenciasSegundoGrupo() {
        return cat_IncidenciasSegundoGrupo;
    }

    public void setCat_IncidenciasSegundoGrupo(Cat_Incidencias cat_IncidenciasSegundoGrupo) {
        this.cat_IncidenciasSegundoGrupo = cat_IncidenciasSegundoGrupo;
    }

    public Cat_Incidencias getCat_IncidenciasTercerGrupo() {
        return cat_IncidenciasTercerGrupo;
    }

    public void setCat_IncidenciasTercerGrupo(Cat_Incidencias cat_IncidenciasTercerGrupo) {
        this.cat_IncidenciasTercerGrupo = cat_IncidenciasTercerGrupo;
    }

    public Cat_Incidencias getCat_IncidenciasCuartoGrupo() {
        return cat_IncidenciasCuartoGrupo;
    }

    public void setCat_IncidenciasCuartoGrupo(Cat_Incidencias cat_IncidenciasCuartoGrupo) {
        this.cat_IncidenciasCuartoGrupo = cat_IncidenciasCuartoGrupo;
    }

    public Integer getDias_primer_grupo() {
        return dias_primer_grupo;
    }

    public void setDias_primer_grupo(Integer dias_primer_grupo) {
        this.dias_primer_grupo = dias_primer_grupo;
    }

    public Integer getDias_segundo_grupo() {
        return dias_segundo_grupo;
    }

    public void setDias_segundo_grupo(Integer dias_segundo_grupo) {
        this.dias_segundo_grupo = dias_segundo_grupo;
    }

    public Integer getDias_tercer_grupo() {
        return dias_tercer_grupo;
    }

    public void setDias_tercer_grupo(Integer dias_tercer_grupo) {
        this.dias_tercer_grupo = dias_tercer_grupo;
    }

    public Integer getDias_cuarto_grupo() {
        return dias_cuarto_grupo;
    }

    public void setDias_cuarto_grupo(Integer dias_cuarto_grupo) {
        this.dias_cuarto_grupo = dias_cuarto_grupo;
    }

    public Integer getExtemporanea_si_no() {
        return extemporanea_si_no;
    }

    public void setExtemporanea_si_no(Integer extemporanea_si_no) {
        this.extemporanea_si_no = extemporanea_si_no;
    }

}
