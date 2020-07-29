/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zyos.condominio.services;

import com.zyos.condominio.entities.Transaccion;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author esneider
 */
@Stateless
public class TransaccionFacade extends AbstractFacade<Transaccion> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TransaccionFacade() {
        super(Transaccion.class);
    }
    
}
