package Controlador;

import Librerias.Libreria;
import Modelo.ModeloPromedios;
import Modelo.ModeloCedula;
import Modelo.ResultadoPromedio;
import Modelo.ResultadoCedula;
import Vista.VistaPrincipal;
import Vista.VistaPromedios;
import Vista.VistaCedula;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CPrincipal {
    
    private VistaPrincipal vista;
    private ArrayList<String[]> datosCSV;
    private CCalculos calculos;
    private ArrayList<ResultadoPromedio> resultadosPromedios;
    private ArrayList<ResultadoCedula> resultadosCedula;
    
    private String archivoPorDefecto = "ReporteInscripcionCalificaciones_2025_1.csv";
    
    public CPrincipal() {
        // Inicializar vistas y datos
        vista = new VistaPrincipal();
        datosCSV = new ArrayList<>();
        resultadosPromedios = new ArrayList<>();
        resultadosCedula = new ArrayList<>();
        
        // Configurar listeners
        configurarListeners();
        
        // Mostrar vista
        vista.setVisible(true);
        
        // Intentar cargar archivo por defecto al iniciar
        cargarArchivoPorDefecto();
    }
    
    private void configurarListeners() {
        // Listener para botón Cargar Archivo
        vista.addCargarArchivoListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarArchivo();
            }
        });
        
        // Listener para botón Calcular Promedios
        vista.addCalcularPromediosListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularPromedios();
            }
        });
        
        // Listener para botón Generar Cédula
        vista.addGenerarCedulaListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarCedula();
            }
        });
        
        // Listener para botón Salir
        vista.addSalirListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opcion = JOptionPane.showConfirmDialog(
                    vista, 
                    "¿Está seguro que desea salir?", 
                    "Confirmar salida", 
                    JOptionPane.YES_NO_OPTION
                );
                if (opcion == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }
    
    private void cargarArchivoPorDefecto() {
        if (Libreria.ExisteArchivo(archivoPorDefecto)) {
            datosCSV = Libreria.LeerDatosCSV(archivoPorDefecto);
            if (!datosCSV.isEmpty()) {
                vista.setArchivoCargado(archivoPorDefecto);
                JOptionPane.showMessageDialog(
                    vista, 
                    "Archivo cargado exitosamente.\nTotal de registros: " + datosCSV.size(),
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        }
    }
    
    private void cargarArchivo() {
        JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.setDialogTitle("Seleccionar archivo CSV");
        
        int resultado = fileChooser.showOpenDialog(vista);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            String rutaArchivo = fileChooser.getSelectedFile().getAbsolutePath();
            
            if (Libreria.ExisteArchivo(rutaArchivo)) {
                datosCSV = Libreria.LeerDatosCSV(rutaArchivo);
                
                if (!datosCSV.isEmpty()) {
                    vista.setArchivoCargado(fileChooser.getSelectedFile().getName());
                    JOptionPane.showMessageDialog(
                        vista, 
                        "Archivo cargado exitosamente.\nTotal de registros: " + datosCSV.size(),
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    JOptionPane.showMessageDialog(
                        vista, 
                        "El archivo está vacío o no tiene datos válidos.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            } else {
                JOptionPane.showMessageDialog(
                    vista, 
                    "No se pudo encontrar el archivo.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
    
    private void calcularPromedios() {
        if (datosCSV.isEmpty()) {
            JOptionPane.showMessageDialog(
                vista, 
                "Primero debe cargar un archivo CSV.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        
        // Mostrar indicador de progreso
        JDialog dialog = new JDialog(vista, "Procesando...", true);
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        dialog.add(progressBar);
        dialog.setSize(300, 80);
        dialog.setLocationRelativeTo(vista);
        
        // Ejecutar cálculos en un hilo separado
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                calculos = new CCalculos(datosCSV);
                resultadosPromedios = calculos.calcularPromedios();
                return null;
            }
            
            @Override
            protected void done() {
                dialog.dispose();
                
                if (!resultadosPromedios.isEmpty()) {
                    vista.setPromediosCalculados();
                    
                    // Mostrar ventana de resultados
                    mostrarVistaPromedios();
                    
                    JOptionPane.showMessageDialog(
                        vista, 
                        "Cálculo de promedios completado.\nTotal de grupos: " + resultadosPromedios.size(),
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    JOptionPane.showMessageDialog(
                        vista, 
                        "No se pudieron calcular los promedios.\nVerifique el formato del archivo.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        };
        
        worker.execute();
        dialog.setVisible(true);
    }
    
    private void mostrarVistaPromedios() {
        VistaPromedios vistaPromedios = new VistaPromedios();
        ModeloPromedios modelo = new ModeloPromedios(resultadosPromedios);
        vistaPromedios.setModelo(modelo);
        
        // Listener para cerrar
        vistaPromedios.addCerrarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vistaPromedios.dispose();
            }
        });
        
        vistaPromedios.setVisible(true);
    }
    
    private void generarCedula() {
        if (resultadosPromedios.isEmpty()) {
            JOptionPane.showMessageDialog(
                vista, 
                "Primero debe calcular los promedios.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        
        // Mostrar indicador de progreso
        JDialog dialog = new JDialog(vista, "Generando cédula...", true);
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        dialog.add(progressBar);
        dialog.setSize(300, 80);
        dialog.setLocationRelativeTo(vista);
        
        // Ejecutar en hilo separado
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                if (calculos == null) {
                    calculos = new CCalculos(datosCSV);
                }
                resultadosCedula = calculos.generarCedula();
                return null;
            }
            
            @Override
            protected void done() {
                dialog.dispose();
                
                if (!resultadosCedula.isEmpty()) {
                    // Mostrar ventana de resultados
                    mostrarVistaCedula();
                } else {
                    JOptionPane.showMessageDialog(
                        vista, 
                        "No se pudo generar la cédula.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        };
        
        worker.execute();
        dialog.setVisible(true);
    }
    
    private void mostrarVistaCedula() {
        VistaCedula vistaCedula = new VistaCedula();
        ModeloCedula modelo = new ModeloCedula(resultadosCedula);
        vistaCedula.setModelo(modelo);
        
        // Listener para cerrar
        vistaCedula.addCerrarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vistaCedula.dispose();
            }
        });
        
        // Listener para exportar
        vistaCedula.addExportarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportarCedula(vistaCedula);
            }
        });
        
        vistaCedula.setVisible(true);
    }
    
    private void exportarCedula(VistaCedula vistaCedula) {
        JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.setDialogTitle("Guardar cédula como CSV");
        fileChooser.setSelectedFile(new java.io.File("cedula_332.csv"));
        
        int resultado = fileChooser.showSaveDialog(vistaCedula);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            String ruta = fileChooser.getSelectedFile().getAbsolutePath();
            if (!ruta.toLowerCase().endsWith(".csv")) {
                ruta += ".csv";
            }
            
            // Construir contenido CSV
            StringBuilder contenido = new StringBuilder();
            // Encabezados
            contenido.append("Academia,Asignatura,No.Grupos,Promedio,% Mayor al Promedio,% Reprobación,Profesores\n");
            
            // Datos
            for (ResultadoCedula rc : resultadosCedula) {
                contenido.append(rc.getAcademia()).append(",")
                         .append(rc.getAsignatura()).append(",")
                         .append(rc.getNumeroGrupos()).append(",")
                         .append(String.format("%.2f", rc.getPromedioGeneral())).append(",")
                         .append(String.format("%.2f", rc.getPorcentajeMayorPromedio())).append(",")
                         .append(String.format("%.2f", rc.getPorcentajeReprobacion())).append(",")
                         .append(rc.getProfesores()).append("\n");
            }
            
            Libreria.EscribirArchivoCSV(ruta, contenido.toString(), false);
            
            JOptionPane.showMessageDialog(
                vistaCedula,
                "Archivo exportado exitosamente:\n" + ruta,
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
    
    public static void main(String[] args) {
        // Iniciar la aplicación
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CPrincipal();
            }
        });
    }
}