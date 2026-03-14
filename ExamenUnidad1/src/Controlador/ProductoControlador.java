package Controlador;

import Modelo.Producto;
import Modelo.GestionProducto;
import Vista.VistaProductos;
import datos.ArchivoProducto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductoControlador implements ActionListener {
    private VistaProductos vista;
    private GestionProducto modelo;

    public ProductoControlador(VistaProductos vista, GestionProducto modelo) {
        this.vista = vista;
        this.modelo = modelo;

        // Registrar listeners
        this.vista.getBtnGuardar().addActionListener(this);
        this.vista.getBtnBuscar().addActionListener(this);
        this.vista.getBtnLimpiar().addActionListener(this);
        this.vista.getBtnEliminar().addActionListener(this);
        this.vista.getBtnMostrarTodos().addActionListener(this);

        // Cargar datos iniciales
        actualizarTabla();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnGuardar()) {
            guardarProducto();
        } else if (e.getSource() == vista.getBtnBuscar()) {
            buscarProducto();
        } else if (e.getSource() == vista.getBtnLimpiar()) {
            limpiarFormulario();
        } else if (e.getSource() == vista.getBtnEliminar()) {
            eliminarProducto();
        } else if (e.getSource() == vista.getBtnMostrarTodos()) {
            actualizarTabla();
            limpiarFormulario();
        }
    }

    private void guardarProducto() {
        // Validar campos vacíos primero
        if (!validarCamposVacios()) return;
        
        // Validar campos numéricos
        if (!validarCamposNumericos()) return;
        
        try {
            String id = vista.getTxtId().getText().trim();
            boolean esNuevo = id.isEmpty();
            
            // Crear objeto con los datos del formulario
            Producto p = new Producto();
            
            if (esNuevo) {
                id = generarId();
                p.setId(id);
            } else {
                p.setId(id);
            }
            
            p.setNombre(vista.getTxtNombre().getText().trim());
            p.setDescripcion(vista.getTxtDescripcion().getText().trim());
            p.setCategoria(vista.getComboCategoria().getSelectedItem().toString());
            p.setPrecioCompra(Double.parseDouble(vista.getTxtPrecioCompra().getText()));
            p.setPrecioVenta(Double.parseDouble(vista.getTxtPrecioVenta().getText()));
            p.setStockInicial(Integer.parseInt(vista.getTxtStockInicial().getText()));
            p.setStockMinimo(Integer.parseInt(vista.getTxtStockMinimo().getText()));
            p.setActivo(vista.getRdbtnActivo().isSelected());
            
            boolean operacionExitosa;
            if (esNuevo) {
                // INSERTAR
                operacionExitosa = modelo.insertar(p);
                if (operacionExitosa) {
                    JOptionPane.showMessageDialog(vista, "Producto guardado con ID: " + id);
                } else {
                    JOptionPane.showMessageDialog(vista, "Error: El ID " + id + " ya existe");
                    return;
                }
            } else {
                // MODIFICAR
                operacionExitosa = modelo.actualizar(p);
                if (operacionExitosa) {
                    JOptionPane.showMessageDialog(vista, "Producto actualizado correctamente");
                } else {
                    JOptionPane.showMessageDialog(vista, "Error: No se encontró el producto con ID: " + id);
                    return;
                }
            }
            
            if (operacionExitosa) {
                actualizarTabla();
                ArchivoProducto.exportarCSV(modelo.getLista());
                limpiarFormulario();
            }
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, 
                "Error inesperado en campos numéricos: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarProducto() {
        String id = JOptionPane.showInputDialog(vista, "Ingrese el ID del producto a buscar:");
        if (id != null && !id.trim().isEmpty()) {
            Producto p = modelo.buscar(id.trim());
            if (p != null) {
                mostrarEnFormulario(p);
                JOptionPane.showMessageDialog(vista, "Producto encontrado. Puede editarlo y guardar.");
            } else {
                JOptionPane.showMessageDialog(vista, "Producto no encontrado");
            }
        }
    }

    private void eliminarProducto() {
        String id = JOptionPane.showInputDialog(vista, "Ingrese el ID del producto a eliminar:");
        if (id != null && !id.trim().isEmpty()) {
            Producto p = modelo.buscar(id.trim());
            if (p == null) {
                JOptionPane.showMessageDialog(vista, "Producto no encontrado");
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(vista, 
                "¿Está seguro de eliminar el producto?\n" +
                "ID: " + p.getId() + "\n" +
                "Nombre: " + p.getNombre(), 
                "Confirmar eliminación", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
            
            if (confirm == JOptionPane.YES_OPTION) {
                if (modelo.eliminar(id.trim())) {
                    JOptionPane.showMessageDialog(vista, "Producto eliminado");
                    actualizarTabla();
                    ArchivoProducto.exportarCSV(modelo.getLista());
                    limpiarFormulario();
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al eliminar el producto");
                }
            }
        }
    }

    private void mostrarEnFormulario(Producto p) {
        vista.getTxtId().setText(p.getId());
        vista.getTxtNombre().setText(p.getNombre());
        vista.getTxtDescripcion().setText(p.getDescripcion());
        vista.getComboCategoria().setSelectedItem(p.getCategoria());
        vista.getTxtPrecioCompra().setText(String.valueOf(p.getPrecioCompra()));
        vista.getTxtPrecioVenta().setText(String.valueOf(p.getPrecioVenta()));
        vista.getTxtStockInicial().setText(String.valueOf(p.getStockInicial()));
        vista.getTxtStockMinimo().setText(String.valueOf(p.getStockMinimo()));
        
        if (p.isActivo()) {
            vista.getRdbtnActivo().setSelected(true);
        } else {
            vista.getRdbtnDesactivado().setSelected(true);
        }
    }

    private void limpiarFormulario() {
        vista.getTxtId().setText("");
        vista.getTxtNombre().setText("");
        vista.getTxtDescripcion().setText("");
        vista.getComboCategoria().setSelectedIndex(0);
        vista.getTxtPrecioCompra().setText("");
        vista.getTxtPrecioVenta().setText("");
        vista.getTxtStockInicial().setText("");
        vista.getTxtStockMinimo().setText("");
        vista.getRdbtnActivo().setSelected(true);
    }
    
    private boolean validarCamposNumericos() {
        try {
            if (!vista.getTxtPrecioCompra().getText().trim().isEmpty()) {
                Double.parseDouble(vista.getTxtPrecioCompra().getText());
            }
            if (!vista.getTxtPrecioVenta().getText().trim().isEmpty()) {
                Double.parseDouble(vista.getTxtPrecioVenta().getText());
            }
            if (!vista.getTxtStockInicial().getText().trim().isEmpty()) {
                Integer.parseInt(vista.getTxtStockInicial().getText());
            }
            if (!vista.getTxtStockMinimo().getText().trim().isEmpty()) {
                Integer.parseInt(vista.getTxtStockMinimo().getText());
            }
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vista, 
                "Los campos numéricos solo deben contener números", 
                "Error de formato", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private boolean validarCamposVacios() {
        if (vista.getTxtNombre().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, 
                "El nombre del producto no puede estar vacío", 
                "Campo requerido", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (vista.getTxtDescripcion().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, 
                "La descripción no puede estar vacía", 
                "Campo requerido", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (vista.getTxtPrecioCompra().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, 
                "El precio de compra no puede estar vacío", 
                "Campo requerido", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (vista.getTxtPrecioVenta().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, 
                "El precio de venta no puede estar vacío", 
                "Campo requerido", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (vista.getTxtStockInicial().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, 
                "El stock inicial no puede estar vacío", 
                "Campo requerido", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (vista.getTxtStockMinimo().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, 
                "El stock mínimo no puede estar vacío", 
                "Campo requerido", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void actualizarTabla() {
        DefaultTableModel model = (DefaultTableModel) vista.getTableProductos().getModel();
        model.setRowCount(0);
        
        for (Producto p : modelo.getActivos()) {
            model.addRow(p.toArray());
        }
    }

    private String generarId() {
        int max = 0;
        for (Producto p : modelo.getLista()) {
            try {
                int id = Integer.parseInt(p.getId());
                if (id > max) max = id;
            } catch (NumberFormatException e) {
                // Ignorar IDs no numéricos
            }
        }
        max++;
        return String.format("%03d", max);
    }
}