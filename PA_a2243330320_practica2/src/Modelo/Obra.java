package Modelo;

public class Obra {
    private String idObra;
    private String nombre;
    private String descripcion;
    
    public Obra(String idObra, String nombre, String descripcion) {
        this.idObra = idObra;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    
    // Getters y Setters
    public String getIdObra() { return idObra; }
    public void setIdObra(String idObra) { this.idObra = idObra; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    /**
     * Formato para guardar en archivo: ID;Nombre;Descripción
     */
    public String toLinea() {
        return idObra + ";" + nombre + ";" + descripcion;
    }
    
    @Override
    public String toString() {
        return idObra + " - " + nombre;
    }
}