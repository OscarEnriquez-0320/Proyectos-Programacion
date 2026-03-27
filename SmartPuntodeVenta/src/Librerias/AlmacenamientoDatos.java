package Librerias;

import Modelo.*;
import java.math.BigDecimal;
import java.util.*;

public class AlmacenamientoDatos {
    private static AlmacenamientoDatos instancia;
    
    private Map<Integer, Producto> productos;
    private Map<Integer, Proveedor> proveedores;
    private Map<Integer, UnidadMedida> unidades;
    private List<Venta> ventas;
    private int siguienteIdProducto;
    private int siguienteIdProveedor;
    private int siguienteIdVenta;
    private int siguienteNumeroFactura;
    private Empresa empresa;
    private AlmacenamientoDatos() {
        productos = new HashMap<>();
        proveedores = new HashMap<>();
        unidades = new HashMap<>();
        ventas = new ArrayList<>();
        siguienteIdProducto = 1;
        siguienteIdProveedor = 1;
        siguienteIdVenta = 1;
        siguienteNumeroFactura = 1;
        this.empresa = new Empresa();
        this.empresa.setNombre("Mi Tienda de Abarrotes");
        this.empresa.setEslogan("¡Siempre frescos, siempre cerca!");
        inicializarUnidades();
        inicializarProveedoresEjemplo();
        inicializarProductosEjemplo();
    }
    
    public static AlmacenamientoDatos getInstancia() {
        if (instancia == null) {
            instancia = new AlmacenamientoDatos();
          
        }
        return instancia;
    }
    
    private void inicializarUnidades() {
        unidades.put(1, new UnidadMedida(1, "Unidad", "UND"));
        unidades.put(2, new UnidadMedida(2, "Kilogramo", "KG"));
        unidades.put(3, new UnidadMedida(3, "Gramo", "GR"));
        unidades.put(4, new UnidadMedida(4, "Litro", "L"));
        unidades.put(5, new UnidadMedida(5, "Mililitro", "ML"));
        unidades.put(6, new UnidadMedida(6, "Libra", "LB"));
        unidades.put(7, new UnidadMedida(7, "Paquete", "PQT"));
        unidades.put(8, new UnidadMedida(8, "Bolsa", "BLS"));
    }
    
    private void inicializarProveedoresEjemplo() {
        Proveedor p1 = new Proveedor(siguienteIdProveedor++, "Distribuidora La Central", "2233-4455", "ventas@central.com");
        p1.setRtn("08011999001234");
        p1.setDireccion("Colonia Centro, Calle Principal #123");
        p1.setNombreContacto("Juan Pérez");
        proveedores.put(p1.getId(), p1);
        
        Proveedor p2 = new Proveedor(siguienteIdProveedor++, "Alimentos S.A.", "2255-6677", "ventas@alimentos.com");
        p2.setRtn("08011999005678");
        p2.setDireccion("Zona Industrial, Bodega #45");
        p2.setNombreContacto("María López");
        proveedores.put(p2.getId(), p2);
        
        Proveedor p3 = new Proveedor(siguienteIdProveedor++, "Abarrotes Don Pepe", "2277-8899", "donpepe@abarrotes.com");
        p3.setRtn("08011999009988");
        p3.setDireccion("Mercado Central, Local #15");
        p3.setNombreContacto("José Rodríguez");
        proveedores.put(p3.getId(), p3);
    }
    
    private void inicializarProductosEjemplo() {
        // Arroz
        Producto arroz = new Producto(siguienteIdProducto++, "001", "Arroz", BigDecimal.valueOf(25.00), 
                                       BigDecimal.valueOf(30), 50, unidades.get(6));
        arroz.setCategoria("Granos Básicos");
        arroz.setUbicacion("Estante A1");
        arroz.setProveedor(proveedores.get(1));
        productos.put(arroz.getId(), arroz);
        
        // Frijoles
        Producto frijoles = new Producto(siguienteIdProducto++, "002", "Frijoles Rojos", BigDecimal.valueOf(28.00), 
                                          BigDecimal.valueOf(30), 40, unidades.get(6));
        frijoles.setCategoria("Granos Básicos");
        frijoles.setUbicacion("Estante A2");
        frijoles.setProveedor(proveedores.get(1));
        productos.put(frijoles.getId(), frijoles);
        
        // Azúcar
        Producto azucar = new Producto(siguienteIdProducto++, "003", "Azúcar Blanca", BigDecimal.valueOf(22.00), 
                                        BigDecimal.valueOf(30), 35, unidades.get(6));
        azucar.setCategoria("Dulces y Endulzantes");
        azucar.setUbicacion("Estante B1");
        azucar.setProveedor(proveedores.get(2));
        productos.put(azucar.getId(), azucar);
        
        // Aceite
        Producto aceite = new Producto(siguienteIdProducto++, "004", "Aceite Vegetal 1L", BigDecimal.valueOf(45.00), 
                                        BigDecimal.valueOf(30), 25, unidades.get(4));
        aceite.setCategoria("Aceites y Grasas");
        aceite.setUbicacion("Estante C1");
        aceite.setProveedor(proveedores.get(2));
        productos.put(aceite.getId(), aceite);
        
        // Harina
        Producto harina = new Producto(siguienteIdProducto++, "005", "Harina de Trigo", BigDecimal.valueOf(18.00), 
                                        BigDecimal.valueOf(30), 60, unidades.get(6));
        harina.setCategoria("Harinas");
        harina.setUbicacion("Estante D1");
        harina.setProveedor(proveedores.get(1));
        productos.put(harina.getId(), harina);
        
        // Leche
        Producto leche = new Producto(siguienteIdProducto++, "006", "Leche Pasteurizada 1L", BigDecimal.valueOf(32.00), 
                                       BigDecimal.valueOf(30), 20, unidades.get(4));
        leche.setCategoria("Lácteos");
        leche.setUbicacion("Refrigerador 1");
        leche.setProveedor(proveedores.get(2));
        productos.put(leche.getId(), leche);
        
        // Café
        Producto cafe = new Producto(siguienteIdProducto++, "007", "Café Molido 500g", BigDecimal.valueOf(85.00), 
                                      BigDecimal.valueOf(30), 30, unidades.get(2));
        cafe.setCategoria("Bebidas");
        cafe.setUbicacion("Estante E1");
        cafe.setProveedor(proveedores.get(1));
        productos.put(cafe.getId(), cafe);
        
        // Galletas
        Producto galletas = new Producto(siguienteIdProducto++, "008", "Galletas Dulces", BigDecimal.valueOf(15.00), 
                                          BigDecimal.valueOf(30), 45, unidades.get(7));
        galletas.setCategoria("Snacks");
        galletas.setUbicacion("Estante F1");
        galletas.setProveedor(proveedores.get(2));
        productos.put(galletas.getId(), galletas);
        
        // Atún
        Producto atun = new Producto(siguienteIdProducto++, "009", "Atún en Aceite", BigDecimal.valueOf(18.00), 
                                      BigDecimal.valueOf(30), 55, unidades.get(1));
        atun.setCategoria("Enlatados");
        atun.setUbicacion("Estante G1");
        atun.setProveedor(proveedores.get(3));
        productos.put(atun.getId(), atun);
        
        // Pasta
        Producto pasta = new Producto(siguienteIdProducto++, "010", "Spaghetti", BigDecimal.valueOf(12.00), 
                                       BigDecimal.valueOf(30), 70, unidades.get(7));
        pasta.setCategoria("Pastas");
        pasta.setUbicacion("Estante H1");
        pasta.setProveedor(proveedores.get(3));
        productos.put(pasta.getId(), pasta);
    }
    
