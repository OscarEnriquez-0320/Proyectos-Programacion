package Parte3;

import Libreria.Archivotxt;
import Modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Práctica 06_c - GridLayout
 * Tal como lo describe el PDF en página 26
 * Organiza los paneles en 4 filas
 */
public class Practica06_c extends JFrame implements ActionListener {
    
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
    
    // Paneles para cada fila
    private JPanel fila1; // Categorías
    private JPanel fila2; // Formulario
    private JPanel fila3; // Tabla
    private JPanel fila4; // Botones
    
    public Practica06_c() {
        setTitle("Práctica 06_c - GridLayout");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        
        // PASO 1: Establecer 4 filas en el Layout principal (como dice el PDF)
        setLayout(new GridLayout(4, 1, 10, 10));
        
        // Inicializar datos
        inicializarDatos();
        
        // PASO 2: Agregar un panel a cada fila
        fila1 = new JPanel(new FlowLayout());
        fila1.setBorder(BorderFactory.createTitledBorder("Fila 1 - Categorías"));
        llenarFila1();
        
        fila2 = new JPanel(new FlowLayout());
        fila2.setBorder(BorderFactory.createTitledBorder("Fila 2 - Formulario"));
        llenarFila2();
        
        fila3 = new JPanel(new BorderLayout());
        fila3.setBorder(BorderFactory.createTitledBorder("Fila 3 - Tabla de Productos"));
        llenarFila3();
        
        fila4 = new JPanel(new FlowLayout());
        fila4.setBorder(BorderFactory.createTitledBorder("Fila 4 - Botones"));
        llenarFila4();
        
        // Agregar filas al JFrame
        add(fila1);
        add(fila2);
        add(fila3);
        add(fila4);
        
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
    
    private void llenarFila1() {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(200, 100));
        
        ListaCategoria = new JList<>();
        ListaCategoria.setModel(this.modelocategoria);
        scrollPane.setViewportView(ListaCategoria);
        
        fila1.add(scrollPane);
    }
    
    private void llenarFila2() {
        JPanel grid = new JPanel(new GridLayout(1, 4, 10, 10));
        
        grid.add(new JLabel("ID:"));
        Tid = new JTextField(10);
        Tid.setEditable(false);
        grid.add(Tid);
        
        grid.add(new JLabel("Producto:"));
        Tinsumo = new JTextField(10);
        Tinsumo.setEditable(false);
        grid.add(Tinsumo);
        
        fila2.add(grid);
    }
    
    private void llenarFila3() {
        TareaProductos = new JTable(this.modeloinsumos);
        JScrollPane scrollPane = new JScrollPane(TareaProductos);
        fila3.add(scrollPane, BorderLayout.CENTER);
    }
    
    private void llenarFila4() {
        Bagregar = new JButton("Agregar");
        Beliminar = new JButton("Eliminar");
        Bsalir = new JButton("Salir");
        
        Bagregar.addActionListener(this);
        Beliminar.addActionListener(this);
        Bsalir.addActionListener(this);
        
        fila4.add(Bagregar);
        fila4.add(Beliminar);
        fila4.add(Bsalir);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Bagregar) {
            JOptionPane.showMessageDialog(this, "Función Agregar - GridLayout");
        } else if (e.getSource() == Beliminar) {
            JOptionPane.showMessageDialog(this, "Función Eliminar - GridLayout");
        } else if (e.getSource() == Bsalir) {
            System.exit(0);
        }
    }
    
    public static void main(String[] args) {
        new Practica06_c();
    }
}