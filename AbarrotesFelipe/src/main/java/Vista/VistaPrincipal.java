package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import BasedeDatos.ConexionBD;
import Controlador.ControladorCaja;
import Controlador.ControladorPrincipal;
import Modelo.Usuario;

public class VistaPrincipal extends JFrame {
    private Usuario usuarioActual;
    private int idCorteAbierto;
    private JLabel lblCajero;
    private JLabel lblMontoCaja;
    private JPanel panelContenido;
    private CardLayout cardLayout;
    private ControladorPrincipal controladorPrincipal;
    private JPanel panelMenu;
    
    private VistaPuntoVenta panelPuntoVenta;
    private VistaProductos panelProductos;
    private VistaClientes panelClientes;
    private VistaProveedores panelProveedores;
    private VistaHistorial panelHistorial;
    private VistaUsuarios panelUsuarios;
    private VistaDevolucion panelDevolucion;
    private VistaReportesBase panelReportes;
    private VistaConfiguracion panelConfiguracion;
    private JPanel panelBienvenida;

    public VistaPrincipal(Usuario usuario, int idCorte) {
        this.usuarioActual = usuario;
        this.idCorteAbierto = idCorte;
        this.controladorPrincipal = new ControladorPrincipal();
        
        setTitle("Nexus POS - Sistema de Punto de Venta");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        iniciarComponentes();
        inicializarPaneles();
        actualizarEstadoCaja();
    }
    
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }
    
    public int getIdUsuarioActual() {
        return usuarioActual.getId();
    }
    
    private void actualizarEstadoCaja() {
        ControladorCaja controladorCaja = new ControladorCaja();
        if (!controladorCaja.hayCajaAbierta()) {
            lblMontoCaja.setText(" Caja Actual: CAJA CERRADA");
            lblMontoCaja.setForeground(Color.RED);
        } else {
            lblMontoCaja.setForeground(Color.WHITE);
            refrescarMontoCaja();
        }
    }
    
    public void refrescarMenu() {
        panelMenu.removeAll();
        construirMenu();
        panelMenu.revalidate();
        panelMenu.repaint();
    }
    
    public void deshabilitarModulosVenta() {
        lblMontoCaja.setText(" Caja Actual: CAJA CERRADA");
        lblMontoCaja.setForeground(Color.RED);
        refrescarMenu();
    }
    
    private void construirMenu() {
        panelMenu.setLayout(new GridBagLayout());
        int y = 0;
        
        ControladorCaja controladorCaja = new ControladorCaja();
        boolean hayCaja = controladorCaja.hayCajaAbierta();
        
        if (!hayCaja) {
            JButton btnAbrirCaja = crearBotonMenu("Abrir Caja", "abrirCaja");
            GridBagConstraints gbcAbrir = new GridBagConstraints();
            gbcAbrir.insets = new Insets(10, 10, 10, 10);
            gbcAbrir.gridx = 0;
            gbcAbrir.gridy = y++;
            gbcAbrir.fill = GridBagConstraints.HORIZONTAL;
            panelMenu.add(btnAbrirCaja, gbcAbrir);
        }
        
        if (hayCaja && controladorPrincipal.tienePermiso(usuarioActual, "pos:escritura")) {
            JButton btnPos = crearBotonMenu("Punto de Venta", "pos");
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.gridx = 0;
            gbc.gridy = y++;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            panelMenu.add(btnPos, gbc);
        }
        
        if (controladorPrincipal.tienePermiso(usuarioActual, "productos:lectura")) {
            JButton btnProductos = crearBotonMenu("Productos", "productos");
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.gridx = 0;
            gbc.gridy = y++;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            panelMenu.add(btnProductos, gbc);
        }
        
        if (controladorPrincipal.tienePermiso(usuarioActual, "clientes:lectura")) {
            JButton btnClientes = crearBotonMenu("Clientes", "clientes");
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.gridx = 0;
            gbc.gridy = y++;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            panelMenu.add(btnClientes, gbc);
        }
        
        if (controladorPrincipal.tienePermiso(usuarioActual, "proveedores:lectura")) {
            JButton btnProveedores = crearBotonMenu("Proveedores", "proveedores");
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.gridx = 0;
            gbc.gridy = y++;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            panelMenu.add(btnProveedores, gbc);
        }
        
        if (hayCaja && (controladorPrincipal.tienePermiso(usuarioActual, "ventas:lectura_propias") || 
            controladorPrincipal.tienePermiso(usuarioActual, "ventas:todas"))) {
            JButton btnHistorial = crearBotonMenu("Historial de Ventas", "ventas");
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.gridx = 0;
            gbc.gridy = y++;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            panelMenu.add(btnHistorial, gbc);
        }
        
        if (controladorPrincipal.tienePermiso(usuarioActual, "usuarios:lectura")) {
            JButton btnUsuarios = crearBotonMenu("Usuarios", "usuarios");
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.gridx = 0;
            gbc.gridy = y++;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            panelMenu.add(btnUsuarios, gbc);
        }
        
        if (hayCaja && controladorPrincipal.tienePermiso(usuarioActual, "corte:lectura")) {
            JButton btnCorte = crearBotonMenu("Corte de Caja", "corte");
            GridBagConstraints gbcCorte = new GridBagConstraints();
            gbcCorte.insets = new Insets(10, 10, 10, 10);
            gbcCorte.gridx = 0;
            gbcCorte.gridy = y++;
            gbcCorte.fill = GridBagConstraints.HORIZONTAL;
            panelMenu.add(btnCorte, gbcCorte);
        }
        
        if (hayCaja && controladorPrincipal.tienePermiso(usuarioActual, "devoluciones:lectura")) {
            JButton btnDevoluciones = crearBotonMenu("Devoluciones", "devolucion");
            GridBagConstraints gbcDevoluciones = new GridBagConstraints();
            gbcDevoluciones.insets = new Insets(10, 10, 10, 10);
            gbcDevoluciones.gridx = 0;
            gbcDevoluciones.gridy = y++;
            gbcDevoluciones.fill = GridBagConstraints.HORIZONTAL;
            panelMenu.add(btnDevoluciones, gbcDevoluciones);
        }
        
        if (controladorPrincipal.tienePermiso(usuarioActual, "reportes:lectura")) {
            JButton btnReportes = crearBotonMenu("Reportes", "reportes");
            GridBagConstraints gbcReportes = new GridBagConstraints();
            gbcReportes.insets = new Insets(10, 10, 10, 10);
            gbcReportes.gridx = 0;
            gbcReportes.gridy = y++;
            gbcReportes.fill = GridBagConstraints.HORIZONTAL;
            panelMenu.add(btnReportes, gbcReportes);
        }
        
        if (controladorPrincipal.tienePermiso(usuarioActual, "configuracion:escritura")) {
            JButton btnConfiguracion = crearBotonMenu("Configuracion", "configuracion");
            GridBagConstraints gbcConfig = new GridBagConstraints();
            gbcConfig.insets = new Insets(10, 10, 10, 10);
            gbcConfig.gridx = 0;
            gbcConfig.gridy = y++;
            gbcConfig.fill = GridBagConstraints.HORIZONTAL;
            panelMenu.add(btnConfiguracion, gbcConfig);
        }
        
        GridBagConstraints gbcGlue = new GridBagConstraints();
        gbcGlue.gridx = 0;
        gbcGlue.gridy = y++;
        gbcGlue.weighty = 1.0;
        panelMenu.add(Box.createVerticalGlue(), gbcGlue);
    }
    
    private void inicializarPaneles() {
        cardLayout = new CardLayout();
        panelContenido.setLayout(cardLayout);
        
        panelBienvenida = new JPanel(new GridBagLayout());
        panelBienvenida.setBackground(new Color(240, 248, 255));
        JLabel lblBienvenida = new JLabel("Bienvenido al Sistema de Punto de Venta");
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 24));
        lblBienvenida.setForeground(new Color(30, 41, 59));
        panelBienvenida.add(lblBienvenida);
        panelContenido.add(panelBienvenida, "bienvenida");
        
        panelPuntoVenta = new VistaPuntoVenta(this, usuarioActual, idCorteAbierto);
        panelContenido.add(panelPuntoVenta, "pos");
        
        panelProductos = new VistaProductos(this);
        panelContenido.add(panelProductos, "productos");
        
        panelClientes = new VistaClientes(this);
        panelContenido.add(panelClientes, "clientes");
        
        panelProveedores = new VistaProveedores(this);
        panelContenido.add(panelProveedores, "proveedores");
        
        panelHistorial = new VistaHistorial(this);
        panelContenido.add(panelHistorial, "ventas");
        
        panelUsuarios = new VistaUsuarios(this);
        panelContenido.add(panelUsuarios, "usuarios");
        
        panelDevolucion = new VistaDevolucion(this);
        panelContenido.add(panelDevolucion, "devolucion");
        
        panelReportes = new VistaReportesBase(this);
        panelContenido.add(panelReportes, "reportes");
        
        panelConfiguracion = new VistaConfiguracion(this);
        panelContenido.add(panelConfiguracion, "configuracion");
        
        cardLayout.show(panelContenido, "bienvenida");
    }
    
    private void iniciarComponentes() {
        setLayout(new BorderLayout());
        
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(new Color(79, 70, 229));
        panelSuperior.setPreferredSize(new Dimension(getWidth(), 60));
        
        lblCajero = new JLabel(" Cajero: " + usuarioActual.getNombreCompleto());
        lblCajero.setForeground(Color.WHITE);
        lblCajero.setFont(new Font("Arial", Font.BOLD, 14));
        
        lblMontoCaja = new JLabel(" Caja Actual: $0.00");
        lblMontoCaja.setForeground(Color.WHITE);
        lblMontoCaja.setFont(new Font("Arial", Font.BOLD, 14));
        
        JButton btnCerrarSesion = new JButton("Cerrar Sesion");
        btnCerrarSesion.setBackground(new Color(220, 38, 38));
        btnCerrarSesion.setForeground(Color.WHITE);
        btnCerrarSesion.setFont(new Font("Arial", Font.BOLD, 12));
        btnCerrarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCerrarSesion.setBorderPainted(false);
        
        panelSuperior.add(lblCajero, BorderLayout.WEST);
        panelSuperior.add(lblMontoCaja, BorderLayout.CENTER);
        panelSuperior.add(btnCerrarSesion, BorderLayout.EAST);
        
        add(panelSuperior, BorderLayout.NORTH);
        
        panelMenu = new JPanel();
        panelMenu.setBackground(new Color(30, 41, 59));
        panelMenu.setPreferredSize(new Dimension(250, getHeight()));
        
        construirMenu();
        
        add(panelMenu, BorderLayout.WEST);
        
        panelContenido = new JPanel();
        panelContenido.setBackground(new Color(240, 248, 255));
        add(panelContenido, BorderLayout.CENTER);
        
        btnCerrarSesion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cerrarSesion();
            }
        });
        
        actualizarEstadoCaja();
    }
    
    private JButton crearBotonMenu(String texto, String vista) {
        JButton btn = new JButton(texto);
        btn.setBackground(new Color(51, 65, 85));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(200, 45));
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarVista(vista);
            }
        });
        return btn;
    }
    
    private void mostrarVista(String vista) {
        if (cardLayout == null || panelContenido == null) {
            return;
        }
        
        ControladorCaja controladorCaja = new ControladorCaja();
        
        if (vista.equals("pos")) {
            if (!controladorCaja.hayCajaAbierta()) {
                int respuesta = JOptionPane.showConfirmDialog(this, 
                    "No hay una caja abierta. ¿Desea abrir caja ahora?", 
                    "Caja Cerrada", 
                    JOptionPane.YES_NO_OPTION);
                if (respuesta == JOptionPane.YES_OPTION) {
                    new VistaAperturaCaja(this, usuarioActual.getId()).setVisible(true);
                }
                return;
            }
        }
        
        if (vista.equals("devolucion")) {
            boolean puedeAcceder = usuarioActual.getRol().equals("Administrador") || 
                                   controladorPrincipal.tienePermiso(usuarioActual, "devoluciones:escritura");
            if (!puedeAcceder) {
                JOptionPane.showMessageDialog(this, "No tiene permiso para realizar devoluciones");
                return;
            }
            if (!controladorCaja.hayCajaAbierta()) {
                JOptionPane.showMessageDialog(this, "No hay una caja abierta. Debe abrir caja primero.");
                return;
            }
        }
        
        if (vista.equals("configuracion")) {
            boolean puedeAcceder = usuarioActual.getRol().equals("Administrador") || 
                                   controladorPrincipal.tienePermiso(usuarioActual, "configuracion:escritura");
            if (!puedeAcceder) {
                JOptionPane.showMessageDialog(this, "No tiene permiso para acceder a configuracion");
                return;
            }
        }
        
        if (vista.equals("usuarios")) {
            boolean puedeAcceder = usuarioActual.getRol().equals("Administrador") || 
                                   controladorPrincipal.tienePermiso(usuarioActual, "usuarios:lectura");
            if (!puedeAcceder) {
                JOptionPane.showMessageDialog(this, "No tiene permiso para acceder a usuarios");
                return;
            }
        }
        
        if (vista.equals("corte")) {
            new VistaCierreCaja(this, idCorteAbierto).setVisible(true);
            return;
        }
        
        if (vista.equals("abrirCaja")) {
            new VistaAperturaCaja(this, usuarioActual.getId()).setVisible(true);
            return;
        }
        
        cardLayout.show(panelContenido, vista);
        
        switch (vista) {
            case "pos":
                if (panelPuntoVenta != null) panelPuntoVenta.refrescar();
                break;
            case "productos":
                if (panelProductos != null) panelProductos.refrescar();
                break;
            case "clientes":
                if (panelClientes != null) panelClientes.refrescar();
                break;
            case "proveedores":
                if (panelProveedores != null) panelProveedores.refrescar();
                break;
            case "ventas":
                if (panelHistorial != null) panelHistorial.refrescar();
                break;
            case "usuarios":
                if (panelUsuarios != null) panelUsuarios.refrescar();
                break;
            case "devolucion":
                if (panelDevolucion != null) panelDevolucion.refrescar();
                break;
            case "reportes":
                if (panelReportes != null) panelReportes.refrescar();
                break;
            case "configuracion":
                if (panelConfiguracion != null) panelConfiguracion.refrescar();
                break;
        }
    }
    
    public void refrescarMontoCaja() {
        if (idCorteAbierto != -1) {
            String sql = "SELECT (monto_apertura + monto_ventas_efectivo) as total FROM CorteCaja WHERE id = ?";
            try (Connection conn = ConexionBD.obtenerConexion();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idCorteAbierto);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    double total = rs.getDouble("total");
                    lblMontoCaja.setText(" Caja Actual: $" + String.format("%.2f", total));
                } else {
                    lblMontoCaja.setText(" Caja Actual: $0.00");
                }
                rs.close();
            } catch (SQLException e) {
                lblMontoCaja.setText(" Caja Actual: $0.00");
            }
        } else {
            lblMontoCaja.setText(" Caja Actual: CAJA CERRADA");
            lblMontoCaja.setForeground(Color.RED);
        }
    }
    
    private void cerrarSesion() {
        int confirm = JOptionPane.showConfirmDialog(this, "¿Esta seguro que desea cerrar sesion?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            new VistaLogin().setVisible(true);
            this.dispose();
        }
    }
    
    public void actualizarMontoCaja(double monto) {
        lblMontoCaja.setText(" Caja Actual: $" + String.format("%.2f", monto));
        lblMontoCaja.setForeground(Color.WHITE);
    }
    
    public void setIdCorteAbierto(int idCorte) {
        this.idCorteAbierto = idCorte;
    }
    
    public int getIdCorteAbierto() {
        return idCorteAbierto;
    }
}