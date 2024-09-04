/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladora;

import Logica.DetalleHistoriaClinica;
import Logica.Domicilio;
import controladoraDePersistencia.DomicilioJpaController;
import controladoraDePersistencia.exceptions.NonexistentEntityException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author brizu
 */
public class ControladoraPersistenciaDomicilio {
    
    DomicilioJpaController domicilioJpaController=new DomicilioJpaController();

   public void editarDomicilio(Domicilio domicilio) {
        try {
            domicilioJpaController.edit(domicilio);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistenciaDomicilio.class.getName()).log(Level.SEVERE, null, ex);
        }
           }

  public  void eliminarDomicilio(int id) {
        try {
            domicilioJpaController.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistenciaDomicilio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void crearDomicilio(Domicilio domicilio) {
        domicilioJpaController.create(domicilio);
    }

   public ArrayList<Domicilio> buscarTodosDomicilios() {
        List<Domicilio> listaDomicilios= domicilioJpaController.findDomicilioEntities();
        ArrayList<Domicilio> listadeDomicilios =new ArrayList<>(listaDomicilios);
        return listadeDomicilios;
           }

   public Domicilio buscarDomicilio(int id) {
      return domicilioJpaController.findDomicilio(id);    }
}
