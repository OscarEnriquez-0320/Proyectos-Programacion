package Parte3;

import Libreria.Archivotxt;
import Modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Práctica 06_b - BorderLayout
 * Tal como lo describe el PDF en página 24
 * Organiza los paneles en NORTE, CENTRO, SUR, ESTE, OESTE
 */
public class Practica06_b extends JFrame implements ActionListener {
    
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
    
    // Paneles para BorderLayout
    private JPanel panelNorte;  // Panel superior (panel1 + panel2)
    private JPanel panelCentro; // Panel central (panel3 - JTable)
    private JPanel panelSur;    // Panel inferior (panel4 - botones)
    private JPanel panelEste;   // Panel derecho (opcional)
    private JPanel panelOeste;  // Panel izquierdo (opcional)
    
    public Practica06_b() {
        setTitle("Práctica 06_b - BorderLayout");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 600);
        
        // PASO 1: Layout principal BorderLayout
        setLayout(new BorderLayout(10, 10));
        
        // Inicializar datos
        inicializarDatos();
        
        // PASO 2: Crear los paneles según el PDF
        // "Crear un panel que incluya el panel1 y panel2 que estarán situado en la zona Norte"
        JPanel panel1 = new JPanel(new FlowLayout());
        panel1.setBorder(BorderFactory.createTitledBorder("Categorías"));
        panel1.setPreferredSize(new Dimension(200, 200));
        llenarPanelCategorias(panel1);
        
        JPanel panel2 = new JPanel(new FlowLayout());
        panel2.setBorder(BorderFactory.createTitledBorder("Formulario"));
        panel2.setPreferredSize(new Dimension(300, 200));
        llenarPanelFormulario(panel2);
        
        // PANEL NORTE: contiene panel1 y panel2
        panelNorte = new JPanel(new FlowLayout());
        panelNorte.add(panel1);
        panelNorte.add(panel2);
        
        // PANEL CENTRO: JTable (panel3)
        panelCentro = new JPanel(new BorderLayout());
        panelCentro.setBorder(BorderFactory.createTitledBorder("Productos"));
        TareaProductos = new JTable(this.modeloinsumos);
        JScrollPane scrollPane = new JScrollPane(TareaProductos);
        panelCentro.add(scrollPane, BorderLayout.CENTER);
        
        // PANEL SUR: Botones (panel4)
        panelSur = new JPanel(new FlowLayout());
        panelSur.setBorder(BorderFactory.createTitledBorder("Acciones"));
        Bagregar = new JButton("Agregar");
        Beliminar = new JButton("Eliminar");
        Bsalir = new JButton("Salir");
        Bagregar.addActionListener(this);
        Beliminar.addActionListener(this);
        Bsalir.addActionListener(this);
        panelSur.add(Bagregar);
        panelSur.add(Beliminar);
        panelSur.add(Bsalir);
        
        // PASO 3: Agregar paneles al JFrame en sus posiciones
        add(panelNorte, BorderLayout.NORTH);
        add(panelCentro, BorderLayout.CENTER);
        add(panelSur, BorderLayout.SOUTH);
        
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
    
    private void llenarPanelCategorias(JPanel panel) {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(180, 150));
        
        ListaCategoria = new JList<>();
        ListaCategoria.setModel(this.modelocategoria);
        scrollPane.setViewportView(ListaCategoria);
        
        panel.add(scrollPane);
    }
    
    private void llenarPanelFormulario(JPanel panel) {
        JPanel grid = new JPanel(new GridLayout(2, 2, 5, 5));
        
        grid.add(new JLabel("ID:"));
        Tid = new JTextField(10);
        Tid.setEditable(false);
        grid.add(Tid);
        
        grid.add(new JLabel("Producto:"));
        Tinsumo = new JTextField(10);
        Tinsumo.setEditable(false);
        grid.add(Tinsumo);
        
        panel.add(grid);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Bagregar) {
            JOptionPane.showMessageDialog(this, "Función Agregar - BorderLayout");
        } else if (e.getSource() == Beliminar) {
            JOptionPane.showMessageDialog(this, "Función Eliminar - BorderLayout");
        } else if (e.getSource() == Bsalir) {
            System.exit(0);
        }
    }
    
    public static void main(String[] args) {
        new Practica06_b();
    }
}