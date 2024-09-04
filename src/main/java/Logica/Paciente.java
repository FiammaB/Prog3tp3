
package Logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
@Entity
public class Paciente extends  Persona implements Serializable{
   
    private  int nroSocio;
    @OneToMany(mappedBy = "paciente",cascade = CascadeType.ALL)
    private List<Turno> turnos ;
    @OneToOne(mappedBy = "paciente")
    private HistoriaClinica historiaClinica;

    public Paciente() {
    }

    public Paciente( int nroSocio, List<Turno> turnos, HistoriaClinica historiaClinica) {
        
        this.nroSocio = nroSocio;
        this.turnos = turnos;
        this.historiaClinica = historiaClinica;
    }

    public Paciente( int nroSocio, List<Turno> turnos, HistoriaClinica historiaClinica,  String nombre, String apellido, long dni, Domicilio domicilio) {
        super(nombre, apellido, dni, domicilio);
        
        this.nroSocio = nroSocio;
        this.turnos = new ArrayList<>();
        this.historiaClinica = historiaClinica;
    }

    public Paciente( int nroSocio, List<Turno> turnos, HistoriaClinica historiaClinica, int id, String nombre, String apellido, long dni) {
        super(id, nombre, apellido, dni);
        
        this.nroSocio = nroSocio;
        this.turnos = turnos;
        this.historiaClinica = historiaClinica;
    }

    public Paciente( int nroSocio, List<Turno> turnos, HistoriaClinica historiaClinica, String nombre, String apellido, long dni) {
        super(nombre, apellido, dni);
        
        this.nroSocio = nroSocio;
        this.turnos = turnos;
        this.historiaClinica = historiaClinica;
    }

  

    public int getNroSocio() {
        return nroSocio;
    }

    public void setNroSocio(int nroSocio) {
        this.nroSocio = nroSocio;
    }

    public List<Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(List<Turno> turnos) {
        this.turnos = turnos;
    }

    public HistoriaClinica getHistoriaClinica() {
        return historiaClinica;
    }

    public void setHistoriaClinica(HistoriaClinica historiaClinica) {
        this.historiaClinica = historiaClinica;
    }
    
}
