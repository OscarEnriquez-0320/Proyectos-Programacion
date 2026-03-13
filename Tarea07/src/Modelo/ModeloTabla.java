package Modelo;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;


public class ModeloTabla {
	DefaultTableModel modelo;
	String columnas[]={"D.A.","P.EDUCATIVO","LETRA","PROFESOR","MATERIA","Periodo","Materia","No.ACTA","MATRICULA","ALUMNO","CALIFICACION","OP.INS.","OP.EXA.","MES","CICLO ESCOLAR"};
	
public ModeloTabla(ArrayList<String[]> datos) {
	modelo = new DefaultTableModel(columnas,0 );
	

for (String[] fila : datos) {
		modelo.addRow(fila);
	}
}

public DefaultTableModel getModelo() {
    return modelo;
}


}
