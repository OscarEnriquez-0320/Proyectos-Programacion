package Controlador;

import Librerias.AlmacenamientoDatos;
import Modelo.Producto;
import Modelo.Proveedor;
import Modelo.UnidadMedida;
import java.math.BigDecimal;
import java.util.List;

public class ProductoControlador {
    private AlmacenamientoDatos datos;
    
    public ProductoControlador() {
        this.datos = AlmacenamientoDatos.getInstancia();
    }
    
    // Obtener todos los productos
    public List<Producto> obtenerTodosProductos() {
        return datos.obtenerTodosProductos();
    }
    
    // Obtener producto por ID
    public Producto obtenerProductoPorId(int id) {
        return datos.obtenerProductoPorId(id);
    }
    
    // Obtener producto por código
    public Producto obtenerProductoPorCodigo(String codigo) {
        return datos.obtenerProductoPorCodigo(codigo);
    }
    
    // Crear nuevo producto
    public boolean crearProducto(String codigo, String nombre, String descripcion, 
                                  BigDecimal precioCompra, BigDecimal porcentajeGanancia,
                                  int cantidadAlmacen, int cantidadMinima, 
                                  UnidadMedida unidad, Proveedor proveedor,
                                  String categoria, String ubicacion) {
        try {
            if (codigo == null || codigo.trim().isEmpty()) {
                throw new IllegalArgumentException("El código es obligatorio");
            }
            if (nombre == null || nombre.trim().isEmpty()) {
                throw new IllegalArgumentException("El nombre es obligatorio");
            }
            if (precioCompra == null || precioCompra.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("El precio de compra debe ser mayor a 0");
            }
            
            if (datos.obtenerProductoPorCodigo(codigo) != null) {
                throw new IllegalArgumentException("Ya existe un producto con este código");
            }
            
            Producto nuevo = new Producto();
            nuevo.setCodigo(codigo);
            nuevo.setNombre(nombre);
            nuevo.setDescripcion(descripcion);
            nuevo.setPrecioCompra(precioCompra);
            nuevo.setPorcentajeGanancia(porcentajeGanancia);
            nuevo.setCantidadAlmacen(cantidadAlmacen);
            nuevo.setCantidadMinima(cantidadMinima);
            nuevo.setUnidad(unidad);
            nuevo.setProveedor(proveedor);
            nuevo.setCategoria(categoria);
            nuevo.setUbicacion(ubicacion);
            nuevo.calcularPrecioVenta();
            
            datos.agregarProducto(nuevo);
            return true;
        } catch (Exception e) {
            System.err.println("Error al crear producto: " + e.getMessage());
            return false;
        }
    }
    
    // Actualizar producto existente
    public boolean actualizarProducto(int id, String codigo, String nombre, String descripcion,
                                       BigDecimal precioCompra, BigDecimal porcentajeGanancia,
                                       int cantidadAlmacen, int cantidadMinima,
                                       UnidadMedida unidad, Proveedor proveedor,
                                       String categoria, String ubicacion) {
        try {
            Producto existente = datos.obtenerProductoPorId(id);
            if (existente == null) {
                throw new IllegalArgumentException("Producto no encontrado");
            }
            
            Producto productoMismoCodigo = datos.obtenerProductoPorCodigo(codigo);
            if (productoMismoCodigo != null && productoMismoCodigo.getId() != id) {
                throw new IllegalArgumentException("Ya existe otro producto con este código");
            }
            
            existente.setCodigo(codigo);
            existente.setNombre(nombre);
            existente.setDescripcion(descripcion);
            existente.setPrecioCompra(precioCompra);
            existente.setPorcentajeGanancia(porcentajeGanancia);
            existente.setCantidadAlmacen(cantidadAlmacen);
            existente.setCantidadMinima(cantidadMinima);
            existente.setUnidad(unidad);
            existente.setProveedor(proveedor);
            existente.setCategoria(categoria);
            existente.setUbicacion(ubicacion);
            existente.calcularPrecioVenta();
            
            datos.actualizarProducto(existente);
            return true;
        } catch (Exception e) {
            System.err.println("Error al actualizar producto: " + e.getMessage());
            return false;
        }
    }
    
    // Eliminar producto
    public boolean eliminarProducto(int id) {
        try {
            Producto producto = datos.obtenerProductoPorId(id);
            if (producto == null) {
                throw new IllegalArgumentException("Producto no encontrado");
            }
            
            boolean tieneVentas = datos.obtenerTodasVentas().stream()
                .flatMap(venta -> venta.getDetalles().stream())
                .anyMatch(detalle -> detalle.getProducto().getId() == id);
            
            if (tieneVentas) {
                throw new IllegalArgumentException("No se puede eliminar un producto que tiene ventas registradas");
            }
            
            datos.eliminarProducto(id);
            return true;
        } catch (Exception e) {
            System.err.println("Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }
    
    // Ajustar stock
    public boolean ajustarStock(int id, int nuevoStock, String motivo, String observacion) {
        try {
            Producto producto = datos.obtenerProductoPorId(id);
            if (producto == null) {
                throw new IllegalArgumentException("Producto no encontrado");
            }
            
            if (nuevoStock < 0) {
                throw new IllegalArgumentException("El stock no puede ser negativo");
            }
            
            producto.setCantidadAlmacen(nuevoStock);
            datos.actualizarProducto(producto);
            return true;
        } catch (Exception e) {
            System.err.println("Error al ajustar stock: " + e.getMessage());
            return false;
        }
    }
    
    // Obtener productos con stock bajo
    public List<Producto> obtenerProductosStockBajo() {
        return datos.obtenerProductosStockBajo();
    }
    
    // Buscar productos
    public List<Producto> buscarProductos(String criterio) {
        if (criterio == null || criterio.trim().isEmpty()) {
            return datos.obtenerTodosProductos();
        }
        
        String busqueda = criterio.toLowerCase().trim();
        return datos.obtenerTodosProductos().stream()
            .filter(p -> p.getNombre().toLowerCase().contains(busqueda) ||
                        p.getCodigo().toLowerCase().contains(busqueda) ||
                        (p.getCategoria() != null && p.getCategoria().toLowerCase().contains(busqueda)))
            .toList();
    }
    
    // Calcular valor total del inventario
    public BigDecimal calcularValorInventario() {
        BigDecimal total = BigDecimal.ZERO;
        for (Producto p : datos.obtenerTodosProductos()) {
            total = total.add(p.getPrecioCompra().multiply(BigDecimal.valueOf(p.getCantidadAlmacen())));
        }
        return total;
    }
    
    // Obtener todas las unidades de medida
    public List<UnidadMedida> obtenerTodasUnidades() {
        return datos.obtenerTodasUnidades();
    }
}