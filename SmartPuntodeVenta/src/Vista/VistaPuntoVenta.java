package Vista;

import Controlador.ActualizadorVistas;
import Controlador.ProductoControlador;
import Controlador.PuntoVentaControlador;
import Librerias.CargadorImagenes;
import Modelo.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

public class VistaPuntoVenta extends JPanel {
    private ProductoControlador productoControlador;
    private PuntoVentaControlador puntoVentaControlador;
    private Venta ventaActual;
    private DefaultTableModel modeloDetalles;
    private JTable tablaDetalles;
    private JTextField txtCodigoBusqueda, txtCantidad, txtEfectivo;
    private JLabel lblSubtotal, lblImpuesto, lblTotal, lblCambio, lblTotalProductos;
    private JComboBox<String> cmbMetodoPago;
    private JPanel panelProductos;
    private DecimalFormat formatoMoneda = new DecimalFormat("#,##0.00");
    
    public VistaPuntoVenta() {
        productoControlador = new ProductoControlador();
        puntoVentaControlador = new PuntoVentaControlador();
        ventaActual = new Venta();
        iniciarComponentes();
        cargarProductosGrid();
    }
    
    private void iniciarComponentes() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 245));
        
        JPanel panelSuperior = crearPanelSuperior();
        add(panelSuperior, BorderLayout.NORTH);
        
        JSplitPane splitCentral = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitCentral.setDividerLocation(550);
        splitCentral.setResizeWeight(0.4);
        
        JPanel panelProductosContainer = crearPanelProductosContainer();
        splitCentral.setLeftComponent(panelProductosContainer);
        
        JPanel panelCarrito = crearPanelCarrito();
        splitCentral.setRightComponent(panelCarrito);
        
        add(splitCentral, BorderLayout.CENTER);
        
        JPanel panelInferior = crearPanelInferior();
        add(panelInferior, BorderLayout.SOUTH);
    }
    
    private JPanel crearPanelSuperior() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(52, 73, 94));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel lblTitulo = new JLabel("REGISTRO DE VENTA");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);
        
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBusqueda.setBackground(new Color(52, 73, 94));
        
        JLabel lblCodigo = new JLabel("Código:");
        lblCodigo.setForeground(Color.WHITE);
        lblCodigo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        txtCodigoBusqueda = new JTextField(15);
        txtCodigoBusqueda.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtCodigoBusqueda.addActionListener(e -> buscarAgregarProducto());
        
        JButton btnBuscar = new JButton("🔍 Buscar");
        btnBuscar.setBackground(new Color(46, 204, 113));
        btnBuscar.setForeground(Color.BLACK);
        btnBuscar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnBuscar.setFocusPainted(false);
        btnBuscar.addActionListener(e -> buscarAgregarProducto());
        
        panelBusqueda.add(lblCodigo);
        panelBusqueda.add(txtCodigoBusqueda);
        panelBusqueda.add(btnBuscar);
        
        panel.add(lblTitulo, BorderLayout.WEST);
        panel.add(panelBusqueda, BorderLayout.EAST);
        
        return panel;
    }
    
    private JPanel crearPanelProductosContainer() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)), 
            "PRODUCTOS DESTACADOS", 
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12), new Color(52, 73, 94)
        ));
        
        panelProductos = new JPanel();
        panelProductos.setLayout(new GridLayout(0, 3, 10, 10));
        panelProductos.setBackground(Color.WHITE);
        
        JScrollPane scrollProductos = new JScrollPane(panelProductos);
        scrollProductos.setBorder(null);
        scrollProductos.getVerticalScrollBar().setUnitIncrement(16);
        
        panel.add(scrollProductos, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void cargarProductosGrid() {
        panelProductos.removeAll();
        List<Producto> productos = productoControlador.obtenerTodosProductos();
        
        for (Producto p : productos) {
            JPanel card = crearCardProducto(p);
            panelProductos.add(card);
        }
        
        panelProductos.revalidate();
        panelProductos.repaint();
    }
    
    private JPanel crearCardProducto(Producto producto) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setPreferredSize(new Dimension(160, 140));
        
        ImageIcon imgIcon = CargadorImagenes.cargarImagenProducto(producto.getNombre(), 60, 60);
        
        JLabel lblImagen = new JLabel(imgIcon);
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel lblNombre = new JLabel(producto.getNombre());
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel lblPrecio = new JLabel("$ " + formatoMoneda.format(producto.getPrecioVenta()));
        lblPrecio.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblPrecio.setForeground(new Color(46, 204, 113));
        lblPrecio.setHorizontalAlignment(SwingConstants.CENTER);
        
        String stockText = "Stock: " + producto.getCantidadAlmacen() + " " + 
                           (producto.getUnidad() != null ? producto.getUnidad().getAbreviatura() : "");
        JLabel lblStock = new JLabel(stockText);
        lblStock.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblStock.setForeground(producto.getCantidadAlmacen() <= producto.getCantidadMinima() ? Color.RED : Color.GRAY);
        lblStock.setHorizontalAlignment(SwingConstants.CENTER);
        
        JButton btnAgregar = new JButton("➕ Agregar");
        btnAgregar.setBackground(new Color(52, 152, 219));
        btnAgregar.setForeground(Color.BLACK);
        btnAgregar.setFont(new Font("Segoe UI", Font.BOLD, 10));
        btnAgregar.setFocusPainted(false);
        btnAgregar.addActionListener(e -> agregarProductoVenta(producto, 1));
        
        JPanel panelInfo = new JPanel(new GridLayout(4, 1, 2, 2));
        panelInfo.setBackground(Color.WHITE);
        panelInfo.add(lblNombre);
        panelInfo.add(lblPrecio);
        panelInfo.add(lblStock);
        panelInfo.add(btnAgregar);
        
        card.add(lblImagen, BorderLayout.CENTER);
        card.add(panelInfo, BorderLayout.SOUTH);
        
        return card;
    }
    
    private JPanel crearPanelCarrito() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "CARRITO DE COMPRA",
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12), new Color(52, 73, 94)
        ));
        
        String[] columnas = {"Código", "Producto", "Cant.", "Precio", "Subtotal"};
        modeloDetalles = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaDetalles = new JTable(modeloDetalles);
        tablaDetalles.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaDetalles.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));
        tablaDetalles.setRowHeight(25);
        
        tablaDetalles.getColumnModel().getColumn(0).setPreferredWidth(60);
        tablaDetalles.getColumnModel().getColumn(1).setPreferredWidth(180);
        tablaDetalles.getColumnModel().getColumn(2).setPreferredWidth(50);
        tablaDetalles.getColumnModel().getColumn(3).setPreferredWidth(80);
        tablaDetalles.getColumnModel().getColumn(4).setPreferredWidth(80);
        
        JScrollPane scrollTabla = new JScrollPane(tablaDetalles);
        scrollTabla.setBorder(null);
        
        JPanel panelCantidad = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelCantidad.setBackground(new Color(245, 245, 250));
        panelCantidad.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        txtCantidad = new JTextField(5);
        txtCantidad.setText("1");
        txtCantidad.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        JButton btnActualizarCantidad = new JButton("Actualizar");
        btnActualizarCantidad.setBackground(new Color(52, 152, 219));
        btnActualizarCantidad.setForeground(Color.BLACK);
        btnActualizarCantidad.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        btnActualizarCantidad.setFocusPainted(false);
        btnActualizarCantidad.addActionListener(e -> actualizarCantidad());
        
        JButton btnEliminar = new JButton("🗑 Eliminar");
        btnEliminar.setBackground(new Color(231, 76, 60));
        btnEliminar.setForeground(Color.BLACK);
        btnEliminar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        btnEliminar.setFocusPainted(false);
        btnEliminar.addActionListener(e -> eliminarProducto());
        
        panelCantidad.add(lblCantidad);
        panelCantidad.add(txtCantidad);
        panelCantidad.add(btnActualizarCantidad);
        panelCantidad.add(Box.createHorizontalStrut(20));
        panelCantidad.add(btnEliminar);
        
        panel.add(scrollTabla, BorderLayout.CENTER);
        panel.add(panelCantidad, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearPanelInferior() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(52, 73, 94));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        JPanel panelTotales = new JPanel(new GridLayout(4, 2, 10, 5));
        panelTotales.setBackground(new Color(52, 73, 94));
        
        JLabel lblSubtotalText = new JLabel("SUBTOTAL:");
        lblSubtotalText.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblSubtotalText.setForeground(Color.WHITE);
        
        lblSubtotal = new JLabel("$ 0.00");
        lblSubtotal.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblSubtotal.setForeground(Color.WHITE);
        
        JLabel lblImpuestoText = new JLabel("IMPUESTO (16%):");
        lblImpuestoText.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblImpuestoText.setForeground(Color.WHITE);
        
        lblImpuesto = new JLabel("$ 0.00");
        lblImpuesto.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblImpuesto.setForeground(Color.WHITE);
        
        JLabel lblTotalText = new JLabel("TOTAL:");
        lblTotalText.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTotalText.setForeground(new Color(46, 204, 113));
        
        lblTotal = new JLabel("$ 0.00");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTotal.setForeground(new Color(46, 204, 113));
        
        lblTotalProductos = new JLabel("0 productos");
        lblTotalProductos.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblTotalProductos.setForeground(new Color(200, 200, 200));
        
        panelTotales.add(lblSubtotalText);
        panelTotales.add(lblSubtotal);
        panelTotales.add(lblImpuestoText);
        panelTotales.add(lblImpuesto);
        panelTotales.add(lblTotalText);
        panelTotales.add(lblTotal);
        panelTotales.add(lblTotalProductos);
        panelTotales.add(new JLabel());
        
        JPanel panelPago = new JPanel(new GridLayout(3, 2, 10, 8));
        panelPago.setBackground(new Color(52, 73, 94));
        panelPago.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        
        JLabel lblMetodo = new JLabel("Método de pago:");
        lblMetodo.setForeground(Color.WHITE);
        
        cmbMetodoPago = new JComboBox<>(new String[]{"EFECTIVO", "TARJETA", "TRANSFERENCIA"});
        cmbMetodoPago.setBackground(Color.WHITE);
        
        JLabel lblEfectivo = new JLabel("Efectivo recibido:");
        lblEfectivo.setForeground(Color.WHITE);
        
        txtEfectivo = new JTextField();
        txtEfectivo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtEfectivo.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                calcularCambio();
            }
        });
        
        JLabel lblCambioText = new JLabel("Cambio:");
        lblCambioText.setForeground(Color.WHITE);
        
        lblCambio = new JLabel("$ 0.00");
        lblCambio.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblCambio.setForeground(new Color(46, 204, 113));
        
        panelPago.add(lblMetodo);
        panelPago.add(cmbMetodoPago);
        panelPago.add(lblEfectivo);
        panelPago.add(txtEfectivo);
        panelPago.add(lblCambioText);
        panelPago.add(lblCambio);
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setBackground(new Color(52, 73, 94));
        
        JButton btnNuevaVenta = new JButton("🔄 NUEVA VENTA");
        btnNuevaVenta.setBackground(new Color(149, 165, 166));
        btnNuevaVenta.setForeground(Color.BLACK);
        btnNuevaVenta.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnNuevaVenta.setFocusPainted(false);
        btnNuevaVenta.addActionListener(e -> nuevaVenta());
        
        JButton btnCobrar = new JButton("💰 COBRAR");
        btnCobrar.setBackground(new Color(46, 204, 113));
        btnCobrar.setForeground(Color.BLACK);
        btnCobrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCobrar.setFocusPainted(false);
        btnCobrar.addActionListener(e -> realizarVenta());
        
        panelBotones.add(btnNuevaVenta);
        panelBotones.add(Box.createHorizontalStrut(10));
        panelBotones.add(btnCobrar);
        
        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.setBackground(new Color(52, 73, 94));
        panelSur.add(panelTotales, BorderLayout.WEST);
        panelSur.add(panelPago, BorderLayout.CENTER);
        panelSur.add(panelBotones, BorderLayout.EAST);
        
        panel.add(panelSur, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void buscarAgregarProducto() {
        String codigo = txtCodigoBusqueda.getText().trim();
        if (!codigo.isEmpty()) {
            Producto p = productoControlador.obtenerProductoPorCodigo(codigo);
            if (p != null) {
                agregarProductoVenta(p, 1);
                txtCodigoBusqueda.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Producto no encontrado", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public void recargarProductos() {
        cargarProductosGrid();
    }
    private void agregarProductoVenta(Producto producto, int cantidad) {
        if (!puntoVentaControlador.verificarStock(producto, cantidad)) {
            JOptionPane.showMessageDialog(this, 
                "Stock insuficiente. Disponible: " + producto.getCantidadAlmacen(),
                "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        for (int i = 0; i < modeloDetalles.getRowCount(); i++) {
            if (modeloDetalles.getValueAt(i, 0).equals(producto.getCodigo())) {
                int nuevaCantidad = (Integer) modeloDetalles.getValueAt(i, 2) + cantidad;
                if (producto.getCantidadAlmacen() >= nuevaCantidad) {
                    modeloDetalles.setValueAt(nuevaCantidad, i, 2);
                    actualizarDetalleVenta(i, nuevaCantidad);
                }
                actualizarTotales();
                return;
            }
        }
        
        DetalleVenta detalle = puntoVentaControlador.agregarAlCarrito(producto, cantidad);
        ventaActual.agregarDetalle(detalle);
        
        modeloDetalles.addRow(new Object[]{
            producto.getCodigo(),
            producto.getNombre(),
            cantidad,
            "$ " + formatoMoneda.format(producto.getPrecioVenta()),
            "$ " + formatoMoneda.format(detalle.getSubtotal())
        });
        
        actualizarTotales();
    }
    
    private void actualizarDetalleVenta(int fila, int nuevaCantidad) {
        Producto p = productoControlador.obtenerProductoPorCodigo((String) modeloDetalles.getValueAt(fila, 0));
        if (p != null) {
            DetalleVenta detalle = ventaActual.getDetalles().get(fila);
            detalle.setCantidad(nuevaCantidad);
            modeloDetalles.setValueAt("$ " + formatoMoneda.format(detalle.getSubtotal()), fila, 4);
            puntoVentaControlador.recalcularTotales(ventaActual);
        }
    }
    
    private void actualizarCantidad() {
        int fila = tablaDetalles.getSelectedRow();
        if (fila >= 0) {
            try {
                int nuevaCantidad = Integer.parseInt(txtCantidad.getText().trim());
                if (nuevaCantidad > 0) {
                    Producto p = productoControlador.obtenerProductoPorCodigo((String) modeloDetalles.getValueAt(fila, 0));
                    if (puntoVentaControlador.verificarStock(p, nuevaCantidad)) {
                        modeloDetalles.setValueAt(nuevaCantidad, fila, 2);
                        actualizarDetalleVenta(fila, nuevaCantidad);
                        actualizarTotales();
                    } else {
                        JOptionPane.showMessageDialog(this, "Stock insuficiente. Máximo: " + p.getCantidadAlmacen());
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Cantidad inválida");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un producto");
        }
    }
    
    private void eliminarProducto() {
        int fila = tablaDetalles.getSelectedRow();
        if (fila >= 0) {
            ventaActual.eliminarDetalle(fila);
            modeloDetalles.removeRow(fila);
            actualizarTotales();
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un producto");
        }
    }
    
    private void actualizarTotales() {
        lblSubtotal.setText("$ " + formatoMoneda.format(ventaActual.getSubtotal()));
        lblImpuesto.setText("$ " + formatoMoneda.format(ventaActual.getImpuesto()));
        lblTotal.setText("$ " + formatoMoneda.format(ventaActual.getTotal()));
        lblTotalProductos.setText(ventaActual.getDetalles().size() + " productos");
        calcularCambio();
    }
    
    private void calcularCambio() {
        try {
            String texto = txtEfectivo.getText().trim();
            if (!texto.isEmpty()) {
                BigDecimal efectivo = new BigDecimal(texto);
                BigDecimal cambio = puntoVentaControlador.calcularCambio(efectivo, ventaActual.getTotal());
                if (efectivo.compareTo(ventaActual.getTotal()) < 0) {
                    lblCambio.setText("$ 0.00 (Faltante)");
                    lblCambio.setForeground(Color.RED);
                } else {
                    lblCambio.setText("$ " + formatoMoneda.format(cambio));
                    lblCambio.setForeground(new Color(46, 204, 113));
                }
            } else {
                lblCambio.setText("$ 0.00");
            }
        } catch (NumberFormatException e) {
            lblCambio.setText("$ 0.00");
        }
    }
    
  
    private void realizarVenta() {
        if (ventaActual.getDetalles().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay productos en la venta");
            return;
        }
        
        String metodoPago = (String) cmbMetodoPago.getSelectedItem();
        
        // Para efectivo, validar que el pago sea suficiente
        if (metodoPago.equals("EFECTIVO")) {
            try {
                String efectivoTexto = txtEfectivo.getText().trim();
                if (efectivoTexto.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Ingrese el efectivo recibido");
                    return;
                }
                
                BigDecimal efectivo = new BigDecimal(efectivoTexto);
                if (efectivo.compareTo(ventaActual.getTotal()) < 0) {
                    JOptionPane.showMessageDialog(this, 
                        "Efectivo insuficiente. Total: $ " + formatoMoneda.format(ventaActual.getTotal()) +
                        "\nRecibido: $ " + formatoMoneda.format(efectivo),
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                BigDecimal cambio = efectivo.subtract(ventaActual.getTotal());
                int confirm = JOptionPane.showConfirmDialog(this, 
                    "Total: $ " + formatoMoneda.format(ventaActual.getTotal()) +
                    "\nRecibido: $ " + formatoMoneda.format(efectivo) +
                    "\nCambio: $ " + formatoMoneda.format(cambio) +
                    "\n\n¿Confirmar venta?",
                    "Confirmar Venta", JOptionPane.YES_NO_OPTION);
                
                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Ingrese un monto válido para el efectivo");
                return;
            }
        } else {
            // Tarjeta o Transferencia - solo confirmar
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Total: $ " + formatoMoneda.format(ventaActual.getTotal()) +
                "\nMétodo: " + metodoPago +
                "\n\n¿Confirmar venta?",
                "Confirmar Venta", JOptionPane.YES_NO_OPTION);
            
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }
        }
        
        // Realizar la venta
        boolean exito = puntoVentaControlador.realizarVenta(ventaActual, metodoPago, 
                     metodoPago.equals("EFECTIVO") ? new BigDecimal(txtEfectivo.getText().trim()) : BigDecimal.ZERO);
        
        if (exito) {
            String mensaje = "Venta realizada exitosamente!\n" +
                             "Factura: " + ventaActual.getNumeroFactura() + "\n" +
                             "Total: $ " + formatoMoneda.format(ventaActual.getTotal()) + "\n" +
                             "Método: " + ventaActual.getMetodoPago();
            JOptionPane.showMessageDialog(this, mensaje, "Venta Completada", JOptionPane.INFORMATION_MESSAGE);
            nuevaVenta();
            
            // Refrescar todas las vistas
            ActualizadorVistas.refrescarTodasLasVistas();
        } else {
            JOptionPane.showMessageDialog(this, "Error al realizar la venta", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
 
    private void nuevaVenta() {
        ventaActual = new Venta();
        modeloDetalles.setRowCount(0);
        txtEfectivo.setText("");
        actualizarTotales();
        cargarProductosGrid();
    }
}