package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import Controlador.ControladorTicket;
import Controlador.ControladorVenta;
import Modelo.DetalleVenta;
import Modelo.Venta;

public class VistaPago extends JDialog {
    private VistaPrincipal vistaPrincipal;
    private VistaPuntoVenta vistaPuntoVenta;
    private ControladorVenta controladorVenta;
    private BigDecimal total;
    private List<ControladorVenta.ItemCarrito> carrito;
    private int idCorteAbierto;
    private int idUsuario;
    
    private JLabel lblTotal;
    private JTextField txtRecibido;
    private JLabel lblCambio;
    private JComboBox<String> cmbMetodoPago;
    private JButton btnConfirmar;
    private JLabel lblError;
    

    private JTextField txtDescuentoPorcentaje;
    private JLabel lblTotalConDescuento;
    private JLabel lblDescuentoAplicado;
    
    public VistaPago(JFrame padre, VistaPuntoVenta puntoVenta, BigDecimal total, 
                     List<ControladorVenta.ItemCarrito> carrito, int idCorte, int idUsuario) {
        super(padre, "Pago", true);
        this.vistaPrincipal = (VistaPrincipal) padre;
        this.vistaPuntoVenta = puntoVenta;
        this.controladorVenta = new ControladorVenta();
        this.total = total;
        this.carrito = carrito;
        this.idCorteAbierto = idCorte;
        this.idUsuario = idUsuario;
        
        setSize(480, 520);
        setLocationRelativeTo(padre);
        setResizable(false);
        getContentPane().setLayout(new BorderLayout());
        
        iniciarComponentes();
    }
    
    private void iniciarComponentes() {
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(79, 70, 229));
        panelTitulo.setPreferredSize(new Dimension(getWidth(), 60));
        
        JLabel lblTitulo = new JLabel("FINALIZAR COMPRA");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelTitulo.add(lblTitulo);
        
        getContentPane().add(panelTitulo, BorderLayout.NORTH);
        
        JPanel panelCentral = new JPanel();
        panelCentral.setBackground(new Color(240, 248, 255));
        panelCentral.setLayout(new GridBagLayout());
        
        lblTotal = new JLabel("Total a pagar: $" + total.toString());
        lblTotal.setFont(new Font("Arial", Font.BOLD, 20));
        lblTotal.setForeground(new Color(79, 70, 229));
        
        JLabel lblMetodo = new JLabel("Metodo de pago:");
        lblMetodo.setFont(new Font("Arial", Font.BOLD, 12));
        
        String[] metodos = {"Efectivo","Tarjeta"};
        cmbMetodoPago = new JComboBox<>(metodos);
        cmbMetodoPago.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JLabel lblDescuento = new JLabel("Descuento (%):");
        lblDescuento.setFont(new Font("Arial", Font.BOLD, 12));
        txtDescuentoPorcentaje = new JTextField(10);
        txtDescuentoPorcentaje.setText("0");
        txtDescuentoPorcentaje.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JLabel lblDescuentoAplicadoTxt = new JLabel("Descuento aplicado:");
        lblDescuentoAplicadoTxt.setFont(new Font("Arial", Font.BOLD, 12));
        lblDescuentoAplicado = new JLabel("$0.00");
        lblDescuentoAplicado.setFont(new Font("Arial", Font.BOLD, 12));
        lblDescuentoAplicado.setForeground(new Color(220, 38, 38));
        
        JLabel lblTotalConDesc = new JLabel("Total con descuento:");
        lblTotalConDesc.setFont(new Font("Arial", Font.BOLD, 12));
        lblTotalConDescuento = new JLabel("$" + total.toString());
        lblTotalConDescuento.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotalConDescuento.setForeground(new Color(34, 197, 94));
        
        JLabel lblRecibido = new JLabel("Cantidad recibida:");
        lblRecibido.setFont(new Font("Arial", Font.BOLD, 12));
        
        txtRecibido = new JTextField(15);
        txtRecibido.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JLabel lblCambioTxt = new JLabel("Cambio:");
        lblCambioTxt.setFont(new Font("Arial", Font.BOLD, 12));
        
        lblCambio = new JLabel("$0.00");
        lblCambio.setFont(new Font("Arial", Font.BOLD, 16));
        lblCambio.setForeground(new Color(34, 197, 94));
        
        btnConfirmar = new JButton("Confirmar Pago");
        btnConfirmar.setBackground(new Color(34, 197, 94));
        btnConfirmar.setForeground(Color.BLACK);
        btnConfirmar.setFont(new Font("Arial", Font.BOLD, 14));
        btnConfirmar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        lblError = new JLabel("");
        lblError.setForeground(Color.RED);
        lblError.setFont(new Font("Arial", Font.BOLD, 11));
        
        // Fila 0 - Total
        GridBagConstraints gbc0 = new GridBagConstraints();
        gbc0.insets = new Insets(20, 10, 10, 10);
        gbc0.gridx = 0;
        gbc0.gridy = 0;
        gbc0.gridwidth = 2;
        panelCentral.add(lblTotal, gbc0);
        

        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.insets = new Insets(15, 10, 5, 10);
        gbc1.gridx = 0;
        gbc1.gridy = 1;
        panelCentral.add(lblMetodo, gbc1);
        
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(15, 10, 5, 10);
        gbc2.gridx = 1;
        gbc2.gridy = 1;
        panelCentral.add(cmbMetodoPago, gbc2);

        GridBagConstraints gbcDescLabel = new GridBagConstraints();
        gbcDescLabel.insets = new Insets(10, 10, 5, 10);
        gbcDescLabel.gridx = 0;
        gbcDescLabel.gridy = 2;
        gbcDescLabel.anchor = GridBagConstraints.EAST;
        panelCentral.add(lblDescuento, gbcDescLabel);
        
        GridBagConstraints gbcDescField = new GridBagConstraints();
        gbcDescField.insets = new Insets(10, 10, 5, 10);
        gbcDescField.gridx = 1;
        gbcDescField.gridy = 2;
        gbcDescField.anchor = GridBagConstraints.WEST;
        panelCentral.add(txtDescuentoPorcentaje, gbcDescField);
        

        GridBagConstraints gbcDescAplicadoLabel = new GridBagConstraints();
        gbcDescAplicadoLabel.insets = new Insets(5, 10, 5, 10);
        gbcDescAplicadoLabel.gridx = 0;
        gbcDescAplicadoLabel.gridy = 3;
        gbcDescAplicadoLabel.anchor = GridBagConstraints.EAST;
        panelCentral.add(lblDescuentoAplicadoTxt, gbcDescAplicadoLabel);
        
        GridBagConstraints gbcDescAplicadoField = new GridBagConstraints();
        gbcDescAplicadoField.insets = new Insets(5, 10, 5, 10);
        gbcDescAplicadoField.gridx = 1;
        gbcDescAplicadoField.gridy = 3;
        gbcDescAplicadoField.anchor = GridBagConstraints.WEST;
        panelCentral.add(lblDescuentoAplicado, gbcDescAplicadoField);
        

        GridBagConstraints gbcTotalDescLabel = new GridBagConstraints();
        gbcTotalDescLabel.insets = new Insets(5, 10, 5, 10);
        gbcTotalDescLabel.gridx = 0;
        gbcTotalDescLabel.gridy = 4;
        gbcTotalDescLabel.anchor = GridBagConstraints.EAST;
        panelCentral.add(lblTotalConDesc, gbcTotalDescLabel);
        
        GridBagConstraints gbcTotalDescField = new GridBagConstraints();
        gbcTotalDescField.insets = new Insets(5, 10, 5, 10);
        gbcTotalDescField.gridx = 1;
        gbcTotalDescField.gridy = 4;
        gbcTotalDescField.anchor = GridBagConstraints.WEST;
        panelCentral.add(lblTotalConDescuento, gbcTotalDescField);
        

        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.insets = new Insets(10, 10, 5, 10);
        gbc3.gridx = 0;
        gbc3.gridy = 5;
        panelCentral.add(lblRecibido, gbc3);
        
        GridBagConstraints gbc4 = new GridBagConstraints();
        gbc4.insets = new Insets(10, 10, 5, 10);
        gbc4.gridx = 1;
        gbc4.gridy = 5;
        panelCentral.add(txtRecibido, gbc4);
        

        GridBagConstraints gbc5 = new GridBagConstraints();
        gbc5.insets = new Insets(5, 10, 10, 10);
        gbc5.gridx = 0;
        gbc5.gridy = 6;
        panelCentral.add(lblCambioTxt, gbc5);
        
        GridBagConstraints gbc6 = new GridBagConstraints();
        gbc6.insets = new Insets(5, 10, 10, 10);
        gbc6.gridx = 1;
        gbc6.gridy = 6;
        panelCentral.add(lblCambio, gbc6);
        

        GridBagConstraints gbc7 = new GridBagConstraints();
        gbc7.insets = new Insets(20, 10, 10, 10);
        gbc7.gridx = 0;
        gbc7.gridy = 7;
        gbc7.gridwidth = 2;
        panelCentral.add(btnConfirmar, gbc7);
        

        GridBagConstraints gbc8 = new GridBagConstraints();
        gbc8.insets = new Insets(5, 10, 20, 10);
        gbc8.gridx = 0;
        gbc8.gridy = 8;
        gbc8.gridwidth = 2;
        panelCentral.add(lblError, gbc8);
        
        getContentPane().add(panelCentral, BorderLayout.CENTER);
        

        txtDescuentoPorcentaje.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                calcularTotalConDescuento();
            }
        });
        

        cmbMetodoPago.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean esEfectivo = cmbMetodoPago.getSelectedItem().equals("Efectivo");
                txtRecibido.setEnabled(esEfectivo);
                if (!esEfectivo) {
                    txtRecibido.setText("");
                    lblCambio.setText("$0.00");
                }
            }
        });
        

        txtRecibido.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                calcularCambio();
            }
        });
        

        btnConfirmar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                confirmarPago();
            }
        });
    }
    
    private void calcularTotalConDescuento() {
        try {
            double porcentaje = Double.parseDouble(txtDescuentoPorcentaje.getText());
            if (porcentaje < 0) {
                lblError.setText("El porcentaje no puede ser negativo");
                lblTotalConDescuento.setText("$" + total.toString());
                lblDescuentoAplicado.setText("$0.00");
                return;
            }
            if (porcentaje > 100) {
                lblError.setText("El porcentaje no puede superar 100%");
                lblTotalConDescuento.setText("$0.00");
                BigDecimal descuentoMonto = total;
                lblDescuentoAplicado.setText("$" + descuentoMonto.toString());
                return;
            }
            
            BigDecimal descuentoMonto = total.multiply(BigDecimal.valueOf(porcentaje)).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
            BigDecimal totalConDesc = total.subtract(descuentoMonto);
            
            lblDescuentoAplicado.setText("$" + descuentoMonto.toString());
            lblTotalConDescuento.setText("$" + totalConDesc.toString());
            lblError.setText("");
            
        } catch (NumberFormatException ex) {
            lblTotalConDescuento.setText("$" + total.toString());
            lblDescuentoAplicado.setText("$0.00");
        }
    }
    
    private void calcularCambio() {
        try {
            BigDecimal totalConDesc = total;
            try {
                double porcentaje = Double.parseDouble(txtDescuentoPorcentaje.getText());
                if (porcentaje >= 0 && porcentaje <= 100) {
                    BigDecimal descuentoMonto = total.multiply(BigDecimal.valueOf(porcentaje)).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
                    totalConDesc = total.subtract(descuentoMonto);
                }
            } catch (NumberFormatException e) {
                totalConDesc = total;
            }
            
            double recibido = Double.parseDouble(txtRecibido.getText());
            double totalDouble = totalConDesc.doubleValue();
            if (recibido >= totalDouble) {
                double cambio = recibido - totalDouble;
                lblCambio.setText("$" + String.format("%.2f", cambio));
            } else {
                lblCambio.setText("$0.00");
            }
        } catch (NumberFormatException e) {
            lblCambio.setText("$0.00");
        }
    }
    
    private BigDecimal obtenerTotalConDescuento() {
        try {
            double porcentaje = Double.parseDouble(txtDescuentoPorcentaje.getText());
            if (porcentaje < 0) return total;
            if (porcentaje > 100) return BigDecimal.ZERO;
            BigDecimal descuentoMonto = total.multiply(BigDecimal.valueOf(porcentaje)).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
            return total.subtract(descuentoMonto);
        } catch (NumberFormatException e) {
            return total;
        }
    }
    
    private BigDecimal obtenerMontoDescuento() {
        try {
            double porcentaje = Double.parseDouble(txtDescuentoPorcentaje.getText());
            if (porcentaje < 0) return BigDecimal.ZERO;
            if (porcentaje > 100) return total;
            return total.multiply(BigDecimal.valueOf(porcentaje)).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }
    
    private void confirmarPago() {
        String metodoPago = (String) cmbMetodoPago.getSelectedItem();
        
        BigDecimal totalConDesc = obtenerTotalConDescuento();
        BigDecimal montoDescuento = obtenerMontoDescuento();
        
        BigDecimal recibido = BigDecimal.ZERO;
        BigDecimal cambio = BigDecimal.ZERO;
        
        if (metodoPago.equals("Efectivo")) {
            try {
                recibido = new BigDecimal(txtRecibido.getText());
                if (recibido.compareTo(totalConDesc) < 0) {
                    lblError.setText("Cantidad recibida insuficiente");
                    return;
                }
                cambio = recibido.subtract(totalConDesc);
            } catch (NumberFormatException e) {
                lblError.setText("Ingrese una cantidad valida");
                return;
            }
        }
        
        String folio = controladorVenta.generarFolio();
        
        Venta venta = new Venta();
        venta.setFolio(folio);
        venta.setFecha(new Date());
        venta.setTotal(totalConDesc);
        venta.setMetodoPago(metodoPago);
        venta.setIdUsuario(idUsuario);
        venta.setEfectivoRecibido(recibido);
        venta.setNombreUsuario(vistaPrincipal.getUsuarioActual().getNombreCompleto());
        venta.setCambio(cambio);
        venta.setCancelada(false);
        venta.setDescuento(montoDescuento);
        venta.setIdCorte(idCorteAbierto);
        
        List<DetalleVenta> detalles = new ArrayList<>();
        
        for (ControladorVenta.ItemCarrito item : carrito) {
            DetalleVenta detalle = new DetalleVenta();
            detalle.setIdProducto(item.getIdProducto());
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(item.getPrecio());
            detalle.setSubtotal(item.getSubtotal());
            detalle.setNombreProducto(item.getNombre()); 
            detalles.add(detalle);
        }
        
        boolean guardado = controladorVenta.guardarVenta(venta, detalles);
        
        if (guardado) {
            File carpetaTickets = new File("tickets");
            if (!carpetaTickets.exists()) {
                carpetaTickets.mkdirs();
            }
            
            String rutaTicket = "tickets/ticket_" + folio + ".pdf";
            
            ControladorTicket controladorTicket = new ControladorTicket();
            boolean ticketGenerado = controladorTicket.generarTicketPDF(venta, detalles, rutaTicket);
            
            if (ticketGenerado) {
                VistaTicket ticketVista = new VistaTicket(vistaPrincipal, venta, detalles, rutaTicket);
                ticketVista.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Venta realizada, pero error al generar ticket\nFolio: " + folio);
            }
            
            vistaPuntoVenta.finalizarVenta();
            dispose();
        } else {
            lblError.setText("Error al guardar la venta");
        }
    }
}