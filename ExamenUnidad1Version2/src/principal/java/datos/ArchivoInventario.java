package principal.java.datos;

import principal.java.Modelo.*;
import java.io.*;
import java.util.ArrayList;

public class ArchivoInventario {
    private static final String NOMBRE_ARCHIVO = "inventario.csv";

    public static void exportarCSV(ArrayList<Inventario> lista) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(NOMBRE_ARCHIVO))) {
            for (Inventario inv : lista) {
                writer.println(inv.toString());
            }
            System.out.println("Inventario guardado en " + NOMBRE_ARCHIVO);
        } catch (IOException e) {
            System.err.println("Error al guardar inventario: " + e.getMessage());
        }
    }

    public static ArrayList<Inventario> importarCSV() {
        ArrayList<Inventario> lista = new ArrayList<>();
        File archivo = new File(NOMBRE_ARCHIVO);

        if (!archivo.exists()) {
            return lista;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 7) {
                    Inventario inv = new Inventario(
                        datos[0], datos[1], 
                        Integer.parseInt(datos[2]), Integer.parseInt(datos[3]),
                        datos[4], Double.parseDouble(datos[5])
                    );
                    lista.add(inv);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al leer inventario: " + e.getMessage());
        }
        return lista;
    }
}