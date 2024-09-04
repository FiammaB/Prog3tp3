/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Entity
public class Turno implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTurno;
    private Date fechaDate;
    private  int hora;
    private int minutos;
    @ManyToOne
    @JoinColumn(name = "idmedico")
    private  Medico medico;
    @ManyToOne
    @JoinColumn(name = "idPaciente")
    private Paciente paciente;

    public Turno() {
    }

    public Turno(Date fechaDate, int hora, int minutos, Medico medico, Paciente paciente) {
        this.idTurno = idTurno;
        this.fechaDate = fechaDate;
        this.hora = hora;
        this.minutos = minutos;
        this.medico = medico;
        this.paciente = paciente;
    }

    public Turno(Date fechaDate, int hora, int minutos) {
        
        this.fechaDate = fechaDate;
        this.hora = hora;
        this.minutos = minutos;
    }

    public int getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(int idTurno) {
        this.idTurno = idTurno;
    }

    public Date getFechaDate() {
        return fechaDate;
    }

    public void setFechaDate(Date fechaDate) {
        this.fechaDate = fechaDate;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
    
}
