package Controlador;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import BasedeDatos.ConexionBD;
import Modelo.Configuracion;

public class ControladorConfiguracion {
    
    public String getConfiguracion(String clave) {
        String sql = "SELECT valor FROM Configuracion WHERE clave = ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, clave);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getString("valor");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return "";
    }
    
    public boolean setConfiguracion(String clave, String valor) {
        String sql = "UPDATE Configuracion SET valor = ?, fecha_actualizacion = GETDATE() WHERE clave = ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, valor);
            stmt.setString(2, clave);
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Configuracion> getAllConfiguraciones() {
        List<Configuracion> lista = new ArrayList<>();
        String sql = "SELECT id, clave, valor, descripcion, fecha_actualizacion FROM Configuracion ORDER BY id";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Configuracion c = new Configuracion();
                c.setId(rs.getInt("id"));
                c.setClave(rs.getString("clave"));
                c.setValor(rs.getString("valor"));
                c.setDescripcion(rs.getString("descripcion"));
                c.setFechaActualizacion(rs.getTimestamp("fecha_actualizacion"));
                lista.add(c);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return lista;
    }
    
    public boolean respaldarBaseDatos(String rutaDestino) {
        String dbName = "AbarrotesFelipe";
        String backupPath = rutaDestino + "\\backup_" + dbName + "_" + 
                           new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".bak";
        
        String sql = "BACKUP DATABASE [" + dbName + "] TO DISK = ? WITH FORMAT, INIT, NAME = 'Full Backup'";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, backupPath);
            stmt.execute();
            return true;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean cambiarLogo(String rutaOrigen) {
        try {
            File origen = new File(rutaOrigen);
            String extension = rutaOrigen.substring(rutaOrigen.lastIndexOf("."));
            String nombreDestino = "logotipo" + extension;
            String rutaDestino = "ImagenesProductos/" + nombreDestino;
            
            File destino = new File(rutaDestino);
            java.nio.file.Files.copy(origen.toPath(), destino.toPath(), 
                                     java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            
            return setConfiguracion("empresa_logo_ruta", rutaDestino);
            
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}