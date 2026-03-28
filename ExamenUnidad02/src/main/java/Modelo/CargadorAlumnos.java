package Modelo;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.util.*;

public class CargadorAlumnos {

    private static Map<String, List<Alumno>> alumnosPorClase = new HashMap<>();
    private static boolean cargado = false;

    public static void cargarDesdeExcel(String rutaArchivo) {
        alumnosPorClase.clear();

        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            System.err.println("⚠ Archivo no encontrado: " + rutaArchivo);
            System.err.println("   Creando datos de ejemplo...");
            crearDatosEjemplo();
            return;
        }

        try (FileInputStream fis = new FileInputStream(archivo);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            boolean esPrimeraFila = true;
            int filasProcesadas = 0;

            for (Row row : sheet) {
                if (esPrimeraFila) {
                    esPrimeraFila = false;
                    continue;
                }

                try {
                    String grupo = getCellValue(row.getCell(0));
                    String profesor = getCellValue(row.getCell(1));
                    String asignatura = getCellValue(row.getCell(2));
                    String matricula = getCellValue(row.getCell(3));
                    String nombre = getCellValue(row.getCell(4));

                    if (grupo.isEmpty() || profesor.isEmpty() || asignatura.isEmpty() ||
                            matricula.isEmpty() || nombre.isEmpty()) {
                        continue;
                    }

                    String claveClase = generarClave(asignatura, profesor, grupo);
                    Alumno alumno = new Alumno(matricula, nombre, grupo, asignatura, profesor);

                    if (!alumnosPorClase.containsKey(claveClase)) {
                        alumnosPorClase.put(claveClase, new ArrayList<>());
                    }

                    alumnosPorClase.get(claveClase).add(alumno);
                    filasProcesadas++;

                } catch (Exception e) {
                    System.err.println("Error procesando fila: " + e.getMessage());
                }
            }

            cargado = true;
            System.out.println("✅ Cargados " + filasProcesadas + " alumnos en " +
                    alumnosPorClase.size() + " grupos");

        } catch (Exception e) {
            System.err.println("❌ Error al cargar archivo Excel: " + e.getMessage());
            e.printStackTrace();
            crearDatosEjemplo();
        }
    }

    private static String getCellValue(Cell cell) {
        if (cell == null) return "";

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();

            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    double valor = cell.getNumericCellValue();
                    if (valor == (long) valor) {
                        return String.valueOf((long) valor);
                    }
                    return String.valueOf(valor);
                }

            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());

            default:
                return "";
        }
    }

    private static void crearDatosEjemplo() {
        System.out.println("📝 Creando datos de ejemplo para pruebas...");

        String[][] datosEjemplo = {
                {"I", "AGUILAR DIAZ LILIANA", "INGENIERIA ECONOMICA", "2223330167", "IBARRA LOREDO JUAN JESUS"},
                {"I", "AGUILAR DIAZ LILIANA", "INGENIERIA ECONOMICA", "2223330168", "IZAGUIRRE CORTES EMANUEL"},
                {"I", "AGUILAR DIAZ LILIANA", "INGENIERIA ECONOMICA", "2223330174", "MARTINEZ ACUÑA BRANDON"},
                {"G", "ALVAREZ NAVARRO EDUARDO", "PROGRAMACION AVANZADA", "2173223044", "ALEJO ROCHER ULISES ALFREDO"},
                {"G", "ALVAREZ NAVARRO EDUARDO", "PROGRAMACION AVANZADA", "2233336112", "ARIAS CASTILLO RAUL ARAM"},
                {"G", "ALVAREZ NAVARRO EDUARDO", "PROGRAMACION AVANZADA", "2233336113", "ATANASIO GOMEZ EDSON FELIPE"},
                {"I", "ALVAREZ NAVARRO EDUARDO", "PROGRAMACION AVANZADA", "2223330143", "CAMPOS HERNANDEZ OSCAR ERNESTO"},
                {"I", "ALVAREZ NAVARRO EDUARDO", "PROGRAMACION AVANZADA", "2213332141", "CARDONA RUSCA ALDO DAVID"},
        };

        for (String[] datos : datosEjemplo) {

            String grupo = datos[0];
            String profesor = datos[1];
            String asignatura = datos[2];
            String matricula = datos[3];
            String nombre = datos[4];

            String claveClase = generarClave(asignatura, profesor, grupo);
            Alumno alumno = new Alumno(matricula, nombre, grupo, asignatura, profesor);

            if (!alumnosPorClase.containsKey(claveClase)) {
                alumnosPorClase.put(claveClase, new ArrayList<>());
            }

            alumnosPorClase.get(claveClase).add(alumno);
        }

        cargado = true;
        System.out.println("✅ Creados " + alumnosPorClase.size() + " grupos con datos de ejemplo");
    }

    private static String generarClave(String asignatura, String profesor, String grupo) {
        return asignatura.trim() + "|" + profesor.trim() + "|" + grupo.trim();
    }

    public static List<Alumno> getAlumnosPorClase(String asignatura, String profesor, String grupo) {

        if (!cargado) {
            System.err.println("⚠ Datos no cargados. Ejecutar cargarDesdeExcel() primero.");
            return new ArrayList<>();
        }

        String clave = generarClave(asignatura, profesor, grupo);
        List<Alumno> alumnos = alumnosPorClase.get(clave);

        return alumnos != null ? new ArrayList<>(alumnos) : new ArrayList<>();
    }

    public static List<Equipo> crearEquipos(String asignatura, String profesor, String grupo, int tamanoEquipo) {

        List<Alumno> alumnos = getAlumnosPorClase(asignatura, profesor, grupo);
        List<Equipo> equipos = new ArrayList<>();

        if (alumnos.isEmpty()) {
            return equipos;
        }

        List<Alumno> alumnosMezclados = new ArrayList<>(alumnos);
        Collections.shuffle(alumnosMezclados);

        for (int i = 0; i < alumnosMezclados.size(); i += tamanoEquipo) {

            List<String> integrantes = new ArrayList<>();

            for (int j = i; j < i + tamanoEquipo && j < alumnosMezclados.size(); j++) {
                integrantes.add(alumnosMezclados.get(j).getNombre());
            }

            equipos.add(new Equipo(integrantes, 0));
        }

        return equipos;
    }

    public static List<String> getNombresAlumnos(String asignatura, String profesor, String grupo) {

        List<Alumno> alumnos = getAlumnosPorClase(asignatura, profesor, grupo);
        List<String> nombres = new ArrayList<>();

        for (Alumno a : alumnos) {
            nombres.add(a.getNombre());
        }

        return nombres;
    }

    public static boolean hayAlumnos(String asignatura, String profesor, String grupo) {
        return !getAlumnosPorClase(asignatura, profesor, grupo).isEmpty();
    }

    public static int getTotalAlumnos(String asignatura, String profesor, String grupo) {
        return getAlumnosPorClase(asignatura, profesor, grupo).size();
    }

    public static Map<String, List<Alumno>> getTodasLasClases() {
        return alumnosPorClase;
    }

    public static boolean isCargado() {
        return cargado;
    }
}