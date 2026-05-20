package Vista;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import BasedeDatos.ConexionBD;
import Controlador.ControladorProducto;
import Controlador.ControladorVenta;
import Modelo.Venta;
import Modelo.DetalleVenta;

public class VistaDevolucion extends JPanel {
    private VistaPrincipal vistaPrincipal;
    private ControladorVenta controladorVenta;
    private ControladorProducto controladorProducto;
    
    private JTextField txtBuscarFolio;
    private JButton btnBuscar;
    private JLabel lblFolio;
    private JLabel lblFecha;
    private JLabel lblCliente;
    private JLabel lblTotal;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private JButton btnDevolver;
    private JButton btnCancelar;
    private JLabel lblError;
    
    private int idVentaSeleccionada;
    private String folioVenta;
    private BigDecimal totalVenta;
    private List<DetalleVenta> listaDetalles;
    
    public VistaDevolucion(VistaPrincipal vista) {
        this.vistaPrincipal = vista;
        this.controladorVenta = new ControladorVenta();
        this.controladorProducto = new ControladorProducto();
        
        setLayout(new BorderLayout());
        setBackground(new Color(240, 248, 255));
        
        listaDetalles = new ArrayList<>();
        
        iniciarComponentes();
    }
    
    private void iniciarComponentes() {
        
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.setBackground(new Color(240, 248, 255));
        panelBusqueda.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel lblInstruccion = new JLabel("Ingrese Folio de Venta:");
        lblInstruccion.setFont(new Font("Arial", Font.BOLD, 12));
        
        txtBuscarFolio = new JTextField(15);
        txtBuscarFolio.setFont(new Font("Arial", Font.PLAIN, 12));
        
        btnBuscar = new JButton("Buscar Venta");
        btnBuscar.setBackground(new Color(79, 70, 229));
        btnBuscar.setForeground(Color.BLACK);
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 12));
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        panelBusqueda.add(lblInstruccion);
        panelBusqueda.add(txtBuscarFolio);
        panelBusqueda.add(btnBuscar);
        
        add(panelBusqueda, BorderLayout.NORTH);
        

        JPanel panelInfo = new JPanel(new GridLayout(4, 2, 10, 10));
        panelInfo.setBackground(new Color(240, 248, 255));
        panelInfo.setBorder(BorderFactory.createTitledBorder("Informacion de la Venta"));
        
        JLabel lblFolioTxt = new JLabel("Folio:");
        lblFolioTxt.setFont(new Font("Arial", Font.BOLD, 12));
        lblFolio = new JLabel("");
        lblFolio.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JLabel lblFechaTxt = new JLabel("Fecha:");
        lblFechaTxt.setFont(new Font("Arial", Font.BOLD, 12));
        lblFecha = new JLabel("");
        lblFecha.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JLabel lblClienteTxt = new JLabel("Cliente:");
        lblClienteTxt.setFont(new Font("Arial", Font.BOLD, 12));
        lblCliente = new JLabel("");
        lblCliente.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JLabel lblTotalTxt = new JLabel("Total Venta:");
        lblTotalTxt.setFont(new Font("Arial", Font.BOLD, 12));
        lblTotal = new JLabel("");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotal.setForeground(new Color(79, 70, 229));
        
        panelInfo.add(lblFolioTxt);
        panelInfo.add(lblFolio);
        panelInfo.add(lblFechaTxt);
        panelInfo.add(lblFecha);
        panelInfo.add(lblClienteTxt);
        panelInfo.add(lblCliente);
        panelInfo.add(lblTotalTxt);
        panelInfo.add(lblTotal);
        

        String[] columnas = {"Seleccionar", "ID", "Producto", "Cantidad", "Precio", "Subtotal"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            public Class<?> getColumnClass(int column) {
                if (column == 0) return Boolean.class;
                return String.class;
            }
            public boolean isCellEditable(int row, int column) {
                return column == 0;
            }
        };
        
        tablaProductos = new JTable(modeloTabla);
        tablaProductos.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaProductos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaProductos.setRowHeight(30);
        
        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.add(panelInfo, BorderLayout.NORTH);
        panelCentral.add(scrollPane, BorderLayout.CENTER);
        
        add(panelCentral, BorderLayout.CENTER);
        

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelInferior.setBackground(new Color(240, 248, 255));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        btnDevolver = new JButton("Registrar Devolucion");
        btnDevolver.setBackground(new Color(220, 38, 38));
        btnDevolver.setForeground(Color.BLACK);
        btnDevolver.setFont(new Font("Arial", Font.BOLD, 12));
        btnDevolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDevolver.setEnabled(false);
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(107, 114, 128));
        btnCancelar.setForeground(Color.BLACK);
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 12));
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        lblError = new JLabel("");
        lblError.setForeground(Color.RED);
        lblError.setFont(new Font("Arial", Font.BOLD, 11));
        
        panelInferior.add(lblError);
        panelInferior.add(btnDevolver);
        panelInferior.add(btnCancelar);
        
        add(panelInferior, BorderLayout.SOUTH);
        

        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buscarVenta();
            }
        });
        
        txtBuscarFolio.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    buscarVenta();
                }
            }
        });
        
        btnDevolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registrarDevolucion();
            }
        });
        
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
            }
        });
    }
    
    private void buscarVenta() {
        String folio = txtBuscarFolio.getText().trim();
        
        if (folio.isEmpty()) {
            lblError.setText("Ingrese un folio de venta");
            return;
        }
        
        Venta venta = null;
        try {
            String sql = "SELECT v.id, v.folio, v.fecha, v.total FROM Venta v WHERE v.folio = ? AND v.cancelada = 0";
            try (Connection conn = ConexionBD.obtenerConexion();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, folio);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    venta = new Venta();
                    venta.setId(rs.getInt("id"));
                    venta.setFolio(rs.getString("folio"));
                    venta.setFecha(rs.getTimestamp("fecha"));
                    venta.setTotal(rs.getBigDecimal("total"));
                }
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            lblError.setText("Error: " + e.getMessage());
            return;
        }
        
        if (venta != null) {
            idVentaSeleccionada = venta.getId();
            folioVenta = venta.getFolio();
            totalVenta = venta.getTotal();
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            lblFolio.setText(venta.getFolio());
            lblFecha.setText(venta.getFecha() != null ? sdf.format(venta.getFecha()) : "");
            lblCliente.setText("Publico General");
            lblTotal.setText("$" + totalVenta.toString());
            
            cargarDetallesVenta();
            btnDevolver.setEnabled(true);
            lblError.setText("");
        } else {
            lblError.setText("Venta no encontrada o ya fue cancelada");
            limpiarFormulario();
        }
    }
    
    private void cargarDetallesVenta() {
        String sql = "SELECT dv.producto_id, p.nombre, dv.cantidad, dv.precio_unitario, dv.subtotal " +
                     "FROM DetalleVenta dv " +
                     "LEFT JOIN Producto p ON dv.producto_id = p.id " +
                     "WHERE dv.venta_id = ?";
        
        modeloTabla.setRowCount(0);
        listaDetalles.clear();
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idVentaSeleccionada);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                DetalleVenta d = new DetalleVenta();
                d.setIdProducto(rs.getInt("producto_id"));
                d.setNombreProducto(rs.getString("nombre"));
                d.setCantidad(rs.getInt("cantidad"));
                d.setPrecioUnitario(rs.getBigDecimal("precio_unitario"));
                d.setSubtotal(rs.getBigDecimal("subtotal"));
                listaDetalles.add(d);
                
                modeloTabla.addRow(new Object[]{
                    false,
                    d.getIdProducto(),
                    d.getNombreProducto(),
                    d.getCantidad(),
                    "$" + d.getPrecioUnitario().toString(),
                    "$" + d.getSubtotal().toString()
                });
            }
            rs.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            lblError.setText("Error al cargar detalles: " + e.getMessage());
        }
    }
    
    private void registrarDevolucion() {
        List<DetalleVenta> productosADevolver = new ArrayList<>();
        BigDecimal totalDevolucion = BigDecimal.ZERO;
        
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            Boolean seleccionado = (Boolean) modeloTabla.getValueAt(i, 0);
            if (seleccionado != null && seleccionado) {
                DetalleVenta d = listaDetalles.get(i);
                productosADevolver.add(d);
                totalDevolucion = totalDevolucion.add(d.getSubtotal());
            }
        }
        
        if (productosADevolver.isEmpty()) {
            lblError.setText("Seleccione al menos un producto para devolver");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Registrar devolucion?\n\n" +
            "Folio: " + folioVenta + "\n" +
            "Total a devolver: $" + totalDevolucion.toString() + "\n\n" +
            "Se reembolsara al cliente y se actualizara el inventario.",
            "Confirmar Devolucion", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            Connection conn = null;
            try {
                conn = ConexionBD.obtenerConexion();
                conn.setAutoCommit(false);
                
                String sqlDevolucion = "INSERT INTO Devolucion (venta_id, fecha, monto_total, usuario_id) VALUES (?, GETDATE(), ?, ?)";
                PreparedStatement stmtDevolucion = conn.prepareStatement(sqlDevolucion, Statement.RETURN_GENERATED_KEYS);
                stmtDevolucion.setInt(1, idVentaSeleccionada);
                stmtDevolucion.setBigDecimal(2, totalDevolucion);
                stmtDevolucion.setInt(3, 1);
                stmtDevolucion.executeUpdate();
                
                ResultSet rs = stmtDevolucion.getGeneratedKeys();
                int idDevolucion = 0;
                if (rs.next()) {
                    idDevolucion = rs.getInt(1);
                }
                rs.close();
                stmtDevolucion.close();
                
                String sqlDetalle = "INSERT INTO DetalleDevolucion (devolucion_id, producto_id, cantidad, precio_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmtDetalle = conn.prepareStatement(sqlDetalle);
                
                String sqlUpdateStock = "UPDATE Producto SET stock = stock + ? WHERE id = ?";
                PreparedStatement stmtStock = conn.prepareStatement(sqlUpdateStock);
                
                for (DetalleVenta d : productosADevolver) {
                    stmtDetalle.setInt(1, idDevolucion);
                    stmtDetalle.setInt(2, d.getIdProducto());
                    stmtDetalle.setDouble(3, d.getCantidad());  
                    stmtDetalle.setBigDecimal(4, d.getPrecioUnitario());
                    stmtDetalle.setBigDecimal(5, d.getSubtotal());
                    stmtDetalle.addBatch();
                    
                    stmtStock.setDouble(1, d.getCantidad());  
                    stmtStock.setInt(2, d.getIdProducto());
                    stmtStock.addBatch();
                }
                
                stmtDetalle.executeBatch();
                stmtStock.executeBatch();
                stmtDetalle.close();
                stmtStock.close();
                
                conn.commit();
                
                JOptionPane.showMessageDialog(this, "Devolucion registrada correctamente");
                limpiarFormulario();
                
            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    if (conn != null) conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                lblError.setText("Error al registrar devolucion: " + e.getMessage());
            }
        }
    }
    
    private void limpiarFormulario() {
        txtBuscarFolio.setText("");
        lblFolio.setText("");
        lblFecha.setText("");
        lblCliente.setText("");
        lblTotal.setText("");
        modeloTabla.setRowCount(0);
        btnDevolver.setEnabled(false);
        lblError.setText("");
        idVentaSeleccionada = 0;
        folioVenta = null;
        totalVenta = null;
    }
    
    public void refrescar() {
        limpiarFormulario();
    }
}