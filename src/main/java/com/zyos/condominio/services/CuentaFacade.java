/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zyos.condominio.services;

import com.zyos.condominio.entities.Cuenta;
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
public class CuentaFacade extends AbstractFacade<Cuenta> implements CuentaFacadeLocal {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CuentaFacade() {
        super(Cuenta.class);
    }

    @Override
    public List<Cuenta> findByCuenta(Integer cuenta) {
        TypedQuery<Cuenta> query = em.createNamedQuery(
                "findByCuenta", Cuenta.class);       
        
        query.setParameter("cuenta",cuenta);                
        List<Cuenta> lista = query.getResultList();

        return lista;
    }
    
}
