package Parte3;

import Libreria.Archivotxt;
import Libreria.Librerias;
import Modelo.ListaObras;
import Modelo.Obra;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class InternalFrameObras extends JInternalFrame implements ActionListener {
    
    private ListaObras listaObras;
    private Archivotxt archivoObras;
    private Librerias libreria;
    
    
    private JFrame padre;
    private JMenuBar barraMenu;
    
    
    private JTextField Tnombre, Tdescripcion;
    private JButton Bagregar, Beliminar, Bsalir;
    private JTable TablaObras;
    private DefaultTableModel modeloTabla;
    
    
    private JLabel lblIdGenerado;
    
    public InternalFrameObras(JFrame padre) {
        
        setTitle("Administración de Obras");
        setBounds(50, 50, 650, 450);
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        setResizable(true);
        setLayout(null);
        
      
        this.padre = padre;
        this.libreria = new Librerias();
        if (padre != null) {
            this.barraMenu = padre.getJMenuBar();
            if (this.barraMenu != null) {
                libreria.menuspadre(barraMenu, false);
            }
        }
        
       
        inicializarDatos();
        
        
        JLabel lblId = new JLabel("ID (autogenerado):");
        lblId.setBounds(30, 30, 120, 25);
        add(lblId);
        
        lblIdGenerado = new JLabel(listaObras.generarId());
        lblIdGenerado.setBounds(160, 30, 100, 25);
        lblIdGenerado.setForeground(Color.BLUE);
        add(lblIdGenerado);
        
        
        JLabel lblNombre = new JLabel("Nombre de la obra:");
        lblNombre.setBounds(30, 70, 120, 25);
        add(lblNombre);
        
        Tnombre = new JTextField();
        Tnombre.setBounds(160, 70, 300, 25);
        add(Tnombre);
        
        
        JLabel lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setBounds(30, 110, 120, 25);
        add(lblDescripcion);
        
        Tdescripcion = new JTextField();
        Tdescripcion.setBounds(160, 110, 300, 25);
        add(Tdescripcion);
        
        // Botones
        Bagregar = new JButton("Agregar Obra");
        Bagregar.setBounds(160, 150, 130, 30);
        Bagregar.addActionListener(this);
        add(Bagregar);
        
        Beliminar = new JButton("Eliminar");
        Beliminar.setBounds(300, 150, 100, 30);
        Beliminar.addActionListener(this);
        add(Beliminar);
        
        Bsalir = new JButton("Cerrar");
        Bsalir.setBounds(410, 150, 100, 30);
        Bsalir.addActionListener(this);
        add(Bsalir);
        
        // Tabla de obras
        JLabel lblTabla = new JLabel("Obras registradas:");
        lblTabla.setBounds(30, 200, 150, 25);
        add(lblTabla);
        
        JScrollPane scrollTabla = new JScrollPane();
        scrollTabla.setBounds(30, 230, 580, 150);
        TablaObras = new JTable();
        TablaObras.setRowHeight(25);
        scrollTabla.setViewportView(TablaObras);
        add(scrollTabla);
        
        // Listener para selección en tabla
        TablaObras.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = TablaObras.getSelectedRow();
                if (fila >= 0) {
                    String id = (String) TablaObras.getValueAt(fila, 0);
                    String nombre = (String) TablaObras.getValueAt(fila, 1);
                    String desc = (String) TablaObras.getValueAt(fila, 2);
                    
                    Tnombre.setText(nombre);
                    Tdescripcion.setText(desc);
                    lblIdGenerado.setText(id); // Mostrar el ID seleccionado
                }
            }
        });
        
        // Actualizar tabla
        actualizarTabla();
        
        setVisible(true);
    }
    
    /**
     * Inicializa datos desde Obras.txt
     */
    private void inicializarDatos() {
        archivoObras = new Archivotxt("Obras");
        listaObras = new ListaObras();
        
        if (archivoObras.existe()) {
            listaObras.cargarObras(archivoObras.cargar());
            System.out.println("Obras cargadas: " + listaObras.size());
        }
    }
    
    /**
     * Actualiza la tabla con los datos actuales
     */
    private void actualizarTabla() {
        modeloTabla = listaObras.getModeloTabla();
        TablaObras.setModel(modeloTabla);
        
        // Ajustar columnas
        TablaObras.getColumnModel().getColumn(0).setPreferredWidth(60);
        TablaObras.getColumnModel().getColumn(1).setPreferredWidth(200);
        TablaObras.getColumnModel().getColumn(2).setPreferredWidth(300);
    }
    
    /**
     * Limpia los campos y genera nuevo ID
     */
    private void limpiarCampos() {
        Tnombre.setText("");
        Tdescripcion.setText("");
        lblIdGenerado.setText(listaObras.generarId()); // Nuevo ID para la próxima
        TablaObras.clearSelection();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Bagregar) {
            // Validar campos
            String nombre = Tnombre.getText().trim();
            String descripcion = Tdescripcion.getText().trim();
            
            if (nombre.isEmpty() || descripcion.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Debe llenar todos los campos", 
                    "Campos incompletos", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Generar ID automático (requisito del PDF)
            String id = listaObras.generarId();
            
            // Crear y agregar obra
            Obra nueva = new Obra(id, nombre, descripcion);
            listaObras.agregarObra(nueva);
            
            // Guardar en archivo
            archivoObras.guardar(listaObras.toArchivo());
            
            // Actualizar tabla
            actualizarTabla();
            
            // Limpiar campos y generar nuevo ID
            limpiarCampos();
            
            JOptionPane.showMessageDialog(this, 
                "Obra agregada con ID: " + id, 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            
        } else if (e.getSource() == Beliminar) {
            // Eliminar obra
            if (listaObras.size() == 0) {
                JOptionPane.showMessageDialog(this, "No hay obras para eliminar");
                return;
            }
            
            Object[] ids = listaObras.getIdObras();
            String id = (String) JOptionPane.showInputDialog(
                this,
                "Seleccione la obra a eliminar:",
                "Eliminar obra",
                JOptionPane.QUESTION_MESSAGE,
                null,
                ids,
                ids[0]);
            
            if (id != null) {
                int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "¿Eliminar obra " + id + "?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    listaObras.eliminarObra(id);
                    archivoObras.guardar(listaObras.toArchivo());
                    actualizarTabla();
                    limpiarCampos();
                    JOptionPane.showMessageDialog(this, "Obra eliminada");
                }
            }
            
        } else if (e.getSource() == Bsalir) {
            // Habilitar menú del padre antes de cerrar
            if (barraMenu != null) {
                libreria.menuspadre(barraMenu, true);
            }
            this.dispose();
        }
    }
}