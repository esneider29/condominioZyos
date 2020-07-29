/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zyos.condominio.modelo;

import com.zyos.condominio.entities.Departamento;
import com.zyos.condominio.entities.Transaccion;

/**
 *
 * @author esneider
 */
public class Resumen {
    private String transaccion;    
    private double valor;
    private int departamento;
    private String Asociacion;

    public Resumen(String transaccion, double valor, int departamento, String Asociacion) {
        this.transaccion = transaccion;
        this.valor = valor;
        this.departamento = departamento;
        this.Asociacion = Asociacion;
    }

    public String getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(String transaccion) {
        this.transaccion = transaccion;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getDepartamento() {
        return departamento;
    }

    public void setDepartamento(int departamento) {
        this.departamento = departamento;
    }

    public String getAsociacion() {
        return Asociacion;
    }

    public void setAsociacion(String Asociacion) {
        this.Asociacion = Asociacion;
    }

    
    
    
}
