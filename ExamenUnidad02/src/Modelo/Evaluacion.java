package Modelo;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

public class Evaluacion {
    private String id;
    private String asignatura;
    private String profesor;
    private String grupo;
    private String instrumento;
    private LocalDate fecha;
    private List<AtributoEgreso> atributos;
    private List<Equipo> equipos;
    private String observaciones;
    private String estatus;
    
    public Evaluacion() {
        this.atributos = new ArrayList<>();
        this.equipos = new ArrayList<>();
        this.fecha = LocalDate.now();
        this.estatus = "pendiente";
    }
    
    public Evaluacion(String asignatura, String profesor, String grupo) {
        this();
        this.asignatura = asignatura;
        this.profesor = profesor;
        this.grupo = grupo;
        this.id = generarId();
    }
    
    private String generarId() {
        return asignatura.replace(" ", "_") + "_" + 
               profesor.replace(" ", "_") + "_" + 
               grupo;
    }
    
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getAsignatura() { return asignatura; }
    public void setAsignatura(String asignatura) { this.asignatura = asignatura; }
    
    public String getProfesor() { return profesor; }
    public void setProfesor(String profesor) { this.profesor = profesor; }
    
    public String getGrupo() { return grupo; }
    public void setGrupo(String grupo) { this.grupo = grupo; }
    
    public String getInstrumento() { return instrumento; }
    public void setInstrumento(String instrumento) { this.instrumento = instrumento; }
    
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    
    public List<AtributoEgreso> getAtributos() { return atributos; }
    public void setAtributos(List<AtributoEgreso> atributos) { this.atributos = atributos; }
    
    public List<Equipo> getEquipos() { return equipos; }
    public void setEquipos(List<Equipo> equipos) { this.equipos = equipos; }
    
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    
    public String getEstatus() { return estatus; }
    public void setEstatus(String estatus) { this.estatus = estatus; }
}