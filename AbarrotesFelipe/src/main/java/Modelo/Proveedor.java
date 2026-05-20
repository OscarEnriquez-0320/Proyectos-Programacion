package Modelo;

public class Proveedor {
    private int id;
    private String nombreEmpresa;
    private String contactoNombre;
    private String telefono;
    private String categoria;


    public Proveedor() {}

    public Proveedor(int id, String nombreEmpresa, String contactoNombre, String telefono, String categoria, String rutaImagen) {
        this.id = id;
        this.nombreEmpresa = nombreEmpresa;
        this.contactoNombre = contactoNombre;
        this.telefono = telefono;
        this.categoria = categoria;
        
    }

  
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombreEmpresa() { return nombreEmpresa; }
    public void setNombreEmpresa(String nombreEmpresa) { this.nombreEmpresa = nombreEmpresa; }
    public String getContactoNombre() { return contactoNombre; }
    public void setContactoNombre(String contactoNombre) { this.contactoNombre = contactoNombre; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    
}