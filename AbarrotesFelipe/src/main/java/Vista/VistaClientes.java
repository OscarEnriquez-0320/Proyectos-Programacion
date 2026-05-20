package Vista;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import Controlador.ControladorPrincipal;
import Modelo.Cliente;

public class VistaClientes extends JPanel {
    private VistaPrincipal vistaPrincipal;
    private ControladorPrincipal controladorPrincipal;
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    private JTextField txtBuscar;
    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnRefrescar;
    
    private ArrayList<Cliente> listaClientes;
    
    public VistaClientes(VistaPrincipal vista) {
        this.vistaPrincipal = vista;
        this.controladorPrincipal = new ControladorPrincipal();
        
        setLayout(new BorderLayout());
        setBackground(new Color(240, 248, 255));
        
        listaClientes = new ArrayList<Cliente>();
        
        iniciarComponentes();
        cargarClientes();
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
        
        String[] columnas = {"ID", "Imagen", "Nombre", "Email", "Telefono", "Puntos"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            public Class<?> getColumnClass(int column) {
                if (column == 1) return ImageIcon.class;
                return String.class;
            }
        };
        
        tablaClientes = new JTable(modeloTabla);
        tablaClientes.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaClientes.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaClientes.setRowHeight(60);
        tablaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaClientes.getColumnModel().getColumn(1).setPreferredWidth(60);
        
        JScrollPane scrollPane = new JScrollPane(tablaClientes);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelInferior.setBackground(new Color(240, 248, 255));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        btnAgregar = new JButton("Agregar Cliente");
        btnAgregar.setBackground(new Color(34, 197, 94));
        btnAgregar.setForeground(Color.BLACK);
        btnAgregar.setFont(new Font("Arial", Font.BOLD, 12));
        btnAgregar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnEditar = new JButton("Editar Cliente");
        btnEditar.setBackground(new Color(59, 130, 246));
        btnEditar.setForeground(Color.BLACK);
        btnEditar.setFont(new Font("Arial", Font.BOLD, 12));
        btnEditar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnEliminar = new JButton("Eliminar Cliente");
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
                buscarClientes();
            }
        });
        
        txtBuscar.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                buscarClientes();
            }
        });
        
        btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarFormularioCliente(null);
            }
        });
        
        btnEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editarCliente();
            }
        });
        
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarCliente();
            }
        });
        
        btnRefrescar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cargarClientes();
            }
        });
    }
    
    private void cargarClientes() {
        ArrayList<Cliente> clientes = controladorPrincipal.listarClientes();
        actualizarTabla(clientes);
    }
    
    private void actualizarTabla(ArrayList<Cliente> clientes) {
        this.listaClientes = clientes;
        modeloTabla.setRowCount(0);
        
        if (clientes == null || clientes.size() == 0) {
            modeloTabla.addRow(new Object[]{"-", "", "No hay datos", "", "", ""});
            return;
        }
        
        for (int i = 0; i < clientes.size(); i++) {
            Cliente c = clientes.get(i);
            
            ImageIcon imagen = null;
            if (c.getRutaImagen() != null && !c.getRutaImagen().isEmpty()) {
                try {
                    File archivo = new File(c.getRutaImagen());
                    if (archivo.exists()) {
                        ImageIcon original = new ImageIcon(archivo.getAbsolutePath());
                        Image img = original.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                        imagen = new ImageIcon(img);
                    }
                } catch (Exception e) {
                    imagen = null;
                }
            }
            
            modeloTabla.addRow(new Object[]{
                c.getId(),
                imagen != null ? imagen : "",
                c.getNombre(),
                c.getEmail() != null ? c.getEmail() : "",
                c.getTelefono() != null ? c.getTelefono() : "",
                c.getPuntos()
            });
        }
    }
    
    private void buscarClientes() {
        String texto = txtBuscar.getText().trim();
        if (texto.isEmpty()) {
            cargarClientes();
        } else {
            ArrayList<Cliente> resultados = controladorPrincipal.buscarClientes(texto);
            actualizarTabla(resultados);
        }
    }
    
    private void mostrarFormularioCliente(Cliente cliente) {
        VistaFormularioCliente formulario = new VistaFormularioCliente(vistaPrincipal, this, cliente);
        formulario.setVisible(true);
    }
    
    private void editarCliente() {
        int fila = tablaClientes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para editar");
            return;
        }
        
        int idCliente = (int) modeloTabla.getValueAt(fila, 0);
        Cliente clienteEditar = null;
        
        for (int i = 0; i < listaClientes.size(); i++) {
            Cliente c = listaClientes.get(i);
            if (c.getId() == idCliente) {
                clienteEditar = c;
                break;
            }
        }
        
        if (clienteEditar != null) {
            mostrarFormularioCliente(clienteEditar);
        }
    }
    
    private void eliminarCliente() {
        int fila = tablaClientes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para eliminar");
            return;
        }
        
        int idCliente = (int) modeloTabla.getValueAt(fila, 0);
        String nombreCliente = (String) modeloTabla.getValueAt(fila, 2);
        
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar al cliente: " + nombreCliente + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean eliminado = controladorPrincipal.eliminarCliente(idCliente);
            if (eliminado) {
                JOptionPane.showMessageDialog(this, "Cliente eliminado correctamente");
                cargarClientes();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el cliente");
            }
        }
    }
    
    public void solicitarGuardarCliente(Cliente cliente, int idExistente) {
        if (idExistente > 0) {
            cliente.setId(idExistente);
        }
        controladorPrincipal.guardarCliente(cliente, this);
    }
    
    public void actualizarTablaDesdeControlador(ArrayList<Cliente> clientes) {
        actualizarTabla(clientes);
    }
    
    public void refrescar() {
        cargarClientes();
    }
}