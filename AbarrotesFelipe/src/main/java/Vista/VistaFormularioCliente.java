package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import Modelo.Cliente;

public class VistaFormularioCliente extends JDialog {
    private VistaClientes vistaClientes;
    private Cliente clienteEditar;
    private boolean esEdicion;
    
    private JTextField txtNombre;
    private JTextField txtEmail;
    private JTextField txtTelefono;
    private JTextField txtPuntos;
    private JLabel lblImagen;
    private JButton btnSeleccionarImagen;
    private String nombreImagenGuardada;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JLabel lblError;
    
    private static final String RUTA_IMAGENES = "img_clientes/";
    
    public VistaFormularioCliente(JFrame padre, VistaClientes vista, Cliente cliente) {
        super(padre, cliente == null ? "Agregar Cliente" : "Editar Cliente", true);
        this.vistaClientes = vista;
        this.clienteEditar = cliente;
        this.esEdicion = cliente != null;
        
        setSize(500, 550);
        setLocationRelativeTo(padre);
        setResizable(false);
        getContentPane().setLayout(new BorderLayout());
        
        File carpeta = new File(RUTA_IMAGENES);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
        
        iniciarComponentes();
        
        if (clienteEditar != null) {
            cargarDatosCliente();
        }
    }
    
    private void iniciarComponentes() {
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(79, 70, 229));
        panelTitulo.setPreferredSize(new Dimension(getWidth(), 50));
        
        String titulo = clienteEditar == null ? "AGREGAR CLIENTE" : "EDITAR CLIENTE";
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelTitulo.add(lblTitulo);
        
        getContentPane().add(panelTitulo, BorderLayout.NORTH);
        
        JPanel panelFormulario = new JPanel();
        panelFormulario.setBackground(new Color(240, 248, 255));
        panelFormulario.setLayout(new GridBagLayout());
        
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Arial", Font.BOLD, 12));
        txtNombre = new JTextField(20);
        txtNombre.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Arial", Font.BOLD, 12));
        txtEmail = new JTextField(20);
        txtEmail.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JLabel lblTelefono = new JLabel("Telefono:");
        lblTelefono.setFont(new Font("Arial", Font.BOLD, 12));
        txtTelefono = new JTextField(20);
        txtTelefono.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JLabel lblPuntos = new JLabel("Puntos:");
        lblPuntos.setFont(new Font("Arial", Font.BOLD, 12));
        txtPuntos = new JTextField(20);
        txtPuntos.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JLabel lblImagenTitulo = new JLabel("Imagen:");
        lblImagenTitulo.setFont(new Font("Arial", Font.BOLD, 12));
        
        lblImagen = new JLabel();
        lblImagen.setPreferredSize(new Dimension(100, 100));
        lblImagen.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagen.setVerticalAlignment(SwingConstants.CENTER);
        lblImagen.setText("Sin imagen");
        
        btnSeleccionarImagen = new JButton("Seleccionar Imagen");
        btnSeleccionarImagen.setBackground(new Color(59, 130, 246));
        btnSeleccionarImagen.setForeground(Color.BLACK);
        btnSeleccionarImagen.setFont(new Font("Arial", Font.BOLD, 11));
        btnSeleccionarImagen.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
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
        
        int fila = 0;
        
        GridBagConstraints gbc0 = new GridBagConstraints();
        gbc0.insets = new Insets(20, 20, 5, 10);
        gbc0.gridx = 0;
        gbc0.gridy = fila;
        gbc0.anchor = GridBagConstraints.EAST;
        panelFormulario.add(lblNombre, gbc0);
        
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.insets = new Insets(20, 10, 5, 20);
        gbc1.gridx = 1;
        gbc1.gridy = fila++;
        gbc1.anchor = GridBagConstraints.WEST;
        panelFormulario.add(txtNombre, gbc1);
        
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(5, 20, 5, 10);
        gbc2.gridx = 0;
        gbc2.gridy = fila;
        gbc2.anchor = GridBagConstraints.EAST;
        panelFormulario.add(lblEmail, gbc2);
        
        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.insets = new Insets(5, 10, 5, 20);
        gbc3.gridx = 1;
        gbc3.gridy = fila++;
        gbc3.anchor = GridBagConstraints.WEST;
        panelFormulario.add(txtEmail, gbc3);
        
        GridBagConstraints gbc4 = new GridBagConstraints();
        gbc4.insets = new Insets(5, 20, 5, 10);
        gbc4.gridx = 0;
        gbc4.gridy = fila;
        gbc4.anchor = GridBagConstraints.EAST;
        panelFormulario.add(lblTelefono, gbc4);
        
        GridBagConstraints gbc5 = new GridBagConstraints();
        gbc5.insets = new Insets(5, 10, 5, 20);
        gbc5.gridx = 1;
        gbc5.gridy = fila++;
        gbc5.anchor = GridBagConstraints.WEST;
        panelFormulario.add(txtTelefono, gbc5);
        
        GridBagConstraints gbc6 = new GridBagConstraints();
        gbc6.insets = new Insets(5, 20, 5, 10);
        gbc6.gridx = 0;
        gbc6.gridy = fila;
        gbc6.anchor = GridBagConstraints.EAST;
        panelFormulario.add(lblPuntos, gbc6);
        
