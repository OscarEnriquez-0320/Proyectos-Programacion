package Parte3;

import Libreria.Archivotxt;
import Libreria.Librerias;
import Modelo.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Práctica 03_b - Versión como JInternalFrame
 * Para ser usado dentro de un JDesktopPane
 */
public class Practica03_b extends JInternalFrame implements ActionListener {
    
    // Objetos para manejo de datos
    private ListaInsumos listainsumo;
    private ListaCategorias listacategorias;
    
    // Objetos archivo
    private Archivotxt archivocategorias;
    private Archivotxt archivoinsumos;
    
    // Librerías utilitarias
    private Librerias libreria;
    
    // Referencia al padre y su menú
    private JFrame padre;
    private JMenuBar barra;
    
    // Componentes de la interfaz
    private JList<Categoria> ListaCategoria;
    private JTextField Tid, Tinsumo;
    private JButton Bagregar, Beliminar, Bsalir;
    private JPanel panelFormulario;
    private JTable TareaProductos;
    private JLabel Limagen;
    private DefaultListModel<Categoria> modelocategoria;
    private DefaultTableModel modeloinsumos;
    
    /**
     * Constructor que recibe el JFrame padre
     * @param Padre JFrame principal que contiene el menú
     */
    public Practica03_b(JFrame Padre) {
        // Configuración del JInternalFrame
        setTitle("Administración de Productos");
        setBounds(50, 50, 700, 550);
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        setResizable(true);
        setLayout(null);
        
        // Inicializar utilerías
        libreria = new Librerias();
        
        // Guardar referencia al padre y deshabilitar su menú
        this.padre = Padre;
        this.barra = this.padre.getJMenuBar();
        if (this.barra != null) {
            this.barra = libreria.menuspadre(this.barra, false);
        }
        
        // Inicializar datos
        inicializarCategorias();
        
        // Crear panel principal
        panelFormulario = new JPanel();
        panelFormulario.setLayout(null);
        panelFormulario.setBounds(0, 0, 684, 510);
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
        
        // JLabel para imagen
        Limagen = new JLabel("Imagen", SwingConstants.CENTER);
        Limagen.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        Limagen.setBounds(550, 30, 120, 120);
        Limagen.setOpaque(true);
        Limagen.setBackground(Color.WHITE);
        panelFormulario.add(Limagen);
        
        // JTable para productos
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(40, 250, 600, 160);
        panelFormulario.add(scrollPane);
        
        TareaProductos = new JTable();
        TareaProductos.setRowHeight(25);
        TareaProductos.setRowSelectionAllowed(true);
        scrollPane.setViewportView(TareaProductos);
        
        // Configurar selección de la tabla
        ListSelectionModel cellSelectionModel = this.TareaProductos.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                int fila = TareaProductos.getSelectedRow();
                if (fila >= 0) {
                    actualizarimagen();
                }
            }
        });
        
        // Botones
        Bagregar = new JButton("Agregar");
        Bagregar.setBounds(150, 430, 100, 30);
        panelFormulario.add(Bagregar);
        Bagregar.addActionListener(this);
        
        Beliminar = new JButton("Eliminar");
        Beliminar.setBounds(270, 430, 100, 30);
        panelFormulario.add(Beliminar);
        Beliminar.addActionListener(this);
        
        Bsalir = new JButton("Salir");
        Bsalir.setBounds(390, 430, 100, 30);
        panelFormulario.add(Bsalir);
        Bsalir.addActionListener(this);
        
        // Actualizar tabla y mostrar imagen
        this.actualizartabla();
        actualizarimagen();
        
        setVisible(true);
    }
    
    /**
     * Actualiza la imagen según el producto seleccionado
     */
    public void actualizarimagen() {
        int fila = TareaProductos.getSelectedRow();
        String Nombrearchivo;
        String DirectorioTrabajo = System.getProperty("user.dir");
        
        if (fila >= 0) {
            Nombrearchivo = ((String) TareaProductos.getValueAt(fila, 0)) + ".png";
        } else {
            Nombrearchivo = "000.png";
        }
        
        File fichero = new File(DirectorioTrabajo + "\\Imagenes\\" + Nombrearchivo);
        String archivo = fichero.getPath();
        
        Icon icono = libreria.EtiquetaImagen(archivo, this.Limagen.getWidth(), this.Limagen.getHeight());
        
        if (icono != null) {
            this.Limagen.setIcon(icono);
            this.Limagen.setText("");
        } else {
            this.Limagen.setIcon(null);
            this.Limagen.setText("Sin imagen");
        }
    }
    
    /**
     * Inicializa categorías e insumos
     */
    public void inicializarCategorias() {
        this.archivocategorias = new Archivotxt("Categoria");
        this.archivoinsumos = new Archivotxt("Insumos");
        
        this.listacategorias = new ListaCategorias();
        this.listainsumo = new ListaInsumos();
        
        if (this.archivocategorias.existe()) {
            this.listacategorias.cargarCategorias(this.archivocategorias.cargar());
        } else {
            listacategorias.agregarCategoria(new Categoria("CAT01", "Electrónica"));
            listacategorias.agregarCategoria(new Categoria("CAT02", "Ropa"));
            listacategorias.agregarCategoria(new Categoria("CAT03", "Hogar"));
            archivocategorias.guardar(listacategorias.toArchivo());
        }
        
        if (this.archivoinsumos.existe()) {
            this.listainsumo.cargarInsumo(this.archivoinsumos.cargar());
        }
        
        modelocategoria = this.listacategorias.generarModeloCategorias();
        this.modeloinsumos = new DefaultTableModel();
        this.modeloinsumos = this.listainsumo.getmodelo(this.listacategorias);
    }
    
    /**
     * Actualiza la tabla
     */
    public void actualizartabla() {
        this.modeloinsumos = this.listainsumo.getmodelo(this.listacategorias);
        this.TareaProductos.setModel(this.modeloinsumos);
        
        this.TareaProductos.getColumnModel().getColumn(0).setPreferredWidth(50);
        this.TareaProductos.getColumnModel().getColumn(1).setPreferredWidth(300);
        this.TareaProductos.getColumnModel().getColumn(2).setPreferredWidth(150);
    }
    
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
                    this.archivoinsumos.guardar(this.listainsumo.toArchivo());
                    this.actualizartabla();
                    actualizarimagen();
                    JOptionPane.showMessageDialog(this, "Producto agregado correctamente");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Debe completar todos los campos");
            }
            this.Volveralinicio();
        }
    }
    
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
                actualizarimagen();
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
                // Antes de cerrar, habilitar el menú del padre
                if (this.barra != null) {
                    this.barra = libreria.menuspadre(this.barra, true);
                }
                this.dispose(); // Cerrar el InternalFrame
            }
        }
    }
}