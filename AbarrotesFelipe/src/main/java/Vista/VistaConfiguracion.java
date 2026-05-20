package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import Controlador.ControladorConfiguracion;

public class VistaConfiguracion extends JPanel {
    private VistaPrincipal vistaPrincipal;
    private ControladorConfiguracion controladorConfig;
    
    private JTextField txtEmpresaNombre;
    private JTextField txtEmpresaTelefono;
    private JTextField txtEmpresaDireccion;
    private JTextField txtEmpresaEmail;
    private JTextField txtTiempoSesion;
    private JCheckBox chkRecordarUsuario;
    private JCheckBox chkRespaldoAutomatico;
    private JTextArea txtTicketMensaje;
    private JLabel lblLogoPreview;
    private String rutaLogoSeleccionado;
    
    public VistaConfiguracion(VistaPrincipal vista) {
        this.vistaPrincipal = vista;
        this.controladorConfig = new ControladorConfiguracion();
        
        setLayout(new BorderLayout());
        setBackground(new Color(240, 248, 255));
        
        iniciarComponentes();
        cargarConfiguraciones();
    }
    
    private void iniciarComponentes() {
        JTabbedPane pestañas = new JTabbedPane();
        pestañas.setFont(new Font("Arial", Font.BOLD, 14));
        
        pestañas.addTab("Datos de la Empresa", crearPanelDatosEmpresa());
        pestañas.addTab("Preferencias", crearPanelPreferencias());
        pestañas.addTab("Ticket", crearPanelTicket());
        
        
        add(pestañas, BorderLayout.CENTER);
    }
    
    private JPanel crearPanelDatosEmpresa() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        JLabel lblLogo = new JLabel("Logo de la Empresa:");
        lblLogo.setFont(new Font("Arial", Font.BOLD, 12));
        
        lblLogoPreview = new JLabel();
        lblLogoPreview.setPreferredSize(new Dimension(100, 100));
        lblLogoPreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lblLogoPreview.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogoPreview.setText("Sin logo");
        
        JButton btnSeleccionarLogo = new JButton("Seleccionar Logo");
        btnSeleccionarLogo.setBackground(new Color(79, 70, 229));
        btnSeleccionarLogo.setForeground(Color.BLACK);
        btnSeleccionarLogo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JLabel lblNombre = new JLabel("Nombre de la Empresa:");
        lblNombre.setFont(new Font("Arial", Font.BOLD, 12));
        txtEmpresaNombre = new JTextField(30);
        
        JLabel lblTelefono = new JLabel("Telefono:");
        lblTelefono.setFont(new Font("Arial", Font.BOLD, 12));
        txtEmpresaTelefono = new JTextField(30);
        
        JLabel lblDireccion = new JLabel("Direccion:");
        lblDireccion.setFont(new Font("Arial", Font.BOLD, 12));
        txtEmpresaDireccion = new JTextField(30);
        
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Arial", Font.BOLD, 12));
        txtEmpresaEmail = new JTextField(30);
        
        JButton btnGuardar = new JButton("Guardar Cambios");
        btnGuardar.setBackground(new Color(34, 197, 94));
        btnGuardar.setForeground(Color.BLACK);
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 12));
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        int row = 0;
        
        gbc.gridx = 0; gbc.gridy = row; panel.add(lblLogo, gbc);
        gbc.gridx = 1; gbc.gridy = row; panel.add(lblLogoPreview, gbc);
        gbc.gridx = 2; gbc.gridy = row; panel.add(btnSeleccionarLogo, gbc);
        row++;
        
        gbc.gridx = 0; gbc.gridy = row; panel.add(lblNombre, gbc);
        gbc.gridx = 1; gbc.gridy = row; gbc.gridwidth = 2; panel.add(txtEmpresaNombre, gbc);
        gbc.gridwidth = 1; row++;
        
        gbc.gridx = 0; gbc.gridy = row; panel.add(lblTelefono, gbc);
        gbc.gridx = 1; gbc.gridy = row; gbc.gridwidth = 2; panel.add(txtEmpresaTelefono, gbc);
        gbc.gridwidth = 1; row++;
        
        gbc.gridx = 0; gbc.gridy = row; panel.add(lblDireccion, gbc);
        gbc.gridx = 1; gbc.gridy = row; gbc.gridwidth = 2; panel.add(txtEmpresaDireccion, gbc);
        gbc.gridwidth = 1; row++;
        
        gbc.gridx = 0; gbc.gridy = row; panel.add(lblEmail, gbc);
        gbc.gridx = 1; gbc.gridy = row; gbc.gridwidth = 2; panel.add(txtEmpresaEmail, gbc);
        gbc.gridwidth = 1; row++;
        
        gbc.gridx = 1; gbc.gridy = row; gbc.gridwidth = 2; panel.add(btnGuardar, gbc);
        
        btnSeleccionarLogo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                seleccionarLogo();
            }
        });
        
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarDatosEmpresa();
            }
        });
        
        return panel;
    }
    
    private JPanel crearPanelPreferencias() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        JLabel lblTiempoSesion = new JLabel("Tiempo de sesion (minutos):");
        lblTiempoSesion.setFont(new Font("Arial", Font.BOLD, 12));
        txtTiempoSesion = new JTextField(10);
        
        chkRecordarUsuario = new JCheckBox("Recordar ultimo usuario en login");
        chkRecordarUsuario.setFont(new Font("Arial", Font.PLAIN, 12));
        chkRecordarUsuario.setBackground(new Color(240, 248, 255));
        
        chkRespaldoAutomatico = new JCheckBox("Realizar respaldo automatico al cerrar");
        chkRespaldoAutomatico.setFont(new Font("Arial", Font.PLAIN, 12));
        chkRespaldoAutomatico.setBackground(new Color(240, 248, 255));
        
        JButton btnGuardar = new JButton("Guardar Preferencias");
        btnGuardar.setBackground(new Color(34, 197, 94));
        btnGuardar.setForeground(Color.BLACK);
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 12));
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; panel.add(lblTiempoSesion, gbc);
        gbc.gridx = 1; gbc.gridy = row; panel.add(txtTiempoSesion, gbc); row++;
        
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2; panel.add(chkRecordarUsuario, gbc); row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2; panel.add(chkRespaldoAutomatico, gbc); row++;
        
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2; panel.add(btnGuardar, gbc);
        
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarPreferencias();
            }
        });
        
        return panel;
    }
    
    private JPanel crearPanelTicket() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        
        JLabel lblMensaje = new JLabel("Mensaje en ticket:");
        lblMensaje.setFont(new Font("Arial", Font.BOLD, 12));
        
        txtTicketMensaje = new JTextArea(5, 40);
        txtTicketMensaje.setFont(new Font("Arial", Font.PLAIN, 12));
        txtTicketMensaje.setLineWrap(true);
        txtTicketMensaje.setWrapStyleWord(true);
        JScrollPane scrollMensaje = new JScrollPane(txtTicketMensaje);
        
        JButton btnGuardar = new JButton("Guardar Mensaje");
        btnGuardar.setBackground(new Color(34, 197, 94));
        btnGuardar.setForeground(Color.BLACK);
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 12));
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        gbc.gridx = 0; gbc.gridy = 0; panel.add(lblMensaje, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(scrollMensaje, gbc);
        gbc.gridx = 0; gbc.gridy = 2; panel.add(btnGuardar, gbc);
        
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarMensajeTicket();
            }
        });
        
        return panel;
    }
    
    private JPanel crearPanelRespaldos() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel lblInfo = new JLabel("Realice un respaldo manual de la base de datos");
        lblInfo.setFont(new Font("Arial", Font.BOLD, 14));
        
        JButton btnRespaldar = new JButton("Realizar Respaldo");
        btnRespaldar.setBackground(new Color(79, 70, 229));
        btnRespaldar.setForeground(Color.BLACK);
        btnRespaldar.setFont(new Font("Arial", Font.BOLD, 12));
        btnRespaldar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRespaldar.setPreferredSize(new Dimension(200, 40));
        
        JLabel lblUbicacion = new JLabel("Los respaldos se guardan en la carpeta seleccionada");
        lblUbicacion.setFont(new Font("Arial", Font.PLAIN, 11));
        lblUbicacion.setForeground(Color.GRAY);
        
        gbc.gridx = 0; gbc.gridy = 0; panel.add(lblInfo, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(btnRespaldar, gbc);
        gbc.gridx = 0; gbc.gridy = 2; panel.add(lblUbicacion, gbc);
        
        btnRespaldar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                realizarRespaldo();
            }
        });
        
        return panel;
    }
    
    private void cargarConfiguraciones() {
        txtEmpresaNombre.setText(controladorConfig.getConfiguracion("empresa_nombre"));
        txtEmpresaTelefono.setText(controladorConfig.getConfiguracion("empresa_telefono"));
        txtEmpresaDireccion.setText(controladorConfig.getConfiguracion("empresa_direccion"));
        txtEmpresaEmail.setText(controladorConfig.getConfiguracion("empresa_email"));
        txtTiempoSesion.setText(controladorConfig.getConfiguracion("tiempo_sesion_minutos"));
        chkRecordarUsuario.setSelected(controladorConfig.getConfiguracion("recordar_usuario").equals("true"));
        chkRespaldoAutomatico.setSelected(controladorConfig.getConfiguracion("respaldo_automatico").equals("true"));
        txtTicketMensaje.setText(controladorConfig.getConfiguracion("ticket_mensaje"));
        
        String rutaLogo = controladorConfig.getConfiguracion("empresa_logo_ruta");
        if (rutaLogo != null && !rutaLogo.isEmpty()) {
            try {
                ImageIcon icono = new ImageIcon(rutaLogo);
                Image img = icono.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                lblLogoPreview.setIcon(new ImageIcon(img));
                lblLogoPreview.setText("");
            } catch (Exception e) {
                lblLogoPreview.setText("Sin logo");
            }
        }
    }
    
    private void seleccionarLogo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "Imagenes", "jpg", "jpeg", "png", "gif"));
        
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            rutaLogoSeleccionado = fileChooser.getSelectedFile().getAbsolutePath();
            try {
                ImageIcon icono = new ImageIcon(rutaLogoSeleccionado);
                Image img = icono.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                lblLogoPreview.setIcon(new ImageIcon(img));
                lblLogoPreview.setText("");
            } catch (Exception e) {
                lblLogoPreview.setText("Error al cargar");
            }
        }
    }
    
    private void guardarDatosEmpresa() {
        controladorConfig.setConfiguracion("empresa_nombre", txtEmpresaNombre.getText());
        controladorConfig.setConfiguracion("empresa_telefono", txtEmpresaTelefono.getText());
        controladorConfig.setConfiguracion("empresa_direccion", txtEmpresaDireccion.getText());
        controladorConfig.setConfiguracion("empresa_email", txtEmpresaEmail.getText());
        
        if (rutaLogoSeleccionado != null) {
            controladorConfig.cambiarLogo(rutaLogoSeleccionado);
        }
        
        JOptionPane.showMessageDialog(this, "Datos guardados correctamente.\nLos cambios se veran al reiniciar sesion.");
    }
    
    private void guardarPreferencias() {
        controladorConfig.setConfiguracion("tiempo_sesion_minutos", txtTiempoSesion.getText());
        controladorConfig.setConfiguracion("recordar_usuario", String.valueOf(chkRecordarUsuario.isSelected()));
        controladorConfig.setConfiguracion("respaldo_automatico", String.valueOf(chkRespaldoAutomatico.isSelected()));
        
        JOptionPane.showMessageDialog(this, "Preferencias guardadas");
    }
    
    private void guardarMensajeTicket() {
        controladorConfig.setConfiguracion("ticket_mensaje", txtTicketMensaje.getText());
        JOptionPane.showMessageDialog(this, "Mensaje guardado");
    }
    
    private void realizarRespaldo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar carpeta para respaldo");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            String ruta = fileChooser.getSelectedFile().getAbsolutePath();
            boolean exito = controladorConfig.respaldarBaseDatos(ruta);
            
            if (exito) {
                JOptionPane.showMessageDialog(this, "Respaldo realizado correctamente en:\n" + ruta);
            } else {
                JOptionPane.showMessageDialog(this, "Error al realizar el respaldo");
            }
        }
    }
    
    public void refrescar() {
        cargarConfiguraciones();
    }
 

    
}