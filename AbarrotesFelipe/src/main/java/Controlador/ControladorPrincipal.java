package Controlador;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import Modelo.Cliente;
import Modelo.Producto;
import Modelo.Proveedor;
import Modelo.Usuario;
import Modelo.Venta;
import Vista.VistaClientes;
import Vista.VistaHistorial;
import Vista.VistaUsuarios;

public class ControladorPrincipal {
    private ControladorUsuario controladorUsuario;
    private ControladorCliente controladorCliente;
    private ControladorVenta controladorVenta;
    private ControladorCaja controladorCaja;
    private ControladorProducto controladorProducto;
    private ControladorProveedor controladorProveedor;
    
    public ControladorPrincipal() {
        this.controladorUsuario = new ControladorUsuario();
        this.controladorCliente = new ControladorCliente();
        this.controladorVenta = new ControladorVenta();
        this.controladorCaja = new ControladorCaja();
        this.controladorProducto = new ControladorProducto();
        this.controladorProveedor = new ControladorProveedor();
    }
    
    // metodos para permisos
    
    public boolean tienePermiso(Usuario usuario, String permiso) {
        if (usuario == null) return false;
        if (usuario.getRol().equals("Administrador")) return true;  
        if (usuario.getPermisos() == null) return false;
        if (usuario.getPermisos().equals("all")) return true;
        return usuario.getPermisos().contains(permiso);
    }
    
    // metodos productos
    
    public ArrayList<Producto> listarProductos() {
        return controladorProducto.listarTodos();
    }
    
    public boolean deshabilitarProducto(int idProducto, String rutaImagen) {
        return controladorProducto.eliminar(idProducto, rutaImagen);
    }
    
    // prov
    
    public ArrayList<Proveedor> listarProveedoresParaCombo() {
        ArrayList<Proveedor> lista = controladorProveedor.listarParaCombo();
        if (lista == null) {
            return new ArrayList<Proveedor>();
        }
        return lista;
    }
    // us
    
    public void cargarUsuarios(VistaUsuarios vista) {
        ArrayList<Usuario> usuarios = controladorUsuario.listarTodos();
        if (usuarios != null) {
            vista.actualizarTabla(usuarios);
        } else {
            vista.actualizarTabla(new ArrayList<Usuario>());
        }
    }
    
    public void buscarUsuarios(VistaUsuarios vista, String texto) {
        ArrayList<Usuario> usuarios = controladorUsuario.buscar(texto);
        if (usuarios != null) {
            vista.actualizarTabla(usuarios);
        } else {
            vista.actualizarTabla(new ArrayList<Usuario>());
        }
    }
    
    public void guardarUsuario(Usuario usuario, String password, VistaUsuarios vista) {
        boolean guardado = controladorUsuario.guardar(usuario, password);
        if (guardado) {
            JOptionPane.showMessageDialog(null, "Usuario guardado correctamente");
            cargarUsuarios(vista);
        } else {
            JOptionPane.showMessageDialog(null, "Error al guardar el usuario");
        }
    }
    
    public void eliminarUsuario(int id, VistaUsuarios vista) {
        boolean eliminado = controladorUsuario.eliminar(id);
        if (eliminado) {
            JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente");
            cargarUsuarios(vista);
        } else {
            JOptionPane.showMessageDialog(null, "Error al eliminar el usuario");
        }
    }
    
    // clientes
    
    public ArrayList<Cliente> listarClientes() {
        return controladorCliente.listarTodos();
    }
    
    public ArrayList<Cliente> buscarClientes(String texto) {
        return controladorCliente.buscar(texto);
    }
    
    public void guardarCliente(Cliente cliente, VistaClientes vista) {
        boolean guardado = controladorCliente.guardar(cliente);
        if (guardado) {
            JOptionPane.showMessageDialog(null, "Cliente guardado correctamente");
            vista.actualizarTablaDesdeControlador(controladorCliente.listarTodos());
        } else {
            JOptionPane.showMessageDialog(null, "Error al guardar el cliente");
        }
    }
    
    public boolean eliminarCliente(int id) {
        return controladorCliente.eliminar(id);
    }
    
    // ventas
    
    public ArrayList<Venta> listarVentas(String filtro, int idUsuario, boolean esAdmin) {
        return controladorVenta.listarVentas(filtro, idUsuario, esAdmin);
    }
    
    public Venta obtenerVentaConDetalles(int idVenta) {
        return controladorVenta.obtenerVentaConDetalles(idVenta);
    }
    
    public boolean cancelarVenta(int idVenta) {
        return controladorVenta.cancelarVenta(idVenta);
    }
    
    // caja
    
    public boolean tieneCajaAbierta() {
        return controladorCaja.hayCajaAbierta();
    }
    
    public int getIdCorteAbierto() {
        ControladorCaja.CorteInfo corte = controladorCaja.obtenerCajaAbiertaInfo();
        if (corte != null) {
            return corte.id;
        }
        return -1;
    }
    
    public double getMontoEsperado(int idCorte) {
        return controladorCaja.getMontoEsperado(idCorte);
    }
    
    public boolean cerrarCaja(int idCorte, double montoFisico, int idUsuario) {
        return controladorCaja.cerrarCaja(idCorte, montoFisico, idUsuario);
    }
}