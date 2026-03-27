package Controlador;

import Librerias.AlmacenamientoDatos;
import Modelo.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class ReporteControlador {
    private AlmacenamientoDatos datos;
    
    public ReporteControlador() {
        this.datos = AlmacenamientoDatos.getInstancia();
    }
    
    // ========== REPORTES DE VENTAS ==========
    
    public List<Venta> obtenerTodasVentas() {
        return datos.obtenerTodasVentas();
    }
    
    public List<Venta> obtenerVentasPorFecha(Date inicio, Date fin) {
        return datos.obtenerTodasVentas().stream()
            .filter(v -> v.getFecha().compareTo(inicio) >= 0 && v.getFecha().compareTo(fin) <= 0)
            .toList();
    }
    
    public Map<String, List<Venta>> obtenerVentasPorMetodoPago() {
        return datos.obtenerTodasVentas().stream()
            .collect(Collectors.groupingBy(Venta::getMetodoPago));
    }
    
    public Map<String, Object> obtenerResumenVentasPorMetodo() {
        Map<String, Object> resumen = new HashMap<>();
        Map<String, List<Venta>> porMetodo = obtenerVentasPorMetodoPago();
        BigDecimal totalGeneral = obtenerTotalVentas();
        
        for (Map.Entry<String, List<Venta>> entry : porMetodo.entrySet()) {
            Map<String, Object> metodoInfo = new HashMap<>();
            int cantidad = entry.getValue().size();
            BigDecimal total = entry.getValue().stream()
                .map(Venta::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            metodoInfo.put("cantidad", cantidad);
            metodoInfo.put("total", total);
            metodoInfo.put("porcentaje", totalGeneral.compareTo(BigDecimal.ZERO) > 0 ? 
                total.doubleValue() / totalGeneral.doubleValue() * 100 : 0);
            
            resumen.put(entry.getKey(), metodoInfo);
        }
        return resumen;
    }
    
    public BigDecimal obtenerTotalVentas() {
        return datos.obtenerTodasVentas().stream()
            .map(Venta::getTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    // ========== REPORTES DE PRODUCTOS ==========
    
    public List<Map<String, Object>> obtenerProductosMasVendidos(int limite) {
        Map<String, Integer> cantidadVendida = new HashMap<>();
        Map<String, BigDecimal> totalVendido = new HashMap<>();
        
        for (Venta v : datos.obtenerTodasVentas()) {
            for (DetalleVenta dv : v.getDetalles()) {
                String nombre = dv.getProducto().getNombre();
                cantidadVendida.put(nombre, cantidadVendida.getOrDefault(nombre, 0) + dv.getCantidad());
                totalVendido.put(nombre, totalVendido.getOrDefault(nombre, BigDecimal.ZERO).add(dv.getSubtotal()));
            }
        }
        
        List<Map<String, Object>> resultado = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : cantidadVendida.entrySet()) {
            Map<String, Object> item = new HashMap<>();
            item.put("producto", entry.getKey());
            item.put("cantidad", entry.getValue());
            item.put("total", totalVendido.get(entry.getKey()));
            
            Producto p = buscarProductoPorNombre(entry.getKey());
            item.put("stockActual", p != null ? p.getCantidadAlmacen() : 0);
            resultado.add(item);
        }
        
        resultado.sort((a, b) -> ((Integer) b.get("cantidad")).compareTo((Integer) a.get("cantidad")));
        
        if (limite > 0 && limite < resultado.size()) {
            return resultado.subList(0, limite);
        }
        return resultado;
    }
    
    public Map<String, List<Producto>> obtenerProductosPorCategoria() {
        return datos.obtenerTodosProductos().stream()
            .collect(Collectors.groupingBy(p -> p.getCategoria() != null ? p.getCategoria() : "Sin Categoría"));
    }
    
    public List<Map<String, Object>> obtenerResumenPorCategoria() {
        Map<String, List<Producto>> porCategoria = obtenerProductosPorCategoria();
        List<Map<String, Object>> resumen = new ArrayList<>();
        
        for (Map.Entry<String, List<Producto>> entry : porCategoria.entrySet()) {
            Map<String, Object> catInfo = new HashMap<>();
            int stockTotal = 0;
            BigDecimal valorInventario = BigDecimal.ZERO;
            
            for (Producto p : entry.getValue()) {
                stockTotal += p.getCantidadAlmacen();
                valorInventario = valorInventario.add(p.getPrecioCompra().multiply(BigDecimal.valueOf(p.getCantidadAlmacen())));
            }
            
            catInfo.put("categoria", entry.getKey());
            catInfo.put("cantidadProductos", entry.getValue().size());
            catInfo.put("stockTotal", stockTotal);
            catInfo.put("valorInventario", valorInventario);
            
            resumen.add(catInfo);
        }
        return resumen;
    }
    
    // ========== REPORTES DE INVENTARIO ==========
    
    public List<Producto> obtenerProductosStockBajo() {
        return datos.obtenerProductosStockBajo();
    }
    
    public List<Producto> obtenerProductosAgotados() {
        return datos.obtenerTodosProductos().stream()
            .filter(p -> p.getCantidadAlmacen() == 0)
            .toList();
    }
    
    public BigDecimal obtenerValorInventario() {
        BigDecimal total = BigDecimal.ZERO;
        for (Producto p : datos.obtenerTodosProductos()) {
            total = total.add(p.getPrecioCompra().multiply(BigDecimal.valueOf(p.getCantidadAlmacen())));
        }
        return total;
    }
    
    public BigDecimal obtenerValorVentaPotencial() {
        BigDecimal total = BigDecimal.ZERO;
        for (Producto p : datos.obtenerTodosProductos()) {
            total = total.add(p.getPrecioVenta().multiply(BigDecimal.valueOf(p.getCantidadAlmacen())));
        }
        return total;
    }
    
    // ========== REPORTES DE UTILIDAD ==========
    
    public BigDecimal obtenerUtilidadTotal() {
        BigDecimal utilidad = BigDecimal.ZERO;
        for (Venta v : datos.obtenerTodasVentas()) {
            for (DetalleVenta dv : v.getDetalles()) {
                BigDecimal costo = dv.getProducto().getPrecioCompra().multiply(BigDecimal.valueOf(dv.getCantidad()));
                utilidad = utilidad.add(dv.getSubtotal().subtract(costo));
            }
        }
        return utilidad;
    }
    
    public BigDecimal obtenerUtilidadPorFecha(Date inicio, Date fin) {
        BigDecimal utilidad = BigDecimal.ZERO;
        for (Venta v : obtenerVentasPorFecha(inicio, fin)) {
            for (DetalleVenta dv : v.getDetalles()) {
                BigDecimal costo = dv.getProducto().getPrecioCompra().multiply(BigDecimal.valueOf(dv.getCantidad()));
                utilidad = utilidad.add(dv.getSubtotal().subtract(costo));
            }
        }
        return utilidad;
    }
    
    // ========== MÉTODOS AUXILIARES ==========
    
    private Producto buscarProductoPorNombre(String nombre) {
        for (Producto p : datos.obtenerTodosProductos()) {
            if (p.getNombre().equals(nombre)) {
                return p;
            }
        }
        return null;
    }
    
    public Map<String, Object> obtenerEstadisticasGenerales() {
        Map<String, Object> stats = new HashMap<>();
        
        List<Producto> productos = datos.obtenerTodosProductos();
        List<Venta> ventas = datos.obtenerTodasVentas();
        
        stats.put("totalProductos", productos.size());
        stats.put("productosStockBajo", productos.stream().filter(Producto::necesitaReponer).count());
        stats.put("productosAgotados", productos.stream().filter(p -> p.getCantidadAlmacen() == 0).count());
        stats.put("valorInventario", obtenerValorInventario());
        
        stats.put("totalVentas", ventas.size());
        stats.put("totalFacturado", obtenerTotalVentas());
        stats.put("ventasHoy", datos.obtenerTotalVentasHoy());
        stats.put("utilidadTotal", obtenerUtilidadTotal());
        stats.put("topProductos", obtenerProductosMasVendidos(5));
        
        return stats;
    }
}