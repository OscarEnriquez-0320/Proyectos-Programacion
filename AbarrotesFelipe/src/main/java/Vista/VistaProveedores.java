package Vista;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import Controlador.ControladorProveedor;
import Modelo.Proveedor;

public class VistaProveedores extends JPanel {
    private VistaPrincipal vistaPrincipal;
    private ControladorProveedor controladorProveedor;
    
    private JTable tablaProveedores;
    private DefaultTableModel modeloTabla;
    private JTextField txtBuscar;
    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnRefrescar;
    
    private List<Proveedor> listaProveedores;
    
    public VistaProveedores(VistaPrincipal vista) {
        this.vistaPrincipal = vista;
        this.controladorProveedor = new ControladorProveedor();
        
        setLayout(new BorderLayout());
        setBackground(new Color(240, 248, 255));
        
        listaProveedores = null;
        
        iniciarComponentes();
        cargarProveedores();
    }
    
    private void iniciarComponentes() {

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(new Color(240, 248, 255));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel lblBuscar = new JLabel("Buscar:");
        lblBuscar.setFont(new Font("Arial", Font.BOLD, 12));
        
        txtBuscar = new JTextField(20);
        txtBuscar.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(new Color(79, 70, 229));
        btnBuscar.setForeground(Color.BLACK);
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 12));
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.add(lblBuscar);
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(btnBuscar);
        
        panelSuperior.add(panelBusqueda, BorderLayout.WEST);
        add(panelSuperior, BorderLayout.NORTH);
        

        String[] columnas = {"ID", "Empresa", "Contacto", "Telefono", "Categoria"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaProveedores = new JTable(modeloTabla);
        tablaProveedores.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaProveedores.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaProveedores.setRowHeight(30);
        tablaProveedores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(tablaProveedores);
        add(scrollPane, BorderLayout.CENTER);
        

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelInferior.setBackground(new Color(240, 248, 255));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        btnAgregar = new JButton("Agregar Proveedor");
        btnAgregar.setBackground(new Color(34, 197, 94));
        btnAgregar.setForeground(Color.BLACK);
        btnAgregar.setFont(new Font("Arial", Font.BOLD, 12));
        btnAgregar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnEditar = new JButton("Editar Proveedor");
        btnEditar.setBackground(new Color(59, 130, 246));
        btnEditar.setForeground(Color.BLACK);
        btnEditar.setFont(new Font("Arial", Font.BOLD, 12));
        btnEditar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnEliminar = new JButton("Eliminar Proveedor");
        btnEliminar.setBackground(new Color(220, 38, 38));
        btnEliminar.setForeground(Color.BLACK);
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 12));
        btnEliminar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnRefrescar = new JButton("Refrescar");
        btnRefrescar.setBackground(new Color(107, 114, 128));
        btnRefrescar.setForeground(Color.BLACK);
        btnRefrescar.setFont(new Font("Arial", Font.BOLD, 12));
        btnRefrescar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        panelInferior.add(btnAgregar);
        panelInferior.add(btnEditar);
        panelInferior.add(btnEliminar);
        panelInferior.add(btnRefrescar);
        
        add(panelInferior, BorderLayout.SOUTH);
        

        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buscarProveedores();
            }
        });
        
        txtBuscar.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                buscarProveedores();
            }
        });
        
        btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarFormularioProveedor(null);
            }
        });
        
        btnEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editarProveedor();
            }
        });
        
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarProveedor();
            }
        });
        
        btnRefrescar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cargarProveedores();
            }
        });
    }
    
    private void cargarProveedores() {
        listaProveedores = controladorProveedor.listarTodos();
        actualizarTabla(listaProveedores);
    }
    
    private void actualizarTabla(List<Proveedor> proveedores) {
        modeloTabla.setRowCount(0);
        
        if (proveedores == null) return;
        
        for (Proveedor p : proveedores) {
            modeloTabla.addRow(new Object[]{
                p.getId(),
                p.getNombreEmpresa(),
                p.getContactoNombre() != null ? p.getContactoNombre() : "",
                p.getTelefono() != null ? p.getTelefono() : "",
                p.getCategoria() != null ? p.getCategoria() : ""
            });
        }
    }
    
    private void buscarProveedores() {
        String texto = txtBuscar.getText().trim();
        if (texto.isEmpty()) {
            actualizarTabla(listaProveedores);
        } else {
            List<Proveedor> resultados = controladorProveedor.buscar(texto);
            actualizarTabla(resultados);
        }
    }
    
    private void mostrarFormularioProveedor(Proveedor proveedor) {
        VistaFormularioProveedor formulario = new VistaFormularioProveedor(vistaPrincipal, this, proveedor);
        formulario.setVisible(true);
    }
    
    private void editarProveedor() {
        int fila = tablaProveedores.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor para editar");
            return;
        }
        
        int idProveedor = (int) modeloTabla.getValueAt(fila, 0);
        Proveedor proveedorEditar = null;
        
        for (Proveedor p : listaProveedores) {
            if (p.getId() == idProveedor) {
                proveedorEditar = p;
                break;
            }
        }
        
        if (proveedorEditar != null) {
            mostrarFormularioProveedor(proveedorEditar);
        }
    }
    
    private void eliminarProveedor() {
        int fila = tablaProveedores.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor para eliminar");
            return;
        }
        
        int idProveedor = (int) modeloTabla.getValueAt(fila, 0);
        String nombreEmpresa = (String) modeloTabla.getValueAt(fila, 1);
        
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar al proveedor: " + nombreEmpresa + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean eliminado = controladorProveedor.eliminar(idProveedor);
            if (eliminado) {
                JOptionPane.showMessageDialog(this, "Proveedor eliminado correctamente");
                cargarProveedores();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el proveedor");
            }
        }
    }
    
    public void refrescar() {
        cargarProveedores();
    }
}