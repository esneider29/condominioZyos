/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zyos.condominio.services;

import com.zyos.condominio.entities.Propietario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author esneider
 */
@Stateless
public class PropietarioFacade extends AbstractFacade<Propietario> implements PropietarioFacadeLocal {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PropietarioFacade() {
        super(Propietario.class);
    }

    @Override
    public List<Propietario> findByPropietario(String propietario) {
        TypedQuery<Propietario> query = em.createNamedQuery(
                "findByPropietario", Propietario.class);       
        
        query.setParameter("propietario", "%" +propietario+"%");                
        List<Propietario> lista = query.getResultList();

        return lista;
        
    }

    @Override
    public List<Propietario> findByDocumento(Integer documento) {
        TypedQuery<Propietario> query = em.createNamedQuery(
                "findByDocumento", Propietario.class);       
        
        query.setParameter("documento", documento);                
        List<Propietario> lista = query.getResultList();

        return lista;
    }
    
    
}
