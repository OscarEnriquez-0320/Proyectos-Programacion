package principal.java.Modelo;

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
            inv.setCantidadActual(inv.getCantidadActual() + cantidad);
            return true;
        }
        return false;
    }

    public boolean quitarStock(String idProducto, int cantidad) {
        Inventario inv = buscar(idProducto);
        if (inv != null && inv.getCantidadActual() >= cantidad) {
            inv.setCantidadActual(inv.getCantidadActual() - cantidad);
            return true;
        }
        return false;
    }

    public ArrayList<Inventario> getDisponibles() {
        ArrayList<Inventario> disponibles = new ArrayList<>();
        for (Inventario inv : listaInventario) {
            if (inv.getCantidadActual() > 0) {
                disponibles.add(inv);
            }
        }
        return disponibles;
    }

    public ArrayList<Inventario> getItemsConStockBajo() {
        ArrayList<Inventario> bajos = new ArrayList<>();
        for (Inventario inv : listaInventario) {
            if (inv.getCantidadActual() <= inv.getCantidadMinima()) {
                bajos.add(inv);
            }
        }
        return bajos;
    }

    public ArrayList<Inventario> getLista() {
        return listaInventario;
    }

    public void sincronizarConProductos(ArrayList<ProductoAbstract> productos) {
        listaInventario.clear();
        for (ProductoAbstract p : productos) {
            Inventario inv = new Inventario(
                p.getId(),
                p.getNombre(),
                p.getStock(),
                p.getStockMinimo(),
                p.getCategoria(),
                p.getPrecioVenta()
            );
            listaInventario.add(inv);
        }
    }
}