package Controlador;

import Modelo.*;
import Vista.PanelInstrumento;
import Vista.PanelControl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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
    
    // Constantes para los instrumentos
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
    
    /**
     * Cargar una evaluación existente
     */
    public void cargarEvaluacion(String asignatura, String profesor, String grupo) {
        evaluacionActual = manejadordatos.buscarEvaluacion(asignatura, profesor, grupo);
        
        if (evaluacionActual != null) {
            // Cargar datos en todos los paneles de instrumentos
            for (Map.Entry<String, PanelInstrumento> entry : panelesInstrumentos.entrySet()) {
                String instrumento = entry.getKey();
                PanelInstrumento panel = entry.getValue();
                panel.cargarEvaluacion(evaluacionActual, instrumento);
            }
            
            actualizarStatusBar("✓ Evaluación cargada: " + asignatura + " - " + profesor + " - " + grupo);
            actualizarIndicadorEstatus(evaluacionActual.getEstatus());
            
            JOptionPane.showMessageDialog(null, 
                "Datos cargados exitosamente\n" +
                "Asignatura: " + asignatura + "\n" +
                "Profesor: " + profesor + "\n" +
                "Grupo: " + grupo + "\n" +
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
    
    /**
     * Crear una nueva evaluación (limpia todos los campos)
     */
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
    
    /**
     * Guardar la evaluación actual (crear o actualizar)
     */
    public void guardarEvaluacion(String asignatura, String profesor, String grupo) {
        // Validar campos obligatorios
        if (asignatura == null || asignatura.trim().isEmpty() ||
            profesor == null || profesor.trim().isEmpty() ||
            grupo == null || grupo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                "Por favor seleccione Asignatura, Profesor y Grupo",
                "Campos incompletos",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Crear o actualizar evaluación
        if (evaluacionActual == null) {
            evaluacionActual = new Evaluacion(asignatura, profesor, grupo);
        } else {
            evaluacionActual.setAsignatura(asignatura);
            evaluacionActual.setProfesor(profesor);
            evaluacionActual.setGrupo(grupo);
        }
        
        evaluacionActual.setFecha(LocalDate.now());
        
        // Limpiar atributos existentes para evitar duplicados
        evaluacionActual.getAtributos().clear();
        
        // Recolectar datos de todos los paneles de instrumentos
        int totalAtributos = 0;
        
        for (Map.Entry<String, PanelInstrumento> entry : panelesInstrumentos.entrySet()) {
            String instrumento = entry.getKey();
            PanelInstrumento panel = entry.getValue();
            
            // Obtener datos de la tabla de rúbrica de este instrumento
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
        
        // Determinar estatus basado en datos ingresados
        if (totalAtributos == 0) {
            evaluacionActual.setEstatus("pendiente");
        } else if (totalAtributos < 3) {
            evaluacionActual.setEstatus("incompleto");
        } else {
            evaluacionActual.setEstatus("completo");
        }
        
        // Guardar en JSON y generar Excel
        try {
        	manejadordatos.guardarEvaluacion(evaluacionActual);
            
            actualizarStatusBar("✓ Evaluación guardada exitosamente - Excel generado: " + 
                generarNombreExcel(asignatura, profesor, grupo));
            actualizarIndicadorEstatus(evaluacionActual.getEstatus());
            
            JOptionPane.showMessageDialog(null,
                "Evaluación guardada exitosamente\n\n" +
                "Asignatura: " + asignatura + "\n" +
                "Profesor: " + profesor + "\n" +
                "Grupo: " + grupo + "\n" +
                "Atributos evaluados: " + totalAtributos + "\n" +
                "Estatus: " + getEstatusTexto(evaluacionActual.getEstatus()) + "\n\n" +
                "Archivo Excel generado:\n" + 
                generarNombreExcel(asignatura, profesor, grupo),
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
    
    /**
     * Eliminar una evaluación existente
     */
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
            "Grupo: " + grupo + "\n\n" +
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
                
                // Si la evaluación eliminada era la actual, limpiar formularios
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
    
    /**
     * Agregar un atributo a la evaluación actual
     */
    public void agregarAtributo(String instrumento, String atributo, String criterio, 
                                 String indicador, int calificacion, String observaciones) {
        if (evaluacionActual == null) {
            // Crear evaluación temporal con datos por defecto
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
        
        // Actualizar estatus
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
    
    /**
     * Eliminar un atributo de la evaluación actual
     */
    public void eliminarAtributo(String instrumento, int indiceFila) {
        if (evaluacionActual != null) {
            // Buscar y eliminar el atributo correspondiente
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
            
            // Actualizar estatus
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
    
    /**
     * Obtener la evaluación actual
     */
    public Evaluacion getEvaluacionActual() {
        return evaluacionActual;
    }
    
    /**
     * Verificar si existe una evaluación para la combinación dada
     */
    public boolean existeEvaluacion(String asignatura, String profesor, String grupo) {
        return manejadordatos.buscarEvaluacion(asignatura, profesor, grupo) != null;
    }
    
    /**
     * Obtener todas las evaluaciones
     */
    public java.util.List<Evaluacion> getTodasEvaluaciones() {
        return manejadordatos.getEvaluaciones();
    }
    
    /**
     * Actualizar la barra de estado
     */
    private void actualizarStatusBar(String mensaje) {
        if (statusBar != null) {
            statusBar.setText(" " + mensaje);
            // Timer para limpiar después de 5 segundos (opcional)
            Timer timer = new Timer(5000, e -> {
                if (statusBar != null && statusBar.getText().equals(" " + mensaje)) {
                    statusBar.setText(" Listo");
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }
    
    /**
     * Actualizar el indicador visual de estatus (semáforo)
     */
    private void actualizarIndicadorEstatus(String estatus) {
        if (panelControl != null) {
            panelControl.setStatusIndicator(estatus);
        }
    }
    
    /**
     * Convertir texto de calificación a valor numérico
     */
    private int obtenerValorNumericoCalificacion(String nivel) {
        if (nivel == null) return 0;
        if (nivel.contains("Excelente") || nivel.contains("10")) return 10;
        if (nivel.contains("Bueno") || nivel.contains("9")) return 9;
        if (nivel.contains("Regular") || nivel.contains("8") || nivel.contains("7")) return 7;
        if (nivel.contains("No Alcanza") || nivel.contains("6") || nivel.contains("0")) return 0;
        return 0;
    }
    
    /**
     * Obtener texto descriptivo del estatus
     */
    private String getEstatusTexto(String estatus) {
        switch (estatus) {
            case "pendiente": return "🔴 Sin iniciar";
            case "incompleto": return "🟡 Incompleto";
            case "completo": return "🟢 Completo";
            default: return "⚪ Desconocido";
        }
    }
    
    /**
     * Generar nombre de archivo Excel
     */
    private String generarNombreExcel(String asignatura, String profesor, String grupo) {
        return asignatura.replace(" ", "_") + "_" +
               profesor.replace(" ", "_") + "_" +
               grupo + ".xlsx";
    }
    
    /**
     * Formatear fecha para mostrar
     */
    public String formatearFecha(LocalDate fecha) {
        if (fecha == null) return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return fecha.format(formatter);
    }
}