    // ========== MÉTODOS PARA PRODUCTOS ==========
    public List<Producto> obtenerTodosProductos() {
        return new ArrayList<>(productos.values());
    }
    
    public Producto obtenerProductoPorId(int id) {
        return productos.get(id);
    }
    
    public Producto obtenerProductoPorCodigo(String codigo) {
        for (Producto p : productos.values()) {
            if (p.getCodigo().equals(codigo)) {
                return p;
            }
        }
        return null;
    }
    
    public void agregarProducto(Producto producto) {
        producto.setId(siguienteIdProducto++);
        productos.put(producto.getId(), producto);
    }
    
    public void actualizarProducto(Producto producto) {
        productos.put(producto.getId(), producto);
    }
    
    public void eliminarProducto(int id) {
        productos.remove(id);
    }
    
    // ========== MÉTODOS PARA PROVEEDORES ==========
    public List<Proveedor> obtenerTodosProveedores() {
        return new ArrayList<>(proveedores.values());
    }
    
    public Proveedor obtenerProveedorPorId(int id) {
        return proveedores.get(id);
    }
    
    public void agregarProveedor(Proveedor proveedor) {
        proveedor.setId(siguienteIdProveedor++);
        proveedores.put(proveedor.getId(), proveedor);
    }
    
    public void actualizarProveedor(Proveedor proveedor) {
        proveedores.put(proveedor.getId(), proveedor);
    }
    
    public void eliminarProveedor(int id) {
        proveedores.remove(id);
    }
    
    // ========== MÉTODOS PARA UNIDADES ==========
    public List<UnidadMedida> obtenerTodasUnidades() {
        return new ArrayList<>(unidades.values());
    }
    
    public UnidadMedida obtenerUnidadPorId(int id) {
        return unidades.get(id);
    }
    
    // ========== MÉTODOS PARA VENTAS ==========
    public List<Venta> obtenerTodasVentas() {
        return new ArrayList<>(ventas);
    }
    
    public void agregarVenta(Venta venta) {
        venta.setId(siguienteIdVenta++);
        venta.setNumeroFactura(String.format("FAC-%06d", siguienteNumeroFactura++));
        
        // Actualizar inventario - restar stock
        for (DetalleVenta detalle : venta.getDetalles()) {
            Producto p = detalle.getProducto();
            int nuevoStock = p.getCantidadAlmacen() - detalle.getCantidad();
            if (nuevoStock < 0) {
                throw new IllegalStateException("Stock insuficiente para: " + p.getNombre());
            }
            p.setCantidadAlmacen(nuevoStock);
            actualizarProducto(p);
        }
        
        ventas.add(venta);
    }
    
    public List<Producto> obtenerProductosStockBajo() {
        List<Producto> bajoStock = new ArrayList<>();
        for (Producto p : productos.values()) {
            if (p.necesitaReponer()) {
                bajoStock.add(p);
            }
        }
        return bajoStock;
    }
    
    public BigDecimal obtenerTotalVentasHoy() {
        Date hoy = new Date();
        BigDecimal total = BigDecimal.ZERO;
        for (Venta v : ventas) {
            long diferencia = hoy.getTime() - v.getFecha().getTime();
            if (diferencia < 24 * 60 * 60 * 1000) {
                total = total.add(v.getTotal());
            }
        }
        return total;
    }
    
    public int obtenerCantidadVentasHoy() {
        Date hoy = new Date();
        int cantidad = 0;
        for (Venta v : ventas) {
            long diferencia = hoy.getTime() - v.getFecha().getTime();
            if (diferencia < 24 * 60 * 60 * 1000) {
                cantidad++;
            }
        }
        return cantidad;
    }
    public Empresa obtenerEmpresa() {
        return empresa;
    }

    public void actualizarEmpresa(Empresa nuevaEmpresa) {
        this.empresa = nuevaEmpresa;
    }
}