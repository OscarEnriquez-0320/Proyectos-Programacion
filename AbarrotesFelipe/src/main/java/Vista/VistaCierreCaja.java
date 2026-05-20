package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import Controlador.ControladorCaja;

public class VistaCierreCaja extends JDialog {
    private VistaPrincipal vistaPrincipal;
    private ControladorCaja controladorCaja;
    private int idCorteAbierto;
    
    private JLabel lblFechaApertura;
    private JLabel lblUsuarioApertura;
    private JLabel lblMontoApertura;
    private JLabel lblVentasEfectivo;
    private JLabel lblMontoEsperado;
    private JTextField txtMontoFisico;
    private JLabel lblDiferencia;
    private JButton btnCerrar;
    private JButton btnCancelar;
    private JLabel lblError;
    
    private double montoEsperado;
    
    public VistaCierreCaja(VistaPrincipal vista, int idCorte) {
        super(vista, "Cierre de Caja", true);
        this.vistaPrincipal = vista;
        this.controladorCaja = new ControladorCaja();
        this.idCorteAbierto = idCorte;
        
        setSize(450, 480);
        setLocationRelativeTo(vista);
        setResizable(false);
        getContentPane().setLayout(new BorderLayout());
        
        iniciarComponentes();
        cargarDatosCorte();
    }
    
    private void iniciarComponentes() {
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(79, 70, 229));
        panelTitulo.setPreferredSize(new Dimension(getWidth(), 50));
        
        JLabel lblTitulo = new JLabel("CIERRE DE CAJA");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelTitulo.add(lblTitulo);
        
        getContentPane().add(panelTitulo, BorderLayout.NORTH);
        
        JPanel panelFormulario = new JPanel();
        panelFormulario.setBackground(new Color(240, 248, 255));
        panelFormulario.setLayout(new GridBagLayout());
        
        JLabel lblFecha = new JLabel("Fecha Apertura:");
        lblFecha.setFont(new Font("Arial", Font.BOLD, 12));
        lblFechaApertura = new JLabel("");
        lblFechaApertura.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JLabel lblUsuario = new JLabel("Usuario Apertura:");
        lblUsuario.setFont(new Font("Arial", Font.BOLD, 12));
        lblUsuarioApertura = new JLabel("");
        lblUsuarioApertura.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JLabel lblMonto = new JLabel("Monto Apertura:");
        lblMonto.setFont(new Font("Arial", Font.BOLD, 12));
        lblMontoApertura = new JLabel("");
        lblMontoApertura.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JLabel lblVentas = new JLabel("Ventas en Efectivo:");
        lblVentas.setFont(new Font("Arial", Font.BOLD, 12));
        lblVentasEfectivo = new JLabel("");
        lblVentasEfectivo.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JSeparator separador = new JSeparator();
        
        JLabel lblEsperado = new JLabel("Monto Esperado:");
        lblEsperado.setFont(new Font("Arial", Font.BOLD, 12));
        lblMontoEsperado = new JLabel("$0.00");
        lblMontoEsperado.setFont(new Font("Arial", Font.BOLD, 14));
        lblMontoEsperado.setForeground(new Color(79, 70, 229));
        
        JLabel lblFisico = new JLabel("Monto Fisico en Caja:");
        lblFisico.setFont(new Font("Arial", Font.BOLD, 12));
        txtMontoFisico = new JTextField(15);
        txtMontoFisico.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JLabel lblDif = new JLabel("Diferencia:");
        lblDif.setFont(new Font("Arial", Font.BOLD, 12));
        lblDiferencia = new JLabel("$0.00");
        lblDiferencia.setFont(new Font("Arial", Font.BOLD, 14));
        
