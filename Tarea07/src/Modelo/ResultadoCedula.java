package Modelo;

public class ResultadoCedula {
    private String academia;
    private String asignatura;
    private int numeroGrupos;
    private double promedioGeneral;
    private double porcentajeMayorPromedio;
    private double porcentajeReprobacion;
    private String profesores; // Listado de profesores

    public ResultadoCedula(String academia, String asignatura) {
        this.academia = academia;
        this.asignatura = asignatura;
        this.numeroGrupos = 0;
        this.promedioGeneral = 0;
        this.porcentajeMayorPromedio = 0;
        this.porcentajeReprobacion = 0;
        this.profesores = "";
    }

    // Getters y Setters
    public String getAcademia() { return academia; }
    public void setAcademia(String academia) { this.academia = academia; }

    public String getAsignatura() { return asignatura; }
    public void setAsignatura(String asignatura) { this.asignatura = asignatura; }

    public int getNumeroGrupos() { return numeroGrupos; }
    public void setNumeroGrupos(int numeroGrupos) { this.numeroGrupos = numeroGrupos; }

    public double getPromedioGeneral() { return promedioGeneral; }
    public void setPromedioGeneral(double promedioGeneral) { this.promedioGeneral = promedioGeneral; }

    public double getPorcentajeMayorPromedio() { return porcentajeMayorPromedio; }
    public void setPorcentajeMayorPromedio(double porcentajeMayorPromedio) { this.porcentajeMayorPromedio = porcentajeMayorPromedio; }

    public double getPorcentajeReprobacion() { return porcentajeReprobacion; }
    public void setPorcentajeReprobacion(double porcentajeReprobacion) { this.porcentajeReprobacion = porcentajeReprobacion; }

    public String getProfesores() { return profesores; }
    public void setProfesores(String profesores) { this.profesores = profesores; }
}