package Modelo;

public class Usuario {
    private int id;
    private String nombreUsuario;
    private String contrasena;
    private String nombreCompleto;
    private String rol;
    private String permisos;
    private String rutaImagen;  

    public Usuario() {}

    public Usuario(int id, String nombreUsuario, String contrasena, String nombreCompleto, String rol, String permisos, String rutaImagen) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.nombreCompleto = nombreCompleto;
        this.rol = rol;
        this.permisos = permisos;
        this.rutaImagen = rutaImagen;
    }

   
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    
    public String getPermisos() { return permisos; }
    public void setPermisos(String permisos) { this.permisos = permisos; }
    
    public String getRutaImagen() { return rutaImagen; }
    public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen; }
}