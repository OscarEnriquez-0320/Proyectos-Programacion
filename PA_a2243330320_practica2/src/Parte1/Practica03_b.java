package Parte1;

import Libreria.Archivotxt;
import Modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Practica03_b extends JFrame implements ActionListener {
    
    
    private ListaCategorias listaCategorias;
    private Archivotxt archivoCategorias;
    
 
    private JList<Categoria> ListaCategoria;      
    private JTextField Tid, Tnombre;              
    private JButton Bagregar, Beliminar, Bsalir;
    private JTable TablaCategorias;                 
    
    
    private DefaultListModel<Categoria> modelocategoria;
    private DefaultTableModel modeloTabla;
    
    public Practica03_b() {
        
        setTitle("Administración de Categorías - Actividad 1");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 550);
        setLayout(null);
        
       
        inicializarDatos();
        
        
        JLabel lblLista = new JLabel("Lista de Categorías:");
        lblLista.setBounds(30, 10, 150, 25);
        add(lblLista);
        
        JScrollPane scrollLista = new JScrollPane();
        scrollLista.setBounds(30, 40, 150, 200);
        ListaCategoria = new JList<>();
        ListaCategoria.setModel(modelocategoria);
        scrollLista.setViewportView(ListaCategoria);
        add(scrollLista);
        
        
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
        
        
        JLabel lblTabla = new JLabel("Categorías registradas:");
        lblTabla.setBounds(30, 250, 200, 25);
        add(lblTabla);
        
        JScrollPane scrollTabla = new JScrollPane();
        scrollTabla.setBounds(30, 280, 620, 150);
        TablaCategorias = new JTable();
        TablaCategorias.setRowHeight(25);
        scrollTabla.setViewportView(TablaCategorias);
        add(scrollTabla);
        
        // Agregar listener para selección en tabla
        TablaCategorias.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = TablaCategorias.getSelectedRow();
                if (fila >= 0) {
                    // Mostrar en los campos y seleccionar en JList
                    String id = (String) TablaCategorias.getValueAt(fila, 0);
                    String nombre = (String) TablaCategorias.getValueAt(fila, 1);
                    Tid.setText(id);
                    Tnombre.setText(nombre);
                    
                    // Seleccionar en JList también
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
        Bagregar.setBounds(180, 450, 100, 30);
        Bagregar.addActionListener(this);
        add(Bagregar);
        
        Beliminar = new JButton("Eliminar");
        Beliminar.setBounds(300, 450, 100, 30);
        Beliminar.addActionListener(this);
        add(Beliminar);
        
        Bsalir = new JButton("Salir");
        Bsalir.setBounds(420, 450, 100, 30);
        Bsalir.addActionListener(this);
        add(Bsalir);
        
        // Cargar datos iniciales en la tabla
        actualizarTabla();
        
        setVisible(true);
    }
    
    /**
     * Inicializa datos desde el archivo Categoria.txt
     */
    private void inicializarDatos() {
        archivoCategorias = new Archivotxt("Categoria");
        listaCategorias = new ListaCategorias();
        
        if (archivoCategorias.existe()) {
            listaCategorias.cargarCategorias(archivoCategorias.cargar());
            System.out.println("Categorías cargadas: " + listaCategorias.size());
        } else {
            // Si no existe, crear algunas por defecto
            listaCategorias.agregarCategoria(new Categoria("CAT01", "Electrónica"));
            listaCategorias.agregarCategoria(new Categoria("CAT02", "Ropa"));
            listaCategorias.agregarCategoria(new Categoria("CAT03", "Hogar"));
            archivoCategorias.guardar(listaCategorias.toArchivo());
            System.out.println("Categorías por defecto creadas");
        }
        
        modelocategoria = listaCategorias.generarModeloCategorias();
    }
    
    /**
     * Actualiza la JTable con los datos actuales
     */
    private void actualizarTabla() {
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Tabla no editable
            }
        };
        
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        
        for (Categoria cat : listaCategorias.getCategorias()) {
            modeloTabla.addRow(new Object[]{cat.getIdCategoria(), cat.getNombreCategoria()});
        }
        
        TablaCategorias.setModel(modeloTabla);
        
        // Ajustar ancho de columnas
        TablaCategorias.getColumnModel().getColumn(0).setPreferredWidth(80);
        TablaCategorias.getColumnModel().getColumn(1).setPreferredWidth(200);
    }
    
    /**
     * Limpia los campos de texto
     */
    private void limpiarCampos() {
        Tid.setText("");
        Tnombre.setText("");
        ListaCategoria.clearSelection();
        TablaCategorias.clearSelection();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Bagregar) {
            // Agregar nueva categoría
            String id = JOptionPane.showInputDialog(this, "Ingrese ID de categoría:");
            if (id != null && !id.trim().isEmpty()) {
                // Verificar que no exista (requisito del PDF)
                if (listaCategorias.buscarCategoriaPorId(id.trim()) != null) {
                    JOptionPane.showMessageDialog(this, 
                        "Error: El ID " + id + " ya existe", 
                        "ID duplicado", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                String nombre = JOptionPane.showInputDialog(this, "Ingrese nombre de categoría:");
                if (nombre != null && !nombre.trim().isEmpty()) {
                    Categoria nueva = new Categoria(id.trim(), nombre.trim());
                    listaCategorias.agregarCategoria(nueva);
                    
                    // Guardar en archivo
                    archivoCategorias.guardar(listaCategorias.toArchivo());
                    
                    // Actualizar modelos
                    modelocategoria = listaCategorias.generarModeloCategorias();
                    ListaCategoria.setModel(modelocategoria);
                    actualizarTabla();
                    
                    JOptionPane.showMessageDialog(this, "Categoría agregada correctamente");
                }
            }
            
        } else if (e.getSource() == Beliminar) {
            // Eliminar categoría
            if (listaCategorias.size() == 0) {
                JOptionPane.showMessageDialog(this, "No hay categorías para eliminar");
                return;
            }
            
            Object[] ids = listaCategorias.getIdCategorias();
            String id = (String) JOptionPane.showInputDialog(
                this, 
                "Seleccione la categoría a eliminar:", 
                "Eliminar categoría",
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                ids, 
                ids[0]);
            
            if (id != null) {
                // Confirmar eliminación
                int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "¿Está seguro de eliminar la categoría " + id + "?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    if (listaCategorias.eliminarCategoria(id)) {
                        // Guardar en archivo
                        archivoCategorias.guardar(listaCategorias.toArchivo());
                        
                        // Actualizar modelos
                        modelocategoria = listaCategorias.generarModeloCategorias();
                        ListaCategoria.setModel(modelocategoria);
                        actualizarTabla();
                        limpiarCampos();
                        
                        JOptionPane.showMessageDialog(this, "Categoría eliminada correctamente");
                    }
                }
            }
            
        } else if (e.getSource() == Bsalir) {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de salir?",
                "Confirmar salida",
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }
    
    public static void main(String[] args) {
        // Ejecutar en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            new Practica03_b();
        });
    }
}