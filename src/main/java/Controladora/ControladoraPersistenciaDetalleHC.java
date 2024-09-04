/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladora;

import Logica.DetalleHistoriaClinica;
import controladoraDePersistencia.DetalleHistoriaClinicaJpaController;
import controladoraDePersistencia.exceptions.NonexistentEntityException;
import controladoraDePersistencia.DetalleHistoriaClinicaJpaController;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author brizu
 */
public class ControladoraPersistenciaDetalleHC {
    DetalleHistoriaClinicaJpaController dhClinicaJpaController=new DetalleHistoriaClinicaJpaController();

   public void editarDetalleHC(DetalleHistoriaClinica dhc) {
        try {
            dhClinicaJpaController.edit(dhc);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistenciaDetalleHC.class.getName()).log(Level.SEVERE, null, ex);
        }
           }

  public  void eliminarDetalleHC(int id) {
        try {
            dhClinicaJpaController.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistenciaDetalleHC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void crearDetalleHC(DetalleHistoriaClinica dhc) {
        dhClinicaJpaController.create(dhc);
    }

   public ArrayList<DetalleHistoriaClinica> buscarTodosDetalleHC() {
        List<DetalleHistoriaClinica> listaClinicas= dhClinicaJpaController.findDetalleHistoriaClinicaEntities();
        ArrayList<DetalleHistoriaClinica> listaDetalleHistoriaClinicas =new ArrayList<>(listaClinicas);
        return listaDetalleHistoriaClinicas;
           }

   public DetalleHistoriaClinica buscarDetalleHC(int id) {
      return dhClinicaJpaController.findDetalleHistoriaClinica(id);    }
    
}
