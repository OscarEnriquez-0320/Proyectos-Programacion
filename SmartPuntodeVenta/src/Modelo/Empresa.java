package Modelo;

import java.io.Serializable;

public class Empresa implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String nombre;
    private String eslogan;
    private String rtn;
    private String telefono;
    private String email;
    private String sitioWeb;
    private String direccion;
    private String ciudad;
    private String departamento;
    private String codigoPostal;
    private String correoRecuperacion;
    private String rutaLogo;
    private String formatoFecha;
    private String formatoMoneda;
    
    public Empresa() {
        this.formatoFecha = "dd/MM/yyyy";
        this.formatoMoneda = "L. ###,###.##";
    }
    
    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getEslogan() { return eslogan; }
    public void setEslogan(String eslogan) { this.eslogan = eslogan; }
    
    public String getRtn() { return rtn; }
    public void setRtn(String rtn) { this.rtn = rtn; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getSitioWeb() { return sitioWeb; }
    public void setSitioWeb(String sitioWeb) { this.sitioWeb = sitioWeb; }
    
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    
    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }
    
    public String getCodigoPostal() { return codigoPostal; }
    public void setCodigoPostal(String codigoPostal) { this.codigoPostal = codigoPostal; }
    
    public String getCorreoRecuperacion() { return correoRecuperacion; }
    public void setCorreoRecuperacion(String correoRecuperacion) { this.correoRecuperacion = correoRecuperacion; }
    
    public String getRutaLogo() { return rutaLogo; }
    public void setRutaLogo(String rutaLogo) { this.rutaLogo = rutaLogo; }
    
    public String getFormatoFecha() { return formatoFecha; }
    public void setFormatoFecha(String formatoFecha) { this.formatoFecha = formatoFecha; }
    
    public String getFormatoMoneda() { return formatoMoneda; }
    public void setFormatoMoneda(String formatoMoneda) { this.formatoMoneda = formatoMoneda; }
    
    // Método para obtener dirección completa
    public String getDireccionCompleta() {
        StringBuilder sb = new StringBuilder();
        if (direccion != null && !direccion.isEmpty()) sb.append(direccion);
        if (ciudad != null && !ciudad.isEmpty()) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(ciudad);
        }
        if (departamento != null && !departamento.isEmpty()) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(departamento);
        }
        if (codigoPostal != null && !codigoPostal.isEmpty()) {
            if (sb.length() > 0) sb.append(" - ");
            sb.append(codigoPostal);
        }
        return sb.toString();
    }
}