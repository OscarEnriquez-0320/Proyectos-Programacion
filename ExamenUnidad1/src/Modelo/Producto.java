package Modelo;

public class Producto {
    private String id;
    private String nombre;
    private String descripcion;
    private String categoria;
    private double precioCompra;
    private double precioVenta;
    private int stockInicial;
    private int stockMinimo;
    private boolean activo;

    public Producto() {
        this.activo = true;
    }

    public Producto(String id, String nombre, String descripcion, String categoria, 
                   double precioCompra, double precioVenta, int stockInicial, 
                   int stockMinimo, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.stockInicial = stockInicial;
        this.stockMinimo = stockMinimo;
        this.activo = activo;
    }

  
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public double getPrecioCompra() { return precioCompra; }
    public void setPrecioCompra(double precioCompra) { this.precioCompra = precioCompra; }

    public double getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(double precioVenta) { this.precioVenta = precioVenta; }

    public int getStockInicial() { return stockInicial; }
    public void setStockInicial(int stockInicial) { this.stockInicial = stockInicial; }

    public int getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(int stockMinimo) { this.stockMinimo = stockMinimo; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Override
    public String toString() {
        return id + "," + nombre + "," + descripcion + "," + categoria + "," +
               precioCompra + "," + precioVenta + "," + stockInicial + "," +
               stockMinimo + "," + activo;
    }

    public String[] toArray() {
        return new String[]{
            id, nombre, descripcion, categoria,
            String.valueOf(precioCompra), String.valueOf(precioVenta),
            String.valueOf(stockInicial), String.valueOf(stockMinimo),
            activo ? "Activo" : "Inactivo"
        };
    }
}