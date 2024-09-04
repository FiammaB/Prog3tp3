/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladora;


import Logica.Empleado;
import controladoraDePersistencia.EmpleadoJpaController;
import controladoraDePersistencia.exceptions.NonexistentEntityException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author brizu
 */
public class ControladoraPersistenciaEmpleado {
    EmpleadoJpaController empleadojpa= new EmpleadoJpaController();
     public void crearEmpleado(Empleado empleado) {
         empleadojpa.create(empleado);    }

    public void editarEmpleado(Empleado empleado) {
        try {
            empleadojpa.edit(empleado);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistenciaEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }
 }

    public void eliminarEmpleado(int id) {
        try {
            empleadojpa.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistenciaEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }
 }

    public ArrayList<Empleado> buscarTodosEmpleados() {
        List<Empleado>listita = empleadojpa.findEmpleadoEntities();
        ArrayList<Empleado> listaEmpleados= new ArrayList<Empleado>(listita);
     return  listaEmpleados;}

    public Empleado buscarEmpleado(int id) {
        return empleadojpa.findEmpleado(id);  }


    
}
