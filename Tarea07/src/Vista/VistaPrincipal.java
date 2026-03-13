package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VistaPrincipal extends JFrame {
    
    private JButton btnCargarArchivo;
    private JButton btnCalcularPromedios;
    private JButton btnGenerarCedula;
    private JButton btnSalir;
    private JLabel lblArchivoCargado;
    private JPanel panelBotones;
    private JPanel panelInfo;
    
    public VistaPrincipal() {
        // Configuración básica de la ventana
        setTitle("Sistema de Análisis de Calificaciones - Cédula 3.3.2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 250);
        setLocationRelativeTo(null); // Centrar en pantalla
        setResizable(false);
        
        // Panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Panel superior con información
        panelInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelInfo.setBorder(BorderFactory.createTitledBorder("Información"));
        lblArchivoCargado = new JLabel("Archivo: No cargado");
        lblArchivoCargado.setForeground(Color.RED);
        panelInfo.add(lblArchivoCargado);
        
        // Panel central con botones
        panelBotones = new JPanel(new GridLayout(2, 2, 15, 15));
        panelBotones.setBorder(BorderFactory.createTitledBorder("Acciones"));
        
        btnCargarArchivo = new JButton("1. Cargar Archivo CSV");
        btnCalcularPromedios = new JButton("2. Calcular Promedios");
        btnGenerarCedula = new JButton("3. Generar Cédula 3.3.2");
        btnSalir = new JButton("4. Salir");
        
        // Estilos básicos
        btnCargarArchivo.setBackground(new Color(70, 130, 180));
        btnCargarArchivo.setForeground(Color.WHITE);
        btnCargarArchivo.setFont(new Font("Arial", Font.BOLD, 12));
        
        btnCalcularPromedios.setBackground(new Color(60, 179, 113));
        btnCalcularPromedios.setForeground(Color.WHITE);
        btnCalcularPromedios.setFont(new Font("Arial", Font.BOLD, 12));
        btnCalcularPromedios.setEnabled(false); // Deshabilitado hasta cargar archivo
        
        btnGenerarCedula.setBackground(new Color(255, 140, 0));
        btnGenerarCedula.setForeground(Color.WHITE);
        btnGenerarCedula.setFont(new Font("Arial", Font.BOLD, 12));
        btnGenerarCedula.setEnabled(false); // Deshabilitado hasta calcular promedios
        
        btnSalir.setBackground(new Color(220, 20, 60));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFont(new Font("Arial", Font.BOLD, 12));
        
        // Agregar botones al panel
        panelBotones.add(btnCargarArchivo);
        panelBotones.add(btnCalcularPromedios);
        panelBotones.add(btnGenerarCedula);
        panelBotones.add(btnSalir);
        
        // Agregar paneles al panel principal
        mainPanel.add(panelInfo, BorderLayout.NORTH);
        mainPanel.add(panelBotones, BorderLayout.CENTER);
        
        // Agregar panel principal a la ventana
        add(mainPanel);
    }
    
    // Métodos para manejar eventos desde el controlador
    public void addCargarArchivoListener(ActionListener listener) {
        btnCargarArchivo.addActionListener(listener);
    }
    
    public void addCalcularPromediosListener(ActionListener listener) {
        btnCalcularPromedios.addActionListener(listener);
    }
    
    public void addGenerarCedulaListener(ActionListener listener) {
        btnGenerarCedula.addActionListener(listener);
    }
    
    public void addSalirListener(ActionListener listener) {
        btnSalir.addActionListener(listener);
    }
    
    // Métodos para actualizar la interfaz
    public void setArchivoCargado(String nombreArchivo) {
        lblArchivoCargado.setText("Archivo: " + nombreArchivo);
        lblArchivoCargado.setForeground(new Color(0, 100, 0));
        btnCalcularPromedios.setEnabled(true);
    }
    
    public void setPromediosCalculados() {
        btnGenerarCedula.setEnabled(true);
    }
    
    // Getters para los botones (si se necesitan)
    public JButton getBtnCargarArchivo() { return btnCargarArchivo; }
    public JButton getBtnCalcularPromedios() { return btnCalcularPromedios; }
    public JButton getBtnGenerarCedula() { return btnGenerarCedula; }
}