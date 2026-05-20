package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import Modelo.Usuario;

public class VistaFormularioUsuario extends JDialog {
    private VistaUsuarios vistaUsuarios;
    private Usuario usuarioEditar;
    private boolean esEdicion;
    
    private JTextField txtNombreUsuario;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmarPassword;
    private JTextField txtNombreCompleto;
    private JComboBox<String> cmbRol;
    private JTabbedPane tabbedPane;
    
  
    private JLabel lblImagen;
    private JButton btnSeleccionarImagen;
    private String nombreImagenGuardada;
    private static final String RUTA_IMAGENES = "img_usuarios/";
    

    private JCheckBox chkPosEscritura;
    private JCheckBox chkVentasLecturaPropias;
    private JCheckBox chkTicketsReimprimirPropios;
    

    private JCheckBox chkProductosLectura;
    private JCheckBox chkClientesLectura;
    private JCheckBox chkClientesEscritura;
    

    private JCheckBox chkProveedoresLectura;
    private JCheckBox chkUsuariosLectura;
    private JCheckBox chkUsuariosEscritura;
    

    private JCheckBox chkCorteLectura;
    private JCheckBox chkDevolucionesLectura;
    private JCheckBox chkDevolucionesEscritura;
    private JCheckBox chkReportesLectura;
    private JCheckBox chkConfiguracionEscritura;
    
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JLabel lblError;
    
    private String[] roles = {"Cajero", "Administrador"};
    
    public VistaFormularioUsuario(JFrame padre, VistaUsuarios vista, Usuario usuario) {
        super(padre, usuario == null ? "Agregar Usuario" : "Editar Usuario", true);
        this.vistaUsuarios = vista;
        this.usuarioEditar = usuario;
        this.esEdicion = usuario != null;
        
        setSize(700, 550);
        setLocationRelativeTo(padre);
        setResizable(false);
        getContentPane().setLayout(new BorderLayout());
        
        File carpeta = new File(RUTA_IMAGENES);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
        
        iniciarComponentes();
        
        if (usuarioEditar != null) {
            cargarDatosUsuario();
        }
        
        configurarEventos();
    }
    
    private void iniciarComponentes() {
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(79, 70, 229));
        panelTitulo.setPreferredSize(new Dimension(getWidth(), 50));
        
        String titulo = usuarioEditar == null ? "AGREGAR USUARIO" : "EDITAR USUARIO";
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelTitulo.add(lblTitulo);
        
