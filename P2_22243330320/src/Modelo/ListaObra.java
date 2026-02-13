package Modelo;

import java.util.LinkedList;
import javax.swing.DefaultListModel;

public class ListaObra {
    private LinkedList<Obra> lista;
    
    public ListaObra() {
        lista = new LinkedList<>();
    }
    
    public void insertar(Obra obra) {
        lista.add(obra);
    }
    
    public void eliminar(int indice) {
        if(indice >= 0 && indice < lista.size()) {
            lista.remove(indice);
        }
    }
    
    public Obra obtener(int indice) {
        if(indice >= 0 && indice < lista.size()) {
            return lista.get(indice);
        }
        return null;
    }
    
    public DefaultListModel<String> getModeloLista() {
        DefaultListModel<String> model = new DefaultListModel<>();
        for(Obra o : lista) {
            model.addElement(o.toString());
        }
        return model;
    }
    
    public int tamaño() {
        return lista.size();
    }
}