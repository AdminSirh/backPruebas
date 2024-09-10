/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.entity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "admin_log")
public class AdminLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer log_id;                   // Registrar clave primaria
    private String type;                     // Tipo de registro
    private String operation;                 // Descripción del evento de operación de registro
    private String remote_addr;                // Solicitar dirección ip
    private String request_uri;                //URI
    private String method;                   // Método de solicitud
    private String params;                   // Enviar parámetros
    @Column(name = "operate_date")
    private Date createdAt;                  //Tiempo de empezar
    private Integer user_id;                    // ID de usuario
    private String user_name;                 //nombre de usuario
    private String result_params;            // Devolver parámetros
    private String exception_log;           // Descripción de la excepción

    public AdminLog() {
    }

    public Integer getLog_id() {
        return log_id;
    }

    public void setLog_id(Integer log_id) {
        this.log_id = log_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getRemote_addr() {
        return remote_addr;
    }

    public void setRemote_addr(String remote_addr) {
        this.remote_addr = remote_addr;
    }

    public String getRequest_uri() {
        return request_uri;
    }

    public void setRequest_uri(String request_uri) {
        this.request_uri = request_uri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getResult_params() {
        return result_params;
    }

    public void setResult_params(String result_params) {
        this.result_params = result_params;
    }

    public String getException_log() {
        return exception_log;
    }

    public void setException_log(String exception_log) {
        this.exception_log = exception_log;
    }

}
