package Parte1;

import Libreria.Archivotxt;
import Modelo.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Práctica 01_b - Versión con guardado en archivo
 * Agrega persistencia para insumos
 */
public class Practica01_b extends JFrame implements ActionListener {
    
    // Objetos para manejo de datos
    private ListaInsumos listainsumo;
    private ListaCategorias listacategorias;
    
    // Objetos archivo (ahora dos archivos: categorías e insumos)
    private Archivotxt archivocategorias;
    private Archivotxt archivoinsumos;
    
    // Componentes de la interfaz
    private JList<Categoria> ListaCategoria;
    private JTextField Tid, Tinsumo;
    private JButton Bagger, Beliminar, Bsalir;
    private JTextArea areaProduct;
    private DefaultListModel<Categoria> modelocategoria;
    private JPanel panelFormulario;
    
    public Practica01_b() {
        // Configuración básica del JFrame
        setTitle("Administración de Productos - Práctica 01_b (con archivo)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 550);
        setLayout(null);
        
        // Inicializar datos
        inicializarCategorias();
        
        // Crear panel principal
        panelFormulario = new JPanel();
        panelFormulario.setLayout(null);
        panelFormulario.setBounds(0, 0, 584, 511);
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
        
        // JList dentro de JScrollPane
        JScrollPane scrollPane_jlist = new JScrollPane();
        scrollPane_jlist.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane_jlist.setBounds(40, 30, 180, 200);
        panelFormulario.add(scrollPane_jlist);
        
        ListaCategoria = new JList<>();
        scrollPane_jlist.setViewportView(ListaCategoria);
        ListaCategoria.setModel(this.modelocategoria);
        ListaCategoria.setEnabled(false);
        
        // Área de texto para mostrar productos
        JScrollPane scrollArea = new JScrollPane();
        scrollArea.setBounds(40, 250, 500, 150);
        panelFormulario.add(scrollArea);
        
        areaProduct = new JTextArea();
        areaProduct.setEditable(false);
        scrollArea.setViewportView(areaProduct);
        
        // Botones
        Bagger = new JButton("Agregar");
        Bagger.setBounds(150, 420, 100, 30);
        panelFormulario.add(Bagger);
        Bagger.addActionListener(this);
        
        Beliminar = new JButton("Eliminar");
        Beliminar.setBounds(270, 420, 100, 30);
        panelFormulario.add(Beliminar);
        Beliminar.addActionListener(this);
        
        Bsalir = new JButton("Salir");
        Bsalir.setBounds(390, 420, 100, 30);
        panelFormulario.add(Bsalir);
        Bsalir.addActionListener(this);
        
        // Mostrar datos si existen
        if (this.listainsumo.size() > 0) {
            this.areaProduct.setText(this.listainsumo.toString());
        }
        
        setVisible(true);
    }
    
    /**
     * Inicializa categorías e insumos desde los archivos
     */
    public void inicializarCategorias() {
        // Inicializar archivos
        this.archivocategorias = new Archivotxt("Categoria");
        this.archivoinsumos = new Archivotxt("Insumos");
        
        // Inicializar listas
        this.listacategorias = new ListaCategorias();
        this.listainsumo = new ListaInsumos();
        
        // Cargar categorías si existe el archivo
        if (this.archivocategorias.existe()) {
            this.listacategorias.cargarCategorias(this.archivocategorias.cargar());
        } else {
            // Crear categorías por defecto
            listacategorias.agregarCategoria(new Categoria("CAT01", "Electrónica"));
            listacategorias.agregarCategoria(new Categoria("CAT02", "Ropa"));
            listacategorias.agregarCategoria(new Categoria("CAT03", "Hogar"));
            archivocategorias.guardar(listacategorias.toArchivo());
        }
        
        // Cargar insumos si existe el archivo
        if (this.archivoinsumos.existe()) {
            this.listainsumo.cargarInsumo(this.archivoinsumos.cargar());
        }
        
        // Generar modelo para JList
        modelocategoria = this.listacategorias.generarModeloCategorias();
    }
    
    /**
     * Verifica que todos los datos del formulario estén completos
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
     * Restablece el formulario al estado inicial
     */
    public void Volveralinicio() {
        this.Bagger.setText("Agregar");
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
     * Maneja la operación de alta/modificación
     */
    public void Altas() {
        if (this.Bagger.getText().compareTo("Agregar") == 0) {
            // Modo inserción
            this.ListaCategoria.setSelectedIndex(0);
            this.Bagger.setText("Guardar");
            this.Bsalir.setText("Cancelar");
            this.Beliminar.setEnabled(false);
            this.Tid.setEditable(true);
            this.Tinsumo.setEditable(true);
            this.ListaCategoria.setEnabled(true);
            this.ListaCategoria.setFocusable(true);
        } else {
            // Modo guardar
            if (esdatoscompletos()) {
                String id = this.Tid.getText().trim();
                String insumo = this.Tinsumo.getText().trim();
                String idcategoria = this.modelocategoria.get(this.ListaCategoria.getSelectedIndex()).getIdCategoria();
                
                Insumo nodo = new Insumo(id, insumo, idcategoria);
                
                if (!this.listainsumo.agregarInsumo(nodo)) {
                    String mensaje = "Lo siento, el ID " + id + " ya existe. Asignado a: " + 
                                     this.listainsumo.buscarInsumo(id).getProducto();
                    JOptionPane.showMessageDialog(this, mensaje);
                } else {
                    // Guardar en archivo después de agregar
                    this.archivoinsumos.guardar(this.listainsumo.toArchivo());
                    this.areaProduct.setText(this.listainsumo.toString());
                    JOptionPane.showMessageDialog(this, "Producto agregado correctamente");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Debe completar todos los campos");
            }
            this.Volveralinicio();
        }
    }
    
    /**
     * Maneja la operación de eliminación
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
                // Guardar en archivo después de eliminar
                this.archivoinsumos.guardar(this.listainsumo.toArchivo());
                this.areaProduct.setText(this.listainsumo.toString());
                JOptionPane.showMessageDialog(this, "Producto eliminado correctamente");
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.Bagger) {
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
            new Practica01_b();
        });
    }
}