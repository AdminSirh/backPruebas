/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.backB.backB.entity;

/**
 *
 * @author rroscero23
 */
public class JwtResponse {

    private String token;

    public JwtResponse(String token) {
        this.token = token;
    }

    public JwtResponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
