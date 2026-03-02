package Parte3;

import Libreria.Archivotxt;
import Libreria.Librerias;
import Modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Práctica 06_a - FlowLayout APLICADO a la práctica de productos
 * Toma los componentes de Practica02_a y los organiza con FlowLayout
 */
public class Practica06_a extends JFrame implements ActionListener {
    
    private ListaInsumos listainsumo;
    private ListaCategorias listacategorias;
    private Archivotxt archivocategorias;
    private Archivotxt archivoinsumos;
    private Librerias libreria;
    
    // Componentes
    private JList<Categoria> ListaCategoria;
    private JTextField Tid, Tinsumo;
    private JButton Bagregar, Beliminar, Bsalir;
    private JTable TareaProductos;
    private DefaultListModel<Categoria> modelocategoria;
    private DefaultTableModel modeloinsumos;
    
    // Paneles para organizar
    private JPanel panelCategorias;  // JList
    private JPanel panelFormulario;  // Campos de texto
    private JPanel panelBotones;      // Botones
    private JPanel panelTabla;        // JTable
    
    public Practica06_a() {
        setTitle("Práctica 06_a - FlowLayout");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        
        // Inicializar datos
        libreria = new Librerias();
        inicializarDatos();
        
        // Configurar LAYOUT PRINCIPAL como FlowLayout
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        // Crear paneles con FlowLayout interno
        crearPanelCategorias();
        crearPanelFormulario();
        crearPanelTabla();
        crearPanelBotones();
        
        // Agregar paneles al JFrame (FlowLayout los coloca en línea)
        add(panelCategorias);
        add(panelFormulario);
        add(panelTabla);
        add(panelBotones);
        
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
    
    private void crearPanelCategorias() {
        panelCategorias = new JPanel(new FlowLayout());
        panelCategorias.setBorder(BorderFactory.createTitledBorder("Categorías"));
        panelCategorias.setPreferredSize(new Dimension(200, 250));
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(180, 200));
        
        ListaCategoria = new JList<>();
        ListaCategoria.setModel(this.modelocategoria);
        scrollPane.setViewportView(ListaCategoria);
        
        panelCategorias.add(scrollPane);
    }
    
    private void crearPanelFormulario() {
        panelFormulario = new JPanel(new FlowLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Producto"));
        panelFormulario.setPreferredSize(new Dimension(300, 250));
        
        JPanel panelInterno = new JPanel(new GridLayout(3, 2, 5, 5));
        
        panelInterno.add(new JLabel("ID:"));
        Tid = new JTextField(15);
        panelInterno.add(Tid);
        
        panelInterno.add(new JLabel("Producto:"));
        Tinsumo = new JTextField(15);
        panelInterno.add(Tinsumo);
        
        panelFormulario.add(panelInterno);
    }
    
    private void crearPanelTabla() {
        panelTabla = new JPanel(new FlowLayout());
        panelTabla.setBorder(BorderFactory.createTitledBorder("Productos"));
        panelTabla.setPreferredSize(new Dimension(750, 200));
        
        TareaProductos = new JTable(this.modeloinsumos);
        JScrollPane scrollPane = new JScrollPane(TareaProductos);
        scrollPane.setPreferredSize(new Dimension(730, 150));
        
        panelTabla.add(scrollPane);
    }
    
    private void crearPanelBotones() {
        panelBotones = new JPanel(new FlowLayout());
        panelBotones.setPreferredSize(new Dimension(750, 50));
        
        Bagregar = new JButton("Agregar");
        Beliminar = new JButton("Eliminar");
        Bsalir = new JButton("Salir");
        
        Bagregar.addActionListener(this);
        Beliminar.addActionListener(this);
        Bsalir.addActionListener(this);
        
        panelBotones.add(Bagregar);
        panelBotones.add(Beliminar);
        panelBotones.add(Bsalir);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Bagregar) {
            JOptionPane.showMessageDialog(this, "Funcionalidad de Agregar");
        } else if (e.getSource() == Beliminar) {
            JOptionPane.showMessageDialog(this, "Funcionalidad de Eliminar");
        } else if (e.getSource() == Bsalir) {
            System.exit(0);
        }
    }
    
    public static void main(String[] args) {
        new Practica06_a();
    }
}