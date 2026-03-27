package Controlador;

import Librerias.AlmacenamientoDatos;
import Modelo.*;
import java.math.BigDecimal;
import java.util.List;

public class PuntoVentaControlador {
    private AlmacenamientoDatos datos;
    
    public PuntoVentaControlador() {
        this.datos = AlmacenamientoDatos.getInstancia();
    }
    
    // Obtener todos los productos
    public List<Producto> obtenerProductos() {
        return datos.obtenerTodosProductos();
    }
    
    // Obtener producto por código
    public Producto obtenerProductoPorCodigo(String codigo) {
        return datos.obtenerProductoPorCodigo(codigo);
    }
    
    // Verificar stock disponible
    public boolean verificarStock(Producto producto, int cantidad) {
        return producto != null && producto.getCantidadAlmacen() >= cantidad;
    }
    
    // Agregar producto al carrito
    public DetalleVenta agregarAlCarrito(Producto producto, int cantidad) {
        if (!verificarStock(producto, cantidad)) {
            throw new IllegalArgumentException("Stock insuficiente. Disponible: " + 
                                               producto.getCantidadAlmacen());
        }
        return new DetalleVenta(producto, cantidad);
    }
    
    // Recalcular totales de la venta
    public void recalcularTotales(Venta venta) {
        venta.recalcularTotales();
    }
    
    // Realizar venta
    public boolean realizarVenta(Venta venta, String metodoPago, BigDecimal efectivoRecibido) {
        try {
            if (venta.getDetalles().isEmpty()) {
                throw new IllegalArgumentException("No hay productos en la venta");
            }
            
            // Verificar stock nuevamente
            for (DetalleVenta detalle : venta.getDetalles()) {
                Producto p = detalle.getProducto();
                if (p.getCantidadAlmacen() < detalle.getCantidad()) {
                    throw new IllegalArgumentException("Stock insuficiente para: " + p.getNombre() +
                                                       ". Disponible: " + p.getCantidadAlmacen());
                }
            }
            
            venta.setMetodoPago(metodoPago);
            
            if (metodoPago.equals("EFECTIVO") && efectivoRecibido.compareTo(venta.getTotal()) < 0) {
                throw new IllegalArgumentException("El efectivo recibido es insuficiente");
            }
            
            datos.agregarVenta(venta);
            return true;
        } catch (Exception e) {
            System.err.println("Error al realizar venta: " + e.getMessage());
            return false;
        }
    }
    
    // Calcular cambio
    public BigDecimal calcularCambio(BigDecimal efectivo, BigDecimal total) {
        if (efectivo == null || total == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal cambio = efectivo.subtract(total);
        return cambio.compareTo(BigDecimal.ZERO) > 0 ? cambio : BigDecimal.ZERO;
    }
    
    // Obtener siguiente número de factura
    public String obtenerSiguienteFactura() {
        int cantidadVentas = datos.obtenerTodasVentas().size();
        return String.format("FAC-%06d", cantidadVentas + 1);
    }
    
    // Obtener ventas del día
    public List<Venta> obtenerVentasDelDia() {
        java.util.Date hoy = new java.util.Date();
        return datos.obtenerTodasVentas().stream()
            .filter(v -> {
                long diferencia = hoy.getTime() - v.getFecha().getTime();
                return diferencia < 24 * 60 * 60 * 1000;
            })
            .toList();
    }
    
    // Obtener total vendido hoy
    public BigDecimal obtenerTotalVentasHoy() {
        return datos.obtenerTotalVentasHoy();
    }
}