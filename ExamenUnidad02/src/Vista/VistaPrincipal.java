package Vista;

import javax.swing.*;
import com.formdev.flatlaf.FlatLightLaf;

import Controlador.EvaluacionControlador;

import java.awt.*;
import Modelo.*;

public class VistaPrincipal extends JFrame {
    private JTabbedPane tabbedPane;
    private PanelControl panelControl;
    private ManejadorDatos manejadordatos;
    private EvaluacionControlador controlador;
    
    public VistaPrincipal() {
        // Configurar FlatLaf
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        manejadordatos = new ManejadorDatos();
        controlador = new EvaluacionControlador(manejadordatos);
        
        initComponents();
        setupUI();
    }
    
    private void initComponents() {
        setTitle("SAE-AE - Sistema de Evaluación de Atributos de Egreso");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        
        // Panel de control superior
        panelControl = new PanelControl(controlador);
        
        // Pestañas para instrumentos
        tabbedPane = new JTabbedPane();
        
        // Crear instrumentos (basados en examen.pdf)
        tabbedPane.addTab("Producto Integrador", crearInstrumentoPanel("Producto Integrador"));
        tabbedPane.addTab("Evaluación de Proyectos", crearInstrumentoPanel("Evaluación de Proyectos"));
        tabbedPane.addTab("Rúbrica de Desempeño", crearInstrumentoPanel("Rúbrica de Desempeño"));
    }
    
    private JPanel crearInstrumentoPanel(String instrumento) {
        PanelInstrumento panel = new PanelInstrumento(instrumento, controlador);
        controlador.registrarPanelInstrumento(instrumento, panel);
        return panel;
    }
    
    private void setupUI() {
        setLayout(new BorderLayout());
        add(panelControl, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
        
        // Agregar barra de estado
        JLabel statusBar = new JLabel(" Listo");
        statusBar.setBorder(BorderFactory.createEtchedBorder());
        statusBar.setBackground(Color.LIGHT_GRAY);
        statusBar.setOpaque(true);
        add(statusBar, BorderLayout.SOUTH);
        
        controlador.setStatusBar(statusBar);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VistaPrincipal().setVisible(true);
        });
    }
}