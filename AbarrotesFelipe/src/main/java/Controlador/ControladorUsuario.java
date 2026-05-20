package Controlador;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import BasedeDatos.ConexionBD;
import Modelo.Usuario;

public class ControladorUsuario {
    
    public ArrayList<Usuario> listarTodos() {
        ArrayList<Usuario> lista = new ArrayList<Usuario>();
        String sql = "SELECT id, nombre_usuario, nombre_completo, rol, permisos, ruta_imagen FROM Usuario ORDER BY nombre_usuario";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNombreUsuario(rs.getString("nombre_usuario"));
                u.setNombreCompleto(rs.getString("nombre_completo"));
                u.setRol(rs.getString("rol"));
                u.setPermisos(rs.getString("permisos"));
                u.setRutaImagen(rs.getString("ruta_imagen"));
                lista.add(u);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return lista;
    }
    
    public ArrayList<Usuario> buscar(String texto) {
        ArrayList<Usuario> lista = new ArrayList<Usuario>();
        String sql = "SELECT id, nombre_usuario, nombre_completo, rol, permisos, ruta_imagen FROM Usuario WHERE nombre_usuario LIKE ? OR nombre_completo LIKE ? OR rol LIKE ? ORDER BY nombre_usuario";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String busqueda = "%" + texto + "%";
            stmt.setString(1, busqueda);
            stmt.setString(2, busqueda);
            stmt.setString(3, busqueda);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNombreUsuario(rs.getString("nombre_usuario"));
                u.setNombreCompleto(rs.getString("nombre_completo"));
                u.setRol(rs.getString("rol"));
                u.setPermisos(rs.getString("permisos"));
                u.setRutaImagen(rs.getString("ruta_imagen"));
                lista.add(u);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return lista;
    }
    
    public boolean guardar(Usuario usuario, String contrasena) {
        boolean esNuevo = usuario.getId() == 0;
        String sql;
        
        if (esNuevo) {
            sql = "INSERT INTO Usuario (nombre_usuario, contrasena, nombre_completo, rol, permisos, ruta_imagen) VALUES (?, ?, ?, ?, ?, ?)";
        } else {
            if (contrasena != null && !contrasena.isEmpty()) {
                sql = "UPDATE Usuario SET nombre_usuario = ?, contrasena = ?, nombre_completo = ?, rol = ?, permisos = ?, ruta_imagen = ? WHERE id = ?";
            } else {
                sql = "UPDATE Usuario SET nombre_usuario = ?, nombre_completo = ?, rol = ?, permisos = ?, ruta_imagen = ? WHERE id = ?";
            }
        }
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, usuario.getNombreUsuario());
            
            if (esNuevo) {
                stmt.setString(2, contrasena);
                stmt.setString(3, usuario.getNombreCompleto());
                stmt.setString(4, usuario.getRol());
                stmt.setString(5, usuario.getPermisos());
                if (usuario.getRutaImagen() != null && !usuario.getRutaImagen().isEmpty()) {
                    stmt.setString(6, usuario.getRutaImagen());
                } else {
                    stmt.setNull(6, java.sql.Types.VARCHAR);
                }
            } else {
                if (contrasena != null && !contrasena.isEmpty()) {
                    stmt.setString(2, contrasena);
                    stmt.setString(3, usuario.getNombreCompleto());
                    stmt.setString(4, usuario.getRol());
                    stmt.setString(5, usuario.getPermisos());
                    if (usuario.getRutaImagen() != null && !usuario.getRutaImagen().isEmpty()) {
                        stmt.setString(6, usuario.getRutaImagen());
                    } else {
                        stmt.setNull(6, java.sql.Types.VARCHAR);
                    }
                    stmt.setInt(7, usuario.getId());
                } else {
                    stmt.setString(2, usuario.getNombreCompleto());
                    stmt.setString(3, usuario.getRol());
                    stmt.setString(4, usuario.getPermisos());
                    if (usuario.getRutaImagen() != null && !usuario.getRutaImagen().isEmpty()) {
                        stmt.setString(5, usuario.getRutaImagen());
                    } else {
                        stmt.setNull(5, java.sql.Types.VARCHAR);
                    }
                    stmt.setInt(6, usuario.getId());
                }
            }
            
            int afectados = stmt.executeUpdate();
            
            if (esNuevo && afectados > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    usuario.setId(rs.getInt(1));
                }
                rs.close();
            }
            
            return afectados > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean eliminar(int id) {
        String sql = "DELETE FROM Usuario WHERE id = ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Usuario obtenerPorId(int id) {
        String sql = "SELECT id, nombre_usuario, nombre_completo, rol, permisos, ruta_imagen FROM Usuario WHERE id = ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNombreUsuario(rs.getString("nombre_usuario"));
                u.setNombreCompleto(rs.getString("nombre_completo"));
                u.setRol(rs.getString("rol"));
                u.setPermisos(rs.getString("permisos"));
                u.setRutaImagen(rs.getString("ruta_imagen"));
                return u;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
}