package Vista;

import Controlador.ProveedorControlador;
import Modelo.Proveedor;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class VistaProveedores extends JPanel {
    private ProveedorControlador proveedorControlador;
    private DefaultTableModel modeloProveedores;
    private JTable tablaProveedores;
    private JTextField txtBuscar;
    private JLabel lblTotalProveedores;
    
    public VistaProveedores() {
        proveedorControlador = new ProveedorControlador();
        iniciarComponentes();
        cargarTabla();
        actualizarResumen();
    }
    
    private void iniciarComponentes() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(new Color(52, 73, 94));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel lblTitulo = new JLabel("GESTIÓN DE PROVEEDORES");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);
        
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBusqueda.setBackground(new Color(52, 73, 94));
        
        JLabel lblBuscar = new JLabel("Buscar:");
        lblBuscar.setForeground(Color.WHITE);
        
        txtBuscar = new JTextField(20);
        txtBuscar.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                filtrarProveedores();
            }
        });
        
        JButton btnNuevo = new JButton("+ NUEVO PROVEEDOR");
        btnNuevo.setBackground(new Color(46, 204, 113));
        btnNuevo.setForeground(Color.BLACK);  // Texto negro
        btnNuevo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnNuevo.setFocusPainted(false);
        btnNuevo.addActionListener(e -> mostrarDialogoProveedor(null));
        
        panelBusqueda.add(lblBuscar);
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(Box.createHorizontalStrut(20));
        panelBusqueda.add(btnNuevo);
        
        panelSuperior.add(lblTitulo, BorderLayout.WEST);
        panelSuperior.add(panelBusqueda, BorderLayout.EAST);
        
        add(panelSuperior, BorderLayout.NORTH);
        
        JPanel panelResumen = crearPanelResumen();
        add(panelResumen, BorderLayout.NORTH);
        
        String[] columnas = {"ID", "Nombre", "RTN", "Teléfono", "Email", "Contacto", "Dirección"};
        modeloProveedores = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaProveedores = new JTable(modeloProveedores);
        tablaProveedores.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaProveedores.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tablaProveedores.setRowHeight(28);
        tablaProveedores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        tablaProveedores.getColumnModel().getColumn(0).setPreferredWidth(40);
        tablaProveedores.getColumnModel().getColumn(1).setPreferredWidth(180);
        tablaProveedores.getColumnModel().getColumn(2).setPreferredWidth(120);
        tablaProveedores.getColumnModel().getColumn(3).setPreferredWidth(100);
        tablaProveedores.getColumnModel().getColumn(4).setPreferredWidth(150);
        tablaProveedores.getColumnModel().getColumn(5).setPreferredWidth(120);
        tablaProveedores.getColumnModel().getColumn(6).setPreferredWidth(200);
        
        JScrollPane scroll = new JScrollPane(tablaProveedores);
        add(scroll, BorderLayout.CENTER);
        
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelInferior.setBackground(new Color(245, 245, 250));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton btnEditar = new JButton("✏ EDITAR");
        btnEditar.setBackground(new Color(52, 152, 219));
        btnEditar.setForeground(Color.BLACK);  // Texto negro
        btnEditar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnEditar.setFocusPainted(false);
        btnEditar.addActionListener(e -> editarProveedor());
        
        JButton btnEliminar = new JButton("🗑 ELIMINAR");
        btnEliminar.setBackground(new Color(231, 76, 60));
        btnEliminar.setForeground(Color.BLACK);  // Texto negro
        btnEliminar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnEliminar.setFocusPainted(false);
        btnEliminar.addActionListener(e -> eliminarProveedor());
        
        JButton btnActualizar = new JButton("🔄 ACTUALIZAR");
        btnActualizar.setBackground(new Color(149, 165, 166));
        btnActualizar.setForeground(Color.BLACK);  // Texto negro
        btnActualizar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnActualizar.setFocusPainted(false);
        btnActualizar.addActionListener(e -> {
            cargarTabla();
            actualizarResumen();
        });
        
        panelInferior.add(btnEditar);
        panelInferior.add(btnEliminar);
        panelInferior.add(btnActualizar);
        
        add(panelInferior, BorderLayout.SOUTH);
    }
    
    private JPanel crearPanelResumen() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(new Color(245, 245, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 73, 94), 1),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        
        JLabel lblTitulo = new JLabel("🏢 TOTAL PROVEEDORES");
        lblTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblTitulo.setForeground(Color.GRAY);
        
        lblTotalProveedores = new JLabel("0");
        lblTotalProveedores.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTotalProveedores.setForeground(new Color(52, 73, 94));
        
        card.add(lblTitulo, BorderLayout.NORTH);
        card.add(lblTotalProveedores, BorderLayout.CENTER);
        
        panel.add(card);
        
        return panel;
    }
    
    private void actualizarResumen() {
        lblTotalProveedores.setText(String.valueOf(proveedorControlador.contarProveedores()));
    }
    
    private void cargarTabla() {
        modeloProveedores.setRowCount(0);
        List<Proveedor> proveedores = proveedorControlador.obtenerTodosProveedores();
        
        for (Proveedor p : proveedores) {
            modeloProveedores.addRow(new Object[]{
                p.getId(),
                p.getNombre(),
                p.getRtn() != null ? p.getRtn() : "-",
                p.getTelefono() != null ? p.getTelefono() : "-",
                p.getEmail() != null ? p.getEmail() : "-",
                p.getNombreContacto() != null ? p.getNombreContacto() : "-",
                p.getDireccion() != null ? p.getDireccion() : "-"
            });
        }
    }
    
    private void filtrarProveedores() {
        String busqueda = txtBuscar.getText().toLowerCase().trim();
        modeloProveedores.setRowCount(0);
        List<Proveedor> proveedores = proveedorControlador.buscarProveedores(busqueda);
        
        for (Proveedor p : proveedores) {
            modeloProveedores.addRow(new Object[]{
                p.getId(),
                p.getNombre(),
                p.getRtn() != null ? p.getRtn() : "-",
                p.getTelefono() != null ? p.getTelefono() : "-",
                p.getEmail() != null ? p.getEmail() : "-",
                p.getNombreContacto() != null ? p.getNombreContacto() : "-",
                p.getDireccion() != null ? p.getDireccion() : "-"
            });
        }
    }
    
    private void mostrarDialogoProveedor(Proveedor proveedor) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), 
                                      proveedor == null ? "Nuevo Proveedor" : "Editar Proveedor", true);
        dialog.setSize(500, 480);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JTextField txtNombre = new JTextField(20);
        JTextField txtRtn = new JTextField(20);
        JTextField txtTelefono = new JTextField(20);
        JTextField txtEmail = new JTextField(20);
        JTextField txtContacto = new JTextField(20);
        JTextField txtDireccion = new JTextField(20);
        
        if (proveedor != null) {
            txtNombre.setText(proveedor.getNombre());
            txtRtn.setText(proveedor.getRtn() != null ? proveedor.getRtn() : "");
            txtTelefono.setText(proveedor.getTelefono() != null ? proveedor.getTelefono() : "");
            txtEmail.setText(proveedor.getEmail() != null ? proveedor.getEmail() : "");
            txtContacto.setText(proveedor.getNombreContacto() != null ? proveedor.getNombreContacto() : "");
            txtDireccion.setText(proveedor.getDireccion() != null ? proveedor.getDireccion() : "");
        }
        
        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1;
        panelForm.add(new JLabel("Nombre:*"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        panelForm.add(txtNombre, gbc);
        
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panelForm.add(new JLabel("RTN:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        panelForm.add(txtRtn, gbc);
        
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panelForm.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        panelForm.add(txtTelefono, gbc);
        
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panelForm.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        panelForm.add(txtEmail, gbc);
        
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panelForm.add(new JLabel("Nombre Contacto:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        panelForm.add(txtContacto, gbc);
        
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panelForm.add(new JLabel("Dirección:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        panelForm.add(txtDireccion, gbc);
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnGuardar = new JButton("💾 GUARDAR");
        btnGuardar.setBackground(new Color(46, 204, 113));
        btnGuardar.setForeground(Color.BLACK);  // Texto negro
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnGuardar.setFocusPainted(false);
        
        JButton btnCancelar = new JButton("CANCELAR");
        btnCancelar.setBackground(new Color(149, 165, 166));
        btnCancelar.setForeground(Color.BLACK);  // Texto negro
        btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnCancelar.setFocusPainted(false);
        btnCancelar.addActionListener(e -> dialog.dispose());
        
        btnGuardar.addActionListener(e -> {
            try {
                if (txtNombre.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "El nombre del proveedor es obligatorio");
                    return;
                }
                
                boolean exito;
                if (proveedor == null) {
                    exito = proveedorControlador.crearProveedor(
                        txtNombre.getText().trim(),
                        txtRtn.getText().trim(),
                        txtTelefono.getText().trim(),
                        txtEmail.getText().trim(),
                        txtContacto.getText().trim(),
                        txtDireccion.getText().trim()
                    );
                } else {
                    exito = proveedorControlador.actualizarProveedor(
                        proveedor.getId(),
                        txtNombre.getText().trim(),
                        txtRtn.getText().trim(),
                        txtTelefono.getText().trim(),
                        txtEmail.getText().trim(),
                        txtContacto.getText().trim(),
                        txtDireccion.getText().trim()
                    );
                }
                
                if (exito) {
                    cargarTabla();
                    actualizarResumen();
                    dialog.dispose();
                    JOptionPane.showMessageDialog(this, "Proveedor guardado exitosamente");
                } else {
                    JOptionPane.showMessageDialog(dialog, "Error al guardar el proveedor");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage());
            }
        });
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        dialog.add(panelForm, BorderLayout.CENTER);
        dialog.add(panelBotones, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
    
    private void editarProveedor() {
        int fila = tablaProveedores.getSelectedRow();
        if (fila >= 0) {
            int id = (int) modeloProveedores.getValueAt(fila, 0);
            Proveedor p = proveedorControlador.obtenerProveedorPorId(id);
            if (p != null) {
                mostrarDialogoProveedor(p);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor");
        }
    }
    public void recargarDatos() {
        cargarTabla();
        actualizarResumen();
    }
    
    private void eliminarProveedor() {
        int fila = tablaProveedores.getSelectedRow();
        if (fila >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "¿Eliminar proveedor permanentemente?\nSe eliminará también la asociación con productos.",
                "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int id = (int) modeloProveedores.getValueAt(fila, 0);
                if (proveedorControlador.eliminarProveedor(id)) {
                    cargarTabla();
                    actualizarResumen();
                    JOptionPane.showMessageDialog(this, "Proveedor eliminado");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar proveedor");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor");
        }
    }
}