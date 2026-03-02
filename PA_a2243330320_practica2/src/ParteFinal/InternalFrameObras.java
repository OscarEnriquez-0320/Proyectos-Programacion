package ParteFinal;

import Libreria.Archivotxt;
import Libreria.Librerias;
import Modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * InternalFrame de Obras con JDialogs
 */
public class InternalFrameObras extends JInternalFrame implements ActionListener {
    
    private ListaObras listaObras;
    private Archivotxt archivoObras;
    private Librerias libreria;
    
    private JFrame padre;
    private JMenuBar barraMenu;
    
    private JTable TablaObras;
    private JButton Bagregar, Bmodificar, Beliminar, Bsalir;
    private DefaultTableModel modeloTabla;
    
    public InternalFrameObras(JFrame padre) {
        setTitle("Obras");
        setBounds(50, 50, 650, 400);
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
        
        // Tabla
        JScrollPane scrollTabla = new JScrollPane();
        scrollTabla.setBounds(20, 20, 590, 250);
        TablaObras = new JTable();
        TablaObras.setRowHeight(25);
        scrollTabla.setViewportView(TablaObras);
        add(scrollTabla);
        
        // Botones
        Bagregar = new JButton("Agregar");
        Bagregar.setBounds(130, 290, 100, 30);
        Bagregar.addActionListener(this);
        add(Bagregar);
        
        Bmodificar = new JButton("Modificar");
        Bmodificar.setBounds(240, 290, 100, 30);
        Bmodificar.addActionListener(this);
        add(Bmodificar);
        
        Beliminar = new JButton("Eliminar");
        Beliminar.setBounds(350, 290, 100, 30);
        Beliminar.addActionListener(this);
        add(Beliminar);
        
        Bsalir = new JButton("Cerrar");
        Bsalir.setBounds(460, 290, 100, 30);
        Bsalir.addActionListener(this);
        add(Bsalir);
        
        actualizarTabla();
        
        addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
            @Override
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent e) {
                if (barraMenu != null) {
                    libreria.menuspadre(barraMenu, true);
                }
            }
        });
        
        setVisible(true);
    }
    
    private void inicializarDatos() {
        archivoObras = new Archivotxt("Obras");
        listaObras = new ListaObras();
        
        if (archivoObras.existe()) {
            listaObras.cargarObras(archivoObras.cargar());
        }
    }
    
    private void actualizarTabla() {
        modeloTabla = listaObras.getModeloTabla();
        TablaObras.setModel(modeloTabla);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Bagregar) {
            String idGenerado = listaObras.generarId();
            Obra nueva = new Obra(idGenerado, "", "");
            
            DialogoObra dialog = new DialogoObra(
                (JFrame) padre,
                "Agregar Obra",
                nueva,
                true
            );
            
            if (dialog.mostrar()) {
                Obra obra = dialog.getObra();
                obra.setIdObra(idGenerado); // Asegurar el ID generado
                
                if (listaObras.agregarObra(obra)) {
                    archivoObras.guardar(listaObras.toArchivo());
                    actualizarTabla();
                    JOptionPane.showMessageDialog(this, "Obra agregada con ID: " + idGenerado);
                }
            }
            
        } else if (e.getSource() == Bmodificar) {
            int fila = TablaObras.getSelectedRow();
            if (fila < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione una obra");
                return;
            }
            
            String id = (String) TablaObras.getValueAt(fila, 0);
            String nombre = (String) TablaObras.getValueAt(fila, 1);
            String desc = (String) TablaObras.getValueAt(fila, 2);
            Obra obra = new Obra(id, nombre, desc);
            
            DialogoObra dialog = new DialogoObra(
                (JFrame) padre,
                "Modificar Obra",
                obra,
                false
            );
            
            if (dialog.mostrar()) {
                archivoObras.guardar(listaObras.toArchivo());
                actualizarTabla();
                JOptionPane.showMessageDialog(this, "Obra modificada");
            }
            
        } else if (e.getSource() == Beliminar) {
            int fila = TablaObras.getSelectedRow();
            if (fila < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione una obra");
                return;
            }
            
            String id = (String) TablaObras.getValueAt(fila, 0);
            
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "¿Eliminar obra " + id + "?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                listaObras.eliminarObra(id);
                archivoObras.guardar(listaObras.toArchivo());
                actualizarTabla();
                JOptionPane.showMessageDialog(this, "Obra eliminada");
            }
            
        } else if (e.getSource() == Bsalir) {
            if (barraMenu != null) {
                libreria.menuspadre(barraMenu, true);
            }
            this.dispose();
        }
    }
}