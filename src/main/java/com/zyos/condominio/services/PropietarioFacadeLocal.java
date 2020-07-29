/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zyos.condominio.services;

import com.zyos.condominio.entities.Propietario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author esneider
 */
@Local
public interface PropietarioFacadeLocal {

    void create(Propietario propietario);

    void edit(Propietario propietario);

    void remove(Propietario propietario);

    Propietario find(Object id);

    List<Propietario> findAll();

    List<Propietario> findRange(int[] range);

    int count();
    
    List<Propietario> findByPropietario(String propietario);
    List<Propietario> findByDocumento(Integer documento);
    
}
