/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Empleado extends Persona implements Serializable{

    private int numeroLegajo;
    private double  sueldo;

    public Empleado() {
    }

    public Empleado(int numeroLegajo, double sueldo) {
       
        this.numeroLegajo = numeroLegajo;
        this.sueldo = sueldo;
    }

    public Empleado( int numeroLegajo, double sueldo,  String nombre, String apellido, long dni, Domicilio domicilio) {
        super( nombre, apellido, dni, domicilio);
        
        this.numeroLegajo = numeroLegajo;
        this.sueldo = sueldo;
    }

    public Empleado( int numeroLegajo, double sueldo, int id, String nombre, String apellido, long dni) {
        super(id, nombre, apellido, dni);
        
        this.numeroLegajo = numeroLegajo;
        this.sueldo = sueldo;
    }

    public Empleado( int numeroLegajo, double sueldo, String nombre, String apellido, long dni) {
        super(nombre, apellido, dni);
        
        this.numeroLegajo = numeroLegajo;
        this.sueldo = sueldo;
    }


    public int getNumeroLegajo() {
        return numeroLegajo;
    }

    public void setNumeroLegajo(int numeroLegajo) {
        this.numeroLegajo = numeroLegajo;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }
    
}
