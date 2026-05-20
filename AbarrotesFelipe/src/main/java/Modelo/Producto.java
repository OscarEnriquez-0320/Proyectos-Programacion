package Modelo;

import java.math.BigDecimal;

public class Producto {
    private int id;
    private String nombre;
    private String codigoBarras;
    private BigDecimal precio;
    private double stock;  
    private double stockMinimo;  
    private double stockMaximo;  
    private String categoria;
    private String tipoVenta;
    private String rutaImagen;
    private int idProveedor;
    private String nombreProveedor;

    public Producto() {}

    public Producto(int id, String nombre, String codigoBarras, BigDecimal precio, double stock, 
                    double stockMinimo, double stockMaximo, String categoria, String tipoVenta, 
                    String rutaImagen, int idProveedor) {
        this.id = id;
        this.nombre = nombre;
        this.codigoBarras = codigoBarras;
        this.precio = precio;
        this.stock = stock;
        this.stockMinimo = stockMinimo;
        this.stockMaximo = stockMaximo;
        this.categoria = categoria;
        this.tipoVenta = tipoVenta;
        this.rutaImagen = rutaImagen;
        this.idProveedor = idProveedor;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getCodigoBarras() { return codigoBarras; }
    public void setCodigoBarras(String codigoBarras) { this.codigoBarras = codigoBarras; }
    
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
    
    public double getStock() { return stock; }
    public void setStock(double stock) { this.stock = stock; }
    
    public double getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(double stockMinimo) { this.stockMinimo = stockMinimo; }
    
    public double getStockMaximo() { return stockMaximo; }
    public void setStockMaximo(double stockMaximo) { this.stockMaximo = stockMaximo; }
    
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    
    public String getTipoVenta() { return tipoVenta; }
    public void setTipoVenta(String tipoVenta) { this.tipoVenta = tipoVenta; }
    
    public String getRutaImagen() { return rutaImagen; }
    public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen; }
    
    public int getIdProveedor() { return idProveedor; }
    public void setIdProveedor(int idProveedor) { this.idProveedor = idProveedor; }
    
    public String getNombreProveedor() { return nombreProveedor; }
    public void setNombreProveedor(String nombreProveedor) { this.nombreProveedor = nombreProveedor; }
}
