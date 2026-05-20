package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import Controlador.ControladorProveedor;
import Modelo.Proveedor;

public class VistaFormularioProveedor extends JDialog {
    private VistaProveedores vistaProveedores;
    private ControladorProveedor controladorProveedor;
    private Proveedor proveedorEditar;
    
    private JTextField txtNombreEmpresa;
    private JTextField txtContacto;
    private JTextField txtTelefono;
    private JTextField txtCategoria;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JLabel lblError;
    
    public VistaFormularioProveedor(JFrame padre, VistaProveedores vista, Proveedor proveedor) {
        super(padre, proveedor == null ? "Agregar Proveedor" : "Editar Proveedor", true);
        this.vistaProveedores = vista;
        this.controladorProveedor = new ControladorProveedor();
        this.proveedorEditar = proveedor;
        
        setSize(450, 350);
        setLocationRelativeTo(padre);
        setResizable(false);
        getContentPane().setLayout(new BorderLayout());
        
        iniciarComponentes();
        
        if (proveedorEditar != null) {
            cargarDatosProveedor();
        }
    }
    
    private void iniciarComponentes() {
        
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(79, 70, 229));
        panelTitulo.setPreferredSize(new Dimension(getWidth(), 50));
        
        String titulo = proveedorEditar == null ? "AGREGAR PROVEEDOR" : "EDITAR PROVEEDOR";
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelTitulo.add(lblTitulo);
        
        getContentPane().add(panelTitulo, BorderLayout.NORTH);
        
        
        JPanel panelFormulario = new JPanel();
        panelFormulario.setBackground(new Color(240, 248, 255));
        panelFormulario.setLayout(new GridBagLayout());
        
        JLabel lblNombreEmpresa = new JLabel("Nombre Empresa:");
        lblNombreEmpresa.setFont(new Font("Arial", Font.BOLD, 12));
        txtNombreEmpresa = new JTextField(20);
        txtNombreEmpresa.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JLabel lblContacto = new JLabel("Contacto:");
        lblContacto.setFont(new Font("Arial", Font.BOLD, 12));
        txtContacto = new JTextField(20);
        txtContacto.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JLabel lblTelefono = new JLabel("Telefono:");
        lblTelefono.setFont(new Font("Arial", Font.BOLD, 12));
        txtTelefono = new JTextField(20);
        txtTelefono.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JLabel lblCategoria = new JLabel("Categoria:");
        lblCategoria.setFont(new Font("Arial", Font.BOLD, 12));
        txtCategoria = new JTextField(20);
        txtCategoria.setFont(new Font("Arial", Font.PLAIN, 12));
        
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
        panelFormulario.add(lblNombreEmpresa, gbc0);
        
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.insets = new Insets(20, 10, 5, 20);
        gbc1.gridx = 1;
        gbc1.gridy = fila++;
        gbc1.anchor = GridBagConstraints.WEST;
        panelFormulario.add(txtNombreEmpresa, gbc1);
        
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(5, 20, 5, 10);
        gbc2.gridx = 0;
        gbc2.gridy = fila;
        gbc2.anchor = GridBagConstraints.EAST;
        panelFormulario.add(lblContacto, gbc2);
        
        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.insets = new Insets(5, 10, 5, 20);
        gbc3.gridx = 1;
        gbc3.gridy = fila++;
        gbc3.anchor = GridBagConstraints.WEST;
        panelFormulario.add(txtContacto, gbc3);
        
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
        panelFormulario.add(lblCategoria, gbc6);
        
        GridBagConstraints gbc7 = new GridBagConstraints();
        gbc7.insets = new Insets(5, 10, 5, 20);
        gbc7.gridx = 1;
        gbc7.gridy = fila++;
        gbc7.anchor = GridBagConstraints.WEST;
        panelFormulario.add(txtCategoria, gbc7);
        
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        GridBagConstraints gbc8 = new GridBagConstraints();
        gbc8.insets = new Insets(20, 10, 10, 10);
        gbc8.gridx = 0;
        gbc8.gridy = fila;
        gbc8.gridwidth = 2;
        panelFormulario.add(panelBotones, gbc8);
        
        GridBagConstraints gbc9 = new GridBagConstraints();
        gbc9.insets = new Insets(5, 10, 20, 10);
        gbc9.gridx = 0;
        gbc9.gridy = fila + 1;
        gbc9.gridwidth = 2;
        panelFormulario.add(lblError, gbc9);
        
        getContentPane().add(panelFormulario, BorderLayout.CENTER);
        
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarProveedor();
            }
        });
        
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    private void cargarDatosProveedor() {
        txtNombreEmpresa.setText(proveedorEditar.getNombreEmpresa());
        if (proveedorEditar.getContactoNombre() != null) {
            txtContacto.setText(proveedorEditar.getContactoNombre());
        }
        if (proveedorEditar.getTelefono() != null) {
            txtTelefono.setText(proveedorEditar.getTelefono());
        }
        if (proveedorEditar.getCategoria() != null) {
            txtCategoria.setText(proveedorEditar.getCategoria());
        }
    }
    
    private void guardarProveedor() {
        String nombreEmpresa = txtNombreEmpresa.getText().trim();
        String contacto = txtContacto.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String categoria = txtCategoria.getText().trim();
        
        if (nombreEmpresa.isEmpty()) {
            lblError.setText("El nombre de la empresa es obligatorio");
            return;
        }
        
        Proveedor proveedor;
        if (proveedorEditar == null) {
            proveedor = new Proveedor();
        } else {
            proveedor = proveedorEditar;
        }
        
        proveedor.setNombreEmpresa(nombreEmpresa);
        proveedor.setContactoNombre(contacto.isEmpty() ? null : contacto);
        proveedor.setTelefono(telefono.isEmpty() ? null : telefono);
        proveedor.setCategoria(categoria.isEmpty() ? null : categoria);
        
        boolean guardado = controladorProveedor.guardar(proveedor);
        
        if (guardado) {
            JOptionPane.showMessageDialog(this, "Proveedor guardado correctamente");
            if (vistaProveedores != null) {
                vistaProveedores.refrescar();
            }
            dispose();
        } else {
            lblError.setText("Error al guardar el proveedor");
        }
    }
}