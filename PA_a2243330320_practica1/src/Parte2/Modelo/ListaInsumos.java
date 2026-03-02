package Parte2.Modelo;
import java.util.ArrayList;

public class ListaInsumos {
    private ArrayList<Insumo> insumos;

    public ListaInsumos() {
        this.insumos = new ArrayList<>();
    }

    public boolean agregarInsumo(Insumo insumo) {
        boolean inserto = true;
        if (buscarInsumoPorId(insumo.getIdproducto()) == null)
            insumos.add(insumo);
        else inserto = false;
        return inserto;
    }
    public int size() {
        return insumos.size();
    }


    public boolean eliminarInsumoPorId(String id) {
        boolean elimino = true;
        Insumo insumo = buscarInsumoPorId(id);
        if (insumo != null)
            insumos.remove(insumo);
        else
            elimino = false;
        return elimino;
    }

    public String toString() {
        String resultado = "";
        for (Insumo insumo : insumos) {
            resultado += insumo.toString() + "\n";
        }
        return resultado;
    }

    private Insumo buscarInsumoPorId(String id) {
        for (Insumo insumo : insumos) {
            if (insumo.getIdproducto().equals(id)) {
                return insumo;
            }
        }
        return null;
    }
    public String buscarInsumo(String id) {
        for (Insumo insumo : insumos)
            if (insumo.getIdproducto().equals(id))
                return insumo.getProducto();
        return null;
    }

    public String[] idinsumos() {
        int pos = -1;
        String[] datos = new String[this.insumos.size()];
        for (Insumo nodo : this.insumos) {
            pos++;
            datos[pos] = nodo.getIdproducto();
        }
        return datos;
    }

}
