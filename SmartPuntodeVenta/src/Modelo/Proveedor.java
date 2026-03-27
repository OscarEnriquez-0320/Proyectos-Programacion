package Modelo;

public class Proveedor {
    private int id;
    private String nombre;
    private String rtn;
    private String telefono;
    private String email;
    private String direccion;
    private String nombreContacto;
    
    public Proveedor() {}
    
    public Proveedor(int id, String nombre, String telefono, String email) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getRtn() { return rtn; }
    public void setRtn(String rtn) { this.rtn = rtn; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    
    public String getNombreContacto() { return nombreContacto; }
    public void setNombreContacto(String nombreContacto) { this.nombreContacto = nombreContacto; }
    
    @Override
    public String toString() {
        return nombre + " - " + telefono;
    }
}