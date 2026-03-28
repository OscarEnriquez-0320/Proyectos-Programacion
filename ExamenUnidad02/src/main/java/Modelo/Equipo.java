package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Equipo {
    private List<String> integrantes;
    private double calificacionRubrica;
    
    public Equipo() {
        this.integrantes = new ArrayList<>();
        this.calificacionRubrica = 0;
    }
    
    public Equipo(List<String> integrantes) {
        this();
        this.integrantes = integrantes;
    }
    
    public Equipo(List<String> integrantes, double calificacion) {
        this.integrantes = integrantes;
        this.calificacionRubrica = calificacion;
    }
    
    // Getters y Setters
    public List<String> getIntegrantes() {
        return integrantes;
    }
    
    public void setIntegrantes(List<String> integrantes) {
        this.integrantes = integrantes;
    }
    
    public double getCalificacionRubrica() {
        return calificacionRubrica;
    }
    
    public void setCalificacionRubrica(double calificacionRubrica) {
        this.calificacionRubrica = calificacionRubrica;
    }
    
    public String getIntegrantesString() {
        return String.join(", ", integrantes);
    }
    
    public int getNumeroIntegrantes() {
        return integrantes.size();
    }
    
    @Override
    public String toString() {
        return getIntegrantesString() + " - Calif: " + calificacionRubrica;
    }
}