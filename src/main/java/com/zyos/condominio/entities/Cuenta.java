/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zyos.condominio.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author esneider
 */
@Entity
@Table(name = "cuenta")
@NamedQueries({
    @NamedQuery(name = "findByCuenta", query = "SELECT c FROM Cuenta c WHERE c.cuenta = :cuenta")})
public class Cuenta implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @Basic(optional = false)    
    @Column(name = "id")
    private Integer id;
    
    /*@Basic(optional = false)
    @NotNull    
    @Column(name = "departamento")
    private Cuenta departamento;
    */
    
    @OneToOne
    @JoinColumn(name = "departamento", updatable = false, nullable = false)
    private Departamento departamento;
    
    
    @Basic(optional = false)        
    @Column(name = "cuenta")
    private Integer cuenta;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Integer getCuenta() {
        return cuenta;
    }

    public void setCuenta(Integer cuenta) {
        this.cuenta = cuenta;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.departamento);
        hash = 37 * hash + Objects.hashCode(this.cuenta);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cuenta other = (Cuenta) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.departamento, other.departamento)) {
            return false;
        }
        if (!Objects.equals(this.cuenta, other.cuenta)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Cuenta{" + "id=" + id + ", departamento=" + departamento + ", cuenta=" + cuenta + '}';
    }
    
    
    
    
    
    
    
}
