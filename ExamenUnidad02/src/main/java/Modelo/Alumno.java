package Modelo;

public class Alumno {
    private String matricula;
    private String nombre;
    private String grupo;
    private String asignatura;
    private String profesor;
    
    public Alumno() {}
    
    public Alumno(String matricula, String nombre, String grupo, String asignatura, String profesor) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.grupo = grupo;
        this.asignatura = asignatura;
        this.profesor = profesor;
    }
    
    // Getters y Setters
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getGrupo() { return grupo; }
    public void setGrupo(String grupo) { this.grupo = grupo; }
    
    public String getAsignatura() { return asignatura; }
    public void setAsignatura(String asignatura) { this.asignatura = asignatura; }
    
    public String getProfesor() { return profesor; }
    public void setProfesor(String profesor) { this.profesor = profesor; }
    
    @Override
    public String toString() {
        return nombre + " (" + matricula + ")";
    }
}