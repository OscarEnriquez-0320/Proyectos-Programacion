package Modelo;

import java.util.Date;

public class MovimientoCaja {
    private int id;
    private int idCorte;
    private String tipo;  
    private double monto;
    private String descripcion;
    private Date fecha;

    public MovimientoCaja() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getIdCorte() { return idCorte; }
    public void setIdCorte(int idCorte) { this.idCorte = idCorte; }
    
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    
    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
}