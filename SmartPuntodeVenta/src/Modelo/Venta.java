package Modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Venta {
    private int id;
    private String numeroFactura;
    private Date fecha;
    private List<DetalleVenta> detalles;
    private BigDecimal subtotal;
    private BigDecimal impuesto;
    private BigDecimal total;
    private String metodoPago;
    
    public Venta() {
        this.detalles = new ArrayList<>();
        this.subtotal = BigDecimal.ZERO;
        this.impuesto = BigDecimal.ZERO;
        this.total = BigDecimal.ZERO;
        this.fecha = new Date();
        this.metodoPago = "EFECTIVO";
    }
    
    public void agregarDetalle(DetalleVenta detalle) {
        detalles.add(detalle);
        recalcularTotales();
    }
    
    public void eliminarDetalle(int index) {
        detalles.remove(index);
        recalcularTotales();
    }
    
    public void limpiarDetalles() {
        detalles.clear();
        recalcularTotales();
    }
    
    public void recalcularTotales() {
        subtotal = BigDecimal.ZERO;
        for (DetalleVenta detalle : detalles) {
            subtotal = subtotal.add(detalle.getSubtotal());
        }
        // IVA en México es 16%
        impuesto = subtotal.multiply(BigDecimal.valueOf(0.16));
        total = subtotal.add(impuesto);
    }
    
    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNumeroFactura() { return numeroFactura; }
    public void setNumeroFactura(String numeroFactura) { this.numeroFactura = numeroFactura; }
    
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    
    public List<DetalleVenta> getDetalles() { return detalles; }
    
    public BigDecimal getSubtotal() { return subtotal; }
    public BigDecimal getImpuesto() { return impuesto; }
    public BigDecimal getTotal() { return total; }
    
    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
}