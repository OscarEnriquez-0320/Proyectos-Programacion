package Modelo;

public class Obra {
    private String idobra;
    private String nombre;
    private String fecha_inicio;
    private String fecha_fin;
    
    public Obra(String idobra, String nombre, String fecha_inicio, String fecha_fin) {
        this.idobra = idobra;
        this.nombre = nombre;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
    }
    
    public String getIdobra() { return idobra; }
    public void setIdobra(String idobra) { this.idobra = idobra; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getFecha_inicio() { return fecha_inicio; }
    public void setFecha_inicio(String fecha_inicio) { this.fecha_inicio = fecha_inicio; }
    public String getFecha_fin() { return fecha_fin; }
    public void setFecha_fin(String fecha_fin) { this.fecha_fin = fecha_fin; }
    
    @Override
    public String toString() {
        return idobra + " - " + nombre + " (" + fecha_inicio + " a " + fecha_fin + ")";
    }
}