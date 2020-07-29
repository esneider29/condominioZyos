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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author esneider
 */
@Entity
@Table(name = "propietario")
@NamedQueries({
    @NamedQuery(name = "findByPropietario", query = "SELECT p FROM Propietario p WHERE lower(p.propietario) LIKE :propietario"),
    @NamedQuery(name = "findByDocumento", query = "SELECT p FROM Propietario p WHERE p.documento = :documento")})

public class Propietario implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @Basic(optional = false)    
    private Integer id;
    
    @Basic(optional = false)
    @NotNull    
    @Column(name="documento")
    private Integer documento;
    
    @Basic(optional = false)
    @NotNull    
    @Column(name="propietario")
    private String propietario;
    
    
    @ManyToOne
    @JoinColumn(
            name = "departamento",             
            updatable = false)
    private Departamento departamento;
    
    
    
    /*@ManyToMany(mappedBy="propietarios")    
    private List<Departamento> departamentos;
    */
    public Propietario() {
        //this.departamento = new Departamento();
    }

    /**
     * Get the value of documento
     *
     * @return the value of documento
     */
    public Integer getDocumento() {
        return documento;
    }

    /**
     * Set the value of documento
     *
     * @param documento new value of documento
     */
    public void setDocumento(Integer documento) {
        this.documento = documento;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }


    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    
    

    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.id);
        hash = 43 * hash + Objects.hashCode(this.documento);
        hash = 43 * hash + Objects.hashCode(this.propietario);
        hash = 43 * hash + Objects.hashCode(this.departamento);
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
        final Propietario other = (Propietario) obj;
        if (!Objects.equals(this.propietario, other.propietario)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.documento, other.documento)) {
            return false;
        }
        if (!Objects.equals(this.departamento, other.departamento)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "Propietario{" + "id=" + id + ", documento=" + documento + ", propietario=" + propietario + ", departamento=" + departamento + '}';
    }
    
}
