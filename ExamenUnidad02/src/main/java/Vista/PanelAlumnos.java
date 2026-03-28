package Vista;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;
import Modelo.*;

public class PanelAlumnos extends JPanel {

    private Evaluacion evaluacionActual;
    private JTable tablaAlumnos;
    private DefaultTableModel modeloTabla;
    private JLabel lblEstadisticas;

    private class PromedioCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {

            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (!isSelected && evaluacionActual != null &&
                    evaluacionActual.getCalificacionesAlumnos() != null &&
                    row < evaluacionActual.getCalificacionesAlumnos().size()) {

                CalificacionAlumno ca = evaluacionActual.getCalificacionesAlumnos().get(row);

                if (column == table.getColumnCount() - 1) {
                    if (ca.isReprobado()) {
                        c.setBackground(new Color(255, 182, 182));
                        c.setForeground(Color.BLACK);
                    } else {
                        c.setBackground(new Color(144, 238, 144));
                        c.setForeground(Color.BLACK);
                    }
                } else {
                    c.setBackground(Color.WHITE);
                }
            }

            return c;
        }
    }

    public PanelAlumnos() {
        initComponents();
    }

    private void initComponents() {

        setLayout(new BorderLayout(5, 5));
        setBorder(new TitledBorder("Evaluación por Alumno"));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAgregarCriterio = new JButton("+ Agregar Criterio");
        JButton btnEliminarCriterio = new JButton("- Eliminar Criterio");

        btnAgregarCriterio.addActionListener(e -> agregarCriterio());
        btnEliminarCriterio.addActionListener(e -> eliminarCriterio());

        panelBotones.add(btnAgregarCriterio);
        panelBotones.add(btnEliminarCriterio);

        add(panelBotones, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel();
        tablaAlumnos = new JTable(modeloTabla);
        tablaAlumnos.setRowHeight(25);

        tablaAlumnos.setDefaultEditor(Double.class, new DefaultCellEditor(new JTextField()) {
            @Override
            public Object getCellEditorValue() {
                String valor = super.getCellEditorValue().toString();
                try {
                    double d = Double.parseDouble(valor);
                    return Math.max(0, Math.min(10, d));
                } catch (NumberFormatException e) {
                    return 0.0;
                }
            }
        });

        JScrollPane scrollTabla = new JScrollPane(tablaAlumnos);
        add(scrollTabla, BorderLayout.CENTER);

        JPanel panelEstadisticas = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelEstadisticas.setBorder(new TitledBorder("Estadísticas"));

        lblEstadisticas = new JLabel("Total alumnos: 0 | Promedio general: 0.00 | Reprobados: 0");
        panelEstadisticas.add(lblEstadisticas);

        add(panelEstadisticas, BorderLayout.SOUTH);

        tablaAlumnos.getModel().addTableModelListener(e -> {
            if (e.getType() == javax.swing.event.TableModelEvent.UPDATE) {
                actualizarEstadisticas();
            }
        });
    }

    public void cargarEvaluacion(Evaluacion evaluacion) {

        this.evaluacionActual = evaluacion;

        if (evaluacion == null || evaluacion.getCalificacionesAlumnos() == null ||
                evaluacion.getCalificacionesAlumnos().isEmpty()) {

            modeloTabla.setRowCount(0);
            modeloTabla.setColumnCount(0);

            lblEstadisticas.setText("Total alumnos: 0 | Promedio general: 0.00 | Reprobados: 0");

            return;
        }

        modeloTabla.setColumnCount(0);

        modeloTabla.addColumn("Alumno");
        modeloTabla.addColumn("Matrícula");

        List<String> criterios = evaluacion.getCriterios();

        for (String criterio : criterios) {
            modeloTabla.addColumn(criterio);
        }

        modeloTabla.addColumn("Promedio");

        List<CalificacionAlumno> alumnos = evaluacion.getCalificacionesAlumnos();

        modeloTabla.setRowCount(0);

        for (CalificacionAlumno ca : alumnos) {

            Object[] fila = new Object[2 + criterios.size() + 1];

            fila[0] = ca.getAlumnoNombre();
            fila[1] = ca.getAlumnoMatricula();

            for (int i = 0; i < criterios.size(); i++) {
                fila[2 + i] = ca.getCalificacion(i);
            }

            fila[2 + criterios.size()] = String.format("%.2f", ca.getPromedio());

            modeloTabla.addRow(fila);
        }

        int promedioCol = 2 + criterios.size();
        tablaAlumnos.getColumnModel().getColumn(promedioCol).setCellRenderer(new PromedioCellRenderer());

        for (int i = 2; i < 2 + criterios.size(); i++) {

            tablaAlumnos.getColumnModel().getColumn(i).setCellEditor(
                    new DefaultCellEditor(new JTextField()) {

                        @Override
                        public Object getCellEditorValue() {

                            String valor = super.getCellEditorValue().toString();

                            try {
                                double d = Double.parseDouble(valor);
                                return Math.max(0, Math.min(10, d));
                            } catch (NumberFormatException e) {
                                return 0.0;
                            }
                        }
                    });
        }

        actualizarEstadisticas();
    }

    public void guardarCalificaciones() {

        if (evaluacionActual == null) return;

        List<CalificacionAlumno> alumnos = evaluacionActual.getCalificacionesAlumnos();
        List<String> criterios = evaluacionActual.getCriterios();

        for (int row = 0; row < alumnos.size() && row < modeloTabla.getRowCount(); row++) {

            CalificacionAlumno ca = alumnos.get(row);

            for (int col = 0; col < criterios.size(); col++) {

                Object valor = modeloTabla.getValueAt(row, 2 + col);

                double calif = 0;

                if (valor != null) {
                    try {
                        calif = Double.parseDouble(valor.toString());
                        calif = Math.max(0, Math.min(10, calif));
                    } catch (NumberFormatException e) {
                        calif = 0;
                    }
                }

                ca.setCalificacion(col, calif);
            }
        }

        for (int row = 0; row < alumnos.size(); row++) {

            CalificacionAlumno ca = alumnos.get(row);

            modeloTabla.setValueAt(
                    String.format("%.2f", ca.getPromedio()),
                    row,
                    2 + criterios.size()
            );
        }

        actualizarEstadisticas();
    }

    private void agregarCriterio() {

        if (evaluacionActual == null) {

            JOptionPane.showMessageDialog(this,
                    "Primero debe seleccionar una asignatura, profesor y grupo",
                    "Sin evaluación",
                    JOptionPane.WARNING_MESSAGE);

            return;
        }

        String nuevoCriterio = JOptionPane.showInputDialog(this,
                "Nombre del nuevo criterio:",
                "Agregar Criterio",
                JOptionPane.QUESTION_MESSAGE);

        if (nuevoCriterio != null && !nuevoCriterio.trim().isEmpty()) {

            evaluacionActual.agregarCriterio(nuevoCriterio.trim());

            cargarEvaluacion(evaluacionActual);
        }
    }

    private void eliminarCriterio() {

        if (evaluacionActual == null) {
            return;
        }

        List<String> criterios = evaluacionActual.getCriterios();

        if (criterios.isEmpty()) {

            JOptionPane.showMessageDialog(this, "No hay criterios para eliminar");

            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Eliminar el criterio '" + criterios.get(criterios.size() - 1) + "'?\n" +
                        "Se perderán las calificaciones de este criterio.",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {

            criterios.remove(criterios.size() - 1);

            for (CalificacionAlumno ca : evaluacionActual.getCalificacionesAlumnos()) {

                double[] nuevas = new double[criterios.size()];
                double[] viejas = ca.getCalificaciones();

                for (int i = 0; i < nuevas.length && i < viejas.length; i++) {
                    nuevas[i] = viejas[i];
                }

                for (int i = 0; i < nuevas.length; i++) {
                    ca.setCalificacion(i, nuevas[i]);
                }
            }

            cargarEvaluacion(evaluacionActual);
        }
    }

    private void actualizarEstadisticas() {

        if (evaluacionActual == null || evaluacionActual.getCalificacionesAlumnos().isEmpty()) {

            lblEstadisticas.setText("Total alumnos: 0 | Promedio general: 0.00 | Reprobados: 0");

            return;
        }

        String texto = String.format(
                "Total alumnos: %d | Promedio general: %.2f | Reprobados: %d",
                evaluacionActual.getCalificacionesAlumnos().size(),
                evaluacionActual.getPromedioGeneral(),
                evaluacionActual.getNumReprobados()
        );

        lblEstadisticas.setText(texto);

        tablaAlumnos.repaint();
    }

    public void limpiar() {

        modeloTabla.setRowCount(0);
        modeloTabla.setColumnCount(0);

        evaluacionActual = null;

        lblEstadisticas.setText("Total alumnos: 0 | Promedio general: 0.00 | Reprobados: 0");
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }

    public JTable getTablaAlumnos() {
        return tablaAlumnos;
    }
}