/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladora;

import Logica.Especialidad;
import controladoraDePersistencia.EspecialidadJpaController;
import controladoraDePersistencia.exceptions.NonexistentEntityException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author brizu
 */
public class ControladoraPersistenciaEspecialidad {
    EspecialidadJpaController especialidadjpa= new EspecialidadJpaController();
      public void crearEspecialidad(Especialidad especialidad) {
         especialidadjpa.create(especialidad);    }

    public void editarEspecialidad(Especialidad especialidad) {
        try {
            especialidadjpa.edit(especialidad);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistenciaEspecialidad.class.getName()).log(Level.SEVERE, null, ex);
        }
 }

    public void eliminarEspecialidad(int id) {
        try {
            especialidadjpa.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistenciaEspecialidad.class.getName()).log(Level.SEVERE, null, ex);
        }
 }

    public ArrayList<Especialidad> buscarTodosEspecialidades() {
        List<Especialidad>listita = especialidadjpa.findEspecialidadEntities();
        ArrayList<Especialidad> listaEspecialidades= new ArrayList<Especialidad>(listita);
     return  listaEspecialidades;}

    public Especialidad buscarEspecialidad(int id) {
        return especialidadjpa.findEspecialidad(id);  }


}
