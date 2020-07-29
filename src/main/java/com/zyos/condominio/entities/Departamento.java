/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zyos.condominio.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author esneider
 */
@Entity
@Table(name = "departamento")
@XmlRootElement
@NamedQueries({    
    @NamedQuery(name = "findByReferencia", query = "SELECT d FROM Departamento d WHERE d.referencia = :referencia")})
public class Departamento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "referencia")
    private int referencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "manzana")
    private int manzana;
    @Basic(optional = false)
    @NotNull
    @Column(name = "departamentos")
    private int departamento;
    
    //@ManyToMany(cascade = CascadeType.ALL)
    @OneToMany(mappedBy="departamento")
    private List<Propietario> propietarios;

    
    @OneToOne(mappedBy = "departamento"
            ,cascade = CascadeType.ALL
    )
    private Cuenta cuenta;
            
    public Departamento() {
    }

    public Departamento(Integer id) {
        this.id = id;
    }

    public Departamento(Integer id, int referencia, int manzana, int departamento) {
        this.id = id;
        this.referencia = referencia;
        this.manzana = manzana;
        this.departamento = departamento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getReferencia() {
        return referencia;
    }

    public void setReferencia(int referencia) {
        this.referencia = referencia;
    }

    public int getManzana() {
        return manzana;
    }

    public void setManzana(int manzana) {
        this.manzana = manzana;
    }

    public int getDepartamento() {
        return departamento;
    }

    public void setDepartamento(int departamento) {
        this.departamento = departamento;
    }

    public List<Propietario> getPropietarios() {
        return propietarios;
    }

    public void setPropietarios(List<Propietario> propietarios) {
        this.propietarios = propietarios;
    }

    
    

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + this.referencia;
        hash = 97 * hash + this.manzana;
        hash = 97 * hash + this.departamento;
        hash = 97 * hash + Objects.hashCode(this.propietarios);
        hash = 97 * hash + Objects.hashCode(this.cuenta);
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
        final Departamento other = (Departamento) obj;
        if (this.referencia != other.referencia) {
            return false;
        }
        if (this.manzana != other.manzana) {
            return false;
        }
        if (this.departamento != other.departamento) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.propietarios, other.propietarios)) {
            return false;
        }
        if (!Objects.equals(this.cuenta, other.cuenta)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Departamento{" + "id=" + id + ", referencia=" + referencia + ", manzana=" + manzana + ", departamento=" + departamento + ", propietarios=" + propietarios + ", cuenta=" + cuenta + '}';
    }

    
    
    
    
    
}
