/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zyos.condominio.mb;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.zyos.condominio.entities.Cuenta;
import com.zyos.condominio.entities.Departamento;
import com.zyos.condominio.entities.Propietario;
import com.zyos.condominio.entities.Transaccion;
import com.zyos.condominio.modelo.PropietarioDepto;
import com.zyos.condominio.modelo.Resumen;
import com.zyos.condominio.services.CuentaFacadeLocal;
import com.zyos.condominio.services.DepartamentoFacadeLocal;
import com.zyos.condominio.services.PropietarioFacadeLocal;
import com.zyos.condominio.services.TransaccionFacade;
//import com.zyos.condominio.services.TransaccionFacadeLocal;
import com.zyos.condominio.util.LeerExcel;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;



import javax.faces.application.FacesMessage;

import javax.faces.context.FacesContext;



import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.compress.utils.Sets;
//import javax.enterprise.context.RequestScoped;


/**
 *
 * @author esneider
 */

@ManagedBean
//@SessionScoped
@ViewScoped
//@Named("mainMb")
//@RequestScoped
public class MainMb implements Serializable{    

    @EJB
    private CuentaFacadeLocal cuentaFacade;

    @EJB
    private PropietarioFacadeLocal propietarioFacade;

    @EJB
    private DepartamentoFacadeLocal departamentoFacade;

    @EJB
    private TransaccionFacade transaccionFacade;    
    
    List<Transaccion> transacciones;
    List<Departamento> departamentos;
    List<Propietario> propietarios;
    List<Cuenta> cuentas;    
    List<PropietarioDepto> propDeptos;
    
    Set<Resumen> resumenList;
    
    
  
    boolean cargarDis = false;    
    
    /**
     * Creates a new instance of Main
     */
    
    @PostConstruct
    public void init(){
        cargarListas();
        if (!transacciones.isEmpty()){
            cargarDis = true;
        } 
    }
    
    
    public void cargarExcel() {
        
        List<Transaccion> transaccionList = null;
        List<Departamento> departamentoList = null;
        List<Propietario> propietarioList = null;
        List<Cuenta> cuentaList = null;        
        Map<Integer, Integer> propietarioDepartamento = null;
        
        
        LeerExcel leer = new LeerExcel();            
                
        
        
        transaccionList = leer.getTransacciones();              
        transaccionList.stream().forEach(transaccionFacade::create);
        
        
        propietarioList = leer.getPropietarios();        
        propietarioList.stream().forEach(propietarioFacade::create);
        
        
        departamentoList = leer.getDepartamentos();        
        departamentoList.stream().forEach(c -> {            
            departamentoFacade.create(c);
                    });
        
        cuentaList = leer.getCuentas();
        
        cuentaList.stream().forEach((c -> {
            if ((c.getCuenta() != null) && (c.getCuenta() > 0)){
                Departamento deptoTemp;
                deptoTemp = departamentoFacade.find(c.getDepartamento().getId());
                c.setDepartamento(deptoTemp);                
            }
            cuentaFacade.create(c);                
                
        }));
        
        propietarioDepartamento = leer.getPropietarioDepartamento();
        
        
        
        propietarioDepartamento.forEach((k,v)->{
            Propietario p = propietarioFacade.find(k);
            Departamento d = departamentoFacade.find(v);
            p.setDepartamento(d);        
            propietarioFacade.edit(p);                    
        });        
        
        
        
         addMessage("informacion cargada");         
         
         cargarListas();         
         cargarDis = true;
    }
    
