package Vista;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import Controlador.ControladorPrincipal;
import Controlador.ControladorTicket;
import Modelo.Usuario;
import Modelo.Venta;

public class VistaHistorial extends JPanel {
    private VistaPrincipal vistaPrincipal;
    private ControladorPrincipal controladorPrincipal;
    private Usuario usuarioActual;
    
    private JTable tablaVentas;
    private DefaultTableModel modeloTabla;
    private JComboBox<String> cmbFiltro;
    private JButton btnRefrescar;
    private JButton btnVerDetalle;
    private JButton btnCancelarVenta;
    private JButton btnReimprimirTicket;
    
    private ArrayList<Venta> listaVentas;
    
    public VistaHistorial(VistaPrincipal vista) {
        this.vistaPrincipal = vista;
        this.controladorPrincipal = new ControladorPrincipal();
        this.usuarioActual = vista.getUsuarioActual();
        
        setLayout(new BorderLayout());
        setBackground(new Color(240, 248, 255));
        
        listaVentas = new ArrayList<Venta>();
        
        iniciarComponentes();
        cargarVentas("TODAS");
    }
    
    private void iniciarComponentes() {
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.setBackground(new Color(240, 248, 255));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel lblFiltro = new JLabel("Filtrar por:");
        lblFiltro.setFont(new Font("Arial", Font.BOLD, 12));
        
        String[] opciones = {"TODAS", "HOY", "ESTA SEMANA", "ESTE MES"};
        cmbFiltro = new JComboBox<>(opciones);
        cmbFiltro.setFont(new Font("Arial", Font.PLAIN, 12));
        
        btnRefrescar = new JButton("Refrescar");
        btnRefrescar.setBackground(new Color(79, 70, 229));
        btnRefrescar.setForeground(Color.BLACK);
        btnRefrescar.setFont(new Font("Arial", Font.BOLD, 12));
        btnRefrescar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        panelSuperior.add(lblFiltro);
        panelSuperior.add(cmbFiltro);
        panelSuperior.add(btnRefrescar);
        
        add(panelSuperior, BorderLayout.NORTH);
        
        String[] columnas = {"ID", "Folio", "Fecha", "Hora", "Total", "Metodo Pago", "Vendedor", "Cancelada"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaVentas = new JTable(modeloTabla);
        tablaVentas.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaVentas.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaVentas.setRowHeight(30);
        tablaVentas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(tablaVentas);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelInferior.setBackground(new Color(240, 248, 255));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        btnVerDetalle = new JButton("Ver Detalle");
        btnVerDetalle.setBackground(new Color(59, 130, 246));
        btnVerDetalle.setForeground(Color.BLACK);
        btnVerDetalle.setFont(new Font("Arial", Font.BOLD, 12));
        btnVerDetalle.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnCancelarVenta = new JButton("Cancelar Venta");
        btnCancelarVenta.setBackground(new Color(220, 38, 38));
        btnCancelarVenta.setForeground(Color.BLACK);
        btnCancelarVenta.setFont(new Font("Arial", Font.BOLD, 12));
        btnCancelarVenta.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnReimprimirTicket = new JButton("Reimprimir Ticket");
        btnReimprimirTicket.setBackground(new Color(255, 159, 28));
        btnReimprimirTicket.setForeground(Color.BLACK);
        btnReimprimirTicket.setFont(new Font("Arial", Font.BOLD, 12));
        btnReimprimirTicket.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        panelInferior.add(btnVerDetalle);
        panelInferior.add(btnCancelarVenta);
        panelInferior.add(btnReimprimirTicket);
        
        add(panelInferior, BorderLayout.SOUTH);
        
        btnRefrescar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String filtro = (String) cmbFiltro.getSelectedItem();
                cargarVentas(filtro);
            }
        });
        
        btnVerDetalle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                verDetalleVenta();
            }
        });
        
        btnCancelarVenta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelarVenta();
            }
        });
        
        btnReimprimirTicket.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reimprimirTicket();
            }
        });
        
        tablaVentas.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    verDetalleVenta();
                }
            }
        });
    }
    
    private void cargarVentas(String filtro) {
        boolean esAdmin = usuarioActual.getRol().equals("Administrador");
        listaVentas = controladorPrincipal.listarVentas(filtro, usuarioActual.getId(), esAdmin);
        actualizarTabla(listaVentas);
    }
    
    private void actualizarTabla(ArrayList<Venta> ventas) {
        modeloTabla.setRowCount(0);
        SimpleDateFormat sdfFecha = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm:ss");
        
        if (ventas == null) return;
        
        for (int i = 0; i < ventas.size(); i++) {
            Venta v = ventas.get(i);
            String fechaStr = v.getFecha() != null ? sdfFecha.format(v.getFecha()) : "";
            String horaStr = v.getFecha() != null ? sdfHora.format(v.getFecha()) : "";
            String cancelada = v.isCancelada() ? "SI" : "NO";
            
            modeloTabla.addRow(new Object[]{
                v.getId(),
                v.getFolio(),
                fechaStr,
                horaStr,
                "$" + v.getTotal(),
                v.getMetodoPago(),
                v.getNombreUsuario(),
                cancelada
            });
        }
    }
    
    private void verDetalleVenta() {
        int fila = tablaVentas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una venta para ver detalle");
            return;
        }
        
        int idVenta = (int) modeloTabla.getValueAt(fila, 0);
        String folio = (String) modeloTabla.getValueAt(fila, 1);
        
        Venta venta = controladorPrincipal.obtenerVentaConDetalles(idVenta);
        
        if (venta != null && venta.getDetalles() != null) {
            String rutaTicket = "tickets/ticket_" + folio + ".pdf";
            File archivoTicket = new File(rutaTicket);
            
            if (!archivoTicket.exists()) {
                ControladorTicket controladorTicket = new ControladorTicket();
                controladorTicket.generarTicketPDF(venta, venta.getDetalles(), rutaTicket);
            }
            
            VistaTicket ticketVista = new VistaTicket(vistaPrincipal, venta, venta.getDetalles(), rutaTicket);
            ticketVista.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Error al cargar los detalles de la venta");
        }
    }
    
    private void cancelarVenta() {
        int fila = tablaVentas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una venta para cancelar");
            return;
        }
        
        int idVenta = (int) modeloTabla.getValueAt(fila, 0);
        String folio = (String) modeloTabla.getValueAt(fila, 1);
        String cancelada = (String) modeloTabla.getValueAt(fila, 7);
        
        if (cancelada.equals("SI")) {
            JOptionPane.showMessageDialog(this, "Esta venta ya fue cancelada");
            return;
        }
        
  
        boolean puedeCancelar = false;
        if (usuarioActual.getRol().equals("Administrador")) {
            puedeCancelar = true;
        } else {

            Venta venta = controladorPrincipal.obtenerVentaConDetalles(idVenta);
            if (venta != null && venta.getIdUsuario() == usuarioActual.getId()) {
                puedeCancelar = true;
            }
        }
        
        if (!puedeCancelar) {
            JOptionPane.showMessageDialog(this, "No tiene permiso para cancelar esta venta");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "¿Cancelar la venta " + folio + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean cancelado = controladorPrincipal.cancelarVenta(idVenta);
            if (cancelado) {
                JOptionPane.showMessageDialog(this, "Venta cancelada correctamente");
                String filtro = (String) cmbFiltro.getSelectedItem();
                cargarVentas(filtro);
            } else {
                JOptionPane.showMessageDialog(this, "Error al cancelar la venta");
            }
        }
    }
    
    private void reimprimirTicket() {
        int fila = tablaVentas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una venta para reimprimir ticket");
            return;
        }
        
        int idVenta = (int) modeloTabla.getValueAt(fila, 0);
        String folio = (String) modeloTabla.getValueAt(fila, 1);
        String cancelada = (String) modeloTabla.getValueAt(fila, 7);
        
        if (cancelada.equals("SI")) {
            JOptionPane.showMessageDialog(this, "No se puede reimprimir ticket de una venta cancelada");
            return;
        }
        
        Venta venta = controladorPrincipal.obtenerVentaConDetalles(idVenta);
        
        if (venta != null && venta.getDetalles() != null) {
            String rutaTicket = "tickets/ticket_reimpreso_" + folio + ".pdf";
            
            ControladorTicket controladorTicket = new ControladorTicket();
            boolean ticketGenerado = controladorTicket.generarTicketPDF(venta, venta.getDetalles(), rutaTicket);
            
            if (ticketGenerado) {
                VistaTicket ticketVista = new VistaTicket(vistaPrincipal, venta, venta.getDetalles(), rutaTicket);
                ticketVista.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Error al reimprimir el ticket");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Error al obtener los detalles de la venta");
        }
    }
    
    public void refrescar() {
        String filtro = (String) cmbFiltro.getSelectedItem();
        cargarVentas(filtro);
    }
}