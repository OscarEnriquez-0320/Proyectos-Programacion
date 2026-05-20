package Vista;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import Controlador.ControladorCaja;
import Controlador.ControladorProducto;
import Controlador.ControladorVenta;
import Modelo.Producto;
import Modelo.Usuario;

public class VistaPuntoVenta extends JPanel {
    private VistaPrincipal vistaPrincipal;
    private ControladorProducto controladorProducto;
    private ControladorVenta controladorVenta;
    private Usuario usuarioActual;
    private int idCorteAbierto;
    
    private JTextField txtBuscar;
    private JComboBox<String> cmbCategoria;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private JTable tablaCarrito;
    private DefaultTableModel modeloCarrito;
    private JLabel lblTotal;
    private JSpinner spinnerCantidadUnidad;
    private JTextField txtCantidadPeso;
    private JLabel lblCantidad;
    private JLabel lblUnidadMedida;
    private JButton btnAgregar;
    private JButton btnPagar;
    private JButton btnLimpiar;
    
    private ArrayList<Producto> listaProductos;
    private ArrayList<Producto> productosFiltrados;
    private Producto productoSeleccionadoActual;
    
    private String[] categorias = {"Todas", "Bebidas", "Panaderia", "Salchichoneria y Lacteos", "Limpieza", 
    		"Abarrotes", "General",   "Cereales y Leguminosas",
    		"Frutas y Verduras", "Productos de Higiene Personal"};
    
    
    public VistaPuntoVenta(VistaPrincipal vista, Usuario usuario, int idCorte) {
        this.vistaPrincipal = vista;
        this.controladorProducto = new ControladorProducto();
        this.controladorVenta = new ControladorVenta();
        this.usuarioActual = usuario;
        this.idCorteAbierto = idCorte;
        

        ControladorCaja controladorCaja = new ControladorCaja();
        if (!controladorCaja.hayCajaAbierta()) {
            setLayout(new BorderLayout());
            JLabel lblError = new JLabel("No hay una caja abierta. No puede realizar ventas.", SwingConstants.CENTER);
            lblError.setFont(new Font("Arial", Font.BOLD, 16));
            lblError.setForeground(Color.RED);
            add(lblError, BorderLayout.CENTER);
            return;
        }
        
        setLayout(new BorderLayout());
        setBackground(new Color(240, 248, 255));
        
        listaProductos = new ArrayList<Producto>();
        productosFiltrados = new ArrayList<Producto>();
        
        iniciarComponentes();
        cargarProductos();
    }
    private void iniciarComponentes() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridBagLayout());
        panelPrincipal.setBackground(new Color(240, 248, 255));

        JPanel panelLista = new JPanel();
        panelLista.setLayout(new BorderLayout());
        panelLista.setBackground(Color.WHITE);
        panelLista.setBorder(BorderFactory.createTitledBorder("Productos"));
        

        JPanel panelBusqueda = new JPanel();
        panelBusqueda.setLayout(new BoxLayout(panelBusqueda, BoxLayout.Y_AXIS));
        panelBusqueda.setBackground(Color.WHITE);
        panelBusqueda.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        

        JPanel panelBuscar = new JPanel(new BorderLayout());
        txtBuscar = new JTextField();
        txtBuscar.setFont(new Font("Arial", Font.PLAIN, 14));
        txtBuscar.setPreferredSize(new Dimension(200, 35));
        
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(new Color(79, 70, 229));
        btnBuscar.setForeground(Color.BLACK);
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 12));
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        panelBuscar.add(txtBuscar, BorderLayout.CENTER);
        panelBuscar.add(btnBuscar, BorderLayout.EAST);
        

        JPanel panelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblCategoria = new JLabel("Categoria:");
        lblCategoria.setFont(new Font("Arial", Font.BOLD, 12));
        cmbCategoria = new JComboBox<>(categorias);
        cmbCategoria.setFont(new Font("Arial", Font.PLAIN, 12));
        
        panelFiltro.add(lblCategoria);
        panelFiltro.add(cmbCategoria);
        
        panelBusqueda.add(panelBuscar);
        panelBusqueda.add(Box.createRigidArea(new Dimension(0, 10)));
        panelBusqueda.add(panelFiltro);
        
        panelLista.add(panelBusqueda, BorderLayout.NORTH);
        

        String[] columnas = {"ID", "Imagen", "Nombre", "Precio", "Stock", "Tipo"};
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
        

        tablaProductos.getColumnModel().getColumn(0).setPreferredWidth(50);
        tablaProductos.getColumnModel().getColumn(1).setPreferredWidth(60);
        tablaProductos.getColumnModel().getColumn(2).setPreferredWidth(200);
        tablaProductos.getColumnModel().getColumn(3).setPreferredWidth(80);
        tablaProductos.getColumnModel().getColumn(4).setPreferredWidth(60);
        tablaProductos.getColumnModel().getColumn(5).setPreferredWidth(80);
        
        tablaProductos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int fila = tablaProductos.getSelectedRow();
                    if (fila != -1 && productosFiltrados != null && fila < productosFiltrados.size()) {
                        productoSeleccionadoActual = productosFiltrados.get(fila);
                        actualizarSelectorCantidad(productoSeleccionadoActual.getTipoVenta());
                    }
                    if (spinnerCantidadUnidad != null) spinnerCantidadUnidad.setValue(1);
                    if (txtCantidadPeso != null) txtCantidadPeso.setText("1.0");
                }
            }
        });
        
        JScrollPane scrollProductos = new JScrollPane(tablaProductos);
        panelLista.add(scrollProductos, BorderLayout.CENTER);
        

        JPanel panelCantidad = new JPanel();
        panelCantidad.setLayout(new BoxLayout(panelCantidad, BoxLayout.Y_AXIS));
        panelCantidad.setBackground(Color.WHITE);
        panelCantidad.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel panelSelector = new JPanel();
        panelSelector.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelSelector.setBackground(Color.WHITE);
        
        lblCantidad = new JLabel("Cantidad: ");
        lblCantidad.setFont(new Font("Arial", Font.BOLD, 12));
        lblCantidad.setForeground(Color.BLACK);
        
        spinnerCantidadUnidad = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));
        spinnerCantidadUnidad.setFont(new Font("Arial", Font.PLAIN, 14));
        spinnerCantidadUnidad.setPreferredSize(new Dimension(80, 35));
        
        txtCantidadPeso = new JTextField(8);
        txtCantidadPeso.setText("1.0");
        txtCantidadPeso.setFont(new Font("Arial", Font.PLAIN, 14));
        txtCantidadPeso.setHorizontalAlignment(JTextField.CENTER);
        
        lblUnidadMedida = new JLabel("Kg");
        lblUnidadMedida.setFont(new Font("Arial", Font.BOLD, 12));
        lblUnidadMedida.setForeground(Color.BLACK);
        
        panelSelector.add(lblCantidad);
        panelSelector.add(spinnerCantidadUnidad);
        panelSelector.add(txtCantidadPeso);
        panelSelector.add(lblUnidadMedida);
        
        txtCantidadPeso.setVisible(false);
        lblUnidadMedida.setVisible(false);
        
        btnAgregar = new JButton("AGREGAR AL CARRITO");
        btnAgregar.setBackground(new Color(34, 197, 94));
        btnAgregar.setForeground(Color.BLACK);
        btnAgregar.setFont(new Font("Arial", Font.BOLD, 14));
        btnAgregar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAgregar.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panelCantidad.add(panelSelector);
        panelCantidad.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCantidad.add(btnAgregar);
        
        panelLista.add(panelCantidad, BorderLayout.SOUTH);
        

        JPanel panelCarrito = new JPanel();
        panelCarrito.setLayout(new BorderLayout());
        panelCarrito.setBackground(Color.WHITE);
        panelCarrito.setBorder(BorderFactory.createTitledBorder("Carrito de Compra"));
        
        String[] columnasCarrito = {"ID", "Producto", "Cantidad", "Precio", "Subtotal"};
        modeloCarrito = new DefaultTableModel(columnasCarrito, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaCarrito = new JTable(modeloCarrito);
        tablaCarrito.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaCarrito.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaCarrito.setRowHeight(30);
        
        JScrollPane scrollCarrito = new JScrollPane(tablaCarrito);
        panelCarrito.add(scrollCarrito, BorderLayout.CENTER);
        
        JPanel panelTotal = new JPanel();
        panelTotal.setLayout(new BorderLayout());
        panelTotal.setBackground(new Color(240, 248, 255));
        panelTotal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        lblTotal = new JLabel("TOTAL: $0.00");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 22));
        lblTotal.setForeground(new Color(79, 70, 229));
        lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        btnLimpiar = new JButton("LIMPIAR");
        btnLimpiar.setBackground(new Color(220, 38, 38));
        btnLimpiar.setForeground(Color.BLACK);
        btnLimpiar.setFont(new Font("Arial", Font.BOLD, 12));
        btnLimpiar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLimpiar.setPreferredSize(new Dimension(100, 35));
        
        btnPagar = new JButton("PAGAR");
        btnPagar.setBackground(new Color(34, 197, 94));
        btnPagar.setForeground(Color.BLACK);
        btnPagar.setFont(new Font("Arial", Font.BOLD, 14));
        btnPagar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPagar.setPreferredSize(new Dimension(120, 40));
        
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnPagar);
        
        panelTotal.add(lblTotal, BorderLayout.CENTER);
        panelTotal.add(panelBotones, BorderLayout.EAST);
        panelCarrito.add(panelTotal, BorderLayout.SOUTH);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.6;
        gbc.weighty = 1.0;
        panelPrincipal.add(panelLista, gbc);
        
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(10, 10, 10, 10);
        gbc2.fill = GridBagConstraints.BOTH;
        gbc2.gridx = 1;
        gbc2.gridy = 0;
        gbc2.weightx = 0.4;
        gbc2.weighty = 1.0;
        panelPrincipal.add(panelCarrito, gbc2);
        
        add(panelPrincipal, BorderLayout.CENTER);
        
       
        txtBuscar.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                filtrarProductos();
            }
        });
        
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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
                agregarAlCarrito();
            }
        });
        
        btnLimpiar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiarCarrito();
            }
        });
        
        btnPagar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarVentanaPago();
            }
        });
    }
    
    private void cargarProductos() {
        listaProductos = controladorProducto.listarTodos();
        filtrarProductos();
    }
    
    private void filtrarProductos() {
        String textoBusqueda = txtBuscar.getText().trim().toLowerCase();
        String categoriaSeleccionada = (String) cmbCategoria.getSelectedItem();
        
        if (listaProductos == null) {
            return;
        }
        
        ArrayList<Producto> filtrados = new ArrayList<Producto>();
        
      
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
            
       
            if (p.getStock() > 0) {
                filtrados.add(p);
            }
        }
        
        productosFiltrados = filtrados;
        actualizarTablaProductos();
    }
    
    private void actualizarTablaProductos() {
        modeloTabla.setRowCount(0);
        
        if (productosFiltrados == null) return;
        
        for (int i = 0; i < productosFiltrados.size(); i++) {
            Producto p = productosFiltrados.get(i);
            
      
            ImageIcon imagen = null;
            if (p.getRutaImagen() != null && !p.getRutaImagen().isEmpty()) {
                try {
                    File archivo = new File(p.getRutaImagen());
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
                p.getId(),
                imagen != null ? imagen : "",
                p.getNombre(),
                "$" + p.getPrecio(),
                p.getStock(),
                p.getTipoVenta() != null ? p.getTipoVenta() : "Unidad"
            });
        }
    }
    
    private void actualizarSelectorCantidad(String tipoVenta) {
        if (tipoVenta == null) tipoVenta = "Unidad";
        
        switch (tipoVenta) {
            case "Peso":
                spinnerCantidadUnidad.setVisible(false);
                txtCantidadPeso.setVisible(true);
                lblUnidadMedida.setText("Kg");
                lblUnidadMedida.setVisible(true);
                break;
            case "Granel":
                spinnerCantidadUnidad.setVisible(false);
                txtCantidadPeso.setVisible(true);
                lblUnidadMedida.setText("Lts");
                lblUnidadMedida.setVisible(true);
                break;
            default:
                spinnerCantidadUnidad.setVisible(true);
                txtCantidadPeso.setVisible(false);
                lblUnidadMedida.setVisible(false);
                break;
        }
        
        spinnerCantidadUnidad.getParent().revalidate();
        spinnerCantidadUnidad.getParent().repaint();
    }
    
    private double obtenerCantidadSeleccionada() {
        if (productoSeleccionadoActual == null) return 1;
        
        String tipo = productoSeleccionadoActual.getTipoVenta();
        if (tipo == null) tipo = "Unidad";
        
        switch (tipo) {
            case "Peso":
            case "Granel":
                try {
                    return Double.parseDouble(txtCantidadPeso.getText().trim());
                } catch (NumberFormatException e) {
                    return 1.0;
                }
            default:
                return (int) spinnerCantidadUnidad.getValue();
        }
    }
    
    private void agregarAlCarrito() {
        if (productoSeleccionadoActual == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto de la lista");
            return;
        }
        
        double cantidad = obtenerCantidadSeleccionada();
        String mensaje = controladorVenta.agregarAlCarrito(productoSeleccionadoActual, cantidad);
        JOptionPane.showMessageDialog(this, mensaje);
        actualizarTablaCarrito();
        
        if (!mensaje.contains("Error") && !mensaje.contains("insuficiente")) {
            if (productoSeleccionadoActual.getTipoVenta() == null || productoSeleccionadoActual.getTipoVenta().equals("Unidad")) {
                spinnerCantidadUnidad.setValue(1);
            } else {
                txtCantidadPeso.setText("1.0");
            }
            cargarProductos();
        }
    }
    
    private void actualizarTablaCarrito() {
        modeloCarrito.setRowCount(0);
        
        java.util.List<ControladorVenta.ItemCarrito> carrito = controladorVenta.getCarrito();
        for (int i = 0; i < carrito.size(); i++) {
            ControladorVenta.ItemCarrito item = carrito.get(i);
            modeloCarrito.addRow(new Object[]{
                item.getIdProducto(),
                item.getNombre(),
                item.getCantidadTexto(),
                "$" + item.getPrecio(),
                "$" + item.getSubtotal()
            });
        }
        
        lblTotal.setText("TOTAL: $" + controladorVenta.getTotalCarrito().toString());
    }
    
    private void limpiarCarrito() {
        if (controladorVenta.carritoVacio()) {
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "¿Desea limpiar todo el carrito?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            controladorVenta.limpiarCarrito();
            actualizarTablaCarrito();
        }
    }
    
    private void mostrarVentanaPago() {
        if (controladorVenta.carritoVacio()) {
            JOptionPane.showMessageDialog(this, "El carrito esta vacio");
            return;
        }
        
        VistaPago pago = new VistaPago(vistaPrincipal, this, controladorVenta.getTotalCarrito(), 
                                        controladorVenta.getCarrito(), idCorteAbierto, usuarioActual.getId());
        pago.setVisible(true);
    }
    
    public void finalizarVenta() {
        cargarProductos();
        controladorVenta.limpiarCarrito();
        actualizarTablaCarrito();
        if (vistaPrincipal != null) {
            vistaPrincipal.refrescarMontoCaja();
        }
    }
    
    public void refrescar() {
        cargarProductos();
        controladorVenta.limpiarCarrito();
        actualizarTablaCarrito();
    }
}