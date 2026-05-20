package Modelo;

import java.util.Date;
import java.util.List;

public class Devolucion {
    private int id;
    private int idVenta;
    private String folioVenta; // auxiliar
    private Date fecha;
    private double montoTotal;
    private int idUsuario;
    private String nombreUsuario;
    private List<DetalleDevolucion> detalles;

    public Devolucion() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdVenta() { return idVenta; }
    public void setIdVenta(int idVenta) { this.idVenta = idVenta; }
    public String getFolioVenta() { return folioVenta; }
    public void setFolioVenta(String folioVenta) { this.folioVenta = folioVenta; }
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    public double getMontoTotal() { return montoTotal; }
    public void setMontoTotal(double montoTotal) { this.montoTotal = montoTotal; }
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    public List<DetalleDevolucion> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleDevolucion> detalles) { this.detalles = detalles; }
}