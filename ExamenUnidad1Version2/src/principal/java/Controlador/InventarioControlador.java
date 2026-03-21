package principal.java.Controlador;

import principal.java.Modelo.*;

import principal.java.datos.*;
import Vista.VistaInventario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InventarioControlador implements ActionListener {
    private VistaInventario vista;
    private GestionInventario modelo;
    private GestionProducto modeloProductos;

    public InventarioControlador(VistaInventario vista, GestionInventario modelo, GestionProducto modeloProductos) {
        this.vista = vista;
        this.modelo = modelo;
        this.modeloProductos = modeloProductos;

        this.vista.getBtnBuscar().addActionListener(this);
        this.vista.getBtnLimpiarFiltros().addActionListener(this);
        this.vista.getBtnCrear().addActionListener(this);
        this.vista.getBtnModificar().addActionListener(this);
        this.vista.getBtnEliminar().addActionListener(this);

        actualizarTabla();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnBuscar()) {
            buscarEnInventario();
        } else if (e.getSource() == vista.getBtnLimpiarFiltros()) {
            limpiarFiltros();
        } else if (e.getSource() == vista.getBtnCrear()) {
            crearNuevoProducto();
        } else if (e.getSource() == vista.getBtnModificar()) {
            agregarStock();
        } else if (e.getSource() == vista.getBtnEliminar()) {
            ajustarStock();
        }
    }

    private void actualizarTabla() {
        DefaultTableModel model = (DefaultTableModel) vista.getTableInventario().getModel();
        model.setRowCount(0);
        
        java.util.ArrayList<Inventario> lista = null;
        if (vista.getRdbtnDisponible().isSelected()) {
            lista = modelo.getDisponibles();
        } else if (vista.getRdbtnAgotado().isSelected()) {
            lista = modelo.getItemsConStockBajo();
        } else {
            lista = modelo.getLista();
        }
        
        for (Inventario inv : lista) {
            model.addRow(new Object[]{
                inv.getIdProducto(),
                inv.getNombreProducto(),
                inv.getTipo(),
                inv.getCantidadActual(),
                String.format("$%.2f", inv.getPrecio()),
                inv.getEstado()
            });
        }
    }

    private void buscarEnInventario() {
        String id = vista.getTxtId().getText().trim();
        String nombre = vista.getTxtNombre().getText().trim();
        String tipo = (String) vista.getComboTipo().getSelectedItem();
        
        DefaultTableModel model = (DefaultTableModel) vista.getTableInventario().getModel();
        model.setRowCount(0);
        
        for (Inventario inv : modelo.getLista()) {
            boolean coincide = true;
            
            if (!id.isEmpty() && !inv.getIdProducto().toLowerCase().contains(id.toLowerCase())) {
                coincide = false;
            }
            if (!nombre.isEmpty() && !inv.getNombreProducto().toLowerCase().contains(nombre.toLowerCase())) {
                coincide = false;
            }
            if (tipo != null && !tipo.isEmpty() && !inv.getTipo().equals(tipo)) {
                coincide = false;
            }
            
            if (coincide) {
                model.addRow(new Object[]{
                    inv.getIdProducto(),
                    inv.getNombreProducto(),
                    inv.getTipo(),
                    inv.getCantidadActual(),
                    String.format("$%.2f", inv.getPrecio()),
                    inv.getEstado()
                });
            }
        }
        
        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(vista, "No se encontraron productos con esos criterios", 
                "Búsqueda", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void limpiarFiltros() {
        vista.getTxtId().setText("");
        vista.getTxtNombre().setText("");
        vista.getComboTipo().setSelectedIndex(0);
        vista.getRdbtnTodos().setSelected(true);
        actualizarTabla();
    }

    private void crearNuevoProducto() {
        JOptionPane.showMessageDialog(vista, 
            "Para crear nuevos productos, use el módulo de Productos\n" +
            "Una vez creado, aparecerá automáticamente en el inventario", 
            "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void agregarStock() {
        int fila = vista.getTableInventario().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un producto de la tabla", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String idProducto = vista.getTableInventario().getValueAt(fila, 0).toString();
        String nombreProducto = vista.getTableInventario().getValueAt(fila, 1).toString();
        String stockActual = vista.getTableInventario().getValueAt(fila, 3).toString();
        
        String cantidadStr = JOptionPane.showInputDialog(vista, 
            "Producto: " + nombreProducto + "\nStock actual: " + stockActual + 
            "\nIngrese la cantidad a agregar:", 
            "Agregar Stock", JOptionPane.QUESTION_MESSAGE);
        
        if (cantidadStr != null && !cantidadStr.trim().isEmpty()) {
            try {
                int cantidad = Integer.parseInt(cantidadStr);
                if (cantidad <= 0) {
                    JOptionPane.showMessageDialog(vista, 
                        "La cantidad debe ser mayor a 0", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                Inventario inv = modelo.buscar(idProducto);
                if (inv != null) {
                    inv.setCantidadActual(inv.getCantidadActual() + cantidad);
                    ArchivoInventario.exportarCSV(modelo.getLista());
                    actualizarTabla();
                    JOptionPane.showMessageDialog(vista, 
                        "Stock agregado correctamente\nNuevo stock: " + (Integer.parseInt(stockActual) + cantidad), 
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(vista, 
                    "Ingrese un número válido", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void ajustarStock() {
        int fila = vista.getTableInventario().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un producto de la tabla", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String idProducto = vista.getTableInventario().getValueAt(fila, 0).toString();
        String nombreProducto = vista.getTableInventario().getValueAt(fila, 1).toString();
        String stockActual = vista.getTableInventario().getValueAt(fila, 3).toString();
        
        int confirm = JOptionPane.showConfirmDialog(vista, 
            "¿Está seguro de ajustar el stock de:\n" +
            "Producto: " + nombreProducto + "\n" +
            "Stock actual: " + stockActual, 
            "Confirmar ajuste", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        
        String cantidadStr = JOptionPane.showInputDialog(vista, 
            "Producto: " + nombreProducto + "\nStock actual: " + stockActual + 
            "\nIngrese la nueva cantidad:", 
            "Ajustar Stock", JOptionPane.QUESTION_MESSAGE);
        
        if (cantidadStr != null && !cantidadStr.trim().isEmpty()) {
            try {
                int nuevaCantidad = Integer.parseInt(cantidadStr);
                if (nuevaCantidad < 0) {
                    JOptionPane.showMessageDialog(vista, 
                        "La cantidad no puede ser negativa", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                Inventario inv = modelo.buscar(idProducto);
                if (inv != null) {
                    inv.setCantidadActual(nuevaCantidad);
                    ArchivoInventario.exportarCSV(modelo.getLista());
                    actualizarTabla();
                    JOptionPane.showMessageDialog(vista, 
                        "Stock ajustado correctamente\nNuevo stock: " + nuevaCantidad, 
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(vista, 
                    "Ingrese un número válido", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}