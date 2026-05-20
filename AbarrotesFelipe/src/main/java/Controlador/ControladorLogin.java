package Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import BasedeDatos.ConexionBD;
import Modelo.Usuario;
import Vista.VistaLogin;
import Vista.VistaPrincipal;

public class ControladorLogin {
    private VistaLogin vista;
    
    public ControladorLogin(VistaLogin vista) {
        this.vista = vista;
    }
    
    public void autenticar() {
        String usuario = vista.getUsuario();
        String contrasena = vista.getPassword();
        
        if (usuario == null || usuario.trim().isEmpty()) {
            vista.mostrarError("Ingrese un usuario");
            return;
        }
        
        if (contrasena == null || contrasena.trim().isEmpty()) {
            vista.mostrarError("Ingrese una contrasena");
            return;
        }
        
        String sql = "SELECT id, nombre_usuario, nombre_completo, rol, permisos FROM Usuario WHERE nombre_usuario = ? AND contrasena = ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, usuario);
            stmt.setString(2, contrasena);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNombreUsuario(rs.getString("nombre_usuario"));
                u.setNombreCompleto(rs.getString("nombre_completo"));
                u.setRol(rs.getString("rol"));
                u.setPermisos(rs.getString("permisos"));
                
                abrirPrincipal(u);
            } else {
                vista.mostrarError("Usuario o contrasena incorrectos");
            }
            
            rs.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            vista.mostrarError("Error de conexion: " + e.getMessage());
        }
    }
    
    private void abrirPrincipal(Usuario usuario) {
        ControladorCaja controladorCaja = new ControladorCaja();
        int idCorte = -1;
        
       
        boolean hayCaja = controladorCaja.hayCajaAbierta();
        
        if (hayCaja) {
            
            idCorte = controladorCaja.obtenerIdCorteAbierto();
            VistaPrincipal principal = new VistaPrincipal(usuario, idCorte);
            principal.setVisible(true);
            vista.dispose();
        } else {
            
            String montoStr = JOptionPane.showInputDialog(vista, 
                "No hay caja abierta.\nIngrese el monto inicial en efectivo:", 
                "Apertura de Caja", 
                JOptionPane.QUESTION_MESSAGE);
            
            if (montoStr == null) {
                
                return;
            }
            
            try {
                double montoInicial = Double.parseDouble(montoStr);
                if (montoInicial < 0) {
                    JOptionPane.showMessageDialog(vista, "El monto no puede ser negativo");
                    return;
                }
                
                boolean abierta = controladorCaja.abrirCaja(usuario.getId(), montoInicial);
                if (!abierta) {
                    JOptionPane.showMessageDialog(vista, "Error al abrir la caja");
                    return;
                }
                
                idCorte = controladorCaja.obtenerIdCorteAbierto();
                JOptionPane.showMessageDialog(vista, "Caja abierta correctamente con $" + montoInicial);
                
                VistaPrincipal principal = new VistaPrincipal(usuario, idCorte);
                principal.setVisible(true);
                vista.dispose();
                
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(vista, "Ingrese un número válido");
            }
        }
    }
}