package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import Controlador.ControladorTicket;
import Modelo.Venta;
import Modelo.DetalleVenta;
import java.util.List;

public class VistaTicket extends JDialog {
    private Venta venta;
    private List<DetalleVenta> detalles;
    private String rutaTicket;
    private JTextArea txtTicket;
    private JButton btnGuardar;
    private JButton btnCerrar;
    private ControladorTicket controladorTicket;
    
    public VistaTicket(JFrame padre, Venta venta, List<DetalleVenta> detalles, String rutaTicket) {
        super(padre, "Ticket de Venta - " + venta.getFolio(), true);
        this.venta = venta;
        this.detalles = detalles;
        this.rutaTicket = rutaTicket;
        this.controladorTicket = new ControladorTicket();
        
        setSize(400, 500);
        setLocationRelativeTo(padre);
        setResizable(false);
        getContentPane().setLayout(new BorderLayout());
        
        iniciarComponentes();
        mostrarTicket();
    }
    
    private void iniciarComponentes() {
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(79, 70, 229));
        panelTitulo.setPreferredSize(new Dimension(getWidth(), 40));
        
        JLabel lblTitulo = new JLabel("TICKET DE VENTA");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelTitulo.add(lblTitulo);
        
        getContentPane().add(panelTitulo, BorderLayout.NORTH);
        
        txtTicket = new JTextArea();
        txtTicket.setFont(new Font("Monospaced", Font.PLAIN, 11));
        txtTicket.setEditable(false);
        txtTicket.setMargin(new Insets(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(txtTicket);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.setBackground(new Color(240, 248, 255));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        btnGuardar = new JButton("Guardar PDF");
        btnGuardar.setBackground(new Color(34, 197, 94));
        btnGuardar.setForeground(Color.BLACK);
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 12));
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnCerrar = new JButton("Cerrar");
        btnCerrar.setBackground(new Color(107, 114, 128));
        btnCerrar.setForeground(Color.BLACK);
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 12));
        btnCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCerrar);
        
        getContentPane().add(panelBotones, BorderLayout.SOUTH);
        
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarPDF();
            }
        });
        
        btnCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    private void mostrarTicket() {
        String texto = controladorTicket.generarTextoTicket(venta, detalles);
        txtTicket.setText(texto);
        txtTicket.setCaretPosition(0);
    }
    
    private void guardarPDF() {
        boolean generado = controladorTicket.generarTicketPDF(venta, detalles, rutaTicket);
        
        if (generado) {
            JOptionPane.showMessageDialog(this, "Ticket guardado en:\n" + rutaTicket);
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar el ticket");
        }
    }
}