/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zyos.condominio.util;

import com.zyos.condominio.entities.Cuenta;
import com.zyos.condominio.entities.Departamento;
import com.zyos.condominio.entities.Propietario;
import com.zyos.condominio.entities.Transaccion;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.Application;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;


import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;


/**
 *
 * @author esneider
 */
public class LeerExcel{
    List<Transaccion> transacciones;
    List<Departamento> departamentos;
    List<Propietario> propietarios;
    List<Cuenta> cuentas;
    Map<Integer,Integer> propietarioDepartamento;
    
    public LeerExcel() {
        try {        
            leerArchivo();
        } catch (IOException ex) {
            Logger.getLogger(LeerExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    public Map<Integer, Integer> getPropietarioDepartamento() {
        return propietarioDepartamento;
    }

    public void setPropietarioDepartamento(Map<Integer, Integer> propietarioDepartamento) {
        this.propietarioDepartamento = propietarioDepartamento;
    }


    
    
    
    private void leerArchivo() throws IOException{
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream file = classloader.getResourceAsStream("condominio.xlsx");

        Workbook workbook = new XSSFWorkbook(file);
        
        //pestaña de transacciones
	
        transacciones =  cargarTransacciones(workbook.getSheetAt(0));        
        departamentos =  cargarDepartamentos(workbook.getSheetAt(1));
        propietarios = cargarPropietarios(workbook.getSheetAt(2));
        cuentas = cargarCuentas(workbook.getSheetAt(3));
        
        propietarioDepartamento = propietarioDepartamento(workbook.getSheetAt(4));
        
        
        
	// cerramos el libro excel
	workbook.close();        
    }
   
    private List<Cuenta> cargarCuentas(Sheet sheet){
        List <Cuenta> lista = new ArrayList<>();
        Cuenta  cuenta = null;
        
	Iterator<Row> filaIt = sheet.iterator();
	Row fila;

        //se omiten las 2 primeras filas de titulo
        for (int i = 0; i < 2;i++){
            if (filaIt.hasNext()){
                filaIt.next();
            }else{
                i = 99;
            }
        }
	
        //recorrido entre filas
	while (filaIt.hasNext()){
	    fila = filaIt.next();
	    
	    Iterator<Cell> cellIterator = fila.cellIterator();
	    Cell celda;           
            
            //se salta la columna del id, ya que el id se crea automaticamente en la base de datos
            if (cellIterator.hasNext()){
                cellIterator.next();
            }
            
            cuenta = new Cuenta();
            
            //recorridos entre celdas
	    while (cellIterator.hasNext()){
		celda = cellIterator.next();         
                
		switch(celda.getColumnIndex()) {
		case 1:
                    if (celda.getCellType() == CellType.NUMERIC){                    
                        cuenta.setDepartamento(new Departamento((int)celda.getNumericCellValue()));
                    }else{
		       Logger.getLogger(LeerExcel.class.getName()).log(Level.SEVERE, null, "error en celda " + celda.getColumnIndex());
                    }		    
		    break;
                    
		case 2:                    
                    if(celda.getCellType() == CellType.NUMERIC){                        
                         cuenta.setCuenta((int)celda.getNumericCellValue());
                    }
		    break;
                
                    
		}                
	    }            
            
            lista.add(cuenta);
            
	}        
        
        return lista;
        
    }
    
    private  List<Propietario> cargarPropietarios(Sheet sheet){
        List <Propietario> lista = new ArrayList<>();
        Propietario  propietario = null;
        
	Iterator<Row> filaIt = sheet.iterator();
	Row fila;

        //se omiten las 2 primeras filas de titulo
        for (int i = 0; i < 2;i++){
            if (filaIt.hasNext()){
                filaIt.next();
            }else{
                i = 99;
            }
        }
	
        //recorrido entre filas
	while (filaIt.hasNext()){
	    fila = filaIt.next();
	    
	    Iterator<Cell> cellIterator = fila.cellIterator();
	    Cell celda;           
            
            //se salta la columna del id, ya que el id se crea automaticamente en la base de datos
            if (cellIterator.hasNext()){
                cellIterator.next();
            }
            
            propietario = new Propietario();
            
            //recorridos entre celdas
	    while (cellIterator.hasNext()){
		celda = cellIterator.next();         
                
		switch(celda.getColumnIndex()) {
		case 1:
                    if (celda.getCellType() == CellType.NUMERIC){                    
                        propietario.setDocumento((int)celda.getNumericCellValue());
                    }else{
		       Logger.getLogger(LeerExcel.class.getName()).log(Level.SEVERE, null, "error en celda " + celda.getColumnIndex());
                    }		    
		    break;
                    
		case 2:                    
                    if(celda.getCellType() == CellType.STRING){                        
                         propietario.setPropietario(celda.getStringCellValue());
                    }else{
                            Logger.getLogger(LeerExcel.class.getName()).log(Level.SEVERE, null, "error en celda " + celda.getColumnIndex());
                    }                    
		    break;                    
		}                
	    }            
            
            
            lista.add(propietario);
	}        
        
        
        
        return lista;
        
    }
    
    private Map<Integer,Integer> propietarioDepartamento(Sheet sheet){
        Map<Integer,Integer> lista = new HashMap<>();
        //Propietario  propietario = null;
        int propietario=0, departamento=0;
        
	Iterator<Row> filaIt = sheet.iterator();
	Row fila;

        //se omiten las 2 primeras filas de titulo
        for (int i = 0; i < 2;i++){
            if (filaIt.hasNext()){
                filaIt.next();
            }else{
                i = 99;
            }
        }
	
        //recorrido entre filas
	while (filaIt.hasNext()){
	    fila = filaIt.next();
	    
	    Iterator<Cell> cellIterator = fila.cellIterator();
	    Cell celda;           
            
            //se salta la columna del id, ya que el id se crea automaticamente en la base de datos
            if (cellIterator.hasNext()){
                cellIterator.next();
            }
            
            //propietario = new Propietario();
            
            //recorridos entre celdas
	    while (cellIterator.hasNext()){
		celda = cellIterator.next();         
                
		switch(celda.getColumnIndex()) {
		case 1:
                    if (celda.getCellType() == CellType.NUMERIC){                    
                        propietario = (int)celda.getNumericCellValue();                        
                    }else{
		       Logger.getLogger(LeerExcel.class.getName()).log(Level.SEVERE, null, "error en celda " + celda.getColumnIndex());
                    }		    
		    break;                     
		case 2:                    
                    if(celda.getCellType() == CellType.NUMERIC){                        
                         departamento  = (int)celda.getNumericCellValue();
                    }else{
                            Logger.getLogger(LeerExcel.class.getName()).log(Level.SEVERE, null, "error en celda " + celda.getColumnIndex());
                    }                    
		    break;                    
		}                
	    }            
            
            lista.put(propietario, departamento);
	}        
        
        
        
        return lista;   
    }
    
    private List<Departamento> cargarDepartamentos(Sheet sheet){
        List <Departamento> lista = new ArrayList<>();
        Departamento  departamento = null;
        
	Iterator<Row> filaIt = sheet.iterator();
	Row fila;

        //se omiten las 2 primeras filas de titulo
        for (int i = 0; i < 2;i++){
            if (filaIt.hasNext()){
                filaIt.next();
            }else{
                i = 99;
            }
        }
	
        //recorrido entre filas
	while (filaIt.hasNext()){
	    fila = filaIt.next();
	    
	    Iterator<Cell> cellIterator = fila.cellIterator();
	    Cell celda;           
            
            //se salta la columna del id, ya que el id se crea automaticamente en la base de datos
            if (cellIterator.hasNext()){
                cellIterator.next();
            }
            
            departamento = new Departamento();
            
            //recorridos entre celdas
	    while (cellIterator.hasNext()){
		celda = cellIterator.next();         
                
		switch(celda.getColumnIndex()) {
		case 1:
                    if (celda.getCellType() == CellType.NUMERIC){                    
                        departamento.setReferencia((int)celda.getNumericCellValue());
                    }else{
		       Logger.getLogger(LeerExcel.class.getName()).log(Level.SEVERE, null, "error en celda " + celda.getColumnIndex());
                    }		    
		    break;
                    
		case 2:                    
                    if(celda.getCellType() == CellType.NUMERIC){                        
                         departamento.setManzana((int)celda.getNumericCellValue());
                    }else{
                            Logger.getLogger(LeerExcel.class.getName()).log(Level.SEVERE, null, "error en celda " + celda.getColumnIndex());
                    }                    
		    break;
                case 3:                    
                    if(celda.getCellType() == CellType.NUMERIC){                        
                         departamento.setDepartamento((int)celda.getNumericCellValue());
                    }else{
                            Logger.getLogger(LeerExcel.class.getName()).log(Level.SEVERE, null, "error en celda " + celda.getColumnIndex());
                    }                    
		    break;
                    
		}                
	    }            
            
            /*Cuenta cta = new Cuenta();
            cta.setDepartamento(departamento);
            departamento.setCuenta(cta);
*/
            
            lista.add(departamento);
            
	}        
        
        return lista;
        
    }
    
    private List<Transaccion> cargarTransacciones(Sheet sheet){       
        List <Transaccion> lista = new ArrayList<>();
        Transaccion  transaccion = null;
        
	Iterator<Row> filaIt = sheet.iterator();
	Row fila;

        //se omiten las 2 primeras filas de titulo
        for (int i = 0; i < 2;i++){
            if (filaIt.hasNext()){
                filaIt.next();
            }else{
                i = 99;
            }
        }
	
        //recorrido entre filas
	while (filaIt.hasNext()){
	    fila = filaIt.next();
	    
	    Iterator<Cell> cellIterator = fila.cellIterator();
	    Cell celda;           
            
            //se salta la columna del id, ya que el id se crea automaticamente en la base de datos
            if (cellIterator.hasNext()){
                cellIterator.next();
            }
            
            transaccion = new Transaccion();
            
            
            //recorridos entre celdas
	    while (cellIterator.hasNext()){
		celda = cellIterator.next();         
                
		switch(celda.getColumnIndex()) {
		case 1:
                    if (celda.getCellType() == CellType.STRING){                    
                        transaccion.setTransaccion(celda.getStringCellValue());		                            
                    }else{
		       Logger.getLogger(LeerExcel.class.getName()).log(Level.SEVERE, null, "error en celda " + celda.getColumnIndex());
                    }		    
		    break;                    
		case 2:                    
                    switch(celda.getCellType()){
                        case NUMERIC: 
                         transaccion.setValor(celda.getNumericCellValue());                                                                  
                         break;
                        case STRING:
                            transaccion.setValor(Double.parseDouble(celda.getStringCellValue()));
                            break;
                        default:
                            Logger.getLogger(LeerExcel.class.getName()).log(Level.SEVERE, null, "error en celda " + celda.getColumnIndex());
                    }                    
		    break;		}                
	    }            
            
            lista.add(transaccion);
            
	}        
        
        return lista;
    }

}
