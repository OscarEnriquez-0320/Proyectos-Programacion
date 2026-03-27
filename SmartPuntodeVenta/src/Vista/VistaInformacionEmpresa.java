package Vista;

import Librerias.AlmacenamientoDatos;
import Modelo.Empresa;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class VistaInformacionEmpresa extends JPanel {
    private AlmacenamientoDatos datos;
    private Empresa empresa;
    
    // Campos del formulario
    private JTextField txtNombreEmpresa;
    private JTextField txtEslogan;
    private JTextField txtRtn;
    private JTextField txtTelefono;
    private JTextField txtEmail;
    private JTextField txtSitioWeb;
    private JTextField txtDireccion;
    private JTextField txtCiudad;
    private JTextField txtDepartamento;
    private JTextField txtCodigoPostal;
    private JTextField txtCorreoRecuperacion;
    
    private JLabel lblLogo;
    private String rutaLogoActual;
    
    private JComboBox<String> cmbFormatoFecha;
    private JComboBox<String> cmbFormatoMoneda;
    
    public VistaInformacionEmpresa() {
        datos = AlmacenamientoDatos.getInstancia();
        empresa = datos.obtenerEmpresa();
        if (empresa == null) {
            empresa = new Empresa();
            datos.actualizarEmpresa(empresa);
        }
        
        iniciarComponentes();
        cargarDatos();
    }
    
    private void iniciarComponentes() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));
        
        // Título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(52, 73, 94));
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel lblTitulo = new JLabel("INFORMACIÓN DE LA EMPRESA");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);
        
        add(panelTitulo, BorderLayout.NORTH);
        
        // Panel central con scroll
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBackground(Color.WHITE);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Sección 1: Datos Generales
        JPanel panelDatosGenerales = crearSeccionDatosGenerales();
        panelCentral.add(panelDatosGenerales);
        panelCentral.add(Box.createVerticalStrut(15));
        
        // Sección 2: Dirección Fiscal
        JPanel panelDireccion = crearSeccionDireccion();
        panelCentral.add(panelDireccion);
        panelCentral.add(Box.createVerticalStrut(15));
        
        // Sección 3: Correo de Recuperación
        JPanel panelCorreo = crearSeccionCorreo();
        panelCentral.add(panelCorreo);
        panelCentral.add(Box.createVerticalStrut(15));
        
        // Sección 4: Fecha y Moneda
        JPanel panelFechaMoneda = crearSeccionFechaMoneda();
        panelCentral.add(panelFechaMoneda);
        
        JScrollPane scrollCentral = new JScrollPane(panelCentral);
        scrollCentral.getVerticalScrollBar().setUnitIncrement(16);
        scrollCentral.setBorder(null);
        
        // Panel derecho - Logo
        JPanel panelLogo = crearPanelLogo();
        
        // Panel central dividido
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollCentral, panelLogo);
        splitPane.setDividerLocation(550);
        splitPane.setResizeWeight(0.65);
        
        add(splitPane, BorderLayout.CENTER);
        
        // Panel inferior - Botones
        JPanel panelInferior = crearPanelInferior();
        add(panelInferior, BorderLayout.SOUTH);
    }
    
    private JPanel crearSeccionDatosGenerales() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "DATOS GENERALES",
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12), new Color(52, 73, 94)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Fila 1: Nombre Empresa
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1;
        gbc.weightx = 0;
        panel.add(new JLabel("Nombre de la Empresa:*"), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        gbc.weightx = 1;
        txtNombreEmpresa = new JTextField(25);
        panel.add(txtNombreEmpresa, gbc);
        
        // Fila 2: Eslogan
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        gbc.weightx = 0;
        panel.add(new JLabel("Eslogan:"), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        gbc.weightx = 1;
        txtEslogan = new JTextField(25);
        panel.add(txtEslogan, gbc);
        
        // Fila 3: RTN
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1;
        gbc.weightx = 0;
        panel.add(new JLabel("Registro Tributario (RTN):"), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        gbc.weightx = 1;
        txtRtn = new JTextField(25);
        panel.add(txtRtn, gbc);
        
        // Fila 4: Teléfono
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1;
        gbc.weightx = 0;
        panel.add(new JLabel("Teléfono:"), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        gbc.weightx = 1;
        txtTelefono = new JTextField(25);
        panel.add(txtTelefono, gbc);
        
        // Fila 5: Email
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 1;
        gbc.weightx = 0;
        panel.add(new JLabel("Correo Electrónico:"), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        gbc.weightx = 1;
        txtEmail = new JTextField(25);
        panel.add(txtEmail, gbc);
        
        // Fila 6: Sitio Web
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 1;
        gbc.weightx = 0;
        panel.add(new JLabel("Sitio Web:"), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        gbc.weightx = 1;
        txtSitioWeb = new JTextField(25);
        panel.add(txtSitioWeb, gbc);
        
        return panel;
    }
    
    private JPanel crearSeccionDireccion() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "DIRECCIÓN FISCAL",
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12), new Color(52, 73, 94)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Fila 1: Dirección
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1;
        gbc.weightx = 0;
        panel.add(new JLabel("Dirección:"), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        gbc.weightx = 1;
        txtDireccion = new JTextField(25);
        panel.add(txtDireccion, gbc);
        
        // Fila 2: Ciudad
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        gbc.weightx = 0;
        panel.add(new JLabel("Ciudad:"), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        gbc.weightx = 1;
        txtCiudad = new JTextField(25);
        panel.add(txtCiudad, gbc);
        
        // Fila 3: Departamento
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1;
        gbc.weightx = 0;
        panel.add(new JLabel("Departamento:"), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        gbc.weightx = 1;
        txtDepartamento = new JTextField(25);
        panel.add(txtDepartamento, gbc);
        
        // Fila 4: Código Postal
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1;
        gbc.weightx = 0;
        panel.add(new JLabel("Código Postal:"), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        gbc.weightx = 1;
        txtCodigoPostal = new JTextField(25);
        panel.add(txtCodigoPostal, gbc);
        
        return panel;
    }
    
    private JPanel crearSeccionCorreo() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "CORREO DE RECUPERACIÓN",
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12), new Color(52, 73, 94)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1;
        gbc.weightx = 0;
        panel.add(new JLabel("Correo Recuperación:"), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        gbc.weightx = 1;
        txtCorreoRecuperacion = new JTextField(25);
        panel.add(txtCorreoRecuperacion, gbc);
        
        JLabel lblInfo = new JLabel("Será utilizado para recuperar la contraseña del administrador");
        lblInfo.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblInfo.setForeground(Color.GRAY);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 3;
        panel.add(lblInfo, gbc);
        
        return panel;
    }
    
    private JPanel crearSeccionFechaMoneda() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "FECHA Y MONEDA",
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12), new Color(52, 73, 94)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Fila 1: Formato Fecha
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1;
        gbc.weightx = 0;
        panel.add(new JLabel("Formato de Fecha:"), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        gbc.weightx = 1;
        cmbFormatoFecha = new JComboBox<>(new String[]{"dd/MM/yyyy", "MM/dd/yyyy", "yyyy/MM/dd"});
        panel.add(cmbFormatoFecha, gbc);
        
        // Fila 2: Formato Moneda
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        gbc.weightx = 0;
        panel.add(new JLabel("Formato de Moneda:"), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        gbc.weightx = 1;
        cmbFormatoMoneda = new JComboBox<>(new String[]{"$ ###,###.##", "MXN ###,###.##", "USD ###,###.##"});
        panel.add(cmbFormatoMoneda, gbc);
        
        return panel;
    }
    
    private JPanel crearPanelLogo() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "LOGO DE LA EMPRESA",
            TitledBorder.CENTER, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12), new Color(52, 73, 94)
        ));
        panel.setPreferredSize(new Dimension(300, 350));
        
        // Panel para mostrar el logo
        JPanel panelImagen = new JPanel(new BorderLayout());
        panelImagen.setBackground(new Color(245, 245, 250));
        panelImagen.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        
        lblLogo = new JLabel();
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogo.setVerticalAlignment(SwingConstants.CENTER);
        lblLogo.setPreferredSize(new Dimension(250, 180));
        panelImagen.add(lblLogo, BorderLayout.CENTER);
        
        // Panel de botones para logo
        JPanel panelBotonesLogo = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotonesLogo.setBackground(Color.WHITE);
        
        JButton btnCargarLogo = new JButton("📷 CARGAR IMAGEN");
        btnCargarLogo.setBackground(new Color(52, 152, 219));
        btnCargarLogo.setForeground(Color.BLACK);
        btnCargarLogo.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnCargarLogo.setFocusPainted(false);
        btnCargarLogo.addActionListener(e -> cargarLogo());
        
        JButton btnEliminarLogo = new JButton("🗑 ELIMINAR");
        btnEliminarLogo.setBackground(new Color(231, 76, 60));
        btnEliminarLogo.setForeground(Color.BLACK);
        btnEliminarLogo.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnEliminarLogo.setFocusPainted(false);
        btnEliminarLogo.addActionListener(e -> eliminarLogo());
        
        JButton btnExportarLogo = new JButton("💾 EXPORTAR");
        btnExportarLogo.setBackground(new Color(149, 165, 166));
        btnExportarLogo.setForeground(Color.BLACK);
        btnExportarLogo.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnExportarLogo.setFocusPainted(false);
        btnExportarLogo.addActionListener(e -> exportarLogo());
        
        panelBotonesLogo.add(btnCargarLogo);
        panelBotonesLogo.add(btnEliminarLogo);
        panelBotonesLogo.add(btnExportarLogo);
        
        panel.add(panelImagen, BorderLayout.CENTER);
        panel.add(panelBotonesLogo, BorderLayout.SOUTH);
        
        // Panel de activación
        JPanel panelActivacion = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelActivacion.setBackground(Color.WHITE);
        panelActivacion.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        
        JButton btnActivacion = new JButton("🔑 ACTIVACIÓN DEL SOFTWARE");
        btnActivacion.setBackground(new Color(230, 126, 34));
        btnActivacion.setForeground(Color.BLACK);
        btnActivacion.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnActivacion.setFocusPainted(false);
        btnActivacion.addActionListener(e -> mostrarActivacion());
        
        panelActivacion.add(btnActivacion);
        
        panel.add(panelActivacion, BorderLayout.NORTH);
        
        return panel;
    }
    
    private JPanel crearPanelInferior() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBackground(new Color(245, 245, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton btnGuardar = new JButton("💾 GUARDAR Y CERRAR");
        btnGuardar.setBackground(new Color(46, 204, 113));
        btnGuardar.setForeground(Color.BLACK);
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnGuardar.setFocusPainted(false);
        btnGuardar.addActionListener(e -> guardarDatos());
        
        JButton btnCancelar = new JButton("CANCELAR");
        btnCancelar.setBackground(new Color(149, 165, 166));
        btnCancelar.setForeground(Color.BLACK);
        btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnCancelar.setFocusPainted(false);
        btnCancelar.addActionListener(e -> cargarDatos());
        
        panel.add(btnGuardar);
        panel.add(btnCancelar);
        
        return panel;
    }
    
    private void cargarDatos() {
        if (empresa != null) {
            txtNombreEmpresa.setText(empresa.getNombre() != null ? empresa.getNombre() : "");
            txtEslogan.setText(empresa.getEslogan() != null ? empresa.getEslogan() : "");
            txtRtn.setText(empresa.getRtn() != null ? empresa.getRtn() : "");
            txtTelefono.setText(empresa.getTelefono() != null ? empresa.getTelefono() : "");
            txtEmail.setText(empresa.getEmail() != null ? empresa.getEmail() : "");
            txtSitioWeb.setText(empresa.getSitioWeb() != null ? empresa.getSitioWeb() : "");
            txtDireccion.setText(empresa.getDireccion() != null ? empresa.getDireccion() : "");
            txtCiudad.setText(empresa.getCiudad() != null ? empresa.getCiudad() : "");
            txtDepartamento.setText(empresa.getDepartamento() != null ? empresa.getDepartamento() : "");
            txtCodigoPostal.setText(empresa.getCodigoPostal() != null ? empresa.getCodigoPostal() : "");
            txtCorreoRecuperacion.setText(empresa.getCorreoRecuperacion() != null ? empresa.getCorreoRecuperacion() : "");
            
            rutaLogoActual = empresa.getRutaLogo();
            cargarLogoEnLabel();
            
            if (empresa.getFormatoFecha() != null) {
                cmbFormatoFecha.setSelectedItem(empresa.getFormatoFecha());
            }
            if (empresa.getFormatoMoneda() != null) {
                cmbFormatoMoneda.setSelectedItem(empresa.getFormatoMoneda());
            }
        }
    }
    
    private void guardarDatos() {
        empresa.setNombre(txtNombreEmpresa.getText().trim());
        empresa.setEslogan(txtEslogan.getText().trim());
        empresa.setRtn(txtRtn.getText().trim());
        empresa.setTelefono(txtTelefono.getText().trim());
        empresa.setEmail(txtEmail.getText().trim());
        empresa.setSitioWeb(txtSitioWeb.getText().trim());
        empresa.setDireccion(txtDireccion.getText().trim());
        empresa.setCiudad(txtCiudad.getText().trim());
        empresa.setDepartamento(txtDepartamento.getText().trim());
        empresa.setCodigoPostal(txtCodigoPostal.getText().trim());
        empresa.setCorreoRecuperacion(txtCorreoRecuperacion.getText().trim());
        empresa.setRutaLogo(rutaLogoActual);
        empresa.setFormatoFecha((String) cmbFormatoFecha.getSelectedItem());
        empresa.setFormatoMoneda((String) cmbFormatoMoneda.getSelectedItem());
        
        datos.actualizarEmpresa(empresa);
        
        JOptionPane.showMessageDialog(this, 
            "Información de la empresa guardada exitosamente", 
            "Guardado", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void cargarLogo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar logo de la empresa");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Imágenes (jpg, png, gif)", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);
        
        int resultado = fileChooser.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            rutaLogoActual = archivo.getAbsolutePath();
            cargarLogoEnLabel();
        }
    }
    
    private void cargarLogoEnLabel() {
        if (rutaLogoActual != null && !rutaLogoActual.isEmpty()) {
            try {
                ImageIcon icono = new ImageIcon(rutaLogoActual);
                Image imagen = icono.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
                lblLogo.setIcon(new ImageIcon(imagen));
                lblLogo.setText("");
            } catch (Exception e) {
                lblLogo.setIcon(null);
                lblLogo.setText("Logo no disponible");
                lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
            }
        } else {
            lblLogo.setIcon(null);
            lblLogo.setText("Sin logo\nSeleccione una imagen");
            lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
            lblLogo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            lblLogo.setForeground(Color.GRAY);
        }
    }
    
    private void eliminarLogo() {
        rutaLogoActual = null;
        cargarLogoEnLabel();
    }
    
    private void exportarLogo() {
        if (rutaLogoActual != null && !rutaLogoActual.isEmpty()) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar logo como");
            fileChooser.setSelectedFile(new File("logo_empresa.png"));
            
            int resultado = fileChooser.showSaveDialog(this);
            if (resultado == JFileChooser.APPROVE_OPTION) {
                try {
                    File destino = fileChooser.getSelectedFile();
                    java.nio.file.Files.copy(
                        new File(rutaLogoActual).toPath(),
                        destino.toPath(),
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING
                    );
                    JOptionPane.showMessageDialog(this, "Logo exportado exitosamente");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error al exportar logo: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "No hay logo para exportar");
        }
    }
    
    private void mostrarActivacion() {
        JOptionPane.showMessageDialog(this,
            "Módulo de Activación\n\n" +
            "Para activar el software:\n" +
            "1. Genere el archivo de solicitud\n" +
            "2. Ingrese al portal web\n" +
            "3. Adquiera su licencia\n" +
            "4. Active con el archivo recibido\n\n" +
            "Portal: https://www.smartpuntodeventa.com/licencias",
            "Activación", JOptionPane.INFORMATION_MESSAGE);
    }
}