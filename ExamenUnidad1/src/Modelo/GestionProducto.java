package Modelo;

import java.util.ArrayList;
import java.util.Iterator;

public class GestionProducto {
    private ArrayList<Producto> listaProductos;

    public GestionProducto() {
        this.listaProductos = new ArrayList<>();
    }

    
    public boolean insertar(Producto producto) {
        if (!existe(producto.getId())) {
            return listaProductos.add(producto);
        }
        return false;
    }

    
    public Producto buscar(String id) {
        Iterator<Producto> it = listaProductos.iterator();
        while (it.hasNext()) {
            Producto p = it.next();
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

  
    public boolean actualizar(Producto productoActualizado) {
        for (int i = 0; i < listaProductos.size(); i++) {
            if (listaProductos.get(i).getId().equals(productoActualizado.getId())) {
                listaProductos.set(i, productoActualizado);
                return true;
            }
        }
        return false;
    }

   
    public boolean eliminar(String id) {
        Iterator<Producto> it = listaProductos.iterator();
        while (it.hasNext()) {
            Producto p = it.next();
            if (p.getId().equals(id)) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    
    public boolean existe(String id) {
        return buscar(id) != null;
    }

    public ArrayList<Producto> getLista() {
        return listaProductos;
    }

   
    public ArrayList<Producto> getActivos() {
        ArrayList<Producto> activos = new ArrayList<>();
        for (Producto p : listaProductos) {
            if (p.isActivo()) {
                activos.add(p);
            }
        }
        return activos;
    }

    
    public void cargarDatosIniciales() {
        String[][] datos = {
            {"001", "Arroz 1kg", "Arroz blanco de grano largo", "Alimentos", "30.0", "35.0", "50", "10", "true"},
            {"002", "Azúcar 1kg", "Azúcar refinada", "Alimentos", "20.0", "25.0", "40", "10", "true"},
            {"003", "Harina 1kg", "Harina de trigo", "Alimentos", "23.0", "28.0", "30", "10", "true"},
            {"004", "Aceite 1L", "Aceite vegetal", "Alimentos", "45.0", "50.0", "25", "5", "true"},
            {"005", "Leche 1L", "Leche pasteurizada", "Alimentos", "30.0", "35.0", "20", "8", "true"}
        };

        for (String[] d : datos) {
            Producto p = new Producto(
                d[0], d[1], d[2], d[3],
                Double.parseDouble(d[4]), Double.parseDouble(d[5]),
                Integer.parseInt(d[6]), Integer.parseInt(d[7]),
                Boolean.parseBoolean(d[8])
            );
            listaProductos.add(p);
        }
    }
}