package Controlador;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import BasedeDatos.ConexionBD;
import Modelo.Producto;
import Modelo.Proveedor;

public class ControladorProducto {
    
    private static final String RUTA_IMAGENES = "img_productos/";
    
    
    public ArrayList<Producto> listarTodos() {
        ArrayList<Producto> lista = new ArrayList<Producto>();
        String sql = "SELECT p.id, p.nombre, p.precio, p.stock, p.tipo_venta, p.stock_minimo, p.stock_maximo, p.categoria, p.ruta_imagen, p.activo, pr.nombre_empresa FROM Producto p LEFT JOIN Proveedor pr ON p.id_proveedor = pr.id WHERE p.activo = 1 ORDER BY p.nombre";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getBigDecimal("precio"));
                p.setStock(rs.getDouble("stock"));
                p.setTipoVenta(rs.getString("tipo_venta"));
                p.setStockMinimo(rs.getDouble("stock_minimo"));
                p.setStockMaximo(rs.getDouble("stock_maximo"));
                p.setCategoria(rs.getString("categoria"));
                p.setRutaImagen(rs.getString("ruta_imagen"));
                p.setNombreProveedor(rs.getString("nombre_empresa"));
                lista.add(p);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return lista;
    }
    

    public ArrayList<Producto> buscar(String texto) {
        ArrayList<Producto> lista = new ArrayList<Producto>();
        String sql = "SELECT p.id, p.nombre, p.precio, p.stock, p.tipo_venta, p.stock_minimo, p.stock_maximo, p.categoria, p.ruta_imagen, p.activo, pr.nombre_empresa FROM Producto p LEFT JOIN Proveedor pr ON p.id_proveedor = pr.id WHERE (p.nombre LIKE ? OR p.categoria LIKE ?) AND p.activo = 1 ORDER BY p.nombre";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String busqueda = "%" + texto + "%";
            stmt.setString(1, busqueda);
            stmt.setString(2, busqueda);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getBigDecimal("precio"));
                p.setStock(rs.getDouble("stock"));
                p.setTipoVenta(rs.getString("tipo_venta"));
                p.setStockMinimo(rs.getDouble("stock_minimo"));
                p.setStockMaximo(rs.getDouble("stock_maximo"));
                p.setCategoria(rs.getString("categoria"));
                p.setRutaImagen(rs.getString("ruta_imagen"));
                p.setNombreProveedor(rs.getString("nombre_empresa"));
                lista.add(p);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return lista;
    }
    
 
    public boolean guardar(Producto producto) {
        boolean esNuevo = producto.getId() == 0;
        String sql;
        
        if (esNuevo) {
            sql = "INSERT INTO Producto (nombre, precio, stock, stock_minimo, stock_maximo, categoria, tipo_venta, id_proveedor, ruta_imagen) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        } else {
            sql = "UPDATE Producto SET nombre = ?, precio = ?, stock = ?, stock_minimo = ?, stock_maximo = ?, categoria = ?, tipo_venta = ?, id_proveedor = ?, ruta_imagen = ? WHERE id = ?";
        }
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, producto.getNombre());
            stmt.setBigDecimal(2, producto.getPrecio());
            stmt.setDouble(3, producto.getStock());
            stmt.setDouble(4, producto.getStockMinimo());
            stmt.setDouble(5, producto.getStockMaximo());
            stmt.setString(6, producto.getCategoria());
            stmt.setString(7, producto.getTipoVenta());
            
            if (producto.getIdProveedor() > 0) {
                stmt.setInt(8, producto.getIdProveedor());
            } else {
                stmt.setNull(8, java.sql.Types.INTEGER);
            }
            
            if (producto.getRutaImagen() != null && !producto.getRutaImagen().isEmpty()) {
                stmt.setString(9, producto.getRutaImagen());
            } else {
                stmt.setNull(9, java.sql.Types.VARCHAR);
            }
            
            if (!esNuevo) {
                stmt.setInt(10, producto.getId());
            }
            
            int afectados = stmt.executeUpdate();
            
            if (esNuevo && afectados > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    producto.setId(rs.getInt(1));
                }
                rs.close();
            }
            
            return afectados > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    public boolean eliminar(int idProducto, String rutaImagen) {
      
        String sql = "UPDATE Producto SET activo = 0 WHERE id = ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idProducto);
            int afectados = stmt.executeUpdate();
            
            return afectados > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
        

  
    public Producto obtenerPorId(int id) {
        String sql = "SELECT p.id, p.nombre, p.precio, p.stock, p.tipo_venta, p.stock_minimo, p.stock_maximo, p.categoria, p.ruta_imagen, p.id_proveedor FROM Producto p WHERE p.id = ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getBigDecimal("precio"));
                p.setStock(rs.getInt("stock"));
                p.setTipoVenta(rs.getString("tipo_venta"));
                p.setStockMinimo(rs.getInt("stock_minimo"));
                p.setStockMaximo(rs.getInt("stock_maximo"));
                p.setCategoria(rs.getString("categoria"));
                p.setRutaImagen(rs.getString("ruta_imagen"));
                p.setIdProveedor(rs.getInt("id_proveedor"));
                return p;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
 
    public boolean actualizarStock(int idProducto, int cantidad) {
        String sql = "UPDATE Producto SET stock = stock - ? WHERE id = ? AND stock >= ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, cantidad);
            stmt.setInt(2, idProducto);
            stmt.setInt(3, cantidad);
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    

    public boolean restaurarStock(int idProducto, int cantidad) {
        String sql = "UPDATE Producto SET stock = stock + ? WHERE id = ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, cantidad);
            stmt.setInt(2, idProducto);
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    

    public List<Producto> listarStockCritico() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, stock, stock_minimo FROM Producto WHERE stock <= stock_minimo ORDER BY stock ASC";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setStock(rs.getInt("stock"));
                p.setStockMinimo(rs.getInt("stock_minimo"));
                lista.add(p);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return lista;
    }
    

    public ImageIcon obtenerImagen(String ruta) {
        if (ruta == null || ruta.isEmpty()) {
            return null;
        }
        
        String rutaCompleta = RUTA_IMAGENES + ruta;
        File archivo = new File(rutaCompleta);
        
        if (archivo.exists()) {
            try {
                ImageIcon original = new ImageIcon(rutaCompleta);
                return original;
            } catch (Exception e) {
                return null;
            }
        }
        
        return null;
    }
    

    public boolean existeProducto(String nombre, int idExcluir) {
        String sql = "SELECT COUNT(*) FROM Producto WHERE nombre = ? AND id != ?";
        
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