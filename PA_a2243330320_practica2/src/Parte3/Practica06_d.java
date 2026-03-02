package Parte3;

import Libreria.Archivotxt;
import Modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Práctica 06_d - GridBagLayout
 * Tal como lo describe el PDF en página 27
 * Configuración precisa con GridBagConstraints
 */
public class Practica06_d extends JFrame implements ActionListener {
    
    private ListaInsumos listainsumo;
    private ListaCategorias listacategorias;
    private Archivotxt archivocategorias;
    private Archivotxt archivoinsumos;
    
    // Componentes
    private JList<Categoria> ListaCategoria;
    private JTextField Tid, Tinsumo;
    private JButton Bagregar, Beliminar, Bsalir;
    private JTable TareaProductos;
    private DefaultListModel<Categoria> modelocategoria;
    private DefaultTableModel modeloinsumos;
    
    // Paneles
    private JPanel panel1; // Fila 1 - Categorías
    private JPanel panel2; // Fila 2 - Formulario
    private JPanel panel3; // Filas 3,4,5 - Tabla
    private JPanel panel4; // Fila 6 - Botones
    
    public Practica06_d() {
        setTitle("Práctica 06_d - GridBagLayout");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        
        // PASO 1: Establecer GridBagLayout
        setLayout(new GridBagLayout());
        
        // Inicializar datos
        inicializarDatos();
        
        // PASO 2: Crear los paneles
        panel1 = new JPanel(new FlowLayout());
        panel1.setBorder(BorderFactory.createTitledBorder("Categorías"));
        llenarPanel1();
        
        panel2 = new JPanel(new FlowLayout());
        panel2.setBorder(BorderFactory.createTitledBorder("Formulario"));
        llenarPanel2();
        
        panel3 = new JPanel(new BorderLayout());
        panel3.setBorder(BorderFactory.createTitledBorder("Productos"));
        llenarPanel3();
        
        panel4 = new JPanel(new FlowLayout());
        panel4.setBorder(BorderFactory.createTitledBorder("Acciones"));
        llenarPanel4();
        
        // PASO 3: Configurar GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // panel1 - fila 1
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.2;
        add(panel1, gbc);
        
        // panel2 - fila 2
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 0.2;
        add(panel2, gbc);
        
        // panel3 - filas 3, 4, 5 (gridheight = 3)
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridheight = 3;
        gbc.weighty = 0.4;
        add(panel3, gbc);
        
        // panel4 - fila 6
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridheight = 1;
        gbc.weighty = 0.2;
        add(panel4, gbc);
        
        setVisible(true);
    }
    
    private void inicializarDatos() {
        this.archivocategorias = new Archivotxt("Categoria");
        this.archivoinsumos = new Archivotxt("Insumos");
        this.listacategorias = new ListaCategorias();
        this.listainsumo = new ListaInsumos();
        
        if (this.archivocategorias.existe()) {
            this.listacategorias.cargarCategorias(this.archivocategorias.cargar());
        }
        if (this.archivoinsumos.existe()) {
            this.listainsumo.cargarInsumo(this.archivoinsumos.cargar());
        }
        
        modelocategoria = this.listacategorias.generarModeloCategorias();
        this.modeloinsumos = this.listainsumo.getmodelo(this.listacategorias);
    }
    
    private void llenarPanel1() {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(200, 100));
        
        ListaCategoria = new JList<>();
        ListaCategoria.setModel(this.modelocategoria);
        scrollPane.setViewportView(ListaCategoria);
        
        panel1.add(scrollPane);
    }
    
    private void llenarPanel2() {
        JPanel grid = new JPanel(new GridLayout(1, 4, 5, 5));
        
        grid.add(new JLabel("ID:"));
        Tid = new JTextField(10);
        Tid.setEditable(false);
        grid.add(Tid);
        
        grid.add(new JLabel("Producto:"));
        Tinsumo = new JTextField(10);
        Tinsumo.setEditable(false);
        grid.add(Tinsumo);
        
        panel2.add(grid);
    }
    
    private void llenarPanel3() {
        TareaProductos = new JTable(this.modeloinsumos);
        JScrollPane scrollPane = new JScrollPane(TareaProductos);
        panel3.add(scrollPane, BorderLayout.CENTER);
    }
    
    private void llenarPanel4() {
        Bagregar = new JButton("Agregar");
        Beliminar = new JButton("Eliminar");
        Bsalir = new JButton("Salir");
        
        Bagregar.addActionListener(this);
        Beliminar.addActionListener(this);
        Bsalir.addActionListener(this);
        
        panel4.add(Bagregar);
        panel4.add(Beliminar);
        panel4.add(Bsalir);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Bagregar) {
            JOptionPane.showMessageDialog(this, "Función Agregar - GridBagLayout");
        } else if (e.getSource() == Beliminar) {
            JOptionPane.showMessageDialog(this, "Función Eliminar - GridBagLayout");
        } else if (e.getSource() == Bsalir) {
            System.exit(0);
        }
    }
    
    public static void main(String[] args) {
        new Practica06_d();
    }
}