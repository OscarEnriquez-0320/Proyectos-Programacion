package Modelo;

import java.util.Date;

public class Configuracion {
    private int id;
    private String clave;
    private String valor;
    private String descripcion;
    private Date fechaActualizacion;

    public Configuracion() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
    
    public String getValor() { return valor; }
    public void setValor(String valor) { this.valor = valor; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public Date getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(Date fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }
}