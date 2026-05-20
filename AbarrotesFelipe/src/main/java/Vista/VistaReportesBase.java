package Vista;

import javax.swing.*;
import javax.swing.table.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import Controlador.ControladorReporte;
import Modelo.Venta;
import java.util.ArrayList;


public class VistaReportesBase extends JPanel {
    private VistaPrincipal vistaPrincipal;
    private ControladorReporte controladorReporte;
    
    private JTabbedPane pestañas;
    

    private JDateChooser dateInicio;
    private JDateChooser dateFin;
    private JButton btnFiltrarVentas;
    private JButton btnExportarExcel;
    private JButton btnVerDetalle;
    private JTable tablaVentas;
    private DefaultTableModel modeloTablaVentas;
    private JLabel lblTotalVentas;
    

    private JTable tablaStockBajo;
    private DefaultTableModel modeloTablaStock;
    private JButton btnRefrescarStock;
    private JButton btnExportarStockExcel;
    
    public VistaReportesBase(VistaPrincipal vista) {
        this.vistaPrincipal = vista;
        this.controladorReporte = new ControladorReporte();
        
        setLayout(new BorderLayout());
        setBackground(new Color(240, 248, 255));
        
        iniciarComponentes();
        cargarReporteVentas();
        cargarReporteStockBajo();
    }
    
    private void iniciarComponentes() {
        pestañas = new JTabbedPane();
        pestañas.setFont(new Font("Arial", Font.BOLD, 14));
        
        pestañas.addTab("Reporte de Ventas", crearPanelReporteVentas());
        pestañas.addTab("Productos Bajo Stock", crearPanelReporteStock());
        pestañas.addTab("Productos Mas Vendidos", crearPanelProductosMasVendidos());
        
        add(pestañas, BorderLayout.CENTER);
    }
    
    private JPanel crearPanelReporteVentas() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 255));
        
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltros.setBackground(new Color(240, 248, 255));
        panelFiltros.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel lblFechaInicio = new JLabel("Fecha Inicio:");
        lblFechaInicio.setFont(new Font("Arial", Font.BOLD, 12));
        dateInicio = new JDateChooser();
        dateInicio.setPreferredSize(new Dimension(120, 25));
        dateInicio.setDateFormatString("yyyy-MM-dd");
        dateInicio.setDate(new Date());
        
        JLabel lblFechaFin = new JLabel("Fecha Fin:");
        lblFechaFin.setFont(new Font("Arial", Font.BOLD, 12));
        dateFin = new JDateChooser();
        dateFin.setPreferredSize(new Dimension(120, 25));
        dateFin.setDateFormatString("yyyy-MM-dd");
        dateFin.setDate(new Date());
        
        btnFiltrarVentas = new JButton("Filtrar");
        btnFiltrarVentas.setBackground(new Color(79, 70, 229));
        btnFiltrarVentas.setForeground(Color.BLACK);
        btnFiltrarVentas.setFont(new Font("Arial", Font.BOLD, 12));
        btnFiltrarVentas.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnExportarExcel = new JButton("Exportar a Excel");
        btnExportarExcel.setBackground(new Color(34, 197, 94));
        btnExportarExcel.setForeground(Color.BLACK);
        btnExportarExcel.setFont(new Font("Arial", Font.BOLD, 12));
        btnExportarExcel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnVerDetalle = new JButton("Ver Detalle Venta");
        btnVerDetalle.setBackground(new Color(59, 130, 246));
        btnVerDetalle.setForeground(Color.BLACK);
        btnVerDetalle.setFont(new Font("Arial", Font.BOLD, 12));
        btnVerDetalle.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        panelFiltros.add(lblFechaInicio);
        panelFiltros.add(dateInicio);
        panelFiltros.add(Box.createRigidArea(new Dimension(10, 0)));
        panelFiltros.add(lblFechaFin);
        panelFiltros.add(dateFin);
        panelFiltros.add(Box.createRigidArea(new Dimension(10, 0)));
        panelFiltros.add(btnFiltrarVentas);
        panelFiltros.add(Box.createRigidArea(new Dimension(20, 0)));
        panelFiltros.add(btnExportarExcel);
        panelFiltros.add(Box.createRigidArea(new Dimension(10, 0)));
        panelFiltros.add(btnVerDetalle);
        
        panel.add(panelFiltros, BorderLayout.NORTH);
        
        String[] columnas = {"ID", "Folio", "Fecha", "Hora", "Total", "Metodo Pago", "Vendedor"};
        modeloTablaVentas = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaVentas = new JTable(modeloTablaVentas);
        tablaVentas.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaVentas.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaVentas.setRowHeight(30);
        tablaVentas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(tablaVentas);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel panelTotal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelTotal.setBackground(new Color(240, 248, 255));
        panelTotal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        lblTotalVentas = new JLabel("Total de Ventas: $0.00");
        lblTotalVentas.setFont(new Font("Arial", Font.BOLD, 16));
        lblTotalVentas.setForeground(new Color(79, 70, 229));
        
        panelTotal.add(lblTotalVentas);
        panel.add(panelTotal, BorderLayout.SOUTH);
        
        btnFiltrarVentas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cargarReporteVentas();
            }
        });
        
        btnExportarExcel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exportarVentasExcel();
            }
        });
        
        btnVerDetalle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                verDetalleVenta();
            }
        });
        
        tablaVentas.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    verDetalleVenta();
                }
            }
        });
        
        return panel;
    }
    
    private JPanel crearPanelReporteStock() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 255));
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setBackground(new Color(240, 248, 255));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        btnRefrescarStock = new JButton("Refrescar");
        btnRefrescarStock.setBackground(new Color(79, 70, 229));
        btnRefrescarStock.setForeground(Color.BLACK);
        btnRefrescarStock.setFont(new Font("Arial", Font.BOLD, 12));
        btnRefrescarStock.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnExportarStockExcel = new JButton("Exportar a Excel");
        btnExportarStockExcel.setBackground(new Color(34, 197, 94));
        btnExportarStockExcel.setForeground(Color.BLACK);
        btnExportarStockExcel.setFont(new Font("Arial", Font.BOLD, 12));
        btnExportarStockExcel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        panelBotones.add(btnRefrescarStock);
        panelBotones.add(btnExportarStockExcel);
        
        panel.add(panelBotones, BorderLayout.NORTH);
        
        String[] columnas = {"ID", "Producto", "Stock Actual", "Stock Minimo", "Categoria", "Tipo Venta"};
        modeloTablaStock = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaStockBajo = new JTable(modeloTablaStock);
        tablaStockBajo.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaStockBajo.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaStockBajo.setRowHeight(30);
        
        JScrollPane scrollPane = new JScrollPane(tablaStockBajo);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        btnRefrescarStock.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cargarReporteStockBajo();
            }
        });
        
        btnExportarStockExcel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exportarStockExcel();
            }
        });
        
        return panel;
    }
    
    private JPanel crearPanelProductosMasVendidos() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 255));
        

        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltros.setBackground(new Color(240, 248, 255));
        panelFiltros.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel lblFechaInicio = new JLabel("Fecha Inicio:");
        lblFechaInicio.setFont(new Font("Arial", Font.BOLD, 12));
        JDateChooser dateInicio = new JDateChooser();
        dateInicio.setPreferredSize(new Dimension(120, 25));
        dateInicio.setDateFormatString("yyyy-MM-dd");
        dateInicio.setDate(new Date());
        
        JLabel lblFechaFin = new JLabel("Fecha Fin:");
        lblFechaFin.setFont(new Font("Arial", Font.BOLD, 12));
        JDateChooser dateFin = new JDateChooser();
        dateFin.setPreferredSize(new Dimension(120, 25));
        dateFin.setDateFormatString("yyyy-MM-dd");
        dateFin.setDate(new Date());
        
        JLabel lblLimite = new JLabel("Mostrar:");
        lblLimite.setFont(new Font("Arial", Font.BOLD, 12));
        JComboBox<Integer> cmbLimite = new JComboBox<>(new Integer[]{5, 10, 15, 20, 30, 50});
        cmbLimite.setSelectedItem(10);
        cmbLimite.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.setBackground(new Color(79, 70, 229));
        btnFiltrar.setForeground(Color.BLACK);
        btnFiltrar.setFont(new Font("Arial", Font.BOLD, 12));
        btnFiltrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JButton btnExportar = new JButton("Exportar a Excel");
        btnExportar.setBackground(new Color(34, 197, 94));
        btnExportar.setForeground(Color.BLACK);
        btnExportar.setFont(new Font("Arial", Font.BOLD, 12));
        btnExportar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        panelFiltros.add(lblFechaInicio);
        panelFiltros.add(dateInicio);
        panelFiltros.add(Box.createRigidArea(new Dimension(10, 0)));
        panelFiltros.add(lblFechaFin);
        panelFiltros.add(dateFin);
        panelFiltros.add(Box.createRigidArea(new Dimension(10, 0)));
        panelFiltros.add(lblLimite);
        panelFiltros.add(cmbLimite);
        panelFiltros.add(Box.createRigidArea(new Dimension(10, 0)));
        panelFiltros.add(btnFiltrar);
        panelFiltros.add(Box.createRigidArea(new Dimension(20, 0)));
        panelFiltros.add(btnExportar);
        
        panel.add(panelFiltros, BorderLayout.NORTH);
        

        String[] columnas = {"ID", "Producto", "Cantidad Vendida", "Total Vendido", "Categoria", "Tipo Venta"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable tablaProductos = new JTable(modeloTabla);
        tablaProductos.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaProductos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaProductos.setRowHeight(30);
        
        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        panel.add(scrollPane, BorderLayout.CENTER);
        

        btnFiltrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                java.util.Date fechaInicioDate = dateInicio.getDate();
                java.util.Date fechaFinDate = dateFin.getDate();
                int limite = (int) cmbLimite.getSelectedItem();
                
                if (fechaInicioDate == null || fechaFinDate == null) {
                    JOptionPane.showMessageDialog(panel, "Seleccione fechas validas");
                    return;
                }
                
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String fechaInicioStr = sdf.format(fechaInicioDate);
                String fechaFinStr = sdf.format(fechaFinDate);
                
                List<Object[]> productos = controladorReporte.getProductosMasVendidos(fechaInicioStr, fechaFinStr, limite);
                
                modeloTabla.setRowCount(0);
                
                if (productos.isEmpty()) {
                    modeloTabla.addRow(new Object[]{"-", "No hay datos", "-", "-", "-", "-"});
                } else {
                    for (Object[] p : productos) {
                        String unidad = "";
                        String tipoVenta = (String) p[5];
                        if (tipoVenta != null) {
                            if (tipoVenta.equals("Peso")) unidad = " Kg";
                            if (tipoVenta.equals("Granel")) unidad = " Lts";
                        }
                        
                        modeloTabla.addRow(new Object[]{
                            p[0], 
                            p[1], 
                            p[2] + unidad, 
                            "$" + p[3], 
                            p[4], 
                            tipoVenta != null ? tipoVenta : "Unidad"
                        });
                    }
                }
            }
        });
        

        btnExportar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Guardar Reporte de Productos Mas Vendidos");
                fileChooser.setSelectedFile(new File("ReporteProductosMasVendidos.csv"));
                
                int resultado = fileChooser.showSaveDialog(panel);
                if (resultado == JFileChooser.APPROVE_OPTION) {
                    String ruta = fileChooser.getSelectedFile().getAbsolutePath();
                    if (!ruta.endsWith(".csv")) {
                        ruta += ".csv";
                    }
                    
                    List<String[]> datos = new ArrayList<>();
                    for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                        String[] fila = new String[6];
                        for (int j = 0; j < 6; j++) {
                            Object valor = modeloTabla.getValueAt(i, j);
                            fila[j] = valor != null ? valor.toString() : "";
                        }
                        datos.add(fila);
                    }
                    
                    String[][] arrayDatos = datos.toArray(new String[0][]);
                    String[] encabezados = {"ID", "Producto", "Cantidad Vendida", "Total Vendido", "Categoria", "Tipo Venta"};
                    
                    boolean exportado = controladorReporte.exportarACSV(ruta, arrayDatos, encabezados);
                    
                    if (exportado) {
                        JOptionPane.showMessageDialog(panel, "Reporte exportado a: " + ruta);
                    } else {
                        JOptionPane.showMessageDialog(panel, "Error al exportar el reporte");
                    }
                }
            }
        });
        
        return panel;
    }
    
    
    
    
    
    private void cargarReporteVentas() {
        java.util.Date fechaInicioDate = dateInicio.getDate();
        java.util.Date fechaFinDate = dateFin.getDate();
        
        if (fechaInicioDate == null || fechaFinDate == null) {
            JOptionPane.showMessageDialog(this, "Seleccione fechas validas");
            return;
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaInicio = sdf.format(fechaInicioDate);
        String fechaFin = sdf.format(fechaFinDate);
        
        List<Object[]> ventas = controladorReporte.getReporteVentas(fechaInicio, fechaFin);
        BigDecimal total = controladorReporte.getTotalVentasPeriodo(fechaInicio, fechaFin);
        
        modeloTablaVentas.setRowCount(0);
        
        for (Object[] venta : ventas) {
            modeloTablaVentas.addRow(new Object[]{
                venta[0], venta[1], venta[2], venta[3], "$" + venta[4], venta[5], venta[6]
            });
        }
        
        lblTotalVentas.setText("Total de Ventas: $" + total.toString());
    }
    
    private void cargarReporteStockBajo() {
        List<Object[]> productos = controladorReporte.getProductosStockBajo();
        
        modeloTablaStock.setRowCount(0);
        
        if (productos.isEmpty()) {
            modeloTablaStock.addRow(new Object[]{"-", "No hay productos con stock bajo", "-", "-", "-", "-"});
        } else {
            for (Object[] p : productos) {
                String unidad = "";
                String tipoVenta = (String) p[5];
                if (tipoVenta != null) {
                    if (tipoVenta.equals("Peso")) unidad = " Kg";
                    if (tipoVenta.equals("Granel")) unidad = " Lts";
                }
                
                modeloTablaStock.addRow(new Object[]{
                    p[0], p[1], p[2] + unidad, p[3] + unidad, p[4], tipoVenta != null ? tipoVenta : "Unidad"
                });
            }
        }
    }
    
    private void verDetalleVenta() {
        int fila = tablaVentas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una venta para ver detalle");
            return;
        }
        
        int idVenta = (int) modeloTablaVentas.getValueAt(fila, 0);
        String folio = (String) modeloTablaVentas.getValueAt(fila, 1);
        
        Venta venta = controladorReporte.obtenerVentaConDetalles(idVenta);
        
        if (venta != null && venta.getDetalles() != null) {

            String rutaTicket = controladorReporte.generarTicketSiNoExiste(venta, folio);
            
            if (rutaTicket != null) {
                VistaTicket ticketVista = new VistaTicket(vistaPrincipal, venta, venta.getDetalles(), rutaTicket);
                ticketVista.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Error al generar el ticket");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Error al cargar los detalles de la venta");
        }
    }
    
    private void exportarVentasExcel() {
        java.util.Date fechaInicioDate = dateInicio.getDate();
        java.util.Date fechaFinDate = dateFin.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String fechaInicioStr = sdf.format(fechaInicioDate);
        String fechaFinStr = sdf.format(fechaFinDate);
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Reporte de Ventas");
        fileChooser.setSelectedFile(new File("ReporteVentas_" + fechaInicioStr + "_a_" + fechaFinStr + ".csv"));
        
        int resultado = fileChooser.showSaveDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            String ruta = fileChooser.getSelectedFile().getAbsolutePath();
            if (!ruta.endsWith(".csv")) {
                ruta += ".csv";
            }
            
            List<String[]> datos = new ArrayList<>();
            for (int i = 0; i < modeloTablaVentas.getRowCount(); i++) {
                String[] fila = new String[7];
                for (int j = 0; j < 7; j++) {
                    Object valor = modeloTablaVentas.getValueAt(i, j);
                    fila[j] = valor != null ? valor.toString() : "";
                }
                datos.add(fila);
            }
            
            String[][] arrayDatos = datos.toArray(new String[0][]);
            String[] encabezados = {"ID", "Folio", "Fecha", "Hora", "Total", "Metodo Pago", "Vendedor"};
            
            boolean exportado = controladorReporte.exportarACSV(ruta, arrayDatos, encabezados);
            
            if (exportado) {
                JOptionPane.showMessageDialog(this, "Reporte exportado a: " + ruta);
            } else {
                JOptionPane.showMessageDialog(this, "Error al exportar el reporte");
            }
        }
    }
    
    private void exportarStockExcel() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Reporte de Stock Bajo");
        fileChooser.setSelectedFile(new File("ReporteStockBajo_" + sdf.format(new Date()) + ".csv"));
        
        int resultado = fileChooser.showSaveDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            String ruta = fileChooser.getSelectedFile().getAbsolutePath();
            if (!ruta.endsWith(".csv")) {
                ruta += ".csv";
            }
            
            List<String[]> datos = new ArrayList<>();
            for (int i = 0; i < modeloTablaStock.getRowCount(); i++) {
                String[] fila = new String[6];
                for (int j = 0; j < 6; j++) {
                    Object valor = modeloTablaStock.getValueAt(i, j);
                    fila[j] = valor != null ? valor.toString() : "";
                }
                datos.add(fila);
            }
            
            String[][] arrayDatos = datos.toArray(new String[0][]);
            String[] encabezados = {"ID", "Producto", "Stock Actual", "Stock Minimo", "Categoria", "Tipo Venta"};
            
            boolean exportado = controladorReporte.exportarACSV(ruta, arrayDatos, encabezados);
            
            if (exportado) {
                JOptionPane.showMessageDialog(this, "Reporte exportado a: " + ruta);
            } else {
                JOptionPane.showMessageDialog(this, "Error al exportar el reporte");
            }
        }
    }
    
    public void refrescar() {
        cargarReporteVentas();
        cargarReporteStockBajo();
    }
}