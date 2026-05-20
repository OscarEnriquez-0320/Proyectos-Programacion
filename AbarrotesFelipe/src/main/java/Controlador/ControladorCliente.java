package Controlador;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import BasedeDatos.ConexionBD;
import Modelo.Cliente;

public class ControladorCliente {
    
    public ArrayList<Cliente> listarTodos() {
        ArrayList<Cliente> lista = new ArrayList<Cliente>();
        String sql = "SELECT id, nombre, email, telefono, puntos, ruta_imagen FROM Cliente ORDER BY nombre";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setNombre(rs.getString("nombre"));
                c.setEmail(rs.getString("email"));
                c.setTelefono(rs.getString("telefono"));
                c.setPuntos(rs.getInt("puntos"));
                c.setRutaImagen(rs.getString("ruta_imagen"));
                lista.add(c);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return lista;
    }
    
    public ArrayList<Cliente> buscar(String texto) {
        ArrayList<Cliente> lista = new ArrayList<Cliente>();
        String sql = "SELECT id, nombre, email, telefono, puntos, ruta_imagen FROM Cliente WHERE nombre LIKE ? OR email LIKE ? OR telefono LIKE ? ORDER BY nombre";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String busqueda = "%" + texto + "%";
            stmt.setString(1, busqueda);
            stmt.setString(2, busqueda);
            stmt.setString(3, busqueda);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setNombre(rs.getString("nombre"));
                c.setEmail(rs.getString("email"));
                c.setTelefono(rs.getString("telefono"));
                c.setPuntos(rs.getInt("puntos"));
                c.setRutaImagen(rs.getString("ruta_imagen"));
                lista.add(c);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return lista;
    }
    
    public boolean guardar(Cliente cliente) {
        boolean esNuevo = cliente.getId() == 0;
        String sql;
        
        if (esNuevo) {
            sql = "INSERT INTO Cliente (nombre, email, telefono, puntos, ruta_imagen) VALUES (?, ?, ?, ?, ?)";
        } else {
            sql = "UPDATE Cliente SET nombre = ?, email = ?, telefono = ?, puntos = ?, ruta_imagen = ? WHERE id = ?";
        }
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getTelefono());
            stmt.setInt(4, cliente.getPuntos());
            
            if (cliente.getRutaImagen() != null && !cliente.getRutaImagen().isEmpty()) {
                stmt.setString(5, cliente.getRutaImagen());
            } else {
                stmt.setNull(5, java.sql.Types.VARCHAR);
            }
            
            if (!esNuevo) {
                stmt.setInt(6, cliente.getId());
            }
            
            int afectados = stmt.executeUpdate();
            
            if (esNuevo && afectados > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    cliente.setId(rs.getInt(1));
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
        String sql = "DELETE FROM Cliente WHERE id = ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Cliente obtenerPorId(int id) {
        String sql = "SELECT id, nombre, email, telefono, puntos, ruta_imagen FROM Cliente WHERE id = ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setNombre(rs.getString("nombre"));
                c.setEmail(rs.getString("email"));
                c.setTelefono(rs.getString("telefono"));
                c.setPuntos(rs.getInt("puntos"));
                c.setRutaImagen(rs.getString("ruta_imagen"));
                return c;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
}