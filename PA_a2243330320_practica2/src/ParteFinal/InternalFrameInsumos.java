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
 * InternalFrame de Insumos con JDialogs
 */
public class InternalFrameInsumos extends JInternalFrame implements ActionListener {
    
    private ListaInsumos listaInsumos;
    private ListaCategorias listaCategorias;
    private Archivotxt archivoInsumos;
    private Archivotxt archivoCategorias;
    private Librerias libreria;
    
    private JFrame padre;
    private JMenuBar barraMenu;
    
    private JTable TablaInsumos;
    private JButton Bagregar, Bmodificar, Beliminar, Bsalir;
    private DefaultTableModel modeloTabla;
    
    public InternalFrameInsumos(JFrame padre) {
        setTitle("Insumos");
        setBounds(50, 50, 700, 450);
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
        scrollTabla.setBounds(20, 20, 640, 300);
        TablaInsumos = new JTable();
        TablaInsumos.setRowHeight(25);
        scrollTabla.setViewportView(TablaInsumos);
        add(scrollTabla);
        
        // Botones
        Bagregar = new JButton("Agregar");
        Bagregar.setBounds(150, 340, 100, 30);
        Bagregar.addActionListener(this);
        add(Bagregar);
        
        Bmodificar = new JButton("Modificar");
        Bmodificar.setBounds(260, 340, 100, 30);
        Bmodificar.addActionListener(this);
        add(Bmodificar);
        
        Beliminar = new JButton("Eliminar");
        Beliminar.setBounds(370, 340, 100, 30);
        Beliminar.addActionListener(this);
        add(Beliminar);
        
        Bsalir = new JButton("Cerrar");
        Bsalir.setBounds(480, 340, 100, 30);
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
        archivoInsumos = new Archivotxt("Insumos");
        
        listaCategorias = new ListaCategorias();
        listaInsumos = new ListaInsumos();
        
        if (archivoCategorias.existe()) {
            listaCategorias.cargarCategorias(archivoCategorias.cargar());
        }
        if (archivoInsumos.existe()) {
            listaInsumos.cargarInsumo(archivoInsumos.cargar());
        }
    }
    
    private void actualizarTabla() {
        modeloTabla = listaInsumos.getmodelo(listaCategorias);
        TablaInsumos.setModel(modeloTabla);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Bagregar) {
            DialogoInsumo dialog = new DialogoInsumo(
                (JFrame) padre,
                "Agregar Insumo",
                null,
                true,
                listaCategorias
            );
            
            if (dialog.mostrar()) {
                Insumo nuevo = dialog.getInsumo();
                if (listaInsumos.agregarInsumo(nuevo)) {
                    archivoInsumos.guardar(listaInsumos.toArchivo());
                    actualizarTabla();
                    JOptionPane.showMessageDialog(this, "Insumo agregado");
                } else {
                    JOptionPane.showMessageDialog(this, "Error: ID ya existe");
                }
            }
            
        } else if (e.getSource() == Bmodificar) {
            int fila = TablaInsumos.getSelectedRow();
            if (fila < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione un insumo");
                return;
            }
            
            String id = (String) TablaInsumos.getValueAt(fila, 0);
            String nombre = (String) TablaInsumos.getValueAt(fila, 1);
            String idCat = "";
            
            // Buscar la categoría (esto es un poco chapuza, pero funciona)
            for (Insumo ins : listaInsumos.getInsumos()) {
                if (ins.getIdProducto().equals(id)) {
                    idCat = ins.getIdCategoria();
                    break;
                }
            }
            
            Insumo ins = new Insumo(id, nombre, idCat);
            
            DialogoInsumo dialog = new DialogoInsumo(
                (JFrame) padre,
                "Modificar Insumo",
                ins,
                false,
                listaCategorias
            );
            
            if (dialog.mostrar()) {
                archivoInsumos.guardar(listaInsumos.toArchivo());
                actualizarTabla();
                JOptionPane.showMessageDialog(this, "Insumo modificado");
            }
            
        } else if (e.getSource() == Beliminar) {
            int fila = TablaInsumos.getSelectedRow();
            if (fila < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione un insumo");
                return;
            }
            
            String id = (String) TablaInsumos.getValueAt(fila, 0);
            
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "¿Eliminar insumo " + id + "?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                listaInsumos.eliminarInsumoPorId(id);
                archivoInsumos.guardar(listaInsumos.toArchivo());
                actualizarTabla();
                JOptionPane.showMessageDialog(this, "Insumo eliminado");
            }
            
        } else if (e.getSource() == Bsalir) {
            if (barraMenu != null) {
                libreria.menuspadre(barraMenu, true);
            }
            this.dispose();
        }
    }
}