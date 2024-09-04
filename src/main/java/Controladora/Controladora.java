
package Controladora;

import Logica.DetalleHistoriaClinica;
import Logica.Domicilio;
import Logica.Empleado;
import Logica.Especialidad;
import Logica.HistoriaClinica;
import Logica.Medico;
import Logica.Paciente;
import Logica.Turno;
import java.util.ArrayList;


public class Controladora {

    //TURNO
    ControladoraPersistenciaTurno cntrlTurno = new ControladoraPersistenciaTurno();
        public  void crearTurno(Turno turno){
   cntrlTurno.crearTurno(turno);}
    
    public  void editarTurno(Turno turno){
    cntrlTurno.editarTurno(turno);}
    
    public  void eliminarTurno(int id){
    cntrlTurno.eliminarTurno(id);}
    
    public  ArrayList<Turno>buscarTodosTurnos(){
    return cntrlTurno.buscarTodosTurnos();}
     
    public  Turno buscarTurno(int id){
    return cntrlTurno.buscarTurno(id);}
    
    //DETALLE HISTORIA CLINICA
    ControladoraPersistenciaDetalleHC cntrlDetalleHC = new ControladoraPersistenciaDetalleHC();
       public  void crearDetalleHC(DetalleHistoriaClinica dhc){
        cntrlDetalleHC.crearDetalleHC(dhc);}
    
    public  void editarDetalleHC(DetalleHistoriaClinica dhc){
    cntrlDetalleHC.editarDetalleHC(dhc);}
    
    public  void eliminarDetalleHC(int id){
    cntrlDetalleHC.eliminarDetalleHC(id);}
    
    public  ArrayList<DetalleHistoriaClinica>buscarTodosDetalleHC(){
    return cntrlDetalleHC.buscarTodosDetalleHC();}
     
    public  DetalleHistoriaClinica buscarDetalleHC(int id){
    return cntrlDetalleHC.buscarDetalleHC(id);}
    
    //MEDICO
    ControladoraPersistenciaMedico cntrlMedico=new ControladoraPersistenciaMedico();
          public  void crearMedico(Medico medico){
   cntrlMedico.crearMedico(medico);}
    
    public  void editarMedico(Medico medico){
    cntrlMedico.editarMedico(medico);}
    
    public  void eliminarMedico(int id){
    cntrlMedico.eliminarMedico(id);}
    
    public  ArrayList<Medico>buscarTodosMedicos(){
    return cntrlMedico.buscarTodosMedicos();}
     
    public  Medico buscarMedico(int id){
    return cntrlMedico.buscarMedico(id);}
    //EMPLEADO
       ControladoraPersistenciaEmpleado cntrlEmpleado=new ControladoraPersistenciaEmpleado();
          public  void crearEmpleado(Empleado empleado){
   cntrlEmpleado.crearEmpleado(empleado);}
    
    public  void editarEmpleado(Empleado empleado){
    cntrlEmpleado.editarEmpleado(empleado);}
    
    public  void eliminarEmpleado(int id){
    cntrlEmpleado.eliminarEmpleado(id);}
    
    public  ArrayList<Empleado>buscarTodosEmpleados(){
    return cntrlEmpleado.buscarTodosEmpleados();}
     
    public  Empleado buscarEmpleado(int id){
    return cntrlEmpleado.buscarEmpleado(id);}
    
    //ESPECIALIDAD
     ControladoraPersistenciaEspecialidad cntrlEspecialidad = new ControladoraPersistenciaEspecialidad();
        public  void crearEspecialidad(Especialidad especialidad){
   cntrlEspecialidad.crearEspecialidad(especialidad);}
    
    public  void editarEspecialidad(Especialidad especialidad){
    cntrlEspecialidad.editarEspecialidad(especialidad);}
    
    public  void eliminarEspecialidad(int id){
    cntrlEspecialidad.eliminarEspecialidad(id);}
    
    public  ArrayList<Especialidad>buscarTodosEspecialidades(){
    return cntrlEspecialidad.buscarTodosEspecialidades();}
     
    public  Especialidad buscarEspecialidad(int id){
    return cntrlEspecialidad.buscarEspecialidad(id);}
    
    //HISTORIA CLINICA
     ControladoraPersistenciaHistoriaClinica cntrlHistoriaClinica = new ControladoraPersistenciaHistoriaClinica();
        public  void crearHistoriaClinica(HistoriaClinica historiaClinica){
   cntrlHistoriaClinica.crearHistoriaClinica(historiaClinica);}
    
    public  void editarHistoriaClinica(HistoriaClinica historiaClinica){
    cntrlHistoriaClinica.editarHistoriaClinica(historiaClinica);}
    
    public  void eliminarHistoriaClinica(int id){
    cntrlHistoriaClinica.eliminarHistoriaClinica(id);}
    
    public  ArrayList<HistoriaClinica>buscarTodosHistoriaClinicas(){
    return cntrlHistoriaClinica.buscarTodosHistoriaClinicas();}
     
    public  HistoriaClinica buscarHistoriaClinica(int id){
    return cntrlHistoriaClinica.buscarHistoriaClinica(id);}
    //DOMICILIO
    ControladoraPersistenciaDomicilio cntrlDomicilio = new ControladoraPersistenciaDomicilio();
        public  void crearDomicilio(Domicilio domicilio){
   cntrlDomicilio.crearDomicilio(domicilio);}
    
    public  void editarDomicilio(Domicilio domicilio){
    cntrlDomicilio.editarDomicilio(domicilio);}
    
    public  void eliminarDomicilio(int id){
    cntrlDomicilio.eliminarDomicilio(id);}
    
    public  ArrayList<Domicilio>buscarTodosDomicilios(){
    return cntrlDomicilio.buscarTodosDomicilios();}
     
    public  Domicilio buscarDomicilio(int id){
    return cntrlDomicilio.buscarDomicilio(id);}
    
    //PACIENTE
    ControladoraPersistenciaPaciente cntrlPaciente = new ControladoraPersistenciaPaciente();
        public  void crearPaciente(Paciente paciente){
   cntrlPaciente.crearPaciente(paciente);}
    
    public  void editarPaciente(Paciente paciente){
    cntrlPaciente.editarPaciente(paciente);}
    
    public  void eliminarPaciente(int id){
    cntrlPaciente.eliminarPaciente(id);}
    
    public  ArrayList<Paciente>buscarTodosPacientes(){
    return cntrlPaciente.buscarTodosPacientes();}
     
    public  Paciente buscarPaciente(int id){
    return cntrlPaciente.buscarPaciente(id);}
}
