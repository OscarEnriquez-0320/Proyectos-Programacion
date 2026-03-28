package Controlador;

import Modelo.*;
import Vista.PanelInstrumento;
import Vista.PanelControl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class EvaluacionControlador {

    private ManejadorDatos manejadordatos;
    private Map<String, PanelInstrumento> panelesInstrumentos;
    private JLabel statusBar;
    private Evaluacion evaluacionActual;
    private PanelControl panelControl;

    public static final String INSTRUMENTO_PI = "Producto Integrador";
    public static final String INSTRUMENTO_EP = "Evaluación de Proyectos";
    public static final String INSTRUMENTO_RD = "Rúbrica de Desempeño";

    public EvaluacionControlador(ManejadorDatos manejadordatos) {
        this.manejadordatos = manejadordatos;
        this.panelesInstrumentos = new HashMap<>();
        this.evaluacionActual = null;
    }

    public void setPanelControl(PanelControl panelControl) {
        this.panelControl = panelControl;
    }

    public void registrarPanelInstrumento(String nombre, PanelInstrumento panel) {
        panelesInstrumentos.put(nombre, panel);
    }

    public void setStatusBar(JLabel statusBar) {
        this.statusBar = statusBar;
    }

    public void cargarEvaluacion(String asignatura, String profesor, String grupo) {
        evaluacionActual = manejadordatos.buscarEvaluacion(asignatura, profesor, grupo);

        if (evaluacionActual != null) {

            for (Map.Entry<String, PanelInstrumento> entry : panelesInstrumentos.entrySet()) {
                String instrumento = entry.getKey();
                PanelInstrumento panel = entry.getValue();
                panel.cargarEvaluacion(evaluacionActual, instrumento);
                panel.setPeriodo(evaluacionActual.getPeriodo());
                panel.setActividad(evaluacionActual.getActividad());
            }

            actualizarStatusBar("✓ Evaluación cargada: " + asignatura + " - " + profesor + " - " + grupo);
            actualizarIndicadorEstatus(evaluacionActual.getEstatus());

            JOptionPane.showMessageDialog(null,
                    "Datos cargados exitosamente\n\n" +
                            "Asignatura: " + asignatura + "\n" +
                            "Profesor: " + profesor + "\n" +
                            "Grupo: " + grupo + "\n" +
                            "Período: " + evaluacionActual.getPeriodo() + "\n" +
                            "Actividad: " + evaluacionActual.getActividad() + "\n" +
                            "Estatus: " + getEstatusTexto(evaluacionActual.getEstatus()),
                    "Carga Completa",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            actualizarStatusBar("⚠ No se encontró evaluación para: " + asignatura + " - " + profesor + " - " + grupo);
            JOptionPane.showMessageDialog(null,
                    "No existe una evaluación previa para esta combinación.\n\n" +
                            "Asignatura: " + asignatura + "\n" +
                            "Profesor: " + profesor + "\n" +
                            "Grupo: " + grupo + "\n\n" +
                            "Puede crear un nuevo registro llenando los datos y presionando 'Guardar'.",
                    "No encontrado",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void nuevaEvaluacion() {
        evaluacionActual = null;
        for (PanelInstrumento panel : panelesInstrumentos.values()) {
            panel.limpiarFormulario();
        }
        if (panelControl != null) {
            panelControl.setStatusIndicator("pendiente");
        }
        actualizarStatusBar("📝 Nuevo registro - Complete los datos y presione 'Guardar'");
    }

    public void guardarEvaluacion(String asignatura, String profesor, String grupo) {

        if (asignatura == null || asignatura.trim().isEmpty() ||
                profesor == null || profesor.trim().isEmpty() ||
                grupo == null || grupo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Por favor seleccione Asignatura, Profesor y Grupo",
                    "Campos incompletos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (evaluacionActual == null) {
            evaluacionActual = new Evaluacion(asignatura, profesor, grupo);
        } else {
            evaluacionActual.setAsignatura(asignatura);
            evaluacionActual.setProfesor(profesor);
            evaluacionActual.setGrupo(grupo);
        }

        evaluacionActual.setFecha(LocalDate.now());

        for (Map.Entry<String, PanelInstrumento> entry : panelesInstrumentos.entrySet()) {
            PanelInstrumento panel = entry.getValue();
            panel.guardarCalificaciones();
        }

        evaluacionActual.getAtributos().clear();

        int totalAtributos = 0;
        String periodo = "";
        String actividad = "";

        for (Map.Entry<String, PanelInstrumento> entry : panelesInstrumentos.entrySet()) {
            String instrumento = entry.getKey();
            PanelInstrumento panel = entry.getValue();

            if (periodo.isEmpty()) {
                periodo = panel.getPeriodo();
                actividad = panel.getActividad();
            }

            DefaultTableModel model = panel.getModeloTabla();
            int rowCount = model.getRowCount();
            totalAtributos += rowCount;

            for (int i = 0; i < rowCount; i++) {
                String atributo = (String) model.getValueAt(i, 0);
                String criterio = (String) model.getValueAt(i, 1);
                String indicador = (String) model.getValueAt(i, 2);
                String calificacionStr = (String) model.getValueAt(i, 3);
                String observaciones = (String) model.getValueAt(i, 4);

                int calificacion = obtenerValorNumericoCalificacion(calificacionStr);

                AtributoEgreso attr = new AtributoEgreso(atributo, criterio, indicador);
                attr.setCalificacion(calificacion);
                attr.setObservaciones(observaciones);
                attr.setInstrumento(instrumento);

                evaluacionActual.getAtributos().add(attr);
            }
        }

        evaluacionActual.setPeriodo(periodo);
        evaluacionActual.setActividad(actividad);

        if (totalAtributos == 0) {
            evaluacionActual.setEstatus("pendiente");
        } else if (totalAtributos < 3) {
            evaluacionActual.setEstatus("incompleto");
        } else {
            evaluacionActual.setEstatus("completo");
        }

        try {
            manejadordatos.guardarEvaluacion(evaluacionActual);

            actualizarStatusBar("✓ Evaluación guardada exitosamente");
            actualizarIndicadorEstatus(evaluacionActual.getEstatus());

            JOptionPane.showMessageDialog(null,
                    "Evaluación guardada exitosamente\n\n" +
                            "Asignatura: " + asignatura + "\n" +
                            "Profesor: " + profesor + "\n" +
                            "Grupo: " + grupo + "\n" +
                            "Período: " + periodo + "\n" +
                            "Actividad: " + actividad + "\n" +
                            "Atributos evaluados: " + totalAtributos,
                    "Guardado Exitoso",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Error al guardar la evaluación:\n" + e.getMessage(),
                    "Error de Guardado",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void eliminarEvaluacion(String asignatura, String profesor, String grupo) {
        Evaluacion existente = manejadordatos.buscarEvaluacion(asignatura, profesor, grupo);

        if (existente == null) {
            JOptionPane.showMessageDialog(null,
                    "No existe una evaluación para eliminar:\n" +
                            "Asignatura: " + asignatura + "\n" +
                            "Profesor: " + profesor + "\n" +
                            "Grupo: " + grupo,
                    "No encontrado",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null,
                "¿Está seguro que desea eliminar esta evaluación?\n\n" +
                        "Asignatura: " + asignatura + "\n" +
                        "Profesor: " + profesor + "\n" +
                        "Grupo: " + grupo + "\n" +
                        "Período: " + existente.getPeriodo() + "\n" +
                        "Actividad: " + existente.getActividad() + "\n\n" +
                        "Se eliminarán:\n" +
                        "- El registro del archivo JSON\n" +
                        "- El archivo Excel asociado\n\n" +
                        "Esta acción no se puede deshacer.",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                manejadordatos.eliminarEvaluacion(asignatura, profesor, grupo);

                if (evaluacionActual != null &&
                        evaluacionActual.getAsignatura().equals(asignatura) &&
                        evaluacionActual.getProfesor().equals(profesor) &&
                        evaluacionActual.getGrupo().equals(grupo)) {
                    evaluacionActual = null;
                    for (PanelInstrumento panel : panelesInstrumentos.values()) {
                        panel.limpiarFormulario();
                    }
                }

                actualizarStatusBar("🗑 Evaluación eliminada: " + asignatura + " - " + profesor + " - " + grupo);
                if (panelControl != null) {
                    panelControl.setStatusIndicator("pendiente");
                }

                JOptionPane.showMessageDialog(null,
                        "Evaluación eliminada exitosamente",
                        "Eliminación Exitosa",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Error al eliminar la evaluación:\n" + e.getMessage(),
                        "Error de Eliminación",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void agregarAtributo(String instrumento, String atributo, String criterio,
                                String indicador, int calificacion, String observaciones) {
        if (evaluacionActual == null) {
            evaluacionActual = new Evaluacion();
            evaluacionActual.setAsignatura("Por definir");
            evaluacionActual.setProfesor("Por definir");
            evaluacionActual.setGrupo("Por definir");
        }

        AtributoEgreso attr = new AtributoEgreso(atributo, criterio, indicador);
        attr.setCalificacion(calificacion);
        attr.setObservaciones(observaciones);
        attr.setInstrumento(instrumento);

        evaluacionActual.getAtributos().add(attr);

        int total = evaluacionActual.getAtributos().size();
        if (total >= 3) {
            evaluacionActual.setEstatus("completo");
            actualizarIndicadorEstatus("completo");
        } else if (total > 0) {
            evaluacionActual.setEstatus("incompleto");
            actualizarIndicadorEstatus("incompleto");
        }

        actualizarStatusBar("✓ Atributo agregado: " + atributo);
    }

    public void eliminarAtributo(String instrumento, int indiceFila) {
        if (evaluacionActual != null) {
            int count = 0;
            for (int i = 0; i < evaluacionActual.getAtributos().size(); i++) {
                AtributoEgreso attr = evaluacionActual.getAtributos().get(i);
                if (attr.getInstrumento().equals(instrumento)) {
                    if (count == indiceFila) {
                        evaluacionActual.getAtributos().remove(i);
                        break;
                    }
                    count++;
                }
            }

            int total = evaluacionActual.getAtributos().size();
            if (total == 0) {
                evaluacionActual.setEstatus("pendiente");
                actualizarIndicadorEstatus("pendiente");
            } else if (total < 3) {
                evaluacionActual.setEstatus("incompleto");
                actualizarIndicadorEstatus("incompleto");
            }

            actualizarStatusBar("🗑 Atributo eliminado");
        }
    }

    public Evaluacion getEvaluacionActual() {
        return evaluacionActual;
    }

    public boolean existeEvaluacion(String asignatura, String profesor, String grupo) {
        return manejadordatos.buscarEvaluacion(asignatura, profesor, grupo) != null;
    }

    public java.util.List<Evaluacion> getTodasEvaluaciones() {
        return manejadordatos.getEvaluaciones();
    }

    private int obtenerValorNumericoCalificacion(String nivel) {
        if (nivel == null) return 0;
        if (nivel.contains("Excelente")) return 10;
        if (nivel.contains("Bueno")) return 9;
        if (nivel.contains("Regular")) return 7;
        return 0;
    }

    private String getEstatusTexto(String estatus) {
        switch (estatus) {
            case "pendiente":
                return "Sin iniciar";
            case "incompleto":
                return "Incompleto";
            case "completo":
                return "Completo";
            default:
                return "Desconocido";
        }
    }

    private void actualizarStatusBar(String mensaje) {
        if (statusBar != null) {
            statusBar.setText(" " + mensaje);
            Timer timer = new Timer(5000, e -> {
                if (statusBar != null && statusBar.getText().equals(" " + mensaje)) {
                    statusBar.setText(" Listo");
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    private void actualizarIndicadorEstatus(String estatus) {
        if (panelControl != null) {
            panelControl.setStatusIndicator(estatus);
        }
    }

    public String formatearFecha(LocalDate fecha) {
        if (fecha == null) return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return fecha.format(formatter);
    }
}