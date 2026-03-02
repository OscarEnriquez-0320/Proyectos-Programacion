package Modelo;

/**
 * Clase que representa una Categoría de productos
 */
public class Categoria {
    private String idCategoria;
    private String nombreCategoria;
    
    public Categoria(String idCategoria, String nombreCategoria) {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
    }
    
    // Getters y Setters
    public String getIdCategoria() {
        return idCategoria;
    }
    
    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }
    
    public String getNombreCategoria() {
        return nombreCategoria;
    }
    
    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }
    
    /**
     * Método para guardar en archivo
     * @return String con formato para archivo (separado por ;)
     */
    public String toLinea() {
        return idCategoria + ";" + nombreCategoria;
    }
    
    @Override
    public String toString() {
        return nombreCategoria; // Importante: así se muestra en JList
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Categoria categoria = (Categoria) obj;
        return idCategoria.equals(categoria.idCategoria);
    }
}