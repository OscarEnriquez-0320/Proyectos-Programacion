package Vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class VistaProductos extends JInternalFrame {
    
    private JPanel contentPane;
    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtPrecioCompra;
    private JTextField txtPrecioVenta;
    private JTextField txtStockInicial;
    private JTextField txtStockMinimo;
    private JTextArea txtDescripcion;
    private JComboBox<String> comboCategoria;
    private JRadioButton rdbtnActivo;
    private JRadioButton rdbtnDesactivado;
    private JButton btnGuardar;
    private JButton btnLimpiar;
    private JButton btnBuscar;
    private JButton btnEliminar;
    private JButton btnMostrarTodos;
    private JTable tableProductos;
    private JComboBox<String> comboBuscarPor;
    private JTextField txtBuscar;
    private JButton btnExportar;

    public VistaProductos() {
        setTitle("Gestión de Productos");
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setBounds(50, 50, 900, 650);
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));
        
   
        JPanel panelAltaEdicion = new JPanel();
        panelAltaEdicion.setBorder(new TitledBorder(null, "Alta y Edici\u00F3n", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        contentPane.add(panelAltaEdicion, BorderLayout.NORTH);
        GridBagLayout gbl_panelAltaEdicion = new GridBagLayout();
        gbl_panelAltaEdicion.columnWidths = new int[]{0, 0, 0, 0, 0};
        gbl_panelAltaEdicion.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_panelAltaEdicion.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
        gbl_panelAltaEdicion.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        panelAltaEdicion.setLayout(gbl_panelAltaEdicion);
        
    
        JLabel lblId = new JLabel("ID [Auto]:");
        GridBagConstraints gbc_lblId = new GridBagConstraints();
        gbc_lblId.anchor = GridBagConstraints.EAST;
        gbc_lblId.insets = new Insets(5, 5, 5, 5);
        gbc_lblId.gridx = 0;
        gbc_lblId.gridy = 0;
        panelAltaEdicion.add(lblId, gbc_lblId);
        
        txtId = new JTextField();
        txtId.setEnabled(false);
        GridBagConstraints gbc_txtId = new GridBagConstraints();
        gbc_txtId.gridwidth = 3;
        gbc_txtId.insets = new Insets(5, 0, 5, 5);
        gbc_txtId.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtId.gridx = 1;
        gbc_txtId.gridy = 0;
        panelAltaEdicion.add(txtId, gbc_txtId);
        txtId.setColumns(10);
        
        
        JLabel lblNombre = new JLabel("Nombre del Producto:");
        GridBagConstraints gbc_lblNombre = new GridBagConstraints();
        gbc_lblNombre.anchor = GridBagConstraints.EAST;
        gbc_lblNombre.insets = new Insets(5, 5, 5, 5);
        gbc_lblNombre.gridx = 0;
        gbc_lblNombre.gridy = 1;
        panelAltaEdicion.add(lblNombre, gbc_lblNombre);
        
        txtNombre = new JTextField();
        GridBagConstraints gbc_txtNombre = new GridBagConstraints();
        gbc_txtNombre.gridwidth = 3;
        gbc_txtNombre.insets = new Insets(5, 0, 5, 5);
        gbc_txtNombre.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtNombre.gridx = 1;
        gbc_txtNombre.gridy = 1;
        panelAltaEdicion.add(txtNombre, gbc_txtNombre);
        txtNombre.setColumns(10);
        
    
        JLabel lblDescripcion = new JLabel("Descripci\u00F3n:");
        GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
        gbc_lblDescripcion.anchor = GridBagConstraints.NORTHEAST;
        gbc_lblDescripcion.insets = new Insets(5, 5, 5, 5);
        gbc_lblDescripcion.gridx = 0;
        gbc_lblDescripcion.gridy = 2;
        panelAltaEdicion.add(lblDescripcion, gbc_lblDescripcion);
        
        txtDescripcion = new JTextArea();
        txtDescripcion.setRows(3);
        txtDescripcion.setLineWrap(true);
        JScrollPane scrollDesc = new JScrollPane(txtDescripcion);
        GridBagConstraints gbc_scrollDesc = new GridBagConstraints();
        gbc_scrollDesc.gridwidth = 3;
        gbc_scrollDesc.insets = new Insets(5, 0, 5, 5);
        gbc_scrollDesc.fill = GridBagConstraints.BOTH;
        gbc_scrollDesc.gridx = 1;
        gbc_scrollDesc.gridy = 2;
        panelAltaEdicion.add(scrollDesc, gbc_scrollDesc);
        
     
        JLabel lblCategoria = new JLabel("Categor\u00EDa:");
        GridBagConstraints gbc_lblCategoria = new GridBagConstraints();
        gbc_lblCategoria.anchor = GridBagConstraints.EAST;
        gbc_lblCategoria.insets = new Insets(5, 5, 5, 5);
        gbc_lblCategoria.gridx = 0;
        gbc_lblCategoria.gridy = 3;
        panelAltaEdicion.add(lblCategoria, gbc_lblCategoria);
        
        comboCategoria = new JComboBox<String>();
        comboCategoria.setModel(new DefaultComboBoxModel<String>(new String[] {"Electr\u00F3nica", "Ropa", "Alimentos", "Hogar"}));
        GridBagConstraints gbc_comboCategoria = new GridBagConstraints();
        gbc_comboCategoria.gridwidth = 3;
        gbc_comboCategoria.insets = new Insets(5, 0, 5, 5);
        gbc_comboCategoria.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboCategoria.gridx = 1;
        gbc_comboCategoria.gridy = 3;
        panelAltaEdicion.add(comboCategoria, gbc_comboCategoria);
        

        JLabel lblPrecioCompra = new JLabel("Precio Compra:");
        GridBagConstraints gbc_lblPrecioCompra = new GridBagConstraints();
        gbc_lblPrecioCompra.anchor = GridBagConstraints.EAST;
        gbc_lblPrecioCompra.insets = new Insets(5, 5, 5, 5);
        gbc_lblPrecioCompra.gridx = 0;
        gbc_lblPrecioCompra.gridy = 4;
        panelAltaEdicion.add(lblPrecioCompra, gbc_lblPrecioCompra);
        
        txtPrecioCompra = new JTextField();
        GridBagConstraints gbc_txtPrecioCompra = new GridBagConstraints();
        gbc_txtPrecioCompra.insets = new Insets(5, 0, 5, 5);
        gbc_txtPrecioCompra.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtPrecioCompra.gridx = 1;
        gbc_txtPrecioCompra.gridy = 4;
        panelAltaEdicion.add(txtPrecioCompra, gbc_txtPrecioCompra);
        txtPrecioCompra.setColumns(5);
        

        JLabel lblPrecioVenta = new JLabel("Precio Venta:");
        GridBagConstraints gbc_lblPrecioVenta = new GridBagConstraints();
        gbc_lblPrecioVenta.anchor = GridBagConstraints.EAST;
        gbc_lblPrecioVenta.insets = new Insets(5, 5, 5, 5);
        gbc_lblPrecioVenta.gridx = 2;
        gbc_lblPrecioVenta.gridy = 4;
        panelAltaEdicion.add(lblPrecioVenta, gbc_lblPrecioVenta);
        
        txtPrecioVenta = new JTextField();
        GridBagConstraints gbc_txtPrecioVenta = new GridBagConstraints();
        gbc_txtPrecioVenta.insets = new Insets(5, 0, 5, 5);
        gbc_txtPrecioVenta.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtPrecioVenta.gridx = 3;
        gbc_txtPrecioVenta.gridy = 4;
        panelAltaEdicion.add(txtPrecioVenta, gbc_txtPrecioVenta);
        txtPrecioVenta.setColumns(5);

        JLabel lblStockInicial = new JLabel("Stock Inicial:");
        GridBagConstraints gbc_lblStockInicial = new GridBagConstraints();
        gbc_lblStockInicial.anchor = GridBagConstraints.EAST;
        gbc_lblStockInicial.insets = new Insets(5, 5, 5, 5);
        gbc_lblStockInicial.gridx = 0;
        gbc_lblStockInicial.gridy = 5;
        panelAltaEdicion.add(lblStockInicial, gbc_lblStockInicial);
        
        txtStockInicial = new JTextField();
        GridBagConstraints gbc_txtStockInicial = new GridBagConstraints();
        gbc_txtStockInicial.insets = new Insets(5, 0, 5, 5);
        gbc_txtStockInicial.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtStockInicial.gridx = 1;
        gbc_txtStockInicial.gridy = 5;
        panelAltaEdicion.add(txtStockInicial, gbc_txtStockInicial);
        txtStockInicial.setColumns(5);
        
       
        JLabel lblStockMinimo = new JLabel("Stock M\u00EDnimo Alerta:");
        GridBagConstraints gbc_lblStockMinimo = new GridBagConstraints();
        gbc_lblStockMinimo.anchor = GridBagConstraints.EAST;
        gbc_lblStockMinimo.insets = new Insets(5, 5, 5, 5);
        gbc_lblStockMinimo.gridx = 2;
        gbc_lblStockMinimo.gridy = 5;
        panelAltaEdicion.add(lblStockMinimo, gbc_lblStockMinimo);
        
        txtStockMinimo = new JTextField();
        GridBagConstraints gbc_txtStockMinimo = new GridBagConstraints();
        gbc_txtStockMinimo.insets = new Insets(5, 0, 5, 5);
        gbc_txtStockMinimo.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtStockMinimo.gridx = 3;
        gbc_txtStockMinimo.gridy = 5;
        panelAltaEdicion.add(txtStockMinimo, gbc_txtStockMinimo);
        txtStockMinimo.setColumns(5);
        
        
        JLabel lblEstado = new JLabel("Estado Actual:");
        GridBagConstraints gbc_lblEstado = new GridBagConstraints();
        gbc_lblEstado.anchor = GridBagConstraints.EAST;
        gbc_lblEstado.insets = new Insets(5, 5, 5, 5);
        gbc_lblEstado.gridx = 0;
        gbc_lblEstado.gridy = 6;
        panelAltaEdicion.add(lblEstado, gbc_lblEstado);
        
        JPanel panelEstado = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panelEstado.getLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        GridBagConstraints gbc_panelEstado = new GridBagConstraints();
        gbc_panelEstado.gridwidth = 3;
        gbc_panelEstado.insets = new Insets(5, 0, 5, 5);
        gbc_panelEstado.fill = GridBagConstraints.BOTH;
        gbc_panelEstado.gridx = 1;
        gbc_panelEstado.gridy = 6;
        panelAltaEdicion.add(panelEstado, gbc_panelEstado);
        
        rdbtnActivo = new JRadioButton("Activo");
        rdbtnActivo.setSelected(true);
        panelEstado.add(rdbtnActivo);
        
        rdbtnDesactivado = new JRadioButton("Desactivado");
        panelEstado.add(rdbtnDesactivado);
        
        ButtonGroup grupoEstado = new ButtonGroup();
        grupoEstado.add(rdbtnActivo);
        grupoEstado.add(rdbtnDesactivado);
        
       
        JPanel panelBotones = new JPanel();
        GridBagConstraints gbc_panelBotones = new GridBagConstraints();
        gbc_panelBotones.gridwidth = 4;
        gbc_panelBotones.insets = new Insets(5, 0, 5, 0);
        gbc_panelBotones.fill = GridBagConstraints.BOTH;
        gbc_panelBotones.gridx = 0;
        gbc_panelBotones.gridy = 7;
        panelAltaEdicion.add(panelBotones, gbc_panelBotones);
        
        btnGuardar = new JButton("Guardar Cambios");
        panelBotones.add(btnGuardar);
        
        btnLimpiar = new JButton("Limpiar Formulario");
        panelBotones.add(btnLimpiar);
        
      
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BorderLayout(0, 0));
        contentPane.add(panelCentral, BorderLayout.CENTER);
        
      
        JPanel panelCatalogo = new JPanel();
        panelCatalogo.setBorder(new TitledBorder(null, "Cat\u00E1logo de Productos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelCentral.add(panelCatalogo, BorderLayout.CENTER);
        panelCatalogo.setLayout(new BorderLayout(0, 0));
        
        JScrollPane scrollProductos = new JScrollPane();
        panelCatalogo.add(scrollProductos, BorderLayout.CENTER);
        
        tableProductos = new JTable();
        tableProductos.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {"ID", "Nombre", "Descripción", "Categoría", "P.Compra", "P.Venta", "Stock", "Mínimo", "Estado"}
        ));
        scrollProductos.setViewportView(tableProductos);
        
       
        JPanel panelBusqueda = new JPanel();
        panelBusqueda.setBorder(new TitledBorder(null, "Buscar Productos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelCentral.add(panelBusqueda, BorderLayout.NORTH);
        
        JLabel lblBuscarPor = new JLabel("Buscar por:");
        panelBusqueda.add(lblBuscarPor);
        
        comboBuscarPor = new JComboBox<String>();
        comboBuscarPor.setModel(new DefaultComboBoxModel<String>(new String[] {"ID", "Nombre"}));
        panelBusqueda.add(comboBuscarPor);
        
        txtBuscar = new JTextField();
        txtBuscar.setColumns(10);
        panelBusqueda.add(txtBuscar);
        
        btnBuscar = new JButton("Buscar");
        panelBusqueda.add(btnBuscar);
        
        btnMostrarTodos = new JButton("Mostrar Todos");
        panelBusqueda.add(btnMostrarTodos);
        
        btnEliminar = new JButton("Eliminar");
        panelBusqueda.add(btnEliminar);
        
        btnExportar = new JButton("Exportar Lista");
        panelBusqueda.add(btnExportar);
    }


    public JTextField getTxtId() { return txtId; }
    public JTextField getTxtNombre() { return txtNombre; }
    public JTextArea getTxtDescripcion() { return txtDescripcion; }
    public JComboBox<String> getComboCategoria() { return comboCategoria; }
    public JTextField getTxtPrecioCompra() { return txtPrecioCompra; }
    public JTextField getTxtPrecioVenta() { return txtPrecioVenta; }
    public JTextField getTxtStockInicial() { return txtStockInicial; }
    public JTextField getTxtStockMinimo() { return txtStockMinimo; }
    public JRadioButton getRdbtnActivo() { return rdbtnActivo; }
    public JRadioButton getRdbtnDesactivado() { return rdbtnDesactivado; }
    public JButton getBtnGuardar() { return btnGuardar; }
    public JButton getBtnLimpiar() { return btnLimpiar; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnMostrarTodos() { return btnMostrarTodos; }
    public JButton getBtnEliminar() { return btnEliminar; }
    public JTable getTableProductos() { return tableProductos; }
}