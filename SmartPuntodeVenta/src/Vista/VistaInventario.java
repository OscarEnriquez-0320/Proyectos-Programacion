package Vista;

import Controlador.ProductoControlador;
import Modelo.Producto;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

public class VistaInventario extends JPanel {
    private ProductoControlador productoControlador;
    private DefaultTableModel modeloInventario;
    private JTable tablaInventario;
    private JTextField txtBuscar;
    private JComboBox<String> cmbFiltro;
    private JLabel lblTotalProductos, lblTotalValor, lblProductosBajoStock;
    private JButton btnActualizar;  // Botón de actualización
    private DecimalFormat formatoMoneda = new DecimalFormat("#,##0.00");
    
    public VistaInventario() {
        productoControlador = new ProductoControlador();
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
        
        JLabel lblTitulo = new JLabel("CONTROL DE INVENTARIO");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);
        
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBusqueda.setBackground(new Color(52, 73, 94));
        
        JLabel lblFiltro = new JLabel("Filtrar:");
        lblFiltro.setForeground(Color.WHITE);
        
        cmbFiltro = new JComboBox<>(new String[]{"Todos", "Stock Bajo", "Stock Suficiente", "Sin Stock"});
        cmbFiltro.setBackground(Color.WHITE);
        cmbFiltro.addActionListener(e -> cargarTabla());
        
        JLabel lblBuscar = new JLabel("Buscar:");
        lblBuscar.setForeground(Color.WHITE);
        