        GridBagConstraints gbc7 = new GridBagConstraints();
        gbc7.insets = new Insets(5, 10, 5, 20);
        gbc7.gridx = 1;
        gbc7.gridy = fila++;
        gbc7.anchor = GridBagConstraints.WEST;
        panelFormulario.add(txtPuntos, gbc7);
        
        GridBagConstraints gbc8 = new GridBagConstraints();
        gbc8.insets = new Insets(10, 20, 5, 10);
        gbc8.gridx = 0;
        gbc8.gridy = fila;
        gbc8.anchor = GridBagConstraints.NORTHEAST;
        panelFormulario.add(lblImagenTitulo, gbc8);
        
        JPanel panelImagen = new JPanel(new BorderLayout(5, 5));
        panelImagen.add(lblImagen, BorderLayout.CENTER);
        panelImagen.add(btnSeleccionarImagen, BorderLayout.SOUTH);
        
        GridBagConstraints gbc9 = new GridBagConstraints();
        gbc9.insets = new Insets(10, 10, 5, 20);
        gbc9.gridx = 1;
        gbc9.gridy = fila++;
        gbc9.anchor = GridBagConstraints.WEST;
        panelFormulario.add(panelImagen, gbc9);
        
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        GridBagConstraints gbc10 = new GridBagConstraints();
        gbc10.insets = new Insets(20, 10, 10, 10);
        gbc10.gridx = 0;
        gbc10.gridy = fila;
        gbc10.gridwidth = 2;
        panelFormulario.add(panelBotones, gbc10);
        
        GridBagConstraints gbc11 = new GridBagConstraints();
        gbc11.insets = new Insets(5, 10, 20, 10);
        gbc11.gridx = 0;
        gbc11.gridy = fila + 1;
        gbc11.gridwidth = 2;
        panelFormulario.add(lblError, gbc11);
        
        getContentPane().add(panelFormulario, BorderLayout.CENTER);
        
        btnSeleccionarImagen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                seleccionarImagen();
            }
        });
        
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Cliente cliente = construirCliente();
                if (cliente != null && vistaClientes != null) {
                    vistaClientes.solicitarGuardarCliente(cliente, esEdicion ? clienteEditar.getId() : 0);
                }
            }
        });
        
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    private void seleccionarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar Imagen para Cliente");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "Imagenes (*.jpg, *.jpeg, *.png, *.gif)", "jpg", "jpeg", "png", "gif"));
        
        int resultado = fileChooser.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoOrigen = fileChooser.getSelectedFile();
            
            if (!archivoOrigen.exists()) {
                lblError.setText("El archivo no existe");
                return;
            }
            
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
                Image img = imagen.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                lblImagen.setIcon(new ImageIcon(img));
                lblImagen.setText("");
            } catch (Exception ex) {
                lblError.setText("Error al copiar la imagen: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
    
    private void cargarDatosCliente() {
        txtNombre.setText(clienteEditar.getNombre());
        if (clienteEditar.getEmail() != null) {
            txtEmail.setText(clienteEditar.getEmail());
        }
        if (clienteEditar.getTelefono() != null) {
            txtTelefono.setText(clienteEditar.getTelefono());
        }
        txtPuntos.setText(String.valueOf(clienteEditar.getPuntos()));
        
        if (clienteEditar.getRutaImagen() != null && !clienteEditar.getRutaImagen().isEmpty()) {
            File archivoImagen = new File(clienteEditar.getRutaImagen());
            if (archivoImagen.exists()) {
                try {
                    ImageIcon imagen = new ImageIcon(archivoImagen.getAbsolutePath());
                    Image img = imagen.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    lblImagen.setIcon(new ImageIcon(img));
                    lblImagen.setText("");
                } catch (Exception e) {
                    lblImagen.setText("Sin imagen");
                }
            } else {
                lblImagen.setText("Sin imagen");
            }
        }
    }
    
    private Cliente construirCliente() {
        String nombre = txtNombre.getText().trim();
        String email = txtEmail.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String puntosStr = txtPuntos.getText().trim();
        
        if (nombre.isEmpty()) {
            lblError.setText("El nombre es obligatorio");
            return null;
        }
        
        int puntos = 0;
        if (!puntosStr.isEmpty()) {
            try {
                puntos = Integer.parseInt(puntosStr);
                if (puntos < 0) {
                    lblError.setText("Los puntos no pueden ser negativos");
                    return null;
                }
            } catch (NumberFormatException e) {
                lblError.setText("Puntos invalido");
                return null;
            }
        }
        
        Cliente cliente;
        if (clienteEditar == null) {
            cliente = new Cliente();
        } else {
            cliente = clienteEditar;
        }
        
        cliente.setNombre(nombre);
        cliente.setEmail(email.isEmpty() ? null : email);
        cliente.setTelefono(telefono.isEmpty() ? null : telefono);
        cliente.setPuntos(puntos);
        
        if (nombreImagenGuardada != null && !nombreImagenGuardada.isEmpty()) {
            cliente.setRutaImagen(RUTA_IMAGENES + nombreImagenGuardada);
        }
        
        return cliente;
    }
}