/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

/**
 *
 * @author rroscero23
 */
public class FiniquitoDTO {

    //Datos del trabajador para cálculo
    private Double sueldoDiarioTabulado;
    private Double sueldoMensualTabulado;
    private Double salarioDiarioIntegrado;
    private Double salarioMensualIntegrado;
    //Percepciones
    private Double mesesIndemnizacion;
    private Double veinteDiasAnio;
    private Double diasPrimaAntiguedad;
    private Double mesesCompensacionAntiguedad;
    private Double diasVacaciones;
    private Double diasPrimaVacacional;
    private Double diasAguinaldo;
    private String periodoVacacionalStr;
    private Integer periodoAguinaldoAnio;
    //Deducciones
    private Double isrIndemnizacion;
    private Double isrFiniquito;
    //Importes individuales 
    private Double importeUnitarioPrimaAntiguedad;
    private Double importeUnitarioPrimaVacacional;
    //Totales (Multiplicacion por campos correspondientes)
    private Double totalMesesIndemnizacion;
    private Double totalVeinteDiasAnio;
    private Double totalDiasPrimaAntiguedad;
    private Double totalMesesCompAntiguedad;
    private Double totalDiasVacaciones;
    private Double totaDiasPrimaVacacional;
    private Double totalDiasAguinaldo;
    //Conceptos de Pensión alimenticia para deducciones en porcentaje
    private Double pension1;
    private Double pension2;
    private Double pension3;
    private Double pension4;
    private Double pension5;
    //Conceptos de Pensión alimenticia para deducciones en couta fija
    private Double pension1Fija;
    private Double pension2Fija;
    private Double pension3Fija;
    private Double pension4Fija;
    private Double pension5Fija;
    //Gran total de deducciones y percepciones (Sumas)
    private Double totalPercepciones;
    private Double totalDeducciones;

    public FiniquitoDTO() {
    }

    public FiniquitoDTO(Double sueldoDiarioTabulado, Double sueldoMensualTabulado, Double salarioDiarioIntegrado, Double salarioMensualIntegrado, Double mesesIndemnizacion, Double veinteDiasAnio, Double diasPrimaAntiguedad, Double mesesCompensacionAntiguedad, Double diasVacaciones, Double diasPrimaVacacional, Double diasAguinaldo, String periodoVacacionalStr, Integer periodoAguinaldoAnio, Double isrIndemnizacion, Double isrFiniquito, Double importeUnitarioPrimaAntiguedad, Double importeUnitarioPrimaVacacional, Double totalMesesIndemnizacion, Double totalVeinteDiasAnio, Double totalDiasPrimaAntiguedad, Double totalMesesCompAntiguedad, Double totalDiasVacaciones, Double totaDiasPrimaVacacional, Double totalDiasAguinaldo, Double pension1, Double pension2, Double pension3, Double pension4, Double pension5, Double pension1Fija, Double pension2Fija, Double pension3Fija, Double pension4Fija, Double pension5Fija, Double totalPercepciones, Double totalDeducciones) {
        this.sueldoDiarioTabulado = sueldoDiarioTabulado;
        this.sueldoMensualTabulado = sueldoMensualTabulado;
        this.salarioDiarioIntegrado = salarioDiarioIntegrado;
        this.salarioMensualIntegrado = salarioMensualIntegrado;
        this.mesesIndemnizacion = mesesIndemnizacion;
        this.veinteDiasAnio = veinteDiasAnio;
        this.diasPrimaAntiguedad = diasPrimaAntiguedad;
        this.mesesCompensacionAntiguedad = mesesCompensacionAntiguedad;
        this.diasVacaciones = diasVacaciones;
        this.diasPrimaVacacional = diasPrimaVacacional;
        this.diasAguinaldo = diasAguinaldo;
        this.periodoVacacionalStr = periodoVacacionalStr;
        this.periodoAguinaldoAnio = periodoAguinaldoAnio;
        this.isrIndemnizacion = isrIndemnizacion;
        this.isrFiniquito = isrFiniquito;
        this.importeUnitarioPrimaAntiguedad = importeUnitarioPrimaAntiguedad;
        this.importeUnitarioPrimaVacacional = importeUnitarioPrimaVacacional;
        this.totalMesesIndemnizacion = totalMesesIndemnizacion;
        this.totalVeinteDiasAnio = totalVeinteDiasAnio;
        this.totalDiasPrimaAntiguedad = totalDiasPrimaAntiguedad;
        this.totalMesesCompAntiguedad = totalMesesCompAntiguedad;
        this.totalDiasVacaciones = totalDiasVacaciones;
        this.totaDiasPrimaVacacional = totaDiasPrimaVacacional;
        this.totalDiasAguinaldo = totalDiasAguinaldo;
        this.pension1 = pension1;
        this.pension2 = pension2;
        this.pension3 = pension3;
        this.pension4 = pension4;
        this.pension5 = pension5;
        this.pension1Fija = pension1Fija;
        this.pension2Fija = pension2Fija;
        this.pension3Fija = pension3Fija;
        this.pension4Fija = pension4Fija;
        this.pension5Fija = pension5Fija;
        this.totalPercepciones = totalPercepciones;
        this.totalDeducciones = totalDeducciones;
    }

