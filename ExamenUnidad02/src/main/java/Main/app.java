package Main;

import Vista.VistaPrincipal;

import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;
import com.formdev.flatlaf.FlatLightLaf;

import Modelo.Alumno;
import Modelo.CargadorAlumnos;

public class app {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                
                FlatLightLaf.setup();
                
               
                String rutaExcel = "datos/Datosbase.xlsx";
                CargadorAlumnos.cargarDesdeExcel(rutaExcel);
            
                System.out.println("\n=== VERIFICACIÓN DE ALUMNOS ===");

              
                String asignatura = "INGENIERIA ECONOMICA";
                String profesor = "AGUILAR DIAZ LILIANA";
                String grupo = "I";

                System.out.println("Buscando alumnos para:");
                System.out.println("  Asignatura: " + asignatura);
                System.out.println("  Profesor: " + profesor);
                System.out.println("  Grupo: " + grupo);

                List<Alumno> alumnos = CargadorAlumnos.getAlumnosPorClase(asignatura, profesor, grupo);
                System.out.println("Alumnos encontrados: " + alumnos.size());

                if (alumnos.isEmpty()) {
                    System.out.println("\n⚠ No se encontraron alumnos para esa combinación.");
                    System.out.println("Mostrando TODOS los grupos disponibles:");
                    
                    Map<String, List<Alumno>> todasLasClases = CargadorAlumnos.getTodasLasClases();
                    for (Map.Entry<String, List<Alumno>> entry : todasLasClases.entrySet()) {
                        if (entry.getKey().contains(asignatura) || entry.getKey().contains(profesor)) {
                            System.out.println("  Clave: " + entry.getKey());
                            System.out.println("    Alumnos: " + entry.getValue().size());
                        }
                    }
                } else {
                    System.out.println("\n✅ Alumnos encontrados:");
                    for (Alumno a : alumnos) {
                        System.out.println("  - " + a.getNombre() + " (" + a.getMatricula() + ")");
                    }
                }
                
                if (CargadorAlumnos.isCargado()) {
                    System.out.println("📊 Total de grupos cargados: " + 
                                       CargadorAlumnos.getTodasLasClases().size());
                }
                
                
                VistaPrincipal frame = new VistaPrincipal();
                frame.setVisible(true);
                
                System.out.println("✅ Sistema SAE-AE iniciado correctamente");
                
            } catch (Exception e) {
                System.err.println("❌ Error al iniciar la aplicación:");
                e.printStackTrace();
            }
        });
    }
}