
package Logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
@Entity
public class HistoriaClinica implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int idHistoriClinica;
    private  int numero;
    private Date fechaAlta ;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idPaciente")
    private  Paciente paciente;
    @OneToMany(mappedBy="historiaClinica", cascade = CascadeType.ALL)
    private List<DetalleHistoriaClinica> detalleHistoriasClinicas;
    public HistoriaClinica() {
    }

    public HistoriaClinica( int numero, Date fechaAlta, Paciente paciente, List<DetalleHistoriaClinica> detalleHistoriasClinicas) {
        this.numero = numero;
        this.fechaAlta = fechaAlta;
        this.paciente = paciente;
        this.detalleHistoriasClinicas = detalleHistoriasClinicas;
    }

    public List<DetalleHistoriaClinica> getDetalleHistoriasClinicas() {
        return detalleHistoriasClinicas;
    }

    public void setDetalleHistoriasClinicas(List<DetalleHistoriaClinica> detalleHistoriasClinicas) {
        this.detalleHistoriasClinicas = detalleHistoriasClinicas;
    }


    public int getIdHistoriClinica() {
        return idHistoriClinica;
    }

    public void setIdHistoriClinica(int idHistoriClinica) {
        this.idHistoriClinica = idHistoriClinica;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
    
}
