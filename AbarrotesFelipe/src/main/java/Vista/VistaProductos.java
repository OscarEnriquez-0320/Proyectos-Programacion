package Vista;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import Controlador.ControladorPrincipal;
import Modelo.Producto;
import Modelo.Proveedor;
import Modelo.Usuario;

public class VistaProductos extends JPanel {
    private VistaPrincipal vistaPrincipal;
    private ControladorPrincipal controladorPrincipal;
    private Usuario usuarioActual;
    
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private JTextField txtBuscar;
    private JComboBox<String> cmbCategoria;
    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnRefrescar;
    
    private ArrayList<Producto> listaProductos;
    private ArrayList<Proveedor> listaProveedores;
    
    private String[] categorias = {"Todas", "Bebidas", "Panaderia", "Salchichoneria y Lacteos", "Limpieza", 
    		"Abarrotes", "General",   "Cereales y Leguminosas",
    		"Frutas y Verduras", "Productos de Higiene Personal"};
    
    public VistaProductos(VistaPrincipal vista) {
        this.vistaPrincipal = vista;
        this.controladorPrincipal = new ControladorPrincipal();
        this.usuarioActual = vista.getUsuarioActual();
        
        setLayout(new BorderLayout());
        setBackground(new Color(240, 248, 255));
        
        listaProductos = new ArrayList<Producto>();
        listaProveedores = new ArrayList<Proveedor>();
        
        iniciarComponentes();
        cargarProductos();
        cargarProveedores();
        configurarPermisos();
    }
    
    private void configurarPermisos() {
        boolean puedeEscribir = controladorPrincipal.tienePermiso(usuarioActual, "productos:escritura");
        boolean puedeEliminar = controladorPrincipal.tienePermiso(usuarioActual, "productos:eliminar");
        
        btnAgregar.setEnabled(puedeEscribir);
        btnEditar.setEnabled(puedeEscribir);
        btnEliminar.setEnabled(puedeEliminar);
        
        if (!puedeEscribir) {
            JLabel lblModoLectura = new JLabel("Modo Solo Lectura - No puede modificar productos");
            lblModoLectura.setForeground(Color.RED);
            lblModoLectura.setFont(new Font("Arial", Font.BOLD, 12));
            add(lblModoLectura, BorderLayout.SOUTH);
        }
    }
    
