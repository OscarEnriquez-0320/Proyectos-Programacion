package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import Controlador.ControladorCaja;
import Controlador.ControladorConfiguracion;
import Controlador.ControladorLogin;
import Modelo.Usuario;

public class VistaLogin extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnIngresar;
    private JToggleButton btnMostrarOcultar;
    private JCheckBox chkRecordar;
    private JLabel lblError;
    private JLabel lblFechaHora;
    private JLabel lblMensajeEstado;
    private JLabel lblLogo;
    private ControladorLogin controlador;
    private ControladorConfiguracion controladorConfig;
    private VistaLogin vista;
    private Timer timer;
    
    public VistaLogin() {
        controlador = new ControladorLogin(this);
        controladorConfig = new ControladorConfiguracion();
        
        setTitle("Abarrotes Felipe - Sistema de Punto de Venta");
        setSize(500, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 248, 255));
        
        iniciarComponentes();
        cargarLogo();
        iniciarReloj();
        cargarUsuarioRecordado();
    }
    
    private void cargarLogo() {
        String rutaLogo = controladorConfig.getConfiguracion("empresa_logo_ruta");
        
        if (rutaLogo != null && !rutaLogo.isEmpty()) {
            File archivoLogo = new File(rutaLogo);
            if (archivoLogo.exists()) {
                try {
                    ImageIcon iconoOriginal = new ImageIcon(rutaLogo);
                    Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    lblLogo.setIcon(new ImageIcon(imagenEscalada));
                    lblLogo.setText("");
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
        lblLogo.setText("🛒");
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 60));
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
    }
    
    private void iniciarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(240, 248, 255));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
        panelSuperior.setBackground(new Color(240, 248, 255));
        
        JPanel panelLogo = new JPanel();
        panelLogo.setBackground(new Color(240, 248, 255));
        lblLogo = new JLabel();
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        panelLogo.add(lblLogo);
        
        JLabel lblTitulo = new JLabel("Abarrotes Felipe");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(79, 70, 229));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblSubtitulo = new JLabel("Sistema de Punto de Venta");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setForeground(Color.GRAY);
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panelSuperior.add(panelLogo);
        panelSuperior.add(Box.createRigidArea(new Dimension(0, 10)));
        panelSuperior.add(lblTitulo);
        panelSuperior.add(Box.createRigidArea(new Dimension(0, 5)));
        panelSuperior.add(lblSubtitulo);
        
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        

        JPanel panelFormulario = new JPanel();
        panelFormulario.setBackground(new Color(240, 248, 255));
        panelFormulario.setLayout(new GridBagLayout());
        
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 13));
        txtUsuario = new JTextField(20);
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUsuario.setPreferredSize(new Dimension(220, 40));
        txtUsuario.setMinimumSize(new Dimension(220, 40));
        
        
        
        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 13));
        txtPassword = new JPasswordField(20);
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword.setPreferredSize(new Dimension(220, 40));
        txtPassword.setMinimumSize(new Dimension(220, 40));
        

        btnMostrarOcultar = new JToggleButton();
        btnMostrarOcultar.setPreferredSize(new Dimension(45, 35));
        btnMostrarOcultar.setBackground(Color.WHITE);
        btnMostrarOcultar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnMostrarOcultar.setBorderPainted(false);
        btnMostrarOcultar.setFocusPainted(false);
        

        File iconoOpen = new File("recursos/eye_open.png");
        File iconoClosed = new File("recursos/eye_closed.png");
        
        if (iconoOpen.exists() && iconoClosed.exists()) {
            ImageIcon openIcon = new ImageIcon(iconoOpen.getAbsolutePath());
            ImageIcon closedIcon = new ImageIcon(iconoClosed.getAbsolutePath());
            

            Image openImg = openIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            Image closedImg = closedIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            
            btnMostrarOcultar.setIcon(new ImageIcon(openImg));
            btnMostrarOcultar.setSelectedIcon(new ImageIcon(closedImg));
        } 
        
       
        btnMostrarOcultar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (btnMostrarOcultar.isSelected()) {
                    txtPassword.setEchoChar((char) 0);
                } else {
                    txtPassword.setEchoChar('•');
                }
            }
        });
        
        chkRecordar = new JCheckBox("Recordar usuario");
        chkRecordar.setEnabled(false);
        chkRecordar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        chkRecordar.setBackground(new Color(240, 248, 255));
        chkRecordar.setForeground(Color.BLACK);
        
        btnIngresar = new JButton("INGRESAR AL SISTEMA");
        btnIngresar.setBackground(new Color(79, 70, 229));
        btnIngresar.setForeground(new Color(0, 0, 0));
        btnIngresar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnIngresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnIngresar.setPreferredSize(new Dimension(250, 45));
        
        lblError = new JLabel("");
        lblError.setForeground(Color.RED);
        lblError.setFont(new Font("Segoe UI", Font.BOLD, 11));
        

        GridBagConstraints gbcUsuarioLabel = new GridBagConstraints();
        gbcUsuarioLabel.insets = new Insets(8, 10, 8, 10);
        gbcUsuarioLabel.gridx = 0;
        gbcUsuarioLabel.gridy = 0;
        gbcUsuarioLabel.anchor = GridBagConstraints.EAST;
        panelFormulario.add(lblUsuario, gbcUsuarioLabel);
        
        GridBagConstraints gbcUsuarioField = new GridBagConstraints();
        gbcUsuarioField.insets = new Insets(8, 10, 8, 10);
        gbcUsuarioField.gridx = 1;
        gbcUsuarioField.gridy = 0;
        gbcUsuarioField.gridwidth = 2;
        gbcUsuarioField.anchor = GridBagConstraints.WEST;
        panelFormulario.add(txtUsuario, gbcUsuarioField);

        GridBagConstraints gbcPassLabel = new GridBagConstraints();
        gbcPassLabel.insets = new Insets(8, 10, 8, 10);
        gbcPassLabel.gridx = 0;
        gbcPassLabel.gridy = 1;
        gbcPassLabel.anchor = GridBagConstraints.EAST;
        panelFormulario.add(lblPassword, gbcPassLabel);
        
        JPanel panelPassword = new JPanel(new BorderLayout());
        panelPassword.add(txtPassword, BorderLayout.CENTER);
        panelPassword.add(btnMostrarOcultar, BorderLayout.EAST);
        
        GridBagConstraints gbcPassField = new GridBagConstraints();
        gbcPassField.insets = new Insets(8, 10, 8, 10);
        gbcPassField.gridx = 1;
        gbcPassField.gridy = 1;
        gbcPassField.gridwidth = 2;
        gbcPassField.anchor = GridBagConstraints.WEST;
        panelFormulario.add(panelPassword, gbcPassField);
        

        GridBagConstraints gbcRecordar = new GridBagConstraints();
        gbcRecordar.insets = new Insets(8, 10, 8, 10);
        gbcRecordar.gridx = 1;
        gbcRecordar.gridy = 2;
        gbcRecordar.gridwidth = 2;
        gbcRecordar.anchor = GridBagConstraints.WEST;
        panelFormulario.add(chkRecordar, gbcRecordar);
        
 
        GridBagConstraints gbcBoton = new GridBagConstraints();
        gbcBoton.insets = new Insets(15, 10, 10, 10);
        gbcBoton.gridx = 0;
        gbcBoton.gridy = 3;
        gbcBoton.gridwidth = 3;
        gbcBoton.anchor = GridBagConstraints.CENTER;
        panelFormulario.add(btnIngresar, gbcBoton);
        

        GridBagConstraints gbcError = new GridBagConstraints();
        gbcError.insets = new Insets(5, 10, 10, 10);
        gbcError.gridx = 0;
        gbcError.gridy = 4;
        gbcError.gridwidth = 3;
        gbcError.anchor = GridBagConstraints.CENTER;
        panelFormulario.add(lblError, gbcError);
        
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBackground(new Color(240, 248, 255));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        lblFechaHora = new JLabel();
        lblFechaHora.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblFechaHora.setForeground(Color.GRAY);
        
        lblMensajeEstado = new JLabel(" Listo para iniciar sesion");
        lblMensajeEstado.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblMensajeEstado.setForeground(new Color(34, 197, 94));
        
        panelInferior.add(lblFechaHora, BorderLayout.WEST);
        panelInferior.add(lblMensajeEstado, BorderLayout.EAST);
        
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);
        
        getContentPane().add(panelPrincipal, BorderLayout.CENTER);
        

        btnIngresar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controlador.autenticar();
            }
        });
        
        txtUsuario.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    controlador.autenticar();
                }
                actualizarMensajeEstado("Ingresando usuario...");
            }
            public void keyReleased(KeyEvent e) {
                actualizarMensajeEstado("✅ Listo para iniciar sesion");
            }
        });
        
        txtPassword.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    controlador.autenticar();
                }
            }
        });
    }
    private void iniciarReloj() {
        timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        lblFechaHora.setText("📅 " + sdf.format(new Date()));
                    }
                });
            }
        }, 0, 1000);
    }
    
    private void cargarUsuarioRecordado() {
        java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(VistaLogin.class);
        String usuarioRecordado = prefs.get("usuario_recordado", "");
        boolean recordar = prefs.getBoolean("recordar_usuario", false);
        
        if (recordar && !usuarioRecordado.isEmpty()) {
            txtUsuario.setText(usuarioRecordado);
            chkRecordar.setSelected(true);
            txtPassword.requestFocus();
            actualizarMensajeEstado("Usuario cargado. Ingrese contraseña.");
        }
    }
    
    public void guardarUsuarioRecordado(String usuario, boolean recordar) {
        java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(VistaLogin.class);
        if (recordar) {
            prefs.put("usuario_recordado", usuario);
            prefs.putBoolean("recordar_usuario", true);
        } else {
            prefs.remove("usuario_recordado");
            prefs.putBoolean("recordar_usuario", false);
        }
    }
    
    public String getUsuario() {
        return txtUsuario.getText();
    }
    
    public String getPassword() {
        return new String(txtPassword.getPassword());
    }
    
    public boolean isRecordarUsuario() {
        return chkRecordar.isSelected();
    }
    
    public void mostrarError(String mensaje) {
        lblError.setText(mensaje);
        actualizarMensajeEstado("❌ " + mensaje);
        
        Timer timerError = new Timer(true);
        timerError.schedule(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        lblError.setText("");
                        actualizarMensajeEstado(" Listo para iniciar sesion");
                    }
                });
            }
        }, 3000);
    }
    
    public void actualizarMensajeEstado(String mensaje) {
        lblMensajeEstado.setText(mensaje);
    }
    
    private void abrirPrincipal(Usuario usuario) {
        ControladorCaja controladorCaja = new ControladorCaja();
        int idCorte = -1;
        
        if (controladorCaja.hayCajaAbierta()) {
            ControladorCaja.CorteInfo corte = controladorCaja.obtenerCajaAbiertaInfo();
            if (corte != null) {
                idCorte = corte.id;
            }
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
                ControladorCaja.CorteInfo corte = controladorCaja.obtenerCajaAbiertaInfo();
                if (corte != null) {
                    idCorte = corte.id;
                }
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