package Modelo;

import java.util.Date;

public class CorteCaja {
    private int id;
    private Date fechaApertura;
    private Date fechaCierre;
    private double montoApertura;
    private double montoVentasEfectivo;
    private double montoCierre;
    private int idUsuarioApertura;
    private String nombreUsuarioApertura;
    private int idUsuarioCierre;
    private String nombreUsuarioCierre;

    public CorteCaja() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Date getFechaApertura() { return fechaApertura; }
    public void setFechaApertura(Date fechaApertura) { this.fechaApertura = fechaApertura; }
    public Date getFechaCierre() { return fechaCierre; }
    public void setFechaCierre(Date fechaCierre) { this.fechaCierre = fechaCierre; }
    public double getMontoApertura() { return montoApertura; }
    public void setMontoApertura(double montoApertura) { this.montoApertura = montoApertura; }
    public double getMontoVentasEfectivo() { return montoVentasEfectivo; }
    public void setMontoVentasEfectivo(double montoVentasEfectivo) { this.montoVentasEfectivo = montoVentasEfectivo; }
    public double getMontoCierre() { return montoCierre; }
    public void setMontoCierre(double montoCierre) { this.montoCierre = montoCierre; }
    public int getIdUsuarioApertura() { return idUsuarioApertura; }
    public void setIdUsuarioApertura(int idUsuarioApertura) { this.idUsuarioApertura = idUsuarioApertura; }
    public String getNombreUsuarioApertura() { return nombreUsuarioApertura; }
    public void setNombreUsuarioApertura(String nombreUsuarioApertura) { this.nombreUsuarioApertura = nombreUsuarioApertura; }
    public int getIdUsuarioCierre() { return idUsuarioCierre; }
    public void setIdUsuarioCierre(int idUsuarioCierre) { this.idUsuarioCierre = idUsuarioCierre; }
    public String getNombreUsuarioCierre() { return nombreUsuarioCierre; }
    public void setNombreUsuarioCierre(String nombreUsuarioCierre) { this.nombreUsuarioCierre = nombreUsuarioCierre; }
}