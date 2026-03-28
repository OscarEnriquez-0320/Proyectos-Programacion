package Vista;

import javax.swing.*;

import Controlador.EvaluacionControlador;

import java.awt.*;
import Modelo.*;
import java.util.List;

public class PanelControl extends JPanel {
    private JComboBox<String> cmbAsignatura;
    private JComboBox<String> cmbProfesor;
    private JComboBox<String> cmbGrupo;
    private JButton btnCargar;
    private JButton btnNuevo;
    private JButton btnGuardar;
    private JButton btnEliminar;
    private JLabel lblStatusIndicator;
    private EvaluacionControlador controlador;
    
   
    private String[] asignaturas = {
        "INGENIERIA ECONOMICA",
        "PROGRAMACION AVANZADA",
        "TERMODINAMICA",
        "ARQUITECTURA DE COMPUTADORAS",
        "ANALISIS DE ALGORITMOS"
    };
    
    private String[] profesores = {
        "AGUILAR DIAZ LILIANA",
        "ALVAREZ NAVARRO EDUARDO",
        "ARCOS ESPINOSA JAVIER AURELIO",
        "CASTAN ROCHA EMILIO",
        "CERVANTES CHIRINOS ERICK EMMANUEL"
    };
    
    private String[] grupos = {"G", "I", "H"};
    
    public PanelControl(EvaluacionControlador controlador) {
        this.controlador = controlador;
        initComponents();
        setupListeners();
    }
    
    private void initComponents() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        setBorder(BorderFactory.createTitledBorder("Panel de Control"));
        
        // Selectores
        add(new JLabel("Asignatura:"));
        cmbAsignatura = new JComboBox<>(asignaturas);
        cmbAsignatura.setPreferredSize(new Dimension(200, 25));
        add(cmbAsignatura);
        
        add(new JLabel("Profesor:"));
        cmbProfesor = new JComboBox<>(profesores);
        cmbProfesor.setPreferredSize(new Dimension(200, 25));
        add(cmbProfesor);
        
        add(new JLabel("Grupo:"));
        cmbGrupo = new JComboBox<>(grupos);
        cmbGrupo.setPreferredSize(new Dimension(80, 25));
        add(cmbGrupo);
        
        // Botones
        btnCargar = new JButton("Cargar Datos");
        btnCargar.setIcon(UIManager.getIcon("FileView.fileIcon"));
        add(btnCargar);
        
        btnNuevo = new JButton("Nuevo Registro");
        btnNuevo.setIcon(UIManager.getIcon("FileView.newFolderIcon"));
        add(btnNuevo);
        
        btnGuardar = new JButton("Guardar/Actualizar");
        btnGuardar.setIcon(UIManager.getIcon("FileView.floppyDriveIcon"));
        add(btnGuardar);
        
        btnEliminar = new JButton("Eliminar");
        btnEliminar.setIcon(UIManager.getIcon("FileView.deleteIcon"));
        add(btnEliminar);
        
        // Indicador de estatus (semáforo)
        lblStatusIndicator = new JLabel("●");
        lblStatusIndicator.setFont(new Font("Arial", Font.BOLD, 20));
        lblStatusIndicator.setForeground(Color.RED);
        lblStatusIndicator.setToolTipText("Sin iniciar");
        add(Box.createHorizontalStrut(20));
        add(new JLabel("Estatus:"));
        add(lblStatusIndicator);
        
        
        
        JButton btnSeleccionarCarpeta = new JButton("📁 Carpeta Reportes");
        btnSeleccionarCarpeta.setToolTipText("Seleccionar carpeta donde guardar los archivos Excel");
        btnSeleccionarCarpeta.addActionListener(e -> {
            ManejadorDatos.seleccionarCarpetaReportes(this);
        });
        add(btnSeleccionarCarpeta);
    }
    
    private void setupListeners() {
        btnCargar.addActionListener(e -> {
            String asignatura = (String) cmbAsignatura.getSelectedItem();
            String profesor = (String) cmbProfesor.getSelectedItem();
            String grupo = (String) cmbGrupo.getSelectedItem();
            controlador.cargarEvaluacion(asignatura, profesor, grupo);
        });
        
        btnNuevo.addActionListener(e -> {
            controlador.nuevaEvaluacion();
            limpiarSeleccion();
        });
        
        btnGuardar.addActionListener(e -> {
            String asignatura = (String) cmbAsignatura.getSelectedItem();
            String profesor = (String) cmbProfesor.getSelectedItem();
            String grupo = (String) cmbGrupo.getSelectedItem();
            controlador.guardarEvaluacion(asignatura, profesor, grupo);
        });
        
        btnEliminar.addActionListener(e -> {
            String asignatura = (String) cmbAsignatura.getSelectedItem();
            String profesor = (String) cmbProfesor.getSelectedItem();
            String grupo = (String) cmbGrupo.getSelectedItem();
            controlador.eliminarEvaluacion(asignatura, profesor, grupo);
        });
    }
    
    private void limpiarSeleccion() {
        cmbAsignatura.setSelectedIndex(0);
        cmbProfesor.setSelectedIndex(0);
        cmbGrupo.setSelectedIndex(0);
    }
    
    public void setStatusIndicator(String estatus) {
        switch (estatus) {
            case "pendiente":
                lblStatusIndicator.setForeground(Color.RED);
                lblStatusIndicator.setToolTipText("Sin iniciar");
                break;
            case "incompleto":
                lblStatusIndicator.setForeground(Color.ORANGE);
                lblStatusIndicator.setToolTipText("Incompleto");
                break;
            case "completo":
                lblStatusIndicator.setForeground(Color.GREEN);
                lblStatusIndicator.setToolTipText("Completo - Excel generado");
                break;
        }
    }
    
    public String getAsignaturaSeleccionada() {
        return (String) cmbAsignatura.getSelectedItem();
    }
    
    public String getProfesorSeleccionado() {
        return (String) cmbProfesor.getSelectedItem();
    }
    
    public String getGrupoSeleccionado() {
        return (String) cmbGrupo.getSelectedItem();
    }
}