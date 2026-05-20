package Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import BasedeDatos.ConexionBD;

public class ControladorCaja {
    
    public static class CorteInfo {
        public int id;
        public String fechaApertura;
        public double montoApertura;
        public double montoVentasEfectivo;
        public String usuarioApertura;
    }
    
    public boolean abrirCaja(int idUsuario, double montoApertura) {
        String sql = "INSERT INTO CorteCaja (fecha_apertura, monto_apertura, monto_ventas_efectivo, usuario_apertura_id) VALUES (GETDATE(), ?, 0, ?)";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDouble(1, montoApertura);
            stmt.setInt(2, idUsuario);
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean hayCajaAbierta() {
        String sql = "SELECT TOP 1 id FROM CorteCaja WHERE fecha_cierre IS NULL ORDER BY fecha_apertura DESC";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            return rs.next();
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public int obtenerIdCorteAbierto() {
        String sql = "SELECT TOP 1 id FROM CorteCaja WHERE fecha_cierre IS NULL ORDER BY fecha_apertura DESC";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt("id");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return -1;
    }
    
    public CorteInfo obtenerCajaAbiertaInfo() {
        String sql = "SELECT TOP 1 c.id, c.fecha_apertura, c.monto_apertura, c.monto_ventas_efectivo, u.nombre_completo " +
                     "FROM CorteCaja c " +
                     "LEFT JOIN Usuario u ON c.usuario_apertura_id = u.id " +
                     "WHERE c.fecha_cierre IS NULL " +
                     "ORDER BY c.fecha_apertura DESC";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                CorteInfo info = new CorteInfo();
                info.id = rs.getInt("id");
                info.fechaApertura = rs.getString("fecha_apertura");
                info.montoApertura = rs.getDouble("monto_apertura");
                info.montoVentasEfectivo = rs.getDouble("monto_ventas_efectivo");
                info.usuarioApertura = rs.getString("nombre_completo");
                return info;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public double getMontoEsperado(int idCorte) {
        String sql = "SELECT (monto_apertura + monto_ventas_efectivo) as total FROM CorteCaja WHERE id = ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idCorte);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble("total");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0;
    }
    
    public boolean cerrarCaja(int idCorte, double montoCierre, int idUsuario) {
        String sql = "UPDATE CorteCaja SET fecha_cierre = GETDATE(), monto_cierre = ?, usuario_cierre_id = ? WHERE id = ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDouble(1, montoCierre);
            stmt.setInt(2, idUsuario);
            stmt.setInt(3, idCorte);
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}