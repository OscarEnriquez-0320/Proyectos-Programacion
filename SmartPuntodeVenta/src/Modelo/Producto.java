package Modelo;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Producto {
    private int id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private BigDecimal precioCompra;
    private BigDecimal porcentajeGanancia;
    private BigDecimal precioVenta;
    private int cantidadAlmacen;
    private int cantidadMinima;
    private UnidadMedida unidad;
    private Proveedor proveedor;
    private String rutaImagen;      // Ruta local de la imagen
    private String categoria;
    private String ubicacion;
    
    public Producto() {
        this.precioCompra = BigDecimal.ZERO;
        this.porcentajeGanancia = BigDecimal.valueOf(30);
        this.precioVenta = BigDecimal.ZERO;
        this.cantidadAlmacen = 0;
        this.cantidadMinima = 5;
    }
    
    public Producto(int id, String codigo, String nombre, BigDecimal precioCompra, 
                    BigDecimal porcentajeGanancia, int cantidadAlmacen, UnidadMedida unidad) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.precioCompra = precioCompra;
        this.porcentajeGanancia = porcentajeGanancia;
        this.cantidadAlmacen = cantidadAlmacen;
        this.unidad = unidad;
        calcularPrecioVenta();
    }
    
    public void calcularPrecioVenta() {
        if (precioCompra != null && porcentajeGanancia != null) {
            BigDecimal ganancia = precioCompra.multiply(porcentajeGanancia)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            this.precioVenta = precioCompra.add(ganancia);
        }
    }
    
    public boolean necesitaReponer() {
        return cantidadAlmacen <= cantidadMinima;
    }
    
    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public BigDecimal getPrecioCompra() { return precioCompra; }
    public void setPrecioCompra(BigDecimal precioCompra) { 
        this.precioCompra = precioCompra;
        calcularPrecioVenta();
    }
    
    public BigDecimal getPorcentajeGanancia() { return porcentajeGanancia; }
    public void setPorcentajeGanancia(BigDecimal porcentajeGanancia) { 
        this.porcentajeGanancia = porcentajeGanancia;
        calcularPrecioVenta();
    }
    
    public BigDecimal getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(BigDecimal precioVenta) { this.precioVenta = precioVenta; }
    
    public int getCantidadAlmacen() { return cantidadAlmacen; }
    public void setCantidadAlmacen(int cantidadAlmacen) { this.cantidadAlmacen = cantidadAlmacen; }
    
    public int getCantidadMinima() { return cantidadMinima; }
    public void setCantidadMinima(int cantidadMinima) { this.cantidadMinima = cantidadMinima; }
    
    public UnidadMedida getUnidad() { return unidad; }
    public void setUnidad(UnidadMedida unidad) { this.unidad = unidad; }
    
    public Proveedor getProveedor() { return proveedor; }
    public void setProveedor(Proveedor proveedor) { this.proveedor = proveedor; }
    
    public String getRutaImagen() { return rutaImagen; }
    public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen; }
    
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    
    @Override
    public String toString() {
        return nombre + " (" + codigo + ") - " + cantidadAlmacen + " " + 
               (unidad != null ? unidad.getAbreviatura() : "");
    }
}