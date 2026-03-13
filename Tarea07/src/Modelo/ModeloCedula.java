package Modelo;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class ModeloCedula extends DefaultTableModel {
    private String[] columnas = {
        "Academia", "Asignatura", "No. Grupos",
        "Promedio", "% Mayor al Promedio", 
        "% Reprobación", "Profesores"
    };
    
    private ArrayList<ResultadoCedula> datos;

    public ModeloCedula(ArrayList<ResultadoCedula> datos) {
        this.datos = datos;
        this.setColumnIdentifiers(columnas);
        
        for (ResultadoCedula r : datos) {
            Object[] fila = {
                r.getAcademia(),
                r.getAsignatura(),
                r.getNumeroGrupos(),
                String.format("%.2f", r.getPromedioGeneral()),
                String.format("%.2f%%", r.getPorcentajeMayorPromedio()),
                String.format("%.2f%%", r.getPorcentajeReprobacion()),
                r.getProfesores()
            };
            this.addRow(fila);
        }
    }
}