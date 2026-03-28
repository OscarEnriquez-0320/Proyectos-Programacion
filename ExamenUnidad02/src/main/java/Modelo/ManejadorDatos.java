package Modelo;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ManejadorDatos {
    private static final String JSON_FILE = "datos/evaluaciones.json";
    private static String EXCEL_DIR = "reportes/";  
    private Gson gson;
    private List<Evaluacion> evaluaciones;
    private static String carpetaSeleccionada = null;
    
    public ManejadorDatos() {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        evaluaciones = new ArrayList<>();
        crearDirectorios();
        cargarJSON();
    }
    
    private void crearDirectorios() {
        new File("datos").mkdirs();
        new File(EXCEL_DIR).mkdirs();
    }
    
    
    public static boolean seleccionarCarpetaReportes(JComponent parent) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setDialogTitle("Seleccionar carpeta para guardar reportes Excel");
        
       
        if (carpetaSeleccionada != null) {
            fileChooser.setCurrentDirectory(new File(carpetaSeleccionada));
        } else {
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        }
        
        int resultado = fileChooser.showOpenDialog(parent);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File carpeta = fileChooser.getSelectedFile();
            carpetaSeleccionada = carpeta.getAbsolutePath();
            EXCEL_DIR = carpetaSeleccionada + File.separator;
            
            // Crear carpeta si no existe
            new File(EXCEL_DIR).mkdirs();
            
            JOptionPane.showMessageDialog(parent,
                "Carpeta seleccionada:\n" + EXCEL_DIR + "\n\n" +
                "Los reportes Excel se guardarán aquí.",
                "Carpeta configurada",
                JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        
        return false;
    }
    
    public static String getCarpetaReportes() {
        return EXCEL_DIR;
    }
    
    
    public static void restablecerCarpetaPorDefecto() {
        carpetaSeleccionada = null;
        EXCEL_DIR = "reportes/";
        new File(EXCEL_DIR).mkdirs();
    }
    
    private void cargarJSON() {
        File file = new File(JSON_FILE);
        if (file.exists()) {
            try (Reader reader = new FileReader(file)) {
                Type listType = new TypeToken<List<Evaluacion>>(){}.getType();
                evaluaciones = gson.fromJson(reader, listType);
                if (evaluaciones == null) evaluaciones = new ArrayList<>();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void guardarJSON() {
        try (Writer writer = new FileWriter(JSON_FILE)) {
            gson.toJson(evaluaciones, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Evaluacion buscarEvaluacion(String asignatura, String profesor, String grupo) {
        for (Evaluacion eval : evaluaciones) {
            if (eval.getAsignatura().equals(asignatura) &&
                eval.getProfesor().equals(profesor) &&
                eval.getGrupo().equals(grupo)) {
                return eval;
            }
        }
        return null;
    }
    
    public void guardarEvaluacion(Evaluacion evaluacion) {
        Evaluacion existente = buscarEvaluacion(
            evaluacion.getAsignatura(),
            evaluacion.getProfesor(),
            evaluacion.getGrupo()
        );
        
        if (existente != null) {
            int index = evaluaciones.indexOf(existente);
            evaluaciones.set(index, evaluacion);
        } else {
            evaluaciones.add(evaluacion);
        }
        
        guardarJSON();
        generarExcel(evaluacion);
    }
    
    public void eliminarEvaluacion(String asignatura, String profesor, String grupo) {
        Evaluacion existente = buscarEvaluacion(asignatura, profesor, grupo);
        if (existente != null) {
            evaluaciones.remove(existente);
            guardarJSON();
            
            String fileName = generarNombreExcel(asignatura, profesor, grupo);
            File excelFile = new File(EXCEL_DIR + fileName);
            if (excelFile.exists()) {
                excelFile.delete();
            }
        }
    }
    
    public void generarExcel(Evaluacion evaluacion) {
        String fileName = generarNombreExcel(
            evaluacion.getAsignatura(),
            evaluacion.getProfesor(),
            evaluacion.getGrupo()
        );
        
        Workbook workbook;
        File excelFile = new File(EXCEL_DIR + fileName);
        
        try {
            if (excelFile.exists()) {
                try {
                    workbook = WorkbookFactory.create(excelFile);
                } catch (Exception e) {
                    // Si el archivo está corrupto, crear uno nuevo
                    System.err.println("Archivo Excel corrupto, creando uno nuevo: " + e.getMessage());
                    workbook = new XSSFWorkbook();
                }
            } else {
                workbook = new XSSFWorkbook();
            }
            
           
        } catch (Exception e) {
            e.printStackTrace();
            
            try {
                workbook = new XSSFWorkbook();
                
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private void crearEncabezados(Sheet sheet, Workbook workbook) {
        Row headerRow = sheet.createRow(0);
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        
        String[] headers = {"Instrumento", "Atributo de Egreso", "Criterio de Desempeño", "Indicador", "Calificación", "Observaciones"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
    }
    
    private void llenarDatosEvaluacion(Sheet sheet, Evaluacion evaluacion, Workbook workbook) {
        List<AtributoEgreso> atributos = evaluacion.getAtributos();
        
        for (int i = 0; i < atributos.size(); i++) {
            Row row = sheet.getRow(i + 1);
            if (row == null) row = sheet.createRow(i + 1);
            
            AtributoEgreso attr = atributos.get(i);
            row.createCell(0).setCellValue(attr.getInstrumento() != null ? attr.getInstrumento() : "");
            row.createCell(1).setCellValue(attr.getAtributo());
            row.createCell(2).setCellValue(attr.getCriterio());
            row.createCell(3).setCellValue(attr.getIndicador());
            row.createCell(4).setCellValue(attr.getCalificacion());
            row.createCell(5).setCellValue(attr.getObservaciones() != null ? attr.getObservaciones() : "");
        }
        
        for (int i = 0; i < 6; i++) {
            sheet.autoSizeColumn(i);
        }
    }
    
    public void generarExcelAlumnos(Evaluacion evaluacion) {
        String fileName = generarNombreExcel(
            evaluacion.getAsignatura(),
            evaluacion.getProfesor(),
            evaluacion.getGrupo()
        );
        
        Workbook workbook;
        File excelFile = new File(EXCEL_DIR + fileName);
        
        try {
            if (excelFile.exists()) {
                workbook = WorkbookFactory.create(excelFile);
            } else {
                workbook = new XSSFWorkbook();
            }
            
            Sheet sheet = workbook.getSheet("Alumnos");
            if (sheet == null) {
                sheet = workbook.createSheet("Alumnos");
            } else {
                
                int lastRow = sheet.getLastRowNum();
                for (int i = 1; i <= lastRow; i++) {
                    Row row = sheet.getRow(i);
                    if (row != null) sheet.removeRow(row);
                }
            }
            
            
            Row headerRow = sheet.createRow(0);
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            
            String[] headers = new String[2 + evaluacion.getCriterios().size() + 1];
            headers[0] = "Alumno";
            headers[1] = "Matrícula";
            for (int i = 0; i < evaluacion.getCriterios().size(); i++) {
                headers[2 + i] = evaluacion.getCriterios().get(i);
            }
            headers[2 + evaluacion.getCriterios().size()] = "Promedio";
            
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }
            
            
            List<CalificacionAlumno> alumnos = evaluacion.getCalificacionesAlumnos();
            for (int row = 0; row < alumnos.size(); row++) {
                Row dataRow = sheet.createRow(row + 1);
                CalificacionAlumno ca = alumnos.get(row);
                
                dataRow.createCell(0).setCellValue(ca.getAlumnoNombre());
                dataRow.createCell(1).setCellValue(ca.getAlumnoMatricula());
                
                for (int col = 0; col < evaluacion.getCriterios().size(); col++) {
                    dataRow.createCell(2 + col).setCellValue(ca.getCalificacion(col));
                }
                
                dataRow.createCell(2 + evaluacion.getCriterios().size()).setCellValue(ca.getPromedio());
            }
            
         
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            try (FileOutputStream fileOut = new FileOutputStream(excelFile)) {
                workbook.write(fileOut);
            }
            workbook.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String generarNombreExcel(String asignatura, String profesor, String grupo) {
        return asignatura.replace(" ", "_") + "_" +
               profesor.replace(" ", "_") + "_" +
               grupo + ".xlsx";
    }
    
    public List<Evaluacion> getEvaluaciones() { 
        return evaluaciones; 
    }
    
    private static class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
        @Override
        public JsonElement serialize(LocalDate date, Type type, JsonSerializationContext context) {
            return new JsonPrimitive(date.toString());
        }
        
        @Override
        public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
            return LocalDate.parse(json.getAsString());
        }
    }
}