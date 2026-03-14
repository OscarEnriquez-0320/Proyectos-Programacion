package datos;

import Modelo.Producto;
import java.io.*;
import java.util.ArrayList;

public class ArchivoProducto {
    private static final String NOMBRE_ARCHIVO = "productos.csv";

    public static void exportarCSV(ArrayList<Producto> lista) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(NOMBRE_ARCHIVO))) {
            for (Producto p : lista) {
                writer.println(p.toString());
            }
            System.out.println("Datos guardados correctamente en " + NOMBRE_ARCHIVO);
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo: " + e.getMessage());
        }
    }

    public static ArrayList<Producto> importarCSV() {
        ArrayList<Producto> lista = new ArrayList<>();
        File archivo = new File(NOMBRE_ARCHIVO);

        if (!archivo.exists()) {
            System.out.println("El archivo no existe, se creará uno nuevo");
            return lista;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 9) {
                    Producto p = new Producto(
                        datos[0], datos[1], datos[2], datos[3],
                        Double.parseDouble(datos[4]), Double.parseDouble(datos[5]),
                        Integer.parseInt(datos[6]), Integer.parseInt(datos[7]),
                        Boolean.parseBoolean(datos[8])
                    );
                    lista.add(p);
                }
            }
            System.out.println("Datos cargados correctamente desde " + NOMBRE_ARCHIVO);
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error en el formato de los datos: " + e.getMessage());
        }
        return lista;
    }
}