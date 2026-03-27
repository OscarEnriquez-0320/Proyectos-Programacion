package Controlador;

import Librerias.AlmacenamientoDatos;
import Modelo.Producto;
import Modelo.Proveedor;
import java.util.List;

public class ProveedorControlador {
    private AlmacenamientoDatos datos;
    
    public ProveedorControlador() {
        this.datos = AlmacenamientoDatos.getInstancia();
    }
    
    // Obtener todos los proveedores
    public List<Proveedor> obtenerTodosProveedores() {
        return datos.obtenerTodosProveedores();
    }
    
    // Obtener proveedor por ID
    public Proveedor obtenerProveedorPorId(int id) {
        return datos.obtenerProveedorPorId(id);
    }
    
    // Crear nuevo proveedor
    public boolean crearProveedor(String nombre, String rtn, String telefono, 
                                   String email, String nombreContacto, String direccion) {
        try {
            if (nombre == null || nombre.trim().isEmpty()) {
                throw new IllegalArgumentException("El nombre del proveedor es obligatorio");
            }
            
            Proveedor nuevo = new Proveedor();
            nuevo.setNombre(nombre);
            nuevo.setRtn(rtn);
            nuevo.setTelefono(telefono);
            nuevo.setEmail(email);
            nuevo.setNombreContacto(nombreContacto);
            nuevo.setDireccion(direccion);
            
            datos.agregarProveedor(nuevo);
            return true;
        } catch (Exception e) {
            System.err.println("Error al crear proveedor: " + e.getMessage());
            return false;
        }
    }
    
    // Actualizar proveedor
    public boolean actualizarProveedor(int id, String nombre, String rtn, String telefono,
                                        String email, String nombreContacto, String direccion) {
        try {
            Proveedor existente = datos.obtenerProveedorPorId(id);
            if (existente == null) {
                throw new IllegalArgumentException("Proveedor no encontrado");
            }
            
            existente.setNombre(nombre);
            existente.setRtn(rtn);
            existente.setTelefono(telefono);
            existente.setEmail(email);
            existente.setNombreContacto(nombreContacto);
            existente.setDireccion(direccion);
            
            datos.actualizarProveedor(existente);
            return true;
        } catch (Exception e) {
            System.err.println("Error al actualizar proveedor: " + e.getMessage());
            return false;
        }
    }
    
    // Eliminar proveedor
    public boolean eliminarProveedor(int id) {
        try {
            Proveedor proveedor = datos.obtenerProveedorPorId(id);
            if (proveedor == null) {
                throw new IllegalArgumentException("Proveedor no encontrado");
            }
            
            // Desasociar productos de este proveedor
            for (Producto p : datos.obtenerTodosProductos()) {
                if (p.getProveedor() != null && p.getProveedor().getId() == id) {
                    p.setProveedor(null);
                    datos.actualizarProducto(p);
                }
            }
            
            datos.eliminarProveedor(id);
            return true;
        } catch (Exception e) {
            System.err.println("Error al eliminar proveedor: " + e.getMessage());
            return false;
        }
    }
    
    // Buscar proveedores
    public List<Proveedor> buscarProveedores(String criterio) {
        if (criterio == null || criterio.trim().isEmpty()) {
            return datos.obtenerTodosProveedores();
        }
        
        String busqueda = criterio.toLowerCase().trim();
        return datos.obtenerTodosProveedores().stream()
            .filter(p -> p.getNombre().toLowerCase().contains(busqueda) ||
                        (p.getTelefono() != null && p.getTelefono().contains(busqueda)) ||
                        (p.getRtn() != null && p.getRtn().toLowerCase().contains(busqueda)))
            .toList();
    }
    
    // Obtener productos de un proveedor
    public List<Producto> obtenerProductosPorProveedor(int idProveedor) {
        return datos.obtenerTodosProductos().stream()
            .filter(p -> p.getProveedor() != null && p.getProveedor().getId() == idProveedor)
            .toList();
    }
    
    // Contar proveedores
    public int contarProveedores() {
        return datos.obtenerTodosProveedores().size();
    }
}