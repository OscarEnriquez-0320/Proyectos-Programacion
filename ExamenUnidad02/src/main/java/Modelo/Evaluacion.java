package Modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Evaluacion {

    private String id;
    private String asignatura;
    private String profesor;
    private String grupo;
    private String periodo;
    private String actividad;
    private LocalDate fecha;
    private List<AtributoEgreso> atributos;
    private List<CalificacionAlumno> calificacionesAlumnos;
    private List<String> criterios;
    private String estatus;

    public Evaluacion() {
        this.atributos = new ArrayList<>();
        this.calificacionesAlumnos = new ArrayList<>();
        this.criterios = new ArrayList<>();
        this.fecha = LocalDate.now();
        this.estatus = "pendiente";
        this.periodo = "";
        this.actividad = "";

        criterios.add("Análisis");
        criterios.add("Diseño");
        criterios.add("Implementación");
        criterios.add("Documentación");
    }

    public Evaluacion(String asignatura, String profesor, String grupo) {
        this();
        this.asignatura = asignatura;
        this.profesor = profesor;
        this.grupo = grupo;
        this.id = generarId();
        cargarAlumnos();
    }

    private void cargarAlumnos() {
        List<Alumno> alumnos = CargadorAlumnos.getAlumnosPorClase(asignatura, profesor, grupo);
        for (Alumno alumno : alumnos) {
            calificacionesAlumnos.add(new CalificacionAlumno(
                    alumno.getNombre(),
                    alumno.getMatricula(),
                    criterios.size()
            ));
        }
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

    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }

    public String getActividad() { return actividad; }
    public void setActividad(String actividad) { this.actividad = actividad; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public List<AtributoEgreso> getAtributos() { return atributos; }
    public void setAtributos(List<AtributoEgreso> atributos) { this.atributos = atributos; }

    public List<CalificacionAlumno> getCalificacionesAlumnos() { return calificacionesAlumnos; }
    public void setCalificacionesAlumnos(List<CalificacionAlumno> calificacionesAlumnos) {
        this.calificacionesAlumnos = calificacionesAlumnos;
    }

    public List<String> getCriterios() { return criterios; }
    public void setCriterios(List<String> criterios) { this.criterios = criterios; }

    public String getEstatus() { return estatus; }
    public void setEstatus(String estatus) { this.estatus = estatus; }

    public void agregarCriterio(String nombreCriterio) {
        criterios.add(nombreCriterio);
        for (CalificacionAlumno ca : calificacionesAlumnos) {
            double[] nuevasCalificaciones = new double[criterios.size()];
            double[] viejas = ca.getCalificaciones();
            for (int i = 0; i < viejas.length && i < nuevasCalificaciones.length; i++) {
                nuevasCalificaciones[i] = viejas[i];
            }
        }
    }

    public double getPromedioGeneral() {
        if (calificacionesAlumnos.isEmpty()) return 0;
        double suma = 0;
        for (CalificacionAlumno ca : calificacionesAlumnos) {
            suma += ca.getPromedio();
        }
        return suma / calificacionesAlumnos.size();
    }

    public int getNumReprobados() {
        int count = 0;
        for (CalificacionAlumno ca : calificacionesAlumnos) {
            if (ca.isReprobado()) count++;
        }
        return count;
    }
}