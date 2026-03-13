package Modelo;

public class ResultadoPromedio {
    private String profesor;
    private String asignatura;
    private String grupo;
    private int totalAlumnos;
    private double promedioGeneral;
    private int aprobados;
    private int reprobados;
    private double porcentajeAprobados;
    private double porcentajeReprobados;
    private double promedioAcreditados;

    // Constructor
    public ResultadoPromedio(String profesor, String asignatura, String grupo) {
        this.profesor = profesor;
        this.asignatura = asignatura;
        this.grupo = grupo;
        this.totalAlumnos = 0;
        this.promedioGeneral = 0;
        this.aprobados = 0;
        this.reprobados = 0;
        this.porcentajeAprobados = 0;
        this.porcentajeReprobados = 0;
        this.promedioAcreditados = 0;
    }

    // Getters y Setters
    public String getProfesor() { return profesor; }
    public void setProfesor(String profesor) { this.profesor = profesor; }

    public String getAsignatura() { return asignatura; }
    public void setAsignatura(String asignatura) { this.asignatura = asignatura; }

    public String getGrupo() { return grupo; }
    public void setGrupo(String grupo) { this.grupo = grupo; }

    public int getTotalAlumnos() { return totalAlumnos; }
    public void setTotalAlumnos(int totalAlumnos) { this.totalAlumnos = totalAlumnos; }

    public double getPromedioGeneral() { return promedioGeneral; }
    public void setPromedioGeneral(double promedioGeneral) { this.promedioGeneral = promedioGeneral; }

    public int getAprobados() { return aprobados; }
    public void setAprobados(int aprobados) { this.aprobados = aprobados; }

    public int getReprobados() { return reprobados; }
    public void setReprobados(int reprobados) { this.reprobados = reprobados; }

    public double getPorcentajeAprobados() { return porcentajeAprobados; }
    public void setPorcentajeAprobados(double porcentajeAprobados) { this.porcentajeAprobados = porcentajeAprobados; }

    public double getPorcentajeReprobados() { return porcentajeReprobados; }
    public void setPorcentajeReprobados(double porcentajeReprobados) { this.porcentajeReprobados = porcentajeReprobados; }

    public double getPromedioAcreditados() { return promedioAcreditados; }
    public void setPromedioAcreditados(double promedioAcreditados) { this.promedioAcreditados = promedioAcreditados; }
}