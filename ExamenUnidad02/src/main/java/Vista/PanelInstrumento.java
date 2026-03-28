package Vista;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import Modelo.*;
import Controlador.EvaluacionControlador;

public class PanelInstrumento extends JPanel {
    private String nombreInstrumento;
    private EvaluacionControlador controller;
    private Evaluacion evaluacionActual;
    private String instrumentoActual;

    private JTextField txtAtributo;
    private JTextField txtCriterio;
    private JTextField txtIndicador;
    private JComboBox<String> cmbCalificacion;
    private JTextArea txtObservaciones;
    private JTable tablaRubrica;
    private DefaultTableModel modeloTabla;
    private JButton btnAgregarFila;
    private JButton btnEliminarFila;

    private JComboBox<String> cmbPeriodo;

    private PanelAlumnos panelAlumnos;

    private class CalificacionCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {

            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (!isSelected) {
                String calificacion = "";

                if (column == 3) {
                    calificacion = value != null ? value.toString() : "";
                } else {
                    Object califObj = table.getValueAt(row, 3);
                    calificacion = califObj != null ? califObj.toString() : "";
                }

                if (calificacion.contains("Excelente")) {
                    c.setBackground(new Color(144, 238, 144));
                } else if (calificacion.contains("Bueno")) {
                    c.setBackground(new Color(173, 216, 230));
                } else if (calificacion.contains("Regular")) {
                    c.setBackground(new Color(255, 255, 153));
                } else if (calificacion.contains("No Alcanza")) {
                    c.setBackground(new Color(255, 182, 182));
                } else {
                    c.setBackground(Color.WHITE);
                }
            }

