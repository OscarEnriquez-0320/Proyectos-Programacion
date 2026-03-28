package Vista;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import Modelo.*;
import Controlador.EvaluacionControlador;

public class PanelInstrumento extends JPanel {
    private String nombreInstrumento;
    private EvaluacionControlador controller;
    private Evaluacion evaluacionActual;
    private String instrumentoActual;
    
    // Campos del formulario
    private JTextField txtAtributo;
    private JTextField txtCriterio;
    private JTextField txtIndicador;
    private JComboBox<String> cmbCalificacion;
    private JTextArea txtObservaciones;
    private JTable tablaRubrica;
    private DefaultTableModel modeloTabla;
    private JButton btnAgregarFila;
    private JButton btnEliminarFila;
    
    // Componentes para WindowBuilder
    private JPanel panelInfo;
    private JPanel panelFormulario;
    private JPanel panelRubrica;
    private JLabel lblTitulo;
    private JLabel lblFecha;
    
    public PanelInstrumento(String nombreInstrumento, EvaluacionControlador controller) {
        this.nombreInstrumento = nombreInstrumento;
        this.instrumentoActual = nombreInstrumento;
        this.controller = controller;
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel superior
        panelInfo = new JPanel(new BorderLayout());
        lblTitulo = new JLabel(nombreInstrumento);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(0, 51, 102));
        panelInfo.add(lblTitulo, BorderLayout.WEST);
        
        lblFecha = new JLabel("Fecha: " + java.time.LocalDate.now());
        lblFecha.setFont(new Font("Arial", Font.PLAIN, 12));
        panelInfo.add(lblFecha, BorderLayout.EAST);
        add(panelInfo, BorderLayout.NORTH);
        
        // Panel formulario con GridBagLayout - CORREGIDO
        panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(new TitledBorder("Datos de Evaluación"));
        
        // Crear componentes
        txtAtributo = new JTextField(20);
        txtCriterio = new JTextField(20);
        txtIndicador = new JTextField(20);
        String[] niveles = {"Excelente (10)", "Bueno (9)", "Regular (8-7)", "No Alcanza (6-0)"};
        cmbCalificacion = new JComboBox<>(niveles);
        txtObservaciones = new JTextArea(3, 20);
        JScrollPane scrollObs = new JScrollPane(txtObservaciones);
        btnAgregarFila = new JButton("Agregar a Rúbrica");
        btnEliminarFila = new JButton("Eliminar selección");
        
        // Agregar componentes con GridBagConstraints - CADA UNO CON SU PROPIA INSTANCIA
        GridBagConstraints gbc;
        
        // Fila 0 - Atributo
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        panelFormulario.add(new JLabel("Atributo de Egreso:"), gbc);
        
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panelFormulario.add(txtAtributo, gbc);
        
        // Fila 1 - Criterio
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        panelFormulario.add(new JLabel("Criterio de Desempeño:"), gbc);
        
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panelFormulario.add(txtCriterio, gbc);
        
        // Fila 2 - Indicador
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        panelFormulario.add(new JLabel("Indicador:"), gbc);
        
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panelFormulario.add(txtIndicador, gbc);
        
        // Fila 3 - Calificación
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        panelFormulario.add(new JLabel("Calificación:"), gbc);
        
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panelFormulario.add(cmbCalificacion, gbc);
        
        // Fila 4 - Observaciones
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        panelFormulario.add(new JLabel("Observaciones:"), gbc);
        
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        panelFormulario.add(scrollObs, gbc);
        
        // Fila 5 - Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.add(btnAgregarFila);
        panelBotones.add(btnEliminarFila);
        
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelFormulario.add(panelBotones, gbc);
        
        add(panelFormulario, BorderLayout.CENTER);
        
        // Panel tabla
        panelRubrica = new JPanel(new BorderLayout());
        panelRubrica.setBorder(new TitledBorder("Rúbrica de Evaluación"));
        
        String[] columnas = {"Atributo", "Criterio", "Indicador", "Calificación", "Observaciones"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaRubrica = new JTable(modeloTabla);
        tablaRubrica.setRowHeight(25);
        JScrollPane scrollTabla = new JScrollPane(tablaRubrica);
        panelRubrica.add(scrollTabla, BorderLayout.CENTER);
        
        add(panelRubrica, BorderLayout.SOUTH);
        
        // Configurar listeners
        configurarListeners();
    }
    
    private void configurarListeners() {
        btnAgregarFila.addActionListener(e -> agregarFilaRubrica());
        btnEliminarFila.addActionListener(e -> eliminarFilaSeleccionada());
    }
    
    private void agregarFilaRubrica() {
        String atributo = txtAtributo.getText().trim();
        String criterio = txtCriterio.getText().trim();
        String indicador = txtIndicador.getText().trim();
        String calificacion = (String) cmbCalificacion.getSelectedItem();
        String observaciones = txtObservaciones.getText().trim();
        
        if (atributo.isEmpty() || criterio.isEmpty() || indicador.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor complete los campos obligatorios",
                "Campos incompletos",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        modeloTabla.addRow(new Object[]{atributo, criterio, indicador, calificacion, observaciones});
        
        int valorCalificacion = obtenerValorNumericoCalificacion(calificacion);
        controller.agregarAtributo(nombreInstrumento, atributo, criterio, indicador, valorCalificacion, observaciones);
        
        limpiarCamposFormulario();
    }
    
    private void limpiarCamposFormulario() {
        txtAtributo.setText("");
        txtCriterio.setText("");
        txtIndicador.setText("");
        txtObservaciones.setText("");
        cmbCalificacion.setSelectedIndex(0);
    }
    
    private void eliminarFilaSeleccionada() {
        int filaSeleccionada = tablaRubrica.getSelectedRow();
        if (filaSeleccionada >= 0) {
            modeloTabla.removeRow(filaSeleccionada);
            controller.eliminarAtributo(nombreInstrumento, filaSeleccionada);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Seleccione una fila para eliminar",
                "Sin selección",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private int obtenerValorNumericoCalificacion(String nivel) {
        if (nivel == null) return 0;
        if (nivel.contains("Excelente")) return 10;
        if (nivel.contains("Bueno")) return 9;
        if (nivel.contains("Regular")) return 7;
        return 0;
    }
    
    private String obtenerNivelCalificacion(int calificacion) {
        if (calificacion >= 10) return "Excelente (10)";
        if (calificacion >= 9) return "Bueno (9)";
        if (calificacion >= 7) return "Regular (8-7)";
        return "No Alcanza (6-0)";
    }
    
    public void cargarEvaluacion(Evaluacion evaluacion, String instrumento) {
        this.evaluacionActual = evaluacion;
        this.instrumentoActual = instrumento;
        
        modeloTabla.setRowCount(0);
        
        if (evaluacion != null && evaluacion.getAtributos() != null) {
            for (AtributoEgreso attr : evaluacion.getAtributos()) {
                if (instrumento.equals(attr.getInstrumento())) {
                    String calificacionStr = obtenerNivelCalificacion(attr.getCalificacion());
                    modeloTabla.addRow(new Object[]{
                        attr.getAtributo(),
                        attr.getCriterio(),
                        attr.getIndicador(),
                        calificacionStr,
                        attr.getObservaciones()
                    });
                }
            }
        }
    }
    
    public void limpiarFormulario() {
        if (modeloTabla != null) {
            modeloTabla.setRowCount(0);
        }
        limpiarCamposFormulario();
    }
    
    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }
    
    public String getInstrumentoActual() {
        return instrumentoActual;
    }
}