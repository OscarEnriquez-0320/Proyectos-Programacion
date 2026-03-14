package Modelo;

public class Venta {
    private String idTicket;
    private String idProducto;
    private String nombreProducto;
    private double precioUnitario;
    private int cantidad;
    private double subtotal;
    private String fecha;
    private String cajero;

    public Venta() {}

    public Venta(String idTicket, String idProducto, String nombreProducto, 
                 double precioUnitario, int cantidad, String fecha, String cajero) {
        this.idTicket = idTicket;
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.subtotal = precioUnitario * cantidad;
        this.fecha = fecha;
        this.cajero = cajero;
    }

   
    public String getIdTicket() { return idTicket; }
    public void setIdTicket(String idTicket) { this.idTicket = idTicket; }

    public String getIdProducto() { return idProducto; }
    public void setIdProducto(String idProducto) { this.idProducto = idProducto; }

    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { 
        this.precioUnitario = precioUnitario;
        calcularSubtotal();
    }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { 
        this.cantidad = cantidad;
        calcularSubtotal();
    }

    public double getSubtotal() { return subtotal; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getCajero() { return cajero; }
    public void setCajero(String cajero) { this.cajero = cajero; }

    private void calcularSubtotal() {
        this.subtotal = this.precioUnitario * this.cantidad;
    }

    @Override
    public String toString() {
        return idTicket + "," + idProducto + "," + nombreProducto + "," +
               precioUnitario + "," + cantidad + "," + subtotal + "," + fecha + "," + cajero;
    }

    public String[] toArray() {
        return new String[]{
            idTicket, idProducto, nombreProducto,
            String.valueOf(cantidad),
            String.format("$%.2f", precioUnitario),
            String.format("$%.2f", subtotal)
        };
    }
}