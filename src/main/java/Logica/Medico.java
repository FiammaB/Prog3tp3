
package Logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
@Entity
public class Medico  extends Persona implements Serializable{
    
    private  int matricula;
    private long celular;
    @OneToMany(mappedBy="medico",cascade = CascadeType.ALL)
    private  List<Turno> turnos;
    @ManyToMany
     @JoinTable(
        name = "medico_especialidad",
        joinColumns = @JoinColumn(name = "idMedico"),
        inverseJoinColumns = @JoinColumn(name = "idEspecialidad")
    )
    private  List<Especialidad> especialidades;

    public Medico( int matricula, long celular, List<Turno> turnos, List<Especialidad> especialidades) {
        this.matricula = matricula;
        this.celular = celular;
        this.turnos = turnos;
        this.especialidades = especialidades;
        
        
    }

    public Medico(int matricula, long celular,  String nombre, String apellido, long dni, Domicilio domicilio) {
        super( nombre, apellido, dni, domicilio);
        
        this.matricula = matricula;
        this.celular = celular;
        
    }

    public Medico( int matricula, long celular, List<Turno> turnos, List<Especialidad> especialidades, int id, String nombre, String apellido, long dni) {
        super(id, nombre, apellido, dni);
        this.matricula = matricula;
        this.celular = celular;
        this.turnos = turnos;
        this.especialidades = especialidades;
    }

    public Medico(int matricula, long celular, List<Turno> turnos, List<Especialidad> especialidades, String nombre, String apellido, long dni) {
        super(nombre, apellido, dni);
        this.matricula = matricula;
        this.celular = celular;
        this.turnos = turnos;
        this.especialidades = especialidades;
    }

    public Medico() {
        this.especialidades=new ArrayList<>();
        this.turnos= new ArrayList<>();
    }



    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public long getCelular() {
        return celular;
    }

    public void setCelular(long celular) {
        this.celular = celular;
    }

    public List<Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(List<Turno> turnos) {
        this.turnos = turnos;
    }

    public List<Especialidad> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<Especialidad> especialidades) {
        this.especialidades = especialidades;
    }
    
    
    
}
