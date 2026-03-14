package Modelo;

public class Inventario {
    private String idProducto;
    private String nombreProducto;
    private int cantidadActual;
    private int cantidadMinima;
    private String tipo;
    private double precio;
    private String estado; 

    public Inventario() {
        this.estado = "Disponible";
    }

    public Inventario(String idProducto, String nombreProducto, int cantidadActual, 
                     int cantidadMinima, String tipo, double precio) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.cantidadActual = cantidadActual;
        this.cantidadMinima = cantidadMinima;
        this.tipo = tipo;
        this.precio = precio;
        actualizarEstado();
    }

    
    public String getIdProducto() { return idProducto; }
    public void setIdProducto(String idProducto) { this.idProducto = idProducto; }

    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

    public int getCantidadActual() { return cantidadActual; }
    public void setCantidadActual(int cantidadActual) { 
        this.cantidadActual = cantidadActual;
        actualizarEstado();
    }

    public int getCantidadMinima() { return cantidadMinima; }
    public void setCantidadMinima(int cantidadMinima) { this.cantidadMinima = cantidadMinima; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getEstado() { return estado; }

    private void actualizarEstado() {
        this.estado = (cantidadActual > 0) ? "Disponible" : "Agotado";
    }

    public void agregarStock(int cantidad) {
        this.cantidadActual += cantidad;
        actualizarEstado();
    }

    public boolean quitarStock(int cantidad) {
        if (this.cantidadActual >= cantidad) {
            this.cantidadActual -= cantidad;
            actualizarEstado();
            return true;
        }
        return false;
    }

    public boolean necesitaAlerta() {
        return cantidadActual <= cantidadMinima;
    }

    @Override
    public String toString() {
        return idProducto + "," + nombreProducto + "," + cantidadActual + "," +
               cantidadMinima + "," + tipo + "," + precio + "," + estado;
    }

    public String[] toArray() {
        return new String[]{
            idProducto, nombreProducto, tipo, 
            String.valueOf(cantidadActual), 
            String.format("%.2f", precio), 
            estado
        };
    }
}