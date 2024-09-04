package com.mycompany.prog3tp3;

import Controladora.ControladoraPersistenciaDetalleHC;
import Controladora.ControladoraPersistenciaDomicilio;
import Controladora.ControladoraPersistenciaEmpleado;
import Controladora.ControladoraPersistenciaEspecialidad;
import Controladora.ControladoraPersistenciaHistoriaClinica;
import Controladora.ControladoraPersistenciaMedico;
import Controladora.ControladoraPersistenciaPaciente;
import Controladora.ControladoraPersistenciaTurno;
import Logica.DetalleHistoriaClinica;
import Logica.Domicilio;
import Logica.Empleado;
import Logica.Especialidad;
import Logica.HistoriaClinica;
import Logica.Medico;
import Logica.Paciente;
import Logica.Turno;
import java.util.ArrayList;
import java.util.Date;

public class Prog3tp3 {
    
    public static void main(String[] args) {
        ControladoraPersistenciaDetalleHC cntrlDHC = new ControladoraPersistenciaDetalleHC();
        ControladoraPersistenciaMedico cntrlMedico = new ControladoraPersistenciaMedico();
        ControladoraPersistenciaEmpleado cntrlEmpleado = new ControladoraPersistenciaEmpleado();
        ControladoraPersistenciaEspecialidad cntrlEspecialidad = new ControladoraPersistenciaEspecialidad();
        ControladoraPersistenciaDomicilio cntrlDomicilio = new ControladoraPersistenciaDomicilio();
        ControladoraPersistenciaPaciente cntrlPaciente = new ControladoraPersistenciaPaciente();
        ControladoraPersistenciaHistoriaClinica cntrlHClinica = new ControladoraPersistenciaHistoriaClinica();
        ControladoraPersistenciaTurno cntrlTurno = new ControladoraPersistenciaTurno();
        // Creación de Domicilio
        Domicilio domicilio1 = new Domicilio("Maipú", "Sarmiento", 124);
        cntrlDomicilio.crearDomicilio(domicilio1);

        // Creación de Paciente
        Domicilio domicilioPaciente = new Domicilio( "Gutierrez", "SUCUSI", 600);
        cntrlDomicilio.crearDomicilio(domicilioPaciente);
        Paciente paciente1 = new Paciente(034, new ArrayList<>(), null, "Roberto", "Buldin", 3454340, domicilioPaciente);
        cntrlPaciente.crearPaciente(paciente1);

        // Creación de Medico
        Medico medico1 = new Medico(032, 2343540, "Fiamma", "Brizuela", 443544191, domicilio1);
        Medico medico2 = new Medico(032, 2343540, "Bertoldo", "zela", 443544191, domicilio1);
        cntrlMedico.crearMedico(medico1);
        cntrlMedico.crearMedico(medico2);

        // Creación de Especialidad
        ArrayList<Medico> medicos = new ArrayList<>();
        medicos.add(medico1);
        medicos.add(medico2);
        Especialidad especialidad1 = new Especialidad("Pediatra", medicos);
        Especialidad especialidad2 = new Especialidad("traumatologo", medicos);
        cntrlEspecialidad.crearEspecialidad(especialidad1);
        cntrlEspecialidad.crearEspecialidad(especialidad2);

        // Creación de Turno
        Turno turno1 = new Turno(new Date(), 23, 20);
        Turno turno2 = new Turno(new Date(), 23, 20);
        turno1.setMedico(medico1);
        turno2.setMedico(medico2);
        turno1.setPaciente(paciente1);
        turno2.setPaciente(paciente1);
        ArrayList<Turno> turnos = new ArrayList<>();
        turnos.add(turno1);
        turnos.add(turno2);
        medico1.setTurnos(turnos);
        medico2.setTurnos(turnos);
        cntrlTurno.crearTurno(turno1);
        cntrlTurno.crearTurno(turno2);

        // Creación de HistoriaClinica
        HistoriaClinica historiaClinica1 = new HistoriaClinica(36, new Date(), paciente1, new ArrayList<>());
        cntrlHClinica.crearHistoriaClinica(historiaClinica1);

        // Creación de DetalleHistoriaClinica
        DetalleHistoriaClinica detalleHistoriaClinica1 = new DetalleHistoriaClinica(new Date(), "dolor de panza", "gastritis", "se va a mejorar", historiaClinica1);
        DetalleHistoriaClinica detalleHistoriaClinica2 = new DetalleHistoriaClinica(new Date(), "dolor de cabeza", "Estres", "no se va a mejorar", historiaClinica1);
        ArrayList<DetalleHistoriaClinica> detalleshc = new ArrayList<>();
        detalleshc.add(detalleHistoriaClinica1);
        detalleshc.add(detalleHistoriaClinica2);
        historiaClinica1.setDetalleHistoriasClinicas(detalleshc);
        cntrlDHC.crearDetalleHC(detalleHistoriaClinica1);
        cntrlDHC.crearDetalleHC(detalleHistoriaClinica2);

        // Creación de Empleado
        Empleado empleado1 = new Empleado(432, 04543, "Ruth", "Borir", 4365565);
        cntrlEmpleado.crearEmpleado(empleado1);
        
    }
}
