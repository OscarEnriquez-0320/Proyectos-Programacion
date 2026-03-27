package Vista;

import Controlador.ProductoControlador;
import Controlador.ProveedorControlador;
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

public class VistaProductos extends JPanel {
    private ProductoControlador productoControlador;
    private ProveedorControlador proveedorControlador;
    private DefaultTableModel modeloProductos;
    private JTable tablaProductos;
    private JTextField txtBuscar;
    private DecimalFormat formatoMoneda = new DecimalFormat("#,##0.00");
    
    // Categorías organizadas por grupos
    private String[] categorias = {
        "Seleccionar categoría...",
        // Despensa Básica
        "Despensa Básica - Arroz, frijol y leguminosas",
        "Despensa Básica - Aceites y grasas",
        "Despensa Básica - Pastas y sopas",
        "Despensa Básica - Harinas y repostería",
        "Despensa Básica - Azúcar y endulzantes",
        // Lácteos y Huevo
        "Lácteos y Huevo - Leche (entera, deslactosada, vegetal)",
        "Lácteos y Huevo - Huevos",
        "Lácteos y Huevo - Quesos frescos y maduros",
        "Lácteos y Huevo - Yogur",
        "Lácteos y Huevo - Mantequillas y cremas",
        // Bebidas y Líquidos
        "Bebidas y Líquidos - Agua natural y saborizada",
        "Bebidas y Líquidos - Refrescos y sodas",
        "Bebidas y Líquidos - Jugos y néctares",
        "Bebidas y Líquidos - Bebidas energéticas",
        "Bebidas y Líquidos - Café, té y chocolate",
        // Botanas y Dulces
        "Botanas y Dulces - Papas y frituras",
        "Botanas y Dulces - Galletas dulces y saladas",
        "Botanas y Dulces - Dulces y chocolates",
        "Botanas y Dulces - Frutos secos y semillas",
        // Frutas y Verduras
        "Frutas y Verduras - Frutas de temporada",
        "Frutas y Verduras - Verduras y hortalizas",
        "Frutas y Verduras - Hierbas de olor",
        "Frutas y Verduras - Corte y conveniencia (listas para comer)",
        // Carnes y Salchichonería
        "Carnes y Salchichonería - Pollo",
        "Carnes y Salchichonería - Res",
        "Carnes y Salchichonería - Cerdo",
        "Carnes y Salchichonería - Jamones, salchichas y chorizos",
        "Carnes y Salchichonería - Pescados y mariscos",
        // Cuidado del Hogar
        "Cuidado del Hogar - Detergentes y suavizantes",
        "Cuidado del Hogar - Limpiadores de superficies",
        "Cuidado del Hogar - Papel higiénico y servilletas",
        "Cuidado del Hogar - Desechables",
        "Cuidado del Hogar - Insecticidas",
        // Higiene y Cuidado Personal
        "Higiene y Cuidado Personal - Jabones y geles de baño",
        "Higiene y Cuidado Personal - Cuidado bucal",
        "Higiene y Cuidado Personal - Champú y cuidado del cabello",
        "Higiene y Cuidado Personal - Desodorantes",
        "Higiene y Cuidado Personal - Afeitado",
        // Alimentos Preparados/Enlatados
        "Alimentos Preparados/Enlatados - Atún y sardinas",
        "Alimentos Preparados/Enlatados - Vegetales en conserva",
        "Alimentos Preparados/Enlatados - Chiles y salsas",
        "Alimentos Preparados/Enlatados - Sopas instantáneas"
    };
    
    // Método auxiliar para crear labels con ancho fijo
    private JLabel crearLabelFijo(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setPreferredSize(new Dimension(160, 25));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        return label;
    }
    
    public VistaProductos() {
        productoControlador = new ProductoControlador();
        proveedorControlador = new ProveedorControlador();
        iniciarComponentes();
        cargarTabla();
    }
    
    private void iniciarComponentes() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        // Panel superior
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(new Color(52, 73, 94));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel lblTitulo = new JLabel("GESTIÓN DE PRODUCTOS");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);
        
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBusqueda.setBackground(new Color(52, 73, 94));
        
        JLabel lblBuscar = new JLabel("Buscar:");
        lblBuscar.setForeground(Color.WHITE);
        
        txtBuscar = new JTextField(20);
        txtBuscar.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                filtrarProductos();
            }
        });
        
        JButton btnNuevo = new JButton("+ NUEVO PRODUCTO");
        btnNuevo.setBackground(new Color(46, 204, 113));
        btnNuevo.setForeground(Color.BLACK);
        btnNuevo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnNuevo.setFocusPainted(false);
        btnNuevo.addActionListener(e -> mostrarDialogoProducto(null));
        
        JButton btnRecargarImagenes = new JButton("🖼 RECARGAR IMÁGENES");
        btnRecargarImagenes.setBackground(new Color(52, 152, 219));
        btnRecargarImagenes.setForeground(Color.BLACK);
        btnRecargarImagenes.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnRecargarImagenes.setFocusPainted(false);
        btnRecargarImagenes.addActionListener(e -> {
            CargadorImagenes.recargarImagenes();
            JOptionPane.showMessageDialog(this, "Imágenes recargadas exitosamente");
        });
        
        panelBusqueda.add(lblBuscar);
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(Box.createHorizontalStrut(10));
        panelBusqueda.add(btnRecargarImagenes);
        panelBusqueda.add(Box.createHorizontalStrut(10));
        panelBusqueda.add(btnNuevo);
        
        panelSuperior.add(lblTitulo, BorderLayout.WEST);
        panelSuperior.add(panelBusqueda, BorderLayout.EAST);
        
        add(panelSuperior, BorderLayout.NORTH);
        
        // Tabla de productos
        String[] columnas = {"ID", "Código", "Producto", "Categoría", "Precio Compra", "Precio Venta", "Stock", "Unidad", "Proveedor"};
        modeloProductos = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaProductos = new JTable(modeloProductos);
        tablaProductos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaProductos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tablaProductos.setRowHeight(30);
        tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        tablaProductos.getColumnModel().getColumn(0).setPreferredWidth(40);
        tablaProductos.getColumnModel().getColumn(1).setPreferredWidth(70);
        tablaProductos.getColumnModel().getColumn(2).setPreferredWidth(180);
        tablaProductos.getColumnModel().getColumn(3).setPreferredWidth(280);
        tablaProductos.getColumnModel().getColumn(4).setPreferredWidth(90);
        tablaProductos.getColumnModel().getColumn(5).setPreferredWidth(90);
        tablaProductos.getColumnModel().getColumn(6).setPreferredWidth(60);
        tablaProductos.getColumnModel().getColumn(7).setPreferredWidth(70);
        tablaProductos.getColumnModel().getColumn(8).setPreferredWidth(120);
        
        JScrollPane scroll = new JScrollPane(tablaProductos);
        add(scroll, BorderLayout.CENTER);
        
        // Panel inferior botones
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelInferior.setBackground(new Color(245, 245, 250));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton btnEditar = new JButton("✏ EDITAR");
        btnEditar.setBackground(new Color(52, 152, 219));
        btnEditar.setForeground(Color.BLACK);
        btnEditar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnEditar.setFocusPainted(false);
        btnEditar.addActionListener(e -> editarProducto());
        
        JButton btnEliminar = new JButton("🗑 ELIMINAR");
        btnEliminar.setBackground(new Color(231, 76, 60));
        btnEliminar.setForeground(Color.BLACK);
        btnEliminar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnEliminar.setFocusPainted(false);
        btnEliminar.addActionListener(e -> eliminarProducto());
        
        JButton btnActualizar = new JButton("🔄 ACTUALIZAR");
        btnActualizar.setBackground(new Color(149, 165, 166));
        btnActualizar.setForeground(Color.BLACK);
        btnActualizar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnActualizar.setFocusPainted(false);
        btnActualizar.addActionListener(e -> cargarTabla());
        
        panelInferior.add(btnEditar);
        panelInferior.add(btnEliminar);
        panelInferior.add(btnActualizar);
        
        add(panelInferior, BorderLayout.SOUTH);
    }
    
    private void cargarTabla() {
        modeloProductos.setRowCount(0);
        List<Producto> productos = productoControlador.obtenerTodosProductos();
        
        for (Producto p : productos) {
            modeloProductos.addRow(new Object[]{
                p.getId(),
                p.getCodigo(),
                p.getNombre(),
                p.getCategoria() != null ? p.getCategoria() : "-",
                "$ " + formatoMoneda.format(p.getPrecioCompra()),
                "$ " + formatoMoneda.format(p.getPrecioVenta()),
                p.getCantidadAlmacen(),
                p.getUnidad() != null ? p.getUnidad().getAbreviatura() : "-",
                p.getProveedor() != null ? p.getProveedor().getNombre() : "-"
            });
        }
    }
    public void recargarDatos() {
        cargarTabla();
    }
    
    private void filtrarProductos() {
        String busqueda = txtBuscar.getText().toLowerCase().trim();
        modeloProductos.setRowCount(0);
        List<Producto> productos = productoControlador.buscarProductos(busqueda);
        
        for (Producto p : productos) {
            modeloProductos.addRow(new Object[]{
                p.getId(),
                p.getCodigo(),
                p.getNombre(),
                p.getCategoria() != null ? p.getCategoria() : "-",
                "$ " + formatoMoneda.format(p.getPrecioCompra()),
                "$ " + formatoMoneda.format(p.getPrecioVenta()),
                p.getCantidadAlmacen(),
                p.getUnidad() != null ? p.getUnidad().getAbreviatura() : "-",
                p.getProveedor() != null ? p.getProveedor().getNombre() : "-"
            });
        }
    }
    
    private void mostrarDialogoProducto(Producto producto) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), 
                                      producto == null ? "Nuevo Producto" : "Editar Producto", true);
        dialog.setSize(750, 620);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelForm.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // ========== CAMPOS DEL FORMULARIO ==========
        // Fila 0: Código
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panelForm.add(crearLabelFijo("Código:"), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        JTextField txtCodigo = new JTextField(18);
        txtCodigo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtCodigo.setPreferredSize(new Dimension(180, 28));
        panelForm.add(txtCodigo, gbc);
        
        // Fila 1: Nombre
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panelForm.add(crearLabelFijo("Nombre:"), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        JTextField txtNombre = new JTextField(18);
        txtNombre.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtNombre.setPreferredSize(new Dimension(180, 28));
        panelForm.add(txtNombre, gbc);
        
        // Fila 2: Categoría (ComboBox)
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panelForm.add(crearLabelFijo("Categoría:"), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        JComboBox<String> cmbCategoria = new JComboBox<>(categorias);
        cmbCategoria.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        cmbCategoria.setEditable(false);
        cmbCategoria.setPreferredSize(new Dimension(280, 28));
        panelForm.add(cmbCategoria, gbc);
        
        // Fila 3: Precio Compra
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panelForm.add(crearLabelFijo("Precio Compra:"), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        JTextField txtPrecioCompra = new JTextField(12);
        txtPrecioCompra.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtPrecioCompra.setPreferredSize(new Dimension(120, 28));
        panelForm.add(txtPrecioCompra, gbc);
        
        // Fila 4: % Ganancia
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panelForm.add(crearLabelFijo("% Ganancia:"), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        JTextField txtPorcentaje = new JTextField(8);
        txtPorcentaje.setText("30");
        txtPorcentaje.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtPorcentaje.setPreferredSize(new Dimension(80, 28));
        panelForm.add(txtPorcentaje, gbc);
        
        // Fila 5: Precio Venta (calculado)
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panelForm.add(crearLabelFijo("Precio Venta:"), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        JTextField txtPrecioVenta = new JTextField(12);
        txtPrecioVenta.setEditable(false);
        txtPrecioVenta.setBackground(new Color(245, 245, 245));
        txtPrecioVenta.setFont(new Font("Segoe UI", Font.BOLD, 12));
        txtPrecioVenta.setForeground(new Color(46, 204, 113));
        txtPrecioVenta.setPreferredSize(new Dimension(120, 28));
        panelForm.add(txtPrecioVenta, gbc);
        
        // Fila 6: Stock Inicial
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panelForm.add(crearLabelFijo("Stock Inicial:"), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        JTextField txtStock = new JTextField(8);
        txtStock.setText("0");
        txtStock.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtStock.setPreferredSize(new Dimension(100, 28));
        panelForm.add(txtStock, gbc);
        
        // Fila 7: Stock Mínimo
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panelForm.add(crearLabelFijo("Stock Mínimo:"), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        JTextField txtStockMinimo = new JTextField(8);
        txtStockMinimo.setText("5");
        txtStockMinimo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtStockMinimo.setPreferredSize(new Dimension(100, 28));
        panelForm.add(txtStockMinimo, gbc);
        
        // Fila 8: Unidad
        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panelForm.add(crearLabelFijo("Unidad:"), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        JComboBox<UnidadMedida> cmbUnidad = new JComboBox<>();
        for (UnidadMedida u : productoControlador.obtenerTodasUnidades()) {
            cmbUnidad.addItem(u);
        }
        cmbUnidad.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cmbUnidad.setPreferredSize(new Dimension(150, 28));
        panelForm.add(cmbUnidad, gbc);
        
        // Fila 9: Ubicación
        gbc.gridx = 0; gbc.gridy = 9; gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panelForm.add(crearLabelFijo("Ubicación:"), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        JTextField txtUbicacion = new JTextField(15);
        txtUbicacion.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtUbicacion.setPreferredSize(new Dimension(180, 28));
        panelForm.add(txtUbicacion, gbc);
        
        // Fila 10: Proveedor
        gbc.gridx = 0; gbc.gridy = 10; gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panelForm.add(crearLabelFijo("Proveedor:"), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        JComboBox<Proveedor> cmbProveedor = new JComboBox<>();
        cmbProveedor.addItem(null);
        for (Proveedor prov : proveedorControlador.obtenerTodosProveedores()) {
            cmbProveedor.addItem(prov);
        }
        cmbProveedor.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cmbProveedor.setPreferredSize(new Dimension(200, 28));
        panelForm.add(cmbProveedor, gbc);
        
        // ========== PANEL DE IMAGEN (Lado Derecho) ==========
        JPanel panelImagen = new JPanel(new BorderLayout());
        panelImagen.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "IMAGEN DEL PRODUCTO",
            TitledBorder.CENTER, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12), new Color(52, 73, 94)
        ));
        panelImagen.setBackground(Color.WHITE);
        panelImagen.setPreferredSize(new Dimension(220, 280));
        
        JLabel lblImagenPreview = new JLabel();
        lblImagenPreview.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagenPreview.setVerticalAlignment(SwingConstants.CENTER);
        lblImagenPreview.setPreferredSize(new Dimension(180, 160));
        lblImagenPreview.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        
        JLabel lblInfoImagen = new JLabel("La imagen se carga automáticamente");
        lblInfoImagen.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblInfoImagen.setForeground(Color.GRAY);
        lblInfoImagen.setHorizontalAlignment(SwingConstants.CENTER);
        
        panelImagen.add(lblImagenPreview, BorderLayout.CENTER);
        panelImagen.add(lblInfoImagen, BorderLayout.SOUTH);
        
        // ========== PANEL BOTONES ==========
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setBackground(Color.WHITE);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        JButton btnGuardar = new JButton("💾 GUARDAR");
        btnGuardar.setBackground(new Color(46, 204, 113));
        btnGuardar.setForeground(Color.BLACK);
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnGuardar.setFocusPainted(false);
        btnGuardar.setPreferredSize(new Dimension(120, 35));
        
        JButton btnCancelar = new JButton("CANCELAR");
        btnCancelar.setBackground(new Color(149, 165, 166));
        btnCancelar.setForeground(Color.BLACK);
        btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setPreferredSize(new Dimension(120, 35));
        btnCancelar.addActionListener(e -> dialog.dispose());
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        // ========== PANEL PRINCIPAL CON SPLIT ==========
        JScrollPane scrollForm = new JScrollPane(panelForm);
        scrollForm.setBorder(null);
        scrollForm.getVerticalScrollBar().setUnitIncrement(16);
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(scrollForm);
        splitPane.setRightComponent(panelImagen);
        splitPane.setDividerLocation(480);
        splitPane.setResizeWeight(0.65);
        
        // ========== CARGAR DATOS SI ES EDICIÓN ==========
        if (producto != null) {
            txtCodigo.setText(producto.getCodigo());
            txtNombre.setText(producto.getNombre());
            txtPrecioCompra.setText(producto.getPrecioCompra().toString());
            txtPorcentaje.setText(producto.getPorcentajeGanancia().toString());
            txtPrecioVenta.setText("$ " + formatoMoneda.format(producto.getPrecioVenta()));
            txtStock.setText(String.valueOf(producto.getCantidadAlmacen()));
            txtStockMinimo.setText(String.valueOf(producto.getCantidadMinima()));
            txtUbicacion.setText(producto.getUbicacion() != null ? producto.getUbicacion() : "");
            
            // Seleccionar categoría
            if (producto.getCategoria() != null) {
                for (int i = 0; i < cmbCategoria.getItemCount(); i++) {
                    if (cmbCategoria.getItemAt(i).equals(producto.getCategoria())) {
                        cmbCategoria.setSelectedIndex(i);
                        break;
                    }
                }
            }
            
            // Seleccionar unidad
            for (int i = 0; i < cmbUnidad.getItemCount(); i++) {
                if (cmbUnidad.getItemAt(i).getId() == producto.getUnidad().getId()) {
                    cmbUnidad.setSelectedIndex(i);
                    break;
                }
            }
            
            // Seleccionar proveedor
            if (producto.getProveedor() != null) {
                for (int i = 0; i < cmbProveedor.getItemCount(); i++) {
                    Proveedor prov = cmbProveedor.getItemAt(i);
                    if (prov != null && prov.getId() == producto.getProveedor().getId()) {
                        cmbProveedor.setSelectedIndex(i);
                        break;
                    }
                }
            }
            
            // Cargar imagen automática
            ImageIcon icono = CargadorImagenes.cargarImagenProducto(producto.getNombre(), 160, 140);
            if (icono != null && icono.getIconWidth() > 0) {
                lblImagenPreview.setIcon(icono);
                lblImagenPreview.setText("");
            } else {
                lblImagenPreview.setIcon(null);
                lblImagenPreview.setText("Sin imagen");
                lblImagenPreview.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            }
        } else {
            txtPorcentaje.setText("30");
            txtStock.setText("0");
            txtStockMinimo.setText("5");
            lblImagenPreview.setText("Sin imagen");
            lblImagenPreview.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            lblImagenPreview.setHorizontalAlignment(SwingConstants.CENTER);
            cmbCategoria.setSelectedIndex(0);
        }
        
        // ========== ACTUALIZAR PRECIO VENTA AUTOMÁTICAMENTE ==========
        KeyAdapter actualizarPrecioVenta = new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                try {
                    BigDecimal compra = new BigDecimal(txtPrecioCompra.getText().trim());
                    BigDecimal ganancia = new BigDecimal(txtPorcentaje.getText().trim());
                    BigDecimal venta = compra.add(compra.multiply(ganancia.divide(BigDecimal.valueOf(100))));
                    txtPrecioVenta.setText("$ " + formatoMoneda.format(venta));
                } catch (Exception ex) {
                    txtPrecioVenta.setText("$ 0.00");
                }
            }
        };
        
        txtPrecioCompra.addKeyListener(actualizarPrecioVenta);
        txtPorcentaje.addKeyListener(actualizarPrecioVenta);
        
        // ========== ACTUALIZAR IMAGEN CUANDO CAMBIA EL NOMBRE ==========
        txtNombre.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                String nombre = txtNombre.getText().trim();
                if (!nombre.isEmpty()) {
                    ImageIcon icono = CargadorImagenes.cargarImagenProducto(nombre, 160, 140);
                    if (icono != null && icono.getIconWidth() > 0) {
                        lblImagenPreview.setIcon(icono);
                        lblImagenPreview.setText("");
                    } else {
                        lblImagenPreview.setIcon(null);
                        lblImagenPreview.setText("Sin imagen");
                        lblImagenPreview.setFont(new Font("Segoe UI", Font.PLAIN, 10));
                    }
                }
            }
        });
        
        // ========== ACCIÓN GUARDAR ==========
        btnGuardar.addActionListener(e -> {
            try {
                if (txtCodigo.getText().trim().isEmpty() || txtNombre.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Código y Nombre son obligatorios");
                    return;
                }
                
                if (txtPrecioCompra.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "El precio de compra es obligatorio");
                    return;
                }
                
                String categoriaSeleccionada = (String) cmbCategoria.getSelectedItem();
                if (categoriaSeleccionada.equals("Seleccionar categoría...")) {
                    categoriaSeleccionada = "";
                }
                
                boolean exito;
                if (producto == null) {
                    exito = productoControlador.crearProducto(
                        txtCodigo.getText().trim(),
                        txtNombre.getText().trim(),
                        null,
                        new BigDecimal(txtPrecioCompra.getText().trim()),
                        new BigDecimal(txtPorcentaje.getText().trim()),
                        Integer.parseInt(txtStock.getText().trim()),
                        Integer.parseInt(txtStockMinimo.getText().trim()),
                        (UnidadMedida) cmbUnidad.getSelectedItem(),
                        (Proveedor) cmbProveedor.getSelectedItem(),
                        categoriaSeleccionada,
                        txtUbicacion.getText().trim()
                    );
                } else {
                    exito = productoControlador.actualizarProducto(
                        producto.getId(),
                        txtCodigo.getText().trim(),
                        txtNombre.getText().trim(),
                        null,
                        new BigDecimal(txtPrecioCompra.getText().trim()),
                        new BigDecimal(txtPorcentaje.getText().trim()),
                        Integer.parseInt(txtStock.getText().trim()),
                        Integer.parseInt(txtStockMinimo.getText().trim()),
                        (UnidadMedida) cmbUnidad.getSelectedItem(),
                        (Proveedor) cmbProveedor.getSelectedItem(),
                        categoriaSeleccionada,
                        txtUbicacion.getText().trim()
                    );
                }
                
                if (exito) {
                    cargarTabla();
                    dialog.dispose();
                    JOptionPane.showMessageDialog(this, "Producto guardado exitosamente");
                } else {
                    JOptionPane.showMessageDialog(dialog, "Error al guardar el producto");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage());
            }
        });
        
        // ========== ARMAR DIALOG ==========
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.add(splitPane, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        dialog.add(panelPrincipal, BorderLayout.CENTER);
        dialog.setVisible(true);
    }
    
    private void editarProducto() {
        int fila = tablaProductos.getSelectedRow();
        if (fila >= 0) {
            int id = (int) modeloProductos.getValueAt(fila, 0);
            Producto p = productoControlador.obtenerProductoPorId(id);
            if (p != null) {
                mostrarDialogoProducto(p);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un producto");
        }
    }
    
    private void eliminarProducto() {
        int fila = tablaProductos.getSelectedRow();
        if (fila >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "¿Eliminar producto permanentemente?", "Confirmar", 
                JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int id = (int) modeloProductos.getValueAt(fila, 0);
                Producto p = productoControlador.obtenerProductoPorId(id);
                if (p != null) {
                    CargadorImagenes.eliminarImagenProducto(p.getNombre());
                }
                if (productoControlador.eliminarProducto(id)) {
                    cargarTabla();
                    JOptionPane.showMessageDialog(this, "Producto eliminado");
                } else {
                    JOptionPane.showMessageDialog(this, "No se puede eliminar: el producto tiene ventas asociadas");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un producto");
        }
    }
}