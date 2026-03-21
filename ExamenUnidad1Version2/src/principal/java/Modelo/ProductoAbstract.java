package principal.java.Modelo;

public abstract class ProductoAbstract {
    protected String id;
    protected String nombre;
    protected String descripcion;
    protected String categoria;
    protected double precioCompra;
    protected double precioVenta;
    protected int stock;
    protected int stockMinimo;
    protected boolean activo;
    protected String rutaImagen;

    public ProductoAbstract() {
        this.activo = true;
    }

    public ProductoAbstract(String id, String nombre, String descripcion, String categoria,
                    double precioCompra, double precioVenta, int stock, int stockMinimo,
                    String rutaImagen) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.stock = stock;
        this.stockMinimo = stockMinimo;
        this.activo = true;
        this.rutaImagen = rutaImagen;
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

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public int getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(int stockMinimo) { this.stockMinimo = stockMinimo; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public String getRutaImagen() { return rutaImagen; }
    public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen; }

    public abstract String getTipo();

    public String[] toArray() {
        return new String[]{
            id, nombre, descripcion, categoria,
            String.valueOf(precioCompra), String.valueOf(precioVenta),
            String.valueOf(stock), String.valueOf(stockMinimo),
            activo ? "Activo" : "Inactivo"
        };
    }
}