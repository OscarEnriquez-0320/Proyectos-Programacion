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
 * ACTIVIDAD 2 - Página 29
 * Versión InternalFrame de la Actividad 1 (Categorías)
 * Para ser usado desde Practica05
 */
public class InternalFrameCategorias extends JInternalFrame implements ActionListener {
    
    private ListaCategorias listaCategorias;
    private Archivotxt archivoCategorias;
    private Librerias libreria;
    
    // Referencia al padre para manejar menú
    private JFrame padre;
    private JMenuBar barraMenu;
    
    // Componentes
    private JList<Categoria> ListaCategoria;
    private JTextField Tid, Tnombre;
    private JButton Bagregar, Beliminar, Bsalir;
    private JTable TablaCategorias;
    
    // Modelos
    private DefaultListModel<Categoria> modelocategoria;
    private DefaultTableModel modeloTabla;
    
    public InternalFrameCategorias(JFrame padre) {
        // Configuración del InternalFrame
        setTitle("Administración de Categorías");
        setBounds(50, 50, 700, 500);
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        setResizable(true);
        setLayout(null);
        
        // Guardar referencia al padre y deshabilitar menú
        this.padre = padre;
        this.libreria = new Librerias();
        if (padre != null) {
            this.barraMenu = padre.getJMenuBar();
            if (this.barraMenu != null) {
                libreria.menuspadre(barraMenu, false);
            }
        }
        
        // Inicializar datos
        inicializarDatos();
        
        // ===================================================
        // COMPONENTES (igual que Actividad 1)
        // ===================================================
        
        // JList de categorías
        JLabel lblLista = new JLabel("Lista de Categorías:");
        lblLista.setBounds(30, 10, 150, 25);
        add(lblLista);
        
        JScrollPane scrollLista = new JScrollPane();
        scrollLista.setBounds(30, 40, 150, 200);
        ListaCategoria = new JList<>();
        ListaCategoria.setModel(modelocategoria);
        scrollLista.setViewportView(ListaCategoria);
        add(scrollLista);
        
        // Formulario
        JLabel lblId = new JLabel("ID Categoría:");
        lblId.setBounds(220, 40, 100, 25);
        add(lblId);
        
        Tid = new JTextField();
        Tid.setBounds(320, 40, 150, 25);
        Tid.setEditable(false);
        add(Tid);
        
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(220, 80, 100, 25);
        add(lblNombre);
        
        Tnombre = new JTextField();
        Tnombre.setBounds(320, 80, 150, 25);
        Tnombre.setEditable(false);
        add(Tnombre);
        
        // Tabla de categorías
        JLabel lblTabla = new JLabel("Categorías registradas:");
        lblTabla.setBounds(30, 250, 200, 25);
        add(lblTabla);
        
        JScrollPane scrollTabla = new JScrollPane();
        scrollTabla.setBounds(30, 280, 620, 120);
        TablaCategorias = new JTable();
        TablaCategorias.setRowHeight(25);
        scrollTabla.setViewportView(TablaCategorias);
        add(scrollTabla);
        
        // Listener para selección en tabla
        TablaCategorias.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = TablaCategorias.getSelectedRow();
                if (fila >= 0) {
                    String id = (String) TablaCategorias.getValueAt(fila, 0);
                    String nombre = (String) TablaCategorias.getValueAt(fila, 1);
                    Tid.setText(id);
                    Tnombre.setText(nombre);
                    
                    // Seleccionar en JList
                    for (int i = 0; i < modelocategoria.size(); i++) {
                        if (modelocategoria.get(i).getIdCategoria().equals(id)) {
                            ListaCategoria.setSelectedIndex(i);
                            break;
                        }
                    }
                }
            }
        });
        
        // Botones
        Bagregar = new JButton("Agregar");
        Bagregar.setBounds(180, 420, 100, 30);
        Bagregar.addActionListener(this);
        add(Bagregar);
        
        Beliminar = new JButton("Eliminar");
        Beliminar.setBounds(300, 420, 100, 30);
        Beliminar.addActionListener(this);
        add(Beliminar);
        
        Bsalir = new JButton("Cerrar");
        Bsalir.setBounds(420, 420, 100, 30);
        Bsalir.addActionListener(this);
        add(Bsalir);
        
        // Actualizar tabla
        actualizarTabla();
        
        // Listener para cuando se cierra el InternalFrame
        addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
            @Override
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent e) {
                // Habilitar menú del padre al cerrar
                if (barraMenu != null) {
                    libreria.menuspadre(barraMenu, true);
                }
            }
        });
        
        setVisible(true);
    }
    
    private void inicializarDatos() {
        archivoCategorias = new Archivotxt("Categoria");
        listaCategorias = new ListaCategorias();
        
        if (archivoCategorias.existe()) {
            listaCategorias.cargarCategorias(archivoCategorias.cargar());
        } else {
            listaCategorias.agregarCategoria(new Categoria("CAT01", "Electrónica"));
            listaCategorias.agregarCategoria(new Categoria("CAT02", "Ropa"));
            listaCategorias.agregarCategoria(new Categoria("CAT03", "Hogar"));
            archivoCategorias.guardar(listaCategorias.toArchivo());
        }
        
        modelocategoria = listaCategorias.generarModeloCategorias();
    }
    
    private void actualizarTabla() {
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        
        for (Categoria cat : listaCategorias.getCategorias()) {
            modeloTabla.addRow(new Object[]{cat.getIdCategoria(), cat.getNombreCategoria()});
        }
        
        TablaCategorias.setModel(modeloTabla);
    }
    
    private void limpiarCampos() {
        Tid.setText("");
        Tnombre.setText("");
        ListaCategoria.clearSelection();
        TablaCategorias.clearSelection();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Bagregar) {
            String id = JOptionPane.showInputDialog(this, "Ingrese ID de categoría:");
            if (id != null && !id.trim().isEmpty()) {
                if (listaCategorias.buscarCategoriaPorId(id.trim()) != null) {
                    JOptionPane.showMessageDialog(this, "Error: El ID ya existe");
                    return;
                }
                
                String nombre = JOptionPane.showInputDialog(this, "Ingrese nombre:");
                if (nombre != null && !nombre.trim().isEmpty()) {
                    Categoria nueva = new Categoria(id.trim(), nombre.trim());
                    listaCategorias.agregarCategoria(nueva);
                    archivoCategorias.guardar(listaCategorias.toArchivo());
                    
                    modelocategoria = listaCategorias.generarModeloCategorias();
                    ListaCategoria.setModel(modelocategoria);
                    actualizarTabla();
                    
                    JOptionPane.showMessageDialog(this, "Categoría agregada");
                }
            }
            
        } else if (e.getSource() == Beliminar) {
            if (listaCategorias.size() == 0) {
                JOptionPane.showMessageDialog(this, "No hay categorías");
                return;
            }
            
            Object[] ids = listaCategorias.getIdCategorias();
            String id = (String) JOptionPane.showInputDialog(
                this, "Seleccione ID a eliminar:", "Eliminar",
                JOptionPane.QUESTION_MESSAGE, null, ids, ids[0]);
            
            if (id != null) {
                if (listaCategorias.eliminarCategoria(id)) {
                    archivoCategorias.guardar(listaCategorias.toArchivo());
                    modelocategoria = listaCategorias.generarModeloCategorias();
                    ListaCategoria.setModel(modelocategoria);
                    actualizarTabla();
                    limpiarCampos();
                    JOptionPane.showMessageDialog(this, "Categoría eliminada");
                }
            }
            
        } else if (e.getSource() == Bsalir) {
            // Habilitar menú y cerrar
            if (barraMenu != null) {
                libreria.menuspadre(barraMenu, true);
            }
            this.dispose();
        }
    }
}