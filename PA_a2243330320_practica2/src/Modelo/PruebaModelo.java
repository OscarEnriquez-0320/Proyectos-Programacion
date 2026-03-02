package Modelo;
import Libreria.Archivotxt;
import Libreria.Librerias;
import Modelo.*;

public class PruebaModelo {
    public static void main(String[] args) {
        // 1. Probar creación de categorías
        System.out.println("=== PRUEBA DE CATEGORÍAS ===");
        ListaCategorias listaCat = new ListaCategorias();
        
        listaCat.agregarCategoria(new Categoria("CAT01", "Electrónica"));
        listaCat.agregarCategoria(new Categoria("CAT02", "Ropa"));
        listaCat.agregarCategoria(new Categoria("CAT03", "Hogar"));
        
        System.out.println("Categorías agregadas:");
        System.out.println(listaCat);
        
        // 2. Guardar categorías en archivo
        Archivotxt archivoCat = new Archivotxt("Categoria");
        archivoCat.guardar(listaCat.toArchivo());
        System.out.println("Categorías guardadas en archivo");
        
        // 3. Probar creación de insumos
        System.out.println("\n=== PRUEBA DE INSUMOS ===");
        ListaInsumos listaIns = new ListaInsumos();
        
        listaIns.agregarInsumo(new Insumo("001", "Televisor", "CAT01"));
        listaIns.agregarInsumo(new Insumo("002", "Camisa", "CAT02"));
        listaIns.agregarInsumo(new Insumo("003", "Sartén", "CAT03"));
        
        System.out.println("Insumos agregados:");
        System.out.println(listaIns);
        
        // 4. Probar generación de modelo para JTable
        var modelo = listaIns.getmodelo(listaCat);
        System.out.println("\nModelo para JTable generado:");
        System.out.println("Columnas: " + modelo.getColumnCount());
        System.out.println("Filas: " + modelo.getRowCount());
        
        // 5. Probar generación de ID automático
        Librerias lib = new Librerias();
        String nuevoId = lib.generarId(listaCat.size(), "CAT");
        System.out.println("\nNuevo ID generado: " + nuevoId);
    }
}