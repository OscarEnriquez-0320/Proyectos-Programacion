package Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import BasedeDatos.ConexionBD;
import Modelo.Proveedor;

public class ControladorProveedor {
	    
	    public ArrayList<Proveedor> listarTodos() {
	        ArrayList<Proveedor> lista = new ArrayList<Proveedor>();
	        String sql = "SELECT id, nombre_empresa, contacto_nombre, telefono, categoria, ruta_imagen FROM Proveedor ORDER BY nombre_empresa";
	        
	        try (Connection conn = ConexionBD.obtenerConexion();
	                PreparedStatement stmt = conn.prepareStatement(sql);
	                ResultSet rs = stmt.executeQuery()) {
	               
	               while (rs.next()) {
	                   Proveedor p = new Proveedor();
	                   p.setId(rs.getInt("id"));
	                   p.setNombreEmpresa(rs.getString("nombre_empresa"));
	                   p.setContactoNombre(rs.getString("contacto_nombre"));
	                   p.setTelefono(rs.getString("telefono"));
	                   p.setCategoria(rs.getString("categoria"));
	                  
	                   lista.add(p);
	               }
	               
	           } catch (SQLException e) {
	               e.printStackTrace();
	           }
	           
	           return lista;
	       }	    
	    public ArrayList<Proveedor> buscar(String texto) {
	        ArrayList<Proveedor> lista = new ArrayList<Proveedor>();
	        String sql = "SELECT id, nombre_empresa, contacto_nombre, telefono, categoria,  FROM Proveedor WHERE nombre_empresa LIKE ? OR contacto_nombre LIKE ? OR categoria LIKE ? ORDER BY nombre_empresa";
	        
	        try (Connection conn = ConexionBD.obtenerConexion();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {
	            
	            String busqueda = "%" + texto + "%";
	            stmt.setString(1, busqueda);
	            stmt.setString(2, busqueda);
	            stmt.setString(3, busqueda);
	            ResultSet rs = stmt.executeQuery();
	            
	            while (rs.next()) {
	                Proveedor p = new Proveedor();
	                p.setId(rs.getInt("id"));
	                p.setNombreEmpresa(rs.getString("nombre_empresa"));
	                p.setContactoNombre(rs.getString("contacto_nombre"));
	                p.setTelefono(rs.getString("telefono"));
	                p.setCategoria(rs.getString("categoria"));
	                
	                lista.add(p);
	            }
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        
	        return lista;
	    }
    
    
    public boolean guardar(Proveedor proveedor) {
        boolean esNuevo = proveedor.getId() == 0;
        String sql;
        
        if (esNuevo) {
            sql = "INSERT INTO Proveedor (nombre_empresa, contacto_nombre, telefono, categoria, ) VALUES (?, ?, ?, ?, ?)";
        } else {
            sql = "UPDATE Proveedor SET nombre_empresa = ?, contacto_nombre = ?, telefono = ?, categoria = ?,   WHERE id = ?";
        }
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, proveedor.getNombreEmpresa());
            stmt.setString(2, proveedor.getContactoNombre());
            stmt.setString(3, proveedor.getTelefono());
            stmt.setString(4, proveedor.getCategoria());
            
            
            if (!esNuevo) {
                stmt.setInt(6, proveedor.getId());
            }
            
            int afectados = stmt.executeUpdate();
            
            if (esNuevo && afectados > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    proveedor.setId(rs.getInt(1));
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
        String sql = "DELETE FROM Proveedor WHERE id = ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    

    public Proveedor obtenerPorId(int id) {
        String sql = "SELECT id, nombre_empresa, contacto_nombre, telefono, categoria, ruta_imagen FROM Proveedor WHERE id = ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Proveedor p = new Proveedor();
                p.setId(rs.getInt("id"));
                p.setNombreEmpresa(rs.getString("nombre_empresa"));
                p.setContactoNombre(rs.getString("contacto_nombre"));
                p.setTelefono(rs.getString("telefono"));
                p.setCategoria(rs.getString("categoria"));
                
                return p;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    

    public ArrayList<Proveedor> listarParaCombo() {
        ArrayList<Proveedor> lista = new ArrayList<Proveedor>();
        String sql = "SELECT id, nombre_empresa FROM Proveedor ORDER BY nombre_empresa";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Proveedor p = new Proveedor();
                p.setId(rs.getInt("id"));
                p.setNombreEmpresa(rs.getString("nombre_empresa"));
                lista.add(p);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return lista;
    }
    

    public boolean existeProveedor(String nombre, int idExcluir) {
        String sql = "SELECT COUNT(*) FROM Proveedor WHERE nombre_empresa = ? AND id != ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, nombre);
            stmt.setInt(2, idExcluir);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
}