        txtBuscar = new JTextField(20);
        txtBuscar.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                cargarTabla();
            }
        });
        
        // Botón de actualización
        btnActualizar = new JButton("🔄 ACTUALIZAR");
        btnActualizar.setBackground(new Color(241, 196, 15));
        btnActualizar.setForeground(Color.BLACK);
        btnActualizar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnActualizar.setFocusPainted(false);
        btnActualizar.addActionListener(e -> {
            cargarTabla();
            actualizarResumen();
            JOptionPane.showMessageDialog(this, "Inventario actualizado", 
                "Actualizado", JOptionPane.INFORMATION_MESSAGE);
        });
        
        panelBusqueda.add(lblFiltro);
        panelBusqueda.add(cmbFiltro);
        panelBusqueda.add(Box.createHorizontalStrut(15));
        panelBusqueda.add(lblBuscar);
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(Box.createHorizontalStrut(15));
        panelBusqueda.add(btnActualizar);
        
        panelSuperior.add(lblTitulo, BorderLayout.WEST);
        panelSuperior.add(panelBusqueda, BorderLayout.EAST);
        
        add(panelSuperior, BorderLayout.NORTH);
        
        // Panel de resumen
        JPanel panelResumen = crearPanelResumen();
        add(panelResumen, BorderLayout.NORTH);
        
        // Tabla de inventario
        String[] columnas = {"ID", "Código", "Producto", "Categoría", "Stock Actual", "Stock Mínimo", "Estado", "Ubicación", "Precio Venta"};
        modeloInventario = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaInventario = new JTable(modeloInventario);
        tablaInventario.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaInventario.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tablaInventario.setRowHeight(30);
        tablaInventario.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Renderizador de colores
        tablaInventario.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, 
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    int stock = (int) table.getValueAt(row, 4);
                    int stockMinimo = (int) table.getValueAt(row, 5);
                    
                    if (stock == 0) {
                        c.setBackground(new Color(255, 235, 235));
                        c.setForeground(new Color(192, 57, 43));
                    } else if (stock <= stockMinimo) {
                        c.setBackground(new Color(255, 245, 220));
                        c.setForeground(new Color(230, 126, 34));
                    } else {
                        c.setBackground(Color.WHITE);
                        c.setForeground(Color.BLACK);
                    }
                }
                return c;
            }
        });
        
        tablaInventario.getColumnModel().getColumn(0).setPreferredWidth(40);
        tablaInventario.getColumnModel().getColumn(1).setPreferredWidth(70);
        tablaInventario.getColumnModel().getColumn(2).setPreferredWidth(180);
        tablaInventario.getColumnModel().getColumn(3).setPreferredWidth(180);
        tablaInventario.getColumnModel().getColumn(4).setPreferredWidth(80);
        tablaInventario.getColumnModel().getColumn(5).setPreferredWidth(80);
        tablaInventario.getColumnModel().getColumn(6).setPreferredWidth(100);
        tablaInventario.getColumnModel().getColumn(7).setPreferredWidth(120);
        tablaInventario.getColumnModel().getColumn(8).setPreferredWidth(90);
        
        JScrollPane scroll = new JScrollPane(tablaInventario);
        add(scroll, BorderLayout.CENTER);
        
        // Panel inferior botones
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelInferior.setBackground(new Color(245, 245, 250));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton btnAjustarStock = new JButton("📦 AJUSTAR STOCK");
        btnAjustarStock.setBackground(new Color(52, 152, 219));
        btnAjustarStock.setForeground(Color.BLACK);
        btnAjustarStock.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAjustarStock.setFocusPainted(false);
        btnAjustarStock.addActionListener(e -> ajustarStock());
        
        JButton btnVerProducto = new JButton("🔍 VER PRODUCTO");
        btnVerProducto.setBackground(new Color(149, 165, 166));
        btnVerProducto.setForeground(Color.BLACK);
        btnVerProducto.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnVerProducto.setFocusPainted(false);
        btnVerProducto.addActionListener(e -> verProducto());
        
        panelInferior.add(btnAjustarStock);
        panelInferior.add(btnVerProducto);
        
        add(panelInferior, BorderLayout.SOUTH);
    }
    
    private JPanel crearPanelResumen() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 15, 0));
        panel.setBackground(new Color(245, 245, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JPanel card1 = crearCardResumen("📦 TOTAL PRODUCTOS", "0", new Color(52, 73, 94));
        lblTotalProductos = (JLabel) ((JPanel) card1.getComponent(1)).getComponent(0);
        
        JPanel card2 = crearCardResumen("💰 VALOR INVENTARIO", "$ 0.00", new Color(46, 204, 113));
        lblTotalValor = (JLabel) ((JPanel) card2.getComponent(1)).getComponent(0);
        
        JPanel card3 = crearCardResumen("⚠️ STOCK BAJO", "0", new Color(230, 126, 34));
        lblProductosBajoStock = (JLabel) ((JPanel) card3.getComponent(1)).getComponent(0);
        
        panel.add(card1);
        panel.add(card2);
        panel.add(card3);
        
        return panel;
    }
    
    private JPanel crearCardResumen(String titulo, String valor, Color colorBorde) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(colorBorde, 2),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblTitulo.setForeground(Color.GRAY);
        
        JPanel panelValor = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelValor.setBackground(Color.WHITE);
        
        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblValor.setForeground(colorBorde);
        
        panelValor.add(lblValor);
        
        card.add(lblTitulo, BorderLayout.NORTH);
        card.add(panelValor, BorderLayout.CENTER);
        
        return card;
    }
    
    public void recargarDatos() {
        cargarTabla();
        actualizarResumen();
    }
    
    private void actualizarResumen() {
        List<Producto> productos = productoControlador.obtenerTodosProductos();
        lblTotalProductos.setText(String.valueOf(productos.size()));
        lblTotalValor.setText("$ " + formatoMoneda.format(productoControlador.calcularValorInventario()));
        lblProductosBajoStock.setText(String.valueOf(productoControlador.obtenerProductosStockBajo().size()));
    }
    
    private void cargarTabla() {
        modeloInventario.setRowCount(0);
        List<Producto> productos = productoControlador.obtenerTodosProductos();
        String filtro = (String) cmbFiltro.getSelectedItem();
        String busqueda = txtBuscar.getText().toLowerCase().trim();
        
        for (Producto p : productos) {
            boolean pasarFiltro = true;
            if (filtro.equals("Stock Bajo")) {
                pasarFiltro = p.getCantidadAlmacen() <= p.getCantidadMinima() && p.getCantidadAlmacen() > 0;
            } else if (filtro.equals("Sin Stock")) {
                pasarFiltro = p.getCantidadAlmacen() == 0;
            } else if (filtro.equals("Stock Suficiente")) {
                pasarFiltro = p.getCantidadAlmacen() > p.getCantidadMinima();
            }
            
            if (!pasarFiltro) continue;
            
            if (!busqueda.isEmpty() && 
                !p.getNombre().toLowerCase().contains(busqueda) &&
                !p.getCodigo().toLowerCase().contains(busqueda)) {
                continue;
            }
            
            String estado;
            if (p.getCantidadAlmacen() == 0) {
                estado = "AGOTADO";
            } else if (p.getCantidadAlmacen() <= p.getCantidadMinima()) {
                estado = "STOCK BAJO";
            } else {
                estado = "NORMAL";
            }
            
            modeloInventario.addRow(new Object[]{
                p.getId(),
                p.getCodigo(),
                p.getNombre(),
                p.getCategoria() != null ? p.getCategoria() : "-",
                p.getCantidadAlmacen(),
                p.getCantidadMinima(),
                estado,
                p.getUbicacion() != null ? p.getUbicacion() : "-",
                "$ " + formatoMoneda.format(p.getPrecioVenta())
            });
        }
    }
    
    private void ajustarStock() {
        int fila = tablaInventario.getSelectedRow();
        if (fila >= 0) {
            int id = (int) modeloInventario.getValueAt(fila, 0);
            Producto p = productoControlador.obtenerProductoPorId(id);
            if (p != null) {
                mostrarDialogoAjusteStock(p);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un producto");
        }
    }
    
    private void mostrarDialogoAjusteStock(Producto producto) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), 
                                      "Ajustar Stock - " + producto.getNombre(), true);
        dialog.setSize(400, 280);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel lblStockActual = new JLabel("Stock actual: " + producto.getCantidadAlmacen() + 
                                           " " + (producto.getUnidad() != null ? producto.getUnidad().getAbreviatura() : ""));
        lblStockActual.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        JLabel lblNuevoStock = new JLabel("Nuevo stock:");
        JTextField txtNuevoStock = new JTextField(10);
        txtNuevoStock.setText(String.valueOf(producto.getCantidadAlmacen()));
        
        JLabel lblMotivo = new JLabel("Motivo del ajuste:");
        JComboBox<String> cmbMotivo = new JComboBox<>(new String[]{
            "Ajuste por inventario físico", 
            "Devolución a proveedor", 
            "Merma / Daño", 
            "Producto nuevo", 
            "Corrección de inventario"
        });
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panelForm.add(lblStockActual, gbc);
        
        gbc.gridy = 1; gbc.gridwidth = 1;
        gbc.gridx = 0;
        panelForm.add(lblNuevoStock, gbc);
        gbc.gridx = 1;
        panelForm.add(txtNuevoStock, gbc);
        
        gbc.gridy = 2;
        gbc.gridx = 0;
        panelForm.add(lblMotivo, gbc);
        gbc.gridx = 1;
        panelForm.add(cmbMotivo, gbc);
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnGuardar = new JButton("💾 APLICAR AJUSTE");
        btnGuardar.setBackground(new Color(46, 204, 113));
        btnGuardar.setForeground(Color.BLACK);
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnGuardar.setFocusPainted(false);
        
        JButton btnCancelar = new JButton("CANCELAR");
        btnCancelar.setBackground(new Color(149, 165, 166));
        btnCancelar.setForeground(Color.BLACK);
        btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnCancelar.setFocusPainted(false);
        btnCancelar.addActionListener(e -> dialog.dispose());
        
        btnGuardar.addActionListener(e -> {
            try {
                int nuevoStock = Integer.parseInt(txtNuevoStock.getText().trim());
                if (nuevoStock < 0) {
                    JOptionPane.showMessageDialog(dialog, "El stock no puede ser negativo");
                    return;
                }
                
                if (productoControlador.ajustarStock(producto.getId(), nuevoStock, 
                        (String) cmbMotivo.getSelectedItem(), "")) {
                    cargarTabla();
                    actualizarResumen();
                    dialog.dispose();
                    JOptionPane.showMessageDialog(this, "Ajuste de stock aplicado");
                } else {
                    JOptionPane.showMessageDialog(dialog, "Error al ajustar stock");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Ingrese un número válido");
            }
        });
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        dialog.add(panelForm, BorderLayout.CENTER);
        dialog.add(panelBotones, BorderLayout.SOUTH);
        dialog.setVisible(true);
        
    }
    
    private void verProducto() {
        int fila = tablaInventario.getSelectedRow();
        if (fila >= 0) {
            int id = (int) modeloInventario.getValueAt(fila, 0);
            Producto p = productoControlador.obtenerProductoPorId(id);
            if (p != null) {
                String detalles = "=== DETALLES DEL PRODUCTO ===\n\n" +
                                 "Código: " + p.getCodigo() + "\n" +
                                 "Nombre: " + p.getNombre() + "\n" +
                                 "Categoría: " + (p.getCategoria() != null ? p.getCategoria() : "-") + "\n" +
                                 "Ubicación: " + (p.getUbicacion() != null ? p.getUbicacion() : "-") + "\n" +
                                 "Unidad: " + (p.getUnidad() != null ? p.getUnidad().getNombre() : "-") + "\n\n" +
                                 "Stock Actual: " + p.getCantidadAlmacen() + "\n" +
                                 "Stock Mínimo: " + p.getCantidadMinima() + "\n" +
                                 "Estado: " + (p.getCantidadAlmacen() == 0 ? "AGOTADO" : 
                                              (p.getCantidadAlmacen() <= p.getCantidadMinima() ? "STOCK BAJO" : "NORMAL")) + "\n\n" +
                                 "Precio Compra: $ " + formatoMoneda.format(p.getPrecioCompra()) + "\n" +
                                 "% Ganancia: " + p.getPorcentajeGanancia() + "%\n" +
                                 "Precio Venta: $ " + formatoMoneda.format(p.getPrecioVenta()) + "\n\n" +
                                 "Proveedor: " + (p.getProveedor() != null ? p.getProveedor().getNombre() : "-");
                
                JTextArea textArea = new JTextArea(detalles);
                textArea.setEditable(false);
                textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
                JScrollPane scroll = new JScrollPane(textArea);
                scroll.setPreferredSize(new Dimension(400, 400));
                
                JOptionPane.showMessageDialog(this, scroll, "Detalles del Producto", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un producto");
        }
    }
    
}