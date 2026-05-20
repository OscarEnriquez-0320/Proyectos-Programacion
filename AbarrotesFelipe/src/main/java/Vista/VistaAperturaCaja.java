package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import Controlador.ControladorCaja;

public class VistaAperturaCaja extends JDialog {
    private VistaPrincipal ventanaPrincipal;
    private ControladorCaja controladorCaja;
    private int idUsuario;

    private JTextField txtMontoApertura;
    private JButton btnConfirmar;
    private JLabel lblError;

    public VistaAperturaCaja(VistaPrincipal ventana, int idUsuario) {
        super(ventana, "Apertura de Caja", true);
        this.ventanaPrincipal = ventana;
        this.controladorCaja = new ControladorCaja();
        this.idUsuario = idUsuario;
        
        setSize(400, 300);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(ventana);
        setResizable(false);
        getContentPane().setLayout(new BorderLayout());
        
        iniciarComponentes();
    }
    
    private void iniciarComponentes() {
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(79, 70, 229));
        panelTitulo.setPreferredSize(new Dimension(getWidth(), 60));
        
        JLabel lblTitulo = new JLabel("APERTURA DE CAJA");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelTitulo.add(lblTitulo);
        
        getContentPane().add(panelTitulo, BorderLayout.NORTH);
        
        JPanel panelFormulario = new JPanel();
        panelFormulario.setBackground(new Color(240, 248, 255));
        panelFormulario.setLayout(new GridBagLayout());
        
        JLabel lblInstruccion = new JLabel("Ingrese el monto inicial en efectivo:");
        lblInstruccion.setFont(new Font("Arial", Font.PLAIN, 14));
        
        txtMontoApertura = new JTextField(15);
        txtMontoApertura.setFont(new Font("Arial", Font.BOLD, 20));
        txtMontoApertura.setHorizontalAlignment(JTextField.CENTER);
        txtMontoApertura.setText("0");
        
        btnConfirmar = new JButton("Confirmar Apertura");
        btnConfirmar.setBackground(new Color(34, 197, 94));
        btnConfirmar.setForeground(Color.BLACK);
        btnConfirmar.setFont(new Font("Arial", Font.BOLD, 14));
        btnConfirmar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        lblError = new JLabel("");
        lblError.setForeground(Color.RED);
        lblError.setFont(new Font("Arial", Font.BOLD, 11));
        
        GridBagConstraints gbc0 = new GridBagConstraints();
        gbc0.insets = new Insets(20, 10, 10, 10);
        gbc0.gridx = 0;
        gbc0.gridy = 0;
        gbc0.gridwidth = 2;
        panelFormulario.add(lblInstruccion, gbc0);
        
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.insets = new Insets(10, 20, 10, 20);
        gbc1.gridx = 0;
        gbc1.gridy = 1;
        gbc1.gridwidth = 2;
        gbc1.fill = GridBagConstraints.HORIZONTAL;
        panelFormulario.add(txtMontoApertura, gbc1);
        
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(20, 10, 10, 10);
        gbc2.gridx = 0;
        gbc2.gridy = 2;
        gbc2.gridwidth = 2;
        panelFormulario.add(btnConfirmar, gbc2);
        
        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.insets = new Insets(5, 10, 20, 10);
        gbc3.gridx = 0;
        gbc3.gridy = 3;
        gbc3.gridwidth = 2;
        panelFormulario.add(lblError, gbc3);
        
        getContentPane().add(panelFormulario, BorderLayout.CENTER);
        
        btnConfirmar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                confirmarApertura();
            }
        });
        
        txtMontoApertura.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    confirmarApertura();
                }
            }
        });
    }
    
    private void confirmarApertura() {
        double montoInicial;
        try {
            montoInicial = Double.parseDouble(txtMontoApertura.getText());
            if (montoInicial < 0) {
                lblError.setText("El monto no puede ser negativo");
                return;
            }
        } catch (NumberFormatException e) {
            lblError.setText("Ingrese un monto valido");
            return;
        }
        
        boolean abierto = controladorCaja.abrirCaja(idUsuario, montoInicial);
        
        if (abierto) {
            JOptionPane.showMessageDialog(this, "Caja abierta correctamente con $" + montoInicial);
            ControladorCaja.CorteInfo corte = controladorCaja.obtenerCajaAbiertaInfo();
            if (corte != null) {
                ventanaPrincipal.setIdCorteAbierto(corte.id);
                ventanaPrincipal.actualizarMontoCaja(montoInicial);
            }
            ventanaPrincipal.refrescarMenu();
            this.dispose();
        } else {
            lblError.setText("Error al abrir la caja");
        }
    }
}