package Modelo;

import java.math.BigDecimal;

public class DetalleDevolucion {
    private int id;
    private int idDevolucion;
    private int idProducto;
    private String nombreProducto;
    private int cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;

    public DetalleDevolucion() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdDevolucion() { return idDevolucion; }
    public void setIdDevolucion(int idDevolucion) { this.idDevolucion = idDevolucion; }
    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }
    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}