package Libreria;

import java.io.*;
import java.util.ArrayList;

/**
 * Clase para manejo de archivos de texto plano
 * Utilizada en todas las prácticas para persistencia de datos
 */
public class Archivotxt {
    
    private String nombreArchivo;
    private String rutaCompleta;
    
    /**
     * Constructor que asume la extensión .txt
     * @param nombre Nombre base del archivo (sin extensión)
     */
    public Archivotxt(String nombre) {
        this.nombreArchivo = nombre + ".txt";
        this.rutaCompleta = this.nombreArchivo; // En el directorio del proyecto
    }
    
    /**
     * Constructor con nombre completo de archivo
     * @param nombreArchivo Nombre completo del archivo (con extensión)
     * @param completo Bandera para diferenciar constructores
     */
    public Archivotxt(String nombreArchivo, boolean completo) {
        this.nombreArchivo = nombreArchivo;
        this.rutaCompleta = this.nombreArchivo;
    }
    
    /**
     * Verifica si el archivo existe
     * @return true si existe, false en caso contrario
     */
    public boolean existe() {
        File archivo = new File(this.nombreArchivo);
        return archivo.exists();
    }
    
    /**
     * Carga el contenido del archivo y lo devuelve como ArrayList de String[]
     * Cada String[] representa una línea dividida por el separador ";"
     * @return ArrayList con los datos o null si el archivo no existe
     */
    public ArrayList<String[]> cargar() {
        ArrayList<String[]> lineas = new ArrayList<>();
        
        if (this.existe()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    // Ignorar líneas vacías
                    if (!linea.trim().isEmpty()) {
                        String[] elementos = linea.split(";");
                        lineas.add(elementos);
                    }
                }
                System.out.println("Archivo " + nombreArchivo + " cargado correctamente. " + 
                                   lineas.size() + " registros.");
            } catch (IOException e) {
                System.err.println("Error al cargar el archivo " + nombreArchivo + ": " + e.getMessage());
                return null;
            }
        } else {
            System.out.println("El archivo " + nombreArchivo + " no existe. Se creará al guardar.");
            lineas = null;
        }
        
        return lineas;
    }
    
    /**
     * Guarda una cadena de texto en el archivo (sobrescribe)
     * @param contenido Texto a guardar
     * @return true si se guardó correctamente, false en caso de error
     */
    public boolean guardar(String contenido) {
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            writer.write(contenido);
            System.out.println("Archivo " + nombreArchivo + " guardado correctamente.");
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo " + nombreArchivo + ": " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Agrega contenido al final del archivo
     * @param contenido Texto a agregar
     * @return true si se agregó correctamente, false en caso de error
     */
    public boolean agregar(String contenido) {
        try (FileWriter writer = new FileWriter(nombreArchivo, true)) {
            writer.write(contenido);
            System.out.println("Contenido agregado a " + nombreArchivo);
            return true;
        } catch (IOException e) {
            System.err.println("Error al agregar al archivo " + nombreArchivo + ": " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Elimina el archivo
     * @return true si se eliminó correctamente
     */
    public boolean eliminar() {
        File archivo = new File(nombreArchivo);
        if (archivo.exists()) {
            return archivo.delete();
        }
        return false;
    }
    
    /**
     * Obtiene el nombre del archivo
     * @return Nombre del archivo
     */
    public String getNombreArchivo() {
        return nombreArchivo;
    }
    
    /**
     * Establece un nuevo nombre de archivo
     * @param nombreArchivo Nuevo nombre
     */
    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }
}