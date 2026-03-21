package principal.java.datos;

import principal.java.Modelo.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.ArrayList;

public class ArchivoProductoJSON {
    private static final String NOMBRE_ARCHIVO = "productos.json";

    public static void exportarJSON(ArrayList<ProductoAbstract> lista) {
        JSONArray jsonArray = new JSONArray();

        for (ProductoAbstract p : lista) {
            JSONObject obj = new JSONObject();
            obj.put("tipo", p.getTipo());
            obj.put("id", p.getId());
            obj.put("nombre", p.getNombre());
            obj.put("descripcion", p.getDescripcion());
            obj.put("categoria", p.getCategoria());
            obj.put("precioCompra", p.getPrecioCompra());
            obj.put("precioVenta", p.getPrecioVenta());
            obj.put("stock", p.getStock());
            obj.put("stockMinimo", p.getStockMinimo());
            obj.put("activo", p.isActivo());
            obj.put("rutaImagen", p.getRutaImagen());
            jsonArray.add(obj);
        }

        try (FileWriter file = new FileWriter(NOMBRE_ARCHIVO)) {
            file.write(jsonArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<ProductoAbstract> importarJSON() {
        ArrayList<ProductoAbstract> lista = new ArrayList<>();
        JSONParser parser = new JSONParser();

        File archivo = new File(NOMBRE_ARCHIVO);
        if (!archivo.exists()) {
            return lista;
        }

        try (FileReader reader = new FileReader(archivo)) {
            JSONArray jsonArray = (JSONArray) parser.parse(reader);

            for (Object obj : jsonArray) {
                try {
                    JSONObject jsonObj = (JSONObject) obj;
                    
                    String tipo = (String) jsonObj.get("tipo");
                    if (tipo == null) continue;
                    
                    String id = (String) jsonObj.get("id");
                    String nombre = (String) jsonObj.get("nombre");
                    String descripcion = (String) jsonObj.get("descripcion");
                    String categoria = (String) jsonObj.get("categoria");
                    double precioCompra = ((Number) jsonObj.get("precioCompra")).doubleValue();
                    double precioVenta = ((Number) jsonObj.get("precioVenta")).doubleValue();
                    int stock = ((Number) jsonObj.get("stock")).intValue();
                    int stockMinimo = ((Number) jsonObj.get("stockMinimo")).intValue();
                    boolean activo = (boolean) jsonObj.get("activo");
                    String rutaImagen = (String) jsonObj.get("rutaImagen");
                    
                    if (rutaImagen != null && rutaImagen.contains("Productos/")) {
                        rutaImagen = rutaImagen.replace("Productos/", "");
                    }

                    ProductoAbstract p = null;
                    switch (tipo) {
                        case "Abarrotes":
                            p = new Abarrotes(id, nombre, descripcion, precioCompra, precioVenta, stock, stockMinimo, rutaImagen);
                            break;
                        case "Bebidas":
                            p = new Bebidas(id, nombre, descripcion, precioCompra, precioVenta, stock, stockMinimo, rutaImagen);
                            break;
                        case "Lácteos":
                            p = new Lacteos(id, nombre, descripcion, precioCompra, precioVenta, stock, stockMinimo, rutaImagen);
                            break;
                        case "Frutas y Verduras":
                            p = new FrutasVerduras(id, nombre, descripcion, precioCompra, precioVenta, stock, stockMinimo, rutaImagen);
                            break;
                        case "Carnes":
                            p = new Carnes(id, nombre, descripcion, precioCompra, precioVenta, stock, stockMinimo, rutaImagen);
                            break;
                        case "Salchichonería":
                            p = new Salchichoneria(id, nombre, descripcion, precioCompra, precioVenta, stock, stockMinimo, rutaImagen);
                            break;
                        case "Panadería":
                            p = new Panaderia(id, nombre, descripcion, precioCompra, precioVenta, stock, stockMinimo, rutaImagen);
                            break;
                        case "Limpieza":
                            p = new Limpieza(id, nombre, descripcion, precioCompra, precioVenta, stock, stockMinimo, rutaImagen);
                            break;
                        case "Cuidado Personal":
                            p = new CuidadoPersonal(id, nombre, descripcion, precioCompra, precioVenta, stock, stockMinimo, rutaImagen);
                            break;
                        case "Snacks":
                            p = new Snacks(id, nombre, descripcion, precioCompra, precioVenta, stock, stockMinimo, rutaImagen);
                            break;
                        case "Mascotas":
                            p = new Mascotas(id, nombre, descripcion, precioCompra, precioVenta, stock, stockMinimo, rutaImagen);
                            break;
                    }
                    
                    if (p != null) {
                        p.setActivo(activo);
                        p.setCategoria(categoria);
                        lista.add(p);
                    }
                    
                } catch (Exception e) {
                    System.out.println("Error al procesar producto: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}