package Controlador;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import BasedeDatos.ConexionBD;
import Modelo.DetalleVenta;
import Modelo.Producto;
import Modelo.Venta;

public class ControladorVenta {
    
    private List<ItemCarrito> carrito = new ArrayList<>();
    

    
    public void limpiarCarrito() {
        carrito.clear();
    }
    
    public boolean carritoVacio() {
        return carrito.isEmpty();
    }
    
    public BigDecimal getTotalCarrito() {
        BigDecimal total = BigDecimal.ZERO;
        for (ItemCarrito item : carrito) {
            total = total.add(item.getSubtotal());
        }
        return total;
    }
    
    public List<ItemCarrito> getCarrito() {
        return carrito;
    }
    
    public String agregarAlCarrito(Producto producto, double cantidad) {
        if (producto == null) {
            return "Seleccione un producto";
        }
        
        if (cantidad <= 0) {
            return "La cantidad debe ser mayor a cero";
        }
        
        if (cantidad > producto.getStock()) {
            return "Stock insuficiente. Stock disponible: " + producto.getStock();
        }
        
        String unidad = "";
        BigDecimal precioUnitario = producto.getPrecio();
        BigDecimal subtotal;
        
        if (producto.getTipoVenta() != null) {
            switch (producto.getTipoVenta()) {
                case "Peso":
                    unidad = " Kg";
                    subtotal = precioUnitario.multiply(BigDecimal.valueOf(cantidad)).setScale(2, RoundingMode.HALF_UP);
                    break;
                case "Granel":
                    unidad = " Lts";
                    subtotal = precioUnitario.multiply(BigDecimal.valueOf(cantidad)).setScale(2, RoundingMode.HALF_UP);
                    break;
                default:
                    unidad = "";
                    subtotal = precioUnitario.multiply(BigDecimal.valueOf(cantidad)).setScale(2, RoundingMode.HALF_UP);
                    break;
            }
        } else {
            subtotal = precioUnitario.multiply(BigDecimal.valueOf(cantidad)).setScale(2, RoundingMode.HALF_UP);
        }
        
        for (ItemCarrito item : carrito) {
            if (item.getIdProducto() == producto.getId()) {
                double nuevaCantidad = item.getCantidad() + cantidad;
                if (nuevaCantidad > producto.getStock()) {
                    return "Stock insuficiente. Stock disponible: " + producto.getStock();
                }
                item.setCantidad(nuevaCantidad);
                BigDecimal nuevoSubtotal = item.getPrecio().multiply(BigDecimal.valueOf(nuevaCantidad)).setScale(2, RoundingMode.HALF_UP);
                item.setSubtotal(nuevoSubtotal);
                return "Producto agregado al carrito";
            }
        }
        
        ItemCarrito nuevo = new ItemCarrito();
        nuevo.setIdProducto(producto.getId());
        String tipoTexto = producto.getTipoVenta() != null ? " (" + producto.getTipoVenta() + ")" : "";
        nuevo.setNombre(producto.getNombre() + tipoTexto);
        nuevo.setCantidad(cantidad);
        nuevo.setUnidad(unidad);
        nuevo.setPrecio(precioUnitario);
        nuevo.setSubtotal(subtotal);
        carrito.add(nuevo);
        
        return "Producto agregado al carrito";
    }
    

    
    public String generarFolio() {
        return "F-" + System.currentTimeMillis();
    }
    
    public boolean guardarVenta(Venta venta, List<DetalleVenta> detalles) {
        Connection conn = null;
        
        try {
            conn = ConexionBD.obtenerConexion();
            conn.setAutoCommit(false);
            
            String sqlVenta = "INSERT INTO Venta (folio, fecha, total, metodo_pago, usuario_id, efectivo_recibido, cambio, cancelada, descuento, impuesto, corte_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmtVenta = conn.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS);
            
            stmtVenta.setString(1, venta.getFolio());
            stmtVenta.setTimestamp(2, new Timestamp(venta.getFecha().getTime()));
            stmtVenta.setBigDecimal(3, venta.getTotal());
            stmtVenta.setString(4, venta.getMetodoPago());
            stmtVenta.setInt(5, venta.getIdUsuario());
            stmtVenta.setBigDecimal(6, venta.getEfectivoRecibido() != null ? venta.getEfectivoRecibido() : BigDecimal.ZERO);
            stmtVenta.setBigDecimal(7, venta.getCambio() != null ? venta.getCambio() : BigDecimal.ZERO);
            stmtVenta.setBoolean(8, venta.isCancelada());
            stmtVenta.setBigDecimal(9, venta.getDescuento() != null ? venta.getDescuento() : BigDecimal.ZERO);
            stmtVenta.setBigDecimal(10, venta.getImpuesto() != null ? venta.getImpuesto() : BigDecimal.ZERO);
            stmtVenta.setInt(11, venta.getIdCorte());
            
            stmtVenta.executeUpdate();
            
            ResultSet rs = stmtVenta.getGeneratedKeys();
            int idVenta = 0;
            if (rs.next()) {
                idVenta = rs.getInt(1);
            }
            rs.close();
            stmtVenta.close();
            
            if (idVenta == 0) {
                conn.rollback();
                return false;
            }
            
            String sqlDetalle = "INSERT INTO DetalleVenta (venta_id, producto_id, cantidad, precio_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmtDetalle = conn.prepareStatement(sqlDetalle);
            
            String sqlUpdateStock = "UPDATE Producto SET stock = stock - ? WHERE id = ?";
            PreparedStatement stmtStock = conn.prepareStatement(sqlUpdateStock);
            
            for (DetalleVenta detalle : detalles) {
                stmtDetalle.setInt(1, idVenta);
                stmtDetalle.setInt(2, detalle.getIdProducto());
                stmtDetalle.setDouble(3, detalle.getCantidad());
                stmtDetalle.setBigDecimal(4, detalle.getPrecioUnitario());
                stmtDetalle.setBigDecimal(5, detalle.getSubtotal());
                stmtDetalle.addBatch();
                
                stmtStock.setDouble(1, detalle.getCantidad());
                stmtStock.setInt(2, detalle.getIdProducto());
                stmtStock.addBatch();
            }
            
            stmtDetalle.executeBatch();
            stmtStock.executeBatch();
            stmtDetalle.close();
            stmtStock.close();
            
            if (venta.getMetodoPago().equals("Efectivo")) {
                String sqlCorte = "UPDATE CorteCaja SET monto_ventas_efectivo = monto_ventas_efectivo + ? WHERE id = ?";
                PreparedStatement stmtCorte = conn.prepareStatement(sqlCorte);
                stmtCorte.setBigDecimal(1, venta.getTotal());
                stmtCorte.setInt(2, venta.getIdCorte());
                stmtCorte.executeUpdate();
                stmtCorte.close();
            }
            
            conn.commit();
            return true;
            
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public ArrayList<Venta> listarVentas(String filtro, int idUsuario, boolean esAdmin) {
        ArrayList<Venta> lista = new ArrayList<Venta>();
        String sql = "SELECT v.id, v.folio, v.fecha, v.total, v.metodo_pago, u.nombre_usuario, v.cancelada " +
                     "FROM Venta v " +
                     "LEFT JOIN Usuario u ON v.usuario_id = u.id " +
                     "WHERE v.cancelada = 0 ";
        
        if (!esAdmin) {
            sql += "AND v.usuario_id = " + idUsuario + " ";
        }
        
        if (filtro.equals("HOY")) {
            sql += "AND CAST(v.fecha AS DATE) = CAST(GETDATE() AS DATE) ";
        } else if (filtro.equals("ESTA SEMANA")) {
            sql += "AND v.fecha >= DATEADD(day, -7, GETDATE()) ";
        } else if (filtro.equals("ESTE MES")) {
            sql += "AND MONTH(v.fecha) = MONTH(GETDATE()) AND YEAR(v.fecha) = YEAR(GETDATE()) ";
        }
        
        sql += "ORDER BY v.fecha DESC";
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Venta v = new Venta();
                v.setId(rs.getInt("id"));
                v.setFolio(rs.getString("folio"));
                v.setFecha(rs.getTimestamp("fecha"));
                v.setTotal(rs.getBigDecimal("total"));
                v.setMetodoPago(rs.getString("metodo_pago"));
                v.setNombreUsuario(rs.getString("nombre_usuario"));
                v.setCancelada(rs.getBoolean("cancelada"));
                lista.add(v);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return lista;
    }
    
    public Venta obtenerVentaConDetalles(int idVenta) {
        Venta venta = null;
        
        String sqlVenta = "SELECT v.id, v.folio, v.fecha, v.total, v.metodo_pago, u.nombre_usuario, v.cancelada, v.usuario_id " +
                          "FROM Venta v " +
                          "LEFT JOIN Usuario u ON v.usuario_id = u.id " +
                          "WHERE v.id = ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sqlVenta)) {
            
            stmt.setInt(1, idVenta);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                venta = new Venta();
                venta.setId(rs.getInt("id"));
                venta.setFolio(rs.getString("folio"));
                venta.setFecha(rs.getTimestamp("fecha"));
                venta.setTotal(rs.getBigDecimal("total"));
                venta.setMetodoPago(rs.getString("metodo_pago"));
                venta.setNombreUsuario(rs.getString("nombre_usuario"));
                venta.setCancelada(rs.getBoolean("cancelada"));
                venta.setIdUsuario(rs.getInt("usuario_id"));
            }
            rs.close();
            
            if (venta != null) {
                List<DetalleVenta> detalles = new ArrayList<>();
                String sqlDetalles = "SELECT p.nombre, dv.cantidad, dv.precio_unitario, dv.subtotal " +
                                     "FROM DetalleVenta dv " +
                                     "LEFT JOIN Producto p ON dv.producto_id = p.id " +
                                     "WHERE dv.venta_id = ?";
                
                PreparedStatement stmtDetalle = conn.prepareStatement(sqlDetalles);
                stmtDetalle.setInt(1, idVenta);
                ResultSet rsDetalle = stmtDetalle.executeQuery();
                
                while (rsDetalle.next()) {
                    DetalleVenta d = new DetalleVenta();
                    d.setNombreProducto(rsDetalle.getString("nombre"));
                    d.setCantidad(rsDetalle.getDouble("cantidad"));
                    d.setPrecioUnitario(rsDetalle.getBigDecimal("precio_unitario"));
                    d.setSubtotal(rsDetalle.getBigDecimal("subtotal"));
                    detalles.add(d);
                }
                rsDetalle.close();
                stmtDetalle.close();
                venta.setDetalles(detalles);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return venta;
    }
    
    public boolean cancelarVenta(int idVenta) {
        String sql = "UPDATE Venta SET cancelada = 1 WHERE id = ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idVenta);
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
 
    
    public class ItemCarrito {
        private int idProducto;
        private String nombre;
        private double cantidad;
        private String unidad;
        private BigDecimal precio;
        private BigDecimal subtotal;
        
        public ItemCarrito() {}
        
        public int getIdProducto() { return idProducto; }
        public void setIdProducto(int idProducto) { this.idProducto = idProducto; }
        
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        
        public double getCantidad() { return cantidad; }
        public void setCantidad(double cantidad) { this.cantidad = cantidad; }
        
        public String getUnidad() { return unidad; }
        public void setUnidad(String unidad) { this.unidad = unidad; }
        
        public BigDecimal getPrecio() { return precio; }
        public void setPrecio(BigDecimal precio) { this.precio = precio; }
        
        public BigDecimal getSubtotal() { return subtotal; }
        public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
        
        public String getCantidadTexto() {
            if (unidad != null && !unidad.isEmpty()) {
                return cantidad + " " + unidad;
            }
            return String.valueOf((int) cantidad);
        }
    }
}