    public Double getSueldoDiarioTabulado() {
        return sueldoDiarioTabulado;
    }

    public void setSueldoDiarioTabulado(Double sueldoDiarioTabulado) {
        this.sueldoDiarioTabulado = sueldoDiarioTabulado;
    }

    public Double getSueldoMensualTabulado() {
        return sueldoMensualTabulado;
    }

    public void setSueldoMensualTabulado(Double sueldoMensualTabulado) {
        this.sueldoMensualTabulado = sueldoMensualTabulado;
    }

    public Double getSalarioDiarioIntegrado() {
        return salarioDiarioIntegrado;
    }

    public void setSalarioDiarioIntegrado(Double salarioDiarioIntegrado) {
        this.salarioDiarioIntegrado = salarioDiarioIntegrado;
    }

    public Double getSalarioMensualIntegrado() {
        return salarioMensualIntegrado;
    }

    public void setSalarioMensualIntegrado(Double salarioMensualIntegrado) {
        this.salarioMensualIntegrado = salarioMensualIntegrado;
    }

    public Double getMesesIndemnizacion() {
        return mesesIndemnizacion;
    }

    public void setMesesIndemnizacion(Double mesesIndemnizacion) {
        this.mesesIndemnizacion = mesesIndemnizacion;
    }

    public Double getVeinteDiasAnio() {
        return veinteDiasAnio;
    }

    public void setVeinteDiasAnio(Double veinteDiasAnio) {
        this.veinteDiasAnio = veinteDiasAnio;
    }

    public Double getDiasPrimaAntiguedad() {
        return diasPrimaAntiguedad;
    }

    public void setDiasPrimaAntiguedad(Double diasPrimaAntiguedad) {
        this.diasPrimaAntiguedad = diasPrimaAntiguedad;
    }

    public Double getMesesCompensacionAntiguedad() {
        return mesesCompensacionAntiguedad;
    }

    public void setMesesCompensacionAntiguedad(Double mesesCompensacionAntiguedad) {
        this.mesesCompensacionAntiguedad = mesesCompensacionAntiguedad;
    }

    public Double getDiasVacaciones() {
        return diasVacaciones;
    }

    public void setDiasVacaciones(Double diasVacaciones) {
        this.diasVacaciones = diasVacaciones;
    }

    public Double getDiasPrimaVacacional() {
        return diasPrimaVacacional;
    }

    public void setDiasPrimaVacacional(Double diasPrimaVacacional) {
        this.diasPrimaVacacional = diasPrimaVacacional;
    }

    public Double getDiasAguinaldo() {
        return diasAguinaldo;
    }

    public void setDiasAguinaldo(Double diasAguinaldo) {
        this.diasAguinaldo = diasAguinaldo;
    }

    public String getPeriodoVacacionalStr() {
        return periodoVacacionalStr;
    }

    public void setPeriodoVacacionalStr(String periodoVacacionalStr) {
        this.periodoVacacionalStr = periodoVacacionalStr;
    }

    public Integer getPeriodoAguinaldoAnio() {
        return periodoAguinaldoAnio;
    }

    public void setPeriodoAguinaldoAnio(Integer periodoAguinaldoAnio) {
        this.periodoAguinaldoAnio = periodoAguinaldoAnio;
    }

    public Double getIsrIndemnizacion() {
        return isrIndemnizacion;
    }

    public void setIsrIndemnizacion(Double isrIndemnizacion) {
        this.isrIndemnizacion = isrIndemnizacion;
    }

    public Double getIsrFiniquito() {
        return isrFiniquito;
    }

    public void setIsrFiniquito(Double isrFiniquito) {
        this.isrFiniquito = isrFiniquito;
    }

    public Double getImporteUnitarioPrimaAntiguedad() {
        return importeUnitarioPrimaAntiguedad;
    }

    public void setImporteUnitarioPrimaAntiguedad(Double importeUnitarioPrimaAntiguedad) {
        this.importeUnitarioPrimaAntiguedad = importeUnitarioPrimaAntiguedad;
    }

    public Double getImporteUnitarioPrimaVacacional() {
        return importeUnitarioPrimaVacacional;
    }

    public void setImporteUnitarioPrimaVacacional(Double importeUnitarioPrimaVacacional) {
        this.importeUnitarioPrimaVacacional = importeUnitarioPrimaVacacional;
    }

    public Double getTotalMesesIndemnizacion() {
        return totalMesesIndemnizacion;
    }

    public void setTotalMesesIndemnizacion(Double totalMesesIndemnizacion) {
        this.totalMesesIndemnizacion = totalMesesIndemnizacion;
    }

    public Double getTotalVeinteDiasAnio() {
        return totalVeinteDiasAnio;
    }

    public void setTotalVeinteDiasAnio(Double totalVeinteDiasAnio) {
        this.totalVeinteDiasAnio = totalVeinteDiasAnio;
    }

    public Double getTotalDiasPrimaAntiguedad() {
        return totalDiasPrimaAntiguedad;
    }

    public void setTotalDiasPrimaAntiguedad(Double totalDiasPrimaAntiguedad) {
        this.totalDiasPrimaAntiguedad = totalDiasPrimaAntiguedad;
    }

    public Double getTotalMesesCompAntiguedad() {
        return totalMesesCompAntiguedad;
    }

    public void setTotalMesesCompAntiguedad(Double totalMesesCompAntiguedad) {
        this.totalMesesCompAntiguedad = totalMesesCompAntiguedad;
    }

    public Double getTotalDiasVacaciones() {
        return totalDiasVacaciones;
    }

    public void setTotalDiasVacaciones(Double totalDiasVacaciones) {
        this.totalDiasVacaciones = totalDiasVacaciones;
    }

    public Double getTotaDiasPrimaVacacional() {
        return totaDiasPrimaVacacional;
    }

    public void setTotaDiasPrimaVacacional(Double totaDiasPrimaVacacional) {
        this.totaDiasPrimaVacacional = totaDiasPrimaVacacional;
    }

    public Double getTotalDiasAguinaldo() {
        return totalDiasAguinaldo;
    }

    public void setTotalDiasAguinaldo(Double totalDiasAguinaldo) {
        this.totalDiasAguinaldo = totalDiasAguinaldo;
    }

    public Double getPension1() {
        return pension1;
    }

    public void setPension1(Double pension1) {
        this.pension1 = pension1;
    }

    public Double getPension2() {
        return pension2;
    }

    public void setPension2(Double pension2) {
        this.pension2 = pension2;
    }

    public Double getPension3() {
        return pension3;
    }

    public void setPension3(Double pension3) {
        this.pension3 = pension3;
    }

    public Double getPension4() {
        return pension4;
    }

    public void setPension4(Double pension4) {
        this.pension4 = pension4;
    }

    public Double getPension5() {
        return pension5;
    }

    public void setPension5(Double pension5) {
        this.pension5 = pension5;
    }

    public Double getPension1Fija() {
        return pension1Fija;
    }

    public void setPension1Fija(Double pension1Fija) {
        this.pension1Fija = pension1Fija;
    }

    public Double getPension2Fija() {
        return pension2Fija;
    }

    public void setPension2Fija(Double pension2Fija) {
        this.pension2Fija = pension2Fija;
    }

    public Double getPension3Fija() {
        return pension3Fija;
    }

    public void setPension3Fija(Double pension3Fija) {
        this.pension3Fija = pension3Fija;
    }

    public Double getPension4Fija() {
        return pension4Fija;
    }

    public void setPension4Fija(Double pension4Fija) {
        this.pension4Fija = pension4Fija;
    }

    public Double getPension5Fija() {
        return pension5Fija;
    }

    public void setPension5Fija(Double pension5Fija) {
        this.pension5Fija = pension5Fija;
    }

    public Double getTotalPercepciones() {
        return totalPercepciones;
    }

    public void setTotalPercepciones(Double totalPercepciones) {
        this.totalPercepciones = totalPercepciones;
    }

    public Double getTotalDeducciones() {
        return totalDeducciones;
    }

    public void setTotalDeducciones(Double totalDeducciones) {
        this.totalDeducciones = totalDeducciones;
    }

}
