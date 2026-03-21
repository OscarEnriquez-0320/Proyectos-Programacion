package principal.java.Controlador;

import principal.java.Modelo.*;
import principal.java.Vista.VistaProductos;
import principal.java.datos.ArchivoProductoJSON;

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

        this.vista.getBtnGuardar().addActionListener(this);
        this.vista.getBtnBuscar().addActionListener(this);
        this.vista.getBtnLimpiar().addActionListener(this);
        this.vista.getBtnEliminar().addActionListener(this);
        this.vista.getBtnMostrarTodos().addActionListener(this);
        this.vista.getBtnSeleccionarImagen().addActionListener(e -> vista.seleccionarImagen());

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
        if (!validarCamposVacios()) return;
        if (!validarCamposNumericos()) return;

        try {
            String id = vista.getTxtId().getText().trim();
            boolean esNuevo = id.isEmpty();
            
            if (esNuevo) {
                id = generarId();
            }

            String tipo = vista.getComboCategoria().getSelectedItem().toString();
            ProductoAbstract p = crearProductoSegunTipo(tipo);
            p.setId(id);
            p.setNombre(vista.getTxtNombre().getText().trim());
            p.setDescripcion(vista.getTxtDescripcion().getText().trim());
            p.setCategoria(tipo);
            p.setPrecioCompra(Double.parseDouble(vista.getTxtPrecioCompra().getText()));
            p.setPrecioVenta(Double.parseDouble(vista.getTxtPrecioVenta().getText()));
            p.setStock(Integer.parseInt(vista.getTxtStockInicial().getText()));
            p.setStockMinimo(Integer.parseInt(vista.getTxtStockMinimo().getText()));
            p.setActivo(vista.getRdbtnActivo().isSelected());
            p.setRutaImagen(vista.getRutaImagen());

            boolean operacionExitosa;
            if (esNuevo) {
                operacionExitosa = modelo.insertar(p);
                if (operacionExitosa) {
                    JOptionPane.showMessageDialog(vista, "Producto guardado con ID: " + id);
                }
            } else {
                operacionExitosa = modelo.actualizar(p);
                if (operacionExitosa) {
                    JOptionPane.showMessageDialog(vista, "Producto actualizado correctamente");
                }
            }

            if (operacionExitosa) {
                actualizarTabla();
                ArchivoProductoJSON.exportarJSON(modelo.getLista());
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(vista, "Error: El ID ya existe o no se pudo procesar");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Error en campos numéricos: " + ex.getMessage());
        }
    }

    private ProductoAbstract crearProductoSegunTipo(String tipo) {
        switch (tipo) {
            case "Abarrotes": return new Abarrotes();
            case "Bebidas": return new Bebidas();
            case "Lácteos": return new Lacteos();
            case "Frutas y Verduras": return new FrutasVerduras();
            case "Carnes": return new Carnes();
            case "Salchichonería": return new Salchichoneria();
            case "Panadería": return new Panaderia();
            case "Limpieza": return new Limpieza();
            case "Cuidado Personal": return new CuidadoPersonal();
            case "Snacks": return new Snacks();
            case "Mascotas": return new Mascotas();
            default: return new Abarrotes();
        }
    }

    private void buscarProducto() {
        String id = JOptionPane.showInputDialog(vista, "Ingrese el ID del producto:");
        if (id != null && !id.trim().isEmpty()) {
            ProductoAbstract p = modelo.buscar(id.trim());
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
            ProductoAbstract p = modelo.buscar(id.trim());
            if (p == null) {
                JOptionPane.showMessageDialog(vista, "Producto no encontrado");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(vista,
                "¿Está seguro de eliminar el producto?\nID: " + p.getId() + "\nNombre: " + p.getNombre(),
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                if (modelo.eliminar(id.trim())) {
                    JOptionPane.showMessageDialog(vista, "Producto eliminado");
                    actualizarTabla();
                    ArchivoProductoJSON.exportarJSON(modelo.getLista());
                    limpiarFormulario();
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al eliminar el producto");
                }
            }
        }
    }

    private void mostrarEnFormulario(ProductoAbstract p) {
        vista.getTxtId().setText(p.getId());
        vista.getTxtNombre().setText(p.getNombre());
        vista.getTxtDescripcion().setText(p.getDescripcion());
        vista.getComboCategoria().setSelectedItem(p.getCategoria());
        vista.getTxtPrecioCompra().setText(String.valueOf(p.getPrecioCompra()));
        vista.getTxtPrecioVenta().setText(String.valueOf(p.getPrecioVenta()));
        vista.getTxtStockInicial().setText(String.valueOf(p.getStock()));
        vista.getTxtStockMinimo().setText(String.valueOf(p.getStockMinimo()));
        
        // Última línea, después de todos los campos
        vista.setRutaImagen(p.getRutaImagen());

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
        vista.setRutaImagen("");
    }

    private boolean validarCamposNumericos() {
        try {
            Double.parseDouble(vista.getTxtPrecioCompra().getText());
            Double.parseDouble(vista.getTxtPrecioVenta().getText());
            Integer.parseInt(vista.getTxtStockInicial().getText());
            Integer.parseInt(vista.getTxtStockMinimo().getText());
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
            JOptionPane.showMessageDialog(vista, "El nombre del producto no puede estar vacío");
            return false;
        }
        if (vista.getTxtDescripcion().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "La descripción no puede estar vacía");
            return false;
        }
        if (vista.getTxtPrecioCompra().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "El precio de compra no puede estar vacío");
            return false;
        }
        if (vista.getTxtPrecioVenta().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "El precio de venta no puede estar vacío");
            return false;
        }
        if (vista.getTxtStockInicial().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "El stock inicial no puede estar vacío");
            return false;
        }
        if (vista.getTxtStockMinimo().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "El stock mínimo no puede estar vacío");
            return false;
        }
        return true;
    }

    private void actualizarTabla() {
        DefaultTableModel model = (DefaultTableModel) vista.getTableProductos().getModel();
        model.setRowCount(0);
        
        for (ProductoAbstract p : modelo.getActivos()) {
            model.addRow(new Object[]{
                p.getId(),
                p.getNombre(),
                p.getDescripcion(),
                p.getCategoria(),
                p.getPrecioCompra(),
                p.getPrecioVenta(),
                p.getStock(),
                p.getStockMinimo(),
                p.isActivo() ? "Activo" : "Inactivo"
            });
        }
    }

    private String generarId() {
        int max = 0;
        for (ProductoAbstract p : modelo.getLista()) {
            try {
                int id = Integer.parseInt(p.getId());
                if (id > max) max = id;
            } catch (NumberFormatException e) {}
        }
        max++;
        return String.format("%03d", max);
    }
}