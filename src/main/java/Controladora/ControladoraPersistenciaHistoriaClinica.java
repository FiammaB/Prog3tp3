/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladora;

import Logica.HistoriaClinica;
import controladoraDePersistencia.HistoriaClinicaJpaController;
import controladoraDePersistencia.exceptions.NonexistentEntityException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author brizu
 */
public class ControladoraPersistenciaHistoriaClinica{
    HistoriaClinicaJpaController  historiaClinicajpa= new HistoriaClinicaJpaController();
        

    public void crearHistoriaClinica(HistoriaClinica historiaClinica) {
         historiaClinicajpa.create(historiaClinica);    }

    public void editarHistoriaClinica(HistoriaClinica historiaClinica) {
        try {
            historiaClinicajpa.edit(historiaClinica);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistenciaHistoriaClinica.class.getName()).log(Level.SEVERE, null, ex);
        }
 }

    public void eliminarHistoriaClinica(int id) {
        try {
            historiaClinicajpa.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistenciaHistoriaClinica.class.getName()).log(Level.SEVERE, null, ex);
        }
 }

    public ArrayList<HistoriaClinica> buscarTodosHistoriaClinicas() {
        List<HistoriaClinica>listita = historiaClinicajpa.findHistoriaClinicaEntities();
        ArrayList<HistoriaClinica> listaHistoriaClinicas= new ArrayList<HistoriaClinica>(listita);
     return  listaHistoriaClinicas;}

    public HistoriaClinica buscarHistoriaClinica(int id) {
        return historiaClinicajpa.findHistoriaClinica(id);  }

}