            return c;
        }
    }

    public PanelInstrumento(String nombreInstrumento, EvaluacionControlador controller) {
        this.nombreInstrumento = nombreInstrumento;
        this.instrumentoActual = nombreInstrumento;
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelSuperior.setBorder(new TitledBorder("Información General"));

        panelSuperior.add(new JLabel("Período:"));

        int anioActual = java.time.Year.now().getValue();
        String[] periodos = {
            anioActual + "-1",
            anioActual + "-2",
            anioActual + "-3"
        };
        cmbPeriodo = new JComboBox<>(periodos);
        cmbPeriodo.setPreferredSize(new Dimension(100, 25));
        panelSuperior.add(cmbPeriodo);

        add(panelSuperior, BorderLayout.NORTH);

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(new TitledBorder("Datos de Evaluación"));

        txtAtributo = new JTextField(20);
        txtCriterio = new JTextField(20);
        txtIndicador = new JTextField(20);
        String[] niveles = {"Excelente (10)", "Bueno (9)", "Regular (8-7)", "No Alcanza (6-0)"};
        cmbCalificacion = new JComboBox<>(niveles);
        txtObservaciones = new JTextArea(2, 15);
        JScrollPane scrollObs = new JScrollPane(txtObservaciones);
        btnAgregarFila = new JButton("Agregar a Rúbrica");
        btnEliminarFila = new JButton("Eliminar selección");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 5, 3, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 0;
        panelFormulario.add(new JLabel("Atributo de Egreso:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        panelFormulario.add(txtAtributo, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        gbc.weightx = 0;
        panelFormulario.add(new JLabel("Criterio de Desempeño:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        panelFormulario.add(txtCriterio, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.weightx = 0;
        panelFormulario.add(new JLabel("Indicador:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        panelFormulario.add(txtIndicador, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.weightx = 0;
        panelFormulario.add(new JLabel("Criterio de Evaluación:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        panelFormulario.add(cmbCalificacion, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        panelFormulario.add(new JLabel("Observaciones:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 0.3;
        panelFormulario.add(scrollObs, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.add(btnAgregarFila);
        panelBotones.add(btnEliminarFila);

        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;
        panelFormulario.add(panelBotones, gbc);

        add(panelFormulario, BorderLayout.CENTER);

        JPanel panelRubrica = new JPanel(new BorderLayout());
        panelRubrica.setBorder(new TitledBorder("Rúbrica de Atributos"));

        String[] columnas = {"Atributo", "Criterio", "Indicador", "Calificación", "Observaciones"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaRubrica = new JTable(modeloTabla);
        tablaRubrica.setRowHeight(22);

        CalificacionCellRenderer colorRenderer = new CalificacionCellRenderer();
        for (int i = 0; i < tablaRubrica.getColumnCount(); i++) {
            tablaRubrica.getColumnModel().getColumn(i).setCellRenderer(colorRenderer);
        }

        tablaRubrica.getColumnModel().getColumn(0).setPreferredWidth(150);
        tablaRubrica.getColumnModel().getColumn(1).setPreferredWidth(150);
        tablaRubrica.getColumnModel().getColumn(2).setPreferredWidth(150);
        tablaRubrica.getColumnModel().getColumn(3).setPreferredWidth(90);
        tablaRubrica.getColumnModel().getColumn(4).setPreferredWidth(120);

        tablaRubrica.setPreferredScrollableViewportSize(new Dimension(800, 120));

        JScrollPane scrollTabla = new JScrollPane(tablaRubrica);
        panelRubrica.add(scrollTabla, BorderLayout.CENTER);

        panelAlumnos = new PanelAlumnos();
        panelAlumnos.setVisible(false);

        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.add(panelRubrica, BorderLayout.CENTER);
        panelSur.add(panelAlumnos, BorderLayout.SOUTH);

        add(panelSur, BorderLayout.SOUTH);

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
                "Complete: Atributo, Criterio e Indicador",
                "Campos incompletos",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        modeloTabla.addRow(new Object[]{atributo, criterio, indicador, calificacion, observaciones});

        int valorCalificacion = obtenerValorNumericoCalificacion(calificacion);
        controller.agregarAtributo(nombreInstrumento, atributo, criterio, indicador,
                                   valorCalificacion, observaciones);

        limpiarCamposFormulario();
        tablaRubrica.repaint();
    }

    private void limpiarCamposFormulario() {
        txtAtributo.setText("");
        txtCriterio.setText("");
        txtIndicador.setText("");
        txtObservaciones.setText("");
        cmbCalificacion.setSelectedIndex(0);
        txtAtributo.requestFocus();
    }

    private void eliminarFilaSeleccionada() {
        int fila = tablaRubrica.getSelectedRow();
        if (fila >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this,
                "¿Eliminar este atributo?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                modeloTabla.removeRow(fila);
                controller.eliminarAtributo(nombreInstrumento, fila);
                tablaRubrica.repaint();
            }
        } else {
            JOptionPane.showMessageDialog(this,
                "Seleccione una fila",
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

        if (evaluacion != null && evaluacion.getPeriodo() != null) {
            cmbPeriodo.setSelectedItem(evaluacion.getPeriodo());
        }

        modeloTabla.setRowCount(0);

        if (evaluacion != null && evaluacion.getAtributos() != null) {
            for (AtributoEgreso attr : evaluacion.getAtributos()) {
                if (instrumento.equals(attr.getInstrumento())) {
                    modeloTabla.addRow(new Object[]{
                        attr.getAtributo(),
                        attr.getCriterio(),
                        attr.getIndicador(),
                        obtenerNivelCalificacion(attr.getCalificacion()),
                        attr.getObservaciones()
                    });
                }
            }
        }

        if (instrumento.equals("Rúbrica de Desempeño")) {
            panelAlumnos.setVisible(true);
            panelAlumnos.cargarEvaluacion(evaluacion);
        } else {
            panelAlumnos.setVisible(false);
        }

        tablaRubrica.repaint();
    }

    public void guardarCalificaciones() {
        if (panelAlumnos != null && panelAlumnos.isVisible()) {
            panelAlumnos.guardarCalificaciones();
        }
    }

    public void limpiarFormulario() {
        if (modeloTabla != null) {
            modeloTabla.setRowCount(0);
        }
        limpiarCamposFormulario();
        int anioActual = java.time.Year.now().getValue();
        cmbPeriodo.setSelectedItem(anioActual + "-1");

        if (panelAlumnos != null) {
            panelAlumnos.limpiar();
            panelAlumnos.setVisible(false);
        }

        tablaRubrica.repaint();
    }

    public String getPeriodo() {
        return (String) cmbPeriodo.getSelectedItem();
    }

    public void setPeriodo(String periodo) {
        if (periodo != null) {
            cmbPeriodo.setSelectedItem(periodo);
        }
    }

    public String getActividad() {
        return nombreInstrumento;
    }

    public void setActividad(String actividad) {}

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }

    public String getInstrumentoActual() {
        return instrumentoActual;
    }
}