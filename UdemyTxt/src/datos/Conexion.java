package datos; // Asegúrate de que coincida con el nombre de tu paquete

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;

public class Conexion {

    /**
     * Este método es el que invoca tu clase Test.
     * Lee un archivo .txt y devuelve cada línea como un elemento de una lista.
     */
    public static List<String> cargarArchivo(File archivo) {
        try {
            // Verificamos si el archivo existe para evitar errores
            if (!archivo.exists()) {
                System.out.println("Ojo: El archivo no existe en: " + archivo.getAbsolutePath());
                return new ArrayList<>();
            }
            // Usamos la librería externa que instalamos
            return FileUtils.readLines(archivo, "UTF-8");
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}