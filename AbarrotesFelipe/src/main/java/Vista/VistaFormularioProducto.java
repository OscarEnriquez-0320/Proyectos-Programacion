package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import BasedeDatos.ConexionBD;
import Controlador.ControladorProducto;
import Modelo.Producto;
import Modelo.Proveedor;

public class VistaFormularioProducto extends JDialog {
    private VistaProductos vistaProductos;
    private ControladorProducto controladorProducto;
    private Producto productoEditar;
    private List<Proveedor> listaProveedores;
    
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JTextField txtStock;
    private JTextField txtStockMinimo;
    private JTextField txtStockMaximo;
    private JComboBox<String> cmbCategoria;
    private JComboBox<String> cmbTipoVenta;
    private JComboBox<String> cmbProveedor;
    private JLabel lblImagen;
    private JButton btnSeleccionarImagen;
    private String rutaImagenSeleccionada;
    private String nombreImagenGuardada;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JLabel lblError;
    

    private JLabel lblStockUnidad;
    private JLabel lblStockMinUnidad;
    private JLabel lblStockMaxUnidad;
    
    private static final String RUTA_IMAGENES = "img_productos/";
    
    private String[] categorias = {
        "Bebidas", "Panaderia", "Salchichoneria y Lacteos", "Limpieza", 
        "Abarrotes", "General",   
        "Cereales y Leguminosas", "Frutas y Verduras", "Productos de Higiene Personal", 
    };
    
    private String[] tiposVenta = {"Unidad", "Peso", "Granel", "Paquete"};
    

    private java.util.Map<String, String> categoriaTipoMap = new java.util.HashMap<>();
    
    public VistaFormularioProducto(JFrame padre, VistaProductos vista, Producto producto, List<Proveedor> proveedores) {
        super(padre, producto == null ? "Agregar Producto" : "Editar Producto", true);
        this.vistaProductos = vista;
        this.controladorProducto = new ControladorProducto();
        this.productoEditar = producto;
        this.listaProveedores = proveedores;
        

        configurarMapaCategorias();
        
        setSize(600, 700);
        setLocationRelativeTo(padre);
        setResizable(false);
        getContentPane().setLayout(new BorderLayout());
        
        File carpeta = new File(RUTA_IMAGENES);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
        
        iniciarComponentes();
        
        if (productoEditar != null) {
            cargarDatosProducto();
        }
    }
    
    private void configurarMapaCategorias() {
       
        
        categoriaTipoMap.put("Frutas y Verduras", "Peso");
        categoriaTipoMap.put("Cereales", "Peso");
        
        
        categoriaTipoMap.put("Bebidas", "Granel");
        categoriaTipoMap.put("Lacteos", "Granel");
        
        
        categoriaTipoMap.put("Panaderia", "Unidad");
        categoriaTipoMap.put("Limpieza", "Unidad");
        categoriaTipoMap.put("Abarrotes", "Unidad");
        categoriaTipoMap.put("General", "Unidad");
        categoriaTipoMap.put("Productos de Higiene Personal", "Unidad");
    }
    
    private void iniciarComponentes() {
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(79, 70, 229));
        panelTitulo.setPreferredSize(new Dimension(getWidth(), 50));
        
        String titulo = productoEditar == null ? "AGREGAR PRODUCTO" : "EDITAR PRODUCTO";
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelTitulo.add(lblTitulo);
        
        getContentPane().add(panelTitulo, BorderLayout.NORTH);
        
        JPanel panelFormulario = new JPanel();
        panelFormulario.setBackground(new Color(240, 248, 255));
        panelFormulario.setLayout(new GridBagLayout());
        
        // Campos basicos
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Arial", Font.BOLD, 12));
        txtNombre = new JTextField(25);
        txtNombre.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setFont(new Font("Arial", Font.BOLD, 12));
        txtPrecio = new JTextField(25);
        txtPrecio.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JLabel lblCategoria = new JLabel("Categoria:");
        lblCategoria.setFont(new Font("Arial", Font.BOLD, 12));
        cmbCategoria = new JComboBox<>(categorias);
        cmbCategoria.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JLabel lblTipoVenta = new JLabel("Tipo Venta:");
        lblTipoVenta.setFont(new Font("Arial", Font.BOLD, 12));
        cmbTipoVenta = new JComboBox<>(tiposVenta);
        cmbTipoVenta.setFont(new Font("Arial", Font.PLAIN, 12));
        
        // Panel de stock con unidades dinamicas
        JLabel lblStock = new JLabel("Stock:");
        lblStock.setFont(new Font("Arial", Font.BOLD, 12));
        txtStock = new JTextField(15);
        txtStock.setFont(new Font("Arial", Font.PLAIN, 12));
        lblStockUnidad = new JLabel("unidades");
        lblStockUnidad.setFont(new Font("Arial", Font.PLAIN, 12));
        lblStockUnidad.setForeground(Color.GRAY);
        
        JLabel lblStockMin = new JLabel("Stock Minimo:");
        lblStockMin.setFont(new Font("Arial", Font.BOLD, 12));
        txtStockMinimo = new JTextField(15);
        txtStockMinimo.setFont(new Font("Arial", Font.PLAIN, 12));
        lblStockMinUnidad = new JLabel("unidades");
        lblStockMinUnidad.setFont(new Font("Arial", Font.PLAIN, 12));
        lblStockMinUnidad.setForeground(Color.GRAY);
        
        JLabel lblStockMax = new JLabel("Stock Maximo:");
        lblStockMax.setFont(new Font("Arial", Font.BOLD, 12));
        txtStockMaximo = new JTextField(15);
        txtStockMaximo.setFont(new Font("Arial", Font.PLAIN, 12));
        lblStockMaxUnidad = new JLabel("unidades");
        lblStockMaxUnidad.setFont(new Font("Arial", Font.PLAIN, 12));
        lblStockMaxUnidad.setForeground(Color.GRAY);
        
        JLabel lblProveedor = new JLabel("Proveedor:");
        lblProveedor.setFont(new Font("Arial", Font.BOLD, 12));
        
        String[] proveedoresNombres = {"Sin proveedor"};
        if (listaProveedores != null && !listaProveedores.isEmpty()) {
            proveedoresNombres = new String[listaProveedores.size() + 1];
            proveedoresNombres[0] = "Sin proveedor";
            for (int i = 0; i < listaProveedores.size(); i++) {
                proveedoresNombres[i + 1] = listaProveedores.get(i).getNombreEmpresa();
            }
        }
        cmbProveedor = new JComboBox<>(proveedoresNombres);
        cmbProveedor.setFont(new Font("Arial", Font.PLAIN, 12));
        
       
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
        btnSeleccionarImagen.setFont(new Font("Arial", Font.BOLD, 10));
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
        panelFormulario.add(lblPrecio, gbc2);
        
        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.insets = new Insets(5, 10, 5, 20);
        gbc3.gridx = 1;
        gbc3.gridy = fila++;
        gbc3.anchor = GridBagConstraints.WEST;
        panelFormulario.add(txtPrecio, gbc3);
        
        
        GridBagConstraints gbc4 = new GridBagConstraints();
        gbc4.insets = new Insets(5, 20, 5, 10);
        gbc4.gridx = 0;
        gbc4.gridy = fila;
        gbc4.anchor = GridBagConstraints.EAST;
        panelFormulario.add(lblCategoria, gbc4);
        
        GridBagConstraints gbc5 = new GridBagConstraints();
        gbc5.insets = new Insets(5, 10, 5, 20);
        gbc5.gridx = 1;
        gbc5.gridy = fila++;
        gbc5.anchor = GridBagConstraints.WEST;
        panelFormulario.add(cmbCategoria, gbc5);
        
       
        GridBagConstraints gbc6 = new GridBagConstraints();
        gbc6.insets = new Insets(5, 20, 5, 10);
        gbc6.gridx = 0;
        gbc6.gridy = fila;
        gbc6.anchor = GridBagConstraints.EAST;
        panelFormulario.add(lblTipoVenta, gbc6);
        
        GridBagConstraints gbc7 = new GridBagConstraints();
        gbc7.insets = new Insets(5, 10, 5, 20);
        gbc7.gridx = 1;
        gbc7.gridy = fila++;
        gbc7.anchor = GridBagConstraints.WEST;
        panelFormulario.add(cmbTipoVenta, gbc7);
        
       
        GridBagConstraints gbc8 = new GridBagConstraints();
        gbc8.insets = new Insets(5, 20, 5, 10);
        gbc8.gridx = 0;
        gbc8.gridy = fila;
        gbc8.anchor = GridBagConstraints.EAST;
        panelFormulario.add(lblStock, gbc8);
        
        JPanel panelStock = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelStock.add(txtStock);
        panelStock.add(lblStockUnidad);
        
        GridBagConstraints gbc9 = new GridBagConstraints();
        gbc9.insets = new Insets(5, 10, 5, 20);
        gbc9.gridx = 1;
        gbc9.gridy = fila++;
        gbc9.anchor = GridBagConstraints.WEST;
        panelFormulario.add(panelStock, gbc9);
        
        
        GridBagConstraints gbc10 = new GridBagConstraints();
        gbc10.insets = new Insets(5, 20, 5, 10);
        gbc10.gridx = 0;
        gbc10.gridy = fila;
        gbc10.anchor = GridBagConstraints.EAST;
        panelFormulario.add(lblStockMin, gbc10);
        
        JPanel panelStockMin = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelStockMin.add(txtStockMinimo);
        panelStockMin.add(lblStockMinUnidad);
        
        GridBagConstraints gbc11 = new GridBagConstraints();
        gbc11.insets = new Insets(5, 10, 5, 20);
        gbc11.gridx = 1;
        gbc11.gridy = fila++;
        gbc11.anchor = GridBagConstraints.WEST;
        panelFormulario.add(panelStockMin, gbc11);
        
        
        GridBagConstraints gbc12 = new GridBagConstraints();
        gbc12.insets = new Insets(5, 20, 5, 10);
        gbc12.gridx = 0;
        gbc12.gridy = fila;
        gbc12.anchor = GridBagConstraints.EAST;
        panelFormulario.add(lblStockMax, gbc12);
        
        JPanel panelStockMax = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelStockMax.add(txtStockMaximo);
        panelStockMax.add(lblStockMaxUnidad);
        
        GridBagConstraints gbc13 = new GridBagConstraints();
        gbc13.insets = new Insets(5, 10, 5, 20);
        gbc13.gridx = 1;
        gbc13.gridy = fila++;
        gbc13.anchor = GridBagConstraints.WEST;
        panelFormulario.add(panelStockMax, gbc13);
        
        
        GridBagConstraints gbc14 = new GridBagConstraints();
        gbc14.insets = new Insets(5, 20, 5, 10);
        gbc14.gridx = 0;
        gbc14.gridy = fila;
        gbc14.anchor = GridBagConstraints.EAST;
        panelFormulario.add(lblProveedor, gbc14);
        
        GridBagConstraints gbc15 = new GridBagConstraints();
        gbc15.insets = new Insets(5, 10, 5, 20);
        gbc15.gridx = 1;
        gbc15.gridy = fila++;
        gbc15.anchor = GridBagConstraints.WEST;
        panelFormulario.add(cmbProveedor, gbc15);
        
        
        GridBagConstraints gbc16 = new GridBagConstraints();
        gbc16.insets = new Insets(10, 20, 5, 10);
        gbc16.gridx = 0;
        gbc16.gridy = fila;
        gbc16.anchor = GridBagConstraints.NORTHEAST;
        panelFormulario.add(lblImagenTitulo, gbc16);
        
        JPanel panelImagen = new JPanel(new BorderLayout());
        panelImagen.add(lblImagen, BorderLayout.CENTER);
        panelImagen.add(btnSeleccionarImagen, BorderLayout.SOUTH);
        
        GridBagConstraints gbc17 = new GridBagConstraints();
        gbc17.insets = new Insets(10, 10, 5, 20);
        gbc17.gridx = 1;
        gbc17.gridy = fila++;
        gbc17.anchor = GridBagConstraints.WEST;
        panelFormulario.add(panelImagen, gbc17);
        
        
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        GridBagConstraints gbc18 = new GridBagConstraints();
        gbc18.insets = new Insets(20, 10, 10, 10);
        gbc18.gridx = 0;
        gbc18.gridy = fila;
        gbc18.gridwidth = 2;
        panelFormulario.add(panelBotones, gbc18);
        
        
        GridBagConstraints gbc19 = new GridBagConstraints();
        gbc19.insets = new Insets(5, 10, 20, 10);
        gbc19.gridx = 0;
        gbc19.gridy = fila + 1;
        gbc19.gridwidth = 2;
        panelFormulario.add(lblError, gbc19);
        
        getContentPane().add(panelFormulario, BorderLayout.CENTER);
        
       
        cmbCategoria.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actualizarPorCategoria();
            }
        });
        
        cmbTipoVenta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actualizarUnidades();
            }
        });
        
        btnSeleccionarImagen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                seleccionarImagen();
            }
        });
        
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarProducto();
            }
        });
        
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    private void actualizarPorCategoria() {
        String categoria = (String) cmbCategoria.getSelectedItem();
        String tipoRecomendado = categoriaTipoMap.get(categoria);
        
        if (tipoRecomendado != null) {
            cmbTipoVenta.setSelectedItem(tipoRecomendado);
        }
        
        actualizarUnidades();
    }
    
    private void actualizarUnidades() {
        String tipoVenta = (String) cmbTipoVenta.getSelectedItem();
        
        if (tipoVenta == null) return;
        
        switch (tipoVenta) {
            case "Peso":
                lblStockUnidad.setText("Kg");
                lblStockMinUnidad.setText("Kg");
                lblStockMaxUnidad.setText("Kg");
                break;
            case "Granel":
                lblStockUnidad.setText("Lts");
                lblStockMinUnidad.setText("Lts");
                lblStockMaxUnidad.setText("Lts");
                break;
            default:
                lblStockUnidad.setText("unidades");
                lblStockMinUnidad.setText("unidades");
                lblStockMaxUnidad.setText("unidades");
                break;
        }
        
        
        if (productoEditar == null) {
            if (tipoVenta.equals("Peso") || tipoVenta.equals("Granel")) {
                if (txtStock.getText().isEmpty() || txtStock.getText().equals("0")) {
                    txtStock.setText("0.0");
                }
                if (txtStockMinimo.getText().isEmpty() || txtStockMinimo.getText().equals("0")) {
                    txtStockMinimo.setText("5.0");
                }
                if (txtStockMaximo.getText().isEmpty() || txtStockMaximo.getText().equals("0")) {
                    txtStockMaximo.setText("100.0");
                }
            } else {
                if (txtStock.getText().isEmpty() || txtStock.getText().equals("0")) {
                    txtStock.setText("0");
                }
                if (txtStockMinimo.getText().isEmpty() || txtStockMinimo.getText().equals("0")) {
                    txtStockMinimo.setText("5");
                }
                if (txtStockMaximo.getText().isEmpty() || txtStockMaximo.getText().equals("0")) {
                    txtStockMaximo.setText("100");
                }
            }
        }
    }
    
    private void seleccionarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar Imagen para Producto");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "Imagenes (*.jpg, *.jpeg, *.png, *.gif)", "jpg", "jpeg", "png", "gif"));
        
        int resultado = fileChooser.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoOrigen = fileChooser.getSelectedFile();
            
            if (!archivoOrigen.exists()) {
                lblError.setText("El archivo no existe");
                return;
            }
            
           
            File carpeta = new File(RUTA_IMAGENES);
            if (!carpeta.exists()) {
                carpeta.mkdirs();
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
                
                System.out.println("Imagen guardada en: " + destino.getAbsolutePath());
                System.out.println("Nombre guardado: " + nombreImagenGuardada);
                
            } catch (Exception ex) {
                lblError.setText("Error al copiar la imagen: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
    
    private void cargarDatosProducto() {
        txtNombre.setText(productoEditar.getNombre());
        txtPrecio.setText(productoEditar.getPrecio().toString());
        txtStock.setText(String.valueOf(productoEditar.getStock()));
        txtStockMinimo.setText(String.valueOf(productoEditar.getStockMinimo()));
        txtStockMaximo.setText(String.valueOf(productoEditar.getStockMaximo()));
        cmbCategoria.setSelectedItem(productoEditar.getCategoria());
        cmbTipoVenta.setSelectedItem(productoEditar.getTipoVenta());
        
        if (productoEditar.getNombreProveedor() != null && !productoEditar.getNombreProveedor().isEmpty()) {
            cmbProveedor.setSelectedItem(productoEditar.getNombreProveedor());
        }
        
       
        if (productoEditar.getRutaImagen() != null && !productoEditar.getRutaImagen().isEmpty()) {
            nombreImagenGuardada = productoEditar.getRutaImagen();
            
           
            if (nombreImagenGuardada.contains("/")) {
                nombreImagenGuardada = nombreImagenGuardada.substring(nombreImagenGuardada.lastIndexOf("/") + 1);
            }
            
            String rutaCompleta = RUTA_IMAGENES + nombreImagenGuardada;
            File archivoImagen = new File(rutaCompleta);
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
        } else {
            lblImagen.setText("Sin imagen");
        }
    }
    
    private void guardarProducto() {
        String nombre = txtNombre.getText().trim();
        String precioStr = txtPrecio.getText().trim();
        String stockStr = txtStock.getText().trim();
        String stockMinStr = txtStockMinimo.getText().trim();
        String stockMaxStr = txtStockMaximo.getText().trim();
        String categoria = (String) cmbCategoria.getSelectedItem();
        String tipoVenta = (String) cmbTipoVenta.getSelectedItem();
        String proveedorNombre = (String) cmbProveedor.getSelectedItem();
        
        if (nombre.isEmpty()) {
            lblError.setText("El nombre es obligatorio");
            return;
        }
        
        BigDecimal precio;
        double stock, stockMin, stockMax;
        
        try {
            precio = new BigDecimal(precioStr);
            if (precio.compareTo(BigDecimal.ZERO) < 0) {
                lblError.setText("El precio no puede ser negativo");
                return;
            }
        } catch (NumberFormatException e) {
            lblError.setText("Precio invalido");
            return;
        }
        
        try {
            stock = Double.parseDouble(stockStr);
            if (stock < 0) {
                lblError.setText("El stock no puede ser negativo");
                return;
            }
        } catch (NumberFormatException e) {
            lblError.setText("Stock invalido");
            return;
        }
        
        try {
            stockMin = Double.parseDouble(stockMinStr);
        } catch (NumberFormatException e) {
            stockMin = 5;
        }
        
        try {
            stockMax = Double.parseDouble(stockMaxStr);
        } catch (NumberFormatException e) {
            stockMax = 100;
        }
        
        int idProveedor = 0;
        if (proveedorNombre != null && !proveedorNombre.equals("Sin proveedor")) {
            for (Proveedor p : listaProveedores) {
                if (p.getNombreEmpresa().equals(proveedorNombre)) {
                    idProveedor = p.getId();
                    break;
                }
            }
        }
        
        Producto producto;
        if (productoEditar == null) {
            producto = new Producto();
        } else {
            producto = productoEditar;
        }
        
        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setStock(stock);
        producto.setStockMinimo(stockMin);
        producto.setStockMaximo(stockMax);
        producto.setCategoria(categoria);
        producto.setTipoVenta(tipoVenta);
        producto.setIdProveedor(idProveedor);
        producto.setRutaImagen(RUTA_IMAGENES + nombreImagenGuardada);
        
        boolean guardado = controladorProducto.guardar(producto);
        if (nombreImagenGuardada != null && !nombreImagenGuardada.isEmpty() && 
        	    !nombreImagenGuardada.equals(productoEditar != null ? productoEditar.getRutaImagen() : null)) {
        	    producto.setRutaImagen(RUTA_IMAGENES + nombreImagenGuardada);
        	} else if (productoEditar != null) {
        	    
        	    producto.setRutaImagen(productoEditar.getRutaImagen());
        	}
        if (guardado) {
            JOptionPane.showMessageDialog(this, "Producto guardado correctamente");
            if (vistaProductos != null) {
                vistaProductos.refrescar();
            }
            dispose();
        } else {
            lblError.setText("Error al guardar el producto");
        }
    }
}