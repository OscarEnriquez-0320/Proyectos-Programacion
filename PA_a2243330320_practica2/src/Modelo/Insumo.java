package Modelo;

/**
 * Clase que representa un Insumo/Producto
 */
public class Insumo {
    private String idProducto;
    private String producto;
    private String idCategoria;
    
    public Insumo(String idProducto, String producto, String idCategoria) {
        this.idProducto = idProducto;
        this.producto = producto;
        this.idCategoria = idCategoria;
    }
    
    // Getters y Setters
    public String getIdProducto() {
        return idProducto;
    }
    
    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }
    
    public String getProducto() {
        return producto;
    }
    
    public void setProducto(String producto) {
        this.producto = producto;
    }
    
    public String getIdCategoria() {
        return idCategoria;
    }
    
    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }
    
    /**
     * Método para guardar en archivo
     * @return String con formato para archivo (separado por ;)
     */
    public String toLinea() {
        return idProducto + ";" + producto + ";" + idCategoria;
    }
    
    @Override
    public String toString() {
        return "ID: " + idProducto + " - " + producto + " (Categoría: " + idCategoria + ")";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Insumo insumo = (Insumo) obj;
        return idProducto.equals(insumo.idProducto);
    }
}