        btnCerrar = new JButton("Cerrar Caja");
        btnCerrar.setBackground(new Color(34, 197, 94));
        btnCerrar.setForeground(Color.BLACK);
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 12));
        btnCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(107, 114, 128));
        btnCancelar.setForeground(Color.BLACK);
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 12));
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        lblError = new JLabel("");
        lblError.setForeground(Color.RED);
        lblError.setFont(new Font("Arial", Font.BOLD, 11));
        
        int fila = 0;
        
        GridBagConstraints gbc0 = new GridBagConstraints();
        gbc0.insets = new Insets(20, 20, 5, 10);
        gbc0.gridx = 0;
        gbc0.gridy = fila;
        gbc0.anchor = GridBagConstraints.EAST;
        panelFormulario.add(lblFecha, gbc0);
        
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.insets = new Insets(20, 10, 5, 20);
        gbc1.gridx = 1;
        gbc1.gridy = fila++;
        gbc1.anchor = GridBagConstraints.WEST;
        panelFormulario.add(lblFechaApertura, gbc1);
        
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(5, 20, 5, 10);
        gbc2.gridx = 0;
        gbc2.gridy = fila;
        gbc2.anchor = GridBagConstraints.EAST;
        panelFormulario.add(lblUsuario, gbc2);
        
        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.insets = new Insets(5, 10, 5, 20);
        gbc3.gridx = 1;
        gbc3.gridy = fila++;
        gbc3.anchor = GridBagConstraints.WEST;
        panelFormulario.add(lblUsuarioApertura, gbc3);
        
        GridBagConstraints gbc4 = new GridBagConstraints();
        gbc4.insets = new Insets(5, 20, 5, 10);
        gbc4.gridx = 0;
        gbc4.gridy = fila;
        gbc4.anchor = GridBagConstraints.EAST;
        panelFormulario.add(lblMonto, gbc4);
        
        GridBagConstraints gbc5 = new GridBagConstraints();
        gbc5.insets = new Insets(5, 10, 5, 20);
        gbc5.gridx = 1;
        gbc5.gridy = fila++;
        gbc5.anchor = GridBagConstraints.WEST;
        panelFormulario.add(lblMontoApertura, gbc5);
        
        GridBagConstraints gbc6 = new GridBagConstraints();
        gbc6.insets = new Insets(5, 20, 5, 10);
        gbc6.gridx = 0;
        gbc6.gridy = fila;
        gbc6.anchor = GridBagConstraints.EAST;
        panelFormulario.add(lblVentas, gbc6);
        
        GridBagConstraints gbc7 = new GridBagConstraints();
        gbc7.insets = new Insets(5, 10, 5, 20);
        gbc7.gridx = 1;
        gbc7.gridy = fila++;
        gbc7.anchor = GridBagConstraints.WEST;
        panelFormulario.add(lblVentasEfectivo, gbc7);
        
        GridBagConstraints gbc8 = new GridBagConstraints();
        gbc8.insets = new Insets(15, 20, 15, 20);
        gbc8.gridx = 0;
        gbc8.gridy = fila;
        gbc8.gridwidth = 2;
        gbc8.fill = GridBagConstraints.HORIZONTAL;
        panelFormulario.add(separador, gbc8);
        fila++;
        
        GridBagConstraints gbc9 = new GridBagConstraints();
        gbc9.insets = new Insets(5, 20, 5, 10);
        gbc9.gridx = 0;
        gbc9.gridy = fila;
        gbc9.anchor = GridBagConstraints.EAST;
        panelFormulario.add(lblEsperado, gbc9);
        
        GridBagConstraints gbc10 = new GridBagConstraints();
        gbc10.insets = new Insets(5, 10, 5, 20);
        gbc10.gridx = 1;
        gbc10.gridy = fila++;
        gbc10.anchor = GridBagConstraints.WEST;
        panelFormulario.add(lblMontoEsperado, gbc10);
        
        GridBagConstraints gbc11 = new GridBagConstraints();
        gbc11.insets = new Insets(5, 20, 5, 10);
        gbc11.gridx = 0;
        gbc11.gridy = fila;
        gbc11.anchor = GridBagConstraints.EAST;
        panelFormulario.add(lblFisico, gbc11);
        
        GridBagConstraints gbc12 = new GridBagConstraints();
        gbc12.insets = new Insets(5, 10, 5, 20);
        gbc12.gridx = 1;
        gbc12.gridy = fila++;
        gbc12.anchor = GridBagConstraints.WEST;
        panelFormulario.add(txtMontoFisico, gbc12);
        
        GridBagConstraints gbc13 = new GridBagConstraints();
        gbc13.insets = new Insets(5, 20, 5, 10);
        gbc13.gridx = 0;
        gbc13.gridy = fila;
        gbc13.anchor = GridBagConstraints.EAST;
        panelFormulario.add(lblDif, gbc13);
        
        GridBagConstraints gbc14 = new GridBagConstraints();
        gbc14.insets = new Insets(5, 10, 5, 20);
        gbc14.gridx = 1;
        gbc14.gridy = fila++;
        gbc14.anchor = GridBagConstraints.WEST;
        panelFormulario.add(lblDiferencia, gbc14);
        
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnCerrar);
        panelBotones.add(btnCancelar);
        
        GridBagConstraints gbc15 = new GridBagConstraints();
        gbc15.insets = new Insets(20, 10, 10, 10);
        gbc15.gridx = 0;
        gbc15.gridy = fila;
        gbc15.gridwidth = 2;
        panelFormulario.add(panelBotones, gbc15);
        fila++;
        
        GridBagConstraints gbc16 = new GridBagConstraints();
        gbc16.insets = new Insets(5, 10, 20, 10);
        gbc16.gridx = 0;
        gbc16.gridy = fila;
        gbc16.gridwidth = 2;
        panelFormulario.add(lblError, gbc16);
        
        getContentPane().add(panelFormulario, BorderLayout.CENTER);
        
        txtMontoFisico.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                calcularDiferencia();
            }
        });
        
        btnCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cerrarCaja();
            }
        });
        
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    private void cargarDatosCorte() {
        ControladorCaja.CorteInfo corte = controladorCaja.obtenerCajaAbiertaInfo();
        
        if (corte != null) {
            lblFechaApertura.setText(corte.fechaApertura != null ? corte.fechaApertura : "");
            lblUsuarioApertura.setText(corte.usuarioApertura != null ? corte.usuarioApertura : "");
            lblMontoApertura.setText("$" + String.format("%.2f", corte.montoApertura));
            lblVentasEfectivo.setText("$" + String.format("%.2f", corte.montoVentasEfectivo));
            
            montoEsperado = corte.montoApertura + corte.montoVentasEfectivo;
            lblMontoEsperado.setText("$" + String.format("%.2f", montoEsperado));
        } else {
            lblError.setText("No hay caja abierta");
        }
    }
    
    private void calcularDiferencia() {
        try {
            double montoFisico = Double.parseDouble(txtMontoFisico.getText());
            double diferencia = montoFisico - montoEsperado;
            
            if (diferencia >= 0) {
                lblDiferencia.setText("+$" + String.format("%.2f", diferencia));
                lblDiferencia.setForeground(new Color(34, 197, 94));
            } else {
                lblDiferencia.setText("-$" + String.format("%.2f", Math.abs(diferencia)));
                lblDiferencia.setForeground(new Color(220, 38, 38));
            }
        } catch (NumberFormatException e) {
            lblDiferencia.setText("$0.00");
            lblDiferencia.setForeground(Color.BLACK);
        }
    }
    
    private void cerrarCaja() {
        double montoFisico;
        try {
            montoFisico = Double.parseDouble(txtMontoFisico.getText());
            if (montoFisico < 0) {
                lblError.setText("El monto no puede ser negativo");
                return;
            }
        } catch (NumberFormatException e) {
            lblError.setText("Ingrese un monto valido");
            return;
        }
        
        double diferencia = montoFisico - montoEsperado;
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "¿Cerrar caja?\n\nMonto Esperado: $" + String.format("%.2f", montoEsperado) +
            "\nMonto Fisico: $" + String.format("%.2f", montoFisico) +
            "\nDiferencia: $" + String.format("%.2f", diferencia),
            "Confirmar Cierre", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean cerrado = controladorCaja.cerrarCaja(idCorteAbierto, montoFisico, 1);
            
            if (cerrado) {
                JOptionPane.showMessageDialog(this, "Caja cerrada correctamente");
                if (vistaPrincipal != null) {
                    vistaPrincipal.setIdCorteAbierto(-1);
                    vistaPrincipal.actualizarMontoCaja(0);
                    vistaPrincipal.deshabilitarModulosVenta();
                }
                dispose();
            } else {
                lblError.setText("Error al cerrar la caja");
            }
        }
    }
}