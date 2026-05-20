package Vista;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import Controlador.ControladorVenta;
import Modelo.DetalleVenta;
import Modelo.Venta;

public class VistaDetalleVenta extends JDialog {
    private ControladorVenta controladorVenta;
    private Venta venta;
    
    private JLabel lblFolio;
    private JLabel lblFecha;
    private JLabel lblVendedor;
    private JLabel lblMetodoPago;
    private JLabel lblTotal;
    private JTable tablaDetalles;
    private DefaultTableModel modeloTabla;
    private JButton btnCerrar;
    
    public VistaDetalleVenta(JFrame padre, Venta venta) {
        super(padre, "Detalle de Venta - " + venta.getFolio(), true);
        this.controladorVenta = new ControladorVenta();
        this.venta = venta;
        
        setSize(700, 500);
        setLocationRelativeTo(padre);
        setResizable(false);
        getContentPane().setLayout(new BorderLayout());
        
        iniciarComponentes();
        cargarDetalle();
    }
    
    private void iniciarComponentes() {
       
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(79, 70, 229));
        panelTitulo.setPreferredSize(new Dimension(getWidth(), 50));
        
        JLabel lblTitulo = new JLabel("DETALLE DE VENTA");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelTitulo.add(lblTitulo);
        
        getContentPane().add(panelTitulo, BorderLayout.NORTH);
        
   
        JPanel panelInfo = new JPanel(new GridLayout(2, 4, 10, 10));
        panelInfo.setBackground(new Color(240, 248, 255));
        panelInfo.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        lblFolio = new JLabel("Folio: ");
        lblFolio.setFont(new Font("Arial", Font.BOLD, 12));
        
        lblFecha = new JLabel("Fecha: ");
        lblFecha.setFont(new Font("Arial", Font.PLAIN, 12));
        
        lblVendedor = new JLabel("Vendedor: ");
        lblVendedor.setFont(new Font("Arial", Font.PLAIN, 12));
        
        lblMetodoPago = new JLabel("Metodo Pago: ");
        lblMetodoPago.setFont(new Font("Arial", Font.PLAIN, 12));
        
        lblTotal = new JLabel("Total: ");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotal.setForeground(new Color(79, 70, 229));
        
        panelInfo.add(lblFolio);
        panelInfo.add(lblFecha);
        panelInfo.add(lblVendedor);
        panelInfo.add(lblMetodoPago);
        panelInfo.add(new JLabel());
        panelInfo.add(new JLabel());
        panelInfo.add(lblTotal);
        panelInfo.add(new JLabel());
        
        getContentPane().add(panelInfo, BorderLayout.NORTH);
        
        
        String[] columnas = {"Producto", "Cantidad", "Precio Unitario", "Subtotal"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaDetalles = new JTable(modeloTabla);
        tablaDetalles.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaDetalles.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaDetalles.setRowHeight(30);
        
        JScrollPane scrollPane = new JScrollPane(tablaDetalles);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        
        
        JPanel panelBoton = new JPanel();
        panelBoton.setBackground(new Color(240, 248, 255));
        panelBoton.setBorder(BorderFactory.createEmptyBorder(10, 10, 15, 10));
        
        btnCerrar = new JButton("Cerrar");
        btnCerrar.setBackground(new Color(107, 114, 128));
        btnCerrar.setForeground(Color.BLACK);
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 12));
        btnCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        panelBoton.add(btnCerrar);
        
        getContentPane().add(panelBoton, BorderLayout.SOUTH);
        
        btnCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    private void cargarDetalle() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        
        lblFolio.setText("Folio: " + venta.getFolio());
        String fechaStr = venta.getFecha() != null ? sdf.format(venta.getFecha()) : "";
        lblFecha.setText("Fecha: " + fechaStr);
        lblVendedor.setText("Vendedor: " + (venta.getNombreUsuario() != null ? venta.getNombreUsuario() : ""));
        lblMetodoPago.setText("Metodo Pago: " + venta.getMetodoPago());
        lblTotal.setText("Total: $" + venta.getTotal().toString());
        
        modeloTabla.setRowCount(0);
        
        if (venta.getDetalles() != null) {
            for (DetalleVenta d : venta.getDetalles()) {
                modeloTabla.addRow(new Object[]{
                    d.getNombreProducto(),
                    d.getCantidad(),
                    "$" + d.getPrecioUnitario().toString(),
                    "$" + d.getSubtotal().toString()
                });
            }
        }
    }
}