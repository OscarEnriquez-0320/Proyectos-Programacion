package Modelo;


public class Insumo {
    private String id;
    private String insumo;
    private String categoria;
    
    public Insumo(String id, String insumo, String categoria) {
        this.id = id;
        this.insumo = insumo;
        this.categoria = categoria;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getInsumo() {
        return insumo;
    }
    
    public void setInsumo(String insumo) {
        this.insumo = insumo;
    }
    
    public String getCategoria() {
        return categoria;
    }
    
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    @Override
    public String toString() {
        return id + " - " + insumo + " - " + categoria;
    }
}