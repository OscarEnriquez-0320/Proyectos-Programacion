package Controlador;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import BasedeDatos.ConexionBD;
import Modelo.Venta;

public class ControladorReporte {
    

    public List<Object[]> getReporteVentas(String fechaInicio, String fechaFin) {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT v.id, v.folio, v.fecha, v.total, v.metodo_pago, u.nombre_usuario " +
                     "FROM Venta v " +
                     "LEFT JOIN Usuario u ON v.usuario_id = u.id " +
                     "WHERE CAST(v.fecha AS DATE) BETWEEN ? AND ? AND v.cancelada = 0 " +
                     "ORDER BY v.fecha DESC";
        
        SimpleDateFormat sdfFecha = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm:ss");
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, fechaInicio);
            stmt.setString(2, fechaFin);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Timestamp fecha = rs.getTimestamp("fecha");
                Object[] fila = new Object[7];
                fila[0] = rs.getInt("id");
                fila[1] = rs.getString("folio");
                fila[2] = fecha != null ? sdfFecha.format(fecha) : "";
                fila[3] = fecha != null ? sdfHora.format(fecha) : "";
                fila[4] = rs.getBigDecimal("total");
                fila[5] = rs.getString("metodo_pago");
                fila[6] = rs.getString("nombre_usuario");
                lista.add(fila);
            }
            rs.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return lista;
    }
    

    public BigDecimal getTotalVentasPeriodo(String fechaInicio, String fechaFin) {
        String sql = "SELECT ISNULL(SUM(total), 0) as total FROM Venta WHERE CAST(fecha AS DATE) BETWEEN ? AND ? AND cancelada = 0";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, fechaInicio);
            stmt.setString(2, fechaFin);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getBigDecimal("total");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return BigDecimal.ZERO;
    }
    

    public List<Object[]> getProductosStockBajo() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, stock, stock_minimo, categoria, tipo_venta " +
                     "FROM Producto " +
                     "WHERE stock <= stock_minimo AND activo = 1 " +
                     "ORDER BY stock ASC";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Object[] fila = new Object[6];
                fila[0] = rs.getInt("id");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getDouble("stock");
                fila[3] = rs.getDouble("stock_minimo");
                fila[4] = rs.getString("categoria");
                fila[5] = rs.getString("tipo_venta");
                lista.add(fila);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return lista;
    }
    

    public Venta obtenerVentaConDetalles(int idVenta) {
        Venta venta = null;
        
        String sqlVenta = "SELECT v.id, v.folio, v.fecha, v.total, v.metodo_pago, u.nombre_usuario, v.cancelada " +
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
            }
            rs.close();
            
            if (venta != null) {
                List<Modelo.DetalleVenta> detalles = new ArrayList<>();
                String sqlDetalles = "SELECT p.nombre, dv.cantidad, dv.precio_unitario, dv.subtotal " +
                                     "FROM DetalleVenta dv " +
                                     "LEFT JOIN Producto p ON dv.producto_id = p.id " +
                                     "WHERE dv.venta_id = ?";
                
                PreparedStatement stmtDetalle = conn.prepareStatement(sqlDetalles);
                stmtDetalle.setInt(1, idVenta);
                ResultSet rsDetalle = stmtDetalle.executeQuery();
                
                while (rsDetalle.next()) {
                    Modelo.DetalleVenta d = new Modelo.DetalleVenta();
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

    public String obtenerRutaTicket(String folio) {
        String rutaTicket = "tickets/ticket_" + folio + ".pdf";
        File archivoTicket = new File(rutaTicket);
        
        if (!archivoTicket.exists()) {
 
            return null;
        }
        
        return rutaTicket;
    }

    public String generarTicketSiNoExiste(Venta venta, String folio) {
        String rutaTicket = "tickets/ticket_" + folio + ".pdf";
        File archivoTicket = new File(rutaTicket);
        
        if (!archivoTicket.exists()) {
            ControladorTicket controladorTicket = new ControladorTicket();
            boolean generado = controladorTicket.generarTicketPDF(venta, venta.getDetalles(), rutaTicket);
            if (generado) {
                return rutaTicket;
            } else {
                return null;
            }
        }
        
        return rutaTicket;
    }
    

    public List<Object[]> getProductosMasVendidos(String fechaInicio, String fechaFin, int limite) {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT TOP " + limite + 
                     " p.id, p.nombre, SUM(dv.cantidad) as total_vendido, SUM(dv.subtotal) as total_venta, p.categoria, p.tipo_venta " +
                     "FROM DetalleVenta dv " +
                     "LEFT JOIN Producto p ON dv.producto_id = p.id " +
                     "LEFT JOIN Venta v ON dv.venta_id = v.id " +
                     "WHERE v.cancelada = 0 AND CAST(v.fecha AS DATE) BETWEEN ? AND ? " +
                     "GROUP BY p.id, p.nombre, p.categoria, p.tipo_venta " +
                     "ORDER BY total_vendido DESC";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, fechaInicio);
            stmt.setString(2, fechaFin);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Object[] fila = new Object[6];
                fila[0] = rs.getInt("id");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getDouble("total_vendido");
                fila[3] = rs.getBigDecimal("total_venta");
                fila[4] = rs.getString("categoria");
                fila[5] = rs.getString("tipo_venta");
                lista.add(fila);
            }
            rs.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return lista;
    }
    
    
   
    public boolean exportarACSV(String ruta, String[][] datos, String[] encabezados) {
        try (FileWriter writer = new FileWriter(ruta)) {
            for (int i = 0; i < encabezados.length; i++) {
                writer.write(encabezados[i]);
                if (i < encabezados.length - 1) writer.write(",");
            }
            writer.write("\n");
            
            for (String[] fila : datos) {
                for (int i = 0; i < fila.length; i++) {
                    writer.write(fila[i]);
                    if (i < fila.length - 1) writer.write(",");
                }
                writer.write("\n");
            }
            
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}