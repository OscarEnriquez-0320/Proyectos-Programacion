package Modelo;

public class CalificacionAlumno {
    private String alumnoNombre;
    private String alumnoMatricula;
    private double[] calificaciones;
    private double promedio;
    
    public CalificacionAlumno(String nombre, String matricula, int numCriterios) {
        this.alumnoNombre = nombre;
        this.alumnoMatricula = matricula;
        this.calificaciones = new double[numCriterios];
        this.promedio = 0;
        
     
        for (int i = 0; i < numCriterios; i++) {
            calificaciones[i] = 0;
        }
    }
    
    public String getAlumnoNombre() {
        return alumnoNombre;
    }
    
    public void setAlumnoNombre(String alumnoNombre) {
        this.alumnoNombre = alumnoNombre;
    }
    
    public String getAlumnoMatricula() {
        return alumnoMatricula;
    }
    
    public void setAlumnoMatricula(String alumnoMatricula) {
        this.alumnoMatricula = alumnoMatricula;
    }
    
    public double[] getCalificaciones() {
        return calificaciones;
    }
    
    public void setCalificacion(int criterioIndex, double valor) {
        if (criterioIndex >= 0 && criterioIndex < calificaciones.length) {
            calificaciones[criterioIndex] = valor;
            calcularPromedio();
        }
    }
    
    public double getCalificacion(int criterioIndex) {
        if (criterioIndex >= 0 && criterioIndex < calificaciones.length) {
            return calificaciones[criterioIndex];
        }
        return 0;
    }
    
    private void calcularPromedio() {
        double suma = 0;
        for (double calif : calificaciones) {
            suma += calif;
        }
        this.promedio = calificaciones.length > 0 ? suma / calificaciones.length : 0;
    }
    
    public double getPromedio() {
        return promedio;
    }
    
    public boolean isReprobado() {
        return promedio < 7;
    }
    
    public int getNumCriterios() {
        return calificaciones.length;
    }
}