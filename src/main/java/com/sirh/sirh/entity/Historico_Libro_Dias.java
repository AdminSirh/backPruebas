package com.sirh.sirh.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.data.annotation.CreatedDate;

/**
 *
 * @author nreyes22
 */
@Entity
@Table(name="historico_libro_dias")
public class Historico_Libro_Dias implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_historico_libro_dias;
    
    private Integer libro_dias_id;
    private Integer comentario;
    private Integer dia_id;
    private String horario_entrada; 
    private String horario_salida;
    private double horas_trabajadas;
    private double horas_pago;
    @CreatedDate
    private LocalDateTime fecha_captura;
    @CreatedDate
    private LocalDateTime fecha_movimiento;
    private Integer estatus;
    private Integer trabajador_id;
    private Integer dia_descanso;
    private Integer deposito_id;

    public Historico_Libro_Dias() {
    }

    public Historico_Libro_Dias(Integer id_historico_libro_dias, Integer libro_dias_id, Integer comentario, Integer dia_id, String horario_entrada, String horario_salida, double horas_trabajadas, double horas_pago, LocalDateTime fecha_captura, LocalDateTime fecha_movimiento, Integer estatus, Integer trabajador_id, Integer dia_descanso, Integer deposito_id) {
        this.id_historico_libro_dias = id_historico_libro_dias;
        this.libro_dias_id = libro_dias_id;
        this.comentario = comentario;
        this.dia_id = dia_id;
        this.horario_entrada = horario_entrada;
        this.horario_salida = horario_salida;
        this.horas_trabajadas = horas_trabajadas;
        this.horas_pago = horas_pago;
        this.fecha_captura = fecha_captura;
        this.fecha_movimiento = fecha_movimiento;
        this.estatus = estatus;
        this.trabajador_id = trabajador_id;
        this.dia_descanso = dia_descanso;
        this.deposito_id = deposito_id;
    }

    public Integer getId_historico_libro_dias() {
        return id_historico_libro_dias;
    }

    public void setId_historico_libro_dias(Integer id_historico_libro_dias) {
        this.id_historico_libro_dias = id_historico_libro_dias;
    }

    public Integer getLibro_dias_id() {
        return libro_dias_id;
    }

    public void setLibro_dias_id(Integer libro_dias_id) {
        this.libro_dias_id = libro_dias_id;
    }

    public Integer getComentario() {
        return comentario;
    }

    public void setComentario(Integer comentario) {
        this.comentario = comentario;
    }

    public Integer getDia_id() {
        return dia_id;
    }

    public void setDia_id(Integer dia_id) {
        this.dia_id = dia_id;
    }

    public String getHorario_entrada() {
        return horario_entrada;
    }

    public void setHorario_entrada(String horario_entrada) {
        this.horario_entrada = horario_entrada;
    }

    public String getHorario_salida() {
        return horario_salida;
    }

    public void setHorario_salida(String horario_salida) {
        this.horario_salida = horario_salida;
    }

    public double getHoras_trabajadas() {
        return horas_trabajadas;
    }

    public void setHoras_trabajadas(double horas_trabajadas) {
        this.horas_trabajadas = horas_trabajadas;
    }

    public double getHoras_pago() {
        return horas_pago;
    }

    public void setHoras_pago(double horas_pago) {
        this.horas_pago = horas_pago;
    }

    public LocalDateTime getFecha_captura() {
        return fecha_captura;
    }

    public void setFecha_captura(LocalDateTime fecha_captura) {
        this.fecha_captura = fecha_captura;
    }

    public LocalDateTime getFecha_movimiento() {
        return fecha_movimiento;
    }

    public void setFecha_movimiento(LocalDateTime fecha_movimiento) {
        this.fecha_movimiento = fecha_movimiento;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public Integer getTrabajador_id() {
        return trabajador_id;
    }

    public void setTrabajador_id(Integer trabajador_id) {
        this.trabajador_id = trabajador_id;
    }

    public Integer getDia_descanso() {
        return dia_descanso;
    }

    public void setDia_descanso(Integer dia_descanso) {
        this.dia_descanso = dia_descanso;
    }

    public Integer getDeposito_id() {
        return deposito_id;
    }

    public void setDeposito_id(Integer deposito_id) {
        this.deposito_id = deposito_id;
    }

   
    
    
    
    
}