    private void cargarListas(){
        transacciones = transaccionFacade.findAll();
         departamentos = departamentoFacade.findAll();
         propietarios = propietarioFacade.findAll();
         cuentas = cuentaFacade.findAll();
         
         propDeptos = new ArrayList<>();
  
         propietarios.stream().forEach((p)->{
                 propDeptos.add(new PropietarioDepto(p.getId(), p.getDepartamento().getId()));                              
         });
         /*departamentos.stream().forEach((d)-> {
             int depto = d.getId();             
             d.getPropietarios().stream().forEach((p)->{
                 propDeptos.add(new PropietarioDepto(p.getId(), depto));                 
             });
             
         });
*/
         
         /////start resumen
                  
         //resumenList = new HashSet<>();
         resumenList = new LinkedHashSet<>();
         transacciones.stream().forEach((t)->{
             
             resumenList.add(new Resumen(t.getTransaccion(), t.getValor(), 0, ""));
             
             Set<String> metaDatos = new HashSet<>();
             
             String[] meta1 = t.getTransaccion().split(" ");             
             Arrays.stream(meta1).filter(f->f.length()>3). forEach(metaDatos::add);
             
             
             String[] meta2 = t.getTransaccion().split("-");             
             Arrays.stream(meta2).filter(f->f.length()>3). forEach(metaDatos::add);

             String[] meta3 = t.getTransaccion().split(".");
             Arrays.stream(meta3).filter(f->f.length()>3). forEach(metaDatos::add);
             
             
            //search into owner by name of owner
             Set<Propietario> propietariosNom = new HashSet<>();
             metaDatos.stream().forEach((m)->{
                 
                propietarioFacade.findByPropietario(m).forEach(
                        (p)->{                            
                            propietariosNom.add(p);
                                });
                });
             
             propietariosNom.stream().forEach((p)->{
                 int depto = p.getDepartamento().getReferencia();
                 resumenList.iterator().forEachRemaining((e)->{
                     if (e.getTransaccion().equals(t.getTransaccion())){
                         e.setDepartamento(depto);
                         e.setAsociacion("nombre_propietario");
                     }
                 });
                 //resumenList.add(new Resumen(t.getTransaccion(), t.getValor(), depto, "nombre_Prpietario"));
             });
             
             //search into owner by document
             Set<Propietario> propietariosDoc = new HashSet<>();
             metaDatos.stream().filter((m)-> m.matches("[0-9]*")).forEach((m)->{
                 
                propietarioFacade.findByDocumento(Integer.valueOf(m)).forEach(
                        (p)->{                            
                            propietariosDoc.add(p);
                                });
                });
             
             propietariosDoc.stream().forEach((p)->{
                 int depto = p.getDepartamento().getReferencia();
                 resumenList.iterator().forEachRemaining((e)->{
                     if (e.getTransaccion().equals(t.getTransaccion())){
                         e.setDepartamento(depto);
                         e.setAsociacion("documento_propietario");
                     }
                 });
                 //resumenList.add(new Resumen(t.getTransaccion(), t.getValor(), depto, "documento_propietario"));
             });
             
             //search into account
             Set<Cuenta> cuentas = new HashSet<>();
             metaDatos.stream().filter((m)-> m.matches("[0-9]*")).forEach((m)->{                 
                cuentaFacade.findByCuenta(Integer.valueOf(m)).forEach(                        
                        (c)->{                            
                            cuentas.add(c);
                                });
             });
                          
             cuentas.stream().forEach((c)->{
                 int depto = c.getDepartamento().getReferencia();
                 resumenList.iterator().forEachRemaining((e)->{
                     if (e.getTransaccion().equals(t.getTransaccion())){
                         e.setDepartamento(depto);
                         e.setAsociacion("numero_cuenta");
                     }
                 });
                 //resumenList.add(new Resumen(t.getTransaccion(), t.getValor(), depto, "numero_cuenta"));
             });
             
             //search into Departamento
             Set<Departamento> departamentos = new HashSet<>();
             metaDatos.stream().filter((m)-> m.matches("[0-9]*")).forEach((m)->{                 
                departamentoFacade.findByReferencia(Integer.valueOf(m)).forEach(                        
                        (d)->{                            
                            departamentos.add(d);
                                });
             });
                          
             departamentos.stream().forEach((d)->{                 
                 resumenList.iterator().forEachRemaining((e)->{
                     if (e.getTransaccion().equals(t.getTransaccion())){
                         e.setDepartamento(d.getReferencia());
                         e.setAsociacion("referencia");
                     }
                 });
                 //resumenList.add(new Resumen(t.getTransaccion(), t.getValor(), d.getReferencia(), "referencia"));
             });
             
         });
         
    }

    public List<Transaccion> getTransacciones() {        
        return transacciones;
    }

    public void setTransacciones(List<Transaccion> transacciones) {
        this.transacciones = transacciones;
    }

    public List<Departamento> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(List<Departamento> departamentos) {
        this.departamentos = departamentos;
    }

    public List<Propietario> getPropietarios() {
        return propietarios;
    }

    public void setPropietarios(List<Propietario> propietarios) {
        this.propietarios = propietarios;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

    public List<PropietarioDepto> getPropDeptos() {
        return propDeptos;
    }

    public void setPropDeptos(List<PropietarioDepto> propDeptos) {
        this.propDeptos = propDeptos;
    }

//    public List<Resumen> getResumenList() {
//        return resumenList;
//    }
//
//    public void setResumenList(List<Resumen> resumenList) {
//        this.resumenList = resumenList;
//    }

    public Set<Resumen> getResumenList() {
        return resumenList;
    }

    public void setResumenList(Set<Resumen> resumenList) {
        this.resumenList = resumenList;
    }
    
    

    
    
    
    
    
    
    
    public boolean isCargarDis() {
        return cargarDis;
    }

    public void setCargarDis(boolean cargarDis) {
        this.cargarDis = cargarDis;
    }
    
    
    
    

    
    
    
    
    
    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
}