    private void iniciarComponentes() {
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(new Color(240, 248, 255));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JLabel lblBuscar = new JLabel("Buscar:");
        lblBuscar.setFont(new Font("Arial", Font.BOLD, 12));
        txtBuscar = new JTextField(20);
        txtBuscar.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(new Color(79, 70, 229));
        btnBuscar.setForeground(Color.BLACK);
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 12));
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JLabel lblCategoria = new JLabel("Categoria:");
        lblCategoria.setFont(new Font("Arial", Font.BOLD, 12));
        cmbCategoria = new JComboBox<>(categorias);
        cmbCategoria.setFont(new Font("Arial", Font.PLAIN, 12));
        
        panelBusqueda.add(lblBuscar);
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(btnBuscar);
        panelBusqueda.add(Box.createRigidArea(new Dimension(20, 0)));
        panelBusqueda.add(lblCategoria);
        panelBusqueda.add(cmbCategoria);
        
        panelSuperior.add(panelBusqueda, BorderLayout.WEST);
        add(panelSuperior, BorderLayout.NORTH);
        
        String[] columnas = {"ID", "Imagen", "Nombre", "Precio", "Stock", "Tipo Venta", "Stock Min", "Stock Max", "Categoria", "Proveedor"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            public Class<?> getColumnClass(int column) {
                if (column == 1) return ImageIcon.class;
                return String.class;
            }
        };
        
        tablaProductos = new JTable(modeloTabla);
        tablaProductos.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaProductos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaProductos.setRowHeight(60);
        tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaProductos.getColumnModel().getColumn(1).setPreferredWidth(60);
        
        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelInferior.setBackground(new Color(240, 248, 255));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        btnAgregar = new JButton("Agregar Producto");
        btnAgregar.setBackground(new Color(34, 197, 94));
        btnAgregar.setForeground(Color.BLACK);
        btnAgregar.setFont(new Font("Arial", Font.BOLD, 12));
        btnAgregar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnEditar = new JButton("Editar Producto");
        btnEditar.setBackground(new Color(59, 130, 246));
        btnEditar.setForeground(Color.BLACK);
        btnEditar.setFont(new Font("Arial", Font.BOLD, 12));
        btnEditar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnEliminar = new JButton("Eliminar Producto");
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
                filtrarProductos();
            }
        });
        
        txtBuscar.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                filtrarProductos();
            }
        });
        
        cmbCategoria.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                filtrarProductos();
            }
        });
        
        btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarFormularioProducto(null);
            }
        });
        
        btnEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editarProducto();
            }
        });
        
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });
        
        btnRefrescar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cargarProductos();
            }
        });
    }
    
    private void cargarProductos() {
        listaProductos = controladorPrincipal.listarProductos();
        filtrarProductos();
    }
    
    private void filtrarProductos() {
        String textoBusqueda = txtBuscar.getText().trim().toLowerCase();
        String categoriaSeleccionada = (String) cmbCategoria.getSelectedItem();
        
        if (listaProductos == null) return;
        
        ArrayList<Producto> productosFiltrados = new ArrayList<Producto>();
        
        for (int i = 0; i < listaProductos.size(); i++) {
            Producto p = listaProductos.get(i);
            
            if (categoriaSeleccionada != null && !categoriaSeleccionada.equals("Todas")) {
                if (p.getCategoria() == null || !p.getCategoria().equals(categoriaSeleccionada)) {
                    continue;
                }
            }
            
            if (!textoBusqueda.isEmpty()) {
                if (!p.getNombre().toLowerCase().contains(textoBusqueda) && 
                    (p.getCategoria() == null || !p.getCategoria().toLowerCase().contains(textoBusqueda))) {
                    continue;
                }
            }
            
            productosFiltrados.add(p);
        }
        
        actualizarTabla(productosFiltrados);
    }
    
    private void actualizarTabla(ArrayList<Producto> productos) {
        modeloTabla.setRowCount(0);
        if (productos == null) return;
        
        for (int i = 0; i < productos.size(); i++) {
            Producto p = productos.get(i);
            
            ImageIcon imagen = null;
            if (p.getRutaImagen() != null && !p.getRutaImagen().isEmpty()) {
                try {

                    File archivo = new File(p.getRutaImagen());
                    if (archivo.exists()) {
                        ImageIcon original = new ImageIcon(archivo.getAbsolutePath());
                        Image img = original.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                        imagen = new ImageIcon(img);
                    } else {
                        System.out.println("Archivo no encontrado: " + archivo.getAbsolutePath());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
            modeloTabla.addRow(new Object[]{
                p.getId(),
                imagen != null ? imagen : "",
                p.getNombre(),
                "$" + p.getPrecio(),
                p.getStock(),
                p.getTipoVenta() != null ? p.getTipoVenta() : "Unidad",
                p.getStockMinimo(),
                p.getStockMaximo(),
                p.getCategoria(),
                p.getNombreProveedor() != null ? p.getNombreProveedor() : ""
            });
        }
    }
    
    private void cargarProveedores() {
        listaProveedores = controladorPrincipal.listarProveedoresParaCombo();
    }
    
    private void mostrarFormularioProducto(Producto producto) {
        VistaFormularioProducto formulario = new VistaFormularioProducto(vistaPrincipal, this, producto, listaProveedores);
        formulario.setVisible(true);
    }
    
    private void editarProducto() {
        int fila = tablaProductos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para editar");
            return;
        }
        
        int idProducto = (int) modeloTabla.getValueAt(fila, 0);
        Producto productoEditar = null;
        
        for (int i = 0; i < listaProductos.size(); i++) {
            Producto p = listaProductos.get(i);
            if (p.getId() == idProducto) {
                productoEditar = p;
                break;
            }
        }
        
        if (productoEditar != null) {
            mostrarFormularioProducto(productoEditar);
        }
    }
    
    private void eliminarProducto() {
        int fila = tablaProductos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para eliminar");
            return;
        }
        
        int idProducto = (int) modeloTabla.getValueAt(fila, 0);
        String nombreProducto = (String) modeloTabla.getValueAt(fila, 2);
        String rutaImagen = null;
        
        for (int i = 0; i < listaProductos.size(); i++) {
            Producto p = listaProductos.get(i);
            if (p.getId() == idProducto) {
                rutaImagen = p.getRutaImagen();
                break;
            }
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "¿Deshabilitar el producto: " + nombreProducto + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean desactivado = controladorPrincipal.deshabilitarProducto(idProducto, rutaImagen);
            if (desactivado) {
                JOptionPane.showMessageDialog(this, "Producto deshabilitado correctamente");
                cargarProductos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al deshabilitar el producto");
            }
        }
    }
    
    public void refrescar() {
        cargarProductos();
        cargarProveedores();
    }
}