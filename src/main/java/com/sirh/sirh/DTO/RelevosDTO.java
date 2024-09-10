/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

/**
 *
 * @author oguerrero23
 */
public class RelevosDTO {
    private Integer id_planta;
    private Integer relevo_1;
    private Integer relevo_2;
    private Integer descanso_1;
    private Integer descanso_2;

    public RelevosDTO() {
    }

    public RelevosDTO(Integer id_planta, Integer relevo_1, Integer relevo_2, Integer descanso_1, Integer descanso_2) {
        this.id_planta = id_planta;
        this.relevo_1 = relevo_1;
        this.relevo_2 = relevo_2;
        this.descanso_1 = descanso_1;
        this.descanso_2 = descanso_2;
    }

    public Integer getId_planta() {
        return id_planta;
    }

    public void setId_planta(Integer id_planta) {
        this.id_planta = id_planta;
    }

    public Integer getRelevo_1() {
        return relevo_1;
    }

    public void setRelevo_1(Integer relevo_1) {
        this.relevo_1 = relevo_1;
    }

    public Integer getRelevo_2() {
        return relevo_2;
    }

    public void setRelevo_2(Integer relevo_2) {
        this.relevo_2 = relevo_2;
    }

    public Integer getDescanso_1() {
        return descanso_1;
    }

    public void setDescanso_1(Integer descanso_1) {
        this.descanso_1 = descanso_1;
    }

    public Integer getDescanso_2() {
        return descanso_2;
    }

    public void setDescanso_2(Integer descanso_2) {
        this.descanso_2 = descanso_2;
    }

    
}