        getContentPane().add(panelTitulo, BorderLayout.NORTH);
        
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(240, 248, 255));
        

        JPanel panelDatos = new JPanel(new GridBagLayout());
        panelDatos.setBackground(new Color(240, 248, 255));
        panelDatos.setBorder(BorderFactory.createTitledBorder("Datos del Usuario"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        JLabel lblNombreUsuario = new JLabel("Nombre Usuario:");
        lblNombreUsuario.setFont(new Font("Arial", Font.BOLD, 12));
        txtNombreUsuario = new JTextField(15);
        
        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(new Font("Arial", Font.BOLD, 12));
        txtPassword = new JPasswordField(15);
        
        JLabel lblConfirmar = new JLabel("Confirmar:");
        lblConfirmar.setFont(new Font("Arial", Font.BOLD, 12));
        txtConfirmarPassword = new JPasswordField(15);
        
        JLabel lblNombreCompleto = new JLabel("Nombre Completo:");
        lblNombreCompleto.setFont(new Font("Arial", Font.BOLD, 12));
        txtNombreCompleto = new JTextField(15);
        
        JLabel lblRol = new JLabel("Rol:");
        lblRol.setFont(new Font("Arial", Font.BOLD, 12));
        cmbRol = new JComboBox<>(roles);
        

        JLabel lblImagenTitulo = new JLabel("Imagen:");
        lblImagenTitulo.setFont(new Font("Arial", Font.BOLD, 12));
        
        lblImagen = new JLabel();
        lblImagen.setPreferredSize(new Dimension(80, 80));
        lblImagen.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagen.setVerticalAlignment(SwingConstants.CENTER);
        lblImagen.setText("Sin imagen");
        
        btnSeleccionarImagen = new JButton("Seleccionar Imagen");
        btnSeleccionarImagen.setBackground(new Color(59, 130, 246));
        btnSeleccionarImagen.setForeground(Color.BLACK);
        btnSeleccionarImagen.setFont(new Font("Arial", Font.BOLD, 10));
        
        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; panelDatos.add(lblNombreUsuario, gbc);
        gbc.gridx = 1; gbc.gridy = row; panelDatos.add(txtNombreUsuario, gbc); row++;
        
        gbc.gridx = 0; gbc.gridy = row; panelDatos.add(lblPassword, gbc);
        gbc.gridx = 1; gbc.gridy = row; panelDatos.add(txtPassword, gbc); row++;
        
        gbc.gridx = 0; gbc.gridy = row; panelDatos.add(lblConfirmar, gbc);
        gbc.gridx = 1; gbc.gridy = row; panelDatos.add(txtConfirmarPassword, gbc); row++;
        
        gbc.gridx = 0; gbc.gridy = row; panelDatos.add(lblNombreCompleto, gbc);
        gbc.gridx = 1; gbc.gridy = row; panelDatos.add(txtNombreCompleto, gbc); row++;
        
        gbc.gridx = 0; gbc.gridy = row; panelDatos.add(lblRol, gbc);
        gbc.gridx = 1; gbc.gridy = row; panelDatos.add(cmbRol, gbc); row++;
        
        gbc.gridx = 0; gbc.gridy = row; panelDatos.add(lblImagenTitulo, gbc);
        JPanel panelImagen = new JPanel(new BorderLayout());
        panelImagen.add(lblImagen, BorderLayout.CENTER);
        panelImagen.add(btnSeleccionarImagen, BorderLayout.SOUTH);
        gbc.gridx = 1; gbc.gridy = row; panelDatos.add(panelImagen, gbc);
        
        panelPrincipal.add(panelDatos, BorderLayout.WEST);
        

        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 12));
        

        JPanel panelVentas = crearPanelVentas();
        tabbedPane.addTab("Ventas", panelVentas);
        

        JPanel panelInventario = crearPanelInventario();
        tabbedPane.addTab("Inventario", panelInventario);

        JPanel panelAdministracion = crearPanelAdministracion();
        tabbedPane.addTab("Administracion", panelAdministracion);
        

        JPanel panelSistema = crearPanelSistema();
        tabbedPane.addTab("Sistema", panelSistema);
        
        panelPrincipal.add(tabbedPane, BorderLayout.CENTER);
        
        getContentPane().add(panelPrincipal, BorderLayout.CENTER);
        

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.setBackground(new Color(240, 248, 255));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(new Color(34, 197, 94));
        btnGuardar.setForeground(Color.BLACK);
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 12));
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(107, 114, 128));
        btnCancelar.setForeground(Color.BLACK);
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 12));
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        lblError = new JLabel("");
        lblError.setForeground(Color.RED);
        lblError.setFont(new Font("Arial", Font.BOLD, 11));
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        getContentPane().add(panelBotones, BorderLayout.SOUTH);
        getContentPane().add(lblError, BorderLayout.SOUTH);
        
        btnSeleccionarImagen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                seleccionarImagen();
            }
        });
        
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarUsuario();
            }
        });
        
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    private JPanel crearPanelVentas() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        chkPosEscritura = new JCheckBox("Punto de Venta - Realizar ventas");
        chkPosEscritura.setFont(new Font("Arial", Font.PLAIN, 12));
        chkPosEscritura.setToolTipText("Permite acceder al modulo de ventas y realizar transacciones");
        
        chkVentasLecturaPropias = new JCheckBox("Historial de Ventas - Ver solo sus propias ventas");
        chkVentasLecturaPropias.setFont(new Font("Arial", Font.PLAIN, 12));
        chkVentasLecturaPropias.setToolTipText("Permite ver el historial de sus propias ventas");
        
        chkTicketsReimprimirPropios = new JCheckBox("Tickets - Reimprimir sus propios tickets");
        chkTicketsReimprimirPropios.setFont(new Font("Arial", Font.PLAIN, 12));
        chkTicketsReimprimirPropios.setToolTipText("Permite reimprimir tickets de ventas realizadas por el usuario");
        
        gbc.gridx = 0; gbc.gridy = 0; panel.add(chkPosEscritura, gbc);
        gbc.gridy = 1; panel.add(chkVentasLecturaPropias, gbc);
        gbc.gridy = 2; panel.add(chkTicketsReimprimirPropios, gbc);
        
        return panel;
    }
    
    private JPanel crearPanelInventario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        chkProductosLectura = new JCheckBox("Productos - Consultar stock (Solo lectura)");
        chkProductosLectura.setFont(new Font("Arial", Font.PLAIN, 12));
        chkProductosLectura.setToolTipText("Permite ver los productos y su stock, pero no puede modificarlos");
        
        chkClientesLectura = new JCheckBox("Clientes - Consultar clientes (Lectura)");
        chkClientesLectura.setFont(new Font("Arial", Font.PLAIN, 12));
        chkClientesLectura.setToolTipText("Permite ver la lista de clientes");
        
        chkClientesEscritura = new JCheckBox("Clientes - Agregar/Editar clientes (Escritura)");
        chkClientesEscritura.setFont(new Font("Arial", Font.PLAIN, 12));
        chkClientesEscritura.setToolTipText("Permite agregar nuevos clientes o editar existentes");
        
        gbc.gridx = 0; gbc.gridy = 0; panel.add(chkProductosLectura, gbc);
        gbc.gridy = 1; panel.add(chkClientesLectura, gbc);
        gbc.gridy = 2; panel.add(chkClientesEscritura, gbc);
        
        return panel;
    }
    
    private JPanel crearPanelAdministracion() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        chkProveedoresLectura = new JCheckBox("Proveedores - Consultar proveedores");
        chkProveedoresLectura.setFont(new Font("Arial", Font.PLAIN, 12));
        chkProveedoresLectura.setToolTipText("Permite ver la lista de proveedores");
        
        chkUsuariosLectura = new JCheckBox("Usuarios - Consultar usuarios (Solo lectura)");
        chkUsuariosLectura.setFont(new Font("Arial", Font.PLAIN, 12));
        chkUsuariosLectura.setToolTipText("Permite ver la lista de usuarios del sistema");
        
        chkUsuariosEscritura = new JCheckBox("Usuarios - Agregar/Editar/Eliminar usuarios");
        chkUsuariosEscritura.setFont(new Font("Arial", Font.PLAIN, 12));
        chkUsuariosEscritura.setToolTipText("Permite administrar usuarios (requiere rol Administrador)");
        
        gbc.gridx = 0; gbc.gridy = 0; panel.add(chkProveedoresLectura, gbc);
        gbc.gridy = 1; panel.add(chkUsuariosLectura, gbc);
        gbc.gridy = 2; panel.add(chkUsuariosEscritura, gbc);
        
        return panel;
    }
    
    private JPanel crearPanelSistema() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        chkCorteLectura = new JCheckBox("Corte de Caja - Consultar estado (Solo lectura)");
        chkCorteLectura.setFont(new Font("Arial", Font.PLAIN, 12));
        chkCorteLectura.setToolTipText("Permite ver el estado de la caja, pero no puede cerrarla");
        
        chkDevolucionesLectura = new JCheckBox("Devoluciones - Consultar historial (Solo lectura)");
        chkDevolucionesLectura.setFont(new Font("Arial", Font.PLAIN, 12));
        chkDevolucionesLectura.setToolTipText("Permite ver el historial de devoluciones");
        
        chkDevolucionesEscritura = new JCheckBox("Devoluciones - Registrar devoluciones");
        chkDevolucionesEscritura.setFont(new Font("Arial", Font.PLAIN, 12));
        chkDevolucionesEscritura.setToolTipText("Permite realizar devoluciones de productos");
        
        chkReportesLectura = new JCheckBox("Reportes - Generar reportes");
        chkReportesLectura.setFont(new Font("Arial", Font.PLAIN, 12));
        chkReportesLectura.setToolTipText("Permite acceder al modulo de reportes");
        
        chkConfiguracionEscritura = new JCheckBox("Configuracion - Modificar configuracion del sistema");
        chkConfiguracionEscritura.setFont(new Font("Arial", Font.PLAIN, 12));
        chkConfiguracionEscritura.setToolTipText("Permite cambiar la configuracion del sistema (requiere Administrador)");
        
        gbc.gridx = 0; gbc.gridy = 0; panel.add(chkCorteLectura, gbc);
        gbc.gridy = 1; panel.add(chkDevolucionesLectura, gbc);
        gbc.gridy = 2; panel.add(chkDevolucionesEscritura, gbc);
        gbc.gridy = 3; panel.add(chkReportesLectura, gbc);
        gbc.gridy = 4; panel.add(chkConfiguracionEscritura, gbc);
        
        return panel;
    }
    
    private void configurarEventos() {
        cmbRol.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean esAdmin = cmbRol.getSelectedItem().equals("Administrador");
                

                chkPosEscritura.setSelected(esAdmin);
                chkVentasLecturaPropias.setSelected(esAdmin);
                chkTicketsReimprimirPropios.setSelected(esAdmin);
                chkProductosLectura.setSelected(esAdmin);
                chkClientesLectura.setSelected(esAdmin);
                chkClientesEscritura.setSelected(esAdmin);
                chkProveedoresLectura.setSelected(esAdmin);
                chkUsuariosLectura.setSelected(esAdmin);
                chkUsuariosEscritura.setSelected(esAdmin);
                chkCorteLectura.setSelected(esAdmin);
                chkDevolucionesLectura.setSelected(esAdmin);
                chkDevolucionesEscritura.setSelected(esAdmin);
                chkReportesLectura.setSelected(esAdmin);
                chkConfiguracionEscritura.setSelected(esAdmin);
                

                chkPosEscritura.setEnabled(!esAdmin);
                chkVentasLecturaPropias.setEnabled(!esAdmin);
                chkTicketsReimprimirPropios.setEnabled(!esAdmin);
                chkProductosLectura.setEnabled(!esAdmin);
                chkClientesLectura.setEnabled(!esAdmin);
                chkClientesEscritura.setEnabled(!esAdmin);
                chkProveedoresLectura.setEnabled(!esAdmin);
                chkUsuariosLectura.setEnabled(!esAdmin);
                chkUsuariosEscritura.setEnabled(!esAdmin);
                chkCorteLectura.setEnabled(!esAdmin);
                chkDevolucionesLectura.setEnabled(!esAdmin);
                chkDevolucionesEscritura.setEnabled(!esAdmin);
                chkReportesLectura.setEnabled(!esAdmin);
                chkConfiguracionEscritura.setEnabled(!esAdmin);
            }
        });
    }
    
    private void seleccionarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar Imagen para Usuario");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "Imagenes (*.jpg, *.jpeg, *.png, *.gif)", "jpg", "jpeg", "png", "gif"));
        
        int resultado = fileChooser.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoOrigen = fileChooser.getSelectedFile();
            
            String nombreArchivo = archivoOrigen.getName();
            String extension = "";
            int idx = nombreArchivo.lastIndexOf(".");
            if (idx > 0) {
                extension = nombreArchivo.substring(idx);
            }
            nombreImagenGuardada = System.currentTimeMillis() + extension;
            
            File destino = new File(RUTA_IMAGENES + nombreImagenGuardada);
            try {
                Files.copy(archivoOrigen.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                
                ImageIcon imagen = new ImageIcon(destino.getAbsolutePath());
                Image img = imagen.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                lblImagen.setIcon(new ImageIcon(img));
                lblImagen.setText("");
            } catch (Exception ex) {
                lblError.setText("Error al copiar la imagen: " + ex.getMessage());
            }
        }
    }
    
    private void cargarDatosUsuario() {
        txtNombreUsuario.setText(usuarioEditar.getNombreUsuario());
        txtNombreCompleto.setText(usuarioEditar.getNombreCompleto());
        cmbRol.setSelectedItem(usuarioEditar.getRol());
        
        String permisos = usuarioEditar.getPermisos();
        if (permisos != null && !permisos.equals("all")) {
            chkPosEscritura.setSelected(permisos.contains("pos:escritura"));
            chkVentasLecturaPropias.setSelected(permisos.contains("ventas:lectura_propias"));
            chkTicketsReimprimirPropios.setSelected(permisos.contains("tickets:reimprimir_propios"));
            chkProductosLectura.setSelected(permisos.contains("productos:lectura"));
            chkClientesLectura.setSelected(permisos.contains("clientes:lectura"));
            chkClientesEscritura.setSelected(permisos.contains("clientes:escritura"));
            chkProveedoresLectura.setSelected(permisos.contains("proveedores:lectura"));
            chkUsuariosLectura.setSelected(permisos.contains("usuarios:lectura"));
            chkUsuariosEscritura.setSelected(permisos.contains("usuarios:escritura"));
            chkCorteLectura.setSelected(permisos.contains("corte:lectura"));
            chkDevolucionesLectura.setSelected(permisos.contains("devoluciones:lectura"));
            chkDevolucionesEscritura.setSelected(permisos.contains("devoluciones:escritura"));
            chkReportesLectura.setSelected(permisos.contains("reportes:lectura"));
            chkConfiguracionEscritura.setSelected(permisos.contains("configuracion:escritura"));
        } else if (permisos != null && permisos.equals("all")) {

            chkPosEscritura.setSelected(true);
            chkVentasLecturaPropias.setSelected(true);
            chkTicketsReimprimirPropios.setSelected(true);
            chkProductosLectura.setSelected(true);
            chkClientesLectura.setSelected(true);
            chkClientesEscritura.setSelected(true);
            chkProveedoresLectura.setSelected(true);
            chkUsuariosLectura.setSelected(true);
            chkUsuariosEscritura.setSelected(true);
            chkCorteLectura.setSelected(true);
            chkDevolucionesLectura.setSelected(true);
            chkDevolucionesEscritura.setSelected(true);
            chkReportesLectura.setSelected(true);
            chkConfiguracionEscritura.setSelected(true);
        }
        
        if (usuarioEditar.getRutaImagen() != null && !usuarioEditar.getRutaImagen().isEmpty()) {
            File archivoImagen = new File(usuarioEditar.getRutaImagen());
            if (archivoImagen.exists()) {
                try {
                    ImageIcon imagen = new ImageIcon(archivoImagen.getAbsolutePath());
                    Image img = imagen.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                    lblImagen.setIcon(new ImageIcon(img));
                    lblImagen.setText("");
                } catch (Exception e) {
                    lblImagen.setText("Sin imagen");
                }
            }
        }
        
        txtPassword.setText("");
        txtConfirmarPassword.setText("");
        
        if (usuarioEditar.getRol().equals("Administrador")) {
            chkPosEscritura.setEnabled(false);
            chkVentasLecturaPropias.setEnabled(false);
            chkTicketsReimprimirPropios.setEnabled(false);
            chkProductosLectura.setEnabled(false);
            chkClientesLectura.setEnabled(false);
            chkClientesEscritura.setEnabled(false);
            chkProveedoresLectura.setEnabled(false);
            chkUsuariosLectura.setEnabled(false);
            chkUsuariosEscritura.setEnabled(false);
            chkCorteLectura.setEnabled(false);
            chkDevolucionesLectura.setEnabled(false);
            chkDevolucionesEscritura.setEnabled(false);
            chkReportesLectura.setEnabled(false);
            chkConfiguracionEscritura.setEnabled(false);
        }
    }
    
    private String construirPermisos() {
        if (cmbRol.getSelectedItem().equals("Administrador")) {
            return "all";
        }
        
        List<String> permisos = new ArrayList<>();
        
        if (chkPosEscritura.isSelected()) permisos.add("pos:escritura");
        if (chkVentasLecturaPropias.isSelected()) permisos.add("ventas:lectura_propias");
        if (chkTicketsReimprimirPropios.isSelected()) permisos.add("tickets:reimprimir_propios");
        if (chkProductosLectura.isSelected()) permisos.add("productos:lectura");
        if (chkClientesLectura.isSelected()) permisos.add("clientes:lectura");
        if (chkClientesEscritura.isSelected()) permisos.add("clientes:escritura");
        if (chkProveedoresLectura.isSelected()) permisos.add("proveedores:lectura");
        if (chkUsuariosLectura.isSelected()) permisos.add("usuarios:lectura");
        if (chkUsuariosEscritura.isSelected()) permisos.add("usuarios:escritura");
        if (chkCorteLectura.isSelected()) permisos.add("corte:lectura");
        if (chkDevolucionesLectura.isSelected()) permisos.add("devoluciones:lectura");
        if (chkDevolucionesEscritura.isSelected()) permisos.add("devoluciones:escritura");
        if (chkReportesLectura.isSelected()) permisos.add("reportes:lectura");
        if (chkConfiguracionEscritura.isSelected()) permisos.add("configuracion:escritura");
        
        StringBuilder sb = new StringBuilder();
        for (String p : permisos) {
            sb.append(p).append(",");
        }
        String resultado = sb.toString();
        if (resultado.endsWith(",")) {
            resultado = resultado.substring(0, resultado.length() - 1);
        }
        
        return resultado;
    }
    
    private Usuario construirUsuario() {
        String nombreUsuario = txtNombreUsuario.getText().trim();
        String nombreCompleto = txtNombreCompleto.getText().trim();
        String rol = (String) cmbRol.getSelectedItem();
        
        if (nombreUsuario.isEmpty()) {
            lblError.setText("El nombre de usuario es obligatorio");
            return null;
        }
        
        if (nombreCompleto.isEmpty()) {
            lblError.setText("El nombre completo es obligatorio");
            return null;
        }
        

        String password = new String(txtPassword.getPassword());
        String confirmar = new String(txtConfirmarPassword.getPassword());
        
        if (usuarioEditar == null && password.isEmpty()) {
            lblError.setText("La contraseña es obligatoria");
            return null;
        }
        
        if (!password.equals(confirmar)) {
            lblError.setText("Las contraseñas no coinciden");
            return null;
        }
        
        Usuario usuario;
        if (usuarioEditar == null) {
            usuario = new Usuario();
        } else {
            usuario = usuarioEditar;
        }
        
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setNombreCompleto(nombreCompleto);
        usuario.setRol(rol);
        usuario.setPermisos(construirPermisos());
        
        if (nombreImagenGuardada != null && !nombreImagenGuardada.isEmpty()) {
            usuario.setRutaImagen(RUTA_IMAGENES + nombreImagenGuardada);
        }
        
        return usuario;
    }
    
    private void guardarUsuario() {
        Usuario usuario = construirUsuario();
        if (usuario == null) return;
        
        String password = new String(txtPassword.getPassword());
        
        if (vistaUsuarios != null) {
            vistaUsuarios.solicitarGuardarUsuario(usuario, password, esEdicion ? usuarioEditar.getId() : 0);
        }
    }
}