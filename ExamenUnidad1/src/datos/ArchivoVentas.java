package datos;

import Modelo.Venta;
import java.io.*;
import java.util.ArrayList;

public class ArchivoVentas {
    private static final String NOMBRE_ARCHIVO = "ventas.csv";

    public static void exportarCSV(ArrayList<Venta> lista) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(NOMBRE_ARCHIVO, true))) {
            for (Venta v : lista) {
                writer.println(v.toString());
            }
        } catch (IOException e) {
            System.err.println("Error al guardar ventas: " + e.getMessage());
        }
    }

    public static ArrayList<Venta> importarCSV() {
        ArrayList<Venta> lista = new ArrayList<>();
        File archivo = new File(NOMBRE_ARCHIVO);

        if (!archivo.exists()) {
            return lista;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 8) {
                    Venta v = new Venta(
                        datos[0], datos[1], datos[2],
                        Double.parseDouble(datos[3]),
                        Integer.parseInt(datos[4]),
                        datos[6], datos[7]
                    );
                    lista.add(v);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al leer ventas: " + e.getMessage());
        }
        return lista;
    }

    public static int obtenerUltimoCorrelativo() {
        int max = 0;
        File archivo = new File(NOMBRE_ARCHIVO);
        
        if (!archivo.exists()) {
            return 0;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            String ultimaLinea = null;
            while ((linea = reader.readLine()) != null) {
                ultimaLinea = linea;
            }
            if (ultimaLinea != null) {
                String[] datos = ultimaLinea.split(",");
                if (datos.length > 0) {
                    max = Integer.parseInt(datos[0]);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al leer correlativo: " + e.getMessage());
        }
        return max;
    }
}