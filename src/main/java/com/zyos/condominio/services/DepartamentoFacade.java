/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zyos.condominio.services;


import com.zyos.condominio.entities.Departamento;
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
public class DepartamentoFacade extends AbstractFacade<Departamento> implements DepartamentoFacadeLocal {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DepartamentoFacade() {
        super(Departamento.class);
    }

    @Override
    public List<Departamento> findByReferencia(Integer referencia) {
            TypedQuery<Departamento> query = em.createNamedQuery(
                "findByReferencia", Departamento.class);       
        
        query.setParameter("referencia",referencia);                
        List<Departamento> lista = query.getResultList();

        return lista;
    }
    
}
