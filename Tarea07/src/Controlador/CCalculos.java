package Controlador;

import Modelo.Asignaturas;
import Modelo.ResultadoPromedio;
import Modelo.ResultadoCedula;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CCalculos {
    
    private ArrayList<String[]> datosCSV;
    private ArrayList<ResultadoPromedio> resultadosPromedios;
    private ArrayList<ResultadoCedula> resultadosCedula;
    private double calificacionMinimaAprobatoria = 6.0; // Configurable

    public CCalculos(ArrayList<String[]> datosCSV) {
        this.datosCSV = datosCSV;
        this.resultadosPromedios = new ArrayList<>();
        this.resultadosCedula = new ArrayList<>();
    }

    /**
     * Método principal para calcular promedios por grupo
     */
    public ArrayList<ResultadoPromedio> calcularPromedios() {
        Map<String, ArrayList<Double>> calificacionesPorGrupo = new HashMap<>();
        Map<String, Integer> alumnosPorGrupo = new HashMap<>();
        Map<String, String> profesorPorGrupo = new HashMap<>();
        Map<String, String> asignaturaPorGrupo = new HashMap<>();
        
        // Primera pasada: recopilar datos
        for (String[] fila : datosCSV) {
            if (fila.length < 11) continue; // Validar que tenga al menos 11 columnas
            
            String profesor = fila[3]; // Columna PROFESOR
            String asignatura = fila[4]; // Columna MATERIA
            String grupo = fila[2]; // Columna LETRA
            String califStr = fila[10]; // Columna CALIFICACION
            
            // Crear clave única para el grupo
            String claveGrupo = profesor + "|" + asignatura + "|" + grupo;
            
            try {
                double calificacion = Double.parseDouble(califStr);
                
                // Almacenar calificación
                if (!calificacionesPorGrupo.containsKey(claveGrupo)) {
                    calificacionesPorGrupo.put(claveGrupo, new ArrayList<>());
                    profesorPorGrupo.put(claveGrupo, profesor);
                    asignaturaPorGrupo.put(claveGrupo, asignatura);
                }
                calificacionesPorGrupo.get(claveGrupo).add(calificacion);
                
            } catch (NumberFormatException e) {
                // Ignorar calificaciones no numéricas
            }
        }
        
        // Segunda pasada: calcular estadísticas
        resultadosPromedios.clear();
        
        for (Map.Entry<String, ArrayList<Double>> entry : calificacionesPorGrupo.entrySet()) {
            String claveGrupo = entry.getKey();
            ArrayList<Double> calificaciones = entry.getValue();
            
            String[] partes = claveGrupo.split("\\|");
            String profesor = partes[0];
            String asignatura = partes[1];
            String grupo = partes[2];
            
            ResultadoPromedio rp = new ResultadoPromedio(profesor, asignatura, grupo);
            rp.setTotalAlumnos(calificaciones.size());
            
            // Calcular promedio general
            double suma = 0;
            int aprobados = 0;
            double sumaAprobados = 0;
            
            for (Double calif : calificaciones) {
                suma += calif;
                if (calif >= calificacionMinimaAprobatoria) {
                    aprobados++;
                    sumaAprobados += calif;
                }
            }
            
            double promedioGeneral = suma / calificaciones.size();
            int reprobados = calificaciones.size() - aprobados;
            
            rp.setPromedioGeneral(promedioGeneral);
            rp.setAprobados(aprobados);
            rp.setReprobados(reprobados);
            
            // Porcentajes
            rp.setPorcentajeAprobados((double) aprobados / calificaciones.size() * 100);
            rp.setPorcentajeReprobados((double) reprobados / calificaciones.size() * 100);
            
            // Promedio de acreditados
            if (aprobados > 0) {
                rp.setPromedioAcreditados(sumaAprobados / aprobados);
            }
            
            resultadosPromedios.add(rp);
        }
        
        return resultadosPromedios;
    }

    /**
     * Método para generar datos de Cédula 3.3.2
     */
    public ArrayList<ResultadoCedula> generarCedula() {
        if (resultadosPromedios.isEmpty()) {
            calcularPromedios();
        }
        
        Map<String, ArrayList<ResultadoPromedio>> gruposPorAsignatura = new HashMap<>();
        
        // Agrupar por academia y asignatura (asumimos que academia = primera palabra de asignatura o similar)
        for (ResultadoPromedio rp : resultadosPromedios) {
            // Aquí deberías tener una forma de obtener la academia a partir de la asignatura
            // Por ahora, usaremos una lógica simple: la academia es la asignatura sin el código
            String academia = obtenerAcademiaDeAsignatura(rp.getAsignatura());
            String clave = academia + "|" + rp.getAsignatura();
            
            if (!gruposPorAsignatura.containsKey(clave)) {
                gruposPorAsignatura.put(clave, new ArrayList<>());
            }
            gruposPorAsignatura.get(clave).add(rp);
        }
        
        // Calcular estadísticas por asignatura
        resultadosCedula.clear();
        
        for (Map.Entry<String, ArrayList<ResultadoPromedio>> entry : gruposPorAsignatura.entrySet()) {
            String clave = entry.getKey();
            String[] partes = clave.split("\\|");
            String academia = partes[0];
            String asignatura = partes[1];
            
            ArrayList<ResultadoPromedio> grupos = entry.getValue();
            
            ResultadoCedula rc = new ResultadoCedula(academia, asignatura);
            rc.setNumeroGrupos(grupos.size());
            
            // Calcular promedio general de la asignatura
            double sumaPromedios = 0;
            double sumaReprobados = 0;
            double totalAlumnos = 0;
            StringBuilder profesores = new StringBuilder();
            
            for (ResultadoPromedio grupo : grupos) {
                sumaPromedios += grupo.getPromedioGeneral();
                sumaReprobados += grupo.getReprobados();
                totalAlumnos += grupo.getTotalAlumnos();
                
                if (profesores.length() > 0) {
                    profesores.append(", ");
                }
                profesores.append(grupo.getProfesor());
            }
            
            double promedioGeneralAsignatura = sumaPromedios / grupos.size();
            rc.setPromedioGeneral(promedioGeneralAsignatura);
            rc.setPorcentajeReprobacion((sumaReprobados / totalAlumnos) * 100);
            rc.setProfesores(profesores.toString());
            
            // Calcular porcentaje de grupos mayor al promedio
            int gruposMayorPromedio = 0;
            for (ResultadoPromedio grupo : grupos) {
                if (grupo.getPromedioGeneral() > promedioGeneralAsignatura) {
                    gruposMayorPromedio++;
                }
            }
            rc.setPorcentajeMayorPromedio((double) gruposMayorPromedio / grupos.size() * 100);
            
            resultadosCedula.add(rc);
        }
        
        return resultadosCedula;
    }

    /**
     * Método auxiliar para obtener academia de una asignatura
     * Esto deberías personalizarlo según tu nomenclatura
     */
    private String obtenerAcademiaDeAsignatura(String asignatura) {
        // Lógica de ejemplo: si la asignatura contiene "CALCULO" -> "CIENCIAS BASICAS"
        if (asignatura.toUpperCase().contains("CALCULO") || 
            asignatura.toUpperCase().contains("MATEMATICAS")) {
            return "CIENCIAS BASICAS";
        } else if (asignatura.toUpperCase().contains("PROGRAMACION") || 
                   asignatura.toUpperCase().contains("COMPUTACION")) {
            return "SISTEMAS";
        } else if (asignatura.toUpperCase().contains("FISICA")) {
            return "CIENCIAS BASICAS";
        } else if (asignatura.toUpperCase().contains("INGLES")) {
            return "IDIOMAS";
        }
        return "OTRAS ACADEMIAS";
    }

    public double getCalificacionMinimaAprobatoria() {
        return calificacionMinimaAprobatoria;
    }

    public void setCalificacionMinimaAprobatoria(double calificacionMinimaAprobatoria) {
        this.calificacionMinimaAprobatoria = calificacionMinimaAprobatoria;
    }
}