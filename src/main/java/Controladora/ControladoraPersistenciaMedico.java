/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladora;

import Logica.Medico;
import controladoraDePersistencia.MedicoJpaController;
import controladoraDePersistencia.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author brizu
 */
public class ControladoraPersistenciaMedico {
    MedicoJpaController medicoJpaController= new MedicoJpaController();

   public void eliminarMedico(int id) {
        try {
            medicoJpaController.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistenciaMedico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   public void crearMedico(Medico medico) {
      medicoJpaController.create(medico);    }

   public void editarMedico(Medico medico) {
        try {
            medicoJpaController.edit(medico);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistenciaMedico.class.getName()).log(Level.SEVERE, null, ex);
        }
 }

   public ArrayList<Medico> buscarTodosMedicos() {
        List<Medico> lista = medicoJpaController.findMedicoEntities();
        ArrayList<Medico> listaMedicos= new ArrayList<>(lista);
        return listaMedicos;
       
    }

  public  Medico buscarMedico(int id) {
       return medicoJpaController.findMedico(id);   }
    
}
