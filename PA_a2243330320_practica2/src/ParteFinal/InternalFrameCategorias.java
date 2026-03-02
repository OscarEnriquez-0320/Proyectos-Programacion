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
 * InternalFrame de Categorías con JDialogs
 */
public class InternalFrameCategorias extends JInternalFrame implements ActionListener {
    
    private ListaCategorias listaCategorias;
    private Archivotxt archivoCategorias;
    private Librerias libreria;
    
    private JFrame padre;
    private JMenuBar barraMenu;
    
    private JTable TablaCategorias;
    private JButton Bagregar, Bmodificar, Beliminar, Bsalir;
    private DefaultTableModel modeloTabla;
    
    public InternalFrameCategorias(JFrame padre) {
        setTitle("Categorías");
        setBounds(50, 50, 600, 400);
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
        scrollTabla.setBounds(20, 20, 540, 250);
        TablaCategorias = new JTable();
        TablaCategorias.setRowHeight(25);
        scrollTabla.setViewportView(TablaCategorias);
        add(scrollTabla);
        
        // Botones
        Bagregar = new JButton("Agregar");
        Bagregar.setBounds(120, 290, 100, 30);
        Bagregar.addActionListener(this);
        add(Bagregar);
        
        Bmodificar = new JButton("Modificar");
        Bmodificar.setBounds(230, 290, 100, 30);
        Bmodificar.addActionListener(this);
        add(Bmodificar);
        
        Beliminar = new JButton("Eliminar");
        Beliminar.setBounds(340, 290, 100, 30);
        Beliminar.addActionListener(this);
        add(Beliminar);
        
        Bsalir = new JButton("Cerrar");
        Bsalir.setBounds(450, 290, 100, 30);
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
        archivoCategorias = new Archivotxt("Categoria");
        listaCategorias = new ListaCategorias();
        
        if (archivoCategorias.existe()) {
            listaCategorias.cargarCategorias(archivoCategorias.cargar());
        }
    }
    
    private void actualizarTabla() {
        modeloTabla = listaCategorias.getModeloTabla();
        TablaCategorias.setModel(modeloTabla);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Bagregar) {
            // Usar JDialog para agregar
            DialogoCategoria dialog = new DialogoCategoria(
                (JFrame) padre, 
                "Agregar Categoría", 
                null, 
                true
            );
            
            if (dialog.mostrar()) {
                Categoria nueva = dialog.getCategoria();
                if (listaCategorias.agregarCategoria(nueva)) {
                    archivoCategorias.guardar(listaCategorias.toArchivo());
                    actualizarTabla();
                    JOptionPane.showMessageDialog(this, "Categoría agregada");
                } else {
                    JOptionPane.showMessageDialog(this, "Error: ID ya existe");
                }
            }
            
        } else if (e.getSource() == Bmodificar) {
            int fila = TablaCategorias.getSelectedRow();
            if (fila < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione una categoría");
                return;
            }
            
            String id = (String) TablaCategorias.getValueAt(fila, 0);
            String nombre = (String) TablaCategorias.getValueAt(fila, 1);
            Categoria cat = new Categoria(id, nombre);
            
            DialogoCategoria dialog = new DialogoCategoria(
                (JFrame) padre,
                "Modificar Categoría",
                cat,
                false
            );
            
            if (dialog.mostrar()) {
                archivoCategorias.guardar(listaCategorias.toArchivo());
                actualizarTabla();
                JOptionPane.showMessageDialog(this, "Categoría modificada");
            }
            
        } else if (e.getSource() == Beliminar) {
            int fila = TablaCategorias.getSelectedRow();
            if (fila < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione una categoría");
                return;
            }
            
            String id = (String) TablaCategorias.getValueAt(fila, 0);
            
            // JOptionPane para eliminar
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "¿Eliminar categoría " + id + "?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                listaCategorias.eliminarCategoria(id);
                archivoCategorias.guardar(listaCategorias.toArchivo());
                actualizarTabla();
                JOptionPane.showMessageDialog(this, "Categoría eliminada");
            }
            
        } else if (e.getSource() == Bsalir) {
            if (barraMenu != null) {
                libreria.menuspadre(barraMenu, true);
            }
            this.dispose();
        }
    }
}