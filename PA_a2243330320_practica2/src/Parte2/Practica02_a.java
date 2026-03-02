package Parte2;

import Libreria.Archivotxt;
import Modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Práctica 02_a - Versión con JTable
 * Reemplaza JTextArea por JTable para mostrar productos
 */
public class Practica02_a extends JFrame implements ActionListener {
    
    // Objetos para manejo de datos
    private ListaInsumos listainsumo;
    private ListaCategorias listacategorias;
    
    // Objetos archivo
    private Archivotxt archivocategorias;
    private Archivotxt archivoinsumos;
    
    // Componentes de la interfaz
    private JList<Categoria> ListaCategoria;
    private JTextField Tid, Tinsumo;
    private JButton Bagregar, Beliminar, Bsalir;
    private JPanel panelFormulario;
    private JTable TareaProductos;  // Cambio importante: de JTextArea a JTable
    private DefaultListModel<Categoria> modelocategoria;
    private DefaultTableModel modeloinsumos;  // Modelo para la tabla
    
    public Practica02_a() {
        // Configuración básica
        setTitle("Administración de Productos - Práctica 02_a (JTable)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 600);
        setLayout(null);
        
        // Inicializar datos
        inicializarCategorias();
        
        // Crear panel principal
        panelFormulario = new JPanel();
        panelFormulario.setLayout(null);
        panelFormulario.setBounds(0, 0, 684, 561);
        setContentPane(panelFormulario);
        
        // Etiquetas y campos
        JLabel lblId = new JLabel("ID Producto:");
        lblId.setBounds(260, 30, 80, 25);
        panelFormulario.add(lblId);
        
        Tid = new JTextField();
        Tid.setBounds(350, 30, 150, 25);
        Tid.setEditable(false);
        panelFormulario.add(Tid);
        
        JLabel lblInsumo = new JLabel("Producto:");
        lblInsumo.setBounds(260, 70, 80, 25);
        panelFormulario.add(lblInsumo);
        
        Tinsumo = new JTextField();
        Tinsumo.setBounds(350, 70, 150, 25);
        Tinsumo.setEditable(false);
        panelFormulario.add(Tinsumo);
        
        JLabel lblCategoria = new JLabel("Categoría:");
        lblCategoria.setBounds(260, 110, 80, 25);
        panelFormulario.add(lblCategoria);
        
        // JList de categorías
        JScrollPane scrollPane_jlist = new JScrollPane();
        scrollPane_jlist.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane_jlist.setBounds(40, 30, 180, 200);
        panelFormulario.add(scrollPane_jlist);
        
        ListaCategoria = new JList<>();
        scrollPane_jlist.setViewportView(ListaCategoria);
        ListaCategoria.setModel(this.modelocategoria);
        ListaCategoria.setEnabled(false);
        
        // JTable para productos
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(40, 250, 600, 200);
        panelFormulario.add(scrollPane);
        
        TareaProductos = new JTable();
        TareaProductos.setRowHeight(25);
        scrollPane.setViewportView(TareaProductos);
        
        // Botones
        Bagregar = new JButton("Agregar");
        Bagregar.setBounds(150, 470, 100, 30);
        panelFormulario.add(Bagregar);
        Bagregar.addActionListener(this);
        
        Beliminar = new JButton("Eliminar");
        Beliminar.setBounds(270, 470, 100, 30);
        panelFormulario.add(Beliminar);
        Beliminar.addActionListener(this);
        
        Bsalir = new JButton("Salir");
        Bsalir.setBounds(390, 470, 100, 30);
        panelFormulario.add(Bsalir);
        Bsalir.addActionListener(this);
        
        // Actualizar tabla con datos existentes
        this.actualizartabla();
        
        setVisible(true);
    }
    
    /**
     * Inicializa categorías e insumos desde archivos
     */
    public void inicializarCategorias() {
        this.archivocategorias = new Archivotxt("Categoria");
        this.archivoinsumos = new Archivotxt("Insumos");
        
        this.listacategorias = new ListaCategorias();
        this.listainsumo = new ListaInsumos();
        
        // Cargar categorías
        if (this.archivocategorias.existe()) {
            this.listacategorias.cargarCategorias(this.archivocategorias.cargar());
        } else {
            // Categorías por defecto
            listacategorias.agregarCategoria(new Categoria("CAT01", "Electrónica"));
            listacategorias.agregarCategoria(new Categoria("CAT02", "Ropa"));
            listacategorias.agregarCategoria(new Categoria("CAT03", "Hogar"));
            archivocategorias.guardar(listacategorias.toArchivo());
        }
        
        // Cargar insumos
        if (this.archivoinsumos.existe()) {
            this.listainsumo.cargarInsumo(this.archivoinsumos.cargar());
        }
        
        // Modelo para JList
        modelocategoria = this.listacategorias.generarModeloCategorias();
        
        // Modelo para JTable
        this.modeloinsumos = new DefaultTableModel();
        this.modeloinsumos = this.listainsumo.getmodelo(this.listacategorias);
    }
    
    /**
     * Actualiza la tabla con los datos actuales
     */
    public void actualizartabla() {
        this.modeloinsumos = this.listainsumo.getmodelo(this.listacategorias);
        this.TareaProductos.setModel(this.modeloinsumos);
        
        // Ajustar ancho de columnas
        this.TareaProductos.getColumnModel().getColumn(0).setPreferredWidth(50);
        this.TareaProductos.getColumnModel().getColumn(1).setPreferredWidth(300);
        this.TareaProductos.getColumnModel().getColumn(2).setPreferredWidth(150);
    }
    
    /**
     * Verifica datos completos
     */
    public Boolean esdatoscompletos() {
        String id = this.Tid.getText().trim();
        String insumo = this.Tinsumo.getText().trim();
        
        if (this.ListaCategoria.getSelectedIndex() >= 0) {
            if (!id.isEmpty() && !insumo.isEmpty()) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Restablece formulario
     */
    public void Volveralinicio() {
        this.Bagregar.setText("Agregar");
        this.Bsalir.setText("Salir");
        this.Beliminar.setEnabled(true);
        this.Tid.setEditable(false);
        this.Tinsumo.setEditable(false);
        this.ListaCategoria.setEnabled(false);
        this.Tid.setText("");
        this.Tinsumo.setText("");
        this.ListaCategoria.setSelectedIndex(0);
    }
    
    /**
     * Maneja altas
     */
    public void Altas() {
        if (this.Bagregar.getText().compareTo("Agregar") == 0) {
            this.ListaCategoria.setSelectedIndex(0);
            this.Bagregar.setText("Guardar");
            this.Bsalir.setText("Cancelar");
            this.Beliminar.setEnabled(false);
            this.Tid.setEditable(true);
            this.Tinsumo.setEditable(true);
            this.ListaCategoria.setEnabled(true);
            this.ListaCategoria.setFocusable(true);
        } else {
            if (esdatoscompletos()) {
                String id = this.Tid.getText().trim();
                String insumo = this.Tinsumo.getText().trim();
                String idcategoria = this.modelocategoria.get(this.ListaCategoria.getSelectedIndex()).getIdCategoria();
                
                Insumo nodo = new Insumo(id, insumo, idcategoria);
                
                if (!this.listainsumo.agregarInsumo(nodo)) {
                    String mensaje = "Lo siento, el ID " + id + " ya existe.";
                    JOptionPane.showMessageDialog(this, mensaje);
                } else {
                    // Guardar y actualizar
                    this.archivoinsumos.guardar(this.listainsumo.toArchivo());
                    this.actualizartabla();
                    JOptionPane.showMessageDialog(this, "Producto agregado correctamente");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Debe completar todos los campos");
            }
            this.Volveralinicio();
        }
    }
    
    /**
     * Maneja eliminación
     */
    public void Eliminar() {
        if (listainsumo.size() == 0) {
            JOptionPane.showMessageDialog(this, "No hay productos para eliminar");
            return;
        }
        
        Object[] opciones = this.listainsumo.idinsumos();
        String id = (String) JOptionPane.showInputDialog(
            null, 
            "Seleccione el ID a eliminar:", 
            "Eliminar Producto", 
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            opciones, 
            opciones[0]
        );
        
        if (id != null && !id.isEmpty()) {
            if (!this.listainsumo.eliminarInsumoPorId(id)) {
                JOptionPane.showMessageDialog(this, "No existe este ID");
            } else {
                this.archivoinsumos.guardar(this.listainsumo.toArchivo());
                this.actualizartabla();
                JOptionPane.showMessageDialog(this, "Producto eliminado correctamente");
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.Bagregar) {
            this.Altas();
        } else if (e.getSource() == this.Beliminar) {
            this.Eliminar();
        } else if (e.getSource() == this.Bsalir) {
            if (this.Bsalir.getText().compareTo("Cancelar") == 0) {
                this.Volveralinicio();
            } else {
                int respuesta = JOptionPane.showConfirmDialog(
                    this, 
                    "¿Está seguro de salir?", 
                    "Confirmar salida", 
                    JOptionPane.YES_NO_OPTION
                );
                if (respuesta == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Practica02_a();
        });
    }
}