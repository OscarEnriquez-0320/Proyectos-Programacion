package Modelo;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class ModeloPromedios extends DefaultTableModel {
    private String[] columnas = {
        "Profesor", "Asignatura", "Grupo", 
        "No. Alumnos", "Promedio", "Aprobados", 
        "Reprobados", "% Aprobados", "% Reprobados", 
        "Prom. Acreditados"
    };
    
    private ArrayList<ResultadoPromedio> datos;

    public ModeloPromedios(ArrayList<ResultadoPromedio> datos) {
        this.datos = datos;
        this.setColumnIdentifiers(columnas);
        
        for (ResultadoPromedio r : datos) {
            Object[] fila = {
                r.getProfesor(),
                r.getAsignatura(),
                r.getGrupo(),
                r.getTotalAlumnos(),
                String.format("%.2f", r.getPromedioGeneral()),
                r.getAprobados(),
                r.getReprobados(),
                String.format("%.2f%%", r.getPorcentajeAprobados()),
                String.format("%.2f%%", r.getPorcentajeReprobados()),
                String.format("%.2f", r.getPromedioAcreditados())
            };
            this.addRow(fila);
        }
    }
}