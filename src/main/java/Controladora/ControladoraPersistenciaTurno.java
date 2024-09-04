/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladora;

import Logica.DetalleHistoriaClinica;
import Logica.Turno;
import controladoraDePersistencia.TurnoJpaController;
import controladoraDePersistencia.exceptions.NonexistentEntityException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControladoraPersistenciaTurno {
    TurnoJpaController turnojpa= new TurnoJpaController();
        

    public void crearTurno(Turno turno) {
         turnojpa.create(turno);    }

    public void editarTurno(Turno turno) {
        try {
            turnojpa.edit(turno);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistenciaTurno.class.getName()).log(Level.SEVERE, null, ex);
        }
 }

    public void eliminarTurno(int id) {
        try {
            turnojpa.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistenciaTurno.class.getName()).log(Level.SEVERE, null, ex);
        }
 }

    public ArrayList<Turno> buscarTodosTurnos() {
        List<Turno>listita = turnojpa.findTurnoEntities();
        ArrayList<Turno> listaTurnos= new ArrayList<Turno>(listita);
     return  listaTurnos;}

    public Turno buscarTurno(int id) {
        return turnojpa.findTurno(id);  }


   
}
