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
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class VistaInventario extends JInternalFrame {
    
    private JPanel contentPane;
    private JTextField txtId;
    private JTextField txtNombre;
    private JComboBox<String> comboTipo;
    private JRadioButton rdbtnTodos;
    private JRadioButton rdbtnDisponible;
    private JRadioButton rdbtnAgotado;
    private JButton btnBuscar;
    private JButton btnLimpiarFiltros;
    private JTable tableInventario;
    private JButton btnCrear;
    private JButton btnModificar;
    private JButton btnEliminar;

    public VistaInventario() {
        setTitle("Control de Inventario");
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setBounds(80, 80, 900, 600);
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));
        
        
        JPanel panelFiltros = new JPanel();
        panelFiltros.setBorder(new TitledBorder(null, "Filtros y B\u00FAsqueda", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        contentPane.add(panelFiltros, BorderLayout.NORTH);
        GridBagLayout gbl_panelFiltros = new GridBagLayout();
        gbl_panelFiltros.columnWidths = new int[]{0, 0, 0};
        gbl_panelFiltros.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
        gbl_panelFiltros.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gbl_panelFiltros.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        panelFiltros.setLayout(gbl_panelFiltros);
        
        JLabel lblId = new JLabel("ID:");
        GridBagConstraints gbc_lblId = new GridBagConstraints();
        gbc_lblId.anchor = GridBagConstraints.EAST;
        gbc_lblId.insets = new Insets(5, 5, 5, 5);
        gbc_lblId.gridx = 0;
        gbc_lblId.gridy = 0;
        panelFiltros.add(lblId, gbc_lblId);
        
        txtId = new JTextField();
        GridBagConstraints gbc_txtId = new GridBagConstraints();
        gbc_txtId.insets = new Insets(5, 0, 5, 5);
        gbc_txtId.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtId.gridx = 1;
        gbc_txtId.gridy = 0;
        panelFiltros.add(txtId, gbc_txtId);
        txtId.setColumns(10);
        
        JLabel lblNombre = new JLabel("Nombre:");
        GridBagConstraints gbc_lblNombre = new GridBagConstraints();
        gbc_lblNombre.anchor = GridBagConstraints.EAST;
        gbc_lblNombre.insets = new Insets(0, 5, 5, 5);
        gbc_lblNombre.gridx = 0;
        gbc_lblNombre.gridy = 1;
        panelFiltros.add(lblNombre, gbc_lblNombre);
        
        txtNombre = new JTextField();
        GridBagConstraints gbc_txtNombre = new GridBagConstraints();
        gbc_txtNombre.insets = new Insets(0, 0, 5, 5);
        gbc_txtNombre.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtNombre.gridx = 1;
        gbc_txtNombre.gridy = 1;
        panelFiltros.add(txtNombre, gbc_txtNombre);
        txtNombre.setColumns(10);
        
        JLabel lblTipo = new JLabel("Tipo:");
        GridBagConstraints gbc_lblTipo = new GridBagConstraints();
        gbc_lblTipo.anchor = GridBagConstraints.EAST;
        gbc_lblTipo.insets = new Insets(0, 5, 5, 5);
        gbc_lblTipo.gridx = 0;
        gbc_lblTipo.gridy = 2;
        panelFiltros.add(lblTipo, gbc_lblTipo);
        
        comboTipo = new JComboBox<String>();
        comboTipo.setModel(new DefaultComboBoxModel<String>(new String[] {"", "Electr\u00F3nica", "Ropa", "Alimentos"}));
        GridBagConstraints gbc_comboTipo = new GridBagConstraints();
        gbc_comboTipo.insets = new Insets(0, 0, 5, 5);
        gbc_comboTipo.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboTipo.gridx = 1;
        gbc_comboTipo.gridy = 2;
        panelFiltros.add(comboTipo, gbc_comboTipo);
        
        JLabel lblEstado = new JLabel("Estado:");
        GridBagConstraints gbc_lblEstado = new GridBagConstraints();
        gbc_lblEstado.anchor = GridBagConstraints.EAST;
        gbc_lblEstado.insets = new Insets(0, 5, 5, 5);
        gbc_lblEstado.gridx = 0;
        gbc_lblEstado.gridy = 3;
        panelFiltros.add(lblEstado, gbc_lblEstado);
        
        JPanel panelEstado = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panelEstado.getLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        GridBagConstraints gbc_panelEstado = new GridBagConstraints();
        gbc_panelEstado.insets = new Insets(0, 0, 5, 5);
        gbc_panelEstado.fill = GridBagConstraints.BOTH;
        gbc_panelEstado.gridx = 1;
        gbc_panelEstado.gridy = 3;
        panelFiltros.add(panelEstado, gbc_panelEstado);
        
        rdbtnTodos = new JRadioButton("Todos");
        rdbtnTodos.setSelected(true);
        panelEstado.add(rdbtnTodos);
        
        rdbtnDisponible = new JRadioButton("Disponible");
        panelEstado.add(rdbtnDisponible);
        
        rdbtnAgotado = new JRadioButton("Agotado");
        panelEstado.add(rdbtnAgotado);
        
        ButtonGroup grupoEstado = new ButtonGroup();
        grupoEstado.add(rdbtnTodos);
        grupoEstado.add(rdbtnDisponible);
        grupoEstado.add(rdbtnAgotado);
        
        JPanel panelBotonesFiltro = new JPanel();
        GridBagConstraints gbc_panelBotonesFiltro = new GridBagConstraints();
        gbc_panelBotonesFiltro.gridwidth = 2;
        gbc_panelBotonesFiltro.insets = new Insets(0, 0, 0, 5);
        gbc_panelBotonesFiltro.fill = GridBagConstraints.BOTH;
        gbc_panelBotonesFiltro.gridx = 0;
        gbc_panelBotonesFiltro.gridy = 4;
        panelFiltros.add(panelBotonesFiltro, gbc_panelBotonesFiltro);
        
        btnBuscar = new JButton("Buscar");
        panelBotonesFiltro.add(btnBuscar);
        
        btnLimpiarFiltros = new JButton("Limpiar Filtros");
        panelBotonesFiltro.add(btnLimpiarFiltros);
        

        JScrollPane scrollInventario = new JScrollPane();
        scrollInventario.setBorder(new TitledBorder(null, "Vista de Inventario", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        contentPane.add(scrollInventario, BorderLayout.CENTER);
        
        tableInventario = new JTable();
        tableInventario.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {"ID", "Nombre", "Tipo", "Cantidad", "Precio", "Estado"}
        ));
        scrollInventario.setViewportView(tableInventario);
        

        JPanel panelAcciones = new JPanel();
        panelAcciones.setBorder(new TitledBorder(null, "Acciones de Inventario", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        contentPane.add(panelAcciones, BorderLayout.SOUTH);
        
        btnCrear = new JButton("Nuevo Producto");
        panelAcciones.add(btnCrear);
        
        btnModificar = new JButton("Agregar Stock");
        panelAcciones.add(btnModificar);
        
        btnEliminar = new JButton("Ajustar Stock");
        panelAcciones.add(btnEliminar);
    }


    public JTextField getTxtId() { return txtId; }
    public JTextField getTxtNombre() { return txtNombre; }
    public JComboBox<String> getComboTipo() { return comboTipo; }
    public JRadioButton getRdbtnTodos() { return rdbtnTodos; }
    public JRadioButton getRdbtnDisponible() { return rdbtnDisponible; }
    public JRadioButton getRdbtnAgotado() { return rdbtnAgotado; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnLimpiarFiltros() { return btnLimpiarFiltros; }
    public JButton getBtnCrear() { return btnCrear; }
    public JButton getBtnModificar() { return btnModificar; }
    public JButton getBtnEliminar() { return btnEliminar; }
    public JTable getTableInventario() { return tableInventario; }
}