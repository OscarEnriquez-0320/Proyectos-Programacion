package Parte2.Modelo;

import java.util.ArrayList;
import javax.swing.JComboBox;

public class ListaCategorias {
    private ArrayList<Categoria> categorias;

    public ListaCategorias() {
        this.categorias = new ArrayList<>();
    }

    public void agregarCategoria(Categoria categoria) {
        if (buscarCategoriaPorId(categoria.getIdcategoria()) == null) {
            categorias.add(categoria);
        }
    }

    public boolean eliminarCategoriaPorId(String id) {
        Categoria categoria = buscarCategoriaPorId(id);
        if (categoria != null) {
            categorias.remove(categoria);
            return true;
        }
        return false;
    }

    private Categoria buscarCategoriaPorId(String id) {
        for (Categoria categoria : categorias) {
            if (categoria.getIdcategoria().equals(id)) {
                return categoria;
            }
        }
        return null;
    }

    public String buscarCategoria(String id) {
        Categoria categoria = buscarCategoriaPorId(id);
        if (categoria != null) {
            return categoria.getCategoria();
        }
        return null;
    }

    public JComboBox<Categoria> agregarCategoriasAsComboBox(JComboBox<Categoria> comboBox) {
        comboBox.removeAllItems();
        for (Categoria categoria : categorias) {
            comboBox.addItem(categoria);
        }
        return comboBox;
    }

    public int size() {
        return categorias.size();
    }

    public Object[] CategoriasArreglo() {
        return this.categorias.toArray();
    }

    @Override
    public String toString() {
        StringBuilder resultado = new StringBuilder();
        for (Categoria categoria : categorias) {
            resultado.append(categoria.toString()).append("\n");
        }
        return resultado.toString();
    }
}