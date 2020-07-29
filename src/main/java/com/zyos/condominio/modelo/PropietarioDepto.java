/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zyos.condominio.modelo;

/**
 *
 * @author esneider
 */
public class PropietarioDepto {
     private int propietario;
     private int departamento;
     
     public PropietarioDepto(int propietario, int departamento){
         this.propietario = propietario;
         this.departamento = departamento;
     }

    public int getPropietario() {
        return propietario;
    }

    public void setPropietario(int propietario) {
        this.propietario = propietario;
    }

    public int getDepartamento() {
        return departamento;
    }

    public void setDepartamento(int departamento) {
        this.departamento = departamento;
    }
     
     
}
