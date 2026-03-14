package Modelo;

import java.util.ArrayList;
import java.util.Iterator;

public class GestionInventario {
    private ArrayList<Inventario> listaInventario;

    public GestionInventario() {
        this.listaInventario = new ArrayList<>();
    }

   
    public boolean insertar(Inventario item) {
        if (!existe(item.getIdProducto())) {
            return listaInventario.add(item);
        }
        return false;
    }

    
    public Inventario buscar(String idProducto) {
        Iterator<Inventario> it = listaInventario.iterator();
        while (it.hasNext()) {
            Inventario inv = it.next();
            if (inv.getIdProducto().equals(idProducto)) {
                return inv;
            }
        }
        return null;
    }

   
    public boolean actualizar(Inventario itemActualizado) {
        for (int i = 0; i < listaInventario.size(); i++) {
            if (listaInventario.get(i).getIdProducto().equals(itemActualizado.getIdProducto())) {
                listaInventario.set(i, itemActualizado);
                return true;
            }
        }
        return false;
    }

   
    public boolean eliminar(String idProducto) {
        Iterator<Inventario> it = listaInventario.iterator();
        while (it.hasNext()) {
            Inventario inv = it.next();
            if (inv.getIdProducto().equals(idProducto)) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    
    public boolean existe(String idProducto) {
        return buscar(idProducto) != null;
    }

   
    public boolean agregarStock(String idProducto, int cantidad) {
        Inventario inv = buscar(idProducto);
        if (inv != null) {
            inv.agregarStock(cantidad);
            return true;
        }
        return false;
    }

  
    public boolean quitarStock(String idProducto, int cantidad) {
        Inventario inv = buscar(idProducto);
        if (inv != null) {
            return inv.quitarStock(cantidad);
        }
        return false;
    }

  
    public ArrayList<Inventario> getItemsConStockBajo() {
        ArrayList<Inventario> alertas = new ArrayList<>();
        for (Inventario inv : listaInventario) {
            if (inv.necesitaAlerta()) {
                alertas.add(inv);
            }
        }
        return alertas;
    }
   
    public ArrayList<Inventario> getDisponibles() {
        ArrayList<Inventario> disponibles = new ArrayList<>();
        for (Inventario inv : listaInventario) {
            if (inv.getEstado().equals("Disponible")) {
                disponibles.add(inv);
            }
        }
        return disponibles;
    }

 
    public ArrayList<Inventario> getLista() {
        return listaInventario;
    }
    public ArrayList<Inventario> getProductosConStockBajo() {
        ArrayList<Inventario> alertas = new ArrayList<>();
        for (Inventario inv : listaInventario) {
            if (inv.getCantidadActual() <= inv.getCantidadMinima()) {
                alertas.add(inv);
            }
        }
        return alertas;
    }
   
    public void sincronizarConProductos(ArrayList<Producto> productos) {
        listaInventario.clear();
        for (Producto p : productos) {
            Inventario inv = new Inventario(
                p.getId(),
                p.getNombre(),
                p.getStockInicial(),
                p.getStockMinimo(),
                p.getCategoria(),
                p.getPrecioVenta()
            );
            listaInventario.add(inv);
        }
    }
}