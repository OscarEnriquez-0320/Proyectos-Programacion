package principal.java.Controlador;

import principal.java.Modelo.*;
import principal.java.datos.*;
import principal.java.Vista.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PuntoVentaControlador implements ActionListener {
    private VistaPuntoVenta vista;
    private GestionInventario modeloInventario;
    private GestionProducto modeloProductos;
    private GestionVentas modeloVentas;
    
    private ArrayList<Venta> carritoActual;
    private String ticketActual;
    private String fechaActual;
    private double subtotal, iva, total;

    public PuntoVentaControlador(VistaPuntoVenta vista, GestionInventario modeloInventario, 
                                GestionProducto modeloProductos) {
        this.vista = vista;
        this.modeloInventario = modeloInventario;
        this.modeloProductos = modeloProductos;
        
        this.modeloVentas = new GestionVentas();
        
        this.carritoActual = new ArrayList<>();
        this.fechaActual = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        this.ticketActual = generarFolio();
        
        this.vista.getBtnAnadirCarrito().addActionListener(this);
        this.vista.getBtnModificarCarrito().addActionListener(this);
        this.vista.getBtnEliminarCarrito().addActionListener(this);
        this.vista.getBtnLimpiarCarrito().addActionListener(this);
        this.vista.getBtnProcesarPago().addActionListener(this);
        this.vista.getBtnExportarTicket().addActionListener(this);
        
        cargarProductosDisponibles();
        actualizarTablaCarrito();
    }

    private String generarFolio() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnAnadirCarrito()) {
            agregarAlCarrito();
        } else if (e.getSource() == vista.getBtnModificarCarrito()) {
            modificarCarrito();
        } else if (e.getSource() == vista.getBtnEliminarCarrito()) {
            eliminarDelCarrito();
        } else if (e.getSource() == vista.getBtnLimpiarCarrito()) {
            limpiarCarrito();
        } else if (e.getSource() == vista.getBtnProcesarPago()) {
            procesarPago();
        } else if (e.getSource() == vista.getBtnExportarTicket()) {
            exportarTicket();
        }
    }

    private void cargarProductosDisponibles() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("Seleccione un producto...");
        
        for (Inventario inv : modeloInventario.getDisponibles()) {
            model.addElement(inv.getIdProducto() + " - " + inv.getNombreProducto() + 
                           " ($" + inv.getPrecio() + ")");
        }
        
        vista.getComboProductos().setModel(model);
    }

    private void agregarAlCarrito() {
        if (!validarCamposVenta()) return;
        
        int index = vista.getComboProductos().getSelectedIndex();
        if (index <= 0) {
            JOptionPane.showMessageDialog(vista, "Seleccione un producto", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String cantidadStr = vista.getTxtCantidad().getText().trim();
        if (cantidadStr.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Ingrese una cantidad", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int cantidad = Integer.parseInt(cantidadStr);
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(vista, "La cantidad debe ser mayor a 0", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String itemSeleccionado = vista.getComboProductos().getSelectedItem().toString();
            String idProducto = itemSeleccionado.split(" - ")[0];
            
            Inventario inv = modeloInventario.buscar(idProducto);
            if (inv == null) {
                JOptionPane.showMessageDialog(vista, "Producto no encontrado", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (inv.getCantidadActual() < cantidad) {
                JOptionPane.showMessageDialog(vista, 
                    "Stock insuficiente. Disponible: " + inv.getCantidadActual(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean existe = false;
            for (Venta v : carritoActual) {
                if (v.getIdProducto().equals(idProducto)) {
                    int nuevaCantidad = v.getCantidad() + cantidad;
                    if (inv.getCantidadActual() < nuevaCantidad) {
                        JOptionPane.showMessageDialog(vista, 
                            "Stock insuficiente. Disponible: " + inv.getCantidadActual(), 
                            "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    v.setCantidad(nuevaCantidad);
                    existe = true;
                    break;
                }
            }

            if (!existe) {
                Venta nuevaVenta = new Venta(
                    ticketActual, idProducto, inv.getNombreProducto(),
                    inv.getPrecio(), cantidad, fechaActual,
                    vista.getTxtCajero().getText().trim()
                );
                carritoActual.add(nuevaVenta);
            }

            actualizarTablaCarrito();
            vista.getTxtCantidad().setText("1");
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vista, "Ingrese un número válido", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarCarrito() {
        int fila = vista.getTableCarrito().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un producto del carrito", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nuevaCantidadStr = JOptionPane.showInputDialog(vista, 
            "Ingrese la nueva cantidad:", 
            JOptionPane.QUESTION_MESSAGE);

        if (nuevaCantidadStr != null && !nuevaCantidadStr.trim().isEmpty()) {
            try {
                int nuevaCantidad = Integer.parseInt(nuevaCantidadStr);
                if (nuevaCantidad <= 0) {
                    JOptionPane.showMessageDialog(vista, "La cantidad debe ser mayor a 0", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Venta v = carritoActual.get(fila);
                Inventario inv = modeloInventario.buscar(v.getIdProducto());
                
                if (inv.getCantidadActual() < nuevaCantidad) {
                    JOptionPane.showMessageDialog(vista, 
                        "Stock insuficiente. Disponible: " + inv.getCantidadActual(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                v.setCantidad(nuevaCantidad);
                actualizarTablaCarrito();
                
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(vista, "Ingrese un número válido", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarDelCarrito() {
        int fila = vista.getTableCarrito().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un producto del carrito", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(vista, 
            "¿Eliminar producto del carrito?", 
            "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            carritoActual.remove(fila);
            actualizarTablaCarrito();
        }
    }

    private void limpiarCarrito() {
        if (carritoActual.isEmpty()) {
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(vista, 
            "¿Limpiar todo el carrito?", 
            "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            carritoActual.clear();
            actualizarTablaCarrito();
        }
    }

    private boolean validarCamposVenta() {
        if (vista.getTxtCajero().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Ingrese el nombre del cajero", 
                "Campo requerido", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void procesarPago() {
        if (carritoActual.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "El carrito está vacío", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (vista.getTxtCajero().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Ingrese el nombre del cajero", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(vista, 
            "Total a pagar: " + String.format("$%.2f", total) + "\n¿Procesar pago?", 
            "Confirmar Venta", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            for (Venta v : carritoActual) {
                modeloInventario.quitarStock(v.getIdProducto(), v.getCantidad());
            }

            ArchivoVentaJSON.guardarTicket(carritoActual, ticketActual);
            
            mostrarTicket();
            
            ticketActual = generarFolio();
            carritoActual.clear();
            actualizarTablaCarrito();
            vista.getTxtIdCliente().setText("");
            vista.getTxtNombreCliente().setText("");
            vista.getTxtCajero().setText("");
            
            cargarProductosDisponibles();
        }
    }

    private void exportarTicket() {
        if (carritoActual.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "No hay ticket para exportar", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        mostrarTicket();
    }

    private void actualizarTablaCarrito() {
        DefaultTableModel model = (DefaultTableModel) vista.getTableCarrito().getModel();
        model.setRowCount(0);
        
        subtotal = 0;
        for (Venta v : carritoActual) {
            model.addRow(v.toArray());
            subtotal += v.getSubtotal();
        }
        
        iva = subtotal * 0.16;
        total = subtotal + iva;
        
        vista.getLblSubtotal().setText(String.format("$%.2f", subtotal));
        vista.getLblIVA().setText(String.format("$%.2f", iva));
        vista.getLblTotal().setText(String.format("$%.2f", total));
    }

    private void mostrarTicket() {
        StringBuilder ticket = new StringBuilder();
        ticket.append("====================================\n");
        ticket.append("         TICKET DE VENTA\n");
        ticket.append("====================================\n");
        ticket.append("Ticket No: ").append(ticketActual).append("\n");
        ticket.append("Fecha: ").append(fechaActual).append("\n");
        ticket.append("Cajero: ").append(vista.getTxtCajero().getText()).append("\n");
        ticket.append("Cliente: ").append(vista.getTxtNombreCliente().getText()).append("\n");
        ticket.append("------------------------------------\n");
        ticket.append("CANT  DESCRIPCIÓN           TOTAL\n");
        ticket.append("------------------------------------\n");
        
        for (Venta v : carritoActual) {
            String desc = v.getNombreProducto().length() > 20 ? 
                v.getNombreProducto().substring(0, 17) + "..." : 
                v.getNombreProducto();
            ticket.append(String.format("%-4d %-20s $%7.2f\n", 
                v.getCantidad(), desc, v.getSubtotal()));
        }
        
        ticket.append("------------------------------------\n");
        ticket.append(String.format("Subtotal:                $%7.2f\n", subtotal));
        ticket.append(String.format("IVA (16%%):               $%7.2f\n", iva));
        ticket.append(String.format("TOTAL:                   $%7.2f\n", total));
        ticket.append("====================================\n");
        ticket.append("¡Gracias por su compra!\n");
        
        JTextArea textArea = new JTextArea(ticket.toString());
        textArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(450, 550));
        
        JOptionPane.showMessageDialog(vista, scrollPane, "Ticket de Venta", 
            JOptionPane.INFORMATION_MESSAGE);
    }
}