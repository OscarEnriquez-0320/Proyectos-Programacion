package Modelo;

import java.util.Objects;

public class Asignaturas {
    private String academia;  // antes era "carrera"
    private String materia;
    private String profesor;
    private String grupo;
    private String periodo;   // Nuevo campo

    public Asignaturas(String academia, String materia, String profesor, String grupo) {
        super();
        this.academia = academia;
        this.materia = materia;
        this.profesor = profesor;
        this.grupo = grupo;
        this.periodo = "";
    }

    // Getters y Setters
    public String getAcademia() {
        return academia;
    }

    public void setAcademia(String academia) {
        this.academia = academia;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    @Override
    public String toString() {
        return this.grupo + " - " + this.getProfesor() + " (" + this.getMateria() + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash(academia, grupo, materia, profesor);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Asignaturas other = (Asignaturas) obj;
        return Objects.equals(academia, other.academia) && 
               Objects.equals(grupo, other.grupo) &&
               Objects.equals(materia, other.materia) && 
               Objects.equals(profesor, other.profesor);
    }
}