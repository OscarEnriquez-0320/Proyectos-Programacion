package Vista;

import Controlador.ReporteControlador;
import Modelo.Venta;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class VistaReportes extends JPanel {
    private ReporteControlador reporteControlador;
    private DefaultTableModel modeloReporte;
    private JTable tablaReporte;
    private JComboBox<String> cmbTipoReporte;
    private JLabel lblTotalVentas, lblTotalProductos, lblTotalVentasHoy, lblUtilidad;
    private DecimalFormat formatoMoneda = new DecimalFormat("#,##0.00");
    private SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    
    public VistaReportes() {
        reporteControlador = new ReporteControlador();
        iniciarComponentes();
        cargarReporte("VENTAS");
        actualizarDashboard();
    }
    
    private void iniciarComponentes() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(new Color(52, 73, 94));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel lblTitulo = new JLabel("REPORTES Y ESTADÍSTICAS");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);
        
        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelControles.setBackground(new Color(52, 73, 94));
        
        JLabel lblTipo = new JLabel("Tipo de Reporte:");
        lblTipo.setForeground(Color.WHITE);
        
        cmbTipoReporte = new JComboBox<>(new String[]{
            "VENTAS", 
            "PRODUCTOS MÁS VENDIDOS", 
            "PRODUCTOS CON STOCK BAJO",
            "PRODUCTOS POR CATEGORÍA",
            "VENTAS POR MÉTODO DE PAGO"
        });
        cmbTipoReporte.setBackground(Color.WHITE);
        cmbTipoReporte.addActionListener(e -> {
            cargarReporte((String) cmbTipoReporte.getSelectedItem());
        });
        
        JButton btnActualizar = new JButton("🔄 ACTUALIZAR");
        btnActualizar.setBackground(new Color(46, 204, 113));
        btnActualizar.setForeground(Color.BLACK);  // Texto negro
        btnActualizar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnActualizar.setFocusPainted(false);
        btnActualizar.addActionListener(e -> {
            cargarReporte((String) cmbTipoReporte.getSelectedItem());
            actualizarDashboard();
        });
        
        panelControles.add(lblTipo);
        panelControles.add(cmbTipoReporte);
        panelControles.add(Box.createHorizontalStrut(15));
        panelControles.add(btnActualizar);
        
        panelSuperior.add(lblTitulo, BorderLayout.WEST);
        panelSuperior.add(panelControles, BorderLayout.EAST);
        
        add(panelSuperior, BorderLayout.NORTH);
        
        JPanel panelDashboard = crearPanelDashboard();
        add(panelDashboard, BorderLayout.NORTH);
        
        modeloReporte = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaReporte = new JTable(modeloReporte);
        tablaReporte.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaReporte.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tablaReporte.setRowHeight(28);
        
        JScrollPane scroll = new JScrollPane(tablaReporte);
        add(scroll, BorderLayout.CENTER);
        
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelInferior.setBackground(new Color(245, 245, 250));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton btnExportar = new JButton("📎 EXPORTAR A EXCEL");
        btnExportar.setBackground(new Color(46, 204, 113));
        btnExportar.setForeground(Color.BLACK);  // Texto negro
        btnExportar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnExportar.setFocusPainted(false);
        btnExportar.addActionListener(e -> exportarReporte());
        
        panelInferior.add(btnExportar);
        
        add(panelInferior, BorderLayout.SOUTH);
    }
    
    private JPanel crearPanelDashboard() {
        JPanel panel = new JPanel(new GridLayout(1, 4, 15, 0));
        panel.setBackground(new Color(245, 245, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JPanel card1 = crearCardDashboard("💰 TOTAL VENTAS", "$. 0.00", new Color(52, 73, 94));
        lblTotalVentas = (JLabel) ((JPanel) card1.getComponent(1)).getComponent(0);
        
        JPanel card2 = crearCardDashboard("📦 PRODUCTOS VENDIDOS", "0", new Color(46, 204, 113));
        lblTotalProductos = (JLabel) ((JPanel) card2.getComponent(1)).getComponent(0);
        
        JPanel card3 = crearCardDashboard("📅 VENTAS HOY", "$. 0.00", new Color(52, 152, 219));
        lblTotalVentasHoy = (JLabel) ((JPanel) card3.getComponent(1)).getComponent(0);
        
        JPanel card4 = crearCardDashboard("📈 UTILIDAD ESTIMADA", "$. 0.00", new Color(230, 126, 34));
        lblUtilidad = (JLabel) ((JPanel) card4.getComponent(1)).getComponent(0);
        
        panel.add(card1);
        panel.add(card2);
        panel.add(card3);
        panel.add(card4);
        
        return panel;
    }
    
    private JPanel crearCardDashboard(String titulo, String valor, Color colorBorde) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(colorBorde, 2),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblTitulo.setForeground(Color.GRAY);
        
        JPanel panelValor = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelValor.setBackground(Color.WHITE);
        
        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblValor.setForeground(colorBorde);
        
        panelValor.add(lblValor);
        
        card.add(lblTitulo, BorderLayout.NORTH);
        card.add(panelValor, BorderLayout.CENTER);
        
        return card;
    }
    
    private void actualizarDashboard() {
        Map<String, Object> stats = reporteControlador.obtenerEstadisticasGenerales();
        
        lblTotalVentas.setText("L. " + formatoMoneda.format((BigDecimal) stats.get("totalFacturado")));
        lblTotalProductos.setText(String.valueOf(stats.get("totalVentas")));
        lblTotalVentasHoy.setText("L. " + formatoMoneda.format((BigDecimal) stats.get("ventasHoy")));
        lblUtilidad.setText("L. " + formatoMoneda.format((BigDecimal) stats.get("utilidadTotal")));
    }
    public void recargarDatos() {
        cargarReporte((String) cmbTipoReporte.getSelectedItem());
        actualizarDashboard();
    }
    private void cargarReporte(String tipo) {
        modeloReporte.setRowCount(0);
        
        switch (tipo) {
            case "VENTAS":
                cargarReporteVentas();
                break;
            case "PRODUCTOS MÁS VENDIDOS":
                cargarReporteProductosMasVendidos();
                break;
            case "PRODUCTOS CON STOCK BAJO":
                cargarReporteStockBajo();
                break;
            case "PRODUCTOS POR CATEGORÍA":
                cargarReporteProductosPorCategoria();
                break;
            case "VENTAS POR MÉTODO DE PAGO":
                cargarReporteVentasPorMetodo();
                break;
        }
    }
    
    private void cargarReporteVentas() {
        modeloReporte.setColumnIdentifiers(new String[]{"Factura", "Fecha", "Total", "Método Pago", "Productos"});
        
        List<Venta> ventas = reporteControlador.obtenerTodasVentas();
        for (Venta v : ventas) {
            int cantidadProductos = v.getDetalles().stream().mapToInt(d -> d.getCantidad()).sum();
            
            modeloReporte.addRow(new Object[]{
                v.getNumeroFactura(),
                formatoFecha.format(v.getFecha()),
                "L. " + formatoMoneda.format(v.getTotal()),
                v.getMetodoPago(),
                cantidadProductos
            });
        }
    }
    
    private void cargarReporteProductosMasVendidos() {
        modeloReporte.setColumnIdentifiers(new String[]{"Producto", "Cantidad Vendida", "Total Vendido", "Stock Actual"});
        
        List<Map<String, Object>> productos = reporteControlador.obtenerProductosMasVendidos(0);
        for (Map<String, Object> p : productos) {
            modeloReporte.addRow(new Object[]{
                p.get("producto"),
                p.get("cantidad"),
                "L. " + formatoMoneda.format((BigDecimal) p.get("total")),
                p.get("stockActual")
            });
        }
    }
    
    private void cargarReporteStockBajo() {
        modeloReporte.setColumnIdentifiers(new String[]{"Código", "Producto", "Stock Actual", "Stock Mínimo", "Unidad", "Ubicación"});
        
        var productos = reporteControlador.obtenerProductosStockBajo();
        for (var p : productos) {
            modeloReporte.addRow(new Object[]{
                p.getCodigo(),
                p.getNombre(),
                p.getCantidadAlmacen(),
                p.getCantidadMinima(),
                p.getUnidad() != null ? p.getUnidad().getAbreviatura() : "-",
                p.getUbicacion() != null ? p.getUbicacion() : "-"
            });
        }
    }
    
    private void cargarReporteProductosPorCategoria() {
        modeloReporte.setColumnIdentifiers(new String[]{"Categoría", "Productos", "Stock Total", "Valor Inventario"});
        
        List<Map<String, Object>> categorias = reporteControlador.obtenerResumenPorCategoria();
        for (Map<String, Object> cat : categorias) {
            modeloReporte.addRow(new Object[]{
                cat.get("categoria"),
                cat.get("cantidadProductos"),
                cat.get("stockTotal"),
                "L. " + formatoMoneda.format((BigDecimal) cat.get("valorInventario"))
            });
        }
    }
    
    private void cargarReporteVentasPorMetodo() {
        modeloReporte.setColumnIdentifiers(new String[]{"Método de Pago", "Cantidad Ventas", "Total Vendido", "Porcentaje"});
        
        Map<String, Object> resumen = reporteControlador.obtenerResumenVentasPorMetodo();
        for (Map.Entry<String, Object> entry : resumen.entrySet()) {
            Map<String, Object> datos = (Map<String, Object>) entry.getValue();
            modeloReporte.addRow(new Object[]{
                entry.getKey(),
                datos.get("cantidad"),
                "L. " + formatoMoneda.format((BigDecimal) datos.get("total")),
                String.format("%.1f%%", datos.get("porcentaje"))
            });
        }
    }
    
    private void exportarReporte() {
        JOptionPane.showMessageDialog(this, 
            "Reporte exportado exitosamente\nTipo: " + cmbTipoReporte.getSelectedItem() +
            "\nFecha: " + formatoFecha.format(new Date()),
            "Exportar", JOptionPane.INFORMATION_MESSAGE);
    }
}