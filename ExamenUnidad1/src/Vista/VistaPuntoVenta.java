package Vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class VistaPuntoVenta extends JInternalFrame {
    
    private JPanel contentPane;
    private JTextField txtIdCliente;
    private JTextField txtNombreCliente;
    private JTextField txtCajero;
    private JTextField txtCantidad;
    private JComboBox<String> comboProductos;
    private JButton btnAnadirCarrito;
    private JButton btnModificarCarrito;
    private JButton btnEliminarCarrito;
    private JTable tableCarrito;
    private JLabel lblSubtotal;
    private JLabel lblIVA;
    private JLabel lblTotal;
    private JButton btnLimpiarCarrito;
    private JButton btnProcesarPago;
    private JButton btnExportarTicket;

    public VistaPuntoVenta() {
        setTitle("Punto de Venta");
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setBounds(100, 100, 900, 650);
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));
       
        JPanel panelDatosVenta = new JPanel();
        panelDatosVenta.setBorder(new TitledBorder(null, "Datos de Venta", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        contentPane.add(panelDatosVenta, BorderLayout.NORTH);
        GridBagLayout gbl_panelDatosVenta = new GridBagLayout();
        gbl_panelDatosVenta.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
        gbl_panelDatosVenta.rowHeights = new int[]{0, 0};
        gbl_panelDatosVenta.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
        gbl_panelDatosVenta.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        panelDatosVenta.setLayout(gbl_panelDatosVenta);
        
        JLabel lblIdCliente = new JLabel("ID Cliente:");
        GridBagConstraints gbc_lblIdCliente = new GridBagConstraints();
        gbc_lblIdCliente.anchor = GridBagConstraints.EAST;
        gbc_lblIdCliente.insets = new Insets(5, 5, 5, 5);
        gbc_lblIdCliente.gridx = 0;
        gbc_lblIdCliente.gridy = 0;
        panelDatosVenta.add(lblIdCliente, gbc_lblIdCliente);
        
        txtIdCliente = new JTextField();
        GridBagConstraints gbc_txtIdCliente = new GridBagConstraints();
        gbc_txtIdCliente.insets = new Insets(5, 0, 5, 5);
        gbc_txtIdCliente.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtIdCliente.gridx = 1;
        gbc_txtIdCliente.gridy = 0;
        panelDatosVenta.add(txtIdCliente, gbc_txtIdCliente);
        txtIdCliente.setColumns(5);
        
        JLabel lblNombreCliente = new JLabel("Nombre:");
        GridBagConstraints gbc_lblNombreCliente = new GridBagConstraints();
        gbc_lblNombreCliente.anchor = GridBagConstraints.EAST;
        gbc_lblNombreCliente.insets = new Insets(5, 5, 5, 5);
        gbc_lblNombreCliente.gridx = 2;
        gbc_lblNombreCliente.gridy = 0;
        panelDatosVenta.add(lblNombreCliente, gbc_lblNombreCliente);
        
        txtNombreCliente = new JTextField();
        GridBagConstraints gbc_txtNombreCliente = new GridBagConstraints();
        gbc_txtNombreCliente.insets = new Insets(5, 0, 5, 5);
        gbc_txtNombreCliente.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtNombreCliente.gridx = 3;
        gbc_txtNombreCliente.gridy = 0;
        panelDatosVenta.add(txtNombreCliente, gbc_txtNombreCliente);
        txtNombreCliente.setColumns(5);
        
        JLabel lblCajero = new JLabel("Cajero:");
        GridBagConstraints gbc_lblCajero = new GridBagConstraints();
        gbc_lblCajero.anchor = GridBagConstraints.EAST;
        gbc_lblCajero.insets = new Insets(5, 5, 5, 5);
        gbc_lblCajero.gridx = 4;
        gbc_lblCajero.gridy = 0;
        panelDatosVenta.add(lblCajero, gbc_lblCajero);
        
        txtCajero = new JTextField();
        GridBagConstraints gbc_txtCajero = new GridBagConstraints();
        gbc_txtCajero.insets = new Insets(5, 0, 5, 5);
        gbc_txtCajero.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtCajero.gridx = 5;
        gbc_txtCajero.gridy = 0;
        panelDatosVenta.add(txtCajero, gbc_txtCajero);
        txtCajero.setColumns(5);
        
       
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BorderLayout(0, 0));
        contentPane.add(panelCentral, BorderLayout.CENTER);
        
      
        JPanel panelSeleccion = new JPanel();
        panelSeleccion.setBorder(new TitledBorder(null, "Selecci\u00F3n de Producto", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelCentral.add(panelSeleccion, BorderLayout.NORTH);
        
        JLabel lblProducto = new JLabel("Producto:");
        panelSeleccion.add(lblProducto);
        
        comboProductos = new JComboBox<String>();
        comboProductos.setModel(new DefaultComboBoxModel<String>(new String[] {"Seleccione un producto..."}));
        comboProductos.setPreferredSize(new java.awt.Dimension(200, 25));
        panelSeleccion.add(comboProductos);
        
        JLabel lblCantidad = new JLabel("Cantidad:");
        panelSeleccion.add(lblCantidad);
        
        txtCantidad = new JTextField();
        txtCantidad.setColumns(5);
        txtCantidad.setText("1");
        panelSeleccion.add(txtCantidad);
        
        btnAnadirCarrito = new JButton("Añadir");
        panelSeleccion.add(btnAnadirCarrito);
        
        btnModificarCarrito = new JButton("Modificar");
        panelSeleccion.add(btnModificarCarrito);
        
        btnEliminarCarrito = new JButton("Eliminar");
        panelSeleccion.add(btnEliminarCarrito);
        
      
        JScrollPane scrollCarrito = new JScrollPane();
        scrollCarrito.setBorder(new TitledBorder(null, "Detalles Transacci\u00F3n Actual", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelCentral.add(scrollCarrito, BorderLayout.CENTER);
        
        tableCarrito = new JTable();
        tableCarrito.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {"C\u00F3d", "Descripci\u00F3n", "Cantidad", "P.Unidad", "Total"}
        ));
        scrollCarrito.setViewportView(tableCarrito);
        
     
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BorderLayout(0, 0));
        contentPane.add(panelInferior, BorderLayout.SOUTH);
        
      
        JPanel panelTotales = new JPanel();
        panelTotales.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelInferior.add(panelTotales, BorderLayout.CENTER);
        panelTotales.setLayout(new GridLayout(3, 2, 10, 5));
        
        panelTotales.add(new JLabel("Subtotal:"));
        lblSubtotal = new JLabel("$0.00");
        panelTotales.add(lblSubtotal);
        
        panelTotales.add(new JLabel("IVA (16%):"));
        lblIVA = new JLabel("$0.00");
        panelTotales.add(lblIVA);
        
        panelTotales.add(new JLabel("Total a Pagar:"));
        lblTotal = new JLabel("$0.00");
        panelTotales.add(lblTotal);
        
        
        JPanel panelBotones = new JPanel();
        panelInferior.add(panelBotones, BorderLayout.SOUTH);
        
        btnLimpiarCarrito = new JButton("Limpiar Carrito");
        panelBotones.add(btnLimpiarCarrito);
        
        btnProcesarPago = new JButton("Procesar Pago");
        panelBotones.add(btnProcesarPago);
        
        btnExportarTicket = new JButton("Exportar Ticket");
        panelBotones.add(btnExportarTicket);
    }

    
    public JTextField getTxtIdCliente() { return txtIdCliente; }
    public JTextField getTxtNombreCliente() { return txtNombreCliente; }
    public JTextField getTxtCajero() { return txtCajero; }
    public JTextField getTxtCantidad() { return txtCantidad; }
    public JComboBox<String> getComboProductos() { return comboProductos; }
    public JButton getBtnAnadirCarrito() { return btnAnadirCarrito; }
    public JButton getBtnModificarCarrito() { return btnModificarCarrito; }
    public JButton getBtnEliminarCarrito() { return btnEliminarCarrito; }
    public JTable getTableCarrito() { return tableCarrito; }
    public JLabel getLblSubtotal() { return lblSubtotal; }
    public JLabel getLblIVA() { return lblIVA; }
    public JLabel getLblTotal() { return lblTotal; }
    public JButton getBtnLimpiarCarrito() { return btnLimpiarCarrito; }
    public JButton getBtnProcesarPago() { return btnProcesarPago; }
    public JButton getBtnExportarTicket() { return btnExportarTicket; }
}