/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladora;

import Logica.Paciente;
import controladoraDePersistencia.PacienteJpaController;
import controladoraDePersistencia.exceptions.NonexistentEntityException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author brizu
 */
public class ControladoraPersistenciaPaciente {
        PacienteJpaController pacientejpa= new PacienteJpaController();
     public void crearPaciente(Paciente paciente) {
         pacientejpa.create(paciente);    }

    public void editarPaciente(Paciente paciente) {
        try {
            pacientejpa.edit(paciente);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistenciaPaciente.class.getName()).log(Level.SEVERE, null, ex);
        }
 }

    public void eliminarPaciente(int id) {
        try {
            pacientejpa.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistenciaPaciente.class.getName()).log(Level.SEVERE, null, ex);
        }
 }

    public ArrayList<Paciente> buscarTodosPacientes() {
        List<Paciente>listita = pacientejpa.findPacienteEntities();
        ArrayList<Paciente> listaPacientes= new ArrayList<Paciente>(listita);
     return  listaPacientes;}

    public Paciente buscarPaciente(int id) {
        return pacientejpa.findPaciente(id);  }

}
