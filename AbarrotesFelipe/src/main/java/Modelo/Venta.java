package Modelo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Venta {
    private int id;
    private String folio;
    private Date fecha;
    private BigDecimal total;
    private String metodoPago;
    private int idUsuario;
    private String nombreUsuario;
    private BigDecimal efectivoRecibido;
    private BigDecimal cambio;
    private boolean cancelada;
    private BigDecimal descuento;
    private BigDecimal impuesto;
    private int idCorte;  
    private List<DetalleVenta> detalles;

    public Venta() {}


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getFolio() { return folio; }
    public void setFolio(String folio) { this.folio = folio; }
    
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    
    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
    
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    
    public BigDecimal getEfectivoRecibido() { return efectivoRecibido; }
    public void setEfectivoRecibido(BigDecimal efectivoRecibido) { this.efectivoRecibido = efectivoRecibido; }
    
    public BigDecimal getCambio() { return cambio; }
    public void setCambio(BigDecimal cambio) { this.cambio = cambio; }
    
    public boolean isCancelada() { return cancelada; }
    public void setCancelada(boolean cancelada) { this.cancelada = cancelada; }
    
    public BigDecimal getDescuento() { return descuento; }
    public void setDescuento(BigDecimal descuento) { this.descuento = descuento; }
    
    public BigDecimal getImpuesto() { return impuesto; }
    public void setImpuesto(BigDecimal impuesto) { this.impuesto = impuesto; }
    
    public int getIdCorte() { return idCorte; }
    public void setIdCorte(int idCorte) { this.idCorte = idCorte; }
    
    public List<DetalleVenta> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleVenta> detalles) { this.detalles = detalles